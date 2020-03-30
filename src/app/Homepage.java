package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class Homepage implements Initializable {

    static int num = 0;

    @FXML
    Button s1;
    @FXML
    Button s2;
    @FXML
    Button s3;
    @FXML
    Button s4;
    @FXML
    RadioButton adm;
    @FXML
    RadioButton usr;

    @FXML
    void Open(ActionEvent event)
    {
        Stage stage = Main.stage;
        stage.setResizable(true);
        Button b = (Button) event.getSource();
        if(b == s1)
        {
            num = 1;
        }
        else if(b == s2)
        {
            num = 2;
        }
        else if(b == s3)
        {
            num = 3;
        }
        else if(b == s4)
        {
            num = 4;
        }
        try {

         if(usr.isSelected()) {
             try {
                 Connection con = Database.con;

                 String[] sub = {"subject1", "subject2", "subject3", "subject4"};
                 Statement st1 = con.createStatement();
                 ResultSet rs1 = st1.executeQuery("SELECT EXISTS(SELECT 1 FROM " + sub[Homepage.num - 1] + ")");
                 rs1.next();
                 int c = rs1.getInt(1);

                 if (c == 0) {
                     Parent pa = FXMLLoader.load(getClass().getResource("End.fxml"));
                     stage.setScene(new Scene(pa));
                     stage.show();
                 }

                 else
                 {
                     Parent p = FXMLLoader.load(getClass().getResource("QA.fxml"));
                     stage.setScene(new Scene(p));
                     stage.show();
                 }
             }
             catch (SQLException e)
             {
                 e.printStackTrace();
             }

             stage.setFullScreen(true);
             stage.setResizable(false);
         }

         else if(adm.isSelected())
         {
             Parent p = FXMLLoader.load(getClass().getResource("Input.fxml"));
             stage.setScene(new Scene(p));
             stage.show();
         }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stage stage = Main.stage;
        stage.setResizable(false);
        Connection con = Database.con;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Namess WHERE Id = 1");
            String[] names = new String[4];
            rs.next();
            Button[] buttons = {s1, s2, s3, s4};
            for(int i = 0; i < 4; i++)
            {
                names[i] = rs.getString(i+2);
                if(!(names[i] == null))
                {
                    buttons[i].setText(names[i]);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
