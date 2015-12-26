package com.gs.transfer;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * Created by WangGenshen on 12/26/15.
 */
public class IPTableModel extends AbstractTableModel {

    private Vector<Vector<Object>> datas;
    private Vector<String> columns;

    public void setDatas(Vector<Vector<Object>> datas) {
        this.datas = datas;
    }

    public Vector<Vector<Object>> getDatas() {
        return datas;
    }

    public void setColumns(Vector<String> columns) {
        this.columns = columns;
    }

    @Override
    public int getRowCount() {
        return datas.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return datas.get(0).get(columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return datas.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        datas.get(rowIndex).set(columnIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex ==0) {
            return false;
        }
        return true;
    }

}
