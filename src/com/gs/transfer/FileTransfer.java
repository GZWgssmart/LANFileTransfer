package com.gs.transfer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Created by WangGenshen on 12/25/15.
 */
public class FileTransfer extends JFrame {

    private FileTransferClient client;

    private JTextField ipTxt;
    private JTable ipTable;

    public FileTransferClient getClient() {
        return client;
    }

    public JTextField getIpTxt() {
        return ipTxt;
    }

    public JTable getIpTable() {
        return ipTable;
    }

    public FileTransfer() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        setSize(480, 500);
        setLocationRelativeTo(null);
        setTitle(Constants.FRAME_TITLE);
        getContentPane().setLayout(new BorderLayout());
        initWidgets();
        TransferUtil.mkDir(Constants.DIR);
        FileTransferServer server = new FileTransferServer();
        server.init();
        client = new FileTransferClient();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        setVisible(true);
    }

    private void initWidgets() {
        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel bottomPanelRight = new JPanel(new FlowLayout());
        JLabel ipLabel = new JLabel(Constants.LABEL_IP);
        ipTxt = new JTextField(8);
        ipTxt.setText(Constants.DEFAULT_IP_SECTION);
        TransferListener listener = new TransferListener(this);
        JButton getIPBtn = new JButton(Constants.BUTTON_GET_IP);
        getIPBtn.setName(Constants.BUTTON_GET_IP_NAME);
        getIPBtn.addActionListener(listener);
        JButton fileBtn = new JButton(Constants.BUTTON_FILE);
        fileBtn.setName(Constants.BUTTON_FILE_NAME);
        fileBtn.addActionListener(listener);
        JButton transferBtn = new JButton(Constants.BUTTON_TRANSFER);
        transferBtn.setName(Constants.BUTTON_TRANSFER_NAME);
        transferBtn.addActionListener(listener);
        topPanel.add(ipLabel);
        topPanel.add(ipTxt);
        topPanel.add(getIPBtn);
        topPanel.add(fileBtn);
        topPanel.add(transferBtn);
        add(topPanel, BorderLayout.NORTH);
        IPTableModel tableModel = new IPTableModel(TransferUtil.getAllIP(), TransferUtil.getColumns());
        ipTable = new JTable(tableModel);
        ipTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ipTable.setSelectionBackground(Color.GRAY);
        JScrollPane scroll = new JScrollPane(ipTable);
        // scroll.setSize(480, 400);
        centerPanel.add(scroll, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        JButton saveBtn = new JButton(Constants.BUTTON_SAVE);
        saveBtn.setName(Constants.BUTTON_SAVE_NAME);
        saveBtn.addActionListener(listener);
        JButton allIPBtn = new JButton(Constants.BUTTON_ALL_IP);
        allIPBtn.setName(Constants.BUTTON_ALL_IP_NAME);
        allIPBtn.addActionListener(listener);
        JButton noneIPBtn = new JButton(Constants.BUTTON_NONE_IP);
        noneIPBtn.setName(Constants.BUTTON_NONE_IP_NAME);
        noneIPBtn.addActionListener(listener);
        bottomPanelRight.add(allIPBtn);
        bottomPanelRight.add(noneIPBtn);
        bottomPanelRight.add(saveBtn);
        bottomPanel.add(bottomPanelRight, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new FileTransfer();
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
