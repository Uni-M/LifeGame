package ru.life.component.menu.dialog.template;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.life.component.menu.dialog.Oscillator;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

import static ru.life.constant.Size.DOT_SIZE;
import static ru.life.constant.Size.SIZE_HEIGHT;
import static ru.life.constant.Size.SIZE_WIDTH;

@Getter
@Component
public class Octagon extends Oscillator {

    private final String name = "Octagon";

    @Override
    public boolean[][] create() {
        boolean[][] cells = new boolean[SIZE_WIDTH][SIZE_HEIGHT];
        for (int i = 0; i < SIZE_WIDTH / DOT_SIZE; i++) {
            for (int j = 0; j < SIZE_HEIGHT / DOT_SIZE; j++) {
                int x = i * DOT_SIZE;
                int y = j * DOT_SIZE;

                if (x == 6 * DOT_SIZE && y == 5 * DOT_SIZE) cells[x][y] = true;
                if (x == 9 * DOT_SIZE && y == 5 * DOT_SIZE) cells[x][y] = true;

                if (x == 5 * DOT_SIZE && y == 6 * DOT_SIZE) cells[x][y] = true;
                if (x == 7 * DOT_SIZE && y == 6 * DOT_SIZE) cells[x][y] = true;
                if (x == 8 * DOT_SIZE && y == 6 * DOT_SIZE) cells[x][y] = true;
                if (x == 10 * DOT_SIZE && y == 6 * DOT_SIZE) cells[x][y] = true;

                if (x == 6 * DOT_SIZE && y == 7 * DOT_SIZE) cells[x][y] = true;
                if (x == 9 * DOT_SIZE && y == 7 * DOT_SIZE) cells[x][y] = true;

                if (x == 6 * DOT_SIZE && y == 8 * DOT_SIZE) cells[x][y] = true;
                if (x == 9 * DOT_SIZE && y == 8 * DOT_SIZE) cells[x][y] = true;

                if (x == 5 * DOT_SIZE && y == 9 * DOT_SIZE) cells[x][y] = true;
                if (x == 7 * DOT_SIZE && y == 9 * DOT_SIZE) cells[x][y] = true;
                if (x == 8 * DOT_SIZE && y == 9 * DOT_SIZE) cells[x][y] = true;
                if (x == 10 * DOT_SIZE && y == 9 * DOT_SIZE) cells[x][y] = true;

                if (x == 6 * DOT_SIZE && y == 10 * DOT_SIZE) cells[x][y] = true;
                if (x == 9 * DOT_SIZE && y == 10 * DOT_SIZE) cells[x][y] = true;
            }
        }
        return cells;
    }

    @Override
    public JLabel getLabel() throws MalformedURLException {
        URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/1/17/JdlV_osc_5.64.gif");
        return new JLabel(new ImageIcon(url));
    }
}
