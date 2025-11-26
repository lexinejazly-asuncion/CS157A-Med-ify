<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Medical Records</title>
</head>
<body>

<h2>Medical Records List</h2>
<table border="1">
    <thead>
    <tr>
        <th>Patient ID</th>
        <th>Doctor ID</th>
        <th>Prescription ID</th>
        <th>Visit Date</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="m" items="${medicalrecords}">
        <tr>
            <td>${m.patientID}</td>
            <td>${m.doctorID}</td>
            <td>${m.prescriptionID}</td>
            <td>${m.visitDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
