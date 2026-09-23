import { useState } from "react";

function EmployeeDashboard({ currentUser }) {
  const [category, setCategory] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    const url = `http://localhost:8080/api/requests?userId=${currentUser.id}&category=${category}`;

    fetch(url, { method: "POST" })
      .then((response) => {
        if (response.ok) {
          setMessage("✅ Success! Your request is pending admin approval.");
          setCategory("");
        } else {
          setMessage("❌ Error: Could not submit request.");
        }
      })
      .catch((error) => {
        console.error("Fetch error:", error);
        setMessage("❌ Server error. Is Spring Boot running?");
      });
  };

  return (
    <div className="bg-white p-8 rounded-lg shadow-sm border border-gray-200 max-w-lg mx-auto">
      <div className="border-b border-gray-100 pb-4 mb-6">
        <h2 className="text-2xl font-semibold text-gray-800">
          Request Equipment
        </h2>
        <p className="text-gray-500 mt-1">
          Welcome, Employee #{currentUser.id} - Please submit your hardware
          request below.
        </p>
      </div>

      <form onSubmit={handleSubmit} className="flex flex-col gap-5">
        <div>
          <label className="block text-sm font-semibold text-gray-700 mb-2">
            Equipment Category
          </label>
          <select
            className="w-full border border-gray-300 rounded-md p-2.5 bg-gray-50 focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          >
            <option value="" disabled>
              -- Select what you need --
            </option>
            <option value="Laptop">Laptop</option>
            <option value="Monitor">Monitor</option>
            <option value="Keyboard">Keyboard</option>
            <option value="Mouse">Mouse</option>
          </select>
        </div>

        <button
          type="submit"
          className="mt-2 w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-md p-3 transition-colors shadow-sm"
        >
          Submit Request
        </button>
      </form>

      {message && (
        <div
          className={`mt-6 p-4 rounded-md border ${message.includes("✅") ? "bg-green-50 border-green-200 text-green-800" : "bg-red-50 border-red-200 text-red-800"}`}
        >
          <p className="font-medium text-center text-sm">{message}</p>
        </div>
      )}
    </div>
  );
}

export default EmployeeDashboard;
