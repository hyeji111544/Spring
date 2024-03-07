
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>member::modify</title>
</head>
<body>
    <h4>member::modify</h4>
    <a href="/ch05">메인</a>
    <a href="/ch05/member/list">목록</a>
    <form action="/ch05/member/modify" method="post">
        <table border="1">
            <tr>
                <td>아이디</td>
                <td><input type="text" name="uid" value="${memberDTO.uid}"></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><input type="text" name="name" value="${memberDTO.name}"></td>
            </tr>
            <tr>
                <td>연락처</td>
                <td><input type="text" name="hp" value="${memberDTO.hp}"></td>
            </tr>
            <tr>
                <td>직급</td>
                <td>
                    <select name="pos" value="${memberDTO.pos}">
                        <option ${memberDTO.pos == '사원' ? 'selected' : ''}>사원</option>
                        <option ${memberDTO.pos == '대리' ? 'selected' : ''}>대리</option>
                        <option ${memberDTO.pos == '과장' ? 'selected' : ''}>과장</option>
                        <option ${memberDTO.pos == '차장' ? 'selected' : ''}>차장</option>
                        <option ${memberDTO.pos == '부장' ? 'selected' : ''}>부장</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>부서</td>
                <td>
                    <select name="dep">
                        <option value="101" ${memberDTO.dep == '101' ? 'selected' : ''}>영업1부</option>
                        <option value="102" ${memberDTO.dep == '102' ? 'selected' : ''}>영업2부</option>
                        <option value="103" ${memberDTO.dep == '103' ? 'selected' : ''}>영업3부</option>
                        <option value="104" ${memberDTO.dep == '104' ? 'selected' : ''}>인사부</option>
                        <option value="105" ${memberDTO.dep == '105' ? 'selected' : ''}>경영지원부</option>
                    </select>
            </tr>

            <tr>
                <td colspan="2" align="right"><input type="submit" value="등록"></td>
            </tr>
        </table>
    </form>
</body>
</html>
