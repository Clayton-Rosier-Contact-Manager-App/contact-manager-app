
import util.Input;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Application {


    //creates the directory and file if they don't already exist
    public static Path createDirectory(String directory, String file) {

        Path contactList = Paths.get(directory);

        if (Files.notExists(contactList)) {
            try {
                Files.createDirectory(contactList);
            } catch (Exception e) {
                System.out.println("Exception caught");
            }
        }

        Path contactsFile = Paths.get(directory, file);

        if (Files.notExists(contactsFile)) {
            try {
                Files.createFile(contactsFile);
            } catch (Exception e) {
                System.out.println("Exception caught");
            }
        }

        return contactsFile;

    }

    //enforces the number length to be between 10 (for US numbers) and 15 digits (for international numbers)
    public static String enforceNumberLength(Input input) {
        String newContactNumber;
        do {
            newContactNumber = input.getString("Enter a valid number: ");

        } while (newContactNumber.length() < 10 || newContactNumber.length() > 15);
        return newContactNumber;
    }


    public static void main(String[] args) throws IOException {

        //returns the path to the contacts.txt file
        Path contactsFile = createDirectory("./data", "contacts.txt");

        //holds the options that are printed out to the user
        String options = "1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):";

        //print the options
        System.out.println(options);

        //boolean to keep the program running until the user exits
        boolean dontExit = true;

        // initial read of the contacts.txt file, stores each line as an element in the contactList ArrayList
        ContactList listOfContacts = new ContactList(contactsFile);


        do {
            //declares a new Input obj
            Input userInput = new Input();

            //gets the user's selection
            int userSelection = userInput.getInt();

            //initializes a string to hold the name of the contact to delete used in case 4 and deleteContact method
            String nameToDelete;

            switch (userSelection) {
                case 1:

                    //view all contacts
//                    System.out.printf("%-15s | Phone number\n" +
//                            "%-15s%n", "Name", "-----------------------------------");

                    //call the numberFormatter method to format the numbers before printing
                    listOfContacts.numberFormatter();
                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 2:
                    //add a new contact
                    String newContactName = userInput.getString("Enter a name: ");

                    //enforces the number length to be between 10 (for US numbers) and 15 digits (for international numbers)
                    String validNumber = enforceNumberLength(userInput);

                    //create a new contact object using the name and number provided by the user
                    Contact newContacts = new Contact(newContactName, validNumber);

                    //call the addContact method to add the new contact to the list
                    listOfContacts.addContact(listOfContacts.findMatch(newContactName), newContacts, contactsFile);

                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 3:
                    //search contact by name

                    //stores the name the user wants to find
                    String nameToFind = userInput.getString("Enter the name of the contact you want to find:\n");

                    //re-reads form the contacts.txt file to make sure listOfContacts is up-to-date
                    listOfContacts.contactList = Files.readAllLines(contactsFile);

                    //stores the matched name that is returned from the findMatch method, returns em pty string if no match is found
                    List<String> matchedNames = listOfContacts.findMatches(nameToFind);

                    if (matchedNames.isEmpty()) {
                        System.out.println("No match found.");
                    } else {
                        //call the numberFormatter method to format the numbers before printing
                        listOfContacts.numberFormatter(matchedNames);
                    }


                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 4:

                    //delete contact
                    nameToDelete = userInput.getString("Enter the name of the contact you want to delete:\n");

                    //re-reads form the contacts.txt file to make sure listOfContacts is up-to-date
                    listOfContacts.contactList = Files.readAllLines(contactsFile);

                    //looks to see if the request contact to delete actually exists
                    String match = listOfContacts.findExactMatch(nameToDelete);

                    //deletes the contact if it does indeed exist
                    listOfContacts.deleteContact(match, nameToDelete, contactsFile);

                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 5:
                    //exit
                    dontExit = false;
                    break;

            }

        } while (dontExit);
    }
}
