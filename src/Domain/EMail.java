package Domain;

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
}
