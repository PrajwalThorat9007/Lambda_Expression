import java.util.Scanner;
import java.util.stream.Stream;

public class PasswordValidator {

    public static boolean isValidPassword(String password) {

        // Basic null/empty check
        if (password == null) return false;

        // ── Rule 1: Minimum 8 characters ────────────────────────────────
        boolean validLength = Stream.of(password)
                .peek(p -> System.out.println("  password length: " + p.length()))
                .allMatch(p -> p.length() >= 8);

        return validLength;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String input = sc.nextLine();

        System.out.println("\n--- Validating: " + input + " ---");

        if (isValidPassword(input)) {
            System.out.println(" Valid Password!");
        } else {
            System.out.println("Invalid Password! Must be at least 8 characters.");
        }
    }
}