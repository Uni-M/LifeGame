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

    public boolean updateSpeed(int k) {
        if ((speed >= 100 && k < 0) || (speed < 1000 && k > 0)) {
            speed += k;
            return true;
        }
        return false;
    }

}
