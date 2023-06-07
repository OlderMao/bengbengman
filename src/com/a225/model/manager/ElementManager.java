package com.a225.model.manager;

import com.a225.model.loader.ElementLoader;
import com.a225.model.vo.SuperElement;
import com.a225.util.Utils;

import java.util.*;

/**
 * Ԫ�ع�����
 * ����ģʽ
 */
public class ElementManager {
    //Ԫ�ع���������
    private static final ElementManager elementManager;

    static {
        elementManager = new ElementManager();
    }

    //Ԫ�ص�Map����
    private Map<String, List<SuperElement>> map;

    //ͼ��˳��map
    private Map<String, Integer> priorityMap;

    //��Ϸ��ͼ
    private GameMap gameMap;


    //���캯��
    private ElementManager() {
        init();//��ʼ������
        initMap();//��ʼ��Ԫ���б��ֵ�
        initPriorityMap();//��ʼ��ͼ�����ȼ��ֵ�
    }

    //Ԫ�ع��������
    public static ElementManager getManager() {
        return elementManager;
    }

    //��ʼ������
    protected void init() {
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        List<String> windowSize = gameInfoMap.get("windowSize");
        gameMap = new GameMap(Integer.parseInt(windowSize.get(0)), Integer.parseInt(windowSize.get(1)));
        map = new HashMap<>();
        priorityMap = new HashMap<>();
    }

    //��ʼ��Ԫ���б��ֵ�
    private void initMap() {
        map.put("player", new ArrayList<SuperElement>());//���
        map.put("bubble", new ArrayList<SuperElement>());//ˮ��
        map.put("explode", new ArrayList<SuperElement>());//ˮ�ݱ�ը
        map.put("fragility", new ArrayList<SuperElement>());//���ƻ�����
        map.put("floor", new ArrayList<SuperElement>());//�ذ�
        map.put("obstacle", new ArrayList<SuperElement>());//�����ƻ�����
        map.put("magicBox", new ArrayList<SuperElement>());//����
        map.put("npc", new ArrayList<SuperElement>());
    }

    //��ʼ��ͼ�����ȼ��ֵ�
    private void initPriorityMap() {
        priorityMap.put("player", 50);
        priorityMap.put("npc", 45);
        priorityMap.put("bubble", 10);
        priorityMap.put("obstacle", 40);
        priorityMap.put("explode", 30);
        priorityMap.put("magicBox", 25);
        priorityMap.put("fragility", 20);
        priorityMap.put("bubble", 10);
        priorityMap.put("floor", -10);
    }

    //ͼ�����ȼ��Ƚ���
    public Comparator<String> getMapKeyComparator() {
        return (o1, o2) -> {
            int p1 = priorityMap.get(o1);
            int p2 = priorityMap.get(o2);
            return Integer.compare(p1, p2);
        };
    }

    //���map����
    public Map<String, List<SuperElement>> getMap() {
        return map;
    }

    //�õ�Ԫ��list
    public List<SuperElement> getElementList(String key) {
        return map.get(key);
    }

    //��ȡ��Ϸ��ͼ��
    public GameMap getGameMap() {
        return gameMap;
    }

    public void loadMap() {
        int mapNum = Integer.parseInt(ElementLoader.getElementLoader().getGameInfoMap().get("mapNum").get(0));
        gameMap.createMap("stage" + (Utils.random.nextInt(mapNum) + 1) + "Map");
    }

    public void overGame(Boolean over) {
        if (over) {
            gameMap.clearMapALL();
            //ʧ�ܶ���
        } else {
            gameMap.clearMapOther();
            //��ϲ����
        }
    }

}

