package org.wildfly.examples;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Log
public class LoggerInterceptor {

    @AroundInvoke
    public Object logMethod(InvocationContext ctx) throws Exception {
        long start = System.nanoTime();
        String className = ctx.getMethod().getDeclaringClass().getSimpleName();
        String methodName = ctx.getMethod().getName();
        Object[] params = ctx.getParameters();
        log.info(() -> String.format("→ %s.%s(%s)", className, methodName, formatParams(params)));
        try {
            Object result = ctx.proceed();
            long durationMicros = (System.nanoTime() - start) / 1_000;
            log.info(() -> String.format("← %s.%s returned: %s [%d µs]", className, methodName, formatReturn(result), durationMicros));
            return result;
        } catch (Exception e) {
            long durationMicros = (System.nanoTime() - start) / 1_000;
            log.severe(String.format("✖ %s.%s threw %s [%d µs]", className, methodName, e.getClass().getName(), durationMicros));
            throw e;
        }
    }

    private static String formatParams(Object[] params) {
        if (params == null || params.length == 0) {
            return "";
        }
        return Arrays.stream(params).map(LoggerInterceptor::safeToString).collect(joining(", "));
    }

    private static String formatReturn(Object result) {
        return safeToString(result);
    }

    private static String safeToString(Object o) {
        try {
            return String.valueOf(o);
        } catch (Throwable t) {
            return o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
        }
    }
}
