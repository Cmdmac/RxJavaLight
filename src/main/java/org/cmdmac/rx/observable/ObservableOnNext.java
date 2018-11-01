package org.cmdmac.rx.observable;

import org.cmdmac.rx.Consumer;
import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableOnNext<T> extends AbstractObservable<T> {
    Consumer<T> consumer;
    public ObservableOnNext(Observable<T> source, Consumer<T> onCompleteAction) {
        super(source);
        this.consumer = onCompleteAction;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        ObserverOnNext<T> observerOnNext = new ObserverOnNext<>(observer, consumer);
        source.subscribe(observerOnNext);
    }

    static class ObserverOnNext<T> extends AtomicReference<Disposable> implements Observer<T> , Disposable {

        Consumer<T> consumer;
        Observer<? super T> observer;
        public ObserverOnNext(Observer<? super T> observer, Consumer<T> consumer) {
            this.observer = observer;
            this.consumer = consumer;
        }

        @Override
        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this, d);
            observer.onSubscribe(this);
        }

        @Override
        public void onNext(T data) {
            observer.onNext(data);
            consumer.accept(data);
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }

        @Override
        public void onComplete() {
            try {
                observer.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                observer.onError(e);
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
