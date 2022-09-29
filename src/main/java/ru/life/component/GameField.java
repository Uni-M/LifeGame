package ru.life.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static ru.life.constant.Size.DOT_SIZE;
import static ru.life.constant.Size.SIZE_HEIGHT;
import static ru.life.constant.Size.SIZE_WIDTH;

public class GameField extends JPanel implements ActionListener {

    private Image emptyDot;

    private boolean[][] cells = new boolean[SIZE_WIDTH][SIZE_HEIGHT];
    private boolean[][] cellsTemp = new boolean[SIZE_WIDTH][SIZE_HEIGHT];

    private static final GameTimer timer = new GameTimer();

    private static boolean step = false;
    private static boolean clean = false;
    private static boolean pause = true;


    public static void setCol(Color col) {
        GameField.col = col;
    }

    private static Color col;

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
        for (int i = 0; i < SIZE_WIDTH / DOT_SIZE; i++) {
            for (int j = 0; j < SIZE_HEIGHT / DOT_SIZE; j++) {
                cells[i * DOT_SIZE][j * DOT_SIZE] = false;
            }
        }
        Timer t = new Timer(timer.getSpeed(), this);
        timer.setTimer(t);
        t.start();
    }

    // Рисунки клеток
    private void loadImages() {
        ImageIcon iie = new ImageIcon("src/main/resources/point.png");
        emptyDot = iie.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
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
                if (!cells[i * DOT_SIZE][j * DOT_SIZE]) {
//                    g2d.draw3DRect(i*DOT_SIZE, j*DOT_SIZE, DOT_SIZE, DOT_SIZE, true);
                    Rectangle2D rect = new Rectangle2D.Double(i*DOT_SIZE, j*DOT_SIZE, DOT_SIZE, DOT_SIZE);
                    g2d.draw(rect);
//                    g2d.drawImage(emptyDot, i * DOT_SIZE, j * DOT_SIZE, this);
                } else {
//                    Rectangle rect = new Rectangle(i*DOT_SIZE, j*DOT_SIZE, DOT_SIZE, DOT_SIZE);
                    Rectangle2D rect = new Rectangle2D.Double(i*DOT_SIZE, j*DOT_SIZE, DOT_SIZE, DOT_SIZE);
                    g2d.fill(rect);
//                    g2d.draw(rect);

                }
            }
        }

    }

    private void life() {

        clone(cellsTemp, cells);

        for (int x = 1; x < (SIZE_WIDTH / DOT_SIZE) - 1; x++) {
            for (int y = 1; y < (SIZE_HEIGHT / DOT_SIZE) - 1; y++) {
                checkNeighbours(cells[x * DOT_SIZE][y * DOT_SIZE], x * DOT_SIZE, y * DOT_SIZE);
            }
        }

        clone(cells, cellsTemp);
    }

    private void clone(boolean[][] to, boolean[][] from) {

        for (int x = 0; x < (SIZE_WIDTH / DOT_SIZE); x++) {
            for (int y = 0; y < (SIZE_HEIGHT / DOT_SIZE); y++) {
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
    public void actionPerformed(ActionEvent e) { //события которые происходят когда тикает таймер

        // TODO заменить на statemachine
        if (!pause && !clean && !step) {
            life();

        } else if(pause && !clean && step) {
            life();
            step = false;

        } else if (clean){
            clone(cells, new boolean[SIZE_WIDTH][SIZE_HEIGHT]);
            clean = false;
            pause = true;
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


    public static void setPause(boolean pause) {
        GameField.pause = pause;
    }

    public static boolean getPause() {
        return pause;
    }

    public static GameTimer getTimer() {
        return timer;
    }

    public static void setClean(boolean clean) {
        GameField.clean = clean;
    }

    public static void setStep(boolean step) {
        GameField.step = step;
    }


}
