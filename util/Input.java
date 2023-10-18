package util;
import java.util.Scanner;

public class Input {

    private final Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String getString () {
        try {
            String userInput = scanner.nextLine();
            return userInput;
        } catch (Exception e) {
            System.out.println("Input not valid");
            return getString();
        }
    }

    public String getString(String prompt) {
        System.out.println(prompt);
        try {
            String userInput = scanner.nextLine();
            return userInput;
        } catch (Exception e) {
            System.out.println("Input not valid");
            return getString(prompt);
        }
    }

    public boolean yesNo() {
        System.out.println("Would you like to continue?");
        try {
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Input not valid");
            return yesNo();
        }
    }

    public int getInt(int min, int max) {
        System.out.printf("Enter any number between %d and %d.%n", min, max);

        int userInput = scanner.nextInt();

        if (userInput >= min && userInput <= max) {

            System.out.println("That's right");
            return userInput;

        }
        System.out.println("That's not right, try again");
        return getInt(min, max);
    }

    public int getInt() {

        int returnNumber = 0;
        try {
            String string = getString();
            returnNumber = Integer.valueOf(string);
            if(returnNumber < 1 || returnNumber > 5) {
                System.out.println("Enter an option (1, 2, 3, 4 or 5):");
                returnNumber = getInt();//recursion
            }

        } catch (NumberFormatException e) {
            System.out.println("Enter an option (1, 2, 3, 4 or 5):");
            returnNumber = getInt();//recursion
        }

        return returnNumber;
    }

    public double getDouble(double min, double max) {
        System.out.printf("Enter a number between %.2f and %.2f.%n", min, max);

        double userInput = scanner.nextDouble();

        if (userInput >= min && userInput <= max) {

            System.out.println("That's right");
            return userInput;

        }
        System.out.println("That's not right, try again");
        return getDouble(min, max);
    }

    public double getDouble() {

        double returnNumber = 0;
        System.out.println("Enter any decimal number you'd like.");

        try {

            String string2 = getString();
            returnNumber = Double.valueOf(string2);

        } catch (NumberFormatException e) {

            System.out.println("Error");
            e.printStackTrace();
            returnNumber = getDouble();
        }

        return returnNumber;
    }

    public static void main (String[] args) {
        Input user = new Input();
//        user.getString();
//        user.yesNo();
//        user.getInt(1,20);
//        user.getInt();
        user.getDouble();

    }

}
