<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>user5::list</title>
</head>
<body>
<h3>user5 목록</h3>

<a href="/ch04">메인</a>
<a href="/ch04/user5/list">목록</a>

<table border="1">
    <tr>
        <td>이름</td>
        <td>성별</td>
        <td>나이</td>
        <td>주소</td>
        <td>관리</td>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.seq}</td>
            <td>${user.name}</td>
            <td>${user.gender}</td>
            <td>${user.age}</td>
            <td>${user.addr}</td>
            <td>
                <a href="/ch04/user5/modify?uid=${user.uid}">수정</a>
                <a href="/ch04/user5/delete?uid=${user.uid}">삭제</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
