package com.a225.model.manager;

public enum MoveTypeEnum {
    LEFT, TOP, RIGHT, DOWN, STOP;

    public static MoveTypeEnum codeToMoveType(int code) {
        return switch (code) {
            case 37, 65 -> MoveTypeEnum.LEFT;
            case 38, 87 -> MoveTypeEnum.TOP;
            case 39, 68 -> MoveTypeEnum.RIGHT;
            case 40, 83 -> MoveTypeEnum.DOWN;
            default -> MoveTypeEnum.STOP;
        };
    }
}
