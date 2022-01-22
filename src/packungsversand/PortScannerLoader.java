package packungsversand;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static javafx.scene.control.Alert.AlertType;

public class PortScannerLoader extends AnchorPane {
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

    public PortScannerLoader(Stage owner) {

        owner.initOwner(owner);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("portSelector.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
        }
    }
}
