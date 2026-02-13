const API_BASE = "https://laptrinhweb1-7wap.onrender.com/api/roles";

function loadRoles() {
  fetch(API_BASE).then((res) => res.json()).then(renderRoles);
}

function searchRoles() {
  const keyword = document.getElementById("searchKeyword").value.trim();
  if (!keyword) {
    loadRoles();
    return;
  }

  fetch(`${API_BASE}/search?keyword=${encodeURIComponent(keyword)}`)
    .then((res) => res.json())
    .then(renderRoles);
}

function clearSearch() {
  document.getElementById("searchKeyword").value = "";
  loadRoles();
}

function renderRoles(data) {
  const tbody = document.querySelector("#roleTable tbody");
  tbody.innerHTML = "";
  data.forEach((r) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
            <td>${r.roleCode}</td>
            <td>${r.roleName || ""}</td>
            <td>${r.description || ""}</td>
            <td>${r.isSystem}</td>
            <td>${r.isActive}</td>
            <td>
                <div class="action-group">
                  <button onclick="viewRole('${r.id}')">Chi tiết</button>
                  <button onclick="editRole('${r.id}')">Sửa</button>
                  <button class="danger" onclick="deleteRole('${r.id}', ${r.isSystem}, this)">Xóa</button>
                </div>
            </td>
        `;
    tbody.appendChild(tr);
  });
}

function viewRole(id) {
  fetch(`${API_BASE}/${id}`)
    .then((res) => res.json())
    .then((r) => {
      alert(
        `ID: ${r.id}\n` +
          `Role Code: ${r.roleCode}\n` +
          `Tên vai trò: ${r.roleName || ""}\n` +
          `Mô tả: ${r.description || ""}\n` +
          `System: ${r.isSystem}\n` +
          `Active: ${r.isActive}`,
      );
    });
}

function editRole(id) {
  fetch(`${API_BASE}/${id}`)
    .then((res) => res.json())
    .then((r) => {
      const roleName = prompt("Tên vai trò:", r.roleName || "");
      if (roleName === null) return;

      const description = prompt("Mô tả:", r.description || "");
      if (description === null) return;

      const payload = {
        roleCode: r.roleCode,
        roleName: roleName,
        description: description,
        isSystem: r.isSystem,
      };

      fetch(`${API_BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      }).then(() => loadRoles());
    });
}

function createRole(event) {
  event.preventDefault();

  const role = {
    roleCode: document.getElementById("roleCode").value,
    roleName: document.getElementById("roleName").value,
    description: document.getElementById("description").value,
    isSystem: document.getElementById("isSystem").value === "true",
  };

  fetch(API_BASE, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(role),
  }).then(() => {
    loadRoles();
    event.target.reset();
  });
}

function deleteRole(id, isSystem, btn) {
  if (isSystem) {
    alert("Không thể xóa role hệ thống");
    return;
  }

  fetch(`${API_BASE}/${id}`, { method: "DELETE" })
    .then((res) => {
      if (!res.ok) {
        throw new Error("Delete failed");
      }
      const row = btn.closest("tr");
      if (row) {
        row.remove();
      } else {
        loadRoles();
      }
    })
    .catch(() => {
      alert("Xóa không thành công");
      loadRoles();
    });
}
