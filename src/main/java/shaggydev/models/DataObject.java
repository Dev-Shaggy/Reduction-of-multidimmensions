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
    private List<RowFloat> _rows;
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
    public void parseFloat(){
        _rows = new ArrayList<>();

        int cursor = title ? 1 : 0;

        if(col_desc){
            for (int i=cursor;i<_list.size();i++){
                _rows.add(new RowFloat(_list.get(i).getRow(col_desc_id)));
            }
        }else {
            for (int i=cursor;i<_list.size();i++){
                _rows.add(new RowFloat(_list.get(i).getRow()));
            }
        }
    }

    public List<RowString> get_list() {
        return _list;
    }
    public List<RowFloat> get_rows() {
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
