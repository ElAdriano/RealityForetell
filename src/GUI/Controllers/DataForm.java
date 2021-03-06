package GUI.Controllers;

import Management.SchemeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

public class DataForm {

    @FXML
    private Slider maxBudgetSlider;
    @FXML
    private Slider amountOfPrisonersSlider;
    @FXML
    private Slider priceOfPrisonWardSlider;
    @FXML
    private Slider maxPrisonersAmountSlider;
    @FXML
    private Slider priceOfSanitaryNookSlider;
    @FXML
    private Slider cameraRangeSlider;
    @FXML
    private Slider generationsAmountSlider;

    @FXML
    private Label maxBudgetMonitor;
    @FXML
    private Label amountOfPrisonersMonitor;
    @FXML
    private Label priceOfPrisonWardMonitor;
    @FXML
    private Label maxPrisonersAmountMonitor;
    @FXML
    private Label priceOfSanitaryNookMonitor;
    @FXML
    private Label cameraRangeMonitor;
    @FXML
    private Label generationsAmountMonitor;

    @FXML
    private Button nextPageButton;

    private DecimalFormat costFormat;
    private DecimalFormat amountFormat;

    public void initialize() {
        costFormat = new DecimalFormat("0.00");
        amountFormat = new DecimalFormat("0");

        getEnteredDataFromCache();

        setListener(maxBudgetSlider, maxBudgetMonitor, costFormat);
        setListener(amountOfPrisonersSlider, amountOfPrisonersMonitor, amountFormat);
        setListener(priceOfPrisonWardSlider, priceOfPrisonWardMonitor, costFormat);

        setListener(maxPrisonersAmountSlider, maxPrisonersAmountMonitor, amountFormat);

        setListener(priceOfSanitaryNookSlider, priceOfSanitaryNookMonitor, costFormat);
        setListener(cameraRangeSlider, cameraRangeMonitor, costFormat);
        setListener(generationsAmountSlider, generationsAmountMonitor, amountFormat);
    }

    private void getEnteredDataFromCache() {
        loadValues(maxBudgetSlider, maxBudgetMonitor, SchemeGenerator.conditions.budget, costFormat);
        loadValues(amountOfPrisonersSlider, amountOfPrisonersMonitor, SchemeGenerator.conditions.amountOfPrisoners, amountFormat);
        loadValues(priceOfPrisonWardSlider, priceOfPrisonWardMonitor, SchemeGenerator.conditions.priceOfPrisonWard, costFormat);
        loadValues(maxPrisonersAmountSlider, maxPrisonersAmountMonitor, SchemeGenerator.conditions.maxAmountOfPrisonersInPrisonWard, amountFormat);
        loadValues(priceOfSanitaryNookSlider, priceOfSanitaryNookMonitor, SchemeGenerator.conditions.priceOfSanitaryNook, costFormat);
        loadValues(cameraRangeSlider, cameraRangeMonitor, SchemeGenerator.conditions.cameraRange, costFormat);
        loadValues(generationsAmountSlider, generationsAmountMonitor, SchemeGenerator.conditions.amountOfGenerations, amountFormat);
    }

    private void loadValues(Slider slider, Label monitor, double value, DecimalFormat textFormat) {
        double valueToSetForSlider = value;
        if (valueToSetForSlider == -1) {
            valueToSetForSlider = (slider.getMin() + slider.getMax()) / 2;
        }
        slider.setValue(valueToSetForSlider);
        monitor.setText(textFormat.format(slider.getValue()));
    }

    private void setListener(Slider slider, Label monitor, DecimalFormat costFormat) {
        if (!slider.equals(maxPrisonersAmountSlider)) {
            slider.valueProperty().addListener((observable, oldValue, newValue) -> monitor.setText(costFormat.format(newValue)));
        } else {
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                monitor.setText(costFormat.format(newValue));
                slider.setValue(Math.round((double) newValue));
            });
        }
    }

    @FXML
    private void onNextPageButtonClick(ActionEvent actionEvent) {
        saveSlidersData();

        Stage nextStage = null;
        try {
            nextStage = FXMLLoader.load(getClass().getResource("/GUI/Templates/DimensionsForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        Stage renderedStage = (Stage) nextPageButton.getScene().getWindow();
        renderedStage.setScene(nextStage.getScene());
    }

    private void saveSlidersData() {
        SchemeGenerator.conditions.budget = Math.round(100.0 * maxBudgetSlider.getValue()) / 100.0;
        SchemeGenerator.conditions.amountOfPrisoners = (int) Math.round(amountOfPrisonersSlider.getValue());
        SchemeGenerator.conditions.priceOfPrisonWard = Math.round(100.0 * priceOfPrisonWardSlider.getValue()) / 100.0;
        SchemeGenerator.conditions.maxAmountOfPrisonersInPrisonWard = (int) Math.round(maxPrisonersAmountSlider.getValue());
        SchemeGenerator.conditions.priceOfSanitaryNook = Math.round(100.0 * priceOfSanitaryNookSlider.getValue()) / 100.0;
        SchemeGenerator.conditions.cameraRange = Math.round(100.0 * cameraRangeSlider.getValue()) / 100.0;
        SchemeGenerator.conditions.amountOfGenerations = (int) Math.round(generationsAmountSlider.getValue());
    }

}
