package com.cts.deliverymodule.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * A logging advice class that uses Aspect-Oriented Programming (AOP) to log the
 * entry and exit of methods within the application.
 * 
 * @author Jeswin Joseph J
 * @since 11 Mar 2025
 */

@Slf4j
@Aspect
@Component
public class LoggerAdvice {
	
	/**
     * A pointcut expression that matches the execution of all methods in any class
     * within the `com.cts.deliverymodule.controller` package.
     * 
     * This is where the logging advice will be applied.
     */
	@Pointcut(value = "execution(* com.cts.deliverymodule.controller.*.*(..))")
	public void pointCut() {
		
	}
	
	/**
     * Advice method that surrounds the execution of methods matched by the pointcut.
     * 
     * @param joinPoint the join point representing the method being intercepted
     * @return the result of the method execution
     * @throws Throwable any exception thrown by the intercepted method
     */
	@Around("pointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().toString();
		String methodName = joinPoint.getSignature().getName();
		log.info("{}::{}: Entry", className, methodName);
		Object obj = null;
		try {
			obj = joinPoint.proceed();
		} catch (Exception e) {
			log.error("{}::{}: {}", className, methodName, e.getMessage());
			throw e;
		}
		log.info("{}::{}: Exit", className, methodName);
		return obj;
	}
}
