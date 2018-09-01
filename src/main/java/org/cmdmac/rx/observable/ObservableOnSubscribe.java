package org.cmdmac.rx.observable;

public interface ObservableOnSubscribe<T> {
    void subscribe(ObservableEmitter<T> emitter);
}
