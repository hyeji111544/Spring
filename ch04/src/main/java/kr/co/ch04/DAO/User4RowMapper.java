package kr.co.ch04.DAO;

import kr.co.ch04.DTO.User4DTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User4RowMapper implements RowMapper<User4DTO> {

    @Override
    public User4DTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        User4DTO user4DTO = new User4DTO();
        user4DTO.setUid(rs.getString(1));
        user4DTO.setName(rs.getString(2));
        user4DTO.setGender(rs.getString(3));
        user4DTO.setAge(rs.getInt(4));
        user4DTO.setHp(rs.getString(5));
        user4DTO.setAddr(rs.getString(6));

        return user4DTO;
    }
}
