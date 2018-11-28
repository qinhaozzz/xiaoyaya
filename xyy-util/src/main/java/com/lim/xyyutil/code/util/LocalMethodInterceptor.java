package com.lim.xyyutil.code.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lim.xyyutil.annotation.LocalLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * aop 拦截器
 * @author qinhao
 */
@Aspect
@Configuration
public class LocalMethodInterceptor {
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

    @Pointcut("execution(public * *(..)) && @annotation(com.lim.xyyutil.annotation.LocalLock)")
    public void localLockCut() {
    }

    @Around(value = "localLockCut()")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        //TODO String key = localLock.key() + pjp.getArgs()
        String key = localLock.key();
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                throw new RuntimeException("请勿重复请求");
            }
            // 如果是第一次请求,就将 key 当前对象压入缓存中
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        } finally {
            // TODO 为了演示效果,这里就不调用 CACHES.invalidate(key); 代码了
            System.out.println("finally");
        }
    }
}
