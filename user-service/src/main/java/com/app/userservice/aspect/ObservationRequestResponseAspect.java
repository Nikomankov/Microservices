package com.app.userservice.aspect;

import brave.Span;
import brave.Tracer;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * This class provide aspect which logging every methode in each controller and also set
 * necessary MDC labels
 */
@Aspect
@Component
@AllArgsConstructor
public class ObservationRequestResponseAspect {
	private final Tracer braveTracer;
	private static final Logger logger = LoggerFactory.getLogger("REST");

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restControllerMethods() {
		// Pointcut for RestController methods
	}

	@Around("restControllerMethods()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		// Get data which write as metadata for log and trace
		long start = System.currentTimeMillis();
		String methodName = new StringBuilder()
			.append(abbreviationPath(joinPoint.getSignature().getDeclaringTypeName()))
			.append(".")
			.append(joinPoint.getSignature().getName()).append("()").toString();

		//Create span for method
		Span newSpan = braveTracer.nextSpan().name(methodName);

		// Add labels to MDC
		MDC.put("methodName", methodName);

		Object proceed;

		try
			(Tracer.SpanInScope ws = braveTracer.withSpanInScope(newSpan.start()))
		{
			proceed = joinPoint.proceed();
		} catch (Throwable throwable) {
			logger.error("Exception, cause = '{}'", throwable.getCause() != null ? throwable.getCause() : "NULL");
			newSpan.error(throwable);
			throw throwable; // Rethrow the exception after logging it
		} finally {
			long executionTime = System.currentTimeMillis() - start;
			logger.info("Execution time {} ms", executionTime);
			newSpan.finish();
			// Clear MDC
			MDC.clear();
		}

		return proceed;
	}

	/**
	 * Convert long path like "directory.some_directory.another_directory.AwesomeClass"
	 * to short path like "d.s.a.AwesomeClass"
	 * @param className long path to methode
	 * @return short path
	 */
	private String abbreviationPath(String className){
		String[] pathArray = className.split("\\.");
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i< pathArray.length-1; i++){
			builder.append(pathArray[i].charAt(0)).append(".");
		}
		builder.append(pathArray[pathArray.length-1]);
		return builder.toString();
	}
}