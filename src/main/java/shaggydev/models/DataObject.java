package shaggydev.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataObject {
    private static DataObject ourInstance = new DataObject();

    public static DataObject getInstance() {
        return ourInstance;
    }

    private DataObject() {
    }

    private List<RowString> _list;
    private List<RowDouble> _rows;
    private boolean title;
    private boolean col_desc;
    private int col_desc_id;

    public void SetData(File file){
        Scanner in = null;

        _list = new ArrayList<>();

        try{
            in = new Scanner(file);
            while (in.hasNextLine()){
                _list.add(new RowString(in.nextLine()));
            }
        }catch (Exception e){
        }
    }
    public void parseDouble(){
        _rows = new ArrayList<>();

        int cursor = title ? 1 : 0;

        if(col_desc){
            for (int i=cursor;i<_list.size();i++){
                _rows.add(new RowDouble(_list.get(i).getRow(col_desc_id)));
            }
        }else {
            for (int i=cursor;i<_list.size();i++){
                _rows.add(new RowDouble(_list.get(i).getRow()));
            }
        }
    }

    public String[][] getStringTab(){
        String [][] table = new String[_list.size()][_list.get(0).getRow().size()];

        for(int i=0;i< _list.size();i++){
            for (int j=0;j<_list.get(0).getRow().size();j++){
                table[i][j] = _list.get(i).getStringByID(j);
            }
        }
        return table;
    }













    public List<RowString> get_list() {
        return _list;
    }
    public List<RowDouble> get_rows() {
        return _rows;
    }

    public boolean isTitle() {
        return title;
    }
    public void setTitle(boolean title) {
        this.title = title;
    }

    public boolean isCol_desc() {
        return col_desc;
    }
    public void setCol_desc(boolean col_desc) {
        this.col_desc = col_desc;
    }

    public int getCol_desc_id() {
        return col_desc_id;
    }
    public void setCol_desc_id(int col_desc_id) {
        this.col_desc_id = col_desc_id;
    }

}
