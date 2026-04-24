<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Courses</title>
</head>
<body>

<h2>Available Courses</h2>

<a href="${pageContext.request.contextPath}/logout">Logout</a>

<c:forEach var="course" items="${courses}">
    <div style="margin-bottom:10px;">
        <b>${course.course_name}</b>

        <form action="${pageContext.request.contextPath}/register/${course.course_id}" method="post" style="display:inline;">
            <button type="submit">Register</button>
        </form>
    </div>
</c:forEach>

</body>
</html>