package org.cmdmac.rx.observable;

public interface ObservableEmitter<T> {
    void onNext(final T data);
    void onComplete();
}
