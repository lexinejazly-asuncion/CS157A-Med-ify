import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function AddPrescription() {
  const [form, setForm] = useState({
    prescriptionDate: "",
    patientID: "",
    prescriptionName: "",
    dose: "",
    quantity: "",
    refills: "0"
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = new URLSearchParams();
    data.append("mode", "insert");
    data.append("prescriptionDate", form.prescriptionDate);
    data.append("patientID", form.patientID);
    data.append("prescriptionName", form.prescriptionName);
    data.append("dose", form.dose);
    data.append("quantity", form.quantity);
    data.append("refills", form.refills);

    const res = await fetch("http://localhost:8080/Med_ify_war_exploded/PrescriptionsServlet", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: data.toString()
    });

    if (res.ok) {
      alert("Prescription added!");
      window.location.href = "/prescriptions";
    } else {
      alert("Error adding prescription.");
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Add New Prescription Record</h2>

      <form onSubmit={handleSubmit} style={{ maxWidth: 400 }}>

        <div>
          <label>Prescription Date:</label><br />
          <input
            type="date"
            required
            placeholder="mm/dd/yyyy"
            value={form.prescriptionDate}
            onChange={(e) =>
              setForm({ ...form, prescriptionDate: e.target.value })
            }
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Patient ID:</label><br />
          <input
            type="number"
            required
            placeholder="e.g., 5"
            value={form.patientID}
            onChange={(e) =>
              setForm({ ...form, patientID: e.target.value })
            }
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Medication Name:</label><br />
          <input
            type="text"
            required
            placeholder="e.g., Amoxicillin"
            value={form.prescriptionName}
            onChange={(e) =>
              setForm({ ...form, prescriptionName: e.target.value })
            }
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Dose:</label><br />
          <input
            type="text"
            required
            placeholder="e.g., 500mg"
            value={form.dose}
            onChange={(e) =>
              setForm({ ...form, dose: e.target.value })
            }
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Quantity:</label><br />
          <input
            type="number"
            required
            placeholder="e.g., 30"
            value={form.quantity}
            onChange={(e) =>
              setForm({ ...form, quantity: e.target.value })
            }
          />
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Refills:</label><br />
          <input
            type="number"
            min="0"
            placeholder="0"
            value={form.refills}
            onChange={(e) =>
              setForm({ ...form, refills: e.target.value })
            }
          />
        </div>

        <button type="submit" style={{ marginTop: 15 }}>
          Add Prescription Record
        </button>

      </form>
    </div>
  );
}
