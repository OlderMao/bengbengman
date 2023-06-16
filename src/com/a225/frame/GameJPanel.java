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
 * 游戏面板
 * 窗体容器：画板类
 */
public class GameJPanel extends JPanel implements Runnable {

    //	显示画板内容，绘画
    @Override
    public void paint(Graphics g) {
        super.paint(g);//清空画板
        gameRuntime(g);//展示元素管理器中所有的元素
    }

    @Override
    //线程运行
    //每隔100毫秒刷新画板
    public void run() {
        while (GameController.isGameRunning()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint(); //每隔100毫秒刷新画板
        }
    }

    //展示元素管理器中所有的元素
    public void gameRuntime(Graphics g) {
        Map<String, List<SuperElement>> map = ElementManager.getManager().getMap();//获取元素管理器中的元素
        Set<String> set = map.keySet();//获取元素管理器中的元素的键值
        Set<String> sortSet = new TreeSet<String>(ElementManager.getManager().getMapKeyComparator());//对键值进行排序
        sortSet.addAll(set);//将排序后的键值放入set中
        for (String key : sortSet) {
            List<SuperElement> list = map.get(key);//获取元素管理器中的元素的键值对应的元素
            for (int i = 0; i < list.size(); i++) {
                list.get(i).showElement(g);//展示元素
            }
        }
        g.setFont(new Font("Times New Roman", Font.BOLD, 24));//设置字体
        int allTime = GameThread.getAllTime() / 1000;//获取剩余时间
        int munite = allTime / 60;//获取剩余分钟
        int second = allTime % 60;//获取剩余秒数
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
        g.drawString("Time: " + m + ":" + s, 0, 3 * MapSquare.PIXEL_Y);//显示剩余时间
    }

}
