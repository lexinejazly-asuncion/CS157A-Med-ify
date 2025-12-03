import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function AddAppointment() {
  const navigate = useNavigate();

  const [patientID, setPatientID] = useState("");
  const [doctorID, setDoctorID] = useState("");
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");

  // Combine date + time → "YYYY-MM-DD HH:MM:00"
  function buildTimestamp() {
    return `${date} ${time}:00`;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    const apptTimestamp = buildTimestamp();

    try {
      const res = await fetch(
        "http://localhost:8080/Med_ify_war_exploded/AppointmentsServlet",
        {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({
            patientID,
            doctorID,
            apptTime: apptTimestamp,
            status: "Scheduled",
            mode: "insert"
          }),
        }
      );

      if (res.ok) {
        alert("Appointment created!");
        navigate("/appointments");
      } else {
        alert("Failed to create appointment.");
      }
    } catch (err) {
      console.error("POST error:", err);
      alert("Server error");
    }
  };

  return (
    <div>
      <h2>Add Appointment</h2>

      <form onSubmit={handleSubmit} style={{ maxWidth: "400px", marginTop: 20 }}>

        <div style={{ marginBottom: 15 }}>
          <label>Patient ID:</label>
          <input
            type="number"
            value={patientID}
            onChange={(e) => setPatientID(e.target.value)}
            required
            style={{ width: "100%", padding: 8 }}
          />
        </div>

        <div style={{ marginBottom: 15 }}>
          <label>Doctor ID:</label>
          <input
            type="number"
            value={doctorID}
            onChange={(e) => setDoctorID(e.target.value)}
            required
            style={{ width: "100%", padding: 8 }}
          />
        </div>

        <div style={{ marginBottom: 15 }}>
          <label>Date:</label>
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
            style={{ width: "100%", padding: 8 }}
          />
        </div>

        <div style={{ marginBottom: 15 }}>
          <label>Time:</label>
          <input
            type="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
            required
            style={{ width: "100%", padding: 8 }}
          />
        </div>

        <button className="button" type="submit">Submit</button>
      </form>
    </div>
  );
}
