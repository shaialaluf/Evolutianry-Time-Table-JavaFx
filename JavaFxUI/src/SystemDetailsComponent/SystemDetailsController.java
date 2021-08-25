package SystemDetailsComponent;

import AlgorithmOperationComponent.AlgorithmOperationController;
import MainComponent.AppController;
import SchoolTimeTable.*;
import com.sun.javaws.jnl.PropertyDesc;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//
//public class SystemDetailsController {
//    @FXML
//    private AppController mainController;
//
//    @FXML
//    private Button showDetailButton;
//
//    @FXML
//    private ToggleGroup details;
//
//    @FXML
//    private TextFlow detailsTextFlow;
//    @FXML
//    private Label systemDetailsLabel;
//    @FXML
//    void showDetailClick(ActionEvent event) {
//        systemDetailsLabel.setText(mainController.modelShowDetails());
//    }
//
//    public void setMainController(AppController mainController) {
//        this.mainController = mainController;
//    }
//
//    public void setDisableDetailClick(){
//        showDetailButton.disableProperty().bind(mainController.getModel().isFileLoadedSuccessfullyProperty().not());
//    }
//
//}


public class SystemDetailsController {

    @FXML
    private AppController mainController;
    @FXML
    private GridPane algorithmOperationComponent;
    @FXML
    private AlgorithmOperationController algorithmOperationComponentController;

    @FXML
    private Button showDetailButton;
    @FXML
    private Tab subjects;

    @FXML
    private TabPane mainTabPane;
    @FXML
    private TableView<Subject> subjectsTableView;
    @FXML
    private TableColumn<Subject, Integer> subjectId;

    @FXML
    private TableColumn<Subject, String> subjectName;

    @FXML
    private TabPane teachersTabPane;

    @FXML
    private Tab teachers;

    @FXML
    private TableView<Subject> teachersTableView;


    @FXML
    private TableColumn<?, ?> subjectIdTeacher;

    @FXML
    private TableColumn<?, ?> subjectNameTeacher;


    @FXML
    private TableColumn<?, ?> subjectIdTeacher1;

    @FXML
    private TableColumn<?, ?> subjectNameTeacher1;

    @FXML
    private Tab classes;
    @FXML
    private TableView<?> classesTableView;

    @FXML
    private TableColumn<?, ?> subjectIdClass;

    @FXML
    private TableColumn<?, ?> subjectNameClass;

    @FXML
    private TableColumn<?, ?> weeklyHoursClass;


    @FXML
    private Tab rules;
    @FXML
    private TableView<?> RulesTableView;

    @FXML
    private TableColumn<?, ?> ruleName;

    @FXML
    private TableColumn<?, ?> ruleType;

    @FXML
    private Tab algorithmDetails;

    @FXML
    private Label algorithmDetailLabel;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
        algorithmOperationComponentController.setMainController(mainController);

