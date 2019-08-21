package shaggydev.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import shaggydev.interfaces.iController;
import shaggydev.models.DataObject;
import shaggydev.models.MDS;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MDSController implements iController, Initializable {

    @FXML
    public ScatterChart MDS_chart;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private AppContoller appContoller;

    public void setUpController(AppContoller app) {
        this.appContoller = app;
    }

    private DataObject dataObject;
    private MDS mds;
    private List<String> dataSeriesNames;

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
                Logger.getLogger(getClass().getName()).warning("Błąd nazw grup.");
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
                    //
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
                MDS_chart.getData().addAll(series);


            } else {
                XYChart.Series<Number, Number> values = new XYChart.Series<>();
                for (double[] doubles : table) {

                    values.setName("PCA");
                    values.getData().add(new XYChart.Data<>(doubles[0], doubles[1]));

                }
                //noinspection unchecked
                MDS_chart.getData().addAll(values);

            }
        }
    }


    @FXML
    public void runAlgorithm() {
        MDS_chart.getData().clear();
        Thread thread = new Thread(mds);
        thread.start();
        setSeriesNames();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Logger.getLogger(getClass().getName()).warning("Błąd uruchamiania algorytmu MDS");

        }
        drawChart(mds.getCoordinates());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            dataObject = DataObject.getInstance();
            mds = MDS.getInstance();
            mds.setData(dataObject.normalizeData());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(getClass().getName()).warning("Błąd inicjalizacji obiektu MDS");

        }

    }
}
