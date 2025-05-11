package com.cts.orderservice.advice;

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
 * Logs include the class name, method name, and error messages (if any).
 * 
 * @author Bhimisetty Renu Sai Ritvik
 * @since 11 Mar 2025
 */
@Slf4j
@Aspect
@Component
public class LoggerAdvice {
	
	@Pointcut(value = "execution(* com.cts.orderservice.controller.*.*(..))")
	public void pointCut() {
		
	}
	
	@Around("pointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().toString();
		String methodName = joinPoint.getSignature().getName();
		log.info("{}::{}: Entry", className, methodName);
		Object obj = joinPoint.proceed();
		log.info("{}::{}: Exit", className, methodName);
		return obj;
	}
}
