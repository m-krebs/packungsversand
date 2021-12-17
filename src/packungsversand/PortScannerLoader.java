package packungsversand;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import static javafx.scene.control.Alert.AlertType;

public class PortScannerLoader extends AnchorPane {
    public PortScannerLoader() {
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
