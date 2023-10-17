import util.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Application {

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

    public static String enforceNumberLength(Input input) {
        String newContactNumber;
        do {
            newContactNumber = input.getString("Enter a valid number: ");

        } while (newContactNumber.length() < 10 || newContactNumber.length() > 15);
        return newContactNumber;
    }


    public static void main(String[] args) throws IOException {

        Path contactsFile = createDirectory("./data", "contacts.txt");

        String options = "1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):";

        System.out.println(options);


        boolean dontExit = true;
        // initial read form the contacts.txt file to make sure listOfContacts is up to date
        ContactList listOfContacts = new ContactList(contactsFile);


        do {

            //declares a new Input obj
            Input userInput = new Input();

            int userSelection = userInput.getInt();

            String nameToDelete;
            switch (userSelection) {
                case 1:

                    //view all contacts
                    System.out.printf("%-15s | Phone number\n" +
                            "%-15s%n", "Name", "-----------------------------------");
                    //call the numberFormatter method to format the numbers before printing
                    listOfContacts.numberFormatter();
                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 2:

                    //adds a new contact//
                    String newContactName = userInput.getString("Enter a name: ");

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
                    String nameToFind = userInput.getString("Enter the name of the contact you want to find:\n");

                    //re-reads form the contacts.txt file to make sure listOfContacts is up to date
                    listOfContacts.contactList = Files.readAllLines(contactsFile);

                    //stores the matched name that is returned from the findMatch method
                    String matchedName = listOfContacts.findMatch(nameToFind);

                    //prints out the formatted matched contact
                    listOfContacts.numberFormatter(matchedName);

                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 4:

                    //delete contact
                    nameToDelete = userInput.getString("Enter the name of the contact you want to delete:\n");

                    //re-reads form the contacts.txt file to make sure listOfContacts is up to date
                    listOfContacts.contactList = Files.readAllLines(contactsFile);

                    //looks to see if the request contact to delete actually exists
                    String match = listOfContacts.findMatch(nameToDelete);

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
