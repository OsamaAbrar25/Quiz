package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Input implements Initializable {

    @FXML
    TextField Question;
    @FXML
    TextField Option1;
    @FXML
    TextField Option2;
    @FXML
    TextField Option3;
    @FXML
    TextField Option4;
    @FXML
    TextField Correct;
    @FXML
    Label Sub;
    @FXML
    ComboBox choose;

    static ArrayList<String> arrayList;
    static String QuestionId;
    private Stage stage = Main.stage;

    @FXML
    void clearAll()
    {
        Question.clear();
        Option1.clear();
        Option2.clear();
        Option3.clear();
        Option4.clear();
        Correct.clear();
    }

    @FXML
    void deleteAll()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to clear all the question?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Clear request");
        alert.showAndWait().ifPresent(Type -> {
            if(Type == ButtonType.YES)
            {
                Connection con = Database.con;
                String[] sub = {"subject1", "subject2", "subject3", "subject4"};
                String choice = (String) choose.getSelectionModel().getSelectedItem();
                    try {

                        Statement st00 = con.createStatement();
                        st00.execute("SET SQL_SAFE_UPDATES = 0");

                        Statement st1 = con.createStatement();
                        st1.executeUpdate("DELETE FROM " + sub[Homepage.num - 1]);

                        Statement stinf = con.createStatement();
                        stinf.execute("SET SQL_SAFE_UPDATES = 1");

                        choose.getItems().clear();
                        choose.getItems().add("New");

                        Question.clear();
                        Option1.clear();
                        Option2.clear();
                        Option3.clear();
                        Option4.clear();
                        Correct.clear();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
            }
        });

    }


    @FXML
    void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the question?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete request");
        alert.showAndWait().ifPresent(Type -> {
            if(Type == ButtonType.YES)
            {
                Connection con = Database.con;
                String[] sub = {"subject1", "subject2", "subject3", "subject4"};
                String choice = (String) choose.getSelectionModel().getSelectedItem();
                if(!choice.equals("New"))
                {
                    try{

                        Statement st0 = con.createStatement();
                        ResultSet rs0 = st0.executeQuery("SELECT * FROM "+sub[Homepage.num-1]);
                        for(int d = 1; d <= Integer.valueOf(choice); d++)
                        {
                            rs0.next();
                        }
                        int qid = rs0.getInt(1);

                        Statement st1 = con.createStatement();
                        st1.executeUpdate("DELETE FROM "+sub[Homepage.num-1]+" WHERE Id = "+qid);

                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery("SELECT COUNT(*) FROM "+sub[Homepage.num-1]);
                        rs2.next();
                        int i = rs2.getInt(1);
                        choose.getItems().remove(""+(i+1));

                        Question.clear();
                        Option1.clear();
                        Option2.clear();
                        Option3.clear();
                        Option4.clear();
                        Correct.clear();

                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @FXML
    void Add()
    {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add/change the question?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Add request");
            alert.showAndWait().ifPresent(Type -> {
                if (Type == ButtonType.YES) {

                    if(Correct.getText().equalsIgnoreCase(Option1.getText()) || Correct.getText().equalsIgnoreCase(Option2.getText()) ||
                            Correct.getText().equalsIgnoreCase(Option3.getText()) || Correct.getText().equalsIgnoreCase(Option4.getText())) {
                        Connection con = Database.con;
                        String[] sub = {"subject1", "subject2", "subject3", "subject4"};
                        String choice = (String) choose.getSelectionModel().getSelectedItem();
                        if (choice.equals("New")) {
                            try {
                                Statement st1 = con.createStatement();
                                st1.executeUpdate("INSERT INTO " + sub[Homepage.num - 1] + "(Question, Option1, Option2, Option3, Option4, Answer)" +
                                        "VALUES('" + Question.getText() + "','" + Option1.getText() + "','" + Option2.getText() + "','" + Option3.getText() + "','"
                                        + Option4.getText() + "','" + Correct.getText() + "');");

                                Statement st2 = con.createStatement();
                                ResultSet rs2 = st2.executeQuery("SELECT COUNT(*) FROM " + sub[Homepage.num - 1]);
                                rs2.next();
                                int i = rs2.getInt(1);
                                choose.getItems().add("" + i);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Statement st00 = con.createStatement();
                                st00.execute("SET SQL_SAFE_UPDATES = 0");

                                Statement st11 = con.createStatement();
                                st11.executeUpdate("UPDATE " + sub[Homepage.num - 1] + " SET Question = '" + Question.getText()
                                        + "', Option1 = '" + Option1.getText() + "', Option3 = '" + Option3.getText() + "', Option4 = '" + Option4.getText() + "', Answer" +
                                        " = '" + Correct.getText() + "' WHERE Question = '" + QuestionId + "' LIMIT 1");

                                Statement stinf = con.createStatement();
                                stinf.execute("SET SQL_SAFE_UPDATES = 1");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        Question.clear();
                        Option1.clear();
                        Option2.clear();
                        Option3.clear();
                        Option4.clear();
                        Correct.clear();
                    }
                    else
                        {
                            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Correct option does not match with the given options.", ButtonType.OK);
                            alert1.setTitle("Error");
                            alert1.showAndWait();
                        }

                }
            });
    }



    @FXML
    void chooseIt()
    {
        String[] sub = {"subject1", "subject2", "subject3", "subject4"};
        TextField[] fields = {Question, Option1, Option2, Option3, Option4, Correct};
        Connection con = Database.con;
        String choice = (String) choose.getSelectionModel().getSelectedItem();
        if(choice.matches("\\d+"))
        {
            try {
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT * FROM "+sub[Homepage.num-1]);
                String[] results = new String[6];
                int i = 0;
                for(int d = 1; d <= Integer.valueOf(choice); d++)
                {
                    rs1.next();
                }
                while(i < 6)
                {
                    results[i] = rs1.getString(i+2);
                    fields[i].setText(results[i]);
                    i++;
                }
                QuestionId = results[0];

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void back()
    {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("quiz.fxml"));
            stage.setScene(new Scene(p));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void Name()
    {
        try {
                Parent p = FXMLLoader.load(getClass().getResource("Name.fxml"));
                stage.setScene(new Scene(p));
                stage.initOwner(Main.stage);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choose.setVisibleRowCount(5);
        Connection con = Database.con;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Namess WHERE Id = 1");
            rs.next();

            String s = rs.getString(Homepage.num+1);
                if(!(s == null))
                {
                    Sub.setText(s);
                }

            String[] sub = {"subject1", "subject2", "subject3", "subject4"};
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT COUNT(*) FROM "+sub[Homepage.num-1]);
            rs1.next();
            choose.setValue("New");
            int i = rs1.getInt(1);
            arrayList = new ArrayList<>();
            arrayList.add("New");
            for(int su = 1; su <= i; su++)
            {
                arrayList.add(Integer.toString(su));
            }
            ObservableList<String> questionN = FXCollections.observableList(arrayList);
            choose.setItems(questionN);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    void setName()
    {

        Connection conn = Database.con;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Namess WHERE Id = 1");
            rs.next();
            String s = rs.getString(Homepage.num + 1);
            if (!(s == null)) {
                Sub.setText(s);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
