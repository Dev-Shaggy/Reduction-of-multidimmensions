package shaggydev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import shaggydev.interfaces.iController;
import shaggydev.models.RowString;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppContoller implements Initializable {

//  GUI
    @FXML
    public BorderPane borderPane;

    private iController controller;



//  Variables
    private ResourceBundle bundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         bundle = ResourceBundle.getBundle("bundles.messages");
    }

    @FXML
    public void open() {
        loadUI("OpenLayout");
    }

    @FXML
    public void pca() {

        loadUI("PCALayout");
    }
    @FXML
    public void mds() {
        loadUI("MDSLayout");
    }
    @FXML
    public void som() {
        loadUI("SOMLayout");
    }
    @FXML
    public void clear() {
        borderPane.setCenter(null);
    }
    private void loadUI(String ui){
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/"+ui+".fxml"));
            loader.setResources(bundle);
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        borderPane.setCenter(root);
    }
}
