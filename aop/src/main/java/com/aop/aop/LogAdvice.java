package com.aop.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {

	@Before( "execution(* com.aop.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("============================================");
	}

	  // arg를 이용해 파라미터 구할 수 있다.  
	  @Before("execution(* com.aop.service.SampleService*.doAdd(String,String)) && args(str1, str2)") 
	  public void logBeforeWithParam(String str1, String str2) {
	  
		  log.info("str1: " + str1); 
		  log.info("str2 : " + str2); 
	  }
	  
	  
	  // 지정된 대상이 예외를 발생한 후 동작	  
	  @AfterThrowing(pointcut =
	  "execution(* com.aop.service.SampleService*.*(..))", throwing="exception")
	  public void logException(Exception exception) {
	  
		  log.info("Exception....!!!!"); 
		  log.info("exception : " + exception); 
	  }
	  
	  // ProceedingJoinPoint는 @Around와 같이 결합해 파라미터나 예외 등 처리能
	  @Around("execution(* com.aop.service.SampleService*.*(..))") 
	  public Object logTime( ProceedingJoinPoint pjp) {
	  
		  long start = System.currentTimeMillis();
		  
		  log.info("Target : " + pjp.getTarget()); 
		  log.info("Param : " + Arrays.toString(pjp.getArgs()));
		  
		  //invoke method 
		  Object result = null;
		  
		  try { 
			  result = pjp.proceed(); 
		  } catch (Throwable e) { 
			  // TODO Auto-generated catch block 
			  e.printStackTrace(); 
		  }
		  
		  long end = System.currentTimeMillis();
		  
		  log.info("TIME : " + (end - start));
		  
		  return result;
	  
	  }
	 

}
