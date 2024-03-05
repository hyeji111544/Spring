package kr.co.ch04.service;

import kr.co.ch04.DAO.User5DAO;
import kr.co.ch04.DTO.User5DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User5Service {

    @Autowired
    private User5DAO dao;
    public void insertUser5(User5DTO user5DTO){
        dao.insertUser5(user5DTO);
    }

    public User5DTO selectUser5(String seq){
        return dao.selectUser5(seq);
    }

    public List<User5DTO> selectUser5s(){
        return dao.selectUser5s();
    }

    public void updateUser5(User5DTO user5DTO){
        dao.updateUser5(user5DTO);
    }

    public void deleteUser5(String seq){
        dao.deleteUser5(seq);
    }
}
