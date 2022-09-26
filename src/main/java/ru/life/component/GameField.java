package ru.life.component;

import ru.life.constant.PictureCells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ru.life.constant.Constant.DOT_SIZE;
import static ru.life.constant.Constant.SIZE_HEIGHT;
import static ru.life.constant.Constant.SIZE_WIDTH;

public class GameField extends JPanel implements ActionListener {

    private Image dot;
    private Image emptyDot;

    private boolean[][] cells = new boolean[SIZE_WIDTH][SIZE_HEIGHT];
    private boolean[][] cellsTemp = new boolean[SIZE_WIDTH][SIZE_HEIGHT];

    private static GameTimer timer = new GameTimer();;

    private boolean inGame = true;
    private static boolean reLoad = false;
    private static boolean pause = true;

    private static String cellFileName = PictureCells.DEFAULT.getFileName();


    public GameField() {
        loadImages();
        initGame();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                click(e);
            }
        });
        setFocusable(true);
        // TODO добавить выбор дефолтных комбинаций ?

    }

    private void initGame() { // Старт при нажатии кнопки старт или энтер???
        for (int i = 0; i < SIZE_WIDTH / 16; i++) {
            for (int j = 0; j < SIZE_HEIGHT / 16; j++) {
                cells[i * DOT_SIZE][j * DOT_SIZE] = false;
            }
        }
        Timer t = new Timer(timer.getSpeed(), this);
        timer.setTimer(t);
        t.start();
    }

    // Рисунки клеток
    private void loadImages() {
        ImageIcon iid = new ImageIcon(cellFileName);
        dot = iid.getImage();
        ImageIcon iie = new ImageIcon("src/main/resources/point.png");
        emptyDot = iie.getImage();

    }

    public void reLoadImages() {
        ImageIcon iid = new ImageIcon(cellFileName);
        dot = iid.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        if (inGame) {

            if (reLoad) {
                reLoadImages();
                reLoad = !reLoad;
            }

            if (timer.isReStartTimer()) {
                timer.getTimer().stop();
                Timer t = new Timer(timer.getSpeed(), this);
                timer.setTimer(t);

                t.start();
                timer.setReStartTimer(false);
            }

            for (int i = 0; i < SIZE_WIDTH / 16; i++) {
                for (int j = 0; j < SIZE_HEIGHT / 16; j++) {
                    if (!cells[i * DOT_SIZE][j * DOT_SIZE]) {
                        g2d.drawImage(emptyDot, i * DOT_SIZE, j * DOT_SIZE, this);
                    } else {
                        g2d.drawImage(dot, i * DOT_SIZE, j * DOT_SIZE, this);
                    }
                }
            }

        } else {
            String end = "Game Over!";
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Courier New", Font.BOLD, 20));
            g2d.drawString(end, SIZE_WIDTH / 2 - (end.length() * DOT_SIZE) / 2, SIZE_HEIGHT / 2);
        }
    }

    private void life() {

        clone(cellsTemp, cells);

        for (int x = 1; x < (SIZE_WIDTH / 16) - 1; x++) {
            for (int y = 1; y < (SIZE_HEIGHT / 16) - 1; y++) {
                checkNeighbours(cells[x * DOT_SIZE][y * DOT_SIZE], x * DOT_SIZE, y * DOT_SIZE);
            }
        }

        clone(cells, cellsTemp);
    }

    private void clone(boolean[][] to, boolean[][] from) {

        for (int x = 0; x < (SIZE_WIDTH / 16); x++) {
            for (int y = 0; y < (SIZE_HEIGHT / 16); y++) {
                to[x * DOT_SIZE][y * DOT_SIZE] = from[x * DOT_SIZE][y * DOT_SIZE];
            }
        }
    }

    // Проверка на наличие живых соседей
    private void checkNeighbours(boolean isAlive, int x, int y) {

        int countNeighbours = 0;

        int xleft = x - DOT_SIZE;
        int yup = y - DOT_SIZE;
        int xright = x + DOT_SIZE;
        int ydown = y + DOT_SIZE;

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
    public void actionPerformed(ActionEvent e) { //собития которые происходят когда тикает таймер
        if (inGame && !pause) {
            life();
        }
        repaint(); //перерисовывает карту для обновления при движении
    }

    private void click(MouseEvent e) {
        if (pause) {
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

    public static void setReLoad(boolean reLoad) {
        GameField.reLoad = reLoad;
    }

    public static void setCellFileName(String cellFileName) {
        GameField.cellFileName = cellFileName;
    }

    public static void setPause(boolean pause) {
        GameField.pause = pause;
    }

    public static boolean getPause() {
        return pause;
    }

    public static GameTimer getTimer() {
        return timer;
    }

}
