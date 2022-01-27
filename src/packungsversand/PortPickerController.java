package packungsversand;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import waage.scanWaage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType;

public class PortPickerController extends Stage implements Initializable {
    private static ObservableList<SerialPort> mdlPorts = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox chbPort;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnAbbr;
    private Alert alert = new Alert(AlertType.NONE);
    @FXML
    private Button btnLaden;

    public static void closeWindow(Button btn) {
        ((Stage) btn.getScene().getWindow()).close();
    }

    public void setPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        mdlPorts.clear();
        mdlPorts.addAll(ports);
        chbPort.getSelectionModel().selectFirst();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        mdlPorts = FXCollections.observableArrayList();
        chbPort.setItems(mdlPorts);
        setPorts();
        chbPort.setConverter(new StringConverter<Object>() {
            @Override
            public String toString(Object object) {
                SerialPort sp = (SerialPort) object;
                return sp.getDescriptivePortName();
            }

            @Override
            public Object fromString(String string) {
                return null;
            }
        });
        chbPort.getSelectionModel().selectFirst();
        alert.setHeaderText(null);
    }

    @FXML
    public void onBtnClick(ActionEvent actionEvent) {
        Button temp = (Button) actionEvent.getSource();
        if (temp == btnAbbr) {
            closeWindow(btnAbbr);
        } else if (temp == btnOK) {
            SerialPort port = (SerialPort) chbPort.getSelectionModel().getSelectedItem();
            if (scanWaage.testConnection(port)) {
                alert.setTitle("Erfolg");
                alert.setContentText("Verbindung erfolgreich");
                alert.setAlertType(AlertType.INFORMATION);
                alert.showAndWait();
                try {
                    new MainWindow();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                closeWindow(btnOK);
            } else {
                alert.setTitle("Verbindungsfehler");
                alert.setContentText("Verbindung fehlgeschlagen");
                alert.setAlertType(AlertType.ERROR);
                alert.show();
            }
        }
    }

    @FXML
    public void onReload() {
        setPorts();
    }


}
