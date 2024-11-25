// TEMP FILE,, code to be transferred  to MovieTheatreApp
import java.util.Scanner;

public class TicketUI {

    public static void purchaseTicket() {

    }

    public static void cancelTicket() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exitProgram = false;
        while (!exitProgram) {
            System.out.println("Would you like to:");
            System.out.println("1. Buy a ticket");
            System.out.println("2. Cancel a ticket");
            System.out.println("3. Exit");

            String userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    purchaseTicket();
                    break;
                case "2":
                    cancelTicket();
                    break;
                case "3":
                    System.out.println("Exiting the program...");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        }

        scanner.close();
    }
}
