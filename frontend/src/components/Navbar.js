export default function Navbar({ userInfo, onLogout }) {
  return (
    <div style={{
      display: "flex",
      justifyContent: "space-between",
      padding: "10px 20px",
      borderBottom: "1px solid #D1D5DB",
      background: "white"
    }}>
      <h2 style={{ color: "#2563EB", margin: 0 }}>Med-ify</h2>
      <div>
        {userInfo.name} ({userInfo.role})
        <button
          className="button"
          style={{ marginLeft: 10, background: "#DC2626" }}
          onClick={onLogout}
        >
          Logout
        </button>
      </div>
    </div>
  );
}
