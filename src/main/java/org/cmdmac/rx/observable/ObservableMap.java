package org.cmdmac.rx.observable;

import org.cmdmac.rx.Func;
import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableMap<F, T> extends Observable<T> {
    Observable<F> source;
    Func<? super F, ? extends T> func;

    public ObservableMap(Observable<F> source, Func<? super F, ? extends T> func) {
        this.source = source;
        this.func = func;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        ObserverMap<F, T> map = new ObserverMap<F, T>(observer, func);
        source.subscribe(map);
    }

    static class ObserverMap<F, T> extends AtomicReference<Disposable> implements Observer<F>, Disposable {
        Observer<? super T> observer;
        Func<? super F, ? extends T> func;

        public ObserverMap(Observer<? super T> observer, Func<? super F, ? extends T> func) {
            this.observer = observer;
            this.func = func;
        }

        @Override
        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this, d);
            observer.onSubscribe(this);
        }

        @Override
        public void onNext(F data) {
            T t = func.apply(data);
            observer.onNext(t);
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
        }
    }
}
