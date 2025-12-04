<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Doctors</title>
    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<h2>Doctor Management</h2>

<!-- SEARCH DOCTOR -->
<div class="section">
    <div style="display:flex; gap:50px;">
        <!-- SEARCH BY ID -->
        <div>
            <form method="get" action="DoctorsServlet">
                <input type="number" name="doctorID" placeholder="Search by Doctor ID">
                <button type="submit" class="button">Search</button>
            </form>

        </div>

        <!-- SEARCH BY NAME -->
        <div>
            <form method="get" action="DoctorsServlet">
                <input type="text" name="searchName" placeholder="Search by Doctor Name">
                <button type="submit" class="button">Search</button>
            </form>

        </div>

        <!-- RESET BUTTON -->
        <form action="DoctorsServlet" method="get">
            <button type="submit" class="button" >Reset View</button>
        </form>
    </div>

    <!-- MESSAGES -->
    <div>
        <c:if test="${idNotFound}">
            <p style="color:red; font-weight:bold;">No doctor found with ID ${param.doctorID}</p>
        </c:if>

        <c:if test="${nameSearch}">
            <p>Results for: <strong>${param.searchName}</strong></p>
        </c:if>

        <c:if test="${nameSearch && nameNotFound}">
            <p style="color:red;"><strong>No matching doctors found.</strong></p>
        </c:if>

    </div>
</div>

<!-- DOCTORS TABLE -->
<h2>Doctors List</h2>
<div class="section">
    <table border="1" class="table">
        <thead>
        <tr>
            <th>Doctor ID</th>
            <th>Name</th>
            <th>Phone Number</th>
            <th>Department</th>
            <th>Actions</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="doctor" items="${doctors}">
            <tr>
                <td>${doctor.doctorID}</td>
                <td>${doctor.doctorName}</td>
                <td>${doctor.phoneNumber}</td>
                <td>${doctor.department}</td>

                <td>
                    <form method="post" action="DoctorsServlet"
                          onsubmit="return confirm('Are you sure you want to delete this doctor?');">

                        <input type="hidden" name="mode" value="delete">
                        <input type="hidden" name="doctorID" value="${doctor.doctorID}">

                        <button type="submit"
                                style="background:red; color:white; padding:5px 10px;">
                            Delete
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
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

        <button type="submit" class="button">Update Doctor</button>
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

        <button type="submit" class="button">Add Doctor</button>
    </form>
</div>

</body>
</html>
