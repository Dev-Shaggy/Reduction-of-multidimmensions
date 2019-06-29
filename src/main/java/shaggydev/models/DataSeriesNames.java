package shaggydev.models;

import javafx.scene.paint.Color;

import java.util.Random;

public class DataSeriesNames {

    public String name;
    public Color color;

    public DataSeriesNames(String name) {
        this.name = name;

        Random r = new Random();
        color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
    }
}
