package com.masking.datamasking.Dto;

import lombok.Data;

@Data
public class TransferRequestDto {
    
    private String fromAccount;
    private String toAccount;
    private double amount;
}
