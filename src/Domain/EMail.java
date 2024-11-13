package Domain;

/**
 * The EMail class represents an email account with an email address and password.
 * It provides methods for accessing and comparing email details.
 */
public class EMail {
    private String emailAdress; /**A string for emailAddress*/
    private String password; /**A string for the emails password*/

    /**
     * Constructs a new EMail instance with the specified email address and password.
     *
     * @param email     the email address of the account
     * @param password  the password associated with the email account
     */
    public EMail(String email, String password) {
        this.emailAdress = email;
        this.password = password;
    }

    /**
     * Gets the email address of this account.
     *
     * @return the email address of this account
     */
    public String getEmailAddress() {
        return emailAdress;
    }

    /**
     * Gets the password of this account.
     *
     * @return the password of this account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns a string representation of the EMail object.
     *
     * @return a string containing the email address and password of the account
     */
    @Override
    public String toString() {
        return "EMail{" +
                "emailAdress='" + emailAdress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Compares this EMail object with another EMail object for equality.
     *
     * @param email the EMail object to compare with
     * @return true if both the email address and password are the same, false otherwise
     */
    public boolean equals(EMail email) {
        return this.emailAdress.equals(email.getEmailAddress()) && this.password.equals(email.getPassword());
    }
}
