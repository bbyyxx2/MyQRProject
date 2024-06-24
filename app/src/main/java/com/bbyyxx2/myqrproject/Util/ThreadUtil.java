package com.bbyyxx2.myqrproject.Util;

import java.util.concurrent.*;

public class ThreadUtil {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * 在新线程中运行指定的Callable任务，并返回Future对象。
     * 可以通过调用Future.get()来阻塞等待任务完成。
     *
     * @param callable 要在新线程中执行的任务
     * @param <T>      任务返回值类型
     * @return 一个Future对象，用于跟踪任务状态和获取结果
     */
    public static <T> Future<T> runInNewThread(Callable<T> callable) {
        return executor.submit(callable);
    }

    /**
     * 关闭ExecutorService，释放资源。
     */
    public static void shutdown() {
        executor.shutdown();
    }
}
