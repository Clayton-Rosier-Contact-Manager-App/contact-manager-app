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



        String options = "1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):";

        System.out.println(options);


        boolean dontExit = true;

        do {

            //declares a new Input obj
            Input userInput = new Input();

            int userSelection = userInput.getInt();
            String newContactName = new String();

            switch (userSelection) {
                case 1:
                    //view all contacts
                    System.out.println(Files.readString(contactsFile));
                    break;
                case 2:
                    //adds a new contact
                    newContactName = userInput.getString("Enter a name: ");
                    String newContactNumber = userInput.getString("Enter a number: ");

                    Contact newContacts = new Contact(newContactName, newContactNumber);
                    contactInfo.add(newContacts.getName() + " " + newContacts.getNumber());

                    Files.write(contactsFile, contactInfo, StandardOpenOption.APPEND);


                    System.out.println(options);

                    break;
                case 3:
                    //search contact by name
                    String nameToFind = userInput.getString("Enter the name of the contact you want to find:\n");

                    List<String> listOfContacts = Files.readAllLines(contactsFile);

                    for (int i = 0; i < listOfContacts.size(); i++) {
                        String[] name =  listOfContacts.get(i).split(" ");
                        if (name[0].equalsIgnoreCase(nameToFind)) {
                            System.out.println(listOfContacts.get(i));
                        }
                    }
//                    Files.write(contactsFile, listOfContacts);
                    System.out.println(options);
                    break;
                case 4:
                    //delete contact
                    String nameToDelete = userInput.getString("Enter the name of the contact you want to delete:\n");

                    listOfContacts = Files.readAllLines(contactsFile);

                    for (int i = 0; i < listOfContacts.size(); i++) {
                       String[] name =  listOfContacts.get(i).split(" ");
                        if (name[0].equalsIgnoreCase(nameToDelete)) {
                            listOfContacts.remove(i);
                        }
                    }

                    Files.write(contactsFile, listOfContacts);
                    System.out.println(options);

                    break;
                case 5:
                    //exit - write, then exit
                    dontExit = false;
                    break;
            }

        } while (dontExit);
    }
}
