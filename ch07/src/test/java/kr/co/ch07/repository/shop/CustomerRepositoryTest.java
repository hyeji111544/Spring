package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("등록")
    public void insertCustomer(){

        Customer customer = Customer.builder()
                .custId("a1001")
                .name("김유신")
                .age(23)
                .hp("010-1234-1001")
                .addr("김해")
                .build();
        customerRepository.save(customer);

    }

    @Test
    @DisplayName("전체조회")
    public void selectCustomers(){
        List<Customer> customers = customerRepository.selectCustomers();
        log.info("selectCustomers : " + customers);
    }

    @Test
    @DisplayName("낱개조회")
    public void selectCustomer(){
        Customer customer = customerRepository.selectCustomer("a101");
        log.info("selectCustomer : " + customer);
    }

}