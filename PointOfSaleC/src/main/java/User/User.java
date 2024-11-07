package User;

public class User {
    private final String username;
    private final String password;

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
