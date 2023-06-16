
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
        this.setIconImage(new ImageIcon("img/bg/icon.png").getImage());
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
        this.setSize(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)));//设置窗口大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭方式
        this.setResizable(false);//设置不可改变大小
        this.setLocationRelativeTo(null);//设置居中

        keyListener = new GameKeyListener();//游戏按键监听器

        this.contentPane = new JPanel();
        this.setContentPane(contentPane);//设置主面板

        this.layout = new CardLayout();
        this.contentPane.setLayout(layout);//设置布局管理器

        this.beginJPanel = new BeginJPanel();//开始画板
        this.contentPane.add("begin", beginJPanel);//添加开始画板

        this.overJPanel = new OverJPanel();//结束画板
        this.contentPane.add("over", overJPanel);//添加结束画板

        this.layout.show(contentPane, "begin");//显示开始画板
        this.setVisible(true);//设置可见
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
