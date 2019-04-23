package shaggydev.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;
import shaggydev.interfaces.iController;
import shaggydev.models.DataObject;
import shaggydev.models.Point;
import shaggydev.models.SOM;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SOMController implements iController, Initializable {

    @FXML public Canvas SOM_canvas;
    @FXML public ProgressIndicator progressIND;
    private AppContoller appContoller;

    public void setUpController(AppContoller app){
        this.appContoller = app;
    }

    private SOM som;
    private List<String> dataSeriesNames;
    private DataObject dataObject;

    private final int CELL_COUNT = 100;

    private final int CELL_SIZE = 400/CELL_COUNT;

    private void setSeriesNames(){
        dataSeriesNames = new ArrayList<>();
        int cursor = dataObject.isTitle() ? 1:0;


        if(dataObject.isCol_desc()){
            int x=0;
            try{
                x= dataObject.getCol_desc_id();

                for( int i= cursor;i<dataObject.get_list().size();i++){
                    String name = dataObject.get_list().get(i).getStringByID(x);
                    if(!dataSeriesNames.contains(name)){
                        dataSeriesNames.add(name);
                    }
                }

            }catch (Exception e){
                //TODO dodać logger
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clearCanvas();
        try{
            dataObject = DataObject.getInstance();
            som = SOM.getInstance();
            som.createMap(CELL_COUNT,dataObject.normalizeData());
        }catch (Exception e){
//TODO dodać logger
//            e.printStackTrace();
        }

    }
    private void clearCanvas(){
        GraphicsContext gc = SOM_canvas.getGraphicsContext2D();
//        gc.setFill(Color.GRAY);
        gc.clearRect(0,0,400,400);
    }

    private void paintCell(Point p){
        GraphicsContext gc = SOM_canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(p.x*CELL_SIZE,p.y*CELL_SIZE,CELL_SIZE,CELL_SIZE);
    }

    @FXML
    public void runAlgorithm() {
        setSeriesNames();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                som.step();
                clearCanvas();
                for (double[] p:dataObject.normalizeData()){
                    paintCell(som.getWinner(p));
                }
                progressIND.setProgress(som.getEpoch()/1000.0);
                if(som.getEpoch()>1000) this.stop();
            }
        }.start();
//        Thread thread = new Thread(som);
//        thread.start();
//        setSeriesNames();
//
//        try {
//            thread.join();
//            clearCanvas();
//
//            for(double[] p:dataObject.normalizeData()){
//                paintCell(som.getWinner(p));
//            }
//
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
