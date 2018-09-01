package org.cmdmac.rx;

public interface Observer<T> {
    void onNext(T data);
    void onError(Throwable throwable);
    void onComplete();
}
