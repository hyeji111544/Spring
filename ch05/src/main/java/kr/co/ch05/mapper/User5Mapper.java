package kr.co.ch05.mapper;

import kr.co.ch05.dto.User5DTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface User5Mapper {
    void insertUser5(User5DTO user5DTO);
    User5DTO selectUser5(int seq);
    List<User5DTO> selectUser5s();
    void updateUser5(User5DTO user5DTO);
    void deleteUser5(int seq);
}
