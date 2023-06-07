package com.a225.thread;

import com.a225.main.GameController;
import com.a225.model.manager.ElementManager;
import com.a225.model.manager.MoveTypeEnum;
import com.a225.model.vo.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Stack;

/**
 * ���̼�����
 * ʵ�ּ��̼������Կ���Player�������ƶ�
 */

public class GameKeyListener implements KeyListener {
    private final Stack<Integer> p1PressStack = new Stack<>();
    private final Stack<Integer> p2PressStack = new Stack<>();
    /**
     * ��ջ�����������ͻ
     * ÿ��ջ������Ų�ͬ�û��İ�����ͨ���жϰ�����code�������ƶ�������߹���
     */

    private List<?> list;

    @Override
    public void keyPressed(KeyEvent e) {
        list = ElementManager.getManager().getElementList("player");
        Player player1 = (Player) list.get(0);

        int code = e.getKeyCode();
        switch (code) {
            case 10 -> {//ը����
                if (player1.isKeepAttack())//������һֱ����ը������ÿ��ֻ�ܷ�һ��ը��
                    player1.setAttack(false);
                else {
                    player1.setKeepAttack(true);
                    player1.setAttack(true);
                }
            }//��������
            case 37, 38, 39, 40 -> {
                if (!p1PressStack.contains(code)) {
                    p1PressStack.push(code);
                }
                player1.setMoveType(MoveTypeEnum.codeToMoveType(code));
            }
            default -> {
            }//������������
        }
        if (GameController.isTwoPlayer()) {
            Player player2 = (Player) list.get(1);
            switch (code) {
                case 32 -> {
                    if (player2.isKeepAttack())
                        player2.setAttack(false);
                    else {
                        player2.setKeepAttack(true);
                        player2.setAttack(true);
                    }
                }
                case 65, 87, 68, 83 -> {
                    if (!p2PressStack.contains(code)) {
                        p2PressStack.push(code);
                    }
                    player2.setMoveType(MoveTypeEnum.codeToMoveType(code));
                }
                default -> {
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        List<?> list = ElementManager.getManager().getElementList("player");
        int code = e.getKeyCode();
        Player player1 = (Player) list.get(0);
        if (!player1.isDead()) {
            switch (code) {
                case 10 -> {
                    player1.setAttack(false);
                    player1.setKeepAttack(false);
                }
                case 37, 38, 39, 40 -> {
                    if (p1PressStack.peek() != code) {
                        p1PressStack.remove(Integer.valueOf(code));
                    } else {
                        p1PressStack.pop();
                        if (p1PressStack.size() == 0) {
                            player1.setMoveType(MoveTypeEnum.STOP);
                        } else {
                            player1.setMoveType(MoveTypeEnum.codeToMoveType(p1PressStack.peek()));
                        }
                    }
                }
                default -> {
                }
            }
        }
        if (GameController.isTwoPlayer()) {
            Player player2 = (Player) list.get(1);
            if (!player2.isDead()) {
                switch (code) {
                    case 32 -> {
                        player2.setAttack(false);
                        player2.setKeepAttack(false);
                    }
                    case 65, 87, 68, 83 -> {
                        if (p2PressStack.peek() != code) {
                            p2PressStack.remove(Integer.valueOf(code));
                        } else {
                            p2PressStack.pop();
                            if (p2PressStack.size() == 0) {
                                player2.setMoveType(MoveTypeEnum.STOP);
                            } else {
                                player2.setMoveType(MoveTypeEnum.codeToMoveType(p2PressStack.peek()));
                            }
                        }
                    }
                    default -> {
                    }
                }
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO �Զ����ɵķ������

    }

}
