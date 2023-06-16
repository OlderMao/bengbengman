package com.a225.frame;

import com.a225.main.GameController;
import com.a225.main.GameStart;
import com.a225.model.loader.ElementLoader;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BeginJPanel extends JPanel {
    private final ImageIcon img;
    private final int w;
    private final int h;

    //构造函数
    public BeginJPanel() {
        //读取窗口大小
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");//data=[w,h]
        this.img = ElementLoader.getElementLoader().getImageMap().get("beginBackground");//背景图片
        this.w = Integer.parseInt(data.get(0));//窗口宽度
        this.h = Integer.parseInt(data.get(1));//窗口高度
        init();//初始化
    }

    private void init() {

        this.setLayout(null);//设置布局管理器为空

        //背景图片
        JLabel jLabel = new JLabel(img);
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));//设置图片大小
        jLabel.setBounds(0, 0, w, h);//设置图片位置

        ImageIcon img2 = new ImageIcon("img/bg/introduce.png");//游戏介绍图片
        final JLabel jLabel2 = new JLabel(img2);//游戏介绍标签
        jLabel2.setBounds(w / 2, h / 6, 600, 800);//设置图片位置
        jLabel2.setVisible(false);//设置不可见

        JButton onePlayerButton = new JButton();//单人游戏按钮
        onePlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect1"));//设置按钮图片
        onePlayerButton.setBounds(w / 6, h / 3, 180, 60);//设置按钮位置
        onePlayerButton.setBorderPainted(false);//设置边框不可见
        onePlayerButton.setFocusPainted(false);//设置焦点不可见
        onePlayerButton.setContentAreaFilled(false);//设置内容区域不可见
        //单人游戏按钮监听器
        onePlayerButton.addActionListener(arg0 -> {
            GameController.setTwoPlayer(false);//设置为单人游戏
            GameStart.startNewGame();//开始新游戏
        });

        //双人游戏按钮
        JButton twoPlayerButton = new JButton();
        twoPlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect2"));//设置按钮图片
        twoPlayerButton.setBounds(w / 6, h / 2, 180, 60);
        twoPlayerButton.setBorderPainted(false);//设置边框不可见
        twoPlayerButton.setFocusPainted(false);
        twoPlayerButton.setContentAreaFilled(false);
        //双人游戏按钮监听器
        twoPlayerButton.addActionListener(arg0 -> {
            GameController.setTwoPlayer(true);
            GameStart.startNewGame();//开始新游戏
        });

        //道具盒按钮
        JButton magicBoxButton = new JButton();
        magicBoxButton.setIcon(new ImageIcon("img/bg/rect3.png"));
        magicBoxButton.setBounds(w / 6, h - h / 3, 180, 60);
        magicBoxButton.setBorderPainted(false);
        magicBoxButton.setFocusPainted(false);
        magicBoxButton.setContentAreaFilled(false);
        //道具盒按钮监听器
        magicBoxButton.addActionListener(arg0 -> {
            jLabel2.setVisible(!jLabel2.isVisible());
        });
        this.add(onePlayerButton);//添加按钮
        this.add(twoPlayerButton);
        this.add(magicBoxButton);
        this.add(jLabel2);//添加游戏介绍图片
        this.add(jLabel);//添加背景图片
        this.setVisible(true);//设置可见
        this.setOpaque(true);//设置不透明
    }

}
