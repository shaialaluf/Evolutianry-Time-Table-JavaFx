package ControlPanelComponent.Mutations;

import EvolutionEngineDB.Mutations.Flipping;
import EvolutionEngineDB.Mutations.Mutation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FlippingController implements MutationController {

    private boolean isFlippingValid=true;
    @FXML
    private TextField maxTupplesTextField;

    @FXML
    private Label totalTuppelsErrorLabel;
    public void setComponentComboBox() {
        componentComboBox.getItems().addAll('T', 'C','S','D','H');
    }
    @FXML
    private Slider probabilitySlider;

    @FXML
    private Label sliderValueLabel;

    @FXML
    private ComboBox<Character> componentComboBox;

    public boolean isMutationValid() {
        return isFlippingValid;
    }

    public void checkTimeTextField() {
        if(maxTupplesTextField.getText().length()==0){
            totalTuppelsErrorLabel.setVisible(false);
            isFlippingValid=true;
        }
        else {
            try {
                int num = Integer.parseInt(maxTupplesTextField.getText());
                if (num <= 0) {
                    totalTuppelsErrorLabel.setVisible(true);
                    isFlippingValid=false;
                } else {
                    totalTuppelsErrorLabel.setVisible(false);
                    isFlippingValid=true;
                }
            } catch (NumberFormatException e) {
                totalTuppelsErrorLabel.setVisible(true);
                isFlippingValid=false;
            }
        }
    }

    @FXML
    void textChanged(KeyEvent event) {
        checkTimeTextField();

    }
    @FXML
    void initialize() {
        sliderValueLabel.textProperty().bind(probabilitySlider.valueProperty().asString());
        setComponentComboBox();
    }

    public void setSlidersDefaultValues(Mutation mutation){
        probabilitySlider.setValue(mutation.getProbability());
    }

    public void setComponentComboBoxDefaultValue(Mutation mutation){
        componentComboBox.setValue(((Flipping)mutation).getComponent());
    }

    public double getProbabilityValue(){
        return probabilitySlider.getValue();
    }

    public char getComponentValue(){
       return componentComboBox.getSelectionModel().selectedItemProperty().get();
    }

    public String getMaxTupplesValue(){
        return maxTupplesTextField.getText();
    }


}
