<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Prescriptions</title>
</head>
<body>


<h2>Prescriptions</h2>

<%--not functional yet--%>
<form action="search_results.jsp" method="get">
    <input type="text" name="query" placeholder="Enter Patient Name">
    <input type="submit" value="Search">
</form>

<table border="1">
    <thead>
    <tr>
        <th>Prescription ID</th>
        <th>Prescription Date</th>
        <th>Prescription Name</th>
        <th>Dose</th>
        <th>Quantity</th>
        <th>Refills</th>
        <th>Patient Name</th>
        <th>Doctor Name</th>
        <th>Prescription Status</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="p" items="${prescriptions}">
        <tr>
            <td>${p.prescriptionID}</td>
            <td>${p.prescriptionDate}</td>
            <td>${p.prescriptionName}</td>
            <td>${p.dose}</td>
            <td>${p.quantity}</td>
            <td>${p.refills}</td>
            <td>${p.patientName}</td>
            <td>${p.doctorName}</td>
            <td>${p.prescriptionStatus}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>
