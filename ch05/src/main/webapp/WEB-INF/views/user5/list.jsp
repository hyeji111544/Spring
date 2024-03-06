<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>user5::list</title>
<script>
    window.onload = function() {
        var table = document.getElementById("userTable");
        var rows = table.getElementsByTagName("tr");
        for (var i = 1; i < rows.length; i++) {
            var rowNumberCell = rows[i].querySelector(".rowNumber");
            rowNumberCell.textContent = i; // 순차적인 번호를 추가합니다.
            var seqCell = rows[i].querySelector(".seqValue");
            seqCell.style.display = "none"; // seq 값을 숨깁니다.
        }
    };
</script>
</head>
<body>
<h3>user5 목록</h3>

<a href="/ch05">메인</a>
<a href="/ch05/user5/list">목록</a>

<table border="1" id="userTable">
    <tr>
        <td>번호</td>
        <td style="display: none;"></td>
        <td>이름</td>
        <td>성별</td>
        <td>나이</td>
        <td>주소</td>
        <td>관리</td>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td class="rowNumber"></td>
            <td class="seqValue">${user.seq}</td>
            <td>${user.name}</td>
            <td>${user.gender}</td>
            <td>${user.age}</td>
            <td>${user.addr}</td>
            <td>
                <a href="/ch05/user5/modify?seq=${user.seq}">수정</a>
                <a href="/ch05/user5/delete?seq=${user.seq}">삭제</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
