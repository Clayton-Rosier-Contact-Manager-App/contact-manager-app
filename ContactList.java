import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import util.Input;

import javax.management.ConstructorParameters;

public class ContactList {

    List<String> contactList;
    Input scanner;


    @ConstructorParameters({"file"}) //determines where contact list is reading from
    public ContactList(Path file) throws IOException {
        contactList = Files.readAllLines(file);
        scanner = new Input();

    }


    public String returnMatch(String nameToFind) {
        // create a new string to hold the match
        String match = new String();
        // loop through the list of contacts
        for (int i = 0; i < contactList.size(); i++) {
            // split the contact into an array
            String[] name = contactList.get(i).split(Pattern.quote(" | "));
            // if the first element of the array matches the name we're looking for, return the contact
            if (name[0].equalsIgnoreCase(nameToFind)) {
                // set the match to the contact
                match = contactList.get(i);
            }
        }
        return match;
    }

    public void numberFormatter() {
        // loop through the list of contacts
        for (int i = 0; i < contactList.size(); i++) {
            // split the contact into an array
            String[] name = contactList.get(i).split(Pattern.quote(" | "));
            // replace the numbers with the formatted numbers
            String number = name[1].replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
            // print the contact with the formatted number
            System.out.printf("%-15s | %-15s |%n", name[0], number);

        }
    }

    public void numberFormatter(String string){


        if (string.isBlank()) {

            System.out.println("No match found.");

        } else {
            String number = string.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");

            // print the contact with the formatted number
            System.out.printf("%s%n", number);
        }



    }

    public String findMatch(String nameToFind) {
        // loop through the list of contacts
        for (int i = 0; i < contactList.size(); i++) {
            // split the contact into an array
            String[] name = contactList.get(i).split(Pattern.quote(" | "));
            // if the first element of the array matches the name we're looking for, return the contact
            if (name[0].equalsIgnoreCase(nameToFind)) {
//                System.out.println(contactList.get(i));
                return contactList.get(i);

            }
        }
        // if there's no match, return an empty string
        return "";
    }


    public void addContact(String match, Contact contact, Path path) {
        //if there's no match, add the contact to the list
        if (match.isBlank()) {
            contactList.add(contact.getName() + " | " + contact.getNumber());
            //then write the list with the new contact to the file
            try {
                Files.write(path, contactList);
            } catch (Exception e) {
                System.out.println("Could not write to file");
            }
            //if there is a match, ask the user if they want to overwrite the contact
        } else {
            System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Yes/No)\n", contact.getName());
            boolean userAnswer = this.scanner.yesNo();
            //if they say yes, remove the old contact and add the new one
            if (userAnswer) {
                //get the index of the match
                String replacement = contactList.get(contactList.indexOf(match));
                //remove the match
                contactList.remove(replacement);
                //add the new contact now that the match is gone
                contactList.add(contact.getName() + " | " + contact.getNumber());
                //now try to write the new list to the file
                try {
                    Files.write(path, contactList);
                } catch (Exception e) {
                    System.out.println("Could not write to file");
                }
            }

        }

    }

    public void deleteContact(String string, String deleteName, Path path) {
        if (string.isBlank()) {
            System.out.printf("%s does not exist%n", deleteName);

        } else {
            System.out.printf("Are you sure you want to delete %s (Yes/No)\n", deleteName);
            boolean userAnswer = this.scanner.yesNo();
            if (userAnswer) {
                contactList.remove(string);

                try {
                    Files.write(path, contactList);
                } catch (Exception e) {
                    System.out.println("Could not write to file.");
                }

                System.out.printf("%s was deleted.%n", deleteName);
            } else {
                System.out.printf("%s was not deleted.%n", deleteName);
            }
        }
    }

}
