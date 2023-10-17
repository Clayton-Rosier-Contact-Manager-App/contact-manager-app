import util.Input;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Application {

    public static boolean findMatch(List<String> list, String nameToFind) {
        for (int i = 0; i < list.size(); i++) {
            String[] name = list.get(i).split(Pattern.quote(" | "));
            if (name[0].equalsIgnoreCase(nameToFind)) {
                return true;
            }
        }
        return false;
    }

    public static String returnMatch(List<String> list, String nameToFind) {
        String match = new String();
        for (int i = 0; i < list.size(); i++) {
            String[] name = list.get(i).split(Pattern.quote(" | "));
            if (name[0].equalsIgnoreCase(nameToFind)) {
                match = list.get(i);
            }
        }
        return match;
    }

    public static void numberFormatter(List<String> phoneNumber) {
        for (int i = 0; i < phoneNumber.size(); i++) {
            String[] name = phoneNumber.get(i).split(Pattern.quote(" | "));
            String number = name[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
            System.out.printf("%15s | %s%n", name[0], number);

        }
    }

    public static void numberFormatter(String number) {
        String[] name = number.split(Pattern.quote(" | "));
        String reformatted = name[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
        System.out.printf("%s | %s%n%n", name[0], reformatted);
    }

    public static void main(String[] args) throws IOException {

        String contactDirectory = "./data";

        Path contactList = Paths.get(contactDirectory);

        if (Files.notExists(contactList)) {
            Files.createDirectory(contactList);
        }

        String contact = "contacts.txt";

        Path contactsFile = Paths.get(contactDirectory, contact);

        if (Files.notExists(contactsFile)) {
            Files.createFile(contactsFile);
        }

        ArrayList<String> contactInfo = new ArrayList<>();


        String options = "1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):";

        System.out.println(options);


        boolean dontExit = true;
        List<String> listOfContacts = Files.readAllLines(contactsFile);

        do {

            //declares a new Input obj
            Input userInput = new Input();

            int userSelection = userInput.getInt();

            String nameToDelete;
            switch (userSelection) {
                case 1:
                    //view all contacts
                    System.out.printf("%15s | Phone number\n" +
                            "%10s--------------------%n", "Name", "-");
//                    System.out.println(Files.readString(contactsFile));
                    listOfContacts = Files.readAllLines(contactsFile);
//                    System.out.println(listOfContacts);
                    numberFormatter(listOfContacts);
                    System.out.println("\n" + options);
                    break;
                case 2:
                    //adds a new contact
                    String newContactName = userInput.getString("Enter a name: ");
                    String newContactNumber = userInput.getString("Enter a number: ");

                    Contact newContacts = new Contact(newContactName, newContactNumber);

                    if (findMatch(listOfContacts, newContactName)) {
                        System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Yes/No)\n", newContactName);
                        boolean userAnswer = userInput.yesNo();
                        if (userAnswer) {
                            listOfContacts.add(newContacts.getName() + " | " + newContacts.getNumber());
                            Files.write(contactsFile, listOfContacts);
                        }
                    } else {
                        listOfContacts.add(newContacts.getName() + " | " + newContacts.getNumber());
                    }


                    Files.write(contactsFile, listOfContacts);


                    System.out.println("\n" + options);

                    break;
                case 3:
                    //search contact by name
                    String nameToFind = userInput.getString("Enter the name of the contact you want to find:\n");

                    listOfContacts = Files.readAllLines(contactsFile);

                    for (String listOfContact : listOfContacts) {
                        String[] name = listOfContact.split(Pattern.quote(" | "));
                        if (name[0].contains(nameToFind)) {
                            numberFormatter(listOfContact);
                        }
                    }
                    System.out.println("\n" + options);
                    break;
                case 4:
                    //delete contact
                    nameToDelete = userInput.getString("Enter the name of the contact you want to delete:\n");

                    listOfContacts = Files.readAllLines(contactsFile);

                    String match = returnMatch(listOfContacts, nameToDelete);

                    if (returnMatch(listOfContacts, nameToDelete).isEmpty()) {
                        System.out.printf("%s does not exist%n", nameToDelete);

                    } else {
                        String[] name = match.split(Pattern.quote(" | "));
                        System.out.printf("Are you sure you want to delete %s (Yes/No)\n", nameToDelete);
                        boolean userAnswer = userInput.yesNo();
                        if (userAnswer) {
                            listOfContacts.remove(match);
                            Files.write(contactsFile, listOfContacts);
                            System.out.printf("%s was deleted%n", nameToDelete);
                        } else {
                            System.out.printf("%s was not deleted%n.", nameToDelete);
                        }
                    }


                    System.out.println("\n" + options);
                    break;

                case 5:
                    //exit - write, then exit

                    dontExit = false;
                    break;

            }

        } while (dontExit);
    }
}
