package com.reb3llion.profiler.utils;


import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

import javax.security.auth.callback.Callback;

public class AsyncExecutor {
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void execute(Runnable runnable){
        executor.execute(runnable);
    }
    public interface AsyncCallback {
        void onStart();

        void onSuccess(STATUS result);

        void onFailure(STATUS error);
    }
}
