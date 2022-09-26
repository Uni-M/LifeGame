package ru.life.constant;

import lombok.Getter;

@Getter
public enum PictureCells {

    DEFAULT("src/main/resources/dotP.png", "Pink"),
    VIOLET("src/main/resources/dotV.png", "Violet"),
    BLACK("src/main/resources/dotB.png", "Black"),
    GREEN("src/main/resources/dotG.png", "Green"),
    BLUE("src/main/resources/dotDB.png", "Blue"),
    RAINBOW("src/main/resources/dotRainbow.png", "Rainbow");

    private final String fileName;
    private final String color;

    PictureCells(String fileName, String color) {
        this.fileName = fileName;
        this.color = color;
    }

}
