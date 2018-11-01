package org.cmdmac.rx.disposable;

/**
 * Created by fengzhiping on 2018/10/31.
 */

public interface Disposable {
    boolean isDisposed();
    void dispose();
}