        setDisableDetailClick();
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        setDisableMainTabPane();
    }

    @FXML
    private void showDetailClick(ActionEvent event) {
        mainController.getModel().setIsNewSuccessfulLoad(false); // to disable the tabpane!!!!!!!!!!!!!!!!!!!!!
        //mainTabPane.setDisable(false);
        setSubjectsTable(mainController.getSchoolSettings().getSubjectsList());
        setTeacherTabs(mainController.getSchoolSettings().getTeacherList());
        setClassesTabs(mainController.getSchoolSettings().getClassRoomsList());
        setRulesTabs(mainController.getSchoolSettings().getRulesList());
        algorithmDetailLabel.setText(mainController.getAlgorithmDetails());
    }

    public void setDisableMainTabPane(){
        mainTabPane.disableProperty().bind(mainController.getModel().isNewSuccessfulLoadProperty());
    }

    public void setDisableDetailClick() {
        showDetailButton.disableProperty().bind(mainController.getModel().isFileLoadedSuccessfullyProperty().not());
    }


    @FXML
    public void setSubjectsTable(List<Subject> subjects) {
        ObservableList<Subject> data = FXCollections.observableArrayList(subjects);
        subjectId.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("id"));
        subjectName.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
        subjectsTableView.setItems(data);

    }
    public void setRulesTabs(List<Rule> rulesList) {
        TableView<Rule> rulesTable;

       // for ( Rule currentRule:rulesList) {
            ObservableList<Rule> data = FXCollections.observableArrayList(rulesList);
            rulesTable = new TableView<Rule>();
            TableColumn ruleId = new TableColumn("Rule id");
            TableColumn typeId = new TableColumn("Rule type");

            rulesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            rulesTable.getColumns().add(ruleId);
            rulesTable.getColumns().add(typeId);
            ruleId.setCellValueFactory(new PropertyValueFactory<Rule, RuleId>("ruleId"));
            typeId.setCellValueFactory(new PropertyValueFactory<Rule, Rule.RuleType>("type"));
            rulesTable.setItems(data);
             rules.setContent(rulesTable);
        }


  //  }
    //teacher.getId()+"."+
    public void setTeacherTabs(List<Teacher> teachersList) {
        TabPane teachersTabPaneTemp = new TabPane();
        teachersTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        TableView<Subject> subjectOfTeacher;

        for (Teacher teacher : teachersList) {
            ObservableList<Subject> data = FXCollections.observableArrayList(teacher.getSubjects());
            VBox teacherVbox = new VBox();
            Label teacherId = new Label("Teacher id: " + teacher.getId());
            Label subjectMsg = new Label("Subjects are: ");
            subjectOfTeacher = new TableView<Subject>();
            TableColumn subjectId = new TableColumn("Subject id");
            TableColumn subjectName = new TableColumn("Subject name");

            subjectOfTeacher.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            subjectOfTeacher.getColumns().add(subjectId);
            subjectOfTeacher.getColumns().add(subjectName);
            subjectId.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("id"));
            subjectName.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
            subjectOfTeacher.setItems(data);
            teacherVbox.getChildren().addAll(teacherId, subjectMsg, subjectOfTeacher);
            teacherVbox.setSpacing(5);
            teachersTabPaneTemp.getTabs().add(new Tab(teacher.getName(), teacherVbox));
        }
        teachers.setContent(teachersTabPaneTemp);

    }
    public void setClassesTabs(List<ClassRoom> classesList) {
        TabPane classesTabPaneTemp = new TabPane();
        classesTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        TableView<StudyDemandUtility> subjectOfClass;
        for (ClassRoom currentClass : classesList) {
            List<StudyDemandUtility> studyDemand=new ArrayList<>();
            for(Map.Entry<Subject,Integer> subjectIntegerEntry:currentClass.getSubject2WeeklyHours().entrySet())
            {
                studyDemand.add(new StudyDemandUtility(subjectIntegerEntry.getValue(),subjectIntegerEntry.getKey().getId(),subjectIntegerEntry.getKey().getName()));
            }
            ObservableList<StudyDemandUtility> data = FXCollections.observableArrayList(studyDemand);
            VBox classVbox = new VBox();
            Label classId = new Label("class id: " + currentClass.getId());
            Label subjectMsg = new Label("Subjects and weekly hours are: ");
            subjectOfClass = new TableView<StudyDemandUtility>();
            TableColumn subjectId = new TableColumn("Subject id");
            TableColumn subjectName = new TableColumn("Subject name");
            TableColumn weeklyHour=new TableColumn("Weekly hours");

            subjectOfClass.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            subjectOfClass.getColumns().add(subjectId);
            subjectOfClass.getColumns().add(subjectName);
            subjectOfClass.getColumns().add(weeklyHour);

            subjectId.setCellValueFactory(new PropertyValueFactory<StudyDemandUtility, Integer>("subjectId"));
            subjectName.setCellValueFactory(new PropertyValueFactory<StudyDemandUtility, String>("subjectName"));
            weeklyHour.setCellValueFactory(new PropertyValueFactory<StudyDemandUtility, Integer>("weeklyHour"));
            subjectOfClass.setItems(data);
            classVbox.getChildren().addAll(classId, subjectMsg, subjectOfClass);
            classVbox.setSpacing(5);
            classesTabPaneTemp.getTabs().add(new Tab(currentClass.getName(), classVbox));
        }
        classes.setContent(classesTabPaneTemp);

    }


    public AlgorithmOperationController getAlgorithmOperationComponentController() {
        return algorithmOperationComponentController;
    }
}
