package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM bookdb.Product", nativeQuery = true)
    List<Product> getByProductLists();

}
