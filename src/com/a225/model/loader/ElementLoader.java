package com.a225.model.loader;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * ��Դ������
 * ʹ�õ������ģʽ
 */
public class ElementLoader {
    private static ElementLoader elementLoader;
    private final Properties properties;
    private final Map<String, List<String>> gameInfoMap;//��Ϸ��Ϣ�ֵ�
    private final Map<String, ImageIcon> imageMap;//ͼƬ�ֵ�
    private final Map<String, List<String>> squareTypeMap;//���������ֵ�

    //���캯��
    private ElementLoader() {
        //��ʼ��
        properties = new Properties();//�����ļ�
        gameInfoMap = new HashMap<>();//��Ϸ��Ϣ�ֵ�
        imageMap = new HashMap<>();//ͼƬ�ֵ�
        squareTypeMap = new HashMap<>();//���������ֵ�
    }

    //����ģʽ
    public static ElementLoader getElementLoader() {
        //˫�ؼ������
        if (elementLoader == null) {
            elementLoader = new ElementLoader();
        }
        return elementLoader;
    }

    //��ȡ�������ļ�
    public void readGamePro() throws IOException {
        //��ȡ�����ļ�
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream("com/a225/pro/Game.pro");
        properties.clear();//��������ļ�
        properties.load(inputStream);//���������ļ�
        for (Object o : properties.keySet()) {
            //��ȡ�������ֵ
            String info = properties.getProperty(o.toString());
            gameInfoMap.put(o.toString(), infoStringToList(info));
        }
    }

    //��ȡͼƬ
    public void readImagePro() throws IOException {
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("imageProPath").get(0));//��ȡͼƬ�����ļ�·��
        properties.clear();
        properties.load(inputStream);//���������ļ�
        for (Object o : properties.keySet()) {
            String loc = properties.getProperty(o.toString());//��ȡͼƬ·��
            imageMap.put(o.toString(), new ImageIcon(loc));//����Map��
        }
    }

    //��ȡ��Ϸ�������
    public void readCharactorsPro() throws IOException {
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("charatersPath").get(0));//��ȡ��Ϸ��������ļ�·��
        properties.clear();
        properties.load(inputStream);
        for (Object o : properties.keySet()) {
            String info = properties.getProperty(o.toString());
            gameInfoMap.put(o.toString(), infoStringToList(info));//����Map��value�е����Ѿ��ָ���������
        }
    }

    //��ȡnpcͼƬ�б�
    public List<ImageIcon> getNpcImageList(String s) { //s��ֵΪnpcA,npcB��npcC ��Ӧ��Ӧ��npc
        List<ImageIcon> imageList = new ArrayList<>();
        String npc = "";
        for (int i = 0; i < 4; i++) {//4��ͼƬ
            npc = s + (char) (i + '0');
            imageList.add(imageMap.get(npc));//��ȡͼƬ
        }
        return imageList;
    }


    //��ȡ����ը���ͱ�ըЧ������Bubble.pro
    public void readBubblePro() throws IOException {
        InputStream inputStream =
                ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("bubblePath").get(0));//��ȡ����ը���ͱ�ըЧ�������ļ�·��
        properties.clear();
        properties.load(inputStream);
        for (Object o : properties.keySet()) {
            String info = properties.getProperty(o.toString());//��ȡ�������ֵ
            gameInfoMap.put(o.toString(), infoStringToList(info));//����Map��value�е����Ѿ��ָ���������
        }
    }


    //��ȡ����������Ϣ
    public void readSquarePro() throws IOException {
        InputStream inputStream =
                ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("squareProPath").get(0));//��ȡ�������������ļ�·��
        properties.clear();
        properties.load(inputStream);
        for (Object o : properties.keySet()) {
            String info = properties.getProperty(o.toString());
            squareTypeMap.put(o.toString(), infoStringToList(info));
        }
    }

    //��ȡ�ض���ͼ
    public List<List<String>> readMapPro(String mapPro) throws IOException {
        List<List<String>> mapList = new ArrayList<>();
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get(mapPro).get(0));//��ȡ��ͼ�����ļ�·��
        properties.clear();
        properties.load(inputStream);
        Set<Object> sortSet = new TreeSet<>((o1, o2) -> {
            try {
                int a = Integer.parseInt(o1.toString());
                int b = Integer.parseInt(o2.toString());
                return Integer.compare(b, a);
            } catch (Exception e) {
                return -1;
            }
        });
        sortSet.addAll(properties.keySet());
        for (Object o : sortSet) {
            String info = properties.getProperty(o.toString());
            if (o.toString().equals("size")) {//��ͼ��С
                gameInfoMap.put("mapSize", infoStringToList(info));
            } else {//��ͼ��Ϣ
                mapList.add(infoStringToList(info));
            }
        }
        Collections.reverse(mapList);
        return mapList;
    }


    /**
     * ���������ָ���ַ����и��תΪ�ַ���List
     */
    private List<String> infoStringToList(String info) {
        return Arrays.asList(info.split(","));
    }

    public Map<String, List<String>> getGameInfoMap() {
        return gameInfoMap;
    }

    public Map<String, ImageIcon> getImageMap() {
        return imageMap;
    }

    public Map<String, List<String>> getSquareTypeMap() {
        return squareTypeMap;
    }

}
