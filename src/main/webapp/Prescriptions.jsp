<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Prescriptions</title>
</head>
<body>

<h2>Prescriptions</h2>

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

    <div>
        <label for="prescriptionStatus">Status:</label>
        <select id="prescriptionStatus" name="prescriptionStatus" required>
            <option value="">Select Status</option>
            <option value="Processing">Processing</option>
            <option value="Filled">Filled</option>
            <option value="Completed">Completed</option>
        </select>
    </div>

    <input type="submit" value="Add Prescription Record">
</form>
</body>
</html>