
package packungsversand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.applet.Main;


public class Packungsversand extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();*/

        new MainWindow();
//        new PortPicker(stage);
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
