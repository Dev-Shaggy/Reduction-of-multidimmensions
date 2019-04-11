package shaggydev.controllers;

import javafx.fxml.Initializable;
import shaggydev.interfaces.iController;

import java.net.URL;
import java.util.ResourceBundle;

public class PCAController implements iController, Initializable {

    private AppContoller appContoller;

    public void setUpController(AppContoller app){
        this.appContoller = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
