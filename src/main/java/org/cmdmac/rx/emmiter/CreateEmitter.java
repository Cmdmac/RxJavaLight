package org.cmdmac.rx.emmiter;

import org.cmdmac.rx.Observer;
import org.cmdmac.rx.observable.ObservableEmitter;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public final class CreateEmitter<T> implements ObservableEmitter<T> {
    Observer<? super T> observer;
    public CreateEmitter(Observer<? super T> observer) {
        this.observer = observer;
    }

    @Override
    public void onNext(T data) {
        observer.onNext(data);
    }

    @Override
    public void onComplete() {
        observer.onComplete();
    }
}
