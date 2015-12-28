package com.gs.transfer;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.util.Vector;

/**
 * Created by WangGenshen on 12/26/15.
 */
public class TransferUtil {

    public static boolean ping(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(3000);
        } catch (IOException e) {
            return false;
        }
    }

    public static void getIP(String ip, IPTableModel model) {
        Thread ipSearchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (ping(ip)) {
                    Vector<Object> ips = new Vector<>();
                    ips.add(ip);
                    ips.add(Boolean.FALSE);
                    model.addRow(ips);
                }
            }
        });
        ipSearchThread.setPriority(Thread.MAX_PRIORITY);
        ipSearchThread.start();
    }

    public static void getAllIP(String section, JTable ipTable) {
        if (section.startsWith(Constants.DEFAULT_IP_START)) {
            IPTableModel model = (IPTableModel) ipTable.getModel();
            model.getDataVector().clear();
            ipTable.setModel(model);
            ipTable.repaint();
            for (int i = 1; i < 255; i++) {
                getIP(section + "." + i, model);
            }
        }
    }

    public static Vector<Vector<Object>> getAllIP() {
        Vector<Vector<Object>> allIP = new Vector<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(Constants.IP_DIR + "/" + Constants.IP_FILE))));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Vector<Object> ip = new Vector<>();
                        ip.add(line);
                        ip.add(Boolean.FALSE);
                        allIP.add(ip);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return allIP;
    }

    public static Vector<String> getColumns() {
        Vector<String> columns = new Vector<>();
        for (String column : Constants.TABLE_COLUMNS) {
            columns.add(column);
        }
        return columns;
    }

    public static void saveIP(String allIp) throws IOException {
        mkDir(Constants.IP_DIR);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(Constants.IP_DIR + "/" + Constants.IP_FILE))));
        writer.write(allIp);
        writer.flush();
        writer.close();
    }

    public static void mkDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static boolean checkFileType(String fileName) {
        for (String type : Constants.FILE_TYPES) {
            return fileName.toLowerCase().endsWith(type);
        }
        return false;
    }

}
