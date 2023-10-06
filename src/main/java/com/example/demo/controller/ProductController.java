package com.example.demo.controller;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //프로젝트내부에 이미지를 저장하기위한 임시
//    @Autowired
//    private ResourceLoader resourceLoader;


    @GetMapping("/index")
    public void product_index() {

    }

    @GetMapping("/set")
    public void product_set() {
        log.info("GET /product/set...");
    }

    @PostMapping("/set")
    public String product_post(ProductDto dto,@RequestParam("files")MultipartFile[] files) throws IOException {
        String path = "c:\\etc\\products"+"\\"+dto.getProducttype()+"\\"+UUID.randomUUID();

        //프로젝트내에 이미지파일을 저장하기위한 임시
//        Resource resource = resourceLoader.getResource("classpath:static/img/");

//        String path = resource.getFile().getAbsolutePath()+"\\"+dto.getProducttype()+"\\"+UUID.randomUUID();
//        System.out.println("path : "+path);

        //만약 폴더가 없으면 생성한다.
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();


        MultipartFile[] images = files;

        //DB 저장용 toString 으로 변환에 사용
        List<String> imglist = new ArrayList<>();
        String[] imgtoString;

        for(MultipartFile img : images){
            String imgName = img.getOriginalFilename();




            //파일을 실제 서버의 디렉토리에 저장
            File fileobj = new File(path,imgName);
            img.transferTo(fileobj);

            imglist.add(path+"\\"+imgName);

        }
        imgtoString = imglist.toArray(new String[0]);




        dto.setProductimagepaths(imgtoString);


        productService.setProduct(dto);

        return "redirect:/product/index";
    }

    @GetMapping("/keyword/set")
    public void product_keyword() {

    }

    @PostMapping("/keyword/set")
    public String product_keyword_post(ProductKeywordDto dto) {

        //null값 방지
        if (dto.getKeyWordname() != null && dto.getKeyWordText() != null && !dto.getKeyWordname().equals("") && !dto.getKeyWordText().equals("")) {
            log.info("POST /product/inkeyword..." + dto);
            dto.setKeyWordNo(null);
            productService.setKeyword(dto);
        } else {
            log.info("POST /product/inkeyword...no data");
            return "redirect:/product/keyword/set";
        }
        return "redirect:/product/keyword/set";

    }
}