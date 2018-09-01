package org.cmdmac.rx.scheduler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Schedulers {
    abstract public void execute(Runnable runnable);

    static class Main extends Schedulers {
        Handler handler;
        public Main() {
            handler = new Handler(Looper.getMainLooper());
        }
        public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    }

    static class Io extends Schedulers {
        ScheduledExecutorService executors = Executors.newScheduledThreadPool(4);
        public Io() {

        }

        @Override
        public void execute(Runnable runnable) {
            executors.submit(runnable);
        }
    }

    static class SingleThread extends Schedulers {

        ExecutorService executors = Executors.newSingleThreadExecutor();
        public SingleThread() {

        }

        @Override
        public void execute(Runnable runnable) {
            executors.execute(runnable);
        }
    }

    static class NewThread extends Schedulers {

        ExecutorService executors = Executors.newFixedThreadPool(4);
        public NewThread() {

        }

        @Override
        public void execute(Runnable runnable) {
            executors.execute(runnable);
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
}
