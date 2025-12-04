<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Patients</title>
    <link rel="stylesheet"
          href="style.css">
</head>
<body>

<h2>Patient Management</h2>

<!-- SEARCH PATIENT -->
<div class="section">
    <div style="display:flex; gap:50px;">

        <!-- SEARCH BY ID -->
        <div>
            <form method="get" action="PatientsServlet" class="inline-form">
                <input type="number" name="patientID" placeholder="Search by Patient ID">
                <button type="submit">Search</button>
            </form>

        </div>

        <!-- SEARCH BY NAME -->
        <div>
            <form method="get" action="PatientsServlet" class="inline-form">
                <input type="text" name="searchName" placeholder="Search by Patient Name">
                <button type="submit">Search</button>
            </form>
        </div>

        <!-- RESET BUTTON -->
        <form action="PatientsServlet" method="get">
            <button type="submit" style="margin-bottom:20px;">Reset View</button>
        </form>
    </div>

    <!-- MESSAGES -->
    <div>
        <c:if test="${idNotFound}">
            <p style="color:red; font-weight:bold;">No patient found with ID ${param.patientID}</p>
        </c:if>

        <c:if test="${nameSearch}">
            <p>Results for: <strong>${param.searchName}</strong></p>
        </c:if>

        <c:if test="${nameSearch and nameNotFound}">
            <p style="color:red;"><strong>No matching patients found.</strong></p>
        </c:if>
    </div>
</div>

<!-- PATIENT TABLE -->
<h2>Patients List</h2>
<div class="section">
    <table border="1" class="table">
        <thead>
        <tr>
            <th>Patient ID</th>
            <th>Name</th>
            <th>DOB</th>
            <th>Gender</th>
            <th>Address</th>
            <th>Actions</th> <!-- NEW COLUMN -->
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

                <!-- DELETE BUTTON -->
                <td>
                    <form method="post" action="PatientsServlet"
                          onsubmit="return confirm('Are you sure you want to delete this patient?');">

                        <input type="hidden" name="mode" value="delete">
                        <input type="hidden" name="patientID" value="${patient.patientID}">

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

</body>
</html>
