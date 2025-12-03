<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Patients</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }
        h2, h3 {
            margin-bottom: 10px;
        }
        .section {
            margin-bottom: 35px;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
        }
        input[type=text], input[type=number], input[type=date], select {
            width: 250px;
            padding: 6px;
            margin-top: 4px;
            margin-bottom: 15px;
        }
        button {
            padding: 6px 12px;
            cursor: pointer;
            margin-top: 5px;
        }
        .inline-form {
            display: inline-block;
            margin-right: 12px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px 12px;
        }
    </style>
</head>
<body>

<h2>Patient Management</h2>

<!-- RESET BUTTON -->
<form action="PatientsServlet" method="get">
    <button type="submit" style="margin-bottom:20px;">Reset View</button>
</form>

<hr>

<!-- SEARCH PATIENT -->
<div class="section">
    <div style="display:flex; gap:50px;">

        <!-- SEARCH BY ID -->
        <div>
            <h3>Search Patient by ID</h3>
            <form method="get" action="PatientsServlet" class="inline-form">
                <input type="number" name="patientID" placeholder="Enter Patient ID">
                <button type="submit">Search</button>
            </form>

            <c:if test="${idNotFound}">
                <p style="color:red; font-weight:bold;">No patient found with ID ${param.patientID}</p>
            </c:if>
        </div>

        <!-- SEARCH BY NAME -->
        <div>
            <h3>Search Patient by Name (case-insensitive)</h3>
            <form method="get" action="PatientsServlet" class="inline-form">
                <input type="text" name="searchName" placeholder="Enter name">
                <button type="submit">Search</button>
            </form>

            <c:if test="${nameSearch}">
                <p>Results for: <strong>${param.searchName}</strong></p>
            </c:if>

            <c:if test="${nameSearch and nameNotFound}">
                <p style="color:red;"><strong>No matching patients found.</strong></p>
            </c:if>
        </div>

    </div>
</div>

<!-- UPDATE PATIENT -->
<div class="section">
    <h3>Update Patient</h3>

    <form method="post" action="PatientsServlet">

        <input type="hidden" name="mode" value="update">

        <label>Patient ID:</label><br>
        <input type="number" name="patientID"
               placeholder="Enter ID to load patient"
               value="${patient.patientID}">
        <br>

        <label>Name:</label><br>
        <input type="text" name="patientName" value="${patient.patientName}">
        <br>

        <label>Date of Birth:</label><br>
        <input type="date" name="dob" value="${patient.DOB}">
        <br>

        <label>Gender:</label><br>
        <select name="gender">
            <option value="Male"   ${patient.gender == 'Male' ? 'selected' : ''}>Male</option>
            <option value="Female" ${patient.gender == 'Female' ? 'selected' : ''}>Female</option>
        </select>
        <br>

        <label>Address:</label><br>
        <input type="text" name="address" value="${patient.address}">
        <br>

        <button type="submit">Update Patient</button>
    </form>
</div>

<!-- ADD NEW PATIENT -->
<div class="section">
    <h3>Add New Patient</h3>

    <form method="post" action="PatientsServlet">

        <input type="hidden" name="mode" value="insert">

        <label>Name:</label><br>
        <input type="text" name="patientName" required>
        <br>

        <label>Date of Birth:</label><br>
        <input type="date" name="dob" required>
        <br>

        <label>Gender:</label><br>
        <select name="gender">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select>
        <br>

        <label>Address:</label><br>
        <input type="text" name="address" required>
        <br>

        <button type="submit">Add Patient</button>
    </form>
</div>

<!-- PATIENT TABLE -->
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
