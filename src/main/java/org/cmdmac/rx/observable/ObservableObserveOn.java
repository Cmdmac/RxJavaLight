package org.cmdmac.rx.observable;

import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.scheduler.Schedulers;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableObserveOn<T> extends AbstractObservable<T> {
    Schedulers schedulers;

    public ObservableObserveOn(Observable<T> source, Schedulers schedulers) {
        super(source);
        this.schedulers = schedulers;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        ObserverObserveOn<T> observerObserveOn = new ObserverObserveOn<>(observer);
        source.subscribe(observerObserveOn);
    }

    final class ObserverObserveOn<T> implements Observer<T> {
        Observer<? super T> observer;
        public ObserverObserveOn(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(final T data) {
            schedulers.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onNext(data);
                }
            });

        }

        @Override
        public void onError(final Throwable throwable) {
            schedulers.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onError(throwable);
                }
            });
        }

        @Override
        public void onComplete() {
            schedulers.execute(new Runnable() {
                @Override
                public void run() {
                    observer.onComplete();
                }
            });
        }

    }
}
