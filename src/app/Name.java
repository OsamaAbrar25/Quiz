package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class Name {

    @FXML
    TextField t1;
    @FXML
    TextField t2;
    @FXML
    TextField t3;
    @FXML
    TextField t4;

    @FXML
    void save(ActionEvent event)
    {
            Connection conn = Database.con;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Input.fxml"));
                Parent parent = loader.load();
                Input in = loader.getController();
                Stage stage = Main.stage;
                stage.setScene(new Scene(parent));


                Statement st00 = conn.createStatement();
                st00.execute("SET SQL_SAFE_UPDATES = 0");

                if(!t1.getText().trim().equals("")) {
                    Statement st1 = conn.createStatement();
                    st1.executeUpdate("UPDATE Namess SET Sub1 = '" + t1.getText().trim() + "' WHERE Id = 1;");
                }


                if(!t2.getText().trim().equals("")) {
                    Statement st2 = conn.createStatement();
                    st2.executeUpdate("UPDATE Namess SET Sub2 = '" + t2.getText().trim() + "' WHERE Id = 1;");
                }


                if(!t3.getText().trim().equals("")) {
                    Statement st3 = conn.createStatement();
                    st3.executeUpdate("UPDATE Namess SET Sub3 = '" + t3.getText().trim() + "' WHERE Id = 1;");
                }


                if(!t4.getText().trim().equals("")) {
                    Statement st4 = conn.createStatement();
                    st4.executeUpdate("UPDATE Namess SET Sub4 = '" + t4.getText().trim() + "' WHERE Id = 1;");
                }

                Statement stinf = conn.createStatement();
                stinf.execute("SET SQL_SAFE_UPDATES = 1");

                in.setName();
            }
            catch (SQLException | IOException e)
            {
                e.printStackTrace();
            }

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
}

