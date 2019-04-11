package shaggydev.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import shaggydev.interfaces.iController;
import shaggydev.models.RowString;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;


public class OpenController implements iController, Initializable {

    @FXML public Label path;
    @FXML public CheckBox col_desc;
    @FXML public CheckBox title;
    @FXML public TableView RAW_DATA;
    @FXML public Button openFileButton;
    @FXML public TextField col_id;


    //Handler do głównego okna
    private AppContoller appContoller;
    private File file;
    private int column_description;


    @Override
    public void setUpController(AppContoller app){
        this.appContoller = app;
    }
    @FXML
    public void openFile() {

        FileChooser fch = new FileChooser();
        file = fch.showOpenDialog(openFileButton.getParent().getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                col_id.setText(oldValue);
            }
        });

        col_id.disableProperty().bind(col_desc.selectedProperty().not());


        /*
            TODO Dodać bindy/listenery do pół łącząc je z polami w klasie DataObject
         */
    }

    @FXML
    public void prepareData() {
        Scanner in = null;

        List<RowString> stringRows = new ArrayList<>();

        try {
            in = new Scanner(file);
            while (in.hasNextLine()){
                stringRows.add(new RowString(in.nextLine()));
            }

        } catch (FileNotFoundException e) {
        }catch (Exception e){

        }
        if(col_id.getText().length()<=0){
            column_description=0;
        }else{
            column_description = Integer.parseInt(col_id.getText());
        }


    }
}
