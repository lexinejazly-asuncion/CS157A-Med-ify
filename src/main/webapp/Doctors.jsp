<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Doctors</title>

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
        input[type=text], input[type=number] {
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

<h2>Doctor Management</h2>

<!-- RESET BUTTON -->
<form action="DoctorsServlet" method="get">
    <button type="submit" style="margin-bottom:20px;">Reset View</button>
</form>

<hr>

<!-- SEARCH DOCTOR -->
<div class="section">
    <div style="display:flex; gap:50px;">

        <!-- SEARCH BY ID -->
        <div>
            <h3>Search Doctor by ID</h3>
            <form method="get" action="DoctorsServlet">
                <input type="number" name="doctorID" placeholder="Enter Doctor ID">
                <button type="submit">Search</button>
            </form>

            <c:if test="${idNotFound}">
                <p style="color:red; font-weight:bold;">No doctor found with ID ${param.doctorID}</p>
            </c:if>
        </div>

        <!-- SEARCH BY NAME -->
        <div>
            <h3>Search Doctor by Name</h3>
            <form method="get" action="DoctorsServlet">
                <input type="text" name="searchName" placeholder="Enter name">
                <button type="submit">Search</button>
            </form>

            <c:if test="${nameSearch}">
                <p>Results for: <strong>${param.searchName}</strong></p>
            </c:if>

            <c:if test="${nameSearch && nameNotFound}">
                <p style="color:red;"><strong>No matching doctors found.</strong></p>
            </c:if>
        </div>
    </div>
</div>

<!-- UPDATE DOCTOR -->
<div class="section">
    <h3>Update Doctor</h3>

    <form method="post" action="DoctorsServlet">
        <input type="hidden" name="mode" value="update">

        Doctor ID:<br>
        <input type="number" name="doctorID" value="${doctor.doctorID}">
        <br>

        Name:<br>
        <input type="text" name="doctorName" value="${doctor.doctorName}">
        <br>

        Phone Number:<br>
        <input type="text" name="phoneNumber" value="${doctor.phoneNumber}">
        <br>

        Department:<br>
        <input type="text" name="department" value="${doctor.department}">
        <br>

        <button type="submit">Update Doctor</button>
    </form>
</div>

<!-- ADD DOCTOR -->
<div class="section">
    <h3>Add New Doctor</h3>

    <form method="post" action="DoctorsServlet">
        <input type="hidden" name="mode" value="insert">

        Name:<br>
        <input type="text" name="doctorName" required>
        <br>

        Phone Number:<br>
        <input type="text" name="phoneNumber" required>
        <br>

        Department:<br>
        <input type="text" name="department" required>
        <br>

        <button type="submit">Add Doctor</button>
    </form>
</div>

<!-- DOCTORS TABLE -->
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
