<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Prescriptions</title>
    <link rel="stylesheet"
          href="style.css">

</head>
<body>

<h2>Prescriptions</h2>

<!-- FILTER/SEARCH PRESCRIPTION -->
<div class="section">
    <div style="display:flex; gap:50px;">
        <!-- FILTER BY Prescription Status -->
        <form method="get" action="PrescriptionsServlet" class="inline-form">
            <select id="status" name="status" onchange="this.form.submit()" required>
                <option value=""disabled selected>Filter by Prescription Status</option>
                <option value="Processing">Processing</option>
                <option value="Filled">Filled</option>
                <option value="Completed">Completed</option>
            </select>
        </form>

        <!-- SEARCH BY ID -->
        <form method="get" action="PrescriptionsServlet" class="inline-form">
            <input type="number" name="prescriptionID" placeholder="Search by Prescription ID">
            <button type="submit" class="button">Search</button>
        </form>


        <!-- SEARCH BY NAME -->
        <form method="get" action="PrescriptionsServlet" class="inline-form">
            <input type="text" name="patientName" placeholder="Search by Patient Name">
            <button type="submit" class="button">Search</button>
        </form>

        <form action="PrescriptionsServlet" method="get">
            <button type="submit" class="button">Reset View</button>
        </form>

    </div>

    <!-- MESSAGES -->
    <div>
        <!-- PrescriptionID is found -->
        <c:if test="${not empty prescriptionID and not idNotFound}">
            <p>Result for Prescription ID: <strong>${prescriptionID}</strong></p>
        </c:if>

        <!-- PrescriptionID is not found -->
        <c:if test="${idNotFound}">
            <p style="color:red; font-weight:bold;">No prescription found with ID ${prescriptionID}</p>
        </c:if>

        <!-- PatientName is found -->
        <c:if test="${nameSearch and not nameNotFound}">
            <p>Results for Patient Name: <strong>${searchName}</strong></p>
        </c:if>

        <!-- PatientName is not found -->
        <c:if test="${nameSearch and nameNotFound}">
            <p style="color:red;"><strong>No matching patients found.</strong></p>
        </c:if>

        <!-- Fail to insert -->
        <c:if test="${insertRecordPatientIDNotFound}">
            <p style="color:red; font-weight:bold;">Insert Failed: No patient record found with ID ${insertRecordPatientID}</p>
        </c:if>

        <!-- Fail to update -->
        <c:if test="${updateIDNotFound}">
            <p style="color:red; font-weight:bold;">Update Failed: No prescription record found with ID ${updatePrescriptionID}</p>
        </c:if>


    </div>
</div>

<!-- PRESCRIPTION TABLE -->
<div class="section">
    <table border="1" class="table">
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
            <th>Actions</th> <!-- COLUMN FOR DELETE -->
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

                <td>
                    <form method="post" action="PrescriptionsServlet"
                          onsubmit="return confirm('Delete this prescription?');">

                        <input type="hidden" name="mode" value="delete">
                        <input type="hidden" name="prescriptionID" value="${p.prescriptionID}">

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

        <input type="submit" class="button" value="Update Status">
    </form>

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


        <input type="submit" class="button" value="Add Prescription Record">
    </form>
</div>

</body>
</html>