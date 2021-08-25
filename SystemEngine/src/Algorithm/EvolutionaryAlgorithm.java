package Algorithm;

import Algorithm.FinishConditions.FinishByFitness;
import Algorithm.FinishConditions.FinishByGenerationsNumber;
import Algorithm.FinishConditions.FinishByMinutes;
import Algorithm.FinishConditions.FinishCondition;
import EvolutionEngineDB.Crossovers.AspectOriented;
import EvolutionEngineDB.Crossovers.Crossover;
import EvolutionEngineDB.Crossovers.DayTimeOriented;
import EvolutionEngineDB.Mutations.Mutation;
import EvolutionEngineDB.Mutations.MutationCollection;
import EvolutionEngineDB.Selections.RouletteWheel;
import EvolutionEngineDB.Selections.Selection;
import EvolutionEngineDB.Selections.Truncation;
import SchoolTimeTable.SchoolDB;
import javafx.application.Platform;
import javafx.util.Pair;


import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

public class EvolutionaryAlgorithm implements AlgorithmExecutor {
    private int initialPopulation;
    private Selection selection;
    private Crossover crossover;
    private SolutionsPopulation<TimeTable> currentGeneration;
    private float bestSolutionOfCurrentGeneration;
    private SolutionsPopulation<TimeTable> nextGeneration;
    private MutationCollection mutations;
    private TimeTable bestSolutionOfAllGeneration;
    private int generationNumberOfBestSolution;
    private List<FinishCondition> finishConditionList;
    private int currentGenerationNumber;
    private int displayResultEachIteration;
    private Consumer<Pair> displayResultEachIterationFunction;
    private Consumer<List<FinishCondition>> displayAlgorithmProgress;
    private List<Pair> displayResultCollection;
    private Instant startTimeOfAlgorithm;
    private Instant start;
    private Long duration=0l;
    private boolean isPaused = false;

    public EvolutionaryAlgorithm() {

    }

    public Selection getSelection() {
        return selection;
    }

    public void init() {
        currentGeneration = new SolutionsPopulation<>();
        finishConditionList = new ArrayList<>();
        displayResultCollection = new LinkedList<>();
    }

    public void createInitialPopulation(int days, int hours, SchoolDB school) {
        currentGenerationNumber = 1;
        for (int i = 0; i < initialPopulation; i++) {
            currentGeneration.addToPopulation(new TimeTable(days, hours, school));
        }
    }

    public MutationCollection getMutationsCollection() {
        return mutations;
    }

