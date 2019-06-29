package shaggydev.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import shaggydev.interfaces.iController;
import shaggydev.models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SOMController implements iController, Initializable {

    @FXML
    public Canvas SOM_canvas;


    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private AppContoller appContoller;

    public void setUpController(AppContoller app) {
        this.appContoller = app;
    }

    private SOM som;
    private DataObject dataObject;

    private List<DataSeriesNames> dsn;

    private final int CELL_COUNT = 20;

    private int CELL_SIZE;


    private void setSeriesNames() {
        List<String> dataSeriesNames = new ArrayList<>();
        dsn = new ArrayList<>();
        int cursor = dataObject.isTitle() ? 1 : 0;


        if (dataObject.isCol_desc()) {
            int x;
            try {
                x = dataObject.getCol_desc_id();

                for (int i = cursor; i < dataObject.get_list().size(); i++) {
                    String name = dataObject.get_list().get(i).getStringByID(x);
                    if (!dataSeriesNames.contains(name)) {
                        dataSeriesNames.add(name);
                        dsn.add(new DataSeriesNames(name));
                    }

                }

            } catch (Exception e) {
                //TODO dodać logger
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clearCanvas();
        try {
            dataObject = DataObject.getInstance();
            som = SOM.getInstance();
            som.createMap(CELL_COUNT, dataObject.normalizeData());
            clearCanvas();
            setSeriesNames();

            CELL_SIZE = 400 / CELL_COUNT;


        } catch (Exception e) {
//TODO dodać logger
//            e.printStackTrace();
        }

    }

    private void clearCanvas() {
        GraphicsContext gc = SOM_canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 400, 400);

        gc.setStroke(new Color(0.5, 0.5, 0.5, 1.0));
        gc.setLineWidth(1);
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                gc.strokeRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void paintCell(Point p, Color c, double dist) {
        GraphicsContext gc = SOM_canvas.getGraphicsContext2D();
        Color tmp = new Color(c.getRed(), c.getGreen(), c.getBlue(), dist);
        gc.setFill(tmp);
        gc.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }


    @FXML
    public void runAlgorithm() {

        double[][] inputs = dataObject.normalizeData();
        clearCanvas();

        int epochs = 50;
        som.reset();

        for (int x = 0; x < epochs; x++) {
            som.step();
        }


        double min;
        String name = "";
        int inputID = 0;

        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                min = som.getDiff(i, j, inputs[0]);
                for (int x = 0; x < inputs.length; x++) {
                    if (min > som.getDiff(i, j, inputs[x])) {
                        min = som.getDiff(i, j, inputs[x]);
                        name = dataObject.get_rows().get(x).getName();
                        inputID = x;
                    }
                }
                for (DataSeriesNames val : dsn) {
                    if (name.equals(val.name)) {
                        paintCell(new Point(i, j), val.color, Math.max(1.0 - som.getDiff(i, j, inputs[inputID]), 0.0));
                    }
                }
            }
        }

    }
}
