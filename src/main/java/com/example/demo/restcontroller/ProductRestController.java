package com.example.demo.restcontroller;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.repository.ProductKeywordRepository;
import com.example.demo.domain.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;


    //상품 목록 페이지
    @GetMapping("/list")
    public List<ProductDto> product_list() throws IOException {
        //상품목록 을 불러온다.
        List<ProductDto> listdto = productService.getProductList();
        //키워드 목록을 불러온다.
        List<ProductKeywordDto> dto = productService.getKeywordList();

        //상품 하나하나에 키워드를 적용
        for(int i=0;i<listdto.size();i++){

            List<String> explain = new ArrayList<>();
            String[] explaintoString;
            List<String> keywords = new ArrayList<>();
            String[] keywordstoString;

            //해당하는 키워드가 있는지 검색
            for(int j=0;j<dto.size();j++){

                //해당 제목과 동일한 키워드가 있는지 확인
                boolean iskey = listdto.get(i).getProductname().contains(dto.get(j).getKeyWordname());

                if(iskey){

                    explain.add(dto.get(j).getKeyWordText());
                    keywords.add(dto.get(j).getKeyWordname());

                }else{



                }

            }


            //리스트를 String 배열로 변경
            explaintoString = explain.toArray(new String[0]);
            keywordstoString = keywords.toArray(new String[0]);

            //리스트에 등록
            listdto.get(i).setProductkeywords(keywordstoString);
            listdto.get(i).setProductexplains(explaintoString);

            log.info(listdto.get(i).getProductname() +" 해당 키워드 : "+Arrays.toString(listdto.get(i).getProductkeywords())+ " 내용 : " + Arrays.toString(listdto.get(i).getProductexplains()));
        }

        //base64 인코딩
        List<String> base64encodelist = new ArrayList<>();
        String[] base64encodeimg;

        for(int i=0;i<listdto.size();i++){

            if(!(listdto.get(i).getProductimagepaths() ==null)){


                for(String img : listdto.get(i).getProductimagepaths()){
                    log.info("이미지 감지 : "+img);

                    Path imgpath = Paths.get(img);
                    byte[] imageData = Files.readAllBytes(imgpath);

                    String base64encode = Base64.getEncoder().encodeToString(imageData);

                    base64encodelist.add("<img src='data:image/jpeg;base64," + base64encode + "'>");
                    log.info("base64 인코딩 : "+base64encodelist);

                }
                base64encodeimg = base64encodelist.toArray(new String[0]);
                listdto.get(i).setProductimagepaths(base64encodeimg);


            }

        }





        return listdto;
    }

    @GetMapping("/keyword/list")
    public List<ProductKeywordDto> getKeyword_List(){
        List<ProductKeywordDto> list = productService.getKeywordList();

        return list;
    }

    @DeleteMapping("/keyword/delete/{no}")
    public void product_keyword_delete(@PathVariable int no){
        log.info("DELETE /product/keyword/delete..."+no);

        productService.deleteKeyword(no);
    }


}
