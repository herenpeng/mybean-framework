package com.mybeanframework.server.util;

import java.util.concurrent.*;

/**
 * @author herenpeng
 * @since 2021-05-30 13:05
 */
public class HttpServerThreadPool {

    /**
     * 核心线程数
     */
    public static final int CORE_POOL_SIZE = 5;
    /**
     * 最大线程数
     */
    public static final int MAXIMUM_POOL_SIZE = 10;
    /**
     * 等待时间
     */
    public static final long KEEP_ALIVE_TIME = 60L;
    /**
     * 队列容量
     */
    public static final int QUEUE_CAPACITY = 10;

    private static ThreadPoolExecutor pool;

    static {
        pool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 执行线程任务
     *
     * @param runnable Runnable接口
     */
    public static void execute(Runnable runnable) {
        pool.execute(runnable);
    }


}
