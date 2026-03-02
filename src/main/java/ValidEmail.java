import java.util.Scanner;
import java.util.stream.Stream;

public class ValidEmail {
    public static boolean isValidEmail(String email){
        if(email==null||email.isBlank()) return false;

        String[] atParts=email.split("@");

        boolean validAtSplit=Stream.of(atParts)
                .count()==2;

        if(!validAtSplit) return false;

        String localPart=atParts[0];
        String domainPart=atParts[1];

        String[] localSegments=localPart.split("\\.");

        boolean validLocal= Stream.of(localSegments)
                .peek(s-> System.out.println("local segment: "+s))
                .allMatch(s->!s.isBlank() && s.matches("[a-zA-Z0-9]+"))
                && localSegments.length >=1
                && localSegments.length <=2;

        if (!validLocal) return false;

        String[] domainSegments = domainPart.split("\\.");

        boolean validDomain=Stream.of(domainSegments)
                .peek(s-> System.out.println(" domain segement: "+ s))
                .allMatch(s->!s.isBlank() && s.matches("[a-zA-z0-9]+"))
                && domainSegments.length >=2
                && domainSegments.length <=3;

        return validDomain;

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email: ");
        String input = sc.nextLine();

        System.out.println("\n--- Validating: " + input + " ---");

        if (isValidEmail(input)) {
            System.out.println("\n✅ Valid Email!");
        } else {
            System.out.println("\n❌ Invalid Email!");
        }
    }


}
