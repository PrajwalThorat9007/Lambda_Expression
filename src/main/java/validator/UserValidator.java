package validator;

import exception.InvalidFirstNameException;
import exception.InvalidLastNameException;
import exception.InvalidEmailException;
import exception.InvalidMobileException;
import exception.InvalidPasswordException;
import exception.*;
import java.util.stream.Stream;

public class UserValidator {

    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}";

    // ── First Name ───────────────────────────────────────────────────────

    /**
     * Validates first name — alphabets only, not null or empty.
     * @throws InvalidFirstNameException if validation fails
     */
    public static void validateFirstName(String firstName) {
        if (firstName == null || firstName.isBlank())
            throw new InvalidFirstNameException("First name cannot be null or empty");

        boolean valid = firstName.chars()
                .allMatch(Character::isLetter);

        if (!valid)
            throw new InvalidFirstNameException(
                    "First name must contain alphabets only. Got: " + firstName);
    }

    // ── Last Name ────────────────────────────────────────────────────────

    /**
     * Validates last name — alphabets only, not null or empty.
     * @throws InvalidLastNameException if validation fails
     */
    public static void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank())
            throw new InvalidLastNameException("Last name cannot be null or empty");

        boolean valid = lastName.chars()
                .allMatch(Character::isLetter);

        if (!valid)
            throw new InvalidLastNameException(
                    "Last name must contain alphabets only. Got: " + lastName);
    }

    // ── Email ────────────────────────────────────────────────────────────

    /**
     * Validates email format — abc.xyz@bl.co.in
     * @throws InvalidEmailException if validation fails
     */
    public static void validateEmail(String email) {
        if (email == null || email.isBlank())
            throw new InvalidEmailException("Email cannot be null or empty");

        // must have exactly one @
        String[] atParts = email.split("@");
        if (Stream.of(atParts).count() != 2)
            throw new InvalidEmailException(
                    "Email must contain exactly one @. Got: " + email);

        String localPart  = atParts[0];
        String domainPart = atParts[1];

        // validate local part — 1 or 2 segments, atleast one letter each
        String[] localSegments = localPart.split("\\.", -1);
        boolean validLocal = Stream.of(localSegments)
                .allMatch(s -> !s.isBlank()
                        && s.matches("[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*"))
                && localSegments.length >= 1
                && localSegments.length <= 2;

        if (!validLocal)
            throw new InvalidEmailException(
                    "Email local part is invalid. Got: " + localPart);

        // validate domain part — 2 or 3 segments, atleast one letter each
        String[] domainSegments = domainPart.split("\\.", -1);
        boolean validDomain = Stream.of(domainSegments)
                .allMatch(s -> !s.isBlank()
                        && s.matches("[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*"))
                && domainSegments.length >= 2
                && domainSegments.length <= 3;

        if (!validDomain)
            throw new InvalidEmailException(
                    "Email domain part is invalid. Got: " + domainPart);
    }

    // ── Mobile ───────────────────────────────────────────────────────────

    /**
     * Validates mobile format — CC space 10digitnumber
     * @throws InvalidMobileException if validation fails
     */
    public static void validateMobile(String mobile) {
        if (mobile == null || mobile.isBlank())
            throw new InvalidMobileException("Mobile cannot be null or empty");

        // must have exactly one space — split gives exactly 2 parts
        String[] parts = mobile.split(" ");
        if (Stream.of(parts).count() != 2)
            throw new InvalidMobileException(
                    "Mobile must have exactly one space between country code and number. Got: " + mobile);

        String countryCode  = parts[0];
        String mobileNumber = parts[1];

        // validate country code — 1 to 3 digits
        boolean validCC = Stream.of(countryCode)
                .allMatch(s -> s.matches("[0-9]+")
                        && s.length() >= 1
                        && s.length() <= 3);

        if (!validCC)
            throw new InvalidMobileException(
                    "Country code must be 1-3 digits, numeric only. Got: " + countryCode);

        // validate mobile number — exactly 10 digits, not starting with 0
        if (!mobileNumber.matches("[0-9]+") || mobileNumber.length() != 10)
            throw new InvalidMobileException(
                    "Mobile number must be exactly 10 digits. Got: " + mobileNumber);

        if (mobileNumber.charAt(0) == '0')
            throw new InvalidMobileException(
                    "Mobile number cannot start with 0. Got: " + mobileNumber);
    }

    // ── Password ─────────────────────────────────────────────────────────

    /**
     * Validates password — all 5 rules using streams.
     * @throws InvalidPasswordException if validation fails
     */
    public static void validatePassword(String password) {
        if (password == null)
            throw new InvalidPasswordException("Password cannot be null");

        // Rule 1: minimum 8 characters
        boolean validLength = Stream.of(password)
                .allMatch(p -> p.length() >= 8);
        if (!validLength)
            throw new InvalidPasswordException(
                    "Password must be atleast 8 characters. Got length: " + password.length());

        // Rule 2: atleast one digit
        boolean hasDigit = password.chars()
                .anyMatch(Character::isDigit);
        if (!hasDigit)
            throw new InvalidPasswordException(
                    "Password must contain atleast one digit");

        // Rule 3: atleast one uppercase
        boolean hasUpper = password.chars()
                .anyMatch(Character::isUpperCase);
        if (!hasUpper)
            throw new InvalidPasswordException(
                    "Password must contain atleast one uppercase letter");

        // Rule 4: exactly one special character
        long specialCount = password.chars()
                .filter(c -> SPECIAL_CHARS.indexOf(c) >= 0)
                .count();
        if (specialCount == 0)
            throw new InvalidPasswordException(
                    "Password must contain exactly one special character");
        if (specialCount > 1)
            throw new InvalidPasswordException(
                    "Password must not contain more than one special character. Found: " + specialCount);

        // Rule 5: no spaces
        boolean hasSpace = password.chars()
                .anyMatch(c -> c == ' ');
        if (hasSpace)
            throw new InvalidPasswordException(
                    "Password must not contain spaces");
    }
}