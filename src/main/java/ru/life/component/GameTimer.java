package ru.life.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Getter
@Setter
@Component
public class GameTimer {

    private int speed = 500;
    private Timer timer;
    private boolean reStartTimer = false;

    public void init(GameField listener) {
        Timer t = new Timer(speed, listener);
        this.setTimer(t);
        t.start();
    }

    public boolean updateSpeed(int k) {
        if ((speed >= 100 && k < 0) || (speed < 1000 && k > 0)) {
            speed += k;
            return true;
        }
        return false;
    }

}
