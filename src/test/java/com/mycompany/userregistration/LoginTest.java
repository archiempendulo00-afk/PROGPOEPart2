package com.mycompany.userregistration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;
    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOutput);
    }

    @Test
    void correctlyFormattedUsernameReturnsExpectedMessage() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertEquals("Welcome Kyle Smith it is great to see you again.",
                login.returnLoginStatus(login.LoginUser("kyl_1", "Ch&&sec@ke99!")));
    }

    @Test
    void incorrectlyFormattedUsernameReturnsExpectedMessage() {
        login.CheckUserName("kyle!!!!!!!");

        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length",
                output.toString().trim());
    }

    @Test
    void passwordMeetingComplexityReturnsExpectedMessage() {
        login.checkPasswordComplexity("Ch&&sec@ke99!");

        assertEquals("Password successfully captured", output.toString().trim());
    }

    @Test
    void passwordFailingComplexityReturnsExpectedMessage() {
        login.checkPasswordComplexity("password");

        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character",
                output.toString().trim());
    }

    @Test
    void correctlyFormattedCellNumberReturnsExpectedMessage() {
        login.checkCellPhoneNumber("+27838968976");

        assertEquals("Cell number successfully captured", output.toString().trim());
    }

    @Test
    void incorrectlyFormattedCellNumberReturnsExpectedMessage() {
        login.checkCellPhoneNumber("08966553");

        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
                output.toString().trim());
    }

    @Test
    void successfulLoginReturnsTrue() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertTrue(login.LoginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void failedLoginReturnsFalse() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");

        assertFalse(login.LoginUser("kyl_1", "wrongPassword1!"));
    }

    @Test
    void usernameValidationReturnsTrueForValidUsername() {
        assertTrue(login.CheckUserName("kyl_1"));
    }

    @Test
    void usernameValidationReturnsFalseForInvalidUsername() {
        assertFalse(login.CheckUserName("kyle!!!!!!!"));
    }

    @Test
    void passwordValidationReturnsTrueForValidPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void passwordValidationReturnsFalseForInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void cellNumberValidationReturnsTrueForValidNumber() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void cellNumberValidationReturnsFalseForInvalidNumber() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}