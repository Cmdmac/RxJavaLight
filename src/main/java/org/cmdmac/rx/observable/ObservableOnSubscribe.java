package org.cmdmac.rx.observable;

import org.cmdmac.rx.emmiter.ObservableEmitter;

public interface ObservableOnSubscribe<T> {
    void subscribe(ObservableEmitter<T> emitter);
}
