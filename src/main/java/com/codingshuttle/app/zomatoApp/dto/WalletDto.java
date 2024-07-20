package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
    private UserDto user;

    private List<PaymentDto> payments;

    private Double balance;
}
