package com.a225.frame;


import com.a225.main.GameStart;
import com.a225.model.loader.ElementLoader;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class OverJPanel extends JPanel {
    private static final JButton result = new JButton();
    private final ImageIcon img;
    private final int w;
    private final int h;

    //¹¹Ôìº¯Êý
    public OverJPanel() {
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
        this.img = ElementLoader.getElementLoader().getImageMap().get("gameOver");
        this.w = Integer.parseInt(data.get(0));
        this.h = Integer.parseInt(data.get(1));
        init();
    }

    public static JButton getResult() {
        return result;
    }

    private void init() {

        this.setLayout(null);

        JLabel jLabel = new JLabel(img);
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        jLabel.setBounds(0, 0, w, h);

        JButton restart = new JButton();
        restart.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect4"));
        restart.setBounds(w / 2 - 90, h - h / 4, 180, 60);
        restart.setBorderPainted(false);
        restart.setFocusPainted(false);
        restart.setContentAreaFilled(false);
        restart.addActionListener(arg0 -> GameStart.changeJPanel("begin"));

        result.setFont(new Font("Times New Roman", Font.BOLD, 48));
        result.setBounds(w / 2 - 150, h - 3 * (h / 7), 300, 80);
        result.setHorizontalTextPosition(SwingConstants.CENTER);
        result.setVerticalTextPosition(SwingConstants.CENTER);
        result.setBorderPainted(false);
        result.setContentAreaFilled(false);
        result.setEnabled(false);
        result.setForeground(new Color(255, 242, 0));

        this.add(result);
        this.add(restart);
        this.add(jLabel);

        this.setVisible(true);
        this.setOpaque(true);
    }
}
