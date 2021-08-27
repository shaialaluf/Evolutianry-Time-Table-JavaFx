package Model;

import Algorithm.EvolutionaryAlgorithm;
import Algorithm.FinishConditions.FinishByFitness;
import Algorithm.FinishConditions.FinishByGenerationsNumber;
import Algorithm.FinishConditions.FinishByMinutes;
import Algorithm.FinishConditions.FinishCondition;
import Algorithm.TimeTable;
import AlgorithmTask.EvolutionaryAlgorithmTask;
import CoreEvolution.SystemManager;
import CoreEvolution.Systemic;
import MainComponent.AppController;
import SchoolTimeTable.SchoolDB;
import UpperSideApp.UpperController;
import exception.*;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmModel {
    private AppController mainAppController;
    private Systemic currentSystem = new SystemManager();
    private StringProperty filePath = new SimpleStringProperty();
    private StringProperty loadResult = new SimpleStringProperty();
    private BooleanProperty isFileLoadedSuccessfully = new SimpleBooleanProperty(false);
    private BooleanProperty isNewSuccessfulLoad = new SimpleBooleanProperty(false);
    private BooleanProperty isGenerationsConditionSelected = new SimpleBooleanProperty();
    private BooleanProperty isFitnessConditionSelected = new SimpleBooleanProperty();
    private BooleanProperty isTimeConditionSelected = new SimpleBooleanProperty();
    private Thread algorithmThread;
    private EvolutionaryAlgorithmTask algorithmTask;


    public AlgorithmModel(AppController mainAppController) {
        this.mainAppController = mainAppController;
        mainAppController.bindPropertiesToAlgorithmOperationController(isGenerationsConditionSelected, isFitnessConditionSelected, isTimeConditionSelected);
    }


    public void buildDataFromXml(String fileName) {
        loadResult.set("File loaded successfully");
        // isNewSuccessfulLoad.set(false);
        try {

            Systemic tempSystem = new SystemManager();
            tempSystem = tempSystem.loadDataFromXmlFile(fileName);

            filePath.set(fileName);
            isFileLoadedSuccessfully.set(true);
            isNewSuccessfulLoad.set(true);
            if (isAlgorithmAlive()) {
                interruptAlgorithmThread();
            }
            while (isAlgorithmAlive()) {

            }
            currentSystem = tempSystem;
            mainAppController.setControlPanelComponents();
            mainAppController.changeToEndConditionsScreen();
            mainAppController.setDisableBestSolution(true);
        } catch (TeacherWithIllegalSubjectException | TeachersIdNotSequentialException | SubjectsIdNotSequentialException | RuleAppearsTwiceException
                | FileErrorException | ClassWithIllegalSubjectException | ClassPassedHoursLimitException | ClassesIdNotSequentialException
                | ElitismBiggerThanPopulationException e) {
            loadResult.set(e.getMessage());

        } catch (Exception e) {
            loadResult.set(e.getMessage());
        }

    }

    public void runAlgorithm() {
        List<FinishCondition> finishConditionList = new ArrayList<>();
        if (isGenerationsConditionSelected.getValue()) {
            finishConditionList.add(new FinishByGenerationsNumber(mainAppController.getGenerationsConditionValue()));
        }
        if (isFitnessConditionSelected.getValue()) {
            finishConditionList.add(new FinishByFitness(mainAppController.getFitnessConditionValue()));
        }
        if (isTimeConditionSelected.getValue()) {
            finishConditionList.add(new FinishByMinutes(mainAppController.getTimeConditionValue()));
        }

        int frequency = mainAppController.getFrequencyValue();

        algorithmTask = new EvolutionaryAlgorithmTask(currentSystem, finishConditionList, frequency, mainAppController);
        algorithmThread = new Thread(algorithmTask);
        algorithmThread.setName("Algorithm Thread");
        algorithmThread.setDaemon(true);
        algorithmThread.start();
    }

    public boolean isAlgorithmAlive() {
        return (algorithmThread != null && algorithmThread.isAlive());
    }

    public EvolutionaryAlgorithm getEvolutionaryObject() {
        return currentSystem.getEvolutionaryObject();
    }

    public void interruptAlgorithmThread() {
        if (algorithmThread != null) {
            algorithmThread.interrupt();

        }
//     while   (algorithmThread.isInterrupted()){
//            System.out.printf("asfkhflaskasfhgaskfgkagfsa");
//    }}
    }
    public TimeTable getBestSolutionTimeTable() {
        return currentSystem.getBestSolutionTimeTable();
    }
    public TimeTable getBestSolution() {
        return currentSystem.getBestSolution();
    }

    public void pauseAlgorithm() {
        currentSystem.pauseAlgorithm();
    }

    public void resumeAlgorithm() {
        currentSystem.resumeAlgorithm();

    }

    public SchoolDB getSchoolSettings() {
        return currentSystem.getSchoolSettings();
    }

    public String getAlgorithmSettings() {
        return currentSystem.getEvolutionaryDetails();
    }

    public String showSystemDetails() {
        return currentSystem.showSystemDetails();
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public StringProperty loadResultProperty() {
        return loadResult;
    }

    public BooleanProperty isFileLoadedSuccessfullyProperty() {
        return isFileLoadedSuccessfully;
    }

    public BooleanProperty isNewSuccessfulLoadProperty() {
        return isNewSuccessfulLoad;
    }

    public void setIsNewSuccessfulLoad(boolean isNewSuccessfulLoad) {
        this.isNewSuccessfulLoad.set(isNewSuccessfulLoad);
    }

    public BooleanProperty getIsTaskRunning() {
        return EvolutionaryAlgorithmTask.isRunningProperty();
    }

    public StringProperty getTaskFitnessProperty() {
        return EvolutionaryAlgorithmTask.getCurrentFitnessProperty();
    }

    public StringProperty getTaskGenerationProperty() {
        return EvolutionaryAlgorithmTask.getCurrentGenerationProperty();
    }

    public FloatProperty getTaskFinishGenerationProperty() {
        return EvolutionaryAlgorithmTask.getFinishGenerationsRatioProperty();
    }

    public FloatProperty getTaskFinishFitnessProperty() {
        return EvolutionaryAlgorithmTask.getFinishFitnessRatioProperty();
    }

    public FloatProperty getTaskFinishTimeProperty() {
        return EvolutionaryAlgorithmTask.getFinishTimeRatioProperty();
    }

    public BooleanProperty getTaskIsDoneProperty() {
        return EvolutionaryAlgorithmTask.isFinishedProperty();
    }

}
