package packungsversand;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SchraubenPane extends VBox {
    public SchraubenPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schraubenPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setLocation(getClass().getResource("/fxml/schraubenPane.fxml"));

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
