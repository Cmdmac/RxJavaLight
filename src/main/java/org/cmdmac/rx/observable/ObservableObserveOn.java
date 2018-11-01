package org.cmdmac.rx.observable;

import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;
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
        ObserveOnObserver<T> observeOnObserver = new ObserveOnObserver<>(observer);
        source.subscribe(observeOnObserver);
    }

    final class ObserveOnObserver<T> implements Observer<T>, Disposable {
        Observer<? super T> observer;
        Disposable s;
        public ObserveOnObserver(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.s, d)) {
                this.s = d;
                observer.onSubscribe(this);
            }
        }

        @Override
        public void onNext(final T data) {
            schedulers.execute(new Runnable() {
                @Override
                public void run() {
                    if (!s.isDisposed()) {
                        observer.onNext(data);
                    }
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

        @Override
        public boolean isDisposed() {
            if (s != null) {
                return s.isDisposed();
            }
            return false;
        }

        @Override
        public void dispose() {
            if (s != null) {
                s.dispose();
            }
        }
    }
}
