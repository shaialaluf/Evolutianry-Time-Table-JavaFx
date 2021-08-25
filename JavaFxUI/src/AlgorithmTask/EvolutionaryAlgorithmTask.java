package AlgorithmTask;

import Algorithm.FinishConditions.FinishByFitness;
import Algorithm.FinishConditions.FinishByGenerationsNumber;
import Algorithm.FinishConditions.FinishByMinutes;
import Algorithm.FinishConditions.FinishCondition;
import AlgorithmOperationComponent.AlgorithmOperationController;
import CoreEvolution.Systemic;
import MainComponent.AppController;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.util.Pair;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EvolutionaryAlgorithmTask extends Task<Boolean> {
    private Systemic currentSystem;
    private static int frequency;
    private List<FinishCondition> finishConditionList;
    private static StringProperty currentFitness=new SimpleStringProperty();
    private static StringProperty currentGeneration=new SimpleStringProperty();
    private static FloatProperty finishGenerationsRatio=new SimpleFloatProperty(0);
    private static FloatProperty finishFitnessRatio=new SimpleFloatProperty(0);
    private static FloatProperty finishTimeRatio=new SimpleFloatProperty(0);
    private static BooleanProperty isFinished=new SimpleBooleanProperty(false);
    private static BooleanProperty isRunning=new SimpleBooleanProperty(false);


    public EvolutionaryAlgorithmTask(Systemic currentSystem , List<FinishCondition> finishConditionList, int frequency) {
        this.currentSystem = currentSystem;
        this.finishConditionList=finishConditionList;
        setFrequency(frequency);

    }

    public static void setFrequency(int frequency) {
        EvolutionaryAlgorithmTask.frequency = frequency;
    }


    public static BooleanProperty isRunningProperty() {
        return isRunning;
    }

    public static BooleanProperty isFinishedProperty() {
        return isFinished;
    }

    @Override
    protected Boolean call() throws Exception {
        isRunning.setValue(true);
        finishGenerationsRatio.setValue(0);
        finishFitnessRatio.setValue(0);
        finishTimeRatio.setValue(0);
        isFinished.setValue(false);
        currentSystem.run(finishConditionList,frequency,EvolutionaryAlgorithmTask::updateUiGenerations,EvolutionaryAlgorithmTask::updateUiProgress);
        isFinished.setValue(true);
        isRunning.setValue(false);
        return true;
    }


    public static void updateUiProgress(List<FinishCondition> conditions){
        Platform.runLater(()->{
        for(FinishCondition finishCondition: conditions){
            if (finishCondition.getClass().equals(FinishByGenerationsNumber.class)) {
                FinishByGenerationsNumber currFinish=(FinishByGenerationsNumber)finishCondition;
                finishGenerationsRatio.setValue((float)currFinish.getCurrentGenerationNumber()/(float)(currFinish.getMaxGenerations()-(currFinish.getMaxGenerations()%frequency)));

            }
             else if (finishCondition.getClass().equals(FinishByFitness.class)) {
                 FinishByFitness currFinish=(FinishByFitness)finishCondition;
                 finishFitnessRatio.setValue(currFinish.getCurrentFitness()/(currFinish.getFitnessToStop()));
                }

            else if(finishCondition.getClass().equals(FinishByMinutes.class)){
                FinishByMinutes currFinish=(FinishByMinutes)finishCondition;
                finishTimeRatio.setValue(currFinish.getCurrentTime()/currFinish.getMinutesToStop());
                }
            }});
    }


    public static void updateUiGenerations(Pair<Integer,Float> currentGeneration2fitness){
        Platform.runLater(()->{
            currentGeneration.setValue(currentGeneration2fitness.getKey().toString());
        currentFitness.setValue(currentGeneration2fitness.getValue().toString());
        });
    }

    public static StringProperty getCurrentFitnessProperty() {
        return currentFitness;
    }


    public static StringProperty getCurrentGenerationProperty() {
        return currentGeneration;
    }
    

    public static FloatProperty getFinishGenerationsRatioProperty() {
        return finishGenerationsRatio;
    }


    public static FloatProperty getFinishFitnessRatioProperty() {
        return finishFitnessRatio;
    }


    public static FloatProperty getFinishTimeRatioProperty() {
        return finishTimeRatio;
    }
}
