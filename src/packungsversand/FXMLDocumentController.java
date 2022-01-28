package packungsversand;

import com.Ostermiller.util.CSVParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import waage.scanWaage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class FXMLDocumentController implements Initializable {
    //region Variablen
    HashMap<Button, Double> alleWaren;

    ArrayList<Button> waren = new ArrayList<>();
    ArrayList<Button> kategorien = new ArrayList<>();

    Alert alert = new Alert(Alert.AlertType.ERROR);

    private Button pressed;
    private Button pressedKategorie;

    //endregion

    //region FXML
    @FXML
    private VBox vbArten;
    @FXML
    private Button btnSchrauben;
    @FXML
    private Button btnMuttern;
    @FXML
    private TextField txtAnzahl;
    @FXML
    private TextField txtGewicht;
    @FXML
    private Button btnAbsenden;
    @FXML
    private Label txtMeldung;
    @FXML
    private HBox hbSchButtons;
    @FXML
    private Pane paneSchraube;
    @FXML
    private Pane paneMutter;
    @FXML
    private HBox hbMutterButtons;
    @FXML
    private Pane paneNaegel;
    @FXML
    private HBox hbNaegelButtons;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAbsenden.setDisable(true);
        alleWaren = new HashMap<>();
        addButtons();
        btnSchrauben.fire();
    }

    //region FXML-Methoden

    // Schiebt das Pane der Muttern, Naegel oder der Schrauben nach vorne und macht die jeweils anderen unischtbar
    @FXML
    private void getArt(ActionEvent event) {
        if (event.getSource().equals(btnSchrauben)) {
            paneSchraube.toFront();
            paneSchraube.setVisible(true);
            paneMutter.setVisible(false);
            paneNaegel.setVisible(false);
        } else if (event.getSource().equals(btnMuttern)) {
            paneMutter.toFront();
            paneMutter.setVisible(true);
            paneNaegel.setVisible(false);
            paneSchraube.setVisible(false);
        } else {
            paneNaegel.toFront();
            paneNaegel.setVisible(true);
            paneMutter.setVisible(false);
            paneSchraube.setVisible(false);
        }
        // Speichert den Button der gedrückt wurde
        int index = kategorien.indexOf((Button) event.getSource());
        pressedKategorie = kategorien.get(index);
    }

    @FXML
    public void onWiegen(ActionEvent event) {
        try {
            berechneGewicht();
        } catch (NullPointerException np) {
            alert.setContentText("Bitte eine Art auswählen");
            alert.showAndWait();
        } catch (RuntimeException re) {
            alert.setContentText("Bitte die Waage anschließen");
            alert.showAndWait();
        } catch (Exception e) {
            alert.setContentText("Ein Fehler ist aufgetreten");
            alert.showAndWait();
        }

    }

    @FXML
    public void onAbbrechen(ActionEvent event) {
        for (Button btn : waren) {
            btn.setDisable(false);
        }
        txtGewicht.setText("");
        txtAnzahl.setText("");
    }

    // Der Button, der Art, die gedrückt wurde wird returned
    @FXML
    private void getWare(ActionEvent event) {
        int index = waren.indexOf((Button) event.getSource());
        pressed = waren.get(index);
    }

    //endregion

    //region Util
    // Fügt alle buttons zu den jeweiligen ArrayList
    private void addButtons() {
        for (Node btn : hbSchButtons.getChildren()) {
            waren.add((Button) btn);
        }
        for (Node btn : hbNaegelButtons.getChildren()) {
            waren.add((Button) btn);
        }
        for (Node btn : hbMutterButtons.getChildren()) {
            waren.add((Button) btn);
        }

        for (Node btn : vbArten.getChildren()) {
            kategorien.add((Button) btn);
        }
        readCSV();
    }

    private void berechneGewicht() {
        // das Gewicht der Art * Anzahl die wir benötigen
        double need = alleWaren.get(this.pressed) * Double.parseDouble(txtAnzahl.getText());
        Double have;

        // Parsed den wert der gewogen wurde zu double
        have = Double.valueOf((scanWaage.getData()));

        System.out.println(have);
        txtGewicht.setText(String.valueOf(have));
        if (have >= need) {
            txtGewicht.setStyle("-fx-border-color: green");
            btnAbsenden.setDisable(false);
            // Berechnet die Anzahl die wir haben an hand des Gewichts
            double diff = Math.ceil(have / alleWaren.get(this.pressed));
            txtMeldung.setText(String.format("Es sind %.0f %s %s vorhanden", diff, this.pressed.getText(), this.pressedKategorie.getText()));
        } else {
            txtGewicht.setStyle("-fx-border-color: red");
            double diff = Math.ceil((need - have) / alleWaren.get(this.pressed));
            String text = String.format("Es fehlen %.0f %s %s", diff, this.pressed.getText(), this.pressedKategorie.getText());
            txtMeldung.setText(text);
        }
    }

    private void readCSV() {
        try {
            CSVParser csvParser = new CSVParser(new FileReader("src/packungsversand/waren.csv"));
            csvParser.changeDelimiter(';');
            String[][] values = csvParser.getAllValues();
            // Die Hashmap wird mit den Button und den jeweiligen wert in der CSV gefüllt
            for (int i = 0; i < values.length; i++) {
                alleWaren.put(waren.get(i), Double.valueOf(values[i][1]));
            }
            csvParser.close();
        } catch (IOException e) {
            System.out.println("File Not found");
        }
    }
    //endregion


}
