package com.masking.datamasking.Service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.masking.datamasking.Dto.TransferRequestDto;

@Service
public class TransferService {

    public HashMap<String, Double> account = new HashMap<>();

    public TransferService(){
        
        account.put("123abc", 40.00);
        account.put("123def", 2400.00);
    }

    public double getAccountBalance(String acc) {
        return account.getOrDefault(acc, 0.0);
    }

     public String transferMoney(TransferRequestDto transferRequestDto) {
        String fromAccount = transferRequestDto.getFromAccount();
        String toAccount = transferRequestDto.getToAccount();
        double amount = transferRequestDto.getAmount();

        // Example logic
        double currentBalance = getAccountBalance(fromAccount);
        if (currentBalance < amount) {
            throw new IllegalArgumentException(
                "Insufficient balance in account " + fromAccount + ". Current balance: $" + currentBalance
            );
        }

        // Perform transfer logic
        updateBalances(fromAccount, toAccount, amount);
        return "Transferred $" + amount + " from " + fromAccount + " to " + toAccount;
    }

    private void updateBalances(String fromAccount, String toAccount, double amount) {
        // Mocked balance update logic
    }
}
