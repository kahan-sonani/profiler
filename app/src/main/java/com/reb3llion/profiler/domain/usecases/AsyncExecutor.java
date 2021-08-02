package com.reb3llion.profiler.domain.usecases;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncExecutor {
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public interface AsyncCallback<T> {
        void onStart();

        void onSuccess(Data<T> result);

        void onFailure(Data<T> error);
    }

    public static class Data<T> {
        T data;

        public Data(T data) {
            this.data = data;
        }

        public Data() {
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
