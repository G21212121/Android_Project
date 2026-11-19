package com.boulderingapp.utils

object PasswordValidator {

    fun validateLogin(email: String, password: String): String? {
        if (email.isBlank()) {
            return "Email address cannot be empty"
        }
        if (password.isBlank()) {
            return "Password cannot be empty"
        }
        return null
    }

    fun validateRegistration(email: String, password: String, confirmPassword: String): String? {
        if (email.isBlank()) {
            return "Email address cannot be empty"
        }
        return validate(password, confirmPassword)
    }
    fun validate(password: String, confirmPassword: String? = null): String? {
        if (password.isEmpty()) {
            return "Password cannot be empty"
        }
        if (confirmPassword != null && confirmPassword.isEmpty()) {
            return "Confirm password cannot be empty"
        }
        if (confirmPassword != null && password != confirmPassword) {
            return "Passwords do not match"
        }
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }
        if (!password.any { it.isUpperCase() }) {
            return "Password must contain at least one uppercase letter"
        }
        if (!password.any { it.isLowerCase()}) {
            return "Password must contain at least one lowercase letter"
        }
        if (!password.any { it.isDigit() }) {
            return "Password must contain at least one digit"
        }
  /**      if (!password.any { !it.isLetterOrDigit() }) {
            return "Password must contain at least one special character"
        }
  **/

        return null // Brak błędów – hasło jest poprawne
    }
}