//import CoreEvolution.SystemManager;
//import CoreEvolution.Systemic;
//
//import java.util.Scanner;
//
//public class MainMenu {
//    private static Systemic currentSystem = new SystemManager();
//    private static boolean isFileLoadedSuccessfully = false;
//    private static boolean isFirstAlgorithmRun = true;
//    private static boolean isAlgorithmRun = false;
//
//    public enum MENUOPTIONS {
//        READ_FROM_XML,
//        SHOW_SYSTEM_DETAILS,
//        OPERATE_ALGORITHM,
//        SHOW_BEST_SOLUTION,
//        SHOW_ALGORITHM_PROCESS,
//        EXIT
//    }
//
//    public static void runMenu() {
//        Scanner scan = new Scanner(System.in);
//        IoHandler.showMainMenu();
//        int choiceAsInt = IoHandler.getUserInput(scan);
//        MENUOPTIONS userChoice = MENUOPTIONS.values()[choiceAsInt];
//        while (userChoice != MENUOPTIONS.EXIT) {
//            switch (userChoice) {
//                case READ_FROM_XML:
//                    IoHandler.getUserXmlFile(scan);
//                    break;
//
//                case SHOW_SYSTEM_DETAILS:
//                    if (isFileLoadedSuccessfully) {
//                        System.out.println(currentSystem.showSystemDetails());
//                    } else {
//                        System.out.println("ERROR: no file is loaded. You can't do this operation.");
//                    }
//                    break;
//
//                case OPERATE_ALGORITHM:
//                    if (isFileLoadedSuccessfully) {
//                        if(isFirstAlgorithmRun==true){
//                            isFirstAlgorithmRun=false;
//                            isAlgorithmRun=true;
//                            currentSystem.runEvolutionaryAlgorithm(IoHandler.getEndConditionType(scan),IoHandler.getFrequencyGeneration(scan),IoHandler::printBestFitnessByRequestedFrequency);
//                        } else {
//                            if (IoHandler.checkIfUserWantAnotherRound(scan)) {
//                                isAlgorithmRun = true;
//                                currentSystem.runEvolutionaryAlgorithm(IoHandler.getEndConditionType(scan), IoHandler.getFrequencyGeneration(scan), IoHandler::printBestFitnessByRequestedFrequency);
//                            }
//                        }
//                    } else {
//                        System.out.println("ERROR: no file is loaded. You can't do this operation.");
//                    }
//                    break;
//
//                case SHOW_BEST_SOLUTION:
//                    if (isFileLoadedSuccessfully) {
//                        if (isAlgorithmRun == true) {
//                            System.out.println(currentSystem.showBestSolution(IoHandler.getBestSolutionOrientation(scan)));
//
//                        }
//                        else{
//                            System.out.println("ERROR: make sure that you run the algorithm before.");
//                        }
//                    }
//                    else{
//                        System.out.println("ERROR: no file is loaded. You can't do this operation.");
//                    }
//                        break;
//
//                case SHOW_ALGORITHM_PROCESS:
//                    if (isFileLoadedSuccessfully) {
//                        if (isAlgorithmRun == true) {
//                            IoHandler.printProgressionThroughGenerations(currentSystem.getProgressOfBestSolutions());
//                        }
//                        else{
//                            System.out.println("ERROR: make sure that you run the algorithm before.");
//                        }
//                    }
//                    else{
//                        System.out.println("ERROR: no file is loaded. You can't do this operation.");
//                    }
//                    break;
//
//                case EXIT:
//                    break;
//            }
//            IoHandler.showMainMenu();
//            choiceAsInt = IoHandler.getUserInput(scan);
//            userChoice = MENUOPTIONS.values()[choiceAsInt];
//        }
//        System.out.println("You exit the program! GOODBYE!");
//    }
//
//    public static void setCurrentSystem(Systemic currentSystem) {
//        MainMenu.currentSystem = currentSystem;
//    }
//
//    public static Systemic getCurrentSystem() {
//        return currentSystem;
//    }
//
//    public static void setIsFileLoadedSuccessfully(boolean isFileLoadedSuccessfully) {
//        MainMenu.isFileLoadedSuccessfully = isFileLoadedSuccessfully;
//    }
//
//    public static void setIsFirstAlgorithmRun(boolean isFirstAlgorithmRun) {
//        MainMenu.isFirstAlgorithmRun = isFirstAlgorithmRun;
//    }
//
//    public static void setIsAlgorithmRun(boolean isAlgorithmRun) {
//        MainMenu.isAlgorithmRun = isAlgorithmRun;
//    }
//
//}
