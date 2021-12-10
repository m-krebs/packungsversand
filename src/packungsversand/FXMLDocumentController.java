/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packungsversand;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import waage.waageDat;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Ralf, Michael
 */


public class FXMLDocumentController implements Initializable {
    HashMap<RadioButton, Double> schraubenGewicht;
    ArrayList<Button> buttons = new ArrayList<>();
    Alert alert = new Alert(Alert.AlertType.ERROR);
    private RadioButton rbPressed;
    // region importFXML
    @FXML
    private VBox vbArten;
    @FXML
    private HBox hbTitle;
    @FXML
    private HBox hbArten;
    @FXML
    private Button btnSchraube;
    @FXML
    private Button btnMutter;
    @FXML
    private Button btnNagel;
    @FXML
    private TextField txtAnzahl;
    @FXML
    private TextField txtGewicht;
    @FXML
    private Button btnWiegen;
    @FXML
    private Button btnAbsenden;
    @FXML
    private TextField txtArtGewicht;
    @FXML
    private Button btnAbbrechen;
    @FXML
    private Label txtMeldung;
    @FXML
    private RadioButton rb3x20;

    @FXML
    private ToggleGroup grpSchrauben;

    @FXML
    private RadioButton rb3x40;

    @FXML
    private RadioButton rb4x30;

    @FXML
    private RadioButton rb4x50;

    @FXML
    private RadioButton rb5x40;

    @FXML
    private RadioButton rb5x60;

    // endregion


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        waageDat.openPort();
        btnAbsenden.setDisable(true);
        schraubenGewicht = new HashMap<>();
        addButtons();
    }

    private void addButtons() {
        ObservableList<Toggle> toggles = grpSchrauben.getToggles();
        double i = 1;
        for (Toggle tog : toggles) {
            schraubenGewicht.put((RadioButton) tog, i);
            i += 0.3;
        }
    }

    @FXML
    private void getArt(ActionEvent event) {
//        int index = buttons.indexOf(event.getSource());
//        System.out.println(index);
//
//        pressed = buttons.get(index);
    }

    @FXML
    public void onWiegen(ActionEvent event) {
        try {
            double need = schraubenGewicht.get(rbPressed) * Double.parseDouble(txtAnzahl.getText());
            System.out.println(schraubenGewicht.get(rbPressed) + " * " + txtAnzahl.getText() + " = " + need);
            Double have = 0.0;
            System.out.println(have);

            have = Double.valueOf((waageDat.getData()));

            System.out.println(have);
            txtGewicht.setText(String.valueOf(have));
            if (have >= need) {
                txtGewicht.setStyle("-fx-border-color: green");
                btnAbsenden.setDisable(false);
                double diff = Math.ceil(have / schraubenGewicht.get(rbPressed));
                txtMeldung.setText(String.format("Es sind %.0f %s vorhanden", diff, rbPressed.getText()));
            } else {
                txtGewicht.setStyle("-fx-border-color: red");
                double diff = Math.ceil((need - have) / schraubenGewicht.get(rbPressed));
                String text = String.format("Es fehlen %.0f %s", diff, rbPressed.getText());
                txtMeldung.setText(text);
            }
        } catch (NullPointerException np) {
            alert.setContentText("Bitte eine Art auswählen");
            alert.showAndWait();
        }

    }

    @FXML
    public void onAbbrechen(ActionEvent event) {
        for (Button btn : buttons) {
            btn.setDisable(false);
        }
        txtGewicht.setText("");
        txtAnzahl.setText("");
    }

    @FXML
    void getSchraube(ActionEvent event) {
        RadioButton rb = (RadioButton) event.getSource();
        double gewicht = schraubenGewicht.get(rb);
        System.out.println(gewicht);
        rbPressed = rb;
    }

}
