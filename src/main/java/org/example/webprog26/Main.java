package org.example.webprog26;

//Protocols: https://javarush.com/groups/posts/2521-chastjh-3-protokolih-httphttps

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UsersLoader usersLoader = new UsersLoader();
        final String usersJson = usersLoader.getLoadedUsers();

        System.out.println("usersJson:\n" + usersJson);

        UserUploader userUploader = new UserUploader();
        userUploader.uploadUsers(usersJson);
    }
}