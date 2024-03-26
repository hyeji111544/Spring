package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.OrderItem;
import kr.co.ch07.entity.shop.QOrderItem;
import kr.co.ch07.repository.shop.custom.OrderItemRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
//import static kr.co.ch07.entity.shop.QOrderItem.OrderItem;

/*
    - OrderItemRepositoryCustom 구현 클래스
    - RepositoryCustom에서 접미사 Custom 대신 Impl 접미사 네이밍 규칙
    - 반드시 @Repository 선언
*/
@RequiredArgsConstructor
@Repository
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // Q도메인 클래스(QueryDSL이 사용하는 엔티티) 선언
    QOrderItem qOrderItem = QOrderItem.orderItem;

    @Override
    public List<OrderItem> selectOrderItems() {
        List<OrderItem> orderItems= jpaQueryFactory.select(qOrderItem).from(qOrderItem).fetch();
        // =select * from OrderItem

        return orderItems;
    }

    @Override
    public OrderItem selectOrderItem(int orderItemId) {
        // 'select * from OrderItem where custId=?' QueryDSL 문법
        return jpaQueryFactory
                .selectFrom(qOrderItem)
                .where(qOrderItem.orderItemId.eq(orderItemId))
                .fetchOne();
    }
}