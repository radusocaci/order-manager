package Model;

public class Customer {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(int id, String lastName, String firstName, String email, String address) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.address = address;
    }

    public Customer(String lastName, String firstName, String email, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return lastName + ' ' +
                firstName + ' ' +
                address + '\n';
    }
}
