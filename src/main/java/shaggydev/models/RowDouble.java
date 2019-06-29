package shaggydev.models;

import java.util.List;

public class RowDouble {

    private List<Double> _row;
    private String name;

    public RowDouble(List<Double> row, String name){
        this._row=row;
        this.name =name;
    }

    public RowDouble(List<Double> row){
        this._row=row;
        this.name = null;
    }
    public double[] get_arrayrow(){
        double []tab = new double[_row.size()];
        for (int i=0;i<_row.size();i++){
            tab[i] = _row.get(i);
        }
        return tab;
    }

    public List<Double> get_row() {
        return _row;
    }
    public String getName(){
        return name;
    }

}
