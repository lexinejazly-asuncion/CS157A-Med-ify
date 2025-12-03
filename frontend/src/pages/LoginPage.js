import { useState } from "react";
import "../styles.css";

export default function LoginPage({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("Receptionist");

  return (
    <div style={{
      height: "100vh",
      display: "flex",
      alignItems: "center",
      justifyContent: "center"
    }}>
      <div className="card" style={{ width: "320px" }}>
        <h2 style={{ textAlign: "center", color: "#2563EB" }}>Med-ify</h2>

        <input
          placeholder="Email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          style={{ width: "100%", marginBottom: 10, padding: 8 }}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          style={{ width: "100%", marginBottom: 10, padding: 8 }}
        />

        <select
          value={role}
          onChange={e => setRole(e.target.value)}
          style={{ width: "100%", marginBottom: 20, padding: 8 }}
        >
          <option>Receptionist</option>
          <option>Doctor</option>
          <option>Nurse</option>
          <option>Pharmacist</option>
        </select>

        <button className="button" style={{ width: "100%" }}
          onClick={() => onLogin(email, role)}
        >
          Login
        </button>
      </div>
    </div>
  );
}
