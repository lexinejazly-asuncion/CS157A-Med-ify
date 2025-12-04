<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Appointments</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>Appointment Management</h2>

<!-- SEARCH APPOINTMENT -->
<div class="section">
    <div style="display:flex; gap:50px;">
        <form method="get" action="AppointmentsServlet" class="inline-form">
            <input type="number" name="appointmentID" placeholder="Search by Appointment ID">
            <button type="submit">Search</button>
        </form>

        <form action="AppointmentsServlet" method="get">
            <button type="submit" style="margin-left:20px;">Reset View</button>
        </form>

    </div>

    <!-- MESSAGES -->
    <div>
        <!-- AppointmentID is found -->
        <c:if test="${not empty appointmentID and not idNotFound}">
            <p>Result for Appointment ID: <strong>${appointmentID}</strong></p>
        </c:if>

        <!-- No AppointmentID is found -->
        <c:if test="${idNotFound}">
            <p style="color:red; font-weight:bold;">
                No appointment found with ID ${appointmentID}
            </p>
        </c:if>
    </div>
</div>

<!-- APPOINTMENTS TABLE -->
<h2>Appointments List</h2>
<div class="section">
    <table border="1" class="table">
        <thead>
        <tr>
            <th>Appointment ID</th>
            <th>Patient ID</th>
            <th>Doctor ID</th>
            <th>Appt Time</th>
            <th>Status</th>
            <th>Actions</th> <!-- COLUMN FOR DELETE -->
        </tr>
        </thead>

        <tbody>
        <c:forEach var="a" items="${appointments}">
            <tr>
                <td>${a.appointmentID}</td>
                <td>${a.patientID}</td>
                <td>${a.doctorID}</td>
                <td>${a.apptTime}</td>
                <td>${a.status}</td>

                <!-- DELETE BUTTON -->
                <td>
                    <form method="post" action="AppointmentsServlet"
                          onsubmit="return confirm('Delete this appointment?');">

                        <input type="hidden" name="mode" value="delete">
                        <input type="hidden" name="appointmentID" value="${a.appointmentID}">

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

<!-- UPDATE APPOINTMENT -->
<div class="section">
    <h3>Update Appointment</h3>

    <form method="post" action="AppointmentsServlet">
        <input type="hidden" name="mode" value="update">

        <label>Appointment ID:</label><br>
        <input type="number" name="appointmentID" placeholder="e.g., 1" required>
        <br><br>

        <label>New Appointment Time:</label><br>
        <input type="datetime-local" name="apptTime" required>
        <br><br>

        <label>New Status:</label><br>
        <select name="status" required>
            <option value="">Select Status</option>
            <option value="Scheduled">Scheduled</option>
            <option value="Completed">Completed</option>
        </select>

        <br><br>
        <button type="submit">Update Appointment</button>
    </form>
</div>

<!-- ADD APPOINTMENT -->
<div class="section">
    <h3>Add New Appointment</h3>

    <form method="post" action="AppointmentsServlet">
        <input type="hidden" name="mode" value="insert">

        <label>Patient ID:</label><br>
        <input type="number" name="patientID" required>
        <br>

        <label>Doctor ID:</label><br>
        <input type="number" name="doctorID" required>
        <br>

        <label>Appt Time:</label><br>
        <input type="datetime-local" name="apptTime" required>
        <br>

        <label>Status:</label><br>
        <select name="status" required>
            <option value="Scheduled">Scheduled</option>
            <option value="Completed">Completed</option>
        </select>

        <br><br>
        <button type="submit">Add Appointment</button>
    </form>
</div>

</body>
</html>
