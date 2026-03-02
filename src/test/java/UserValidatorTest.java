import exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import validator.UserValidator;

public class UserValidatorTest {

    // ═══════════════════════════════════════════════════════════════
    // FIRST NAME
    // ═══════════════════════════════════════════════════════════════

    @ParameterizedTest(name = "valid first name: {0}")
    @ValueSource(strings = {"Alice", "Bob", "A"})
    void testFirstName_Happy_ValidNames(String name) {
        assertDoesNotThrow(() -> UserValidator.validateFirstName(name));
    }

    @ParameterizedTest(name = "null or empty first name: [{0}]")
    @NullAndEmptySource
    void testFirstName_Sad_NullAndEmpty(String name) {
        assertThrows(InvalidFirstNameException.class,
                () -> UserValidator.validateFirstName(name));
    }

    @ParameterizedTest(name = "invalid first name: {0}")
    @ValueSource(strings = {"Alice123", "Alice@", "Ali ce", "123"})
    void testFirstName_Sad_InvalidNames(String name) {
        assertThrows(InvalidFirstNameException.class,
                () -> UserValidator.validateFirstName(name));
    }

    @Test
    void testFirstName_Sad_ExceptionMessage() {
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
        assertDoesNotThrow(() -> UserValidator.validateLastName(name));
    }

    @ParameterizedTest(name = "null or empty last name: [{0}]")
    @NullAndEmptySource
    void testLastName_Sad_NullAndEmpty(String name) {
        assertThrows(InvalidLastNameException.class,
                () -> UserValidator.validateLastName(name));
    }

    @ParameterizedTest(name = "invalid last name: {0}")
    @ValueSource(strings = {"Smith99", "Smith#", "Smi th"})
    void testLastName_Sad_InvalidNames(String name) {
        assertThrows(InvalidLastNameException.class,
                () -> UserValidator.validateLastName(name));
    }

    @Test
    void testLastName_Sad_ExceptionMessage() {
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
        assertDoesNotThrow(() -> UserValidator.validateEmail(email));
    }

    @ParameterizedTest(name = "null or empty email: [{0}]")
    @NullAndEmptySource
    void testEmail_Sad_NullAndEmpty(String email) {
        assertThrows(InvalidEmailException.class,
                () -> UserValidator.validateEmail(email));
    }

    @ParameterizedTest(name = "invalid email: {0}")
    @ValueSource(strings = {
            "abcbl.co.in",
            "abc@@bl.co.in",
            "abc@bl",
            "@bl.co.in",
            "abc.xyz.pqr@bl.co",
            "abc@bl.co.in.uk",
            "abc.@bl.co.in",
            "abc@bl.co.",
            ".abc@bl.co",
            "123@456.78",
            "abc @bl.co",
            "abc@bl .co"
    })
    void testEmail_Sad_InvalidEmails(String email) {
        assertThrows(InvalidEmailException.class,
                () -> UserValidator.validateEmail(email));
    }

    @Test
    void testEmail_Sad_ExceptionMessage_MissingAt() {
        InvalidEmailException ex = assertThrows(
                InvalidEmailException.class,
                () -> UserValidator.validateEmail("abcbl.co.in"));
        assertTrue(ex.getMessage().contains("@"));
    }

    @Test
    void testEmail_Sad_ExceptionMessage_InvalidLocal() {
        InvalidEmailException ex = assertThrows(
                InvalidEmailException.class,
                () -> UserValidator.validateEmail("abc.@bl.co.in"));
        assertTrue(ex.getMessage().contains("local part"));
    }

    @Test
    void testEmail_Sad_ExceptionMessage_InvalidDomain() {
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
        assertDoesNotThrow(() -> UserValidator.validateMobile(mobile));
    }

    @ParameterizedTest(name = "null or empty mobile: [{0}]")
    @NullAndEmptySource
    void testMobile_Sad_NullAndEmpty(String mobile) {
        assertThrows(InvalidMobileException.class,
                () -> UserValidator.validateMobile(mobile));
    }

    @ParameterizedTest(name = "invalid mobile: {0}")
    @ValueSource(strings = {
            "91 0919819801",
            "91 99198198",
            "91 99198198011",
            "919919819801",
            "91  9919819801",
            "ab 9919819801",
            "91 991981980a"
    })
    void testMobile_Sad_InvalidMobiles(String mobile) {
        assertThrows(InvalidMobileException.class,
                () -> UserValidator.validateMobile(mobile));
    }

    @Test
    void testMobile_Sad_ExceptionMessage_StartsWithZero() {
        InvalidMobileException ex = assertThrows(
                InvalidMobileException.class,
                () -> UserValidator.validateMobile("91 0919819801"));
        assertTrue(ex.getMessage().contains("not starting with 0"));
    }

    @Test
    void testMobile_Sad_ExceptionMessage_WrongLength() {
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
        assertDoesNotThrow(() -> UserValidator.validatePassword(password));
    }

    @Test
    void testPassword_Sad_Null() {
        assertThrows(InvalidPasswordException.class,
                () -> UserValidator.validatePassword(null));
    }

    @ParameterizedTest(name = "invalid password: {0}")
    @ValueSource(strings = {
            "He1@",
            "HelloWorld@",
            "hello1@23",
            "Hello1234",
            "Hello1@2#",
            "Hello 1@2"
    })
    void testPassword_Sad_InvalidPasswords(String password) {
        assertThrows(InvalidPasswordException.class,
                () -> UserValidator.validatePassword(password));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_TooShort() {
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("He1@"));
        assertTrue(ex.getMessage().contains("8 characters"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_NoDigit() {
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("HelloWorld@"));
        assertTrue(ex.getMessage().contains("digit"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_NoUppercase() {
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("hello1@23"));
        assertTrue(ex.getMessage().contains("uppercase"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_NoSpecialChar() {
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("Hello1234"));
        assertTrue(ex.getMessage().contains("special character"));
    }

    @Test
    void testPassword_Sad_ExceptionMessage_HasSpace() {
        InvalidPasswordException ex = assertThrows(
                InvalidPasswordException.class,
                () -> UserValidator.validatePassword("Hello 1@2"));
        assertTrue(ex.getMessage().contains("spaces"));
    }
}