package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductKeywordDto {
    private Integer keyWordNo;
    private String keyWordname;
    private String keyWordText;
}
