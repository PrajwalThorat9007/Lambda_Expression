import java.util.Scanner;
import java.util.stream.Stream;

public class PasswordValidator2 {

    public static boolean isValidPassword(String password) {

        // Basic null/empty check
        if (password == null) return false;

        // ── Rule 1: Minimum 8 characters ────────────────────────────────
        boolean validLength = Stream.of(password)
                .peek(p -> System.out.println("  length check   : " + p.length() + " chars"))
                .allMatch(p -> p.length() >= 8);

        if (!validLength) {
            System.out.println("  ❌ Rule 1 Failed - must be atleast 8 characters");
            return false;
        }
        System.out.println("  ✅ Rule 1 Passed");

        // ── Rule 2: Must contain atleast one numeric digit ───────────────
        // split password into individual characters
        // check if atleast one character is a digit

        boolean hasDigit = password.chars()              // IntStream of char values
                .peek(c -> System.out.println("  checking char  : " + (char) c))
                .anyMatch(c -> c >= '0' && c <= '9');    // is it a digit?

        if (!hasDigit) {
            System.out.println("  ❌ Rule 2 Failed - must contain atleast one number");
            return false;
        }
        System.out.println("  ✅ Rule 2 Passed");

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String input = sc.nextLine();

        System.out.println("\n--- Validating: '" + input + "' ---");

        if (isValidPassword(input)) {
            System.out.println("\n✅ Valid Password!");
        } else {
            System.out.println("\n❌ Invalid Password!");
        }
    }
}