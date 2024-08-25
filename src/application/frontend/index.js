document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    const userIdInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const errorMessage = document.getElementById("errorMessage");

    const users = {
        "A00001": "Admin@1234",
        "U00001": "User@1234",
        "D00001": "Doctor@1234"
    };

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        const userId = userIdInput.value;
        const password = passwordInput.value;
        const userPassword = users[userId];

        // Validate User ID
        const userIdPattern = /^[ADU]\d{5}$/;
        if (!userIdPattern.test(userIdInput.value)) {
          valid = false;
          errorMessages.push(
            "• User ID must start with A, D, or U followed by exactly 5 digits."
          );
        }

        // Validate Password
        if (passwordInput.value.length < 8) {
          valid = false;
          errorMessages.push(
            "• Password must be at least 8 characters long."
          );
        }


        if (userPassword && userPassword === password) {
            errorMessage.textContent = "";
            if (userId.startsWith("U")) {
                window.location.href = "user/staffIndex.html";
            } else if (userId.startsWith("D")) {
                window.location.href = "doctor/doctorIndex.html";
            } else if (userId.startsWith("A")) {
                window.location.href = "admin/adminIndex.html";
            }
        } else {
            errorMessage.textContent = "Wrong User Id or Password";
        }
    });
});

 // Array of health quotes
 const quotes = [
    "Health is wealth.",
    "A healthy outside starts from the inside.",
    "To enjoy the glow of good health, you must exercise.",
    "Take care of your body. It's the only place you have to live."
];

// Function to display a random quote
function displayRandomQuote() {
    const quoteText = document.getElementById("quoteText");
    const randomIndex = Math.floor(Math.random() * quotes.length);
    quoteText.textContent = quotes[randomIndex];
}

// Initial display and change quote every 30 seconds
displayRandomQuote();
setInterval(displayRandomQuote, 30000);