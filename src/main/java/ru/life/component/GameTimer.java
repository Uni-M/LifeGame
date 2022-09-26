package ru.life.component;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class GameTimer {

    private int speed = 500;
    private Timer timer;
    private boolean reStartTimer = false;

}
