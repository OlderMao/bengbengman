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
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
        this.img = ElementLoader.getElementLoader().getImageMap().get("beginBackground");
        this.w = Integer.parseInt(data.get(0));
        this.h = Integer.parseInt(data.get(1));
        init();
    }

    private void init() {

        this.setLayout(null);

        JLabel jLabel = new JLabel(img);
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        jLabel.setBounds(0, 0, w, h);

        ImageIcon img2 = new ImageIcon("img/bg/introduce.png");
        final JLabel jLabel2 = new JLabel(img2);
        jLabel2.setBounds(w / 2, h / 6, 600, 800);
        jLabel2.setVisible(false);

        JButton onePlayerButton = new JButton();
        onePlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect1"));
        onePlayerButton.setBounds(w / 6, h / 3, 180, 60);
        onePlayerButton.setBorderPainted(false);
        onePlayerButton.setFocusPainted(false);
        onePlayerButton.setContentAreaFilled(false);
        onePlayerButton.addActionListener(arg0 -> {
            GameController.setTwoPlayer(false);
            GameStart.startNewGame();
        });

        JButton twoPlayerButton = new JButton();
        twoPlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect2"));
        twoPlayerButton.setBounds(w / 6, h / 2, 180, 60);
        twoPlayerButton.setBorderPainted(false);
        twoPlayerButton.setFocusPainted(false);
        twoPlayerButton.setContentAreaFilled(false);
        twoPlayerButton.addActionListener(arg0 -> {
            GameController.setTwoPlayer(true);
            GameStart.startNewGame();
        });

        JButton magicBoxButton = new JButton();
        magicBoxButton.setIcon(new ImageIcon("img/bg/rect3.png"));
        magicBoxButton.setBounds(w / 6, h - h / 3, 180, 60);
        magicBoxButton.setBorderPainted(false);
        magicBoxButton.setFocusPainted(false);
        magicBoxButton.setContentAreaFilled(false);
        magicBoxButton.addActionListener(arg0 -> {
            // TODO 自动生成的方法存根
            jLabel2.setVisible(!jLabel2.isVisible());
        });
        this.add(onePlayerButton);
        this.add(twoPlayerButton);
        this.add(magicBoxButton);
        this.add(jLabel2);
        this.add(jLabel);


        this.setVisible(true);
        this.setOpaque(true);
    }

}
