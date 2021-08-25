package ControlPanelComponent.Mutations;

import EvolutionEngineDB.Mutations.Mutation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class SizerController implements MutationController {
    private boolean isSizerValid = true;
    @FXML
    private TextField totalTupplesTextField;

    @FXML
    private Label totalTuppelsErrorLabel;

    @FXML
    private Slider probabilitySlider;

    public boolean isMutationValid() {
        return isSizerValid;
    }

    public void checkTimeTextField() {
        if (totalTupplesTextField.getText().length() == 0) {
            totalTuppelsErrorLabel.setVisible(false);
            isSizerValid = true;
        } else {
            try {
                int num = Integer.parseInt(totalTupplesTextField.getText());
                totalTuppelsErrorLabel.setVisible(false);
                isSizerValid = true;
            } catch (NumberFormatException e) {
                totalTuppelsErrorLabel.setVisible(true);
                isSizerValid = false;
            }
        }
    }

    @FXML
    private Label sliderValueLabel;

    @FXML
    void checkTotalTuppelsTextField(KeyEvent event) {
        checkTimeTextField();
    }

    @FXML
    void initialize() {
        sliderValueLabel.textProperty().bind(probabilitySlider.valueProperty().asString());
    }

    public void setSlidersDefaultValues(Mutation mutation){
        probabilitySlider.setValue(mutation.getProbability());
    }

    public double getProbabilityValue(){
        return probabilitySlider.getValue();
    }

    public String getTotalTupplesValue() {
        return totalTupplesTextField.getText();
    }
}
