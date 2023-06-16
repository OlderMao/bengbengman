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

    //���캯��
    public OverJPanel() {
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");//data=[w,h]
        this.img = ElementLoader.getElementLoader().getImageMap().get("gameOver");//����ͼƬ
        this.w = Integer.parseInt(data.get(0));//���ڿ��
        this.h = Integer.parseInt(data.get(1));//���ڸ߶�
        init();
    }

    public static JButton getResult() {
        return result;
    }

    private void init() {

        this.setLayout(null);

        JLabel jLabel = new JLabel(img);//����ͼƬ
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));//����ͼƬ��С
        jLabel.setBounds(0, 0, w, h);

        JButton restart = new JButton();
        restart.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect4"));//���ð�ťͼƬ
        restart.setBounds(w / 2 - 90, h - h / 4, 180, 60);
        restart.setBorderPainted(false); //���ý������
        restart.setFocusPainted(false); //���ý������
        restart.setContentAreaFilled(false);//�����������������
        restart.addActionListener(arg0 -> GameStart.changeJPanel("begin"));//������

        result.setFont(new Font("Times New Roman", Font.BOLD, 48));
        result.setBounds(w / 2 - 150, h - 3 * (h / 7), 300, 80);
        result.setHorizontalTextPosition(SwingConstants.CENTER);//��������λ��
        result.setVerticalTextPosition(SwingConstants.CENTER);//��������λ��
        result.setBorderPainted(false);//���ñ߿򲻿ɼ�
        result.setContentAreaFilled(false);//�����������򲻿ɼ�
        result.setEnabled(false);//���ò�����
        result.setForeground(new Color(255, 242, 0));

        this.add(result);//��ӽ����ǩ
        this.add(restart);//������¿�ʼ��ť
        this.add(jLabel);//��ӱ���ͼƬ

        this.setVisible(true);//���ÿɼ�
        this.setOpaque(true);//���ò�͸��
    }
}
