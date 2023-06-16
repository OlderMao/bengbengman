package com.a225.frame;

import com.a225.main.GameController;
import com.a225.model.manager.ElementManager;
import com.a225.model.vo.MapSquare;
import com.a225.model.vo.SuperElement;
import com.a225.thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * ��Ϸ���
 * ����������������
 */
public class GameJPanel extends JPanel implements Runnable {

    //	��ʾ�������ݣ��滭
    @Override
    public void paint(Graphics g) {
        super.paint(g);//��ջ���
        gameRuntime(g);//չʾԪ�ع����������е�Ԫ��
    }

    @Override
    //�߳�����
    //ÿ��100����ˢ�»���
    public void run() {
        while (GameController.isGameRunning()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint(); //ÿ��100����ˢ�»���
        }
    }

    //չʾԪ�ع����������е�Ԫ��
    public void gameRuntime(Graphics g) {
        Map<String, List<SuperElement>> map = ElementManager.getManager().getMap();//��ȡԪ�ع������е�Ԫ��
        Set<String> set = map.keySet();//��ȡԪ�ع������е�Ԫ�صļ�ֵ
        Set<String> sortSet = new TreeSet<String>(ElementManager.getManager().getMapKeyComparator());//�Լ�ֵ��������
        sortSet.addAll(set);//�������ļ�ֵ����set��
        for (String key : sortSet) {
            List<SuperElement> list = map.get(key);//��ȡԪ�ع������е�Ԫ�صļ�ֵ��Ӧ��Ԫ��
            for (int i = 0; i < list.size(); i++) {
                list.get(i).showElement(g);//չʾԪ��
            }
        }
        g.setFont(new Font("Times New Roman", Font.BOLD, 24));//��������
        int allTime = GameThread.getAllTime() / 1000;//��ȡʣ��ʱ��
        int munite = allTime / 60;//��ȡʣ�����
        int second = allTime % 60;//��ȡʣ������
        String m;
        String s;
        if (munite < 10)
            m = "0" + munite;
        else
            m = Integer.toString(munite);
        if (second < 10)
            s = "0" + second;
        else
            s = Integer.toString(second);
        g.drawString("Time: " + m + ":" + s, 0, 3 * MapSquare.PIXEL_Y);//��ʾʣ��ʱ��
    }

}
