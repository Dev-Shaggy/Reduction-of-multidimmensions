package shaggydev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Klasa startowa - rozruchowa.
 *
 */
public class App extends Application
{
    private static String MainLayout = "/fxml/MainLayout.fxml";
    private static String styles = "/styles/globalstyle.css";
    private static String bundles = "/bundles.messages";

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(MainLayout));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(bundle);
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(styles);
        primaryStage.setScene(scene);

        primaryStage.setTitle(bundle.getString("title.application"));
        primaryStage.show();
    }
}
