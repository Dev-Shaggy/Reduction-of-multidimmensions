package shaggydev.models;

import java.util.List;

public class RowDouble {

    private List<Double> _row;
    private String name;

    RowDouble(List<Double> row, String name) {
        this._row = row;
        this.name = name;
    }

    RowDouble(List<Double> row) {
        this._row = row;
        this.name = null;
    }

    List<Double> get_row() {
        return _row;
    }

    public String getName() {
        return name;
    }

}
