document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("signup-form");

  form.addEventListener("submit", function (event) {
    event.preventDefault();
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const confirmPassword = document
      .getElementById("confirm-password")
      .value.trim();

    if (username === "") {
      showError("Please enter a username.");
      return;
    }

    if (email === "") {
      showError("Please enter an email address.");
      return;
    }

    if (!isValidEmail(email)) {
      showError("Please enter a valid email address.");
      return;
    }

    if (password === "") {
      showError("Please enter a password.");
      return;
    }

    if (password !== confirmPassword) {
      showError("Passwords do not match.");
      return;
    }

    // If all validation passes, submit the form
    alert("Form submitted successfully!");
    form.reset();
  });

  function showError(message) {
    alert(message);
  }

  function isValidEmail(email) {
    // Very basic email validation, you might want to improve this
    return /^ ]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
});
