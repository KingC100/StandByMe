package standbyme;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Kiichi
 */
public class StandByMe extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
//        Image icon = new Image("file:res\\icon.png"); // project以下を参照する方法。
        URL url_Empty = StandByMe.class.getResource("res/icon.png"); //src以下を参照する方法。
        Image icon = new Image(url_Empty.toString());
        stage.getIcons().add(icon);
        // タイトルセット
        stage.setTitle("StandByMe");
        // ウィンドウサイズ固定
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
