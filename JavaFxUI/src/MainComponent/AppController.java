package MainComponent;

import AlgorithmOperationComponent.AlgorithmOperationController;
import BestSolutionComponent.BestSolutionController;
import ControlPanelComponent.ControlPanelController;
import EvolutionEngineDB.Mutations.Mutation;
import Model.AlgorithmModel;
import SchoolTimeTable.SchoolDB;
import SystemDetailsComponent.SystemDetailsController;
import UpperSideApp.UpperController;
import XmlReader.SchemaBasedConvertor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class AppController {
    @FXML
    private ScrollPane upAppComponent;
    @FXML
    private UpperController upAppComponentController;
    @FXML
    private SystemDetailsController DetailsAndAlgoTabController;
    @FXML
    private ControlPanelController controlPanelController;
    @FXML
    private BestSolutionController solutionComponentController;
    @FXML
    private ScrollPane controlPanel;
    @FXML
    private ScrollPane solutionComponent;
    @FXML
    private TabPane DetailsAndAlgoTab;

    private AlgorithmModel model;


    public AppController() {
    }

    @FXML
    public void initialize() {
        if (upAppComponentController != null && DetailsAndAlgoTabController != null&& controlPanelController!=null && solutionComponentController!=null) {
            model = new AlgorithmModel(this);

            upAppComponentController.setMainController(this);
            upAppComponentController.setFileLabelsProperty();


            DetailsAndAlgoTabController.setMainController(this);
            DetailsAndAlgoTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            DetailsAndAlgoTab.disableProperty().bind(model.isFileLoadedSuccessfullyProperty().not());
            DetailsAndAlgoTabController.getAlgorithmOperationComponentController().setProperties();

            controlPanelController.setMainController(this);
            controlPanelController.setProperties();


            solutionComponentController.setMainController(this);

        }


    }

    public void startRunningAlgorithm(){
        model.runAlgorithm();
    }
public void setControlPanelComponents(){
        controlPanelController.setEvolutionaryAlgorithm(model.getEvolutionaryObject());
        controlPanelController.setMutationTabs(model.getEvolutionaryObject().getMutationList());
        controlPanelController.setElitismLabels();
        controlPanelController.setSelectionDefaultValue(model.getEvolutionaryObject().getSelection());
        controlPanelController.setCrossoverDefaultValue(model.getEvolutionaryObject().getCrossover());
}

    public void pauseAlgorithmRunning() {
        model.pauseAlgorithm();
    }

    public void resumeAlgorithmRunning() {
        model.resumeAlgorithm();

    }

    public void showBestSolution(){
       solutionComponentController.showBestSolution(model.getSchoolSettings(),model.getBestSolution());
    }

    public AlgorithmModel getModel() {
        return model;
    }

    public String modelShowDetails() {
        return model.showSystemDetails();
    }

    public SchoolDB getSchoolSettings() {
        return model.getSchoolSettings();
    }

    public void modelLoadXmlFile(String fileName) {
        model.buildDataFromXml(fileName);
    }

    public void bindPropertiesToAlgorithmOperationController(BooleanProperty isGenerationsConditionSelected, BooleanProperty isFitnessConditionSelected, BooleanProperty isTimeConditionSelected){
        DetailsAndAlgoTabController.getAlgorithmOperationComponentController().bindPropertyToGenerationsCheckBox(isGenerationsConditionSelected);
        DetailsAndAlgoTabController.getAlgorithmOperationComponentController().bindPropertyToFitnessCheckBox(isFitnessConditionSelected);
        DetailsAndAlgoTabController.getAlgorithmOperationComponentController().bindPropertyToTimeCheckBox(isTimeConditionSelected);
    }

    public float getFitnessConditionValue(){
        return DetailsAndAlgoTabController.getAlgorithmOperationComponentController().getFitnessConditionNumberToAlgorithm();
    }

    public int getGenerationsConditionValue(){
        return DetailsAndAlgoTabController.getAlgorithmOperationComponentController().getGenerationConditionNumberToAlgorithm();
    }


    public int getTimeConditionValue(){
        return DetailsAndAlgoTabController.getAlgorithmOperationComponentController().getTimeConditionNumberToAlgorithm();
    }

    public int getFrequencyValue(){
        return DetailsAndAlgoTabController.getAlgorithmOperationComponentController().getFrequencyNumberToAlgorithm();
    }


    public String getAlgorithmDetails() {
        return model.getAlgorithmSettings();
    }

    public void setPrimaryStageOfUpComponent(Stage stage){
        upAppComponentController.setPrimaryStage(stage);
    }

    public StringProperty getFromModelTaskFitnessProperty(){
        return model.getTaskFitnessProperty();
    }

    public StringProperty getFromModelTaskGenerationProperty(){
        return model.getTaskGenerationProperty();
    }
public BooleanProperty getIsTaskRunningProperty(){
        return model.getIsTaskRunning();
}
    public FloatProperty getFromModelTaskFinishGenerationProperty(){ return model.getTaskFinishGenerationProperty();}

    public FloatProperty getFromModelTaskFinishFitnessProperty(){ return model.getTaskFinishFitnessProperty();}

    public FloatProperty getFromModelTaskFinishTimeProperty(){ return model.getTaskFinishTimeProperty();}

    public BooleanProperty getFromModelTaskIsDoneProperty(){ return model.getTaskIsDoneProperty();}

    public void changeToEndConditionsScreen(){
        DetailsAndAlgoTabController.getAlgorithmOperationComponentController().switchToEndConditionsScreen();
    }
    public void initializeResumePauseButtons(){
        controlPanelController.setPauseOnAndResumeOff();
    }

    public void stopAlgorithmRunning(){
        model.interruptAlgorithmThread();
    }
}
