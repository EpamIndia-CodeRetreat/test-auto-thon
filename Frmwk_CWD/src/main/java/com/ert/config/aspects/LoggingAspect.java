package com.ert.config.aspects;

import com.ert.libs.webActions.ElementActions;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class LoggingAspect {

	@Autowired
	private ElementActions elementActions;

	private Logger logger;

	public LoggingAspect() {
		logger = LogManager.getLogger("ApplicationLogs");
	}

	/**
	 * Run beforeLoggingAdviceForPages before the method execution for all methods
	 * in Page Class
	 * 
	 * @param joinPoint
	 */
	@Before("allPageMethods()")
	public void beforeLoggingAdviceForPages(JoinPoint joinPoint) {
		logger.log(Level.INFO, joinPoint.getSignature().toShortString() + " method called");
	}

	/**
	 * Run afterLoggingAdviceForPages after the method execution for all methods in
	 * Page Class
	 * 
	 * @param joinPoint
	 * @param passed
	 */
	@AfterReturning(pointcut = "allPageMethods()", returning = "passed")
	public void afterLoggingAdviceForPages(JoinPoint joinPoint, String passed) {
		if (passed.equals("")) {
			logger.log(Level.INFO, joinPoint.getSignature().toShortString() + " method call passed");
		} else {
			logger.log(Level.ERROR, joinPoint.getSignature().toShortString() + " method call failed");
		}
	}

	/**
	 * Run afterLoggingAdviceForPages after the method execution for all methods
	 * return boolean in Page Class
	 * 
	 * @param joinPoint
	 * @param passed
	 */
	@AfterReturning(pointcut = "allPageMethods()", returning = "passed")
	public void afterLoggingAdviceForPages(JoinPoint joinPoint, boolean passed) {
		if (passed) {
			logger.log(Level.INFO, joinPoint.getSignature().toShortString() + " method call passed");
		} else {
			logger.log(Level.ERROR, joinPoint.getSignature().toShortString() + " method call failed");
		}
	}

	/**
	 * Run afterLoggingAdviceForPagesWhenExceptionThrown after the Exception is
	 * thrown for all methods in Page Class
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "allPageMethods()", throwing = "e")
	public void afterLoggingAdviceForPagesWhenExceptionThrown(JoinPoint joinPoint, Exception e) {
		logger.log(Level.ERROR, joinPoint.getSignature().toShortString() + " action call failed. Exception caught."
				+ e.toString().split("\n")[0]);
	}

	/**
	 * Run beforeLoggingAdviceForElementActions before the method execution for all
	 * methods in ElementActions Class
	 * 
	 * @param joinPoint
	 * @param param
	 */
	@Before("allElementActions() && args(param,..)")
	public void beforeLoggingAdviceForElementActions(JoinPoint joinPoint, Object param) {

		if (param.getClass().equals(WebElement.class)) {
			WebElement element = (WebElement) param;
			logger.log(Level.INFO, joinPoint.getSignature().toShortString() + " action called on element with "
					+ elementActions.getLocator(element));
		} else {
			logger.log(Level.INFO,
					joinPoint.getSignature().toShortString() + " action called with " + String.valueOf(param));
		}
	}

	/**
	 * Run afterLoggingAdviceForElementActions after the method execution for all
	 * methods in ElementActions Class
	 * 
	 * @param joinPoint
	 * @param passed
	 */
	@AfterReturning(pointcut = "allElementActions()", returning = "passed")
	public void afterLoggingAdviceForElementActions(JoinPoint joinPoint, boolean passed) {
		if (passed) {
			logger.log(Level.INFO, joinPoint.getSignature().toShortString() + " action call passed");
		} else {
			logger.log(Level.ERROR, joinPoint.getSignature().toShortString() + " action call failed");
		}
	}

	/**
	 * Run afterLoggingAdviceForElementActionsWhenExceptionThrown after the
	 * Exception is thrown for all methods in ElementActions Class
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "allElementActions()", throwing = "e")
	public void afterLoggingAdviceForElementActionsWhenExceptionThrown(JoinPoint joinPoint, WebDriverException e) {
		String[] exceptionLines = e.getMessage().split("\n");
		String logException;

		if (exceptionLines.length > 1) {
			logException = exceptionLines[0] + "\n" + exceptionLines[1];
		} else {
			logException = exceptionLines[0];
		}
		logger.log(Level.ERROR,
				joinPoint.getSignature().toShortString() + " action call failed. Exception caught. " + logException);
	}

	/**
	 * Run allPageMethods for all methods in Pages Package
	 */
	@Pointcut("execution(* com.ert.libs.pages.*.*(..))")
	public void allPageMethods() {
		/* Pointcut for all methods in Pages package */
	}

	/**
	 * Run allElementActions for all methods return boolean in ElementActions Class
	 */
	@Pointcut("execution(boolean com.ert.libs.webActions.ElementActions.*(..))")
	public void allElementActions() {
		/* Pointcut for all actions return boolean in ElementActions */
	}

}
