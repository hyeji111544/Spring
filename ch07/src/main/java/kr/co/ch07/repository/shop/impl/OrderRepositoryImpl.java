package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.Order;
import kr.co.ch07.entity.shop.QOrder;
import kr.co.ch07.repository.shop.custom.OrderRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
//import static kr.co.ch07.entity.shop.QCustomer.customer;

/*
    - CustomerRepositoryCustom 구현 클래스
    - RepositoryCustom에서 접미사 Custom 대신 Impl 접미사 네이밍 규칙
    - 반드시 @Repository 선언
*/
@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // Q도메인 클래스(QueryDSL이 사용하는 엔티티) 선언
    QOrder qOrder = QOrder.order;

    @Override
    public List<Order> selectOrders() {
        // 'select * from customer' QueryDSL 문법
        return jpaQueryFactory.select(qOrder).from(qOrder).fetch();
    }

    @Override
    public Order selectOrder(String orderId) {
        // 'select * from customer where custId=?' QueryDSL 문법
        return jpaQueryFactory
                .selectFrom(qOrder)
                .where(qOrder.orderId.eq(Integer.valueOf(orderId)))
                .fetchOne();
    }
}