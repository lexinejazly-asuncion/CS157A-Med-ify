<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Appointments</title>
    <link rel="stylesheet"
          href="style.css">
</head>
<body>

<h2>Appointments</h2>

<div class="section">
    <table border="1" class="table">
        <thead>
        <tr>
            <th>Appointment ID</th>
            <th>Patient ID</th>
            <th>Doctor ID</th>
            <th>Appt Time</th>
            <th>Status</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="a" items="${appointments}">
            <tr>
                <td>${a.appointmentID}</td>
                <td>${a.patientID}</td>
                <td>${a.doctorID}</td>
                <td>${a.apptTime}</td>
                <td>${a.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</div>

</body>
</html>
