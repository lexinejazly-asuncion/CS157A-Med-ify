export default function PatientDetail() {
  return (
    <div>
      <h2>Patient Details</h2>

      <div className="card" style={{ marginTop: 20 }}>
        <h3>John Doe</h3>
        <p><strong>DOB:</strong> 1990-01-10</p>
        <p><strong>Gender:</strong> Male</p>
        <p><strong>Address:</strong> 123 Main St</p>

        <div style={{ marginTop: 15 }}>
          <button className="button">Edit Info</button>
          <button className="button" style={{ marginLeft: 10 }}>
            Schedule Appointment
          </button>
        </div>
      </div>

      <h3 style={{ marginTop: 30 }}>Upcoming Appointments</h3>
      <table className="table">
        <thead>
          <tr>
            <th>Date</th>
            <th>Time</th>
            <th>Doctor</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>2025-02-12</td>
            <td>10:00 AM</td>
            <td>Dr. Kim</td>
            <td>Confirmed</td>
          </tr>
        </tbody>
      </table>

      <h3 style={{ marginTop: 30 }}>Medical Records</h3>
      <table className="table">
        <thead>
          <tr>
            <th>Record ID</th>
            <th>Visit Date</th>
            <th>Doctor</th>
            <th>Notes</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>MR-20</td>
            <td>2025-02-01</td>
            <td>Dr. Patel</td>
            <td>Routine checkup</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
