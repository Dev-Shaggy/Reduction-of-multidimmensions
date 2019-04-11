package shaggydev.models;

import java.util.ArrayList;
import java.util.List;

public class RowString {

    private List<String> _list;

    public RowString(String row){
        String[] splitRow = row.split(" ");
        _list = new ArrayList<>();

        for(String s : splitRow){
            _list.add(s);
        }
    }

    public List<String> getStringRow(){
        return _list;
    }

    public List<Float> getRow(){

        List<Float> dlist = new ArrayList<>();
        for(int i=0;i<_list.size();i++) {
            try {
                dlist.add(Float.parseFloat(_list.get(i)));
            }catch (Exception e) {
                dlist.add(0f);
            }
        }
        return dlist;
    }

    public List<Float> getRow(int col_title){
        List<Float> dlist = new ArrayList<>();
        for(int i=0;i<_list.size();i++) {
            if(i!=col_title) {
                try {
                    dlist.add(Float.parseFloat(_list.get(i)));
                }catch (Exception e) {
                    dlist.add(0f);
                }
            }
        }
        return dlist;
    }

    public String getStringByID(int id) {
        return _list.get(id);
    }
}
