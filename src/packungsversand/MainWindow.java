package packungsversand;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainWindow extends Stage {
    public MainWindow() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxml/main.fxml")));
        Scene scene = new Scene(root);
        this.setTitle("Packungsversand");
        this.setScene(scene);
        this.show();
        this.setResizable(false);
    }
}
