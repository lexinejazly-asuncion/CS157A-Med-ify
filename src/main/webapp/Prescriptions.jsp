<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Prescriptions</title>

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

<h2>Prescriptions</h2>


<!-- RESET BUTTON -->
<form action="PrescriptionsServlet" method="get">
    <button type="submit" style="margin-bottom:20px;">Reset View</button>
</form>

<hr>

<!-- SEARCH PRESCRIPTION -->
<div class="section">
    <div style="display:flex; gap:50px;">

        <!-- SEARCH BY ID -->
        <div>
            <h3>Search Prescription by ID</h3>
            <form method="get" action="PrescriptionsServlet" class="inline-form">
                <input type="number" name="prescriptionID" placeholder="Enter Prescription ID">
                <button type="submit">Search</button>
            </form>

            <c:if test="${idNotFound}">
                <p style="color:red; font-weight:bold;">No prescription found with ID ${prescriptionID}</p>
            </c:if>
        </div>

        <!-- SEARCH BY NAME -->
        <div>
            <h3>Search Prescription by Patient Name</h3>
            <form method="get" action="PrescriptionsServlet" class="inline-form">
                <input type="text" name="patientName" placeholder="Enter Patient Name">
                <button type="submit">Search</button>
            </form>

            <c:if test="${nameSearch}">
                <p>Results for: <strong>${searchName}</strong></p>
            </c:if>

            <c:if test="${nameSearch and nameNotFound}">
                <p style="color:red;"><strong>No matching patients found.</strong></p>
            </c:if>
        </div>

    </div>
</div>

<!-- PRESCRIPTION TABLE -->
<div>
    <table border="1">
        <thead>
        <tr>
            <th>Prescription ID</th>
            <th>Prescription Date</th>
            <th>Patient ID </th>
            <th>Patient Name</th>
            <th>Prescription Name</th>
            <th>Dose</th>
            <th>Quantity</th>
            <th>Refills</th>
            <th>Prescription Status</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="p" items="${prescriptions}">
            <tr>
                <td>${p.prescriptionID}</td>
                <td>${p.prescriptionDate}</td>
                <td>${p.patientID}</td>
                <td>${p.patientName}</td>
                <td>${p.prescriptionName}</td>
                <td>${p.dose}</td>
                <td>${p.quantity}</td>
                <td>${p.refills}</td>
                <td>${p.prescriptionStatus}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- UPDATE PRESCRIPTION -->
<div class="section">
    <h3>Update Prescription Status</h3>

    <form action="PrescriptionsServlet" method="post">
        <input type="hidden" name="mode" value="update">

        <div>
            <label for="prescriptionID">Prescription ID:</label>
            <input type="number" id="prescriptionID" name="prescriptionID" required placeholder="e.g., 1">
        </div>

        <div>
            <label for="newStatus">New Status:</label>
            <select id="newStatus" name="newStatus" required>
                <option value="">Select Status</option>
                <option value="Processing">Processing</option>
                <option value="Filled">Filled</option>
                <option value="Completed">Completed</option>
            </select>
        </div>

        <input type="submit" value="Update Status">
    </form>

    <c:if test="${updateIDNotFound}">
        <p style="color:red; font-weight:bold;">Insert Failed: No prescription record found with ID ${updatePrescriptionID}</p>
    </c:if>

</div>

<!-- INSERT NEW PRESCRIPTION -->
<div class="section">
    <h3>Add New Prescription Record</h3>

    <form action="PrescriptionsServlet" method="post">
        <input type="hidden" name="mode" value="insert">

        <div>
            <label for="prescriptionDate">Prescription Date:</label>
            <input type="date" id="prescriptionDate" name="prescriptionDate" required>
        </div>

        <div>
            <label for="patientID">Patient ID:</label>
            <input type="number" id="patientID" name="patientID" required min="1" placeholder="e.g., 5">
        </div>

        <div>
            <label for="prescriptionName">Medication Name:</label>
            <input type="text" id="prescriptionName" name="prescriptionName" required placeholder="e.g., Amoxicillin">
        </div>

        <div>
            <label for="dose">Dose:</label>
            <input type="text" id="dose" name="dose" required placeholder="e.g., 500mg">
        </div>

        <div>
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" required min="1" placeholder="e.g., 30">
        </div>

        <div>
            <label for="refills">Refills:</label>
            <input type="number" id="refills" name="refills" min="0" value="0">
        </div>


        <input type="submit" value="Add Prescription Record">
    </form>

    <c:if test="${updatePatientIDNotFound}">
        <p style="color:red; font-weight:bold;">Update Failed: No patient record found with ID ${updatePatientID}</p>
    </c:if>
</div>
</body>
</html>