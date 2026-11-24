const userForm = document.getElementById("userForm");
const messageEl = document.getElementById("message");

userForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const userData = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
    };

    messageEl.textContent = "";
    messageEl.className = "message";

    try {
        const response = await fetch("api/v1/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userData),
        });

        const responseText = await response.text();

        console.log("Status:", response.status);
        console.log("Response:", responseText);

        if (!response.ok) {
            throw new Error(`Server error ${response.status}: ${responseText}`);
        }

        const result = JSON.parse(responseText);

        messageEl.className = "message message-success";
        messageEl.textContent = "User saved successfully! : " + result.name;

        console.log(result.name);

        userForm.reset();
    } catch (error) {
        console.error("Error:", error);

        messageEl.className = "message message-error";
        messageEl.textContent = error.message;
    }
});