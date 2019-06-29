package shaggydev.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RowString {

    private List<String> _list;

    RowString(String row) {
        String[] splitRow = row.split(" ");
        _list = new ArrayList<>();

        _list.addAll(Arrays.asList(splitRow));
    }

    List<Double> getRow() {

        List<Double> dlist = new ArrayList<>();
        for (String s : _list) {
            try {
                dlist.add(Double.parseDouble(s));
            } catch (Exception e) {
                dlist.add(0.0);
            }
        }
        return dlist;
    }

    List<Double> getRow(int col_title) {
        List<Double> dlist = new ArrayList<>();
        for (int i = 0; i < _list.size(); i++) {
            if (i != col_title) {
                try {
                    dlist.add(Double.parseDouble(_list.get(i)));
                } catch (Exception e) {
                    dlist.add(0.0);
                }
            }
        }
        return dlist;
    }

    public String getStringByID(int id) {
        return _list.get(id);
    }
}
