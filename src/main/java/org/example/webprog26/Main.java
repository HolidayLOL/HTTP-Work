package org.example.webprog26;

//Protocols: https://javarush.com/groups/posts/2521-chastjh-3-protokolih-httphttps

//A callback function is a function passed as an argument to another function and executed when that function completes
//or some event happens. In most programming languages, callback functions are especially useful when we’re working with
//asynchronous code.

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //Cannot be converted to lambda expression
    private static final OnDataUploadedListener onDataUploadedListener = new OnDataUploadedListener() {
        @Override
        public void onSuccess() {
            System.out.println("We are getting result of data uploading on thread: " + Thread.currentThread().getName());
            System.out.println("Data uploaded successfully");
        }

        @Override
        public void onFailure(String errorMessage) {
            System.out.println("We are getting result of data uploading on thread: " + Thread.currentThread().getName());
            System.out.println("Data wasn't uploaded! An error occurred: " + errorMessage);
        }
    };

    //Not a functional interface anymore
    private static final OnDataLoadedListener onDataLoadedListener = new OnDataLoadedListener() {
        @Override
        public void onDataLoaded(String data) {
            System.out.println("We are getting result of data loading on thread: " + Thread.currentThread().getName());
            System.out.println("onDataLoaded():\n" + data);

            DataUploader usersUploader = new UserUploader();
            usersUploader.uploadData("https://jsonplaceholder.typicode.com/users", data, onDataUploadedListener);
        }

        @Override
        public void onFailure(String errorMessage) {
            System.out.println("We are getting result of data loading on thread: " + Thread.currentThread().getName());
            System.out.println("Data wasn't loaded: An error occurred: " + errorMessage);
        }
    };

    public static void main(String[] args) {
        DataLoader usersLoader = new UsersLoader();
        usersLoader.loadData("https://jsonplaceholder.typicode.com/users", onDataLoadedListener);
    }
}