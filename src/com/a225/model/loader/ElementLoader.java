package com.a225.model.loader;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 资源加载器
 * 使用单例设计模式
 */
public class ElementLoader {
    private static ElementLoader elementLoader;
    private final Properties properties;
    private final Map<String, List<String>> gameInfoMap;//游戏信息字典
    private final Map<String, ImageIcon> imageMap;//图片字典
    private final Map<String, List<String>> squareTypeMap;//方块类型字典

    //构造函数
    private ElementLoader() {
        //初始化
        properties = new Properties();//配置文件
        gameInfoMap = new HashMap<>();//游戏信息字典
        imageMap = new HashMap<>();//图片字典
        squareTypeMap = new HashMap<>();//方块类型字典
    }

    //单例模式
    public static ElementLoader getElementLoader() {
        //双重检查锁定
        if (elementLoader == null) {
            elementLoader = new ElementLoader();
        }
        return elementLoader;
    }

    //读取主配置文件
    public void readGamePro() throws IOException {
        //读取配置文件
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream("com/a225/pro/Game.pro");
        properties.clear();//清空配置文件
        properties.load(inputStream);//加载配置文件
        for (Object o : properties.keySet()) {
            //获取配置项的值
            String info = properties.getProperty(o.toString());
            gameInfoMap.put(o.toString(), infoStringToList(info));
        }
    }

    //读取图片
    public void readImagePro() throws IOException {
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("imageProPath").get(0));//获取图片配置文件路径
        properties.clear();
        properties.load(inputStream);//加载配置文件
        for (Object o : properties.keySet()) {
            String loc = properties.getProperty(o.toString());//获取图片路径
            imageMap.put(o.toString(), new ImageIcon(loc));//放入Map中
        }
    }

    //读取游戏玩家配置
    public void readCharactorsPro() throws IOException {
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("charatersPath").get(0));//获取游戏玩家配置文件路径
        properties.clear();
        properties.load(inputStream);
        for (Object o : properties.keySet()) {
            String info = properties.getProperty(o.toString());
            gameInfoMap.put(o.toString(), infoStringToList(info));//放入Map的value中的是已经分割后的配置项
        }
    }

    //获取npc图片列表
    public List<ImageIcon> getNpcImageList(String s) { //s的值为npcA,npcB或npcC 对应相应的npc
        List<ImageIcon> imageList = new ArrayList<>();
        String npc = "";
        for (int i = 0; i < 4; i++) {//4张图片
            npc = s + (char) (i + '0');
            imageList.add(imageMap.get(npc));//获取图片
        }
        return imageList;
    }


    //读取气泡炸弹和爆炸效果配置Bubble.pro
    public void readBubblePro() throws IOException {
        InputStream inputStream =
                ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("bubblePath").get(0));//获取气泡炸弹和爆炸效果配置文件路径
        properties.clear();
        properties.load(inputStream);
        for (Object o : properties.keySet()) {
            String info = properties.getProperty(o.toString());//获取配置项的值
            gameInfoMap.put(o.toString(), infoStringToList(info));//放入Map的value中的是已经分割后的配置项
        }
    }


    //读取方块类型信息
    public void readSquarePro() throws IOException {
        InputStream inputStream =
                ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("squareProPath").get(0));//获取方块类型配置文件路径
        properties.clear();
        properties.load(inputStream);
        for (Object o : properties.keySet()) {
            String info = properties.getProperty(o.toString());
            squareTypeMap.put(o.toString(), infoStringToList(info));
        }
    }

    //读取特定地图
    public List<List<String>> readMapPro(String mapPro) throws IOException {
        List<List<String>> mapList = new ArrayList<>();
        InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get(mapPro).get(0));//获取地图配置文件路径
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
            if (o.toString().equals("size")) {//地图大小
                gameInfoMap.put("mapSize", infoStringToList(info));
            } else {//地图信息
                mapList.add(infoStringToList(info));
            }
        }
        Collections.reverse(mapList);
        return mapList;
    }


    /**
     * 将配置项按照指定字符串切割后转为字符串List
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
