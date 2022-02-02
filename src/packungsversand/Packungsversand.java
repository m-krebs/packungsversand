
package packungsversand;

import javafx.application.Application;
import javafx.stage.Stage;

public class Packungsversand extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        new PortPicker(stage);
        /*Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();*/
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
