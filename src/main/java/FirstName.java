import java.util.Scanner;
import java.util.function.Predicate;

public class FirstName {

    public static void main(String[] args) {
        Predicate<String> firstLetter=(name)->Character.isUpperCase(name.charAt(0));
        Predicate<String> namelength=(name)->name.length()>=3;
        Predicate<String> validLastName=firstLetter.and(namelength);

        Scanner sc=new Scanner(System.in);
        String lastName=sc.nextLine();

        if(validLastName.test(lastName)){
            System.out.println("Name is valid");
        }else{
            System.out.println("Name is not valid");
        }
    }

}
