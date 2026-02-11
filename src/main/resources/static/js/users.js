const API_BASE = "http://localhost:8080/api/users";

function loadUsers() {
  fetch(API_BASE).then((res) => res.json()).then(renderUsers);
}

function searchUsers() {
  const keyword = document.getElementById("searchKeyword").value.trim();
  if (!keyword) {
    loadUsers();
    return;
  }

  fetch(`${API_BASE}/search?keyword=${encodeURIComponent(keyword)}`)
    .then((res) => res.json())
    .then(renderUsers);
}

function clearSearch() {
  document.getElementById("searchKeyword").value = "";
  loadUsers();
}

function renderUsers(data) {
  const tbody = document.querySelector("#userTable tbody");
  tbody.innerHTML = "";
  data.forEach((u) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
            <td>${u.username}</td>
            <td>${u.fullName || ""}</td>
            <td>${u.email || ""}</td>
            <td>${u.userType}</td>
            <td>${u.isActive}</td>
            <td>
                <div class="action-group">
                  <button onclick="viewUser('${u.id}')">Chi tiết</button>
                  <button onclick="editUser('${u.id}')">Sửa</button>
                  <button onclick="toggleActive('${u.id}')">Bật/Tắt</button>
                  <button class="danger" onclick="deleteUser('${u.id}')">Xóa</button>
                </div>
            </td>
        `;
    tbody.appendChild(tr);
  });
}

function createUser(event) {
  event.preventDefault();

  const user = {
    username: document.getElementById("username").value,
    password: document.getElementById("password").value,
    fullName: document.getElementById("fullName").value,
    email: document.getElementById("email").value,
    phone: document.getElementById("phone").value,
    userType: document.getElementById("userType").value,
  };

  fetch(API_BASE, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user),
  }).then(() => {
    loadUsers();
    event.target.reset();
  });
}

function deleteUser(id) {
  fetch(`${API_BASE}/${id}`, { method: "DELETE" }).then(() => loadUsers());
}

function toggleActive(id) {
  fetch(`${API_BASE}/${id}/active`, { method: "PATCH" }).then(() => loadUsers());
}

function viewUser(id) {
  fetch(`${API_BASE}/${id}`)
    .then((res) => res.json())
    .then((u) => {
      alert(
        `ID: ${u.id}\n` +
          `Username: ${u.username}\n` +
          `Họ tên: ${u.fullName || ""}\n` +
          `Email: ${u.email || ""}\n` +
          `Số điện thoại: ${u.phone || ""}\n` +
          `Loại: ${u.userType || ""}\n` +
          `Active: ${u.isActive}`,
      );
    });
}

function editUser(id) {
  fetch(`${API_BASE}/${id}`)
    .then((res) => res.json())
    .then((u) => {
      const fullName = prompt("Họ tên:", u.fullName || "");
      if (fullName === null) return;

      const email = prompt("Email:", u.email || "");
      if (email === null) return;

      const phone = prompt("Số điện thoại:", u.phone || "");
      if (phone === null) return;

      const userType = prompt(
        "Loại user (ADMIN/GIAOVU/GIANGVIEN/SINHVIEN):",
        u.userType || "",
      );
      if (userType === null) return;

      const payload = {
        username: u.username,
        fullName: fullName,
        email: email,
        phone: phone,
        userType: userType,
      };

      fetch(`${API_BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      }).then(() => loadUsers());
    });
}
