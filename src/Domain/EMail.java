package Domain;

public class EMail {
    private String emailAdress;
    private String password;

    public EMail(String email, String password) {
        this.emailAdress = email;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAdress;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "EMail{" +
                "emailAdress='" + emailAdress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public boolean equals(EMail email) {
        return this.emailAdress.equals(email.getEmailAddress()) && this.password.equals(email.getPassword());
    }
}
