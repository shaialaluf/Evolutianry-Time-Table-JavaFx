package BestSolutionComponent;

import MainComponent.AppController;
import SchoolTimeTable.Subject;
import SchoolTimeTable.Teacher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BestSolutionController {
private AppController mainController;


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
    public void setTeacherTabs(List<Teacher> teachersList) {
        TabPane teachersTabPaneTemp = new TabPane();
        teachersTabPaneTemp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        for (Teacher teacher : teachersList) {
            TableView teacherTimeTable = new TableView();



    }




}}