    public List<Mutation> getMutationList() {
        return mutations.getMutations();
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public void setCrossover(Crossover crossover) {
        this.crossover = crossover;
    }

    public void setCurrentGeneration(SolutionsPopulation<TimeTable> currentGeneration) {
        this.currentGeneration = currentGeneration;
    }

    public void setMutations(MutationCollection mutations) {
        this.mutations = mutations;
    }

    public void setInitialPopulation(int initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    public int getInitialPopulation() {
        return initialPopulation;
    }

    public Pair<Integer, TimeTable> getBestSolutionOfAllGenerationAndNumber() {
        synchronized (bestSolutionOfAllGeneration) {
            Pair<Integer, TimeTable> bestSolutionOfAllGenerationAndNumber = new Pair<>(generationNumberOfBestSolution, bestSolutionOfAllGeneration);
            return bestSolutionOfAllGenerationAndNumber;

        }
    }


    public void createElitism() {
        if (selection.getElitism() > 0) {
            selection.createElitism(currentGeneration);
        }
    }

    public SolutionsPopulation<TimeTable> performSelection() {
        return selection.makeSelection(currentGeneration);
    }


    public void performMutations() {
        for (TimeTable currOffSpring : nextGeneration.getSolutions()) {
            if (currOffSpring.isElitist() == false) {
                for (Mutation currMutation : mutations.getMutations()) {
                    currMutation.makeMutation(currOffSpring);
                }
            }
        }
    }

    public void performCrossover(SolutionsPopulation<TimeTable> parentsToCrossover) {
        Random rndParents = new Random();
        nextGeneration = new SolutionsPopulation<TimeTable>();

        for (TimeTable elitist : selection.getElitaSolutions()) {
            nextGeneration.addToPopulation(elitist);
        }

        while (nextGeneration.getSolutions().size() < initialPopulation) {
            int indexParent1 = rndParents.nextInt(parentsToCrossover.getSolutions().size());
            int indexParent2 = rndParents.nextInt(parentsToCrossover.getSolutions().size());
            List<TimeTable> offSprings = crossover.makeCrossover(parentsToCrossover.getSolutions().get(indexParent1), parentsToCrossover.getSolutions().get(indexParent2), currentGenerationNumber);
            nextGeneration.addToPopulation(offSprings.get(0));
            nextGeneration.addToPopulation(offSprings.get(1));
        }
    }

    public void calcFitnessForPopulation() {
        //currentGeneration.getSolutions().stream().forEach(t -> t.calcFitnessGrade());
        for (TimeTable currT : currentGeneration.getSolutions()) {
            currT.calcFitnessGrade();
        }
    }

    public void setDisplayResultEachIterationFunction(Consumer<Pair> displayResultEachIterationFunction) {
        this.displayResultEachIterationFunction = displayResultEachIterationFunction;
    }

    public void setDisplayAlgorithmProgress(Consumer<List<FinishCondition>> displayAlgorithmProgress) {
        this.displayAlgorithmProgress = displayAlgorithmProgress;
    }

    public void runAlgorithms(List<FinishCondition> finishConditions, int frequency) {
        startTimeOfAlgorithm = Instant.now();
        finishConditionList = finishConditions;
        calcFitnessForPopulation();
        bestSolutionOfAllGeneration = currentGeneration.getSolutions().get(0);
        setCurrentBestSolutionOfAllGenerations(currentGeneration);
        // System.out.println("currentGenerationNumber: " + currentGenerationNumber + " ,Fitness:" + bestSolutionOfAllGeneration.getFitness() + " ,size: "+bestSolutionOfAllGeneration.getTimeTableCells().size());
        displayResultEachIteration = frequency;
        updateGenerationsProgress();
        while (!isFinishCondition() && !Thread.currentThread().isInterrupted()) {
            createElitism();
            SolutionsPopulation<TimeTable> parentsToCrossover = performSelection();
            performCrossover(parentsToCrossover);
            performMutations();
            currentGeneration = nextGeneration;
            calcFitnessForPopulation();
            setCurrentBestSolutionOfAllGenerations(currentGeneration);
            currentGenerationNumber++;
            updateGenerationsProgress();
            checkPausing();
            //   System.out.println("currentGenerationNumber: " + currentGenerationNumber + " ,Fitness:" + bestSolutionOfAllGeneration.getFitness() + " ,size: "+bestSolutionOfAllGeneration.getTimeTableCells().size());
        }
        duration=0l;
        //System.out.println(bestSolutionOfAllGeneration.getTimeTableCells());
    }
public synchronized void notifyAlgorithm(){
    duration+=Duration.between(start,Instant.now()).toMillis();
        this.notifyAll();

}
    public void setPaused(boolean paused) {
        isPaused = paused;

    }

    private synchronized void checkPausing() {
        while (isPaused) {
            try {
                start=Instant.now();
                this.wait();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                isPaused = false;
            }
        }
    }


    private synchronized void updateGenerationsProgress() {
        Pair result;
        if (currentGenerationNumber % displayResultEachIteration == 0) {
            result = new Pair(currentGenerationNumber, bestSolutionOfCurrentGeneration);
            displayResultCollection.add(result);
            displayResultEachIterationFunction.accept(result);
            displayAlgorithmProgress.accept(finishConditionList);
        }
    }


    public void setCurrentBestSolutionOfAllGenerations(SolutionsPopulation<TimeTable> generation) {
        synchronized (bestSolutionOfAllGeneration) {
            TimeTable bestOfCurrGen = findBestSolutionOfGeneration(generation);
            if (bestOfCurrGen.getFitness() > bestSolutionOfAllGeneration.getFitness()) {
                bestSolutionOfAllGeneration = bestOfCurrGen;
                generationNumberOfBestSolution = currentGenerationNumber + 1;
            }
        }
    }

    public TimeTable findBestSolutionOfGeneration(SolutionsPopulation<TimeTable> generation) {
        TimeTable bestSolution = generation.getSolutions().get(0);
        float bestFitness = bestSolution.getFitness();

        for (TimeTable currTimeTable : generation.getSolutions()) {
            if (currTimeTable.getFitness() > bestFitness) {
                bestFitness = currTimeTable.getFitness();
                bestSolution = currTimeTable;
            }
        }
        bestSolutionOfCurrentGeneration = bestSolution.getFitness();

        return bestSolution;
    }


    public boolean isFinishCondition() {
        for (FinishCondition finishCondition : finishConditionList) {
            if (finishCondition.getClass().equals(FinishByGenerationsNumber.class)) {
                if (finishCondition.isFinish(currentGenerationNumber)) {
                    return true;
                }
            } else if (finishCondition.getClass().equals(FinishByFitness.class)) {
                if (finishCondition.isFinish(bestSolutionOfCurrentGeneration)) {
                    return true;
                }

            } else if (finishCondition.getClass().equals(FinishByMinutes.class)) {

                if (finishCondition.isFinish((float) ((Duration.between(startTimeOfAlgorithm, Instant.now()).toMillis()-duration)/60000f)))//.toMillis())).toMillis()/ 60000f))) {
                    return true;
                }

            }

        return false;
    }

    public synchronized List<Pair> getDisplayResultCollection() {
        List<Pair> copyDisplayResultCollection = new LinkedList<>(displayResultCollection);
        return copyDisplayResultCollection;
    }

    @Override
    public String toString() {
        String engineDetails = "Algorithm details:" + System.lineSeparator();
        engineDetails += "Population size: " + initialPopulation + System.lineSeparator();
        engineDetails += "Selection technique :" + selection.toString() + System.lineSeparator();
        engineDetails += "Crossover technique :" + crossover.toString() + System.lineSeparator();
        engineDetails += "Mutations technique :" + mutations.toString() + System.lineSeparator();
        return engineDetails;
    }

    public void replaceSelectionToTruncation(String topPrecent, String elitism) {
        selection = new Truncation(Integer.parseInt(topPrecent), Integer.parseInt(elitism));

    }

    public void replaceSelectionToRouletteWheel(String elitism) {
        selection = new RouletteWheel(Integer.parseInt(elitism));

    }

    public void selectedSelection(String newSelection, String topPrecent, String elitism) {
        if (elitism.equals("")) {
            elitism = Integer.toString(selection.getElitism());
        }

        if (!newSelection.equals(selection.getClass().getSimpleName())) {

            if (newSelection.equals("Truncation")) {
                replaceSelectionToTruncation(topPrecent, elitism);
            } else if (newSelection.equals("RouletteWheel")) {
                replaceSelectionToRouletteWheel(elitism);
            }
        } else {

            selection.setElitism(Integer.parseInt(elitism));

            if (newSelection.equals("Truncation")) {
                if (!topPrecent.equals("")) {
                    ((Truncation) selection).setTopPercent(Integer.parseInt(topPrecent));
                }
            }
        }

    }


    public Crossover getCrossover() {
        return crossover;
    }

    public void selectedCrossover(String crossoverStr, String cuttingPoints, String orientation) {
        if (!crossoverStr.equals(crossover.getClass().getSimpleName())) {
            if (crossoverStr.equals("AspectOriented")) {
                replaceCrossoverToAspectOriented(cuttingPoints, orientation);
            } else if (crossoverStr.equals("DayTimeOriented")) {
                replaceCrossoverToDayTimeOriented(cuttingPoints);
            }
        } else {
            if (!cuttingPoints.equals("")) {
                crossover.setCuttingPointsNumber(Integer.parseInt(cuttingPoints));
            }
            if (crossover.getClass().getSimpleName().equals("AspectOriented")) {
                ((AspectOriented) crossover).setType(orientation);
            }
        }
    }


    public void replaceCrossoverToDayTimeOriented(String cuttingPoints) {
        int cuttingNumber;
        if (cuttingPoints.equals("")) {
            cuttingNumber = crossover.getCuttingPointsNumber();
        } else {
            cuttingNumber = Integer.parseInt(cuttingPoints);
        }
        crossover = new DayTimeOriented(cuttingNumber);

    }

    public void replaceCrossoverToAspectOriented(String cuttingPoints, String orientation) {
        int cuttingNumber;
        if (cuttingPoints.equals("")) {
            cuttingNumber = crossover.getCuttingPointsNumber();
        } else {
            cuttingNumber = Integer.parseInt(cuttingPoints);
        }
        crossover = new AspectOriented(cuttingNumber, orientation);


    }


}
