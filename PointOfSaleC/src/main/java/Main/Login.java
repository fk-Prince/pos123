package Main;

import User.*;

import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("-------------------------------");
                System.out.println("[1]Login\n[2]Signup\n[3]Exit");
                System.out.print("Choice: ");
                int choice = Integer.parseInt(sc.nextLine());
                System.out.println("-------------------------------");
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Username: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();

                        User user = new User(name, password);
                        boolean isUserExist = UserRepository.isUserExist(user);

                        if (isUserExist) {
                            System.out.println("-----Logged in-----");
                            PointOfSale pos = new PointOfSale();
                            pos.run();
                        } else {
                            System.out.println("-----Incorrect Password-----");
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter Username: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();
                        boolean usernameExist = UserRepository.isUsernameExist(name);
                        if (usernameExist) {
                            System.out.println("-----Try again different username-----");
                        } else {
                            User user = new User(name, password);
                            UserRepository.addUser(user);
                            System.out.println("-----Successfully Created-----");
                        }
                    }
                    case 3 -> System.exit(0);
                    default -> System.out.println("Invalid Choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}