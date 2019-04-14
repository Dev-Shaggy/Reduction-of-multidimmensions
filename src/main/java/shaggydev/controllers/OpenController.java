package shaggydev.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import shaggydev.interfaces.iController;
import shaggydev.models.DataObject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


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

    private DataObject dataObject;

    @Override
    public void setUpController(AppContoller app){
        this.appContoller = app;
    }
    @FXML
    public void openFile() {

        try{
            FileChooser fch = new FileChooser();
            file = fch.showOpenDialog(openFileButton.getParent().getScene().getWindow());

            path.setText(file.getName());
            dataObject.SetData(file);

            setRAW_DATA();

        }catch (Exception e){
            //TODO dodać logger
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataObject = DataObject.getInstance();

        try{
            setRAW_DATA();
        }catch (Exception e){
//TODO doać logger
        }
//TODO poprawić bindowanie lub inicjować pola kolumn i tutułu

        col_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                col_id.setText(oldValue);
            }

            try{
                column_description = Integer.parseInt(col_id.getText());
            }catch (Exception e){
                column_description = 0;
            }
            dataObject.setCol_desc_id(column_description);
        });
        col_id.disableProperty().bind(col_desc.selectedProperty().not());

        col_desc.selectedProperty().addListener((observable, oldValue, newValue) -> dataObject.setCol_desc(col_desc.isSelected()));

        title.selectedProperty().addListener((observable, oldValue, newValue) -> {
            dataObject.setTitle(title.isSelected());
            setRAW_DATA();
        });
    }


    private void setRAW_DATA(){
        RAW_DATA.getColumns().clear();
        ObservableList<String[]> observableList = FXCollections.observableArrayList();
        observableList.addAll(dataObject.getStringTab());

        if(dataObject.isTitle()) observableList.remove(0);

        for(int i=0;i<dataObject.getStringTab()[0].length;i++){
            TableColumn tableColumn;

            if(dataObject.isTitle()) tableColumn = new TableColumn(dataObject.getStringTab()[0][i]);
            else tableColumn = new TableColumn("x"+i);

            final int colNo = i;

            tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue()[colNo]));

            tableColumn.setPrefWidth(120);
            RAW_DATA.getColumns().add(tableColumn);
        }
        RAW_DATA.setItems(observableList);
    }


    @FXML
    public void prepareData() {
        dataObject.parseDouble();
    }
}
