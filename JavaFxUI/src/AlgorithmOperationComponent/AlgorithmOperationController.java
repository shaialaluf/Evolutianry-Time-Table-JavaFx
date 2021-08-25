package AlgorithmOperationComponent;

import Algorithm.FinishConditions.FinishByFitness;
import Algorithm.FinishConditions.FinishByGenerationsNumber;
import Algorithm.FinishConditions.FinishByMinutes;
import Algorithm.FinishConditions.FinishCondition;
import MainComponent.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.naming.Binding;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmOperationController {

    @FXML
    private AppController mainController;
    @FXML
    private Label invalidTimeLabel;
    @FXML
    private Label invalidFitnessLabel;
    @FXML
    private Label invalidGeneralError;
    @FXML
    private HBox timeHbox;

    @FXML
    private CheckBox timeCheckBox;

    @FXML
    private TextField timeTextField;

    @FXML
    private VBox timeVbox;

    @FXML
    private ProgressBar timeProgressBar;

    @FXML
    private HBox generationsHbox;

    @FXML
    private CheckBox gereationsCheckBox;

    @FXML
    private TextField gereationsTextField;

    @FXML
    private VBox gereationsVbox;

    @FXML
    private ProgressBar gereationsProgressBar;

    @FXML
    private HBox fitnessHbox;

    @FXML
    private CheckBox fiitnessCheckBox;

    @FXML
    private TextField fitnessTextField;

    @FXML
    private VBox fitnessVbox;

    @FXML
    private HBox frequencyHbox;

    @FXML
    private Button reloadButton;

    @FXML
    private ProgressBar fitnessProgressBar;
    private boolean generationValid = false;
    private boolean fitnessValid = false;
    private boolean timeValid = false;
    private boolean frequencyValid=false;

    @FXML
    private TextField frequencyTextField;

    @FXML
    private VBox AlgoResultVbox;
    @FXML
    private HBox timeProgressHbox;
    @FXML
    private HBox generationProgressHbox;
    @FXML
    private HBox fitnessProgressHbox;

    @FXML
    private Label currentGenerationNumberLabel;

    @FXML
    private Label currentBestFitnessLabel;

    @FXML
    private Label chooseEndConditionLabel;

    @FXML
    private ProgressIndicator timeProgressIndicator;
    @FXML
    private ProgressIndicator fitnessProgressIndicator;
    @FXML
    private ProgressIndicator generationProgressIndicator;

    @FXML
    private Label algorithmProgressionLabel;

    @FXML
    private Button runAlgorithmButton;

    @FXML
    private Label invalidGenerationsLabel;

    @FXML
    private Label invalidFrequencyLabel;


    private int generationConditionNumberToAlgorithm;
    private float fitnessConditionNumberToAlgorithm;
    private int timeConditionNumberToAlgorithm;
    private int frequencyNumberToAlgorithm;


    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        setTextFieldsAndCheckBoxes();
        setTextFieldsError();
        setDisableWhenAllCheckBoxUnselected();
        setErrorMessageWhenAllCheckBoxUnselected();
        setDisableProgressBars();
    }

    public void setProperties(){
        currentGenerationNumberLabel.textProperty().bind(mainController.getFromModelTaskGenerationProperty());
       currentBestFitnessLabel.textProperty().bind(mainController.getFromModelTaskFitnessProperty());
       gereationsProgressBar.progressProperty().bind((mainController.getFromModelTaskFinishGenerationProperty()));
       generationProgressIndicator.progressProperty().bind(gereationsProgressBar.progressProperty());
       fitnessProgressBar.progressProperty().bind(mainController.getFromModelTaskFinishFitnessProperty());
        fitnessProgressIndicator.progressProperty().bind(fitnessProgressBar.progressProperty());
       timeProgressBar.progressProperty().bind(mainController.getFromModelTaskFinishTimeProperty());
        timeProgressIndicator.progressProperty().bind(timeProgressBar.progressProperty());
       reloadButton.disableProperty().bind(mainController.getFromModelTaskIsDoneProperty().not());
    }

    public void setErrorMessageWhenAllCheckBoxUnselected(){

    }

    public void setDisableWhenAllCheckBoxUnselected() {
        runAlgorithmButton.disableProperty().bind(fiitnessCheckBox.selectedProperty().not().and(timeCheckBox.selectedProperty().not()).and(gereationsCheckBox.selectedProperty().not()));
    }

    public void setDisableProgressBars(){
        generationProgressHbox.disableProperty().bind(gereationsCheckBox.selectedProperty().not());
        fitnessProgressHbox.disableProperty().bind(fiitnessCheckBox.selectedProperty().not());
        timeProgressHbox.disableProperty().bind(timeCheckBox.selectedProperty().not());
    }

    private void setTextFieldsError() {
        gereationsTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // focus lost
                checkGenerationsTextField();
            }

        });
        fitnessTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // focus lost
                checkFitnessTextField();
            }
        });
        timeTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // focus lost
                checkTimeTextField();
            }
        });

        frequencyTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // focus lost
                checkFrequencyTextField();
            }
        });
    }


    private void setTextFieldsAndCheckBoxes() {
        fitnessTextField.disableProperty().bind(fiitnessCheckBox.selectedProperty().not());
        gereationsTextField.disableProperty().bind(gereationsCheckBox.selectedProperty().not());
        timeTextField.disableProperty().bind(timeCheckBox.selectedProperty().not());
    }


    @FXML
    void runAlgorithmClick(ActionEvent event) {
        if ((!fitnessValid && fiitnessCheckBox.isSelected()) || (!generationValid && gereationsCheckBox.isSelected()) || (!timeValid && timeCheckBox.isSelected()) ||(!frequencyValid)) {
            if(!fitnessValid && fiitnessCheckBox.isSelected())
            {
                checkFitnessTextField();
            }

            if(!generationValid && gereationsCheckBox.isSelected())
            {
                checkGenerationsTextField();
            }

            if(!timeValid && timeCheckBox.isSelected())
            {
                checkTimeTextField();
            }

            if(!frequencyValid){
                checkFrequencyTextField();
            }

             invalidGeneralError.setVisible(true);



        } else {
            switchToAlgorithmRunningScreen();
            mainController.startRunningAlgorithm();
        }
    }

    public void checkGenerationsTextField() {
        try {
            int num = Integer.parseInt(gereationsTextField.getText());
            if (num <= 100) {
                invalidGenerationsLabel.setText("Invalid input! enter number bigger than 100");
                generationValid=false;

            } else {
                generationConditionNumberToAlgorithm = num;
                generationValid=true;
                invalidGenerationsLabel.setText("");
            }
        } catch (NumberFormatException e) {
            invalidGenerationsLabel.setText("Invalid input! enter number bigger than 100");
            generationValid=false;
        }
    }

    public void checkFitnessTextField() {
        try {
            float num = Float.parseFloat(fitnessTextField.getText());
            if (num < 0 || num > 100) {
                invalidFitnessLabel.setText("Invalid input! enter number between 0 - 100");
                fitnessValid=false;
            } else {
                fitnessValid=true;
                fitnessConditionNumberToAlgorithm = num;
                invalidFitnessLabel.setText("");
            }
        } catch (NumberFormatException e) {
            fitnessValid=false;
            invalidFitnessLabel.setText("Invalid input! enter number between 0 - 100");
        }

    }

    public void checkTimeTextField() {
        try {
            int num = Integer.parseInt(timeTextField.getText());
            if (num <= 0) {
                invalidTimeLabel.setText("Invalid input! enter number natural number");
                timeValid=false;
            } else {
                timeValid=true;
                timeConditionNumberToAlgorithm = num;
                invalidTimeLabel.setText("");
            }
        } catch (NumberFormatException e) {
            timeValid=false;
            invalidTimeLabel.setText("Invalid input! enter number natural number");
        }

    }

    public void checkFrequencyTextField() {
        try{
            int num = Integer.parseInt(frequencyTextField.getText());
            if (num <= 0) {
                invalidFrequencyLabel.setText("Invalid input! " +System.lineSeparator() + "enter a natural number");
                frequencyValid=false;
            } else {
                frequencyValid=true;
                frequencyNumberToAlgorithm = num;
                invalidFrequencyLabel.setText("");
            }
        } catch (NumberFormatException e) {
            frequencyValid=false;
            invalidFrequencyLabel.setText("Invalid input! "+ System.lineSeparator() + "enter a natural number");
        }

    }

    public void switchToAlgorithmRunningScreen(){
        invalidGeneralError.setVisible(false);
        generationsHbox.setVisible(false);
        fitnessHbox.setVisible(false);
        timeHbox.setVisible(false);
        chooseEndConditionLabel.setVisible(false);
        frequencyHbox.setVisible(false);
        runAlgorithmButton.setVisible(false);

        mainController.initializeResumePauseButtons();
        generationProgressHbox.setVisible(true);
        fitnessProgressHbox.setVisible(true);
        timeProgressHbox.setVisible(true);
        AlgoResultVbox.setVisible(true);
        algorithmProgressionLabel.setVisible(true);
        reloadButton.setVisible(true);
    }

    public void switchToEndConditionsScreen(){
       // invalidGeneralError.setVisible(true);
        generationsHbox.setVisible(true);
        fitnessHbox.setVisible(true);
        timeHbox.setVisible(true);
        chooseEndConditionLabel.setVisible(true);
        frequencyHbox.setVisible(true);
        runAlgorithmButton.setVisible(true);

        fitnessProgressHbox.setVisible(false);
        timeProgressHbox.setVisible(false);
        generationProgressHbox.setVisible(false);
        AlgoResultVbox.setVisible(false);
        algorithmProgressionLabel.setVisible(false);
        reloadButton.setVisible(false);
    }

    public void bindPropertyToGenerationsCheckBox(BooleanProperty property){
        property.bind(gereationsCheckBox.selectedProperty());
    }

    public void bindPropertyToFitnessCheckBox(BooleanProperty property){
        property.bind(fiitnessCheckBox.selectedProperty());
    }

    public void bindPropertyToTimeCheckBox(BooleanProperty property){
        property.bind(timeCheckBox.selectedProperty());
    }

    public int getGenerationConditionNumberToAlgorithm() {
        return generationConditionNumberToAlgorithm;
    }

    public float getFitnessConditionNumberToAlgorithm() {
        return fitnessConditionNumberToAlgorithm;
    }

    public int getTimeConditionNumberToAlgorithm() {
        return timeConditionNumberToAlgorithm;
    }

    public int getFrequencyNumberToAlgorithm() {
        return frequencyNumberToAlgorithm;
    }

    @FXML
    void reloadButtonClick(ActionEvent event) {
        switchToEndConditionsScreen();
    }

}
