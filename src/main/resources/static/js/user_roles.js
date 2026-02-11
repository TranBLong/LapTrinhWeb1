const API = "http://localhost:8080/api/user-roles";

function loadAll() {
  fetch(API).then((r) => r.json()).then(render);
}

function loadByUser() {
  const id = document.getElementById("filterUserId").value;
  fetch(`${API}/user/${id}`).then((r) => r.json()).then(render);
}

function loadByRole() {
  const id = document.getElementById("filterRoleId").value;
  fetch(`${API}/role/${id}`).then((r) => r.json()).then(render);
}

function assignRole() {
  const userId = document.getElementById("userId").value;
  const roleId = document.getElementById("roleId").value;

  fetch(API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userId, roleId }),
  }).then(() => loadAll());
}

function remove(id) {
  fetch(`${API}/${id}`, { method: "DELETE" }).then(() => loadAll());
}

function editItem(id, currentUserId, currentRoleId) {
  const userId = prompt("User ID mới:", currentUserId || "");
  if (userId === null) return;

  const roleId = prompt("Role ID mới:", currentRoleId || "");
  if (roleId === null) return;

  fetch(`${API}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userId: userId.trim(), roleId: roleId.trim() }),
  }).then(() => loadAll());
}

function toggleStatus(id, currentActive) {
  const nextActive = !currentActive;
  fetch(`${API}/${id}/status/${nextActive}`, { method: "PUT" }).then(() => loadAll());
}

function render(list) {
  const tbody = document.getElementById("data");
  tbody.innerHTML = "";

  list.forEach((i) => {
    const tr = document.createElement("tr");
    const userId = i.userId || (i.user && i.user.id) || "";
    const roleId = i.roleId || (i.role && i.role.id) || "";
    const isActive = i.isActive ?? true;
    const toggleLabel = isActive ? "Tắt" : "Bật";
    tr.innerHTML = `
            <td>${i.id}</td>
            <td>${userId}</td>
            <td>${roleId}</td>
            <td>${isActive ? "Bật" : "Tắt"}</td>
            <td>
              <div class="action-group">
                <button onclick="editItem('${i.id}', '${userId}', '${roleId}')">Sửa</button>
                <button onclick="toggleStatus('${i.id}', ${isActive})">${toggleLabel}</button>
                <button class="danger" onclick="remove('${i.id}')">Xóa</button>
              </div>
            </td>
        `;
    tbody.appendChild(tr);
  });
}

loadAll();
