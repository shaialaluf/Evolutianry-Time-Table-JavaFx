package ControlPanelComponent;

import Algorithm.EvolutionaryAlgorithm;
import ControlPanelComponent.Mutations.FlippingController;
import ControlPanelComponent.Mutations.MutationController;
import ControlPanelComponent.Mutations.SizerController;
import EvolutionEngineDB.Crossovers.AspectOriented;
import EvolutionEngineDB.Crossovers.Crossover;
import EvolutionEngineDB.Mutations.Flipping;
import EvolutionEngineDB.Mutations.Mutation;
import EvolutionEngineDB.Mutations.MutationCollection;
import EvolutionEngineDB.Mutations.Sizer;
import EvolutionEngineDB.Selections.Selection;
import EvolutionEngineDB.Selections.Truncation;
import MainComponent.AppController;
import SchoolTimeTable.Subject;
import SchoolTimeTable.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ControlPanelController {
    private int populationSize;
    private boolean isSelectionValid = true;
    private boolean isElitismValid = true;
    private boolean isCrossoverValid = true;
    List<Pair<MutationController, Tab>> controllerArr;

    @FXML
    private AppController mainController;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button showBestSolutionsButton;

    @FXML
    private Accordion controlPanelAccordion;

    @FXML
    private TitledPane mutationsTiledPane;
    @FXML
    private VBox controlPanelVbox;
    @FXML
    private TabPane mutationsTabPane;

    @FXML
    private ComboBox<String> selectionTechniqueComboBox;

    @FXML
    private VBox topPrecentVbox;

    @FXML
    private TextField topPrecentTextField;

    @FXML
    private Label topPrecentErrorLabel;

    @FXML
    private Label currenElitismSIzeLabel;

    @FXML
    private Label elitismErrorLabel;

    @FXML
    private TextField elitismTextField;

    @FXML
    private Label populationSizeLabel;

    @FXML
    private ComboBox<String> crossoverTechniqueComboBox;

    @FXML
    private HBox orietationConfigurationHbox;

    @FXML
    private ComboBox<String> orientationComboBox;

    @FXML
    private Label crossoverGeneralErrorLabel;

    @FXML
    private TextField cuttingPointsTextField;
    @FXML
    private Label generalErrorLabel;

    private List<Mutation> mutationList;
    private EvolutionaryAlgorithm evolutionaryAlgorithm;

    @FXML
    void pauseButtonClicked(ActionEvent event) {
        pauseButton.setDisable(true);
        resumeButton.setDisable(false);
        mainController.pauseAlgorithmRunning();
    }

    public StringBuilder getErrorName() {
        StringBuilder errorList = new StringBuilder();
        if (!isElitismValid) {
            errorList.append("Elitism ,");
        }
        if (!isCrossoverValid) {
            errorList.append("Crossover ,");
        }
        if (!isSelectionValid) {
            errorList.append("Selection ,");
        }
        if (!isMutationsConfigurationValid()) {
            errorList.append("Mutations. ");
        }
        if (errorList.length() > 0) {
            errorList.setCharAt(errorList.length() - 1, ' ');

        }
        return errorList;
    }

    @FXML
    void resumeButtonClicked(ActionEvent event) {
        if (isMutationsConfigurationValid() && isElitismValid && isCrossoverValid && isSelectionValid) {
            setNewValuesToAlgorithm();
            mainController.resumeAlgorithmRunning();
            generalErrorLabel.setText("");
            if(!elitismTextField.getText().equals("")){
                currenElitismSIzeLabel.setText(elitismTextField.getText());
            }
            pauseButton.setDisable(false);
            resumeButton.setDisable(true);
        } else {
            generalErrorLabel.setText("Can't resume: you have error in: " + getErrorName().toString());
        }
    }

public void setPauseOnAndResumeOff(){
    pauseButton.setDisable(false);
    resumeButton.setDisable(true);
}
    @FXML
    void stopButtonClick(ActionEvent event) {
        mainController.changeToEndConditionsScreen();
       mainController.stopAlgorithmRunning();
    }

    @FXML
    void bestSolutionClick(ActionEvent event) {
        mainController.showBestSolution();

    }



    public void setNewValuesToAlgorithm(){
        setNewValuesToMutation();
        setSelectionNewValues();
        setCrossoverNewValues();
    }

    private void setNewValuesToMutation() {
        int indexMutation = 0;
        for (Pair<MutationController, Tab> currentMutation : controllerArr) {
            if (currentMutation.getKey().getClass().equals(FlippingController.class)) {
                FlippingController flippingController = (FlippingController) currentMutation.getKey();
                Flipping flippingMutation = (Flipping) mutationList.get(indexMutation);
                flippingMutation.setProbability(flippingController.getProbabilityValue());
                char currentComponentController = flippingController.getComponentValue();
                char currentComponentAlgorithm = flippingMutation.getComponent();

                if (currentComponentController != currentComponentAlgorithm) {
                    flippingMutation.setComponent(currentComponentController);
                }

                if (!flippingController.getMaxTupplesValue().equals("")) {
                    flippingMutation.setMaxTupples(Integer.parseInt(flippingController.getMaxTupplesValue()));
                }

            } else if (currentMutation.getKey().getClass().equals(SizerController.class)) {
                SizerController sizerController = (SizerController) currentMutation.getKey();
                Sizer sizerMutation = (Sizer) mutationList.get(indexMutation);
                sizerMutation.setProbability(sizerController.getProbabilityValue());

                if (!sizerController.getTotalTupplesValue().equals("")) {
                    sizerMutation.setTotalTupples(Integer.parseInt(sizerController.getTotalTupplesValue()));
                }

            }
            indexMutation++;
        }
    }
    public void setAccordionDisability(){
        controlPanelAccordion.disableProperty().bind(pauseButton.disableProperty().not());
    }

    public void setCrossoverNewValues(){
        evolutionaryAlgorithm.selectedCrossover(crossoverTechniqueComboBox.getSelectionModel().selectedItemProperty().get(),cuttingPointsTextField.getText(), orientationComboBox.getSelectionModel().selectedItemProperty().get());
    }


    public void setSelectionNewValues(){
        evolutionaryAlgorithm.selectedSelection(selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get(),topPrecentTextField.getText(),elitismTextField.getText());

     //   if(!selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get().equals(evolutionaryAlgorithm.getSelection().getClass().getSimpleName())){
//            String elitism=elitismTextField.getText();
//            if(elitism.equals("")){
//                elitism=Integer.toString(evolutionaryAlgorithm.getSelection().getElitism());
//            }
//
//            if(selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get().equals("Truncation")){
//
//                evolutionaryAlgorithm.replaceSelectionToTruncation(topPrecentTextField.getText(),elitism);
//            }
//            else if(selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get().equals("RouletteWheel")){
//                evolutionaryAlgorithm.replaceSelectionToRouletteWheel(elitism);
//            }
//        }
//        else {
//            if(selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get().equals("Truncation")){
//                if(!topPrecentTextField.getText().equals("")){
//                    ((Truncation)evolutionaryAlgorithm.getSelection()).setTopPercent(Integer.parseInt(topPrecentTextField.getText()));
//                }
//            }
        }



    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        setAccordionDisability();
        setComboBoxes();
    }

    public void setElitismLabels() {
        populationSize = mainController.getModel().getEvolutionaryObject().getInitialPopulation();
        populationSizeLabel.setText(Integer.toString(populationSize));
        currenElitismSIzeLabel.setText(Integer.toString(mainController.getModel().getEvolutionaryObject().getSelection().getElitism()));

    }

    public void setComboBoxes() {
        setCrossoverComboBox();
        setSelectionComboBox();
        setOrientationComboBox();

    }

    public void checkTopPrecentTextField() {
        try {
                int num = Integer.parseInt(topPrecentTextField.getText());
                if (num <= 0 || num > 100) {
                    topPrecentErrorLabel.setVisible(true);
                    isSelectionValid = false;
                } else {
                    topPrecentErrorLabel.setVisible(false);
                    isSelectionValid = true;
                }
        } catch (NumberFormatException e) {
            topPrecentErrorLabel.setVisible(true);
            isSelectionValid = false;
        }

    }

    @FXML
    public void checkCuttingPointTextTextField() {
        if (cuttingPointsTextField.getText().length() == 0) {
            crossoverGeneralErrorLabel.setVisible(false);
            isCrossoverValid = true;
        } else {
            try {
                int num = Integer.parseInt(cuttingPointsTextField.getText());
                if (num <= 0) {
                    crossoverGeneralErrorLabel.setVisible(true);
                    isCrossoverValid = false;
                } else {
                    crossoverGeneralErrorLabel.setVisible(false);
                    isCrossoverValid = true;
                }
            } catch (NumberFormatException e) {
                crossoverGeneralErrorLabel.setVisible(true);
                isCrossoverValid = false;
            }
        }
    }

    @FXML
    public void checkElitismTextField() {
        if (elitismTextField.getText().length() == 0) {
            elitismErrorLabel.setVisible(false);
            isElitismValid = true;
        } else {
            try {
                int num = Integer.parseInt(elitismTextField.getText());
                if (num < 0 || num >= populationSize) {
                    elitismErrorLabel.setVisible(true);
                    isElitismValid = false;
                } else {
                    elitismErrorLabel.setVisible(false);
                    isElitismValid = true;
                }
            } catch (NumberFormatException e) {
                elitismErrorLabel.setVisible(true);
                isElitismValid = false;
            }
        }
    }

    @FXML
    private void comboBoxSelection(ActionEvent event) {
        if (selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get() == "Truncation") {
            topPrecentVbox.setVisible(true);

            if((evolutionaryAlgorithm.getSelection().getClass().getSimpleName().equals("RouletteWheel") && topPrecentTextField.getText().equals(""))){
                topPrecentErrorLabel.setVisible(true);
                isSelectionValid=false;
            }


        } else if (selectionTechniqueComboBox.getSelectionModel().selectedItemProperty().get() == "RouletteWheel") {
            isSelectionValid = true;
            topPrecentVbox.setVisible(false);
        }
    }

    @FXML
    private void comboBoxCrossover(ActionEvent event) {
        if (crossoverTechniqueComboBox.getSelectionModel().selectedItemProperty().get() == "DayTimeOriented") {
            orietationConfigurationHbox.setVisible(false);
        } else if (crossoverTechniqueComboBox.getSelectionModel().selectedItemProperty().get() == "AspectOriented") {
            orietationConfigurationHbox.setVisible(true);
        }
    }

    @FXML
    void checkTopPrecentTextField(KeyEvent event) {
        checkTopPrecentTextField();
    }


    public void setSelectionComboBox() {
        selectionTechniqueComboBox.getItems().addAll("RouletteWheel", "Truncation");

    }

    public void setOrientationComboBox() {
        orientationComboBox.getItems().addAll("Teacher", "Class");
    }

    public void setCrossoverComboBox() {
        crossoverTechniqueComboBox.getItems().addAll("DayTimeOriented", "AspectOriented");
    }

    public void setProperties() {
        controlPanelVbox.disableProperty().bind(mainController.getIsTaskRunningProperty().not());
    }

    public boolean isMutationsConfigurationValid() {
        for (Pair<MutationController, Tab> currentMutation : controllerArr) {
            if (!currentMutation.getKey().isMutationValid()) {
                return false;
            }
        }
        return true;
    }

    public void setMutationTabs(List<Mutation> mutationsList) {
        this.mutationList=mutationsList;
        TabPane mutationsTabPaneTemp = new TabPane();
        mutationsTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        controllerArr = new ArrayList<>();
        for (Mutation mutation : mutationsList) {
            FXMLLoader fxmlLoader = new FXMLLoader();

            // FXMLLoader fxmlLoader=new FXMLLoader();
            Tab currMutationTab = new Tab(mutation.getClass().getSimpleName());
            if (mutation.getClass().equals(Flipping.class)) {
                try {
                    URL url = (getClass().getResource("/ControlPanelComponent/Mutations/flippingComponent.fxml"));
                    fxmlLoader.setLocation(url);
                    currMutationTab.setContent(fxmlLoader.load(url.openStream()));
                    FlippingController flippingController=fxmlLoader.getController();
                    controllerArr.add(new Pair<MutationController, Tab>(flippingController, currMutationTab));
                    flippingController.setSlidersDefaultValues(mutation);
                    flippingController.setComponentComboBoxDefaultValue(mutation);

                } catch (Exception e) {

                }

            } else if (mutation.getClass().equals(Sizer.class)) {
                try {
                    URL url = (getClass().getResource("/ControlPanelComponent/Mutations/sizerComponent.fxml"));
                    fxmlLoader.setLocation(url);
                    currMutationTab.setContent(fxmlLoader.load(url.openStream()));
                    SizerController sizerController=fxmlLoader.getController();
                    controllerArr.add(new Pair<MutationController, Tab>(sizerController, currMutationTab));//////////////////////////////
                    sizerController.setSlidersDefaultValues(mutation);
                } catch (Exception e) {

                }
            }

            mutationsTabPaneTemp.getTabs().add(currMutationTab);


        }
        mutationsTiledPane.setContent(mutationsTabPaneTemp);

        //  teachers.setContent(teachersTabPaneTemp);

    }

    public void setSelectionDefaultValue(Selection selection){
        if(selection.getClass().getSimpleName().equals("Truncation")){
            selectionTechniqueComboBox.getSelectionModel().select("Truncation");
        }
        else if(selection.getClass().getSimpleName().equals("RouletteWheel")){
            selectionTechniqueComboBox.getSelectionModel().select("RouletteWheel");
        }
    }

    public void setCrossoverDefaultValue(Crossover crossover){
        if(crossover.getClass().getSimpleName().equals("DayTimeOriented")){
            crossoverTechniqueComboBox.getSelectionModel().select("DayTimeOriented");
            orientationComboBox.getSelectionModel().selectFirst();
        }
        else if(crossover.getClass().getSimpleName().equals("AspectOriented")){
            crossoverTechniqueComboBox.getSelectionModel().select("AspectOriented");
            orientationComboBox.getSelectionModel().select(((AspectOriented)crossover).getType().toString());
        }

    }

    public void setEvolutionaryAlgorithm(EvolutionaryAlgorithm evolutionaryAlgorithm){
        this.evolutionaryAlgorithm=evolutionaryAlgorithm;
    }

}
