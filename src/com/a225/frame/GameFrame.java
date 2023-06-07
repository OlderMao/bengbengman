package com.a225.frame;

import com.a225.model.loader.ElementLoader;
import com.a225.thread.GameKeyListener;
import com.a225.thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.List;

public class GameFrame extends JFrame {
    private JPanel contentPane;//主面板
    private BeginJPanel beginJPanel;//开始画板
    private GameJPanel gameJPanel;//画板
    private OverJPanel overJPanel;//结束画板
    private KeyListener keyListener; //游戏按键
    private CardLayout layout;//卡片布局


    public GameFrame() {
        init();
    }

    //	初始化
    protected void init() {
        this.setTitle("崩崩man");
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


    //	切换画板
    public void changePanel(String name) {
        layout.show(contentPane, name);
    }

    //	游戏启动
    public void startGame() {
        //新建游戏面板
        gameJPanel = new GameJPanel();
        //添加进入frame
        contentPane.add("game", gameJPanel);
        //线程启动
        GameThread gameThread = new GameThread();
        gameThread.start();
        //界面刷新线程启动
        if (gameJPanel != null) {
            new Thread(gameJPanel).start();
        }
    }

    //	绑定监听
    public void addListener() {
        if (keyListener != null)
            this.addKeyListener(keyListener);
    }

    //	移除监听
    public void removeListener() {
        this.removeKeyListener(keyListener);
    }

}
