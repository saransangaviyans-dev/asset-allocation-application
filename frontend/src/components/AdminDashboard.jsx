import { useState, useEffect } from "react";

function AdminDashboard({ currentUser }) {
  const [requests, setRequests] = useState([]);
  const [assets, setAssets] = useState([]);
  const [message, setMessage] = useState("");
  const [selectedAssets, setSelectedAssets] = useState({});

  useEffect(() => {
    loadDashboard();
  }, [currentUser.id]);

  const loadDashboard = () => {
    fetch(
      `http://localhost:8080/api/requests/pending?adminId=${currentUser.id}`,
    )
      .then((res) => res.json())
      .then((data) => setRequests(data));

    fetch("http://localhost:8080/api/assets")
      .then((res) => res.json())
      .then((data) => {
        const availableOnly = data.filter(
          (item) => item.status === "AVAILABLE",
        );
        setAssets(availableOnly);
      });
  };

  const handleApprove = (requestId) => {
    const chosenAssetId = selectedAssets[requestId];

    if (!chosenAssetId) {
      setMessage("⚠️ Please select an asset from the dropdown first.");
      return;
    }

    const url = `http://localhost:8080/api/requests/${requestId}/approve?adminId=${currentUser.id}&assetId=${chosenAssetId}`;

    fetch(url, { method: "POST" })
      .then((response) => {
        if (response.ok) {
          setMessage(
            `✅ Ticket #${requestId} successfully approved and allocated!`,
          );
          loadDashboard();
        } else {
          setMessage("❌ Failed to approve request.");
        }
      })
      .catch((err) => console.error("Error:", err));
  };

  return (
    <div className="bg-white p-8 rounded-lg shadow-sm border border-gray-200">
      <div className="flex justify-between items-center border-b border-gray-100 pb-4 mb-6">
        <div>
          <h2 className="text-2xl font-semibold text-gray-800">
            IT Administrator Queue
          </h2>
          <p className="text-gray-500 mt-1">
            Review and approve pending employee hardware requests.
          </p>
        </div>
        <span className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-semibold border border-blue-200">
          {requests.length} Pending
        </span>
      </div>

      {message && (
        <div className="mb-6 p-4 bg-gray-50 border border-gray-200 rounded-md">
          <p
            className={`font-medium text-sm ${message.includes("✅") ? "text-green-700" : "text-amber-700"}`}
          >
            {message}
          </p>
        </div>
      )}

      {requests.length === 0 ? (
        <div className="text-center py-12 bg-gray-50 rounded-lg border-2 border-dashed border-gray-200">
          <p className="text-gray-500 font-medium">
            All caught up! No pending requests in the queue.
          </p>
        </div>
      ) : (
        <div className="overflow-x-auto rounded-lg border border-gray-200">
          <table className="w-full text-left text-sm whitespace-nowrap">
            <thead className="bg-gray-50 border-b border-gray-200 text-gray-600 uppercase tracking-wider text-xs">
              <tr>
                <th className="px-6 py-4 font-semibold">Ticket ID</th>
                <th className="px-6 py-4 font-semibold">Employee ID</th>
                <th className="px-6 py-4 font-semibold">Requested Item</th>
                <th className="px-6 py-4 font-semibold">
                  Inventory Allocation
                </th>
                <th className="px-6 py-4 font-semibold text-right">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {requests.map((req) => (
                <tr
                  key={req.id}
                  className="hover:bg-blue-50/50 transition-colors"
                >
                  <td className="px-6 py-4 font-medium text-gray-900">
                    #{req.id}
                  </td>
                  <td className="px-6 py-4 text-gray-600">{req.user?.id}</td>
                  <td className="px-6 py-4">
                    <span className="bg-gray-100 text-gray-800 px-3 py-1 rounded-full text-xs font-medium border border-gray-200">
                      {req.requestedCategory}
                    </span>
                  </td>
                  <td className="px-6 py-4">
                    <select
                      className="w-full border border-gray-300 rounded-md p-2 text-gray-700 bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                      onChange={(e) =>
                        setSelectedAssets({
                          ...selectedAssets,
                          [req.id]: e.target.value,
                        })
                      }
                      defaultValue=""
                    >
                      <option value="" disabled>
                        Select from inventory...
                      </option>
                      {assets
                        .filter((a) => a.category === req.requestedCategory)
                        .map((a) => (
                          <option key={a.id} value={a.id}>
                            {a.name} (Asset #{a.id})
                          </option>
                        ))}
                    </select>
                  </td>
                  <td className="px-6 py-4 text-right">
                    <button
                      onClick={() => handleApprove(req.id)}
                      className="bg-green-600 hover:bg-green-700 text-white font-medium px-4 py-2 rounded-md transition-colors shadow-sm"
                    >
                      Approve Ticket
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default AdminDashboard;
