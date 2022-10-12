package ru.life.component.menu.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.life.component.GameField;
import ru.life.component.GameTimer;
import ru.life.constant.GameState;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static ru.life.constant.MenuOptions.VIEW;
import static ru.life.constant.MessageTemplate.MAX_SPEED;
import static ru.life.constant.MessageTemplate.MIN_SPEED;
import static ru.life.constant.Size.DOT_SIZE;

/**
 * Adds main menu buttons to Edit.
 * The buttons perform the following functions:
 * - Change color
 * - Change size
 * - Change Speed (with Faster and Slower options)
 */
@Component
@Order(3)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ViewJMenu extends JMenu {

    private final GameField gameField;
    private final GameTimer gameTimer;

    @PostConstruct
    private void init() {
        this.setText(VIEW.getOption());
        this.setMnemonic(KeyEvent.VK_V);
        createButtons();
    }

    private void createButtons() {

        JMenu color = new JMenu("Change color");
        this.add(color);
        color.setMnemonic(KeyEvent.VK_C);
        createColorCellsMenuItem(color);
        createColorBackgroundMenuItem(color);

        createChangeSizeMenuItem();

        JMenu speed = new JMenu("Speed");
        speed.setMnemonic(KeyEvent.VK_S);
        this.add(speed);
        createChangeSpeedMenuItem(speed);

    }

    private void createColorCellsMenuItem(JMenu color) {
        JMenuItem colorCells = color.add(new JMenuItem("Cells", KeyEvent.VK_C));
        colorCells.addActionListener(e -> {

            Thread t = new Thread(() -> {
                Color c = JColorChooser.showDialog(this, "Choose color", Color.BLACK);
                gameField.setCol(c);
            });
            t.start();
        });
        colorCells.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
    }

    private void createColorBackgroundMenuItem(JMenu color) {
        JMenuItem colorBackground = color.add(new JMenuItem("Background", KeyEvent.VK_B));
        colorBackground.addActionListener(e -> {

            Thread t = new Thread(() -> {
                Color c = JColorChooser.showDialog(this, "Choose color", Color.BLACK);
                gameField.setBackground(c);
            });
            t.start();
        });
        colorBackground.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
    }

    private void createChangeSizeMenuItem() {
        JMenu size = new JMenu("Change size");
        size.setMnemonic(KeyEvent.VK_C);
        this.add(size);
        ButtonGroup sizeGroup = new ButtonGroup();
        Arrays.stream(new int[]{4, 8, 16, 20}).forEach(i -> {
                    JRadioButtonMenuItem s = new JRadioButtonMenuItem(i + "px");
                    if (i == 8) {
                        s.setSelected(true);
                    }
                    s.addItemListener(es -> {
                        if (es.getStateChange() == ItemEvent.SELECTED) {
                            gameField.setPrevSize(DOT_SIZE);
                            gameField.setState(GameState.RESIZE);
                            gameField.setResize(i);
                            DOT_SIZE = i;
                        }
                    });

                    s.setAccelerator(KeyStroke.getKeyStroke(String.valueOf(i / 4)));

                    sizeGroup.add(s);
                    size.add(s);
                }
        );
    }

    private void createChangeSpeedMenuItem(JMenu speed) {
        JMenuItem faster = this.add(new JMenuItem("Faster", KeyEvent.VK_F));
        JMenuItem slower = this.add(new JMenuItem("Slower", KeyEvent.VK_S));
        speed.add(faster);
        speed.add(slower);

        faster.addActionListener(e -> updateSpeed(faster, slower, -100, MAX_SPEED));
        faster.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));

        slower.addActionListener(e -> updateSpeed(slower, faster, +100, MIN_SPEED));
        slower.setAccelerator(KeyStroke.getKeyStroke("ctrl M"));
    }

    private void updateSpeed(JMenuItem first, JMenuItem second, int k, String message) {
        first.setEnabled(gameTimer.updateSpeed(k));
        gameTimer.setReStartTimer(true);

        if (!second.isEnabled()) {
            second.setEnabled(true);
        }

        if (!first.isEnabled()) {
            JOptionPane.showMessageDialog(first, message, "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

}
