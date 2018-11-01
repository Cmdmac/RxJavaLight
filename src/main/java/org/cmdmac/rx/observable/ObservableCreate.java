package org.cmdmac.rx.observable;

import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;
import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.emmiter.ObservableEmitter;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableCreate<T> extends Observable<T> {

    ObservableOnSubscribe source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        CreateEmitter<T> parent = new CreateEmitter<T>(observer);
        observer.onSubscribe(parent);
        source.subscribe(parent);
    }

    final class CreateEmitter<T> extends AtomicReference<Disposable> implements ObservableEmitter<T>, Disposable {
        Observer<? super T> observer;
        public CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T data) {
            if (!isDisposed()) {
                observer.onNext(data);
            }
        }

        @Override
        public void onComplete() {
            if (!isDisposed()) {
                observer.onComplete();
            }
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }
    }
}
