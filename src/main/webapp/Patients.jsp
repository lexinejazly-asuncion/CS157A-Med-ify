<%--
  Created by IntelliJ IDEA.
  User: lexinejazly
  Date: 11/24/25
  Time: 10:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Patients</title>
</head>
<body>

<hr>

<h3>Add New Patient</h3>

<form method="post" action="PatientsServlet">

    Name:<br>
    <input type="text" name="patientName" required><br><br>

    Date of Birth:<br>
    <input type="date" name="dob" required><br><br>

    Gender:<br>
    <select name="gender" required>
        <option value="Male">Male</option>
        <option value="Female">Female</option>
    </select><br><br>

    Address:<br>
    <input type="text" name="address" required><br><br>

    <button type="submit">Add Patient</button>

</form>


<h2>Patients List</h2>
<table border="1">
  <thead>
  <tr>
    <th>Patient ID</th>
    <th>Name</th>
    <th>DOB</th>
    <th>Gender</th>
    <th>Address</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="patient" items="${patients}">
    <tr>
      <td>${patient.patientID}</td>
      <td>${patient.patientName}</td>
      <td>${patient.DOB}</td>
      <td>${patient.gender}</td>
      <td>${patient.address}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
