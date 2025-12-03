import { Link } from "react-router-dom";

export default function Sidebar() {
  return (
    <div style={{
      width: "200px",
      background: "#F3F4F6",
      padding: 20,
      borderRight: "1px solid #D1D5DB"
    }}>
      <nav style={{ display: "flex", flexDirection: "column", gap: 10 }}>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/patients">Patients</Link>
        <Link to="/appointments">Appointments</Link>
        <Link to="/medical-records">Medical Records</Link>
        <Link to="/prescriptions">Prescriptions</Link>
      </nav>
    </div>
  );
}
