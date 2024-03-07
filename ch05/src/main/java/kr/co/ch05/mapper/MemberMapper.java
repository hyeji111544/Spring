package kr.co.ch05.mapper;

import kr.co.ch05.dto.MemberDTO;
import kr.co.ch05.dto.ParentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    void insertMember(MemberDTO memberDTO);
    MemberDTO selectMember(String uid);
    List<MemberDTO> selectMembers();
    List<MemberDTO> selectMembersForSearch(@Param("type") String type,
                                           @Param("value") String value,
                                           @Param("pos") String[] pos);

    List<ParentDTO> selectParentWithChild();

    void updateMember(MemberDTO memberDTO);
    void deleteMember(String uid);

}
