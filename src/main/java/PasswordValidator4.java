import java.util.Scanner;
import java.util.stream.Stream;

public class PasswordValidator4 {

    static String specialChars = "!@#$%^&*()_+-=[]{}";

    public static boolean isValidPassword(String password) {

        if (password == null) return false;

        // Rule 1: Minimum 8 characters
        boolean validLength = Stream.of(password)
                .allMatch(p -> p.length() >= 8);

        if (!validLength) {
            System.out.println("Rule 1 Failed - must be atleast 8 characters");
            return false;
        }
        System.out.println("Rule 1 Passed");

        // Rule 2: Must contain atleast one numeric digit
        boolean hasDigit = password.chars()
                .anyMatch(Character::isDigit);

        if (!hasDigit) {
            System.out.println("Rule 2 Failed - must contain atleast one number");
            return false;
        }
        System.out.println("Rule 2 Passed");

        // Rule 3: Must contain atleast one uppercase character
        boolean hasUpper = password.chars()
                .anyMatch(Character::isUpperCase);

        if (!hasUpper) {
            System.out.println("Rule 3 Failed - must contain atleast one uppercase");
            return false;
        }
        System.out.println("Rule 3 Passed");

        // Rule 4: Must contain exactly one special character
        long specialCount = password.chars()
                .filter(c -> specialChars.indexOf(c) >= 0)
                .count();

        if (specialCount == 0) {
            System.out.println("Rule 4 Failed - must contain one special character");
            return false;
        }
        if (specialCount > 1) {
            System.out.println("Rule 4 Failed - must not contain more than one special character");
            return false;
        }
        System.out.println("Rule 4 Passed");

        // Rule 5: Must not contain spaces
        boolean noSpaces = password.chars()
                .noneMatch(c -> c == ' ');

        if (!noSpaces) {
            System.out.println("Rule 5 Failed - must not contain spaces");
            return false;
        }
        System.out.println("Rule 5 Passed");

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String input = sc.nextLine();

        System.out.println("\n--- Validating: '" + input + "' ---\n");

        if (isValidPassword(input)) {
            System.out.println("\nValid Password!");
        } else {
            System.out.println("\nInvalid Password!");
        }
    }
}