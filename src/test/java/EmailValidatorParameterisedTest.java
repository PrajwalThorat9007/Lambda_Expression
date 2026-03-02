import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorParameterisedTest {

    // ── Happy Test Cases ─────────────────────────────────────────────────

    @ParameterizedTest(name = "valid email: {0}")
    @ValueSource(strings = {
            "abc.xyz@bl.co.in",   // full format with all parts
            "abc@bl.co",          // minimal format — no optional parts
            "abc@bl.co.in",       // no xyz part
            "abc.xyz@bl.co",      // no in part
    })
    void testEmail_Happy_ValidEmails(String email) {
        // all above emails should pass validation
        assertTrue(isValidEmail(email));
    }

    // ── Sad Test Cases — Invalid Format ──────────────────────────────────

    @ParameterizedTest(name = "invalid email: {0}")
    @ValueSource(strings = {
            "abcbl.co.in",        // missing @
            "abc@@bl.co.in",      // double @
            "abc@bl",             // domain has only one part
            "@bl.co.in",          // empty local part
            "abc.xyz.pqr@bl.co",  // local has 3 parts
            "abc@bl.co.in.uk",    // domain has 4 parts
            "abc.@bl.co.in",      // empty segment after dot in local
            "abc@.bl.co.in",      // empty segment at start of domain
            "abc@bl..co",         // double dot in domain
            "abc@bl.co.",         // trailing dot in domain
            ".abc@bl.co",         // leading dot in local
            "abc @bl.co",         // space in local part
            "abc@bl .co",         // space in domain part
            "a b c@bl.co",        // multiple spaces in local
            "abc@bl.c o.in",      // space in domain extension
            "123@456.78",         // all numeric — still valid structure
            "abc@bl.co.in.extra", // too many domain parts
    })
    void testEmail_Sad_InvalidEmails(String email) {
        // all above emails should fail validation
        assertFalse(isValidEmail(email));
    }

    // ── Sad Test Cases — Null and Empty ──────────────────────────────────

    @ParameterizedTest(name = "null or empty email: [{0}]")
    @NullAndEmptySource
    void testEmail_Sad_NullAndEmpty(String email) {
        // null and empty should fail
        assertFalse(isValidEmail(email));
    }

    // ── Happy and Sad together using CsvSource ────────────────────────────
    // CsvSource lets us pass both input and expected result in one test
    // format: "input, expectedResult"

    @ParameterizedTest(name = "email: {0} → expected: {1}")
    @CsvSource({
            // input                  expected
            "abc.xyz@bl.co.in,        true",   // full format
            "abc@bl.co,               true",   // minimal format
            "abc@bl.co.in,            true",   // no xyz
            "abc.xyz@bl.co,           true",   // no in
            "abcbl.co.in,             false",  // missing @
            "abc@@bl.co.in,           false",  // double @
            "abc@bl,                  false",  // incomplete domain
            "@bl.co.in,               false",  // empty local
            "abc.xyz.pqr@bl.co,       false",  // too many local parts
            "abc@bl.co.in.uk,         false",  // too many domain parts
            "abc.@bl.co.in,           false",  // empty segment after dot
            "abc@bl.co.,              false",  // trailing dot
    })
    void testEmail_CsvSource_HappyAndSad(String email, boolean expected) {
        // trims whitespace from csv values
        assertEquals(expected, isValidEmail(email.trim()));
    }

    // ── validator method using streams ───────────────────────────────────

    private boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;

        // must have exactly one @ — split gives exactly 2 parts
        String[] atParts = email.split("@");
        boolean validAtSplit = Stream.of(atParts).count() == 2;
        if (!validAtSplit) return false;

        String localPart  = atParts[0];
        String domainPart = atParts[1];

        // use -1 to keep trailing empty strings like "abc." → ["abc", ""]
        String[] localSegments = localPart.split("\\.", -1);
        boolean validLocal = Stream.of(localSegments)
                .allMatch(s -> !s.isBlank() && s.matches("[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*"))
                && localSegments.length >= 1
                && localSegments.length <= 2;

        if (!validLocal) return false;

        // use -1 here too — catches ".co.in" → ["", "co", "in"]
        String[] domainSegments = domainPart.split("\\.", -1);
        return Stream.of(domainSegments)
                .allMatch(s -> !s.isBlank() && s.matches("[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*"))
                && domainSegments.length >= 2
                && domainSegments.length <= 3;
    }
}