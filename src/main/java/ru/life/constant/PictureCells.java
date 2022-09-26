package ru.life.constant;

import lombok.Getter;

@Getter
public enum PictureCells {

    DEFAULT("src/main/resources/dotP.png", "Pink"),
    VIOLET("src/main/resources/dotV.png", "Violet");

    private final String fileName;
    private final String color;

    PictureCells(String fileName, String color) {
        this.fileName = fileName;
        this.color = color;
    }

}
