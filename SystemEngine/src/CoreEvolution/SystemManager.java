package CoreEvolution;

import Algorithm.BestSolutionOrientaion;
import Algorithm.EvolutionaryAlgorithm;
import Algorithm.FinishConditions.FinishCondition;
import Algorithm.TimeTable;
import SchoolTimeTable.SchoolDB;
import XmlReader.SchemaBasedJAXB;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public class SystemManager implements Systemic {
    private SchoolDB schoolSettings;
    private EvolutionaryAlgorithm evolutionaryAlgorithmSettings;
    //private Thread algorithmThread;


    public void setSchoolSettings(SchoolDB schoolSettings) {
        this.schoolSettings = schoolSettings;
    }

    public void setEvolutionaryAlgorithmSettings(EvolutionaryAlgorithm evolutionaryAlgorithmSettings) {
        this.evolutionaryAlgorithmSettings = evolutionaryAlgorithmSettings;
    }

    @Override
    public void pauseAlgorithm() {
        evolutionaryAlgorithmSettings.setPaused(true);
    }

    @Override
    public void resumeAlgorithm() {
        evolutionaryAlgorithmSettings.setPaused(false);
        evolutionaryAlgorithmSettings.notifyAlgorithm();
    }

    public SchoolDB getSchoolSettings() {
        return schoolSettings;
    }

    @Override
    public String getEvolutionaryDetails() {
        return evolutionaryAlgorithmSettings.toString();
    }

    @Override
    public EvolutionaryAlgorithm getEvolutionaryObject() {
        return evolutionaryAlgorithmSettings;
    }

    @Override
    public String showSystemDetails() {
        return schoolSettings.toString() + System.lineSeparator() + System.lineSeparator() + evolutionaryAlgorithmSettings.toString();
    }

//    @Override
//    public List<Pair> getProgressOfBestSolutions() {
//        List<Pair> resultCollections = evolutionaryAlgorithmSettings.getDisplayResultCollection();
//        if ((isAlgorithmAlive()) && (resultCollections.size() >= 10)) {
//            return resultCollections.subList(resultCollections.size() - 10, resultCollections.size());
//        } else {
//            return resultCollections;
//        }
//    }

    @Override
    public TimeTable getBestSolution() {
        Pair<Integer, TimeTable> solution = evolutionaryAlgorithmSettings.getBestSolutionOfAllGenerationAndNumber();
        return solution.getValue();
    }

    public EvolutionaryAlgorithm getEvolutionaryAlgorithmSettings() {
        return evolutionaryAlgorithmSettings;
    }

    @Override
    public Systemic loadDataFromXmlFile(String fileName) {
        return SchemaBasedJAXB.readFromXml(fileName);
    }

//    @Override
//    public boolean isAlgorithmAlive() {
//        return (algorithmThread != null && algorithmThread.isAlive());
//    }
//
//    @Override
//    public void interruptAlgorithmThread() {
//        if (algorithmThread != null) {
//            algorithmThread.interrupt();
//        }
//    }

//    @Override
//    public void runEvolutionaryAlgorithm(List<FinishCondition> finishConditions, int frequency, Consumer<Pair> consumer) {
//
//        if (isAlgorithmAlive()) {
//            interruptAlgorithmThread();
//        }
//
//        while (isAlgorithmAlive()){
//
//        }
//
//        algorithmThread = new Thread(() -> {
//
//        });
//        algorithmThread.setName("Algorithm Thread");
//        algorithmThread.setDaemon(true);
//        algorithmThread.start();
////       Thread t=new Thread(()-> evolutionaryAlgorithmSettings.runAlgorithms(finishCondition,frequency));
////        t.start();
//    }


//    @Override
//    public void runEvolutionaryAlgorithm(EvolutionaryAlgorithmTask algorithmTask) {
//
//        if (isAlgorithmAlive()) {
//            interruptAlgorithmThread();
//        }
//
//        while (isAlgorithmAlive()){
//
//        }
//
//        algorithmThread = new Thread(algorithmTask);
//
//        algorithmThread.setName("Algorithm Thread");
//        algorithmThread.setDaemon(true);
//        algorithmThread.start();
////       Thread t=new Thread(()-> evolutionaryAlgorithmSettings.runAlgorithms(finishCondition,frequency));
////        t.start();
//    }

    @Override
    public void run(List<FinishCondition> finishConditions, int frequency, Consumer<Pair> consumerResult, Consumer<List<FinishCondition>> consumerProgress) {
        evolutionaryAlgorithmSettings.init();
        evolutionaryAlgorithmSettings.setDisplayResultEachIterationFunction(consumerResult);
        evolutionaryAlgorithmSettings.setDisplayAlgorithmProgress(consumerProgress);
        evolutionaryAlgorithmSettings.createInitialPopulation(schoolSettings.getNumberOfDays(), schoolSettings.getNumberOfHours(), schoolSettings);
        evolutionaryAlgorithmSettings.runAlgorithms(finishConditions, frequency);
    }
}
