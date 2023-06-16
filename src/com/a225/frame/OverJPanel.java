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

    //构造函数
    public OverJPanel() {
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");//data=[w,h]
        this.img = ElementLoader.getElementLoader().getImageMap().get("gameOver");//背景图片
        this.w = Integer.parseInt(data.get(0));//窗口宽度
        this.h = Integer.parseInt(data.get(1));//窗口高度
        init();
    }

    public static JButton getResult() {
        return result;
    }

    private void init() {

        this.setLayout(null);

        JLabel jLabel = new JLabel(img);//背景图片
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));//设置图片大小
        jLabel.setBounds(0, 0, w, h);

        JButton restart = new JButton();
        restart.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect4"));//设置按钮图片
        restart.setBounds(w / 2 - 90, h - h / 4, 180, 60);
        restart.setBorderPainted(false); //设置焦点绘制
        restart.setFocusPainted(false); //设置焦点绘制
        restart.setContentAreaFilled(false);//设置内容区域已填充
        restart.addActionListener(arg0 -> GameStart.changeJPanel("begin"));//监听器

        result.setFont(new Font("Times New Roman", Font.BOLD, 48));
        result.setBounds(w / 2 - 150, h - 3 * (h / 7), 300, 80);
        result.setHorizontalTextPosition(SwingConstants.CENTER);//设置文字位置
        result.setVerticalTextPosition(SwingConstants.CENTER);//设置文字位置
        result.setBorderPainted(false);//设置边框不可见
        result.setContentAreaFilled(false);//设置内容区域不可见
        result.setEnabled(false);//设置不可用
        result.setForeground(new Color(255, 242, 0));

        this.add(result);//添加结果标签
        this.add(restart);//添加重新开始按钮
        this.add(jLabel);//添加背景图片

        this.setVisible(true);//设置可见
        this.setOpaque(true);//设置不透明
    }
}
