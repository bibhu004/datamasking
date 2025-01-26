package com.masking.datamasking.Custom;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.masking.datamasking.Dto.TransferRequestDto;
import com.masking.datamasking.Service.TransferService;


@Aspect
@Component
public class LoggingAspect {

    // @Autowired
    // private TransferService transferService;

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    
    @Before("execution(* com.masking.datamasking.Controller.*.*(..))")
    public void logBefore(){
        logger.info("\nBefore Method execution.\n");
    }

    @Before("execution(* com.masking.datamasking.Controller.*.authenticateAndGetToken(..))")
    public void logLoginBefore(){
        logger.info("\nBefore login Method execution.\n");
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
        logger.info("inside transfer");
    }


    @Around("execution(* com.masking.datamasking.Service.TransferService.transferMoney(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before executing transferMoney method...");
        // System.out.println("Before executing transferMoney method...");
        // Log the input arguments (TransferRequestDto in this case)
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof TransferRequestDto) {
            TransferRequestDto dto = (TransferRequestDto) args[0];
            logger.info("Input Details: ");
            logger.warn("From Account: {}", dto.getFromAccount());
            logger.debug("To Account: {}", dto.getToAccount());
            logger.error("Amount: ${}", dto.getAmount());
        }

        // Proceed with the method execution
        Object result = joinPoint.proceed();

        // Log the result
        logger.info("After executing transferMoney method...");
        logger.info("Result: {}", result);

        return result;
    }

    // @Around("execution(* com.masking.datamasking.Service.TransferService.*(..)) && args(fromAccount, toAccount, amount)")
    // public Object logAndValidateTransfer(ProceedingJoinPoint joinPoint, String fromAccount, String toAccount, double amount) throws Throwable {
    //     System.out.println("Before method: Validating transfer...");
        
    //     // Enforce a minimum transfer amount
    //     if (amount < 100) {
    //         System.out.println("Transfer amount too low. Adjusting to $100.");
    //         amount = 100;
    //     }

    //     double currentBalance = transferService.getAccountBalance(fromAccount);
    //     if (currentBalance < amount) {
    //         throw new IllegalArgumentException("Insufficient balance in " + fromAccount + ". Current balance: $" + currentBalance);
    //     }
    //     // Log method details
    //     System.out.println("Transferring from: " + fromAccount);
    //     System.out.println("Transferring to: " + toAccount);
    //     System.out.println("Amount: $" + amount);

    //     // Proceed with the original method
    //     Object result = joinPoint.proceed(new Object[]{fromAccount, toAccount, amount});
        
    //     System.out.println("After method: Transfer successful.");
    //     return result;
    // }
}