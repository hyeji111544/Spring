package kr.co.ch05.mapper;

import kr.co.ch05.dto.User4DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface User4Mapper {
    void insertUser4(User4DTO user4DTO);
    User4DTO selectUser4(String uid);
    List<User4DTO> selectUser4s();
    void updateUser4(User4DTO user4DTO);
    void deleteUser4(String uid);
}
