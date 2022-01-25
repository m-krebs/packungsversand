package packungsversand;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import waage.scanWaage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType;

public class PortPickerController extends Stage implements Initializable {
    @FXML
    private ChoiceBox chbPort;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnAbbr;

    private ObservableList<SerialPort> mdlPorts;
    private Alert alert = new Alert(AlertType.NONE);

    @FXML
    public void onBtnClick(ActionEvent actionEvent) {
        Button temp = (Button) actionEvent.getSource();
        if (temp == btnAbbr) {
            closeWindow(btnAbbr);
        } else if (temp == btnOK) {
            SerialPort port = (SerialPort) chbPort.getSelectionModel().getSelectedItem();
            if (scanWaage.testConnection(port)) {
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
                alert.setContentText("Verbindung fehlgeschlagen");
                alert.setAlertType(AlertType.ERROR);
                alert.show();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mdlPorts = FXCollections.observableArrayList();
        chbPort.setItems(mdlPorts);
        SerialPort[] ports = SerialPort.getCommPorts();
        mdlPorts.addAll(ports);
        chbPort.setConverter(new StringConverter() {
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

    public static void closeWindow(Button btn) {
        ((Stage) btn.getScene().getWindow()).close();
    }
}
