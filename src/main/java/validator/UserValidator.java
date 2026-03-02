package validator;

import exception.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class UserValidator {

    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}";

    // ═══════════════════════════════════════════════════════════════
    // LAMBDAS — each rule is a Predicate lambda
    // Predicate<String> takes a String and returns true/false
    // ═══════════════════════════════════════════════════════════════

    // ── First Name Lambdas ───────────────────────────────────────────────

    // checks name is not null or blank
    private static final Predicate<String> isNotNullOrBlank =
            value -> value != null && !value.isBlank();

    // checks every character is a letter
    private static final Predicate<String> hasOnlyLetters =
            value -> value.chars()
                    .allMatch(Character::isLetter);

    // ── Email Lambdas ────────────────────────────────────────────────────

    // checks exactly one @ exists
    private static final Predicate<String> hasExactlyOneAt =
            value -> Stream.of(value.split("@")).count() == 2;

    // checks local part — 1 to 2 segments, each has atleast one letter
    private static final Predicate<String> hasValidLocalPart =
            value -> {
                String localPart       = value.split("@")[0];
                String[] localSegments = localPart.split("\\.", -1);
                return Stream.of(localSegments)
                        .allMatch(s -> !s.isBlank()
                                && s.matches("[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*"))
                        && localSegments.length >= 1
                        && localSegments.length <= 2;
            };

    // checks domain part — 2 to 3 segments, each has atleast one letter
    private static final Predicate<String> hasValidDomainPart =
            value -> {
                String domainPart       = value.split("@")[1];
                String[] domainSegments = domainPart.split("\\.", -1);
                return Stream.of(domainSegments)
                        .allMatch(s -> !s.isBlank()
                                && s.matches("[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*"))
                        && domainSegments.length >= 2
                        && domainSegments.length <= 3;
            };

    // ── Mobile Lambdas ───────────────────────────────────────────────────

    // checks exactly one space exists
    private static final Predicate<String> hasExactlyOneSpace =
            value -> Stream.of(value.split(" ")).count() == 2;

    // checks country code — 1 to 3 digits, numeric only
    private static final Predicate<String> hasValidCountryCode =
            value -> {
                String cc = value.split(" ")[0];
                return cc.matches("[0-9]+")
                        && cc.length() >= 1
                        && cc.length() <= 3;
            };

    // checks mobile number — exactly 10 digits, numeric, not starting with 0
    private static final Predicate<String> hasValidMobileNumber =
            value -> {
                String number = value.split(" ")[1];
                return number.matches("[0-9]+")
                        && number.length() == 10
                        && number.charAt(0) != '0';
            };

    // ── Password Lambdas ─────────────────────────────────────────────────

    // Rule 1: minimum 8 characters
    private static final Predicate<String> hasMinLength =
            value -> value.length() >= 8;

    // Rule 2: atleast one digit
    private static final Predicate<String> hasDigit =
            value -> value.chars()
                    .anyMatch(Character::isDigit);

    // Rule 3: atleast one uppercase letter
    private static final Predicate<String> hasUpperCase =
            value -> value.chars()
                    .anyMatch(Character::isUpperCase);

    // Rule 4: exactly one special character
    private static final Predicate<String> hasExactlyOneSpecialChar =
            value -> value.chars()
                    .filter(c -> SPECIAL_CHARS.indexOf(c) >= 0)
                    .count() == 1;

    // Rule 5: no spaces
    private static final Predicate<String> hasNoSpaces =
            value -> value.chars()
                    .noneMatch(c -> c == ' ');

    // ═══════════════════════════════════════════════════════════════
    // VALIDATOR METHODS — call lambdas and throw exception if fails
    // ═══════════════════════════════════════════════════════════════

    /**
     * Validates first name using lambda predicates.
     * @throws InvalidFirstNameException if validation fails
     */
    public static void validateFirstName(String firstName) {
        if (!isNotNullOrBlank.test(firstName))
            throw new InvalidFirstNameException(
                    "First name cannot be null or empty");

        if (!hasOnlyLetters.test(firstName))
            throw new InvalidFirstNameException(
                    "First name must contain alphabets only. Got: " + firstName);
    }

    /**
     * Validates last name using lambda predicates.
     * @throws InvalidLastNameException if validation fails
     */
    public static void validateLastName(String lastName) {
        if (!isNotNullOrBlank.test(lastName))
            throw new InvalidLastNameException(
                    "Last name cannot be null or empty");

        if (!hasOnlyLetters.test(lastName))
            throw new InvalidLastNameException(
                    "Last name must contain alphabets only. Got: " + lastName);
    }

    /**
     * Validates email using lambda predicates.
     * @throws InvalidEmailException if validation fails
     */
    public static void validateEmail(String email) {
        if (!isNotNullOrBlank.test(email))
            throw new InvalidEmailException(
                    "Email cannot be null or empty");

        if (!hasExactlyOneAt.test(email))
            throw new InvalidEmailException(
                    "Email must contain exactly one @. Got: " + email);

        if (!hasValidLocalPart.test(email))
            throw new InvalidEmailException(
                    "Email local part is invalid. Got: " + email.split("@")[0]);

        if (!hasValidDomainPart.test(email))
            throw new InvalidEmailException(
                    "Email domain part is invalid. Got: " + email.split("@")[1]);
    }

    /**
     * Validates mobile using lambda predicates.
     * @throws InvalidMobileException if validation fails
     */
    public static void validateMobile(String mobile) {
        if (!isNotNullOrBlank.test(mobile))
            throw new InvalidMobileException(
                    "Mobile cannot be null or empty");

        if (!hasExactlyOneSpace.test(mobile))
            throw new InvalidMobileException(
                    "Mobile must have exactly one space between country code and number. Got: " + mobile);

        if (!hasValidCountryCode.test(mobile))
            throw new InvalidMobileException(
                    "Country code must be 1-3 digits, numeric only. Got: " + mobile.split(" ")[0]);

        if (!hasValidMobileNumber.test(mobile))
            throw new InvalidMobileException(
                    "Mobile number must be exactly 10 digits, not starting with 0. Got: " + mobile.split(" ")[1]);
    }

    /**
     * Validates password using lambda predicates.
     * @throws InvalidPasswordException if validation fails
     */
    public static void validatePassword(String password) {
        if (password == null)
            throw new InvalidPasswordException(
                    "Password cannot be null");

        if (!hasMinLength.test(password))
            throw new InvalidPasswordException(
                    "Password must be atleast 8 characters. Got length: " + password.length());

        if (!hasDigit.test(password))
            throw new InvalidPasswordException(
                    "Password must contain atleast one digit");

        if (!hasUpperCase.test(password))
            throw new InvalidPasswordException(
                    "Password must contain atleast one uppercase letter");

        if (!hasExactlyOneSpecialChar.test(password))
            throw new InvalidPasswordException(
                    "Password must contain exactly one special character");

        if (!hasNoSpaces.test(password))
            throw new InvalidPasswordException(
                    "Password must not contain spaces");
    }
}