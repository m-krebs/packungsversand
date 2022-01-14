package packungsversand;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class SchraubenPane extends VBox {
    public SchraubenPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("schraubenPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setLocation();

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
