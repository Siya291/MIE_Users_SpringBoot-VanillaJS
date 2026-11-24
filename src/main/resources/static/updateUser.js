const params = new URLSearchParams(window.location.search);
const userId = params.get("id");
const updateForm = document.getElementById("updateForm");
const messageEl = document.getElementById("message");

async function loadUserDetails() {
    if (!userId) {
        messageEl.textContent = "No user ID provided.";
        return;
    }
    try {
        const response = await fetch(`/api/v1/users/${userId}`);
        if (!response.ok) throw new Error("User not found");

        const user = await response.json();
        document.getElementById("name").value = user.name;
        document.getElementById("email").value = user.email;
    } catch (error) {
        messageEl.className = "message message-error";
        messageEl.textContent = error.message;
    }
}

updateForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const updatedData = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim()
    };

    try {
        const response = await fetch(`/api/v1/users/${userId}`, {
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatedData)
        });

        if (!response.ok) {
            const errText = await response.text();
            throw new Error(`Update failed (${response.status}): ${errText}`);
        }

        messageEl.className = "message message-success";
        messageEl.textContent = "User updated successfully! Redirecting...";

        setTimeout(() => {
            window.location.href = "viewUsers.html";
        }, 1000);
    } catch (error) {
        messageEl.className = "message message-error";
        messageEl.textContent = error.message;
    }
});