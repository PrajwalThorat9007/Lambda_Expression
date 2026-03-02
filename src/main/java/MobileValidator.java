import java.util.Scanner;
import java.util.stream.Stream;

public class MobileValidator {

    public static boolean isValidMobile(String input){
        if(input==null || input.isBlank()) return false;

        String[] parts=input.split(" ");

        boolean validSplit= Stream.of(parts)
                .peek(s-> System.out.println("Part: "+s))
                .count()==2;

        if (!validSplit) return false;

        String countryCode=parts[0];
        String mobileNumber=parts[1];

        boolean validCountryCode=Stream.of(countryCode)
                .peek(s-> System.out.println("county code: "+s))
                .allMatch(s->!s.isBlank()&& s.matches("[0-9]+") && s.length()>=1 && s.length()<=3);

        if(!validCountryCode) return false;

        boolean validMobile = Stream.of(mobileNumber)
                .peek(s -> System.out.println("  mobile number : " + s))
                .allMatch(s -> !s.isBlank()
                        && s.matches("[0-9]+")
                        && s.length() == 10
                        && s.charAt(0) != '0');

        return validMobile;

    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter mobile number: ");
        String input = sc.nextLine();

        System.out.println("\n--- Validating: " + input + " ---");

        if (isValidMobile(input)) {
            System.out.println(" Valid Mobile Number!");
        } else {
            System.out.println(" Invalid Mobile Number!");
        }
    }
}
