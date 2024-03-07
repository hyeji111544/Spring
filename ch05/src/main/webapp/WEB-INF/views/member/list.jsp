<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>memberList</title>
</head>
<body>
    <h4>memberList</h4>
    <a href="/ch05">메인</a>
    <a href="/ch05/member/register">등록</a>


    <form action="/ch05/member/search">
        <select name="type">
            <option value="uid">아이디</option>
            <option value="name">이름</option>
            <option value="hp">휴대폰</option>
        </select>
        <input type="text" name="value">
        <p>
            <label><input type="checkbox" name="pos" value="사원">사원</label>
            <label><input type="checkbox" name="pos" value="대리">대리</label>
            <label><input type="checkbox" name="pos" value="과장">과장</label>
            <label><input type="checkbox" name="pos" value="차장">차장</label>
            <label><input type="checkbox" name="pos" value="부장">부장</label>
        </p>
        <input type="submit" value="검색">
    </form>
    <table border="1">
        <tr>
            <td>아이디</td>
            <td>이름</td>
            <td>연락처</td>
            <td>직급</td>
            <td>부서</td>
            <td>입사일</td>
            <td>관리</td>
        </tr>
        <c:forEach var="member" items="${members}">
            <tr>
                <td>${member.uid}</td>
                <td>${member.name}</td>
                <td>${member.hp}</td>
                <td>${member.pos}</td>
                <td>${member.dep}</td>
                <td>${fn:substring(member.rdate,0,10)}</td>
                <td>
                    <a href="/ch05/member/modify?uid=${member.uid}">수정</a>
                    <a href="/ch05/member/delete?uid=${member.uid}">삭제</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
