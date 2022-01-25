package packungsversand;

import com.Ostermiller.util.CSVParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import waage.scanWaage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class FXMLDocumentController implements Initializable {
    //region Variablen
    private HashMap<Button, Double> alleWaren;

    private ArrayList<Button> waren = new ArrayList<>();
    private ArrayList<Button> arten = new ArrayList<>();

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    private Button pressed;
    private Button pressedArt;
    //endregion

    //region FXML
    @FXML
    private VBox vbArten;
    @FXML
    private HBox hbTitle;
    @FXML
    private Button btnSchrauben;
    @FXML
    private Button btnMuttern;
    @FXML
    private Button btnNaegel;
    @FXML
    private TextField txtAnzahl;
    @FXML
    private TextField txtGewicht;
    @FXML
    private Button btnWiegen;
    @FXML
    private Button btnAbsenden;
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
    @FXML
    private VBox vbArt;
    @FXML
    private BorderPane bpMain;
    @FXML
    private StackPane stkPane;
    @FXML
    private Pane paneSchraube;
    @FXML
    private Pane paneMutter;
    @FXML
    private HBox hbMutterButtons;
    @FXML
    private ToggleGroup grpSchrauben1;
    @FXML
    private ToggleGroup grpSchrauben11;
    @FXML
    private Pane paneNaegel;
    @FXML
    private Button btnNaeKerb;
    @FXML
    private Button btnNaeStift;
    @FXML
    private Button btnNaeDoppelkopf;
    @FXML
    private Button btnNaeHaken;
    @FXML
    private Button btnNaeHuf;
    @FXML
    private Button btnNaeAnker;
    @FXML
    private HBox hbNaegelButtons;
    @FXML
    private Button btnMutSechskant;
    @FXML
    private Button btnMutVierkant;
    @FXML
    private Button btnMutFluegel;
    @FXML
    private Button btnMutSterngriff;
    @FXML
    private Button btnMutKreuzloch;
    @FXML
    private Button btnMutNut;
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAbsenden.setDisable(true);
        alleWaren = new HashMap<>();
        addButtons();
        DialogPane dialog = alert.getDialogPane();
        alert.setHeaderText(null);
        dialog.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    }

    //region FXML Methoden

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

        int index = arten.indexOf((Button) event.getSource());
        pressedArt = arten.get(index);
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
            alert.setContentText("Andere Exception");
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

    @FXML
    private void getWare(ActionEvent event) {
        int index = waren.indexOf((Button) event.getSource());
        pressed = waren.get(index);
    }

    //endregion

    //region Util
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
            arten.add((Button) btn);
        }
        readCSV();
    }

    private void berechneGewicht() {
        double need = alleWaren.get(this.pressed) * Double.parseDouble(txtAnzahl.getText());
        Double have = 0.0;
        System.out.println(alleWaren.get(this.pressed) + " * " + txtAnzahl.getText() + " = " + need);
        System.out.println(have);

        have = Double.valueOf((scanWaage.getData()));

        System.out.println(have);
        txtGewicht.setText(String.valueOf(have));
        if (have >= need) {
            txtGewicht.setStyle("-fx-border-color: green");
            btnAbsenden.setDisable(false);
            double diff = Math.ceil(have / alleWaren.get(this.pressed));
            txtMeldung.setText(String.format("Es sind %.0f %s vorhanden", diff, this.pressedArt.getText()));
        } else {
            txtGewicht.setStyle("-fx-border-color: red");
            double diff = Math.ceil((need - have) / alleWaren.get(this.pressed));
            String text = String.format("Es fehlen %.0f %s", diff, this.pressedArt.getText());
            txtMeldung.setText(text);
        }
    }

    private void readCSV() {
        try {
            CSVParser csvParser = new CSVParser(new FileReader("src/packungsversand/waren.csv"));
            csvParser.changeDelimiter(';');
            String[][] values = csvParser.getAllValues();
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
