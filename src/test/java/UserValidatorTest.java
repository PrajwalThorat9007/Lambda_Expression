import exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import validator.UserValidator;

import static org.junit.jupiter.api.Assertions.*;
import validator.UserValidator;

public class UserValidatorTest {

    // ═══════════════════════════════════════════════════════════════
    // FIRST NAME
    // ═══════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "valid first name: {0}")
    @ValueSource(strings = {"Alice", "Bob", "A"})
    void testFirstName_Happy_ValidNames(String name) {
        // valid names should not throw any exception
        assertDoesNotThrow(() -> UserValidator.validateFirstName(name));
    }

    @ParameterizedTest(name = "null or empty first name: [{0}]")
    @NullAndEmptySource
    void testFirstName_Sad_NullAndEmpty(String name) {
        // null and empty should throw InvalidFirstNameException
        assertThrows(InvalidFirstNameException.class,
                () -> UserValidator.validateFirstName(name));
    }

    @ParameterizedTest(name = "invalid first name: {0}")
    @ValueSource(strings = {"Alice123", "Alice@", "Ali ce", "123"})
    void testFirstName_Sad_InvalidNames(String name) {
        // invalid names should throw InvalidFirstNameException
        assertThrows(InvalidFirstNameException.class,
                () -> UserValidator.validateFirstName(name));
    }

    @Test
    void testFirstName_Sad_ExceptionMessage() {
        // exception message should be meaningful
        InvalidFirstNameException ex = assertThrows(
                InvalidFirstNameException.class,
                () -> UserValidator.validateFirstName("Alice123"));
        assertTrue(ex.getMessage().contains("alphabets only"));
    }

    // ═══════════════════════════════════════════════════════════════
    // LAST NAME
    // ═══════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "valid last name: {0}")
    @ValueSource(strings = {"Smith", "Doe", "S"})
    void testLastName_Happy_ValidNames(String name) {
        // valid last names should not throw any exception
        assertDoesNotThrow(() -> UserValidator.validateLastName(name));
    }

    @ParameterizedTest(name = "null or empty last name: [{0}]")
    @NullAndEmptySource
    void testLastName_Sad_NullAndEmpty(String name) {
        // null and empty should throw InvalidLastNameException
        assertThrows(InvalidLastNameException.class,
                () -> UserValidator.validateLastName(name));
    }

    @ParameterizedTest(name = "invalid last name: {0}")
    @ValueSource(strings = {"Smith99", "Smith#", "Smi th"})
    void testLastName_Sad_InvalidNames(String name) {
        // invalid last names should throw InvalidLastNameException
        assertThrows(InvalidLastNameException.class,
                () -> UserValidator.validateLastName(name));
    }

    @Test
    void testLastName_Sad_ExceptionMessage() {
        // exception message should be meaningful
        InvalidLastNameException ex = assertThrows(
                InvalidLastNameException.class,
                () -> UserValidator.validateLastName("Smith99"));
        assertTrue(ex.getMessage().contains("alphabets only"));
    }

    // ═══════════════════════════════════════════════════════════════
    // EMAIL
    // ═══════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "valid email: {0}")
    @ValueSource(strings = {
            "abc.xyz@bl.co.in",
            "abc@bl.co",
            "abc@bl.co.in",
            "abc.xyz@bl.co"
    })
    void testEmail_Happy_ValidEmails(String email) {
        // valid emails should not throw any exception
        assertDoesNotThrow(() -> UserValidator.validateEmail(email));
    }

    @ParameterizedTest(name = "null or empty email: [{0}]")
    @NullAndEmptySource
    void testEmail_Sad_NullAndEmpty(String email) {
        // null and empty should throw InvalidEmailException
        assertThrows(InvalidEmailException.class,
                () -> UserValidator.validateEmail(email));
    }

    @ParameterizedTest(name = "invalid email: {0}")
    @ValueSource(strings = {
            "abcbl.co.in",        // missing @
            "abc@@bl.co.in",      // double @
            "abc@bl",             // incomplete domain
            "@bl.co.in",          // empty local part
            "abc.xyz.pqr@bl.co",  // too many local parts
            "abc@bl.co.in.uk",    // too many domain parts
            "abc.@bl.co.in",      // empty segment after dot
            "abc@bl.co.",         // trailing dot in domain
            ".abc@bl.co",         // leading dot in local
            "123@456.78",         // all numeric segments
            "abc @bl.co",         // space in local
            "abc@bl .co"          // space in domain
    })
    void testEmail_Sad_InvalidEmails(String email) {
        // invalid emails should throw InvalidEmailException
        assertThrows(InvalidEmailException.class,
                () -> UserValidator.validateEmail(email));
    }

