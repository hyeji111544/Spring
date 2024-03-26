package kr.co.ch07.repository.shop.custom;

import kr.co.ch07.entity.shop.Order;
import kr.co.ch07.entity.shop.Product;

import java.util.List;

/*
    - QueryDsl로 수행하기 위한 CustomerRepository 확장 인터페이스
    - Repository + Custom 접미사로 네이밍 규칙
    - QueryDsl은 insert, update, delete 지원 안함
    - 기본 CustomerRepository extends 상속
*/
public interface ProductRepositoryCustom {

    public List<Product> selectProduct();
    public Product selectProduct(String productId);

}