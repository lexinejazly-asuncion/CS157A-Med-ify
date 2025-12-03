import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Appointments() {
  const navigate = useNavigate();
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/Med_ify_war_exploded/AppointmentsServlet?type=json")
      .then((res) => res.json())
      .then((data) => {
        console.log("Fetched appointments:", data);
        setAppointments(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Fetch error:", err);
        setLoading(false);
      });
  }, []);

  function formatDateTime(ts) {
    if (!ts) return { date: "", time: "" };

    const iso = ts.replace(" ", "T").split(".")[0];
    const date = new Date(iso);

    return {
      date: date.toISOString().split("T")[0],
      time: date.toTimeString().slice(0, 5),
    };
  }

  return (
    <div>
      <h2>Appointments</h2>

      <button
        className="button"
        style={{ marginTop: 15, marginBottom: 15 }}
        onClick={() => navigate("/appointments/add")}
      >
        New Appointment
      </button>

      <table className="table" style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Time</th>
            <th>Patient ID</th>
            <th>Doctor ID</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {loading ? (
            <tr>
              <td colSpan="6" style={{ textAlign: "center" }}>Loading...</td>
            </tr>
          ) : appointments.length === 0 ? (
            <tr>
              <td colSpan="6" style={{ textAlign: "center" }}>No appointments found.</td>
            </tr>
          ) : (
            appointments.map((a) => {
              const { date, time } = formatDateTime(a.apptTime);

              return (
                <tr key={a.appointmentID}>
                  <td>{a.appointmentID}</td>
                  <td>{date}</td>
                  <td>{time}</td>
                  <td>{a.patientID}</td>
                  <td>{a.doctorID}</td>
                  <td>{a.status}</td>
                </tr>
              );
            })
          )}
        </tbody>
      </table>
    </div>
  );
}
