package kr.co.ch07.repository.shop;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.JavaTemplates;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.dto.ProductAggDTO;
import kr.co.ch07.entity.shop.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class QueryDslTest {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QCustomer qCustomer = QCustomer.customer;
    QProduct qProduct = QProduct.product;
    QOrder qOrder = QOrder.order;

    public void test01(){
        List<Product> products = jpaQueryFactory.select(qProduct).from(qProduct).fetch();
        // = select * from product
        // 사실 이런건 jpa findAll 로 조회하기
    }

    //@Test
    public void test02(){
        //선택 조회
        List<Customer> customers =
                jpaQueryFactory.
                        select(
                                Projections.fields(
                                        Customer.class,
                                        qCustomer.custId,
                                        qCustomer.name,
                                        qCustomer.age
                                )
                        )
                        .from(qCustomer).fetch();
        //= select custId, name, age from customer
        log.info(customers.toString());
    }

    //@Test
    public void test03(){
        //조건 조회
        List<Customer> customers1 =jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.name.eq("김유신")).fetch();
        // = select * from customer where name = '김유신'
        List<Customer> customers2 =jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.name.ne("김유신")).fetch();
        // = select * from customer where name != '김유신'

        log.info(customers1.toString());
        log.info(customers2.toString());
    }

    //@Test
    public void test04(){
        List<Customer> result1 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.gt(30)).fetch();
        List<Customer> result2 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.goe(30)).fetch();
        // where age >= 30
        List<Customer> result3 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.lt(30)).fetch();
        List<Customer> result4 = jpaQueryFactory.selectFrom(qCustomer).where(qCustomer.age.loe(30)).fetch();
        //where age <= 30
        log.info("result1 :"+result1.toString());
    }

    //@Test
    public void test05(){
        // select * fome customer where age in (21, 31, 41)
        List<Customer> customers = jpaQueryFactory.selectFrom(qCustomer)
                .where(qCustomer.age.in(21, 31, 41))
                .fetch();

        log.info("test05 : " + customers);
    }

    //@Test
    public void test06(){
        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(qCustomer.name.like("%신"))
                .fetch();

        log.info("test06 : " + customers);
    }

    //@Test
    public void test07(){
        //select ~ where stock > 0 order by price desc;
        List<Product> products= jpaQueryFactory.selectFrom(qProduct)
                                .where(qProduct.stock.gt(0))
                                .orderBy(qProduct.price.desc())
                                .fetch();

        log.info("test07 : " + products);

    }

    //@Test
    public void test08(){
        // select ~ where stock > 0 order by price asc limit 0, 3
        List<Product> products = jpaQueryFactory.selectFrom(qProduct)
                                .where(qProduct.stock.gt(0))
                                .orderBy(qProduct.price.asc())
                                .offset(0)
                                .limit(3)
                                .fetch();
        log.info("test08 : " + products);
    }

    @Test
    public void test09(){
        // select sum(`price`) as `priceSum`, avg(`price`) as `priceAvg`...
        ProductAggDTO productAggDTO = jpaQueryFactory.select(
                                        Projections.fields(
                                                //클래스 지정을 통해 dto 에 저장
                                                ProductAggDTO.class,
                                                qProduct.price.sum().as("priceSum"),
                                                qProduct.price.avg().as("priceAvg"),
                                                qProduct.price.max().as("priceMax"),
                                                qProduct.price.min().as("priceMin")
                                        )
                                    )
                                    .from(qProduct)
                                    .fetchOne();
        log.info("test09 : " + productAggDTO);
    }

    //@Test
    public void test10(){
        // select ~ where orderStatis = 1 group by `orderer` having cusId > = 2;
        List<Order> orders = jpaQueryFactory
                                .selectFrom(qOrder)
                                .where(qOrder.orderStatus.eq(1))
                                .groupBy(qOrder.customer.custId)
                                .having(qOrder.customer.custId.count().goe(2))
                                .fetch();

        log.info("test10 : " + orders);
    }

    @Test
    public void test11(){
        List<Tuple> orders= jpaQueryFactory
                .select(qOrder, qCustomer)
                .from(qOrder)
                .join(qCustomer)
                .on(qOrder.customer.eq(qCustomer))
                .fetch();

        log.info("test11 : " + orders);
    }

    @Test
    public void test12(){

        String name = "김유신";
        Integer age = 21;

        BooleanBuilder builder = new BooleanBuilder();
        // 동적 쿼리생성 builder
        if (name != null){
            builder.and(qCustomer.name.eq(name));
        }
        if (age != null){
            builder.and(qCustomer.age.eq(age));
        }

        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(builder)
                .fetch();

        log.info("test12 : " + customers.toString());

    }

    @Test
    public void test13(){
        String keyword = "유신";

        // 동적쿼리 생성을 위한 BooleanExpression
        BooleanExpression expression = qCustomer
                .name.containsIgnoreCase(keyword)
                .or(qCustomer.addr.containsIgnoreCase(keyword));

        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(expression)
                .fetch();

        log.info("test13 : " + customers);
    }


}