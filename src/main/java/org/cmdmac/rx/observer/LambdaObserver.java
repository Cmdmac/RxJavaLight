package org.cmdmac.rx.observer;

import org.cmdmac.rx.Action;
import org.cmdmac.rx.Consumer;
import org.cmdmac.rx.Observer;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class LambdaObserver<T> implements Observer<T> {
    Consumer<? super T> onNext;
    Consumer<? super Throwable> onError;
    Action onComplete;

    public LambdaObserver(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
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

    @Override
    public void onComplete() {
        if (onComplete != null) {
            onComplete.run();
        }
    }
}
