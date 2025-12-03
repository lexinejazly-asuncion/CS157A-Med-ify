<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Med-ify Dashboard</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f7fa;
            margin: 0;
            padding: 0;
        }

        .header {
            background: #0066cc;
            color: white;
            text-align: center;
            padding: 25px 0;
            font-size: 28px;
            letter-spacing: 1px;
            font-weight: bold;
        }

        .container {
            width: 80%;
            margin: 50px auto;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 30px;
        }

        .card {
            background: white;
            width: 260px;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }

        .card h3 {
            margin-bottom: 15px;
            font-size: 20px;
            color: #333;
        }

        .card a {
            display: inline-block;
            padding: 10px 18px;
            background: #0066cc;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-size: 15px;
        }

        .card a:hover {
            background: #004b99;
        }

        .footer {
            margin-top: 60px;
            text-align: center;
            color: #888;
            font-size: 14px;
            padding-bottom: 30px;
        }
    </style>
</head>

<body>

<div class="header">Med-ify Management System</div>

<div class="container">

    <div class="card">
        <h3>Patients</h3>
        <a href="PatientsServlet">Open</a>
    </div>

    <div class="card">
        <h3>Doctors</h3>
        <a href="DoctorsServlet">Open</a>
    </div>

    <div class="card">
        <h3>Appointments</h3>
        <a href="AppointmentsServlet">Open</a>
    </div>

    <div class="card">
        <h3>Prescriptions</h3>
        <a href="PrescriptionsServlet">Open</a>
    </div>

    <div class="card">
        <h3>Medical Records</h3>
        <a href="MedicalRecordsServlet">Open</a>
    </div>

</div>

<div class="footer">
    Med-ify © 2025 • Fall 2025 CS157A Term Project
    <br>
    Lexinejazly Asuncion • Arian Bahram • Ena Macahiya
</div>

</body>
</html>
