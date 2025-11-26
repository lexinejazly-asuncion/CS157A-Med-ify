<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Doctors</title>
</head>
<body>

<h2>Doctors List</h2>
<table border="1">
    <thead>
    <tr>
        <th>Doctor ID</th>
        <th>Name</th>
        <th>Phone Number</th>
        <th>Department</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="doctor" items="${doctors}">
        <tr>
            <td>${doctor.doctorID}</td>
            <td>${doctor.doctorName}</td>
            <td>${doctor.phoneNumber}</td>
            <td>${doctor.department}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
