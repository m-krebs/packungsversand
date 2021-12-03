/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packungsversand;

import com.sun.javafx.binding.StringFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    HashMap<Button, Double> artGewicht;
    ArrayList<Button> buttons = new ArrayList<>();
    Alert alert = new Alert(Alert.AlertType.ERROR);

    private Button pressed;

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

// endregion


@Override
public void initialize(URL url, ResourceBundle rb) {
    waageDat.openPort();
    btnAbsenden.setDisable(true);
    artGewicht = new HashMap<>();
    addButtons();
}

    private void addButtons() {
        for (Node btn : vbArten.getChildren()) {
            buttons.add((Button) btn);
            artGewicht.put((Button) btn, 2D);
        }
    }

    @FXML
    private void getArt(ActionEvent event) {
        int index = buttons.indexOf(event.getSource());
        System.out.println(index);
        for (Button btn : buttons) {
            if (buttons.indexOf(btn) != index) {
                btn.setDisable(true);
            }
        }
        pressed = buttons.get(index);
    }

    @FXML
    public void onWiegen(ActionEvent event) {
        try {
            double need = artGewicht.get(pressed) * Double.parseDouble(txtAnzahl.getText());
            System.out.println(artGewicht.get(pressed) + " * " + txtAnzahl.getText() + " = " + need);
            Double have = 0.0;
            System.out.println(have);

            have = Double.valueOf((waageDat.getData()));

            System.out.println(have);
            txtGewicht.setText(String.valueOf(have));
            if (have >= need) {
                txtGewicht.setStyle("-fx-border-color: green");
                btnAbsenden.setDisable(false);
                double diff = Math.ceil(have / artGewicht.get(pressed));
                txtMeldung.setText(String.format("Es sind %.0f %s vorhanden", diff, pressed.getText()));
            } else {
                txtGewicht.setStyle("-fx-border-color: red");
                double diff = Math.ceil((need - have) / artGewicht.get(pressed));
                String text = String.format("Es fehlen %.0f %s", diff, pressed.getText());
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

}
