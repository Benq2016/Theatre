package src.Domain;

public class EMail {
    private String email;
    private String password;

    public EMail(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "EMail{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
