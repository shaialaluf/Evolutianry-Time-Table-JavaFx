package BestSolutionComponent;

import Algorithm.TimeTable;
import Algorithm.TimeTableCellByNames;
import MainComponent.AppController;
import SchoolTimeTable.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class BestSolutionController {

    private AppController mainController;

    @FXML
    private ScrollPane rawScrollPane;

    @FXML
    private VBox solutionVbox;

    @FXML
    private TabPane solutionTabPane;

    @FXML
    private Tab teacherTab;

    @FXML
    private Tab classesTab;

    @FXML
    private Tab rawTab;

    public void setMainController(AppController mainController){
        this.mainController=mainController;
    }

    public void showBestSolution(SchoolDB schoolDB , TimeTable bestSolution){
        setTeacherTabs(schoolDB.getTeacherList(),bestSolution, schoolDB);
        setClassesTabs(schoolDB.getClassRoomsList(),bestSolution,schoolDB);
        setRawTab(schoolDB,bestSolution);
    }

    public void setRawTab(SchoolDB schoolDB , TimeTable bestSolution){
        TableView<TimeTableCellByNames> rawTable=new TableView<>();
        List<TimeTableCellByNames> rawList=new ArrayList<>();

        for(TimeTable.TimeTableCell currFifth: bestSolution.getTimeTableCells()){
            rawList.add(new TimeTableCellByNames(currFifth.getDay(),currFifth.getHour(),
            schoolDB.getClassRoomsCollection().getClassRoomById(currFifth.getClassRoom()).getName(),
                    schoolDB.getTeacherCollection().getTeacherById(currFifth.getTeacher()).getName(),
                    schoolDB.getSubjectsCollection().getSubjectById(currFifth.getSubject()).getName()));
        }

        TableColumn dayCol = new TableColumn("Day");
        TableColumn hourCol = new TableColumn("Hour");
        TableColumn classCol = new TableColumn("Class");
        TableColumn teacherCol = new TableColumn("Teacher");
        TableColumn subjectCol = new TableColumn("Subject");


        dayCol.setCellValueFactory(new PropertyValueFactory<TimeTableCellByNames, Integer>("day"));
        hourCol.setCellValueFactory(new PropertyValueFactory<TimeTableCellByNames, Integer>("hour"));
        classCol.setCellValueFactory(new PropertyValueFactory<TimeTableCellByNames, String>("classRoom"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<TimeTableCellByNames, String>("teacher"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<TimeTableCellByNames, String>("subject"));

        rawTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rawTable.getColumns().addAll(dayCol,hourCol,classCol,teacherCol,subjectCol);
        ObservableList<TimeTableCellByNames> data = FXCollections.observableArrayList(rawList);
        rawTable.setItems(data);
        rawTab.setContent(rawTable);

    }


    public void setClassesTabs(List<ClassRoom> classesList, TimeTable bestSolution, SchoolDB schoolDB) {
        TabPane classesTabPaneTemp = new TabPane();
       classesTab.setContent(classesTabPaneTemp);
        classesTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        for (ClassRoom classRoom : classesList) {
            Tab currentClassTab = new Tab(classRoom.getName());
            classesTabPaneTemp.getTabs().add(currentClassTab);
            GridPane currentClassGridPane = new GridPane();

            ScrollPane classScroll = new ScrollPane(currentClassGridPane);
            currentClassTab.setContent(classScroll);

            for (int d = 0; d < bestSolution.getDays() * 2 + 2; d++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.hgrowProperty().set(Priority.SOMETIMES);
                columnConstraints.minWidthProperty().set(30.0);
                columnConstraints.prefWidthProperty().set(Control.USE_COMPUTED_SIZE);
                currentClassGridPane.getColumnConstraints().add(columnConstraints);
            }


            for (int h = 0; h < bestSolution.getHours() * 2 + 2; h++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.vgrowProperty().set(Priority.SOMETIMES);
                rowConstraints.minHeightProperty().set(30.0);
                rowConstraints.prefHeightProperty().set(Control.USE_COMPUTED_SIZE);
                currentClassGridPane.getRowConstraints().add(rowConstraints);
            }

            for (int d = 0; d < bestSolution.getDays(); d++) {
                Separator separator = new Separator();
                separator.orientationProperty().set(Orientation.VERTICAL);
                separator.prefHeight(200.0);
                separator.setHalignment(HPos.CENTER);
                separator.setValignment(VPos.CENTER);
                currentClassGridPane.add(separator, 2 * d + 2, 0, 1, currentClassGridPane.getRowConstraints().size());
            }

            for (int h = 0; h < bestSolution.getHours(); h++) {
                Separator separator = new Separator();
                separator.prefWidth(200.0);
                currentClassGridPane.add(separator, 0, 2 * h + 1, currentClassGridPane.getColumnConstraints().size(), 1);
            }

            Label dhLabel = new Label("Hour/Day");
            dhLabel.setAlignment(Pos.CENTER);
            currentClassGridPane.add(dhLabel, 0, 0,2,1);

            for (int d = 1; d < bestSolution.getDays() + 1; d++) {
                Label dayLabel = new Label(Integer.toString(d));
                //dayLabel.setAlignment(Pos.CENTER);
                currentClassGridPane.setHalignment(dayLabel, HPos.CENTER); // To align horizontally in the cell
                currentClassGridPane.setValignment(dayLabel, VPos.CENTER);
                currentClassGridPane.add(dayLabel, 2 * d, 0);
            }
            //currentTeacherGridPane.setGridLinesVisible(true);
            for (int h = 1; h < bestSolution.getHours() + 1; h++) {
                Label hourLabel = new Label(Integer.toString(h));
                //hourLabel.setAlignment(Pos.CENTER);
                currentClassGridPane.setHalignment(hourLabel, HPos.CENTER); // To align horizontally in the cell
                currentClassGridPane.setValignment(hourLabel, VPos.CENTER);
                currentClassGridPane.add(hourLabel, 0, 2 * h,2,1);
            }

            createClassTimeTableOnGridPane(currentClassGridPane,bestSolution,classRoom,schoolDB);

        }
    }

    public void createClassTimeTableOnGridPane(GridPane gridPane, TimeTable bestSolution, ClassRoom classRoom, SchoolDB schoolDB){
        List<TimeTable.TimeTableCell>[][] classMatrix=bestSolution.getTimeTableByClass(classRoom.getId());
        int days=bestSolution.getDays();
        int hours=bestSolution.getHours();
        for(int d=0; d<days; d++){
            for(int h=0;h<hours;h++){
                StringBuilder classHourStr=new StringBuilder();
                for(TimeTable.TimeTableCell currCell: classMatrix[d][h]){
                    classHourStr.append("Teacher ID: "+currCell.getTeacher());
                    classHourStr.append(", Teacher Name: "+schoolDB.getTeacherCollection().getTeacherById(currCell.getTeacher()).getName());
                    classHourStr.append(System.lineSeparator()+"Subject ID: "+currCell.getSubject());
                    classHourStr.append(", Subject Name: "+schoolDB.getSubjectsCollection().getSubjectById(currCell.getSubject()).getName());
                    classHourStr.append(System.lineSeparator()+System.lineSeparator());
                }

                Label classOneHourLabel=new Label(classHourStr.toString());
                classOneHourLabel.setPadding(new Insets(0,0,0,10));
                classOneHourLabel.setAlignment(Pos.CENTER);
                classOneHourLabel.setPrefWidth(Control.USE_COMPUTED_SIZE);
                gridPane.add(classOneHourLabel,2*d+2,2*h+2,1,1);
            }
        }
    }





    public void setTeacherTabs(List<Teacher> teachersList, TimeTable bestSolution,SchoolDB schoolDB) {
        TabPane teachersTabPaneTemp = new TabPane();
        teacherTab.setContent(teachersTabPaneTemp);
        teachersTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        for (Teacher teacher : teachersList) {
            Tab currentTeacherTab = new Tab(teacher.getName());
            teachersTabPaneTemp.getTabs().add(currentTeacherTab);
            GridPane currentTeacherGridPane = new GridPane();

            ScrollPane teacherScroll = new ScrollPane(currentTeacherGridPane);
            currentTeacherTab.setContent(teacherScroll);

            for (int d = 0; d < bestSolution.getDays() * 2 + 2; d++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.hgrowProperty().set(Priority.SOMETIMES);
                columnConstraints.minWidthProperty().set(30.0);
                columnConstraints.prefWidthProperty().set(Control.USE_COMPUTED_SIZE);
                currentTeacherGridPane.getColumnConstraints().add(columnConstraints);
            }


            for (int h = 0; h < bestSolution.getHours() * 2 + 2; h++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.vgrowProperty().set(Priority.SOMETIMES);
                rowConstraints.minHeightProperty().set(30.0);
                rowConstraints.prefHeightProperty().set(Control.USE_COMPUTED_SIZE);
                currentTeacherGridPane.getRowConstraints().add(rowConstraints);
            }

            for (int d = 0; d < bestSolution.getDays(); d++) {
                Separator separator = new Separator();
                separator.orientationProperty().set(Orientation.VERTICAL);
                separator.prefHeight(200.0);
                separator.setHalignment(HPos.CENTER);
                separator.setValignment(VPos.CENTER);
                currentTeacherGridPane.add(separator, 2 * d + 2, 0, 1, currentTeacherGridPane.getRowConstraints().size());
            }

            for (int h = 0; h < bestSolution.getHours(); h++) {
                Separator separator = new Separator();
                separator.prefWidth(200.0);
                currentTeacherGridPane.add(separator, 0, 2 * h + 1, currentTeacherGridPane.getColumnConstraints().size(), 1);
            }

            Label dhLabel = new Label("Hour/Day");
            currentTeacherGridPane.setHalignment(dhLabel, HPos.CENTER); // To align horizontally in the cell
            currentTeacherGridPane.setValignment(dhLabel, VPos.CENTER);
            dhLabel.setAlignment(Pos.CENTER);
            currentTeacherGridPane.add(dhLabel, 0, 0,2,1);

            for (int d = 1; d < bestSolution.getDays() + 1; d++) {
                Label dayLabel = new Label(Integer.toString(d));
                //dayLabel.setAlignment(Pos.CENTER);
                currentTeacherGridPane.setHalignment(dayLabel, HPos.CENTER); // To align horizontally in the cell
                currentTeacherGridPane.setValignment(dayLabel, VPos.CENTER);
                currentTeacherGridPane.add(dayLabel, 2 * d, 0,2,1);
            }
            //currentTeacherGridPane.setGridLinesVisible(true);
            for (int h = 1; h < bestSolution.getHours() + 1; h++) {
                Label hourLabel = new Label(Integer.toString(h));
                //hourLabel.setAlignment(Pos.CENTER);
                currentTeacherGridPane.setHalignment(hourLabel, HPos.CENTER); // To align horizontally in the cell
                currentTeacherGridPane.setValignment(hourLabel, VPos.CENTER);
                currentTeacherGridPane.add(hourLabel, 0, 2 * h,2,1);
            }

            createTeacherTimeTableOnGridPane(currentTeacherGridPane,bestSolution,teacher,schoolDB);

        }
    }

    public void createTeacherTimeTableOnGridPane(GridPane gridPane, TimeTable bestSolution, Teacher teacher, SchoolDB schoolDB){
        List<TimeTable.TimeTableCell>[][] teacherMatrix=bestSolution.getTimeTableByTeacher(teacher.getId());
        int days=bestSolution.getDays();
        int hours=bestSolution.getHours();
        for(int d=0; d<days; d++){
            for(int h=0;h<hours;h++){
                StringBuilder teacherHourStr=new StringBuilder();
                for(TimeTable.TimeTableCell currCell: teacherMatrix[d][h]){
                    teacherHourStr.append("Class ID: "+currCell.getClassRoom());
                    teacherHourStr.append(", Class Name: "+schoolDB.getClassRoomsCollection().getClassRoomById(currCell.getClassRoom()).getName());
                    teacherHourStr.append(System.lineSeparator()+"Subject ID: "+currCell.getSubject());
                    teacherHourStr.append(", Subject Name: "+schoolDB.getSubjectsCollection().getSubjectById(currCell.getSubject()).getName());
                    teacherHourStr.append(System.lineSeparator()+System.lineSeparator());
                }

                Label teacherOneHourLabel=new Label(teacherHourStr.toString());
                teacherOneHourLabel.setPadding(new Insets(0,0,0,10));
                teacherOneHourLabel.setAlignment(Pos.CENTER);
                teacherOneHourLabel.setPrefWidth(Control.USE_COMPUTED_SIZE);
                gridPane.add(teacherOneHourLabel,2*d+2,2*h+2,1,1);
            }
        }
    }



//    public void setTeacherTabs(List<Teacher> teachersList, TimeTable bestSolution) {
//        TabPane teachersTabPaneTemp = new TabPane();
//        teacherTab.setContent(teachersTabPaneTemp);
//        teachersTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
//
//        for (Teacher teacher : teachersList) {
//            Tab currentTeacherTab=new Tab(teacher.getName());
//            ///// maybe add scroll pane
//
//            ScrollPane teacherScroll=new ScrollPane();
//            GridPane currentTeacherGridPane=new GridPane();
//            teacherScroll.setContent(currentTeacherGridPane);
//            currentTeacherGridPane.setPrefSize(Control.USE_COMPUTED_SIZE,Control.USE_COMPUTED_SIZE);
//            currentTeacherGridPane.addRow(0,new Label("H/D"));
//
//            for(int h=0;  h<bestSolution.getHours(); h++) {
//                currentTeacherGridPane.addRow(h+1);
//            }
//
//            for(int d=0;  d<bestSolution.getDays()*4; d++){
//                currentTeacherGridPane.addColumn(d+1);
//            }
//
//            currentTeacherGridPane.setStyle("-fx-border-color: black");
//
//            for(int i=0; i<bestSolution.getDays(); i++){
//                Label temp=new Label(Integer.toString(i+1));
//                temp.setStyle("-fx-border-color: black");
//                temp.setPrefSize(40,20);
//                temp.alignmentProperty().set(Pos.BASELINE_CENTER);
//                currentTeacherGridPane.add(temp,i+1,0);//3,1);
//            }
//
//            currentTeacherTab.setContent(currentTeacherGridPane);
//            teachersTabPaneTemp.getTabs().add(currentTeacherTab);
//
//    }




}
