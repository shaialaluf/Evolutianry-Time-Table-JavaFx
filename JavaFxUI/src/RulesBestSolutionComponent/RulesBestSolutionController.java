package RulesBestSolutionComponent;

import Algorithm.TimeTable;
import Algorithm.TimeTableCellByNames;
import MainComponent.AppController;
import SchoolTimeTable.Rule;
import SchoolTimeTable.RuleBestSolution;
import SchoolTimeTable.RuleId;
import SchoolTimeTable.SchoolDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RulesBestSolutionController {


    @FXML
    private TableView<RuleBestSolution> rulesTableView;

    @FXML
    private TableColumn<RuleBestSolution, RuleId> nameCol;

    @FXML
    private TableColumn<RuleBestSolution, Rule.RuleType> typeCol;

    @FXML
    private TableColumn<RuleBestSolution, String> configurationCol;

    @FXML
    private TableColumn<RuleBestSolution, Float> gradeCol;
    private AppController mainController;

    public void setMainController(AppController mainController){
        this.mainController=mainController;
    }
    public TableView<RuleBestSolution> getRulesTableView() {
        return rulesTableView;
    }

    public void setRulesTableView( TimeTable bestSolution){
        rulesTableView.setDisable(false);
      //  TableView<RuleBestSolution> rulesTable=new TableView<>();
        List<RuleBestSolution> ruleList=new ArrayList<>();

        for(Map.Entry<Rule,Float> currRule: bestSolution.getRuleGrades().entrySet()){

            ruleList.add(new RuleBestSolution(currRule.getKey().getRuleId(), currRule.getKey().getType(),currRule.getKey().getRuleConfiguration(),currRule.getValue()));
        }



        nameCol.setCellValueFactory(new PropertyValueFactory<RuleBestSolution, RuleId>("ruleName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<RuleBestSolution, Rule.RuleType>("ruleType"));
        configurationCol.setCellValueFactory(new PropertyValueFactory<RuleBestSolution, String>("configuration"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<RuleBestSolution, Float>("ruleGrade"));


        rulesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       // rulesTableView.getColumns().addAll(nameCol,typeCol,configurationCol,gradeCol);
        ObservableList<RuleBestSolution> data = FXCollections.observableArrayList(ruleList);
        rulesTableView.setItems(data);

        //rulesTableView=rulesTable;

    }



}
