package com.example.controller;

public enum Direction {
    FRONT("f", R.drawable.ic_top),
    RIGHT("r", R.drawable.ic_right),
    LEFT("l", R.drawable.ic_left),
    BACK("b", R.drawable.ic_bottom);

    private String query;
    private int drawableID;

    Direction(String query, int drawableID) {
        this.query = query;
        this.drawableID = drawableID;
    }

    public String getQuery() {
        return query;
    }

    public int getDrawableID() {
        return drawableID;
    }
}
