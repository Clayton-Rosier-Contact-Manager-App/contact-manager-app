public class Contact {

    private String name;

    private String number;

    public int increment = 0;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact(String name, String number) {
        increment++;
        this.name = name;
        this.number = number;

    }
}
