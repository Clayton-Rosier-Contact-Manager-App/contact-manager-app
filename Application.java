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
        System.out.println(listOfContacts.contactList.size());

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
                    //call the numberFormatter method to format the numbers before printing
                    listOfContacts.numberFormatter();
                    //print the options again
                    System.out.println("\n" + options);
                    break;

                case 2:

                    //adds a new contact//
                    String newContactName = userInput.getString("Enter a name: ");
                    String newContactNumber = userInput.getString("Enter a number: ");

                    //create a new contact object using the name and number provided by the user
                    Contact newContacts = new Contact(newContactName, newContactNumber);

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
