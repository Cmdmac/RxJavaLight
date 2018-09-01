package org.cmdmac.rx.observable;

import org.cmdmac.rx.Func;
import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;

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

    static class ObserverMap<F, T> implements Observer<F> {
        Observer<? super T> observer;
        Func<? super F, ? extends T> func;

        public ObserverMap(Observer<? super T> observer, Func<? super F, ? extends T> func) {
            this.observer = observer;
            this.func = func;
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
    }
}
