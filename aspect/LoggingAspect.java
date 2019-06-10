package com.rabin.secdemo.aspect;


import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	//setup a logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	//aspect is java class collection of related advices
	//start with @Before advice on pointcut expression
	@Before("execution(public void addAccount())")
	public void beforeAddAccountAdvice() {
		System.out.println("Executing @before advice before addAccount()");
	}
	
	@Before("execution(public String showFormForAdd())")
	public void beforeShowFormForAddAdvice() {
		System.out.println("Executing @Before advice before showFormForAdd()");
	}
	
	@Before("execution(public String listCustomers(..))")
	public void beforeListCustomersAdvice() {
		System.out.println("Executing @Before advice before listCustomer()");
	}
	//setup point cut declaration for controllers
	@Pointcut("execution (* com.rabin.crmdemo.controller.*.*(..))")
	private void forControllerPackages() {
		
	}
	//setup pointcut declaration for dao
	@Pointcut("execution (* com.rabin.crmdemo.dao.*.*(..))")
	private void forDAOPackages() {
		
	}
	//setup pointcut for services
	@Pointcut("execution (* com.rabin.crmdemo.service.*.*(..))")
	private void forServicePackages() {
		
	}
	@Pointcut("forControllerPackages() || forServicePackages() || forDAOPackages()")
	private void forAppFlow() {
		
	}
	
	//add @before advices
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===>>> in @Before: calling method: " +theMethod);
		//System.out.println("===>>> in @Before: calling method: " +theMethod);
		//display args to the method
		//get the args
		
		Object[] args = theJoinPoint.getArgs();
		
		//loop through and display args
		for (Object tempArgs : args) {
			myLogger.info("====>> argument: "+tempArgs);
		}
		
		
		
		
	}
	//add @afterReturning advice
	@AfterReturning(
					pointcut="forAppFlow()", 
					returning="theResult"
					)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		//display method we are returning
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===>>> in @AfterReturning: from method: " +theMethod);
		// display data returned
		myLogger.info("====>> result: " +theResult);
		
	}
}
