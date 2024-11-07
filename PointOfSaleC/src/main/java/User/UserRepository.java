package User;

import java.io.*;

public class UserRepository {
    private static final File userFile = new File("User.txt");

    public static boolean isUserExist(User user) throws IOException {
        if (!userFile.exists()) userFile.createNewFile();
        return new BufferedReader(new FileReader(userFile))
                .lines().map(lines -> lines.split(","))
                .anyMatch(lines -> user.getUsername().equalsIgnoreCase(lines[0]) && user.getPassword().equalsIgnoreCase(lines[1]));
    }

    public static boolean isUsernameExist(String username) throws IOException {
        if (!userFile.exists()) userFile.createNewFile();
        return new BufferedReader(new FileReader(userFile))
                .lines().map(lines -> lines.split(","))
                .anyMatch(lines -> username.equalsIgnoreCase(lines[0]));
    }

    public static void addUser(User user) throws IOException {
        if (!userFile.exists()) userFile.createNewFile();
        BufferedWriter br = new BufferedWriter(new FileWriter(userFile, true));
        br.write(user.getUsername() + "," + user.getPassword() + "\n");
        br.close();
    }
}
