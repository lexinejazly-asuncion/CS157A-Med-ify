import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useState } from "react";

import Layout from "./layout/Layout";
import LoginPage from "./pages/LoginPage";

import Dashboard from "./pages/Dashboard";
import Patients from "./pages/Patients";
import Appointments from "./pages/Appointments";
import MedicalRecords from "./pages/MedicalRecords";
import Prescriptions from "./pages/Prescriptions";

import AddPatient from "./pages/AddPatient";
import AddMedicalRecord from "./pages/AddMedicalRecord";

import AddPrescription from "./pages/AddPrescription";
import UpdatePrescription from "./pages/UpdatePrescription.js";

import AddAppointment from "./pages/AddAppointment";


export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userInfo, setUserInfo] = useState({
    name: "User",
    role: "Receptionist",
  });

  return (
    <BrowserRouter>
      <Routes>

        {/* LOGIN */}
        <Route
          path="/login"
          element={
            isLoggedIn ? (
              <Navigate to="/dashboard" />
            ) : (
              <LoginPage onLogin={() => setIsLoggedIn(true)} />
            )
          }
        />

        {/* PROTECTED LAYOUT */}
        <Route
          path="/"
          element={
            isLoggedIn ? (
              <Layout userInfo={userInfo} onLogout={() => setIsLoggedIn(false)} />
            ) : (
              <Navigate to="/login" />
            )
          }
        >

          {/* ROUTES */}
          <Route path="dashboard" element={<Dashboard />} />

          <Route path="patients" element={<Patients />} />
          <Route path="patients/add" element={<AddPatient />} />

          <Route path="appointments" element={<Appointments />} />
          <Route path="appointments/add" element={<AddAppointment />} />


          <Route path="medical-records" element={<MedicalRecords />} />
          <Route path="medical-records/add" element={<AddMedicalRecord />} />
          <Route path="medical-records/edit" element={<AddMedicalRecord />} />

          {/* PRESCRIPTIONS */}
          <Route path="prescriptions" element={<Prescriptions />} />
          <Route path="prescriptions/add" element={<AddPrescription />} />
          <Route path="prescriptions/update" element={<UpdatePrescription />} />

        </Route>

      </Routes>
    </BrowserRouter>
  );
}
