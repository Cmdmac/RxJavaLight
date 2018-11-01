package org.cmdmac.rx.observer;

import org.cmdmac.rx.Action;
import org.cmdmac.rx.Consumer;
import org.cmdmac.rx.Observer;
import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.disposable.DisposableHelper;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class LambdaObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    Consumer<? super T> onNext;
    Consumer<? super Throwable> onError;
    Action onComplete;

    public LambdaObserver(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
    }

    @Override
    public void onSubscribe(Disposable d) {
        DisposableHelper.setOnce(this, d);
    }

    @Override
    public void onNext(T data) {
        if (isDisposed()) {
            return;
        }
        if (onNext != null) {
            onNext.accept(data);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (isDisposed()) {
            return;
        }
        if (onError != null) {
            onError.accept(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (isDisposed()) {
            return;
        }
        if (onComplete != null) {
            onComplete.run();
        }
    }

    @Override
    public boolean isDisposed() {
        return get() == DisposableHelper.DISPOSED;
    }

    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }
}
