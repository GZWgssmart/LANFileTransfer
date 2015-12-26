package com.gs.transfer;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Created by WangGenshen on 12/25/15.
 */
public class FileTransferClient {

    public void transfer(String ip, File file) throws IOException {
        new Thread(new ClientThread(ip, file)).start();
    }

    public void transfer(List<String> ips, File file) throws IOException {
        for(String ip : ips) {
            transfer(ip, file);
        }
    }

    private class ClientThread implements Runnable {

        private String ip;
        private File file;

        public ClientThread(String ip, File file) {
            this.ip = ip;
            this.file = file;
        }

        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ip);
                // Socket socket = new Socket(address, Constants.PORT);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(address, Constants.PORT), 3000);
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                DataOutputStream dos  = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
                byte[] buf = new byte[1024];
                int length;
                while ((length = dis.read(buf)) != -1) {
                    dos.write(buf, 0, length);
                }
                dis.close();
                dos.flush();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
