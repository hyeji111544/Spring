package kr.co.ch06.mapper;

import kr.co.ch06.dto.User5DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface User5Mapper {
    void insertUser5(User5DTO user5DTO);
    User5DTO selectUser5(String uid);
    List<User5DTO> selectUser5s();
    void updateUser5(User5DTO user5DTO);
    void deleteUser5(String uid);
}
