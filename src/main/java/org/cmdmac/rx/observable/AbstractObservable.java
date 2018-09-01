package org.cmdmac.rx.observable;

import org.cmdmac.rx.Observable;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public abstract class AbstractObservable<T> extends Observable<T> {
    protected Observable<T> source;
    public AbstractObservable(Observable<T> source) {
        this.source = source;
    }
}
