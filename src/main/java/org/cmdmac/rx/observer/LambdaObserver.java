package org.cmdmac.rx.observer;

import org.cmdmac.rx.Consumer;
import org.cmdmac.rx.Observer;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class LambdaObserver<T> implements Observer<T> {
    Consumer<? super T> onNext;
    Consumer<? super Throwable> onError;

    public LambdaObserver(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        this.onNext = onNext;
        this.onError = onError;
    }

    @Override
    public void onNext(T data) {
        if (onNext != null) {
            onNext.accept(data);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (onError != null) {
            onError.accept(throwable);
        }
    }
}
