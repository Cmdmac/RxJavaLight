package org.cmdmac.rx.observable;

import org.cmdmac.rx.emmiter.CreateEmitter;
import org.cmdmac.rx.Observable;
import org.cmdmac.rx.Observer;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public class ObservableCreate<T> extends Observable<T> {

    ObservableOnSubscribe source;
    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        CreateEmitter<T> parent = new CreateEmitter<T>(observer);
        source.subscribe(parent);
    }
}
