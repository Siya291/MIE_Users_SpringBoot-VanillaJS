const tableBody = document.getElementById("usersTableBody");
const messageEl = document.getElementById("message");

function showMessage(text, type) {
    messageEl.textContent = text;
    messageEl.className = `message message-${type}`;
}

function clearMessage() {
    messageEl.textContent = "";
    messageEl.className = "message";
}

async function getAllUsers() {
    clearMessage();
    tableBody.innerHTML = `<tr><td colspan="4" style="text-align: center;">Loading users...</td></tr>`;

    try {
        const response = await fetch("/api/v1/users");
        const responseText = await response.text();

        if (!response.ok) {
            throw new Error(`Server error ${response.status}: ${responseText}`);
        }

        const users = JSON.parse(responseText);
        renderTable(users);
    } catch (error) {
        console.error("Error fetching users:", error);
        showMessage(error.message, "error");
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align: center; color: #dc2626;">Unable to load user data.</td></tr>`;
    }
}

function renderTable(users) {
    tableBody.innerHTML = "";

    if (!Array.isArray(users) || users.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align: center;">No registered users yet.</td></tr>`;
        return;
    }

    users.forEach((user, index) => {
        const row = document.createElement("tr");

        const indexCell = document.createElement("td");
        indexCell.textContent = index + 1;

        const nameCell = document.createElement("td");
        nameCell.textContent = user.name ?? "";

        const emailCell = document.createElement("td");
        emailCell.textContent = user.email ?? "";

        const actionCell = document.createElement("td");

        const updateLink = document.createElement("a");
        updateLink.href = `updateUser.html?id=${user.userId}`;
        updateLink.title = "Update User";
        updateLink.textContent = "✏️";

        const deleteBtn = document.createElement("button");
        deleteBtn.type = "button";
        deleteBtn.title = "Delete User";
        deleteBtn.textContent = "🗑️";
        deleteBtn.addEventListener("click", () => deleteUser(user.userId, user.name));

        actionCell.append(updateLink, deleteBtn);
        row.append(indexCell, nameCell, emailCell, actionCell);
        tableBody.appendChild(row);
    });
}

async function deleteUser(userId, userName) {
    const confirmed = confirm(`Are you sure you want to delete "${userName}"?`);
    if (!confirmed) return;

    try {
        const response = await fetch(`/api/v1/users/${userId}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            const errText = await response.text();
            throw new Error(`Failed to delete user (${response.status}): ${errText}`);
        }

        await getAllUsers();
        showMessage(`User "${userName}" deleted successfully.`, "success");
    } catch (error) {
        console.error("Error deleting user:", error);
        showMessage(error.message, "error");
    }
}

document.addEventListener("DOMContentLoaded", getAllUsers);