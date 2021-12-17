/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packungsversand;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import waage.scanWaage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Ralf, Michael
 */


public class FXMLDocumentController implements Initializable {
    HashMap<Button, Double> schraubenGewicht;
    //HashMap<RadioButton, Double> schraubenGewicht;

    ArrayList<Button> schrauben = new ArrayList<>();
    ArrayList<Button> arten = new ArrayList<>();

    Alert alert = new Alert(Alert.AlertType.ERROR);
    //private RadioButton rbPressed;
    private String art;
    private Button pressed;
    private Button pressedArt;

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

    @FXML
    private Button btnSch3x20;

    @FXML
    private Button btnSch3x40;

    @FXML
    private Button btnSch4x30;

    @FXML
    private Button btnSch4x50;

    @FXML
    private Button btnSch5x40;

    @FXML
    private Button btnSch5x60;

    @FXML
    private HBox hbSchButtons;
    // endregion


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scanWaage.openPort();
        btnAbsenden.setDisable(true);
        schraubenGewicht = new HashMap<>();
        addButtons();

    }

    private void addButtons() {
        double i = 1;
        for (Node btn : hbSchButtons.getChildren()) {
            schrauben.add((Button) btn);
            i += 0.3;
            schraubenGewicht.put((Button) btn, i);
        }

        for (Node btn : vbArten.getChildren()) {
            arten.add((Button) btn);
        }

        /* Radion Buttons von früher
        ObservableList<Toggle> toggles = grpSchrauben.getToggles();
        double i = 1;
        for (Toggle tog : toggles) {
            schraubenGewicht.put((RadioButton) tog, i);
            i += 0.3;
        }
        */

    }

    @FXML
    private void getArt(ActionEvent event) {
        int index = arten.indexOf((Button) event.getSource());
        System.out.println(index);
        pressedArt = arten.get(index);
    }

    @FXML
    public void onWiegen(ActionEvent event) {
        try {
            berechneGewicht(pressed);
        } catch (NullPointerException np) {
            alert.setContentText("Bitte eine Art auswählen");
            alert.showAndWait();
        }

    }

    private void berechneGewicht(ButtonBase pressed) {
        double need = schraubenGewicht.get(this.pressed) * Double.parseDouble(txtAnzahl.getText());
        System.out.println(schraubenGewicht.get(this.pressed) + " * " + txtAnzahl.getText() + " = " + need);
        Double have = 0.0;
        System.out.println(have);

        have = Double.valueOf((scanWaage.getData()));

        System.out.println(have);
        txtGewicht.setText(String.valueOf(have));
        if (have >= need) {
            txtGewicht.setStyle("-fx-border-color: green");
            btnAbsenden.setDisable(false);
            double diff = Math.ceil(have / schraubenGewicht.get(this.pressed));
            txtMeldung.setText(String.format("Es sind %.0f %s vorhanden", diff, this.pressed.getText()));
        } else {
            txtGewicht.setStyle("-fx-border-color: red");
            double diff = Math.ceil((need - have) / schraubenGewicht.get(this.pressed));
            String text = String.format("Es fehlen %.0f %s", diff, this.pressed.getText());
            txtMeldung.setText(text);
        }
    }

    @FXML
    public void onAbbrechen(ActionEvent event) {
        for (Button btn : schrauben) {
            btn.setDisable(false);
        }
        txtGewicht.setText("");
        txtAnzahl.setText("");
    }

    @FXML
    private void getSchraube(ActionEvent event) {
        /*
        RadioButton rb = (RadioButton) event.getSource();
        double gewicht = schraubenGewicht.get(rb);
        System.out.println(gewicht);
        pressed = rb;

        Button btn = (Button) event.getSource();
        double gewicht = schraubenGewicht.get(btn);
        System.out.println("Gewicht: " + gewicht);
        pressed = btn;
        */
        int index = schrauben.indexOf((Button) event.getSource());
        System.out.println(index);
        pressedArt = schrauben.get(index);
    }

}
