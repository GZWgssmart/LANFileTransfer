package com.gs.transfer;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Created by WangGenshen on 12/26/15.
 */
public class IPTableModel extends DefaultTableModel {

    public IPTableModel(Vector datas, Vector columns) {
        super(datas, columns);
    }

    @SuppressWarnings("unchecked")
    public Vector<Vector<Object>> getDatas() {
        return getDataVector();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getDatas().get(0).get(columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex ==0) {
            return false;
        }
        return true;
    }

}
