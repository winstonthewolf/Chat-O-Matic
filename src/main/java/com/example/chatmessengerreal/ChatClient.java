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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class ChatClient extends Application {
    //Text area for displaying
    DataOutputStream outputToServer = null;
    DataInputStream inputFromServer = null;
    Socket socket = null;


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
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();



//                    String messageFromServer = inputFromServer.readUTF();

        socket = new Socket("localhost", 8000);


        inputFromServer = new DataInputStream(socket.getInputStream());
        outputToServer = new DataOutputStream(socket.getOutputStream());


        tf.setOnAction(e -> {
            try {

                String message = tf.getText();
                outputToServer.writeUTF(message);
                outputToServer.flush();


                //BufferedReader outputFromServer = new BufferedReader(new InputStreamReader(tf.getText()));


                Platform.runLater(()->{
                    ta.appendText("You: " + message + '\n');
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        new Thread(()-> {
            try {

                while(true) {
                    String messageFromServer = inputFromServer.readUTF();
                    Platform.runLater(()-> {
                        ta.appendText("Server: " + messageFromServer + "\n");
                    });


                }


            } catch (IOException ex) {
                ta.appendText(ex.toString() + "\n");
            }
        }).start();

        }
}
