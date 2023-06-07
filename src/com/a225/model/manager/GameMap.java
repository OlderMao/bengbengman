package com.a225.model.manager;

import com.a225.main.GameController;
import com.a225.model.loader.ElementLoader;
import com.a225.model.vo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ��ͼ��
 *
 * @ClassName: Map
 * @Description: ��ͼ��
 */
public class GameMap {
    private static int mapRows;
    private static int mapCols;
    private static int biasX;
    private static int biasY;
    private static List<List<String>> mapList;//��ͼ
    private final int windowW;
    private final int windowH;

    //���캯��
    public GameMap(int windowW, int windowH) {
        this.windowW = windowW;
        this.windowH = windowH;
    }

    //��xyת��Ϊij 0��i 1��j
    public static List<Integer> getIJ(int x, int y) {
        List<Integer> list = new ArrayList<>();
        list.add((y - biasY) / MapSquare.PIXEL_Y);
        list.add((x - biasX) / MapSquare.PIXEL_X);
        return list;
    }

    //��ijת��Ϊxy 0��x 1��y
    public static List<Integer> getXY(int i, int j) {
        List<Integer> tempList = new ArrayList<>();
        tempList.add(i * MapSquare.PIXEL_Y + biasY);
        tempList.add(j * MapSquare.PIXEL_X + biasX);
        return tempList;
    }

    public static List<Integer> getXY(List<Integer> list) {
        List<Integer> tempList = new ArrayList<>();
        tempList.add(list.get(1) * MapSquare.PIXEL_X + biasX);
        tempList.add(list.get(0) * MapSquare.PIXEL_Y + biasY);
        return tempList;
    }

    public static List<List<String>> getMapList() {
        return mapList;
    }

    public static int getBiasX() {
        return biasX;
    }

    public static int getBiasY() {
        return biasY;
    }

    public static int getMapRows() {
        return mapRows;
    }

    public static int getMapCols() {
        return mapCols;
    }

    //�����ذ�
    private void createFloor() {
        Map<String, List<String>> typeMap = ElementLoader.getElementLoader().getSquareTypeMap();
        List<SuperElement> floorList = ElementManager.getManager().getElementList("floor");
        String type = null;
        //�ӵ�ͼ�����ļ��еõ�����һ�ֵذ�
        for (int i = 0; i < mapRows; i++) {
            for (int j = 0; j < mapCols; j++) {
                if (mapList.get(i).get(j).equals("11")) {
                    type = "11";
                    break;
                }
                if (mapList.get(i).get(j).equals("12")) {
                    type = "12";
                    break;
                }
            }
        }
        //����ڵذ幹�캯��������һ������
        for (int i = 0; i < mapRows; i++) {
            for (int j = 0; j < mapCols; j++) {
                floorList.add(MapFloor.createMapFloor(typeMap.get(type), i, j));
            }
        }

    }

    //������ͼԪ��
    private void createSquare() {
        Map<String, List<String>> typeMap = ElementLoader.getElementLoader().getSquareTypeMap();
        Map<String, List<SuperElement>> elmenteMap = ElementManager.getManager().getMap();
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        int npcNum = 0;
        for (int i = 0; i < mapRows; i++) {
            for (int j = 0; j < mapCols; j++) {
                String type = mapList.get(i).get(j);
                switch (type.charAt(0)) {
                    case '0' -> {
                        if (type.equals("00")) break;//����ǽ
                        elmenteMap.get("obstacle").add(MapObstacle.createMapObstacle(typeMap.get(type), i, j));
                    }
                    case '2' ->
                            elmenteMap.get("fragility").add(MapFragility.createMapFragility(typeMap.get(type), i, j));
                    case '3' -> elmenteMap.get("magicBox").add(MagicBox.createMagicBox(i, j));
                    case '6' -> initPlayer(i, j, 0);
                    case '7' -> {
                        if (GameController.isTwoPlayer())
                            initPlayer(i, j, 1);
                        else {
                            switch (type.charAt(1)) {
                                case '1' ->
                                        elmenteMap.get("npc").add(Npc.createNpc(gameInfoMap.get("npcA"), i, j, npcNum++));
                                case '2' ->
                                        elmenteMap.get("npc").add(Npc.createNpc(gameInfoMap.get("npcB"), i, j, npcNum++));
                                case '3' ->
                                        elmenteMap.get("npc").add(Npc.createNpc(gameInfoMap.get("npcC"), i, j, npcNum++));
                                default -> {
                                }
                            }
                        }
                    }
                    case '8' -> {
                        switch (type.charAt(1)) {
                            case '1' ->
                                    elmenteMap.get("npc").add(Npc.createNpc(gameInfoMap.get("npcA"), i, j, npcNum++));
                            case '2' ->
                                    elmenteMap.get("npc").add(Npc.createNpc(gameInfoMap.get("npcB"), i, j, npcNum++));
                            case '3' ->
                                    elmenteMap.get("npc").add(Npc.createNpc(gameInfoMap.get("npcC"), i, j, npcNum++));
                            default -> {
                            }
                        }
                    }
                    default -> {
                    }
                }
            }
        }
        GameController.setNpcNum(npcNum);
    }

