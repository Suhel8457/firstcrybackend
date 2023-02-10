//package com.galaxe.logging;
//
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//@Aspect
//@Order(0)
//public class LogAspect {
//
//	public LogAspect() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Around(value = "com.galaxe.logging.AppPointCuts.mainPointCut()")
//	public Object calulateMethodTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
//		final Logger classLogger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
//		if (classLogger.isDebugEnabled()) {
//			return joinPoint.proceed();
//		}
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
//		String methodArgs = Stream.of(joinPoint.getArgs()).collect(Collectors.toList()).toString();
//		long startTime = System.nanoTime();
//		Object result = joinPoint.proceed()
//		long endTime = System.nanoTime();
//		long elapsedTime = endTime - startTime;
//		LoggerMessage message = LoggerMessage.builder().className(className).methodName(methodName)
//				.methodArgs(methodArgs).result(result).elapsedTimeInMills(TimeUnit.NANOSECONDS.toMillis(elapsedTime))
//				.elapsedTimeInMicros(TimeUnit.NANOSECONDS.toMicros(elapsedTime)).build();
//		classLogger.info("LogAspect:{}", message);
//		return result;
//
//	}
//
//}
