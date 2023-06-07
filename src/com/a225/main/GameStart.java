package com.a225.main;

import com.a225.frame.GameFrame;
import com.a225.model.loader.ElementLoader;
import com.a225.thread.GameMusicPlayer;

import java.io.IOException;
import java.util.Objects;

/**
 * ��Ϸ�������
 */
public class GameStart {
    private static GameFrame gameFrame;

    //��Ϸ�������
    public static void main(String[] args) {
        // ��Դ����
        try {
            ElementLoader.getElementLoader().readGamePro();
            ElementLoader.getElementLoader().readImagePro();
            ElementLoader.getElementLoader().readCharactorsPro();
            ElementLoader.getElementLoader().readBubblePro();
            ElementLoader.getElementLoader().readSquarePro();
        } catch (IOException e) {
            System.out.println("��Դ����ʧ��");
            e.printStackTrace();
        }
        //��ʼ��
        gameFrame = new GameFrame();
        //������ʾ
        gameFrame.setVisible(true);
        //���ֲ���
        GameMusicPlayer musicPlayer = new GameMusicPlayer();
        musicPlayer.start();
    }

    /**
     * �����л�
     */
    public static void changeJPanel(String panelName) {
        if (Objects.equals(panelName, "game")) {
            GameController.setGameRunning(true);
            gameFrame.addListener();
        } else {
            GameController.setGameRunning(false);
            gameFrame.removeListener();
        }
        gameFrame.changePanel(panelName);

        //ǿ��ˢ�£����������Ч
        gameFrame.setVisible(false);
        gameFrame.setVisible(true);
    }

    public static void startNewGame() {
        GameController.setGameRunning(true);
        gameFrame.startGame();
        changeJPanel("game");
    }

}
