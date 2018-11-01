package org.cmdmac.rx.scheduler;

import android.os.Handler;
import android.os.Looper;

import org.cmdmac.rx.disposable.Disposable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Schedulers {
    abstract public Disposable execute(Runnable runnable);

    static class Main extends Schedulers {
        Handler handler;
        public Main() {
            handler = new Handler(Looper.getMainLooper());
        }
        public Disposable execute(Runnable runnable) {
            DisposeTask task = new DisposeTask(runnable);
            handler.post(task);
            return task;
        }
    }

    static class Io extends Schedulers {
        ScheduledExecutorService executors = Executors.newScheduledThreadPool(4);
        public Io() {

        }

        @Override
        public Disposable execute(Runnable runnable) {
            DisposeTask task = new DisposeTask(runnable);
            executors.submit(task);
            return task;
        }
    }

    static class SingleThread extends Schedulers {

        ExecutorService executors = Executors.newSingleThreadExecutor();
        public SingleThread() {

        }

        @Override
        public Disposable execute(Runnable runnable) {
            DisposeTask task = new DisposeTask(runnable);
            executors.execute(task);
            return task;
        }
    }

    static class NewThread extends Schedulers {

        ExecutorService executors = Executors.newFixedThreadPool(4);
        public NewThread() {

        }

        @Override
        public Disposable execute(Runnable runnable) {
            DisposeTask task = new DisposeTask(runnable);
            executors.execute(task);
            return task;
        }
    }

    private static Main sMain;
    private static Io sIo;
    private static SingleThread sSingleThread;
    private static NewThread sNewThread;

    public static Schedulers mainThread() {
        if (sMain == null) {
            sMain = new Main();
        }
        return sMain;
    }

    public static Schedulers io() {
        if (sIo == null) {
            sIo = new Io();
        }
        return sIo;
    }

    public static Schedulers singleThead() {
        if (sSingleThread == null) {
            sSingleThread = new SingleThread();
        }
        return sSingleThread;
    }

    public static Schedulers newThread() {
        if (sNewThread == null) {
            sNewThread = new NewThread();
        }
        return sNewThread;
    }

    static class DisposeTask implements Runnable, Disposable {
        boolean isDisposed = false;
        Runnable runnable;
        DisposeTask(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            if (!isDisposed()) {
                this.runnable.run();
            }
        }

        @Override
        public boolean isDisposed() {
            return isDisposed;
        }

        @Override
        public void dispose() {
            isDisposed = true;
        }
    }
}
