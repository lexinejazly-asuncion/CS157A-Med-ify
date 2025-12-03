export default function Dashboard() {
  return (
    <div>
      <h2>Dashboard</h2>

      <div style={{ display: "flex", gap: 20, marginTop: 20 }}>
        <div className="card" style={{ flex: 1 }}>Today's Appointments: 5</div>
        <div className="card" style={{ flex: 1 }}>Total Patients: 120</div>
        <div className="card" style={{ flex: 1 }}>New Prescriptions: 8</div>
      </div>

      <h3 style={{ marginTop: 30 }}>Upcoming Appointments</h3>

      <table className="table">
        <thead>
          <tr>
            <th>Patient</th>
            <th>Doctor</th>
            <th>Time</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr><td>John Doe</td><td>Dr. Kim</td><td>10:00 AM</td><td>Confirmed</td></tr>
        </tbody>
      </table>
    </div>
  );
}
