package shaggydev.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import shaggydev.interfaces.iController;
import shaggydev.models.DataObject;
import shaggydev.models.PCA;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PCAController implements iController, Initializable {

    @FXML
    public ScatterChart PCA_chart;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private AppContoller appContoller;

    private DataObject dataObject;
    private PCA pca;
    private List<String> dataSeriesNames;

    public void setUpController(AppContoller app) {
        this.appContoller = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            dataObject = DataObject.getInstance();
            pca = PCA.getInstance();
            pca.setData(dataObject.normalizeData());
        } catch (Exception e) {
//TODO dodać logger
//            e.printStackTrace();
        }

    }

    private void setSeriesNames() {
        dataSeriesNames = new ArrayList<>();
        int cursor = dataObject.isTitle() ? 1 : 0;


        if (dataObject.isCol_desc()) {
            int x;
            try {
                x = dataObject.getCol_desc_id();

                for (int i = cursor; i < dataObject.get_list().size(); i++) {
                    String name = dataObject.get_list().get(i).getStringByID(x);
                    if (!dataSeriesNames.contains(name)) {
                        dataSeriesNames.add(name);
                    }
                }

            } catch (Exception e) {
//TODO dodać logger

            }
        }
    }

    private void drawChart(double[][] table) {
        if (table[0].length == 2) {
            if (dataObject.isCol_desc()) {
                List<XYChart.Series<Number, Number>> series = new ArrayList<>();

                for (String name : dataSeriesNames) {
                    XYChart.Series<Number, Number> dataSet = new XYChart.Series<>();
                    dataSet.setName(name);
                    series.add(dataSet);
                }

                int x = 0;
                try {
                    x = dataObject.getCol_desc_id();
                } catch (Exception e) {
                    //TODO dodać logger

                }

                int cursor = 0;
                if (dataObject.isTitle())
                    cursor++;

                for (int i = 0; i < table.length; i++) {

                    for (XYChart.Series<Number, Number> numberNumberSeries : series) {
                        if (numberNumberSeries.getName().equals(dataObject.get_list().get(i + cursor).getStringByID(x))) {
                            numberNumberSeries.getData().add(new XYChart.Data<>(table[i][0], table[i][1]));
                        }
                    }
                }
                //noinspection unchecked
                PCA_chart.getData().addAll(series);


            } else {
                XYChart.Series<Number, Number> values = new XYChart.Series<>();
                for (double[] doubles : table) {

                    values.setName("PCA");
                    values.getData().add(new XYChart.Data<>(doubles[0], doubles[1]));

                }
                //noinspection unchecked
                PCA_chart.getData().addAll(values);

            }
        }
    }


    @FXML
    public void runAlgorithm() {

        PCA_chart.getData().clear();
        Thread thread = new Thread(pca);
        thread.start();
        setSeriesNames();
        try {
            thread.join();
        } catch (InterruptedException e) {
//            e.printStackTrace();
//TODO dodać logger
        }
        drawChart(pca.getCoordinates());
    }
}
