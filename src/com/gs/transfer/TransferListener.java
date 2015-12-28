package com.gs.transfer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by WangGenshen on 12/25/15.
 */
public class TransferListener implements ActionListener {

    private FileTransfer transfer;
    private File choosedFile;

    public TransferListener(FileTransfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            JButton btn = (JButton) source;
            String btnName = btn.getName();
            if (btnName.endsWith(Constants.BUTTON_GET_IP_NAME)) {
                searchIP();
            } else if (btnName.equals(Constants.BUTTON_FILE_NAME)) {
                showFileChooser();
            } else if (btnName.equals(Constants.BUTTON_TRANSFER_NAME)) {
                transfer();
            } else if(btnName.equals(Constants.BUTTON_ALL_IP_NAME)) {
                selectAllIP();
            } else if(btnName.equals(Constants.BUTTON_NONE_IP_NAME)) {
                unselectAllIP();
            } else if(btnName.equals(Constants.BUTTON_SAVE_NAME)) {
                saveIP();
            }
        }
    }

    private void searchIP() {
        JTable ipTable = transfer.getIpTable();
        TransferUtil.getAllIP(transfer.getIpTxt().getText(), ipTable);
    }

    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // fileChooser.setFileFilter(new TransferFileFilter());
        int option = fileChooser.showDialog(new JLabel(), Constants.FILE_CHOOSER_TITLE);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.isDirectory()) {
                showMsg(transfer);
            } else if (file.isFile()) {
                if (TransferUtil.checkFileType(file.getName())) {
                    choosedFile = file;
                } else {
                    showMsg(transfer);
                }
            }
        }
    }

    private void transfer() {
        JTable ipTable = transfer.getIpTable();
        try {
            IPTableModel model = (IPTableModel) ipTable.getModel();
            if(model != null) {
                for(Vector<Object> ip : model.getDatas()) {
                    if((boolean) ip.get(1)) {
                        System.out.println();
                        transfer.getClient().transfer((String) ip.get(0), choosedFile);
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void selectAllIP() {
        JTable ipTable = transfer.getIpTable();
        IPTableModel model = (IPTableModel) ipTable.getModel();
        for(Vector<Object> ip : model.getDatas()) {
            ip.set(1, Boolean.TRUE);
        }
        ipTable.setModel(model);
        ipTable.repaint();
    }

    private void unselectAllIP() {
        JTable ipTable = transfer.getIpTable();
        IPTableModel model = (IPTableModel) ipTable.getModel();
        for(Vector<Object> ip : model.getDatas()) {
            ip.set(1, Boolean.FALSE);
        }
        ipTable.setModel(model);
        ipTable.repaint();
    }

    private void saveIP() {
        IPTableModel model = (IPTableModel) transfer.getIpTable().getModel();
        if(model != null) {
            String allIp = "";
            for(Vector<Object> ip : model.getDatas()) {
                if((boolean) ip.get(1)) {
                    String ipStr = (String) ip.get(0);
                    if(allIp.equals("")) {
                        allIp = ipStr;
                    } else {
                        allIp += "\n" + ipStr;
                    }
                }
            }
            if(!allIp.equals("")) {
                try {
                    TransferUtil.saveIP(allIp);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void showMsg(FileTransfer transfer) {
        JOptionPane.showMessageDialog(transfer.getContentPane(), Constants.FILE_TYPE_INFO, Constants.DIALOG_INFO, JOptionPane.INFORMATION_MESSAGE);
    }

}
