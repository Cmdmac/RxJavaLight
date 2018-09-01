package org.cmdmac.rx;

/**
 * Created by fengzhiping on 2018/9/1.
 */

public interface Consumer<T> {
    void accept(T data);
}
