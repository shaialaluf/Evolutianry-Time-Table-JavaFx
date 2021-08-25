import MainComponent.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class EvolutionaryAlgorithmApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource("/MainComponent/ApplicationMainFxml.fxml");
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();


        // set stage
        primaryStage.setTitle("EvolutionaryAlgorithm");
        Scene scene = new Scene(root, 1200, 900);
        AppController appController=loader.getController();
        appController.setPrimaryStageOfUpComponent(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

        public static void main(String[] args) {
        EvolutionaryAlgorithmApplication.launch(args);

        //MainMenu.runMenu();
    }
}
