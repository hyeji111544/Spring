package kr.co.ch07.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductAggDTO {
    // 집계 결과 받는 DTO
    private int priceSum;
    private double priceAvg;
    private int priceMax;
    private int priceMin;
}
