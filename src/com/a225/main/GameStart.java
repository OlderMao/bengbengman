package com.a225.main;

import com.a225.frame.GameFrame;
import com.a225.model.loader.ElementLoader;
import com.a225.thread.GameMusicPlayer;

import java.io.IOException;
import java.util.Objects;

/**
 * 游戏启动入口
 */
public class GameStart {
    private static GameFrame gameFrame;

    //游戏启动入口
    public static void main(String[] args) {
        // 资源加载
        try {
            ElementLoader.getElementLoader().readGamePro();//读取游戏配置文件
            ElementLoader.getElementLoader().readImagePro();//读取图片配置文件
            ElementLoader.getElementLoader().readCharactorsPro();//读取人物配置文件
            ElementLoader.getElementLoader().readBubblePro();//读取泡泡配置文件
            ElementLoader.getElementLoader().readSquarePro();//读取方块配置文件
        } catch (IOException e) {
            System.out.println("资源加载失败");
            e.printStackTrace();
        }
        //初始化
        gameFrame = new GameFrame();
        //界面显示
        gameFrame.setVisible(true);
        //音乐播放
        GameMusicPlayer musicPlayer = new GameMusicPlayer();
        musicPlayer.start();
    }

    /**
     * 界面切换
     */
    public static void changeJPanel(String panelName) {
        if (Objects.equals(panelName, "game")) {
            GameController.setGameRunning(true);//游戏开始
            gameFrame.addListener();//添加监听
        } else {
            GameController.setGameRunning(false);//游戏结束
            gameFrame.removeListener();//移除监听
        }
        gameFrame.changePanel(panelName);
        //强制刷新，否则监听无效
        gameFrame.setVisible(false);
        gameFrame.setVisible(true);
    }

    public static void startNewGame() {
        //初始化
        GameController.setGameRunning(true);//游戏开始
        gameFrame.startGame();//游戏界面初始化
        changeJPanel("game");//切换到游戏界面
    }

}
