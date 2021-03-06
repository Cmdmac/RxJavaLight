package org.cmdmac.rx;

import org.cmdmac.rx.disposable.Disposable;
import org.cmdmac.rx.observable.ObservableCreate;
import org.cmdmac.rx.observable.ObservableMap;
import org.cmdmac.rx.observable.ObservableObserveOn;
import org.cmdmac.rx.observable.ObservableOnComplete;
import org.cmdmac.rx.observable.ObservableOnNext;
import org.cmdmac.rx.observable.ObservableOnSubscribe;
import org.cmdmac.rx.observable.ObservableSubscribeOn;
import org.cmdmac.rx.observer.LambdaObserver;
import org.cmdmac.rx.scheduler.Schedulers;

public abstract class Observable<T> {

    public static <T> Observable<T> create(ObservableOnSubscribe<T> observableOnSubscribe) {
        return new ObservableCreate(observableOnSubscribe);
//        return observableOnSubscribe;
    }

    public Observable<T> subscribeOn(Schedulers schedulers) {
        return new ObservableSubscribeOn<T>(this, schedulers);
    }

    public Observable<T> observeOn(Schedulers schedulers) {
        return new ObservableObserveOn<T>(this ,schedulers);
    }

    public <R> Observable<R> map(Func<? super T, ? extends R> func) {
        return new ObservableMap<T, R>(this, func);
    }

    public abstract void subscribe(Observer<? super T> observer);
    public Disposable subscribe(Consumer<? super T> onNext) {
        return subscribe(onNext, null);
    }

    public Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        Observer<T> observer = new LambdaObserver<>(onNext, onError, null);
        subscribe(observer);
        return (Disposable)observer;
    }

    public Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action action) {
        Observer<T> observer = new LambdaObserver<>(onNext, onError, action);
        subscribe(observer);
        return (Disposable)observer;
    }

    public Observable<T> doOnComplete(Action action) {
        return new ObservableOnComplete(this, action);
    }

    public Observable<T> doOnNext(Consumer<T> consumer) {
        return new ObservableOnNext(this, consumer);
    }
}
