package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipDto {
    private BigDecimal tipAmount;
}