    public void createMap(String pro) {
        try {
            mapList = ElementLoader.getElementLoader().readMapPro(pro);
            List<String> size = ElementLoader.getElementLoader().getGameInfoMap().get("mapSize");
            mapRows = Integer.parseInt(size.get(0));
            mapCols = Integer.parseInt(size.get(1));
            biasX = (windowW - MapSquare.PIXEL_X * mapCols) / 2;
            biasY = (windowH - MapSquare.PIXEL_Y * mapRows) / 2;
            createFloor();
            createSquare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ͼ���ؽ�ɫ
     *
     * @param i
     * @param j
     * @param num ��ţ����1��0�����2��1
     */
    private void initPlayer(int i, int j, int num) {
        List<SuperElement> playerList = ElementManager.getManager().getMap().get("player");
        if (playerList.size() == (GameController.isTwoPlayer() ? 2 : 1)) {
            List<Integer> locList = GameMap.getXY(i, j);
            playerList.get(num).setX(locList.get(0));
            playerList.get(num).setY(locList.get(1));
        } else {
            Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
            for (SuperElement se : playerList) {
                Player player = (Player) se;
                if (player.getPlayerNum() == num) {
                    return;
                }
            }
            Player player = null;
            if (num == 0) {
                player = Player.createPlayer(gameInfoMap.get("playerOne"), i, j, num);
            } else if (num == 1) {
                player = Player.createPlayer(gameInfoMap.get("playerTwo"), i, j, num);
            } else {
                return;
            }
            playerList.add(num, player);
        }
    }

    /**
     * ��ȡ��ͼij��ķ�������
     *
     * @param i
     * @param j
     * @return ��������
     */
    public SquareType getBlockSquareType(int i, int j) {
        String str = mapList.get(i).get(j);
        return SquareType.valueOf(str.charAt(0));
    }

    /**
     * ��ȡ��ͼij��ķ�������
     *
     * @param list ij�б�
     * @return ��������
     */
    public SquareType getBlockSquareType(List<Integer> list) {
        String str = mapList.get(list.get(0)).get(list.get(1));
        return SquareType.valueOf(str.charAt(0));
    }

    /**
     * ���õ�ͼij�㷽������
     *
     * @param list ij�б�
     * @param type
     */
    public void setBlockSquareType(List<Integer> list, SquareType type) {
        mapList.get(list.get(0)).set(list.get(1), String.valueOf(type.value));
    }

    /**
     * ���õ�ͼij�㷽������
     *
     * @param i
     * @param j
     * @param type
     */
    public void setBlockSquareType(int i, int j, SquareType type) {
        mapList.get(i).set(j, String.valueOf(type.value));
    }

    /**
     * �жϷ����Ƿ����ϰ���
     *
     * @param i
     * @param j
     * @return �Ƿ����ϰ���
     */
    public boolean blockIsObstacle(int i, int j) {
        if (outOfBoundary(i, j)) return true;

        String type = mapList.get(i).get(j);
		return type.charAt(0) == SquareType.OBSTACLE.value;
    }

    /**
     * ��ȡijλ���Ƿ��ͨ��
     *
     * @param list
     * @return ��ͨ��
     */
    public boolean blockIsWalkable(List<Integer> list) {
        String type = mapList.get(list.get(0)).get(list.get(1));
		return type.charAt(0) != SquareType.OBSTACLE.value
				&& type.charAt(0) != SquareType.FRAGILITY.value
				&& type.charAt(0) != SquareType.ITEM.value;
    }

    /**
     * ��ȡijλ���Ƿ��ͨ��
     *
     * @param list
     * @return ��ͨ��
     */
    public boolean blockIsWalkable(int i, int j) {
        String type = mapList.get(i).get(j);
		return type.charAt(0) != SquareType.OBSTACLE.value
				&& type.charAt(0) != SquareType.FRAGILITY.value
				&& type.charAt(0) != SquareType.ITEM.value;
    }

    /**
     * �ж��Ƿ񳬳��߽�
     *
     * @param i
     * @param j
     * @return �Ƿ񳬳��߽�
     */
    public boolean outOfBoundary(int i, int j) {
		return i < 0 || i >= mapRows || j < 0 || j >= mapCols;
    }

    /**
     * ��յ�ͼ�г��������Ķ���
     */
    public void clearMapOther() {
        ElementManager.getManager().getElementList("obstacle").clear();
        ElementManager.getManager().getElementList("fragility").clear();
        ElementManager.getManager().getElementList("floor").clear();
        ElementManager.getManager().getElementList("explode").clear();
        ElementManager.getManager().getElementList("magicBox").clear();
        ElementManager.getManager().getElementList("npc").clear();
        ElementManager.getManager().getElementList("bubble").clear();
    }

    /**
     * ��յ�ͼ���ж���
     */
    public void clearMapALL() {
        ElementManager.getManager().getElementList("player").clear();
        clearMapOther();
    }

    //�Զ��巽�����Ͷ�Ӧö����
    public enum SquareType {
        OBSTACLE('0'), FLOOR('1'), FRAGILITY('2'), ITEM('3'), PLAYER_1('6'), PLAYER_2('7'), NPC('8'), BUBBLE('9');

        private char value = 0;

        SquareType(char value) {
            this.value = value;
        }

        public static SquareType valueOf(char c) {    //��д�Ĵ�int��enum��ת������
            return switch (c) {
                case '0' -> OBSTACLE;    //�ϰ���
                case '1' -> FLOOR;    //�ذ�
                case '2' -> FRAGILITY;//���ƻ���
                case '3' -> ITEM;    //����
                case '6' -> PLAYER_1;    //���1
                case '7' -> PLAYER_2;    //���2
                case '8' -> NPC;        //NPC
                case '9' -> BUBBLE;    //ը��
                default -> null;
            };
        }

        public char value() {
            return this.value;
        }
    }


}
