# Chat-O-Matic
## Real-time chat server and client.
---
This project is comprised of two programs. One being the server program and the other being the client.
It uses the JavaFX framework for the GUI. It uses sockets for intercommunication between the programs and 
it starts the communcication through starting threads in each program. If you run it as is it will open 
a server socket on port 8000 and the client will use localhost to connect to the server. You can also run it
on two different computers on your host network by replacing localhost with the private ip of your server in the client code.

### Instructions: 
---
### PreReqs: 
1. JavaFX 
2. JavaSDK17

Once you install that and add the libraries to your IDE if you're trying to run the programs from your IDE--which is what I will show you and I'll also
show you how to run it through the command line without an IDE-- you need to change your configuration settings on the run of the program. So in intelliJ
go to where you run a program(by the play button) and click on the box to the left of it. You will see a drop down menu where it says Edit Configurations. 
There, click on Modify Options and click Add VM Options under Java. Input these options in the box for VM options:
'--module-path /path/to/javafx-sdk-18.0.1/lib --add-modules javafx.controls' 
Enter your own path where you installed javafx and point it to the lib folder.
Once you do this for both programs run configurations, you can start the server and then run the client. 
You should now be able to send a message to eachother from each application. Congrats.

If you just want to run it from the command line without an IDE go to the folder where the chatClient.java and chatServer are located 
and from there compile them using '/path/to/java-1.17.0-openjdk-amd64/bin/javac --module-path /path/to/javafx-sdk-18.0.1/lib --add-modules javafx.controls ChatServer.java'
changing the chatServer.java file for the chatClient afterwards and running it again to compile the chatClient.class

After that run the class files as so, replacing the chatServer file for the chatClient class file after running the chatServer. You will need to run each in a seperate terminal.
'/path/to/java-1.17.0-openjdk-amd64/bin/java --module-path /path/to/javafx-sdk-18.0.1/lib --add-modules javafx.controls ChatServer'

End.
