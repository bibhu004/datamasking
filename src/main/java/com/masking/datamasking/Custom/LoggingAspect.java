package com.masking.datamasking.Custom;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.masking.datamasking.Dto.TransferRequestDto;
import com.masking.datamasking.Service.TransferService;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // @Autowired
    // private TransferService transferService;

    // private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    
    @Before("execution(* com.masking.datamasking.Controller.*.*(..))")
    public void logBefore(){
        log.info("\nBefore Method execution.\n");
    }

    @Before("execution(* com.masking.datamasking.Controller.*.authenticateAndGetToken(..))")
    public void logLoginBefore(){
        log.info("\nBefore login Method execution.\n");
    }

    @After("execution(* com.masking.datamasking.Controller.*.*(..))")
    public void logAfter(){
        System.out.println("\n Method executed.\n");
    }

    @Before("@annotation(Loggable)")
    public void logMethod(JoinPoint joinPoint) {
        System.out.println("My custom logging annotation");
    }

    @Before("execution(* com.masking.datamasking.Service.TransferService.transferMoney(..))")
    public void logTransferService(){
        log.info("inside transfer");
    }


    @Around("execution(* com.masking.datamasking.Service.TransferService.transferMoney(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before executing transferMoney method...");
        // System.out.println("Before executing transferMoney method...");
        // Log the input arguments (TransferRequestDto in this case)
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof TransferRequestDto) {
            TransferRequestDto dto = (TransferRequestDto) args[0];
            boolean isAdmin = false;
            String maskedFromAccount = MaskingUtils.maskSensitiveData(dto.getFromAccount(), isAdmin);
            String maskedToAccount = MaskingUtils.maskSensitiveData(dto.getToAccount(), isAdmin);
            String maskedAmount = MaskingUtils.maskSensitiveData(String.valueOf(dto.getAmount()), isAdmin);

            log.info("Input Details: ");
            log.info("From Account: {}", maskedFromAccount);
            log.info("To Account: {}", maskedToAccount);
            log.error("Amount: ${}", maskedAmount);
        }

        // Proceed with the method execution
        Object result = joinPoint.proceed();

        // Log the result
        log.info("After executing transferMoney method...");
        log.info("Result: {}", result);

        return result;
    }

   
}