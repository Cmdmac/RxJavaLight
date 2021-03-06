package org.cmdmac.rx.observable;

import org.cmdmac.rx.Action;
import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableOnComplete<T> extends AbstractObservable<T> {
    Action action;
    public ObservableOnComplete(Observable<T> source, Action onCompleteAction) {
        super(source);
        this.action = onCompleteAction;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        ObserverOnComplete<T> observerOnComplete = new ObserverOnComplete<>(observer, action);
        source.subscribe(observerOnComplete);
    }

    static class ObserverOnComplete<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {

        Action action;
        Observer<? super T> observer;
        public ObserverOnComplete(Observer<? super T> observer, Action action) {
            this.observer = observer;
            this.action = action;
        }

        @Override
        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this, d);
            observer.onSubscribe(this);
        }

        @Override
        public void onNext(T data) {
            observer.onNext(data);
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

            action.run();
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
