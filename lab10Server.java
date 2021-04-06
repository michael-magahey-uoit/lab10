import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class lab10Server extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Lab 10 - Server");

        GridPane myGrid = new GridPane();
        myGrid.setAlignment(Pos.CENTER);
        myGrid.setHgap(10);
        myGrid.setVgap(10);
        myGrid.setPadding(new Insets(25, 25, 25, 25));
        
        TextField txtDisplay = new TextField();

        myGrid.add(txtDisplay, 0, 1);

        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        lab10ServerThread[] threads = null;
        int numClients = 0;
        
        try
        {
            serverSocket = new ServerSocket(1337);
            System.out.println("Running...");
            System.out.println("Listening to port: 1337");
            threads = new lab10ServerThread[100];
            while (true)
            {
                clientSocket = serverSocket.accept();
                System.out.println("Client #" + (numClients + 1) + " Connected!");
                threads[numClients] = new lab10ServerThread(clientSocket);
                threads[numClients].start();
                numClients++;
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        Scene scene = new Scene(myGrid, 350, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}