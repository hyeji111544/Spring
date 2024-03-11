package kr.co.ch06.mapper;

import kr.co.ch06.dto.User2DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface User2Mapper {
    void insertUser2(User2DTO user2DTO);
    User2DTO selectUser2(String uid);
    List<User2DTO> selectUser2s();
    void updateUser2(User2DTO user2DTO);
    void deleteUser2(String uid);
}
