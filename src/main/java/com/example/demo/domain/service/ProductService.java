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

        Product product = optionalProduct.get();
        ProductDto dto = new ProductDto();
        dto.setProductcode(product.getProductcode());
        dto.setProducttype(product.getProducttype());
        dto.setProductname(product.getProductname());
        dto.setProducttime(product.getProducttime());
        dto.setProductcontext(product.getProductcontext());
        dto.setProductimagepaths(product.getProductimagepaths());

        return dto;
    }

    //상품 목록을 불러오는 서비스
    public List<ProductDto> getProductList(){

        List<Product> allProducts = productRepository.getByProductLists();

        List<ProductDto> returnList = new ArrayList<ProductDto>();
        ProductDto dto = null;

        //엔티티를 dto로 전환하는 작업
        for(int i=0;i<allProducts.size();i++){

            dto = new ProductDto();
            dto.setProductcode(allProducts.get(i).getProductcode());
            dto.setProducttype(allProducts.get(i).getProducttype());
            dto.setProductname(allProducts.get(i).getProductname());
            dto.setProducttime(allProducts.get(i).getProducttime());
            dto.setProductcontext(allProducts.get(i).getProductcontext());
            dto.setProducttype(allProducts.get(i).getProducttype());
            dto.setProductimagepaths(allProducts.get(i).getProductimagepaths());

            log.info("dto : "+dto);

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
        Product product = Product.builder()
                .productcode(null)
                .productname(dto.getProductname())
                .producttype(dto.getProducttype())
                .productcontext(dto.getProductcontext())
                .producttime(LocalDateTime.now())
                .productimagepaths(dto.getProductimagepaths())

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
