package org.example.fault.retry;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.example.model.RpcResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 固定时间间隔 - 重试策略
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws ExecutionException, RetryException {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                //重试等待策略，使用withWaitStrategy方法指定策略，选择fixedWait固定时间间隔策略
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //重试停止策略，使用withStopStrategy方法指定策略，超过stopAfterAttempt最大重试次数停止
                .withRetryListener(new RetryListener() {
                    //使用withRetryListener监听重试，每次重试时，除了再次执行任务，还能打印重试次数
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数{}", attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }
}
