package ru.life.component.menu.option;

import ru.life.component.GameField;
import ru.life.component.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static ru.life.component.GameField.getTimer;
import static ru.life.component.GameField.setPause;
import static ru.life.component.GameField.setPrevSize;
import static ru.life.component.GameField.setResize;
import static ru.life.constant.MessageTemplate.MAX_SPEED;
import static ru.life.constant.MessageTemplate.MIN_SPEED;
import static ru.life.constant.Size.DOT_SIZE;

public class ViewJMenu extends JMenu {

    public ViewJMenu(String s) {
        super(s);
        this.setMnemonic(KeyEvent.VK_V);
        createButtons();
    }

    /**
     * Adds main menu buttons to Edit.
     * The buttons perform the following functions:
     * - Change color
     * - Change size
     * - Change Speed (with Faster and Slower options)
     */
    private void createButtons() {

        JMenuItem color = new JMenuItem("Change color", KeyEvent.VK_O);
        this.add(color);
        color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Choose color", Color.BLACK);
            GameField.setCol(c);
        });
        color.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));


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
                            setPause(true);
                            setPrevSize(DOT_SIZE);
                            setResize(true, i);
                            DOT_SIZE = i;
                        }
                    });

                    s.setAccelerator(KeyStroke.getKeyStroke(String.valueOf(i / 4)));

                    sizeGroup.add(s);
                    size.add(s);
                }
        );


        this.addSeparator();

        JMenu speed = new JMenu("Speed");
        speed.setMnemonic(KeyEvent.VK_S);
        this.add(speed);

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
        GameTimer timer = getTimer();
        first.setEnabled(timer.updateSpeed(k));
        timer.setReStartTimer(true);

        if (!second.isEnabled()) {
            second.setEnabled(true);
        }

        if (!first.isEnabled()) {
            JOptionPane.showMessageDialog(first, message, "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

}
