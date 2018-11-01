package org.cmdmac.rx.observable;

import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;
import org.cmdmac.rx.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicReference;

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
    public void subscribe(Observer<? super T> s) {
        final SubscribeOnObserver parent = new SubscribeOnObserver(s);
        s.onSubscribe(parent);
        parent.setDisposed(schedulers.execute(new SubscribeTask(parent)));
    }

    final class SubscribeOnObserver extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        Observer<? super T> observer;
        final AtomicReference<Disposable> s;
        public SubscribeOnObserver(Observer<? super T> observer) {
            this.observer = observer;
            this.s = new AtomicReference<Disposable>();
        }

        @Override
        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this.s, d);
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
            observer.onComplete();
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(s);
        }

        public void setDisposed(Disposable d) {
            DisposableHelper.setOnce(this, d);
        }
    }

    class SubscribeTask implements Runnable {
        SubscribeOnObserver parent;
        public SubscribeTask(SubscribeOnObserver parent) {
            this.parent = parent;
        }
        @Override
        public void run() {
            source.subscribe(parent);
        }
    }
}
