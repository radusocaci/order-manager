package Model;

/**
 * Defines a customer as seen in the database
 *
 * @author Socaci Radu Andrei
 */
public class Customer {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String address;

    /**
     * Used for reflection instantiation
     */
    public Customer() {
    }

    /**
     * Used for update operations
     *
     * @param id        client id
     * @param lastName  client last name
     * @param firstName client first name
     * @param email     client email
     * @param address   client address
     */
    public Customer(int id, String lastName, String firstName, String email, String address) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.address = address;
    }

    /**
     * Instantiates a new client. ID is later set from the database
     *
     * @param lastName  client last name
     * @param firstName client first name
     * @param email     client email
     * @param address   client address
     */
    public Customer(String lastName, String firstName, String email, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.address = address;
    }

    /**
     * @return client id
     */
    public int getId() {
        return id;
    }

    /**
     * set client id
     *
     * @param id client id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return client last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set client last name
     *
     * @param lastName client last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return client first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set client first name
     *
     * @param firstName client first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return client email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set client email
     *
     * @param email client email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return client address
     */
    public String getAddress() {
        return address;
    }

    /**
     * set client address
     *
     * @param address client address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer: " + lastName + ' ' +
                firstName + "\n" + "E-Mail: " +
                email + "\nAddress: " + address + "\n";
    }
}
