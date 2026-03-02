import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

public class UserValidatorTest {

    // ═══════════════════════════════════════════════════════════════
    // FIRST NAME TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    void testFirstName_Happy_ValidName() {
        // valid first name with alphabets only
        assertTrue(isValidName("Alice"));
    }

    @Test
    void testFirstName_Happy_SingleChar() {
        // single character is valid
        assertTrue(isValidName("A"));
    }

    @Test
    void testFirstName_Sad_Empty() {
        // empty first name should fail
        assertFalse(isValidName(""));
    }

    @Test
    void testFirstName_Sad_Null() {
        // null first name should fail
        assertFalse(isValidName(null));
    }

    @Test
    void testFirstName_Sad_WithNumbers() {
        // numbers in first name should fail
        assertFalse(isValidName("Alice123"));
    }

    @Test
    void testFirstName_Sad_WithSpecialChar() {
        // special characters in first name should fail
        assertFalse(isValidName("Alice@"));
    }

    @Test
    void testFirstName_Sad_WithSpace() {
        // space in first name should fail
        assertFalse(isValidName("Ali ce"));
    }

    // ═══════════════════════════════════════════════════════════════
    // LAST NAME TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    void testLastName_Happy_ValidName() {
        // valid last name with alphabets only
        assertTrue(isValidName("Smith"));
    }

    @Test
    void testLastName_Happy_SingleChar() {
        // single character last name is valid
        assertTrue(isValidName("S"));
    }

    @Test
    void testLastName_Sad_Empty() {
        // empty last name should fail
        assertFalse(isValidName(""));
    }

    @Test
    void testLastName_Sad_Null() {
        // null last name should fail
        assertFalse(isValidName(null));
    }

    @Test
    void testLastName_Sad_WithNumbers() {
        // numbers in last name should fail
        assertFalse(isValidName("Smith99"));
    }

    @Test
    void testLastName_Sad_WithSpecialChar() {
        // special characters in last name should fail
        assertFalse(isValidName("Smith#"));
    }

    // ═══════════════════════════════════════════════════════════════
    // EMAIL TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    void testEmail_Happy_FullFormat() {
        // full email with all optional parts abc.xyz@bl.co.in
        assertTrue(isValidEmail("abc.xyz@bl.co.in"));
    }

    @Test
    void testEmail_Happy_NoOptionalParts() {
        // email without optional parts abc@bl.co
        assertTrue(isValidEmail("abc@bl.co"));
    }

    @Test
    void testEmail_Happy_NoSubdomain() {
        // email without xyz part abc@bl.co.in
        assertTrue(isValidEmail("abc@bl.co.in"));
    }

    @Test
    void testEmail_Happy_NoExtension() {
        // email without in part abc.xyz@bl.co
        assertTrue(isValidEmail("abc.xyz@bl.co"));
    }

    @Test
    void testEmail_Sad_NoAtSign() {
        // missing @ should fail
        assertFalse(isValidEmail("abcbl.co.in"));
    }

    @Test
    void testEmail_Sad_DoubleAt() {
        // double @ should fail
        assertFalse(isValidEmail("abc@@bl.co.in"));
    }

    @Test
    void testEmail_Sad_MissingDomain() {
        // domain has only one part after @ should fail
        assertFalse(isValidEmail("abc@bl"));
    }

    @Test
    void testEmail_Sad_EmptyLocalPart() {
        // empty local part should fail
        assertFalse(isValidEmail("@bl.co.in"));
    }

    @Test
    void testEmail_Sad_TooManyLocalParts() {
        // local part has more than 2 segments should fail
        assertFalse(isValidEmail("abc.xyz.pqr@bl.co.in"));
    }

    @Test
    void testEmail_Sad_TooManyDomainParts() {
        // domain has more than 3 segments should fail
        assertFalse(isValidEmail("abc@bl.co.in.uk"));
    }

    @Test
    void testEmail_Sad_EmptySegment() {
        // empty segment after dot should fail
        assertFalse(isValidEmail("abc.@bl.co.in"));
    }

    @Test
    void testEmail_Sad_Empty() {
        // empty email should fail
        assertFalse(isValidEmail(""));
    }

    @Test
    void testEmail_Sad_Null() {
        // null email should fail
        assertFalse(isValidEmail(null));
    }

    // ═══════════════════════════════════════════════════════════════
    // MOBILE TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    void testMobile_Happy_TwoDigitCC() {
        // valid mobile with 2 digit country code
        assertTrue(isValidMobile("91 9919819801"));
    }

    @Test
    void testMobile_Happy_OneDigitCC() {
        // valid mobile with 1 digit country code
        assertTrue(isValidMobile("1 9919819801"));
    }

    @Test
    void testMobile_Happy_ThreeDigitCC() {
        // valid mobile with 3 digit country code
        assertTrue(isValidMobile("999 9919819801"));
    }

    @Test
    void testMobile_Sad_StartsWithZero() {
        // mobile number starting with 0 should fail
        assertFalse(isValidMobile("91 0919819801"));
    }

    @Test
    void testMobile_Sad_TooShort() {
        // mobile number less than 10 digits should fail
        assertFalse(isValidMobile("91 99198198"));
    }

    @Test
    void testMobile_Sad_TooLong() {
        // mobile number more than 10 digits should fail
        assertFalse(isValidMobile("91 99198198011"));
    }

    @Test
    void testMobile_Sad_NoSpace() {
        // missing space between CC and number should fail
        assertFalse(isValidMobile("919919819801"));
    }

    @Test
    void testMobile_Sad_DoubleSpace() {
        // double space should fail
        assertFalse(isValidMobile("91  9919819801"));
    }

    @Test
    void testMobile_Sad_NonNumericCC() {
        // non numeric country code should fail
        assertFalse(isValidMobile("ab 9919819801"));
    }

    @Test
    void testMobile_Sad_NonNumericNumber() {
        // non numeric mobile number should fail
        assertFalse(isValidMobile("91 991981980a"));
    }

    @Test
    void testMobile_Sad_Empty() {
        // empty input should fail
        assertFalse(isValidMobile(""));
    }

    @Test
    void testMobile_Sad_Null() {
        // null input should fail
        assertFalse(isValidMobile(null));
    }

    // ═══════════════════════════════════════════════════════════════
    // PASSWORD TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    void testPassword_Happy_AllRulesPass() {
        // valid password satisfying all 5 rules
        assertTrue(isValidPassword("Hello1@23"));
    }

    @Test
    void testPassword_Happy_DifferentSpecialChar() {
        // valid password with different special character
        assertTrue(isValidPassword("HelloWorld1#"));
    }

    @Test
    void testPassword_Happy_SpecialCharAtStart() {
        // valid password with special char at start
        assertTrue(isValidPassword("@HelloWorld1"));
    }

    @Test
    void testPassword_Happy_SpecialCharAtEnd() {
        // valid password with special char at end
        assertTrue(isValidPassword("HelloWorld1@"));
    }

    @Test
    void testPassword_Sad_Rule1_TooShort() {
        // less than 8 characters should fail Rule 1
        assertFalse(isValidPassword("He1@"));
    }

    @Test
    void testPassword_Sad_Rule1_Empty() {
        // empty password should fail Rule 1
        assertFalse(isValidPassword(""));
    }

    @Test
    void testPassword_Sad_Rule2_NoDigit() {
        // no digit should fail Rule 2
        assertFalse(isValidPassword("HelloWorld@"));
    }

    @Test
    void testPassword_Sad_Rule3_NoUppercase() {
        // no uppercase should fail Rule 3
        assertFalse(isValidPassword("hello1@23"));
    }

    @Test
    void testPassword_Sad_Rule4_NoSpecialChar() {
        // no special character should fail Rule 4
        assertFalse(isValidPassword("Hello1234"));
    }

    @Test
    void testPassword_Sad_Rule4_TwoSpecialChars() {
        // two special characters should fail Rule 4
        assertFalse(isValidPassword("Hello1@2#"));
    }

    @Test
    void testPassword_Sad_Rule5_HasSpace() {
        // space in password should fail Rule 5
        assertFalse(isValidPassword("Hello 1@2"));
    }

    @Test
    void testPassword_Sad_Null() {
        // null password should fail
        assertFalse(isValidPassword(null));
    }

    // ═══════════════════════════════════════════════════════════════
    // VALIDATOR METHODS USING STREAMS AND LAMBDAS
    // same logic from our previous validator classes
    // ═══════════════════════════════════════════════════════════════

    // validates first and last name using stream + lambda
    private boolean isValidName(String name) {
        if (name == null || name.isBlank()) return false;

        // stream over each character, allMatch checks every char is a letter
        return name.chars()
                .allMatch(Character::isLetter);
    }

    // validates email format abc.xyz@bl.co.in
    private boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;

        // split by @ — must give exactly 2 parts
        String[] atParts = email.split("@");

        boolean validAtSplit = Stream.of(atParts).count() == 2;
        if (!validAtSplit) return false;

        String localPart  = atParts[0];
        String domainPart = atParts[1];

        // local part — 1 or 2 segments, all alphanumeric
        String[] localSegments = localPart.split("\\.",-1);
        boolean validLocal = Stream.of(localSegments)
                .allMatch(s -> !s.isBlank() && s.matches("[a-zA-Z0-9]+"))
                && localSegments.length >= 1
                && localSegments.length <= 2;

        if (!validLocal) return false;

        // domain part — 2 or 3 segments, all alphanumeric
        String[] domainSegments = domainPart.split("\\.",-1);
        return Stream.of(domainSegments)
                .allMatch(s -> !s.isBlank() && s.matches("[a-zA-Z0-9]+"))
                && domainSegments.length >= 2
                && domainSegments.length <= 3;
    }

    // validates mobile format — CC space 10digitnumber
    private boolean isValidMobile(String input) {
        if (input == null || input.isBlank()) return false;

        // split by space — must give exactly 2 parts
        String[] parts = input.split(" ");

        boolean validSplit = Stream.of(parts).count() == 2;
        if (!validSplit) return false;

        String countryCode  = parts[0];
        String mobileNumber = parts[1];

        // country code — 1 to 3 digits, numeric only
        boolean validCC = Stream.of(countryCode)
                .allMatch(s -> s.matches("[0-9]+")
                        && s.length() >= 1
                        && s.length() <= 3);

        if (!validCC) return false;

        // mobile number — exactly 10 digits, not starting with 0
        return Stream.of(mobileNumber)
                .allMatch(s -> s.matches("[0-9]+")
                        && s.length() == 10
                        && s.charAt(0) != '0');
    }

    // validates password — all 5 rules using streams
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}";

    private boolean isValidPassword(String password) {
        if (password == null) return false;

        // Rule 1: minimum 8 characters
        boolean validLength = Stream.of(password)
                .allMatch(p -> p.length() >= 8);
        if (!validLength) return false;

        // Rule 2: atleast one digit
        boolean hasDigit = password.chars()
                .anyMatch(Character::isDigit);
        if (!hasDigit) return false;

        // Rule 3: atleast one uppercase
        boolean hasUpper = password.chars()
                .anyMatch(Character::isUpperCase);
        if (!hasUpper) return false;

        // Rule 4: exactly one special character
        long specialCount = password.chars()
                .filter(c -> SPECIAL_CHARS.indexOf(c) >= 0)
                .count();
        if (specialCount != 1) return false;

        // Rule 5: no spaces
        return password.chars()
                .noneMatch(c -> c == ' ');
    }
}