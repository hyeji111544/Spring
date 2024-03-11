package kr.co.ch06.mapper;

import kr.co.ch06.dto.User1DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface User1Mapper {
    void insertUser1(User1DTO user1DTO);
    User1DTO selectUser1(String uid);
    List<User1DTO> selectUser1s();
    void updateUser1(User1DTO user1DTO);
    void deleteUser1(String uid);
}
