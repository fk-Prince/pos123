package Repository;

import Entity.User;

import javax.swing.text.html.Option;
import java.io.*;
import java.util.Optional;

public class UserRepository {
    private final File userFile = new File("src/main/resources/USERS.txt");


    public Optional<User> checkUser(String username) {
        try {
            if (!userFile.exists()) userFile.createNewFile();
            return new BufferedReader(new FileReader(userFile))
                    .lines().map(lines -> lines.split(","))
                    .filter(lines -> username.equalsIgnoreCase(lines[0]))
                    .map(lines -> new User(lines[0], lines[1]))
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void addUser(User user) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true));
            bw.write(user.getUserName() + "," + user.getPassword() + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> checkAccount(User user) {
        try {
            if (!userFile.exists()) userFile.createNewFile();
            return new BufferedReader(new FileReader(userFile))
                    .lines().map(lines -> lines.split(","))
                    .filter(lines -> user.getUserName().equalsIgnoreCase(lines[0]) && user.getPassword().equalsIgnoreCase(lines[1]))
                    .map(lines -> new User(lines[0], lines[1]))
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void example() {
        try {

            BufferedReader br = new BufferedReader(new FileReader("filename ninyo.txt"));
            String line; // dri nato i store ang one line sa file
            //(line = br.readLine) which is store nato ang 1 line sa file
            //so dili na null ang line;
            //so line != null which is true so musulod sa while loop
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                String firstname = lines[0];
                String lastname = lines[1];
                //line.split(",") so line kay whole line sa file diba so line.split ato i split sya or bahinon pag naa syay ma basa na ","
                //for example firstnamenako,lastnamenako
                //so every split kay i store nato sa array
                //index sa array
                //so mahimo ana is lines[0] kay firstnamenako since mao toy first na basahan tapos naka kita syag "," so split
                //then lines[1] mahimong lastnamenako


                //then nextline napud same process
                // ra hangtud ang (line != null) hangtud mag false nani meaning wala nay unod ang file wala na syay nabasahan
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write() {
        try {
                                                                                //append(true) mean mag add ta sa file
            BufferedWriter bw = new BufferedWriter(new FileWriter("Fileninyu.txt", true));
            //dali ra ang writer ani ra gyud na
            bw.write("firstnamenako" + "," + "lastnamenako");
            //dapat naa gyud tay comma para sa pag read nato
            bw.newLine();
            //og naa pud tay newline para sa next line napud ang next niya na sulat
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
