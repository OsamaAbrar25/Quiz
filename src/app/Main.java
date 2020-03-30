package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    static Stage stage;

    @Override
            public void start(Stage stage) throws Exception
            {
                this.stage = stage;
                Parent scene = FXMLLoader.load(getClass().getResource("quiz.fxml"));
                stage.setTitle("Quiz");
                stage.setScene(new Scene(scene));
                stage.show();
            }

    public static void main(String[] args)
    {
        Database.InitializeData();
        launch(args);
    }

}
