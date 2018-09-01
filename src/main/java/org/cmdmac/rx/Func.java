package org.cmdmac.rx;

public interface Func<F, T> {
    T apply(F from);
}
