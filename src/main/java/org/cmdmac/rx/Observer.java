package org.cmdmac.rx;

import org.cmdmac.rx.disposable.Disposable;

public interface Observer<T> {
    void onSubscribe(Disposable d);
    void onNext(T data);
    void onError(Throwable throwable);
    void onComplete();
}
