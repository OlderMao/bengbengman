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

    //���캯��
    public BeginJPanel() {
        //��ȡ���ڴ�С
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");//data=[w,h]
        this.img = ElementLoader.getElementLoader().getImageMap().get("beginBackground");//����ͼƬ
        this.w = Integer.parseInt(data.get(0));//���ڿ��
        this.h = Integer.parseInt(data.get(1));//���ڸ߶�
        init();//��ʼ��
    }

    private void init() {

        this.setLayout(null);//���ò��ֹ�����Ϊ��

        //����ͼƬ
        JLabel jLabel = new JLabel(img);
        img.setImage(img.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));//����ͼƬ��С
        jLabel.setBounds(0, 0, w, h);//����ͼƬλ��

        ImageIcon img2 = new ImageIcon("img/bg/introduce.png");//��Ϸ����ͼƬ
        final JLabel jLabel2 = new JLabel(img2);//��Ϸ���ܱ�ǩ
        jLabel2.setBounds(w / 2, h / 6, 600, 800);//����ͼƬλ��
        jLabel2.setVisible(false);//���ò��ɼ�

        JButton onePlayerButton = new JButton();//������Ϸ��ť
        onePlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect1"));//���ð�ťͼƬ
        onePlayerButton.setBounds(w / 6, h / 3, 180, 60);//���ð�ťλ��
        onePlayerButton.setBorderPainted(false);//���ñ߿򲻿ɼ�
        onePlayerButton.setFocusPainted(false);//���ý��㲻�ɼ�
        onePlayerButton.setContentAreaFilled(false);//�����������򲻿ɼ�
        //������Ϸ��ť������
        onePlayerButton.addActionListener(arg0 -> {
            GameController.setTwoPlayer(false);//����Ϊ������Ϸ
            GameStart.startNewGame();//��ʼ����Ϸ
        });

        //˫����Ϸ��ť
        JButton twoPlayerButton = new JButton();
        twoPlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect2"));//���ð�ťͼƬ
        twoPlayerButton.setBounds(w / 6, h / 2, 180, 60);
        twoPlayerButton.setBorderPainted(false);//���ñ߿򲻿ɼ�
        twoPlayerButton.setFocusPainted(false);
        twoPlayerButton.setContentAreaFilled(false);
        //˫����Ϸ��ť������
        twoPlayerButton.addActionListener(arg0 -> {
            GameController.setTwoPlayer(true);
            GameStart.startNewGame();//��ʼ����Ϸ
        });

        //���ߺа�ť
        JButton magicBoxButton = new JButton();
        magicBoxButton.setIcon(new ImageIcon("img/bg/rect3.png"));
        magicBoxButton.setBounds(w / 6, h - h / 3, 180, 60);
        magicBoxButton.setBorderPainted(false);
        magicBoxButton.setFocusPainted(false);
        magicBoxButton.setContentAreaFilled(false);
        //���ߺа�ť������
        magicBoxButton.addActionListener(arg0 -> {
            jLabel2.setVisible(!jLabel2.isVisible());
        });
        this.add(onePlayerButton);//��Ӱ�ť
        this.add(twoPlayerButton);
        this.add(magicBoxButton);
        this.add(jLabel2);//�����Ϸ����ͼƬ
        this.add(jLabel);//��ӱ���ͼƬ
        this.setVisible(true);//���ÿɼ�
        this.setOpaque(true);//���ò�͸��
    }

}
