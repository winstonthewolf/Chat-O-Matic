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

        new Thread(()-> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(()->
                        ta.appendText("Server started at "+ new Date() + '\n'));

                Socket socket = serverSocket.accept();

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                //BufferedReader outputFromServer = new BufferedReader(new InputStreamReader(tf.getText()));

                while(true) {
                    String message = inputFromClient.readUTF();
                    //outputToClient.writeUTF(message);
                    Platform.runLater(() -> {
                        ta.appendText("Friend: " + message + '\n');
                    });

                    tf.setOnAction(e -> {
                        try {

                            String messageFromServer = tf.getText();
                            outputToClient.writeUTF(messageFromServer);
                            outputToClient.flush();
                            Platform.runLater(() -> {
                                ta.appendText("Server: " + messageFromServer+"\n");
                            });
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    });
                }


            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();



    }
}
