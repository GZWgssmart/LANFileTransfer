package com.gs.transfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by WangGenshen on 12/25/15.
 */
public class FileTransferServer {

    public void init() throws IOException {
        new Thread(new ServerThread()).start();
    }

    private class ServerThread implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(Constants.PORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    String name = dis.readUTF();
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(Constants.DIR_MAC + name)));
                    byte[] buf = new byte[1024];
                    int length;
                    while ((length = dis.read(buf)) != -1) {
                        dos.write(buf, 0, length);
                    }
                    dis.close();
                    dos.flush();
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
