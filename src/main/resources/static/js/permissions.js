const API_BASE = "https://laptrinhweb1-7wap.onrender.com/api/permissions";
let basePermissions = [];

function loadPermissions() {
  fetch(API_BASE)
    .then((res) => res.json())
    .then((data) => {
      basePermissions = data;
      populateModuleFilter(basePermissions);
      renderTable(basePermissions);
    });
}

function searchPermissions() {
  const keyword = document.getElementById("searchKeyword").value.trim();
  if (!keyword) {
    loadPermissions();
    return;
  }

  fetch(`${API_BASE}/search?keyword=${encodeURIComponent(keyword)}`)
    .then((res) => res.json())
    .then((data) => {
      basePermissions = data;
      populateModuleFilter(basePermissions);
      renderTable(basePermissions);
    });
}

function clearSearch() {
  document.getElementById("searchKeyword").value = "";
  document.getElementById("filterModule").value = "";
  loadPermissions();
}

function loadByModule() {
  const module = document.getElementById("filterModule").value;
  if (!module) {
    renderTable(basePermissions);
    return;
  }

  const filtered = basePermissions.filter((p) => p.module === module);
  renderTable(filtered);
}

function populateModuleFilter(data) {
  const select = document.getElementById("filterModule");
  const currentValue = select.value;
  const modules = [...new Set(data.map((p) => (p.module || "").trim()).filter((m) => m.length > 0))].sort();

  select.innerHTML = '<option value="">-- Lọc theo module --</option>';
  modules.forEach((m) => {
    const option = document.createElement("option");
    option.value = m;
    option.textContent = m;
    select.appendChild(option);
  });

  if (currentValue && modules.includes(currentValue)) {
    select.value = currentValue;
  } else {
    select.value = "";
  }
}

function renderTable(data) {
  const tbody = document.querySelector("#permissionTable tbody");
  tbody.innerHTML = "";

  data.forEach((p) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
            <td>${p.permissionCode}</td>
            <td>${p.permissionName}</td>
            <td>${p.module}</td>
            <td>${p.description || ""}</td>
            <td>${p.isActive}</td>
            <td>
                <div class="action-group">
                  <button onclick="viewPermission('${p.id}')">Chi tiết</button>
                  <button onclick="editPermission('${p.id}')">Sửa</button>
                  <button class="danger" onclick="deletePermission('${p.id}')">Xóa</button>
                </div>
            </td>
        `;
    tbody.appendChild(tr);
  });
}

function viewPermission(id) {
  fetch(`${API_BASE}/${id}`)
    .then((res) => res.json())
    .then((p) => {
      alert(
        `ID: ${p.id}\n` +
          `Code: ${p.permissionCode}\n` +
          `Tên quyền: ${p.permissionName || ""}\n` +
          `Module: ${p.module || ""}\n` +
          `Mô tả: ${p.description || ""}\n` +
          `Active: ${p.isActive}`,
      );
    });
}

function editPermission(id) {
  fetch(`${API_BASE}/${id}`)
    .then((res) => res.json())
    .then((p) => {
      const permissionName = prompt("Tên quyền:", p.permissionName || "");
      if (permissionName === null) return;

      const module = prompt("Module:", p.module || "");
      if (module === null) return;

      const description = prompt("Mô tả:", p.description || "");
      if (description === null) return;

      const payload = {
        permissionCode: p.permissionCode,
        permissionName: permissionName,
        module: module,
        description: description,
      };

      fetch(`${API_BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      }).then(() => loadPermissions());
    });
}

function createPermission(event) {
  event.preventDefault();

  const permission = {
    permissionCode: document.getElementById("permissionCode").value,
    permissionName: document.getElementById("permissionName").value,
    module: document.getElementById("module").value,
    description: document.getElementById("description").value,
  };

  fetch(API_BASE, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(permission),
  }).then(() => {
    loadPermissions();
    event.target.reset();
  });
}

function deletePermission(id) {
  fetch(`${API_BASE}/${id}`, { method: "DELETE" }).then(() => loadPermissions());
}
