<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Medical Records</title>
    <link rel="stylesheet"
          href="style.css">
</head>
<body>

<h2>Medical Records</h2>

<div class="section">
    <table border="1" class="table">
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
</div>

<div class="section">
    <h3>Add / Update Medical Record</h3>

    <form method="post" action="MedicalRecordsServlet">

    Patient ID:<br>
    <input type="number" name="patientID" required><br><br>

    Doctor ID:<br>
    <input type="number" name="doctorID" required><br><br>

    Prescription ID:<br>
    <input type="number" name="prescriptionID" required><br><br>

    Visit Date:<br>
    <input type="date" name="visitDate" required><br><br>

    <button type="submit" name="mode" value="insert">Create</button>
    <button type="submit" name="mode" value="update">Update</button>

</form>

</body>
</html>
