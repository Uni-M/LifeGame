package ru.life.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static ru.life.constant.Constant.*;

public class GameField extends JPanel implements ActionListener {

    private Image dot;
    private Image emptyDot;

    private boolean[][] cells = new boolean[SIZE_WIDTH][SIZE_HEIGHT];
    private boolean[][] cellsTemp = new boolean[SIZE_WIDTH][SIZE_HEIGHT];

    int speed = 500;
    private Timer timer;

    private boolean inGame = true;
    private boolean start = false;

    public GameField() {
        loadImages();
        initGame();
        addKeyListener(new KeyAdapterImpl());
        addMouseListener(new MouseListenerImpl());
        setFocusable(true);
        // TODO добавить две кнопки старт и паузу и выбор дефолтных комбинаций
    }

    private void initGame() { // Старт при нажатии кнопки старт или энтер???
        for (int i = 0; i < SIZE_WIDTH / 16; i++) {
            for (int j = 0; j < SIZE_HEIGHT / 16; j++) {
                cells[i * DOT_SIZE][j * DOT_SIZE] = false;
            }
        }

        timer = new Timer(speed, this);
        timer.start();
    }

    // Рисунки клеток
    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        dot = iid.getImage();
        ImageIcon iie = new ImageIcon("src/main/resources/point.png");
        emptyDot = iie.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (inGame) {

            for (int i = 0; i < SIZE_WIDTH / 16; i++) {
                for (int j = 0; j < SIZE_HEIGHT / 16; j++) {
                    if (!cells[i * DOT_SIZE][j * DOT_SIZE]) {
                        g.drawImage(emptyDot, i * DOT_SIZE, j * DOT_SIZE, this);
                    } else {
                        g.drawImage(dot, i * DOT_SIZE, j * DOT_SIZE, this);
                    }
                }
            }
        } else {
            String end = "Game Over!";
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString(end, SIZE_WIDTH / 2 - (end.length() * DOT_SIZE) / 2, SIZE_HEIGHT / 2);
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
        if (inGame && start) {
            life();
        }
        repaint(); //перерисовывает карту для обновления при движении
    }


    class KeyAdapterImpl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                start = true;
            }
            if (key == KeyEvent.VK_ESCAPE) {
                start = false;
            }
        }

    }

    class MouseListenerImpl implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            int x = point.x;
            int y = point.y;

            int newX = x - x % DOT_SIZE;
            int newY = y - y % DOT_SIZE;

            cells[newX][newY] = true;

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

}