    @Test
    void testEmail_Sad_ExceptionMessage_MissingAt() {
        // exception message should mention @ for missing @ case
        InvalidEmailException ex = assertThrows(
                InvalidEmailException.class,
                () -> UserValidator.validateEmail("abcbl.co.in"));
        assertTrue(ex.getMessage().contains("@"));
    }

    @Test
    void testEmail_Sad_ExceptionMessage_InvalidLocal() {
        // exception message should mention local part
        InvalidEmailException ex = assertThrows(
                InvalidEmailException.class,
                () -> UserValidator.validateEmail("abc.@bl.co.in"));
        assertTrue(ex.getMessage().contains("local part"));
    }

    @Test
    void testEmail_Sad_ExceptionMessage_InvalidDomain() {
        // exception message should mention domain part
        InvalidEmailException ex = assertThrows(
                InvalidEmailException.class,
                () -> UserValidator.validateEmail("abc@bl"));
        assertTrue(ex.getMessage().contains("domain part"));
    }

    // ═══════════════════════════════════════════════════════════════
    // MOBILE
    // ═══════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "valid mobile: {0}")
    @ValueSource(strings = {
            "91 9919819801",
            "1 9919819801",
            "999 9919819801"
    })
    void testMobile_Happy_ValidMobiles(String mobile) {
        // valid mobiles should not throw any exception
        assertDoesNotThrow(() -> UserValidator.validateMobile(mobile));
    }

    @ParameterizedTest(name = "null or empty mobile: [{0}]")
    @NullAndEmptySource
    void testMobile_Sad_NullAndEmpty(String mobile) {
        // null and empty should throw InvalidMobileException
        assertThrows(InvalidMobileException.class,
                () -> UserValidator.validateMobile(mobile));
    }

    @ParameterizedTest(name = "invalid mobile: {0}")
    @ValueSource(strings = {
            "91 0919819801",    // starts with 0
            "91 99198198",      // too short
            "91 99198198011",   // too long
            "919919819801",     // no space
            "91  9919819801",   // double space
            "ab 9919819801",    // non numeric CC
            "91 991981980a"     // non numeric number
    })
    void testMobile_Sad_InvalidMobiles(String mobile) {
        // invalid mobiles should throw InvalidMobileException
        assertThrows(InvalidMobileException.class,
                () -> UserValidator.validateMobile(mobile));
    }

    @Test
    void testMobile_Sad_ExceptionMessage_StartsWithZero() {
        // exception message should mention starts with 0
        InvalidMobileException ex = assertThrows(
                InvalidMobileException.class,
                () -> UserValidator.validateMobile("91 0919819801"));
        assertTrue(ex.getMessage().contains("start with 0"));
    }

    @Test
    void testMobile_Sad_ExceptionMessage_WrongLength() {
        // exception message should mention 10 digits
        InvalidMobileException ex = assertThrows(
                InvalidMobileException.class,
                () -> UserValidator.validateMobile("91 99198198"));
        assertTrue(ex.getMessage().contains("10 digits"));
    }

    // ═══════════════════════════════════════════════════════════════
    // PASSWORD
    // ═══════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "valid password: {0}")
    @ValueSource(strings = {
            "Hello1@23",
            "HelloWorld1#",
            "@HelloWorld1",
            "HelloWorld1@"
    })
    void testPassword_Happy_ValidPasswords(String password) {
        // valid passwords should not throw any exception
        assertDoesNotThrow(() -> UserValidator.validatePassword(password));
    }

    @Test
    void testPassword_Sad_Null() {
        // null password should throw InvalidPasswordException
        assertThrows(InvalidPasswordException.class,
                () -> UserValidator.validatePassword(null));
    }

    @ParameterizedTest(name = "invalid password: {0}")
    @ValueSource(strings = {
            "He1@",           // Rule 1: too short
            "HelloWorld@",    // Rule 2: no digit
            "hello1@23",      // Rule 3: no uppercase
            "Hello1234",      // Rule 4: no special char
            "Hello1@2#",      // Rule 4: two special chars
            "Hello 1@2"       // Rule 5: has space
    })
    void testPassword_Sad_InvalidPasswords(String password) {
        // invalid passwords should throw InvalidPasswordException
        assertThrows(InvalidPasswordException.class,
                () -> UserValidator.validatePassword(password));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_TooShort() {
        // exception message should mention 8 characters
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("He1@"));
        assertTrue(ex.getMessage().contains("8 characters"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_NoDigit() {
        // exception message should mention digit
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("HelloWorld@"));
        assertTrue(ex.getMessage().contains("digit"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_NoUppercase() {
        // exception message should mention uppercase
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("hello1@23"));
        assertTrue(ex.getMessage().contains("uppercase"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_NoSpecialChar() {
        // exception message should mention special character
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("Hello1234"));
        assertTrue(ex.getMessage().contains("special character"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_HasSpace() {
        // exception message should mention spaces
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("Hello 1@2"));
        assertTrue(ex.getMessage().contains("spaces"));
    }
}