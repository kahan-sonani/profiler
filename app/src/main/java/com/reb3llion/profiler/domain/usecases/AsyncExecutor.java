package com.reb3llion.profiler.domain.usecases;


import com.reb3llion.profiler.presenter.enums.STATUS;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
