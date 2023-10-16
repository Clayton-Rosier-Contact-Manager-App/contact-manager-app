import util.Input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        ArrayList<String> contactInfo = new ArrayList<>();
        Contact newContact = new Contact("will", "1234567891");

        contactInfo.add(newContact.getName() + " " + newContact.getNumber());

        Files.write(contactsFile, contactInfo);

        String options = "1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):";

        System.out.println(options);

//        System.out.println("1. View contacts");
//        System.out.println("2. Add new contact");
//        System.out.println("3. Search contact by name");
//        System.out.println("4. Delete an existing contact");
//        System.out.println("5. Exit");

        boolean dontExit = true;

        do {

            Input userInput = new Input();

            System.out.printf("%n");
            int userSelection = userInput.getInt();

            switch (userSelection) {
                case 1:
                    //view all contact

                    break;
                case 2:

                    String newContactName = userInput.getString("Enter a name: ");
                    String newContactNumber = userInput.getString("Enter a number: ");

                    Contact newContacts = new Contact(newContactName, newContactNumber);
                    contactInfo.add(newContacts.getName() + " " + newContacts.getNumber());


                    System.out.println(options);

                    break;
                case 3:
                    //search contact by name
                    break;
                case 4:
                    //delete contact
                    List<String> listOfContacts = Files.readAllLines(contactList);


                    break;
                case 5:
                    //exit - write, then exit
                    Files.write(contactsFile, contactInfo, StandardOpenOption.APPEND);
                    dontExit = false;
                    break;
            }

        }while(dontExit);
    }

}
