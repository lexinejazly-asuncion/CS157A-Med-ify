<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Med-ify Dashboard</title>
    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="header">Med-ify Management System</div>

<div class="role-selector">
    <h3>Select Your Role</h3>

    <select id="roleDropdown" name="userRole">
        <option value="" disabled selected>Select Your Role</option>
        <option value="Doctor">Doctor</option>
        <option value="Receptionist">Receptionist</option>
        <option value="Pharmacist">Pharmacist</option>
    </select>

    <button onclick="redirectToDashboard()">Go</button>
</div>

<script>
    function redirectToDashboard() {
        const dropdown = document.getElementById('roleDropdown');
        const selectedRole = dropdown.value;

        let targetPage = '';

        switch(selectedRole) {
            case 'Doctor':
                targetPage = 'DoctorDashboard.jsp';
                break;
            case 'Receptionist':
                targetPage = 'ReceptionistDashboard.jsp';
                break;
            case 'Pharmacist':
                targetPage = 'PharmacistDashboard.jsp';
                break;
            default:
                alert('Please select a role.');
                return;
        }

        if (targetPage) {
            window.location.href = targetPage;
        }
    }
</script>


<div class="footer">
    Med-ify © 2025 • Fall 2025 CS157A Term Project
    <br>
    Lexinejazly Asuncion • Arian Bahram • Ena Macahiya
</div>

</body>
</html>
