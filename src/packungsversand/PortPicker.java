package packungsversand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PortPicker extends Stage {
    PortPicker(Stage parentStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/portPicker.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        this.initOwner(parentStage);
        this.setScene(scene);
        this.setTitle("Portauswahl");
        this.show();
    }
}
