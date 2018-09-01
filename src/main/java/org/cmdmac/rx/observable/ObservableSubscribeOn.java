package org.cmdmac.rx.observable;

import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.scheduler.Schedulers;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableSubscribeOn<T> extends AbstractObservable<T> {
    Schedulers schedulers;

    public ObservableSubscribeOn(Observable<T> source, Schedulers schedulers) {
        super(source);
        this.schedulers = schedulers;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        final ObserverSubscribeOn observerSubscribeOn = new ObserverSubscribeOn(observer);
        schedulers.execute(new Runnable() {
            @Override
            public void run() {
                source.subscribe(observerSubscribeOn);
            }
        });
    }

    final class ObserverSubscribeOn implements Observer<T> {
        Observer<? super T> observer;
        public ObserverSubscribeOn(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T data) {
            observer.onNext(data);
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }
    }
}
