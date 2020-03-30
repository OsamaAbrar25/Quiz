package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class QA implements Initializable {

    @FXML
    Label Question1;
    @FXML
    RadioButton Option1;
    @FXML
    RadioButton Option2;
    @FXML
    RadioButton Option3;
    @FXML
    RadioButton Option4;
    @FXML
    Label Correct;
    @FXML
    Label Sub;

    static int QuestioNo = 1;

    private Stage stage = Main.stage;

    @FXML
    void exit()
    {
        stage.close();
    }

    @FXML
    void submit()
    {
        String selected = "";
        Connection con = Database.con;
        if(Option1.isSelected())
        {
            selected = Option1.getText();
        }

        else if(Option2.isSelected())
        {
            selected = Option2.getText();
        }

        else if(Option3.isSelected())
        {
            selected = Option3.getText();
        }

        else if(Option4.isSelected())
        {
            selected = Option4.getText();
        }

        try {
            String[] sub = {"subject1", "subject2", "subject3", "subject4"};
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT Answer FROM " + sub[Homepage.num - 1]+" WHERE Question = '"+Question1.getText()+"'");
            rs.next();
            String results = rs.getString(1);
            if(results.equalsIgnoreCase(selected))
            {
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT COUNT(*) FROM "+sub[Homepage.num-1]);
                rs2.next();
                int i = rs2.getInt(1);

                Correct.setTextFill(Color.GREEN);
                Correct.setText("Correct");

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3) , event -> {
                    if(i > QuestioNo) {
                        ++QuestioNo;
                        try {
                            Statement st3 = con.createStatement();
                            ResultSet rs3 = st3.executeQuery("SELECT * FROM " + sub[Homepage.num - 1]);
                            String[] results1 = new String[6];

                            for (int d = 1; d <= QuestioNo; d++) {
                                rs3.next();
                            }
                            int i1 = 0;
                            RadioButton[] fields = {Option1, Option2, Option3, Option4};
                            while (i1 < 6) {
                                if (i1 < 4) {
                                    results1[i1] = rs3.getString(i1 + 3);
                                    fields[i1].setText(results1[i1]);
                                } else if (i1 == 4) {
                                    Question1.setText(rs3.getString(2));
                                }
                                i1++;
                            }

                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        }

                    else
                    {
                        try {
                            Parent pa = FXMLLoader.load(getClass().getResource("End.fxml"));
                            stage.setScene(new Scene(pa));
                            stage.show();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }));
                timeline.play();
            }
            else
            {
                Correct.setTextFill(Color.RED);
                Correct.setText("Wrong answer");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] sub = {"subject1", "subject2", "subject3", "subject4"};
        Connection con = Database.con;
        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+sub[Homepage.num-1]);
            String[] results = new String[6];
            rs.next();
            int i = 0;
            RadioButton[] fields = {Option1, Option2, Option3, Option4};
            while(i < 6)
            {
                if(i<4) {
                    results[i] = rs.getString(i + 3);
                    fields[i].setText(results[i]);
                }
                else if(i == 4)
                {
                    Question1.setText(rs.getString(2));
                }
                i++;
            }

            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM Namess WHERE Id = 1");
            rs1.next();

            String s = rs1.getString(Homepage.num+1);
            if(!s.equals(""))
            {
                Sub.setText(s);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
