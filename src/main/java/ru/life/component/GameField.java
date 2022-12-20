package ru.life.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.life.constant.GameState;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ru.life.constant.Size.DOT_SIZE;
import static ru.life.constant.Size.SIZE_HEIGHT;
import static ru.life.constant.Size.SIZE_WIDTH;

@Getter
@Setter
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GameField extends JPanel implements ActionListener {

    private boolean[][] cells = new boolean[SIZE_WIDTH][SIZE_HEIGHT];
    private boolean[][] cellsTemp;

    private final GameTimer timer;
    private GameState state;

    private int prevSize = DOT_SIZE;
    private double k = 1;
    private Color col;

    @PostConstruct
    private void initGame() {

        state = GameState.PAUSE;
        timer.init(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                click(e);
            }
        });
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (timer.isReStartTimer()) {
            timer.getTimer().stop();
            Timer t = new Timer(timer.getSpeed(), this);
            timer.setTimer(t);

            t.start();
            timer.setReStartTimer(false);
        }
        g2d.setPaint(col);
        for (int i = 0; i < SIZE_WIDTH / DOT_SIZE; i++) {
            for (int j = 0; j < SIZE_HEIGHT / DOT_SIZE; j++) {
                int x = i * DOT_SIZE;
                int y = j * DOT_SIZE;

                if (cells[x][y]) {
                    g2d.drawRect(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
                    g2d.setColor(col);
                    g2d.fillRect(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
                }

                g2d.setColor(Color.BLACK);
                g2d.drawRect(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
            }
        }

    }

    private void life() {
        cellsTemp = new boolean[SIZE_WIDTH][SIZE_HEIGHT];

        for (int i = 0; i < (SIZE_WIDTH / DOT_SIZE); i++) {
            for (int j = 0; j < (SIZE_HEIGHT / DOT_SIZE); j++) {
                int x = i * DOT_SIZE;
                int y = j * DOT_SIZE;
                checkNeighbours(cells[x][y], x, y);
            }
        }

        clone(cells, cellsTemp);
    }

    private void clone(boolean[][] to, boolean[][] from) {

        for (int i = 0; i < (SIZE_WIDTH / DOT_SIZE); i++) {
            for (int j = 0; j < (SIZE_HEIGHT / DOT_SIZE); j++) {
                int x = i * DOT_SIZE;
                int y = j * DOT_SIZE;
                to[x][y] = from[x][y];
            }
        }
    }

    // Проверка на наличие живых соседей
    private void checkNeighbours(boolean isAlive, int x, int y) {

        int countNeighbours = 0;
        int xleft, xright, yup, ydown;

        if (x == 0) {
            xleft = SIZE_WIDTH - DOT_SIZE;
            xright = x + DOT_SIZE;
        } else if (x == SIZE_WIDTH - DOT_SIZE) {
            xright = 0;
            xleft = x - DOT_SIZE;
        } else {
            xleft = x - DOT_SIZE;
            xright = x + DOT_SIZE;
        }

        if (y == 0) {
            ydown = y + DOT_SIZE;
            yup = SIZE_HEIGHT - DOT_SIZE;
        } else if (y == SIZE_HEIGHT - DOT_SIZE) {
            yup = y - DOT_SIZE;
            ydown = 0;
        } else {
            yup = y - DOT_SIZE;
            ydown = y + DOT_SIZE;
        }

        if (cells[xleft][ydown]) countNeighbours++;
        if (cells[x][ydown]) countNeighbours++;
        if (cells[xright][ydown]) countNeighbours++;
        if (cells[xright][y]) countNeighbours++;
        if (cells[xright][yup]) countNeighbours++;
        if (cells[x][yup]) countNeighbours++;
        if (cells[xleft][yup]) countNeighbours++;
        if (cells[xleft][y]) countNeighbours++;

        if (!isAlive && countNeighbours == 3) {
            cellsTemp[x][y] = true;
        } else if (isAlive && (countNeighbours > 3 || countNeighbours < 2)) {
            cellsTemp[x][y] = false;
        } else if (isAlive && (countNeighbours == 3 || countNeighbours == 2)) {
            cellsTemp[x][y] = true;
        } else {
            cellsTemp[x][y] = false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) { //события которые происходят когда тикает таймер

        switch (state) {
            case RESIZE -> {
                resize();
                state = state.nextState();
            }
            case STEP -> {
                life();
                state = state.nextState();
            }
            case CLEAN -> {
                clone(cells, new boolean[SIZE_WIDTH][SIZE_HEIGHT]);
                state = state.nextState();
            }
            case LIFE -> life();
        }

        repaint(); //перерисовывает карту для обновления при движении
    }

    private void resize() {

        cellsTemp = new boolean[SIZE_WIDTH][SIZE_HEIGHT];

        for (int i = 1; i < SIZE_WIDTH / prevSize; i++) {
            for (int j = 1; j < SIZE_HEIGHT / prevSize; j++) {
                int x = i * prevSize;
                int y = j * prevSize;
                double displacement = Math.abs(k);

                if (k < 0) {
                    cellsTemp[(int) (x / displacement)][(int) (y / displacement)] = cells[x][y];
                } else if (k >= 0 && (y * displacement < SIZE_HEIGHT) && (x * displacement < SIZE_WIDTH)) {
                    cellsTemp[(int) (x * displacement)][(int) (y * displacement)] = cells[x][y];
                }
            }
        }

        clone(cells, cellsTemp);
    }

    private void click(MouseEvent e) {
        if (state.equals(GameState.PAUSE)) {
            Point point = e.getPoint();
            int x = point.x;
            int y = point.y;

            int newX = x - x % DOT_SIZE;
            int newY = y - y % DOT_SIZE;

            if (SwingUtilities.isLeftMouseButton(e)) {
                cells[newX][newY] = true;
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                cells[newX][newY] = false;
            }
        }
    }

    public void setResize(int newSize) {
        k = newSize >= prevSize ? (double) newSize / prevSize : -1 * ((double) prevSize / newSize);
    }

}
