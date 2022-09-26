package ru.life.constant;

import lombok.Getter;

@Getter
public enum MenuOptions {
    FILE("File"),
    EDIT("Edit"),
    VIEW("View"),
    HELP("Help");

    private final String option;

    MenuOptions(String option) {
        this.option = option;
    }

}
