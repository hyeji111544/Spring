package kr.co.ch04.DAO;

import kr.co.ch04.DTO.User5DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class User5DAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertUser5(User5DTO user5DTO){
        String sql = "INSERT INTO `user5` VALUES(?,?,?,?)";
        Object[] params = {
                user5DTO.getName(),
                user5DTO.getGender(),
                user5DTO.getAge(),
                user5DTO.getAddr()
        };
        jdbcTemplate.update(sql, params);
    }

    public User5DTO selectUser5(String seq){
        String sql = "SELECT * FROM `user5` WHERE `seq`=?";
        return null;
    }

    public List<User5DTO> selectUser5s(){
        String sql = "SELECT * FROM `user5`";
        return null;
    }

    public void updateUser5(User5DTO user5DTO){
        String sql = "UPDATE `user5` SET `name`=?, `gender`=?, `age`=?, `addr`=? WHERE `seq`=?";
        Object[] params = {
                user5DTO.getName(),
                user5DTO.getGender(),
                user5DTO.getAge(),
                user5DTO.getAddr(),
                user5DTO.getSeq()
        };

        jdbcTemplate.update(sql, params);
    }

    public void deleteUser5(String seq){
        String sql = "DELETE FROM `user5` WHERE `seq`=?";
        jdbcTemplate.update(sql,seq);
    }

}
