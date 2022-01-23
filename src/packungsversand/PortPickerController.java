package packungsversand;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType;

public class PortPickerController extends Stage implements Initializable {
    @FXML
    private Label txtInfo;
    @FXML
    private Button btnTest;
    @FXML
    private ChoiceBox chbPort;
    @FXML
    private Button btnFertig;
    @FXML
    private Button btnAbbr;

    private ObservableList<String> mdlPorts;

    @FXML
    public void onBtnClick(ActionEvent actionEvent) {
        Button temp = (Button) actionEvent.getSource();
        if (temp == btnAbbr){
            ((Stage)btnAbbr.getScene().getWindow()).close();
        } else if (temp == btnFertig) {
            System.out.println("Fertig");
        } else {

            System.out.println(chbPort.getValue());
            System.out.println("Test Connection");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mdlPorts = FXCollections.observableArrayList();
        chbPort.setItems(mdlPorts);
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            mdlPorts.add(port.getDescriptivePortName());
        }
        chbPort.getSelectionModel().selectFirst();
    }
}
