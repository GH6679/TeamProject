package com.example.demo.domain.entity;

import com.example.demo.domain.entity.converters.ProductStringArrayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue
    private Long productcode;
    private String producttype;
    private String productname;
    private LocalDateTime producttime;
    private String productcontext;

    private String productpath;
    @Convert(converter = ProductStringArrayConverter.class)
    private String[] productimages;


}