const API_BASE = "https://laptrinhweb1-7wap.onrender.com/api/role-permissions";
let editingId = null;

function loadAll() {
  fetch(API_BASE).then((res) => res.json()).then(renderTable);
}

function loadByRole() {
  const roleId = document.getElementById("filterRoleId").value;
  fetch(`${API_BASE}/role/${roleId}`).then((res) => res.json()).then(renderTable);
}

function loadByPermission() {
  const permissionId = document.getElementById("filterPermissionId").value;
  fetch(`${API_BASE}/permission/${permissionId}`).then((res) => res.json()).then(renderTable);
}

function assignPermission() {
  const data = {
    roleId: document.getElementById("roleId").value,
    permissionId: document.getElementById("permissionId").value,
  };

  const isEditing = !!editingId;
  const url = isEditing ? `${API_BASE}/${editingId}` : API_BASE;
  const method = isEditing ? "PUT" : "POST";

  fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  }).then(() => {
    alert(isEditing ? "Cập nhật thành công" : "Gán quyền thành công");
    resetForm();
    loadAll();
  });
}

function startEdit(item) {
  editingId = item.id;
  document.getElementById("roleId").value = item.roleId;
  document.getElementById("permissionId").value = item.permissionId;
  document.getElementById("submitBtn").textContent = "Cập nhật";
}

function resetForm() {
  editingId = null;
  document.getElementById("roleId").value = "";
  document.getElementById("permissionId").value = "";
  document.getElementById("submitBtn").textContent = "Gán";
}

function deleteMapping(id) {
  if (!confirm("Thu hồi quyền này?")) return;

  fetch(`${API_BASE}/${id}`, { method: "DELETE" }).then(() => loadAll());
}

function toggleStatus(id, currentStatus) {
  const nextStatus = !currentStatus;
  const actionLabel = nextStatus ? "bật" : "tắt";

  if (!confirm(`Bạn có chắc muốn ${actionLabel} trạng thái?`)) return;

  fetch(`${API_BASE}/${id}/status/${nextStatus}`, { method: "PUT" }).then(() => loadAll());
}

function renderTable(data) {
  const tbody = document.getElementById("tableBody");
  tbody.innerHTML = "";

  data.forEach((item) => {
    const row = `
            <tr>
                <td>${item.id}</td>
                <td>${item.roleId}</td>
                <td>${item.permissionId}</td>
                <td>${item.isActive ? "Bật" : "Tắt"}</td>
                <td>
                    <div class="action-group">
                      <button onclick='startEdit(${JSON.stringify(item)})'>Sửa</button>
                      <button onclick="toggleStatus('${item.id}', ${item.isActive})">${item.isActive ? "Tắt" : "Bật"}</button>
                      <button class="danger" onclick="deleteMapping('${item.id}')">Xóa</button>
                    </div>
                </td>
            </tr>
        `;
    tbody.innerHTML += row;
  });
}

loadAll();
