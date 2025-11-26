<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Prescriptions</title>
</head>
<body>

<h2>Prescriptions List</h2>
<table border="1">
    <thead>
    <tr>
        <th>Prescription ID</th>
        <th>Appointment ID</th>
        <th>Prescription Date</th>
        <th>Issued By Doctor ID</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="p" items="${prescriptions}">
        <tr>
            <td>${p.prescriptionID}</td>
            <td>${p.appointmentID}</td>
            <td>${p.prescriptionDate}</td>
            <td>${p.issuedByDoctorID}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
