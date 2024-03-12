package kr.co.ch07.repository;

import kr.co.ch07.dto.User1DTO;
import kr.co.ch07.entity.User1;
import kr.co.ch07.repository.User1Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class User1RepositoryTest {

    /*
    *   JUnit 테스트에서는 @Autowired 주입
    * 생성자 주입은 No ParameterResolver registered for .. 에러 발생
    */
    @Autowired
    private User1Repository repository;

    //@Test
    @DisplayName("user1등록")
    public void insertUser1(){

        // given
        User1 user1 = User1.builder()
                        .uid("j101")
                        .name("김유신")
                        .birth("1990-03-12")
                        .hp("010-1234-1111")
                        .age(21).build();
        
        // when : entity 저장
        repository.save(user1);
        
        
        // then (생략)
    }

    //@Test
    @DisplayName("user1조회")
    public void seleteUser1(){

        // given - 조회아이디
        String uid = "j101";
        // when - 조회하기
        Optional<User1> result = repository.findById(uid);
        User1 user1 = result.get();

        log.info(toString());
    }

    @DisplayName("user1목록")
    public List<User1DTO> seleteUser1s(){
        return null;
    }

    @DisplayName("user1수정")
    public void updateUser1(){

    }

    @DisplayName("user1삭제")
    public void deleteUser1(){

    }



    // 사용자 정의 쿼리 메서드
    //@Test
    public void findUser1ByUid(){
        User1 user1 = repository.findUser1ByUid("j101");
        log.warn(user1.toString());
    }
    //@Test
    public void findUser1ByName(){
        List<User1> user1s = repository.findUser1ByName("김유신");
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByNameNot(){
        List<User1> user1s = repository.findUser1ByNameNot("김유신");
        log.warn(user1s.toString());
    }


    //@Test
    public void findUser1ByUidAndName(){
        User1 user1 = repository.findUser1ByUidAndName("j101", "김유신");
        log.warn(user1.toString());
    }
    //@Test
    public void findUser1ByUidOrName(){
        List<User1> user1s = repository.findUser1ByUidOrName("j101", "김유신");
        log.warn(user1s.toString());
    }

    //@Test
    public void findUser1ByAgeGreaterThan(){
        List<User1> user1s = repository.findUser1ByAgeGreaterThan(23);
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByAgeGreaterThanEqual(){
        List<User1> user1s = repository.findUser1ByAgeGreaterThanEqual(23);
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByAgeLessThan(){
        List<User1> user1s = repository.findUser1ByAgeLessThan(23);
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByAgeLessThanEqual(){
        List<User1> user1s = repository.findUser1ByAgeLessThanEqual(32);
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByAgeBetween(){
        List<User1> user1s = repository.findUser1ByAgeBetween(21, 32);
        log.warn(user1s.toString());
    }

    //@Test
    public void findUser1ByNameLike(){
        List<User1> user1s = repository.findUser1ByNameLike("김유신");
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByNameContains(){
        List<User1> user1s = repository.findUser1ByNameContains("김유신");
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByNameStartsWith(){
        List<User1> user1s = repository.findUser1ByNameStartsWith("김");
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByNameEndsWith(){
        List<User1> user1s = repository.findUser1ByNameEndsWith("신");
        log.warn(user1s.toString());

    }
    //@Test
    public void findUser1ByOrderByName(){
        List<User1> user1s = repository.findUser1ByOrderByName();
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByOrderByAgeAsc(){
        List<User1> user1s = repository.findUser1ByOrderByAgeAsc();
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByOrderByAgeDesc(){
        List<User1> user1s = repository.findUser1ByOrderByAgeDesc();
        log.warn(user1s.toString());
    }
    //@Test
    public void findUser1ByAgeGreaterThanOrderByAgeDesc(){
        List<User1> user1s = repository.findUser1ByAgeGreaterThanOrderByAgeDesc(21);
        log.warn(user1s.toString());
    }

    //@Test
    public void countUser1ByUid(){
        int user1 = repository.countUser1ByUid("j101");
        log.warn(String.valueOf(user1));
    }
    //@Test
    public void countUser1ByName(){
        int result = repository.countUser1ByName("김유신");
        log.warn("countUser1ByName : " + result);
    }


    // JPQL 정의
    //@Test
    public void selectUser1UnderAge30(){
        List<User1> user1s = repository.selectUser1UnderAge30();
        log.warn(user1s.toString());
    }

    //@Test
    public void selectUser1ByName(){
        List<Object[]> results = repository.selectUser1ByName("김유신");
        for(Object[] result : results) {
            // 이 부분에서 Object[] 배열에서 User1 엔티티를 추출하여 처리
            User1 user1 = (User1) result[0];
            log.warn(user1.toString());
        }
    }

    @Test
    public void selectUser1ByNameParam(){
        List<Object[]> user1s = repository.selectUser1ByNameParam("김유신");
        for(Object[] user1 : user1s){
            User1 user = (User1) user1[0]; // User1 객체로 형변환
            log.warn("User: {}", user.toString());
        }
    }

    //@Test
    public void selectUser1ByUid(){
        List<Object[]> user1s = repository.selectUser1ByUid("j101");
        user1s.forEach(user -> {
            String uid = (String) user[0];
            String name = (String) user[1];
            int age = (int) user[2];
            log.warn("User: uid={}, name={}, age={}", uid, name, age);
        });
    }

}