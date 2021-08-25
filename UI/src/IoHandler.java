//import Algorithm.BestSolutionOrientaion;
//import Algorithm.FinishConditions.FinishByFitness;
//import Algorithm.FinishConditions.FinishByGenerationsNumber;
//import Algorithm.FinishConditions.FinishCondition;
//import exception.*;
//import javafx.util.Pair;
//
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//
//public class IoHandler {
//
//    public static void showMainMenu() {
//        System.out.println("Please choose an operation - enter a number between 1-6");
//        System.out.println("---------------------------------------------------------");
//        System.out.println("1. Load system details from XML file");
//        System.out.println("2. Show school details and algorithm details");
//        System.out.println("3. Run algorithm");
//        System.out.println("4. Show best solution details");
//        System.out.println("5. Show algorithm process (after the algorithm finished)");
//        System.out.println("6. Exit");
//        System.out.println("---------------------------------------------------------");
//    }
//
//    public static int getUserInput(Scanner scan) {
//        int choice = 0;
//        boolean validInput = false;
//        do {
//            try {
//                choice = scan.nextInt();
//                if (choice < 1 || choice > MainMenu.MENUOPTIONS.values().length) {
//                    System.out.println("ERROR:you entered illegal number." + System.lineSeparator() + "Please enter number between: 1 - " + MainMenu.MENUOPTIONS.values().length + ".");
//                } else {
//                    choice -= 1;
//                    validInput = true;
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a number between 1 -" + MainMenu.MENUOPTIONS.values().length);
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//        return choice;
//    }
//
//    public static boolean checkIfUserWantToStopCurrentAlgorithmRunning(Scanner scan) {
//        System.out.println("you are going to stop the algorithm running and start a new one, are you sure? (for yes press 1,for no press 2)" + System.lineSeparator() + "1.Yes" + System.lineSeparator() + "2.No");
//        boolean validInput = false;
//        boolean userAnswer = false;
//        int userChoice;
//        do {
//            try {
//                userChoice = scan.nextInt();
//
//                if (userChoice == 1) {
//                    userAnswer = true;
//                    validInput = true;
//
//                } else if (userChoice == 2) {
//                    userAnswer = false;
//                    validInput = true;
//
//                } else {
//                    System.out.println("you entered invalid number. (for yes press 1,for not press 2)");
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a number. (for yes press 1,for not press 2)");
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//
//        return userAnswer;
//    }
//
//    public static boolean checkIfUserWantAnotherRound(Scanner scan) {
//        if (MainMenu.getCurrentSystem().isAlgorithmAlive()) {
//            return checkIfUserWantToStopCurrentAlgorithmRunning(scan);
//        }
//        else {
//            return checkIfUserWantAnotherRoundWhileNothingRunsNow(scan);
//        }
//    }
//
//
//
//    public static boolean checkIfUserWantAnotherRoundWhileNothingRunsNow(Scanner scan) {
//        System.out.println("you are going to operate the algorithm again, are you sure? (for yes press 1,for no press 2)"+System.lineSeparator()+"1.Yes"+System.lineSeparator()+"2.No");
//        boolean validInput = false;
//        boolean userAnswer=false;
//        int userChoice ;
//            do {
//                try {
//                    userChoice = scan.nextInt();
//
//                    if (userChoice == 1) {
//                        userAnswer= true;
//                        validInput=true;
//
//                    } else if (userChoice == 2) {
//                        userAnswer= false;
//                        validInput=true;
//
//                    } else {
//                        System.out.println("you entered invalid number. (for yes press 1,for not press 2)");
//                    }
//
//                } catch (InputMismatchException exception) {
//                    System.out.println("ERROR: You must enter your choice as a number. (for yes press 1,for not press 2)");
//                    scan.nextLine();
//                }
//            } while (!validInput);
//            scan.nextLine();
//
//        return userAnswer;
//        }
//
//    public static int getFrequencyGeneration(Scanner scan) {
//        int choice = 0;
//        System.out.println("Please enter Frequency Generation condition - natural number.");
//        boolean validInput = false;
//        do {
//            try {
//                choice = scan.nextInt();
//                if (choice <= 0) {
//                    System.out.println("ERROR: you didnt entered a natural number." + System.lineSeparator() + "Please enter natural number.");
//                } else {
//                    validInput = true;
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a natural number.");
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//        return choice;
//
//    }
//
//public static void  printBestFitnessByRequestedFrequency(Pair generationAndFitness){
//    System.out.println("generation number: "+generationAndFitness.getKey()+", best fitness: "+generationAndFitness.getValue());
//}
//
//    public static void  printBestFitnessByRequestedFrequencyInLine(Pair generationAndFitness){
//        System.out.print("generation number: "+generationAndFitness.getKey()+", best fitness: "+generationAndFitness.getValue());
//    }
//    public static void printProgressionThroughGenerations(List<Pair> generationsAndFitness){
//        float change;
//        if(generationsAndFitness.size()>0){
//            printBestFitnessByRequestedFrequency(generationsAndFitness.get(0));
//            Pair prevGeneration=generationsAndFitness.get(0);
//            List<Pair> pairList=generationsAndFitness.subList(1,generationsAndFitness.size());
//            for (Pair p : pairList) {
//                printBestFitnessByRequestedFrequencyInLine(p);
//                change = (float) p.getValue() - (float) prevGeneration.getValue();
//                System.out.println(", Change compared to the previous introduced generation: (" + (change > 0 ? "+" : "") + change + ")");
//                prevGeneration = p;
//            }
//        }
//        else{
//            System.out.println("No generations to introduce - your frequency was too big");
//            System.out.println();
//        }
//
//    }
//    public static int getEndConditionByGeneration(Scanner scan) {
//        int choice = 0;
//        System.out.println("Please enter end condition - a natural number bigger than 100.");
//        boolean validInput = false;
//        do {
//            try {
//                choice = scan.nextInt();
//                if (choice <= 100) {
//                    System.out.println("ERROR: you entered illegal end condition number." + System.lineSeparator() + "Please enter a natural number bigger then: 100.");
//                } else {
//                    validInput = true;
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a natural number bigger then: 100.");
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//        return choice;
//
//    }
//    public static float getEndConditionByFitness(Scanner scan) {
//        float choice = 0;
//        System.out.println("Please enter end condition - a number between 0-100.");
//        boolean validInput = false;
//        do {
//            try {
//                choice = scan.nextFloat();
//                if (choice < 0||choice>100) {
//                    System.out.println("ERROR: you entered illegal end condition number." + System.lineSeparator() + "Please enter a number between 0-100.");
//                } else {
//                    validInput = true;
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a  number between 0-100.");
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//        return choice;
//
//    }
//
//    public static FinishCondition getEndConditionType(Scanner scan) {
//        int choice = 0;
//        FinishCondition userChoice=null;
//        System.out.println("Please choose the end condition type for the algorithm  - enter a number 1 or 2: " + System.lineSeparator()
//                + "1. Finish By Generations number" +System.lineSeparator() + "2. Finish By fitness goal" );
//        boolean validInput = false;
//        do {
//            try {
//                choice = scan.nextInt();
//                if (choice < 1 || choice > 2) {
//                    System.out.println("ERROR: you didnt entered a number 1 or 2:" + System.lineSeparator() + "Please enter a number 1 or 2:");
//                } else {
//                    validInput = true;
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a number 1 or 2:");
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//
//
//        switch (choice){
//            case 1:
//                userChoice=new FinishByGenerationsNumber(getEndConditionByGeneration(scan));
//                break;
//            case 2:
//                userChoice=new FinishByFitness(getEndConditionByFitness(scan));
//                break;
//
////            case 3:
////                userChoice=BestSolutionOrientaion.CLASSORIENTED;
////                break;
//        }
//
//        return userChoice;
//    }
//
//
//
//    public static BestSolutionOrientaion getBestSolutionOrientation(Scanner scan) {
//        int choice = 0;
//        BestSolutionOrientaion userChoice=null;
//        System.out.println("Please choose the format you want to watch for the best solution  - enter a number between 1 - 3 " + System.lineSeparator()
//        + "1. Raw" +System.lineSeparator() + "2. Teacher Oriented Table" + System.lineSeparator() + "3. Class Oriented Table");
//        boolean validInput = false;
//        do {
//            try {
//                choice = scan.nextInt();
//                if (choice < 1 || choice > 3) {
//                    System.out.println("ERROR: you didnt entered a number between 1 - 3." + System.lineSeparator() + "Please enter a number between 1 - 3.");
//                } else {
//                    validInput = true;
//                }
//
//            } catch (InputMismatchException exception) {
//                System.out.println("ERROR: You must enter your choice as a number between 1 - 3.");
//                scan.nextLine();
//            }
//        } while (!validInput);
//        scan.nextLine();
//
//
//        switch (choice){
//            case 1:
//                userChoice=BestSolutionOrientaion.RAW;
//                break;
//
//            case 2:
//                userChoice=BestSolutionOrientaion.TEACHERORIENTED;
//                break;
//
//            case 3:
//                userChoice=BestSolutionOrientaion.CLASSORIENTED;
//                break;
//        }
//
//        return userChoice;
//    }
//
//    public static void getUserXmlFile(Scanner scan){
//        if(MainMenu.getCurrentSystem().isAlgorithmAlive())
//        {
//            if(IoHandler.checkIfUserWantToStopCurrentAlgorithmRunning(scan)){
//               MainMenu.getCurrentSystem().interruptAlgorithmThread();
//                getFilePathName(scan);
//            }
//        }
//        else{
//            getFilePathName(scan);
//        }
//    }
//
//    private static void getFilePathName(Scanner scan) {
//        System.out.println("Please enter file name that you want to load data from");
//        try {
//            String fileName = scan.nextLine();
//            MainMenu.setCurrentSystem(MainMenu.getCurrentSystem().loadDataFromXmlFile(fileName));
//            MainMenu.setIsFileLoadedSuccessfully(true);
//            MainMenu.setIsAlgorithmRun(false);
//            MainMenu.setIsFirstAlgorithmRun(true);
//            System.out.println("File loaded successfully!");
//            System.out.println();
//
//        } catch (TeacherWithIllegalSubjectException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (TeachersIdNotSequentialException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (SubjectsIdNotSequentialException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (RuleAppearsTwiceException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (FileErrorException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (ClassWithIllegalSubjectException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (ClassPassedHoursLimitException e) {
//            System.out.println(e.getMessage());
//        }
//
//        catch (ClassesIdNotSequentialException e) {
//            System.out.println(e.getMessage());
//        }
//        catch (ElitismBiggerThanPopulationException e){
//            System.out.println(e.getMessage());
//        }
//
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//}
