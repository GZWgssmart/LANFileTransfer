package com.gs.transfer;

import java.io.*;
import java.net.InetAddress;
import java.util.Vector;

/**
 * Created by WangGenshen on 12/26/15.
 */
public class TransferUtil {

    public static boolean ping(String ip){
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(1000);
        } catch (IOException e) {
            return false;
        }
    }

    public static Vector<Vector<Object>> getAllIP(String section) {
        Vector<Vector<Object>> allIP = new Vector<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (section.startsWith(Constants.DEFAULT_IP_START)) {
                    for (int i = 1; i < 255; i++) {
                        String ip = section + "." + i;
                        if (ping(ip)) {
                            Vector<Object> ips = new Vector<>();
                            ips.add(ip);
                            ips.add(Boolean.FALSE);
                            allIP.add(ips);
                        }
                    }
                }
            }
        }).start();
        return allIP;
    }

    public static Vector<Vector<Object>> getAllIP1(String section) {
        Vector<Vector<Object>> allIP = new Vector<>();
        if (section.startsWith(Constants.DEFAULT_IP_START)) {
            for (int i = 1; i < 255; i++) {
                String ip = section + "." + i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (ping(ip)) {
                            Vector<Object> ips = new Vector<>();
                            ips.add(ip);
                            ips.add(Boolean.FALSE);
                            allIP.add(ips);
                        }
                    }
                }).start();
            }
        }
        return allIP;
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
        mkConfigDir();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(Constants.IP_DIR + "/" + Constants.IP_FILE))));
        writer.write(allIp);
        writer.flush();
        writer.close();
    }

    private static void mkConfigDir() {
        File file = new File(Constants.IP_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static boolean checkFileType(String fileName) {
        for(String type : Constants.FILE_TYPES) {
            return fileName.endsWith(type);
        }
        return false;
    }

}
