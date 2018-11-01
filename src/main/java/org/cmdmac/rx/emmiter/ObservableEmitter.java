package org.cmdmac.rx.emmiter;

public interface ObservableEmitter<T> {
    void onNext(final T data);
    void onComplete();
}
