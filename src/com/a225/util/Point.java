package com.a225.util;

import com.a225.model.manager.MoveTypeEnum;

import java.util.Vector;

public class Point {
    public int i;
    public int j;
    public Vector<MoveTypeEnum> path;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
        path = new Vector<>();
    }
}
