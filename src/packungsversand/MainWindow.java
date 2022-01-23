package packungsversand;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class MainWindow extends Stage {
    public MainWindow() throws IOException {
        Parent root = /*
        FXMLLoader fxmlLoader = */FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        Scene scene = new Scene(root);
        this.setTitle("Packungsversand");
        this.setScene(scene);
        this.show();
    }
}
