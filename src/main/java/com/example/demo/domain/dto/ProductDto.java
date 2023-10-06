package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long productcode;
    private String producttype;
    private String productname;
    private LocalDateTime producttime;

    private String productcontext;
    private String[] productexplains;
    private String[] productkeywords;
    private String[] productimagepaths;
}

   