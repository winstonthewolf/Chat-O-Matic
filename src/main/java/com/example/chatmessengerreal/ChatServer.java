package com.example.chatmessengerreal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

//TODO: Write succinct README page on how to compile and run program remember to note system requirements.v
public class ChatServer extends Application {
    //Text area for displaying



    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create scene and place it in stage

        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5,5,5,5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter chat: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("Showing now");

        new Thread(()->{
            try {
                //Start server socket for data transfer
                ServerSocket serverSocket = new ServerSocket(8000);
                //Surround with if statement for if serversocket is successful. *Ignore this comment if you're not me*

                //Display Date and time of when server starts
                Platform.runLater(()-> {
                    ta.appendText("Server started at "+ new Date() + '\n');
                });
                Socket socket = serverSocket.accept();

                //textfield action
                tf.setOnAction(e -> {
                    try {

                        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
                        String messageFromServer = tf.getText();
                        outputToClient.writeUTF(messageFromServer);
                        outputToClient.flush();
                        Platform.runLater(() -> {
                            ta.appendText("Server: " + messageFromServer+"\n");
                        });
                        tf.clear();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                });


                //Read input from client and display it on the screen
                while(true) {
                    DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                    String message = inputFromClient.readUTF();
                    Platform.runLater(() -> {
                        ta.appendText("Friend: " + message + '\n');
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();



    }
}
