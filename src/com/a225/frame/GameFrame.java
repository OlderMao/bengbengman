package com.a225.frame;

import com.a225.model.loader.ElementLoader;
import com.a225.thread.GameKeyListener;
import com.a225.thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.List;

public class GameFrame extends JFrame {
    private JPanel contentPane;//�����
    private BeginJPanel beginJPanel;//��ʼ����
    private GameJPanel gameJPanel;//����
    private OverJPanel overJPanel;//��������
    private KeyListener keyListener; //��Ϸ����
    private CardLayout layout;//��Ƭ����


    public GameFrame() {
        init();
    }

    //	��ʼ��
    protected void init() {
        this.setTitle("����man");
        this.setIconImage(new ImageIcon("img\\bg\\tubiao.png").getImage());
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
        this.setSize(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        keyListener = new GameKeyListener();

        this.contentPane = new JPanel();
        this.setContentPane(contentPane);

        this.layout = new CardLayout();
        this.contentPane.setLayout(layout);

        this.beginJPanel = new BeginJPanel();
        this.contentPane.add("begin", beginJPanel);

        this.overJPanel = new OverJPanel();
        this.contentPane.add("over", overJPanel);

        this.layout.show(contentPane, "begin");
        this.setVisible(true);
    }


    //	�л�����
    public void changePanel(String name) {
        layout.show(contentPane, name);
    }

    //	��Ϸ����
    public void startGame() {
        //�½���Ϸ���
        gameJPanel = new GameJPanel();
        //��ӽ���frame
        contentPane.add("game", gameJPanel);
        //�߳�����
        GameThread gameThread = new GameThread();
        gameThread.start();
        //����ˢ���߳�����
        if (gameJPanel != null) {
            new Thread(gameJPanel).start();
        }
    }

    //	�󶨼���
    public void addListener() {
        if (keyListener != null)
            this.addKeyListener(keyListener);
    }

    //	�Ƴ�����
    public void removeListener() {
        this.removeKeyListener(keyListener);
    }

}
