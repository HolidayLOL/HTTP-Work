package org.example.webprog26;

//Protocols: https://javarush.com/groups/posts/2521-chastjh-3-protokolih-httphttps

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DataLoader usersLoader = new UsersLoader();
        final String usersJson = usersLoader.loadData("https://jsonplaceholder.typicode.com/users");

        System.out.println("usersJson:\n" + usersJson);

        DataUploader usersUploader = new UserUploader();
        usersUploader.uploadData("https://jsonplaceholder.typicode.com/users", usersJson);
    }
}