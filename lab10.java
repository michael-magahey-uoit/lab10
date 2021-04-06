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

public class lab10 extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Lab 10");

        GridPane myGrid = new GridPane();
        myGrid.setAlignment(Pos.CENTER);
        myGrid.setHgap(10);
        myGrid.setVgap(10);
        myGrid.setPadding(new Insets(25, 25, 25, 25));

        Socket client = new Socket("127.0.0.1", 1337);
        DataOutputStream dout = new DataOutputStream(client.getOutputStream());

        Button sendButton = new Button("Send");
        Button exitButton = new Button("Exit");
        Label lbMessage = new Label("Message: ");
        Label lbUsername = new Label("Username: ");
        TextField txtMessage = new TextField();
        TextField txtUsername = new TextField();

        myGrid.add(lbMessage, 0, 1);
        myGrid.add(txtMessage, 1, 1);
        myGrid.add(lbUsername, 0, 2);
        myGrid.add(txtUsername, 1, 2);
        myGrid.add(sendButton, 0, 3);
        myGrid.add(exitButton, 1, 3);

        sendButton.setOnAction(e -> 
        {
            if (client.isConnected())
            {
                try
                {
                    dout.writeUTF(txtUsername.getText() + ": " + txtMessage.getText());
                    dout.flush();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                try
                {
                    dout.close();
                    client.close();
                    System.exit(0);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    System.exit(-1);
                }
            }
        });

        exitButton.setOnAction(e -> 
        {
            try
            {
                dout.close();
                client.close();
                System.exit(0);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.exit(-1);
            }
        });

        Scene scene = new Scene(myGrid, 350, 350);

        primaryStage.setTitle("lab10");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
