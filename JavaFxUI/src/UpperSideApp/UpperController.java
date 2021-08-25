package UpperSideApp;

import MainComponent.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class UpperController {

    @FXML
    private AppController mainController;

    @FXML
    private Button openFileButton;
    @FXML
    private Label fileLabel;
    @FXML
    private Label fullPathLabel;
    @FXML
    private Label statusLabel;

    private Stage primaryStage;

    @FXML
    public void initialize() {

    }


    public void setFileLabelsProperty(){
        fullPathLabel.textProperty().bind(mainController.getModel().filePathProperty());
        statusLabel.textProperty().bind(mainController.getModel().loadResultProperty());
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public AppController getMainController() {
        return mainController;
    }

    public Button getOpenFileButton() {
        return openFileButton;
    }

    public Label getFileLabel() {
        return fileLabel;
    }

    public Label getFullPathLabel() {
        return fullPathLabel;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void openFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile == null) {
            return;
        }

       //primaryStage.initModality(Modality.NONE);


        String absolutePath = selectedFile.getAbsolutePath(); // full path of xml
        mainController.modelLoadXmlFile(absolutePath);




    }

}