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
      <td>${patient.dob}</td>
      <td>${patient.gender}</td>
      <td>${patient.address}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<h3>Add New Patient</h3>
<form action="PatientsServlet" method="post">
  <input type="hidden" name="action" value="insert"> <!-- important -->
  <table>
    <tr>
      <td>Name:</td>
      <td><input type="text" name="name" required></td>
    </tr>
    <tr>
      <td>DOB:</td>
      <td><input type="date" name="dob" required></td>
    </tr>
    <tr>
      <td>Gender:</td>
      <td>
        <select name="gender" required>
          <option value="">--Select--</option>
          <option value="Male">Male</option>
          <option value="Female">Female</option>
          <option value="Other">Other</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>Address:</td>
      <td><input type="text" name="address" required></td>
    </tr>
    <tr>
      <td colspan="2">
        <button type="submit">Add Patient</button>
      </td>
    </tr>
  </table>
</form>

</body>
</html>
