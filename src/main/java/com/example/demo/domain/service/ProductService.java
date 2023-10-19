package com.example.demo.domain.service;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductKeyword;
import com.example.demo.domain.repository.ProductKeywordRepository;
import com.example.demo.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductKeywordRepository productKeywordRepository;

    public ProductDto getProductOne(Long no) {
        Optional<Product> optionalProduct = productRepository.findById(no);

        //entity => dto
        Product product = optionalProduct.get();
        ProductDto dto = new ProductDto();
        dto.setProductcode(product.getProductcode());
        dto.setProducttype(product.getProducttype());
        dto.setProductname(product.getProductname());
        dto.setProducttime(product.getProducttime());
        dto.setProductcontext(product.getProductcontext());

        //경로와 이미지이름을 합쳐서 dto에 저장
        List<String> imagePaths = new ArrayList<String>();
        String[] imagePathstoString = null;
        String[] entityimageNames = product.getProductimages();

        for(int i=0; i<entityimageNames.length;i++){

            imagePaths.add(product.getProductpath()+entityimageNames[i]);

        }
        imagePathstoString = imagePaths.toArray(new String[0]);
        dto.setProductimagepaths(imagePathstoString);

        return dto;
    }

    //상품 목록을 불러오는 서비스
    public List<ProductDto> getProductList(){

        List<Product> allProducts = productRepository.getByProductLists();

        List<ProductDto> returnList = new ArrayList<ProductDto>();
        ProductDto dto = null;

        //entity => dto
        for(int i=0;i<allProducts.size();i++){

            dto = new ProductDto();
            dto.setProductcode(allProducts.get(i).getProductcode());
            dto.setProducttype(allProducts.get(i).getProducttype());
            dto.setProductname(allProducts.get(i).getProductname());
            dto.setProducttime(allProducts.get(i).getProducttime());
            dto.setProductcontext(allProducts.get(i).getProductcontext());
            dto.setProducttype(allProducts.get(i).getProducttype());

            //경로와 이미지이름을 합쳐서 dto에 저장
            List<String> imagePaths = new ArrayList<String>();
            String[] imagePathstoString = null;
            String[] entityimageNames = allProducts.get(i).getProductimages();

            for(int j=0; j<entityimageNames.length;j++){

                imagePaths.add(allProducts.get(i).getProductpath()+entityimageNames[j]);

            }
            imagePathstoString = imagePaths.toArray(new String[0]);
            dto.setProductimagepaths(imagePathstoString);



            returnList.add(dto);
        }

        return returnList;
    }



    //키워드를 등록하는 서비스
    public void setKeyword(ProductKeywordDto dto) {
        ProductKeyword productKeyword = new ProductKeyword();

        productKeyword.setKeyWordNo(null);
        productKeyword.setKeyWordname(dto.getKeyWordname());
        productKeyword.setKeyWordText(dto.getKeyWordText());

        productKeywordRepository.save(productKeyword);
    }

    //상품을 등록하는 서비스
    public void setProduct(ProductDto dto) {
        //dto => entity
        Product product = Product.builder()
                .productcode(null)
                .productname(dto.getProductname())
                .producttype(dto.getProducttype())
                .productcontext(dto.getProductcontext())
                .producttime(LocalDateTime.now())
                .productpath(dto.getProductpath())
                .productimages(dto.getProductimages())

                .build();

        System.out.println("dto : "+dto);
        productRepository.save(product);
    }

    //키워드 설명 검색용 키워드를 불러오는 서비스
    public List<ProductKeywordDto> getKeywordList() {
        List<ProductKeyword> productKeyword = productKeywordRepository.getByProductKeywordLists();

        List<ProductKeywordDto> returnList = new ArrayList<ProductKeywordDto>();
        ProductKeywordDto dto = null;

        for(int i=0;i<productKeyword.size();i++){
            //entity => dto
            dto = new ProductKeywordDto();
            dto.setKeyWordNo(productKeyword.get(i).getKeyWordNo());
            dto.setKeyWordname(productKeyword.get(i).getKeyWordname());
            dto.setKeyWordText(productKeyword.get(i).getKeyWordText());

            returnList.add(dto);
        }
        return returnList;
    }

    public void deleteKeyword(int no) {

        productKeywordRepository.deleteById(no);

    }

    public void deleteProduct(Long no) {

        productRepository.deleteById(no);
    }
}
