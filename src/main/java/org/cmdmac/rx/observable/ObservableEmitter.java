package org.cmdmac.rx.observable;

public interface ObservableEmitter<T> {
    public void onNext(final T data);
}
