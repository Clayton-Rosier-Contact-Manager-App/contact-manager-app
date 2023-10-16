import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {

        String contactDirectory = "./data";

        Path contactList = Paths.get(contactDirectory);

        if (Files.notExists(contactList)) {
            Files.createDirectory(contactList);
        } else {
            System.out.println("files exists");
        }

        String contact = "contacts.txt";

        Path contactsFile = Paths.get(contactDirectory, contact);

        if (Files.notExists(contactsFile)) {
            Files.createFile(contactsFile);
        }

        ArrayList<String> contactInfo = new ArrayList<>();
        Contact newContact = new Contact("will", "1234567891");

        contactInfo.add(newContact.getName() + " " + newContact.getNumber());

        System.out.println(contactInfo);
    }

}
