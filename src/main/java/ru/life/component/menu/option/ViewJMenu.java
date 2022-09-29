package ru.life.component.menu.option;

import ru.life.component.GameField;
import ru.life.component.GameTimer;
//import ru.life.constant.PictureCells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import static ru.life.component.GameField.getTimer;
//import static ru.life.component.GameField.setCellFileName;
//import static ru.life.component.GameField.setReLoad;
import static ru.life.constant.MessageTemplate.MAX_SPEED;
import static ru.life.constant.MessageTemplate.MIN_SPEED;

public class ViewJMenu extends JMenu {

    public ViewJMenu(String s) {
        super(s);
        this.setMnemonic(KeyEvent.VK_V);
        createButtons();
    }

    private void createButtons() {
        // TODO изменить через Color color = JColorChooser.showDialog(this, "Choose color", Color.BLACK); ??
        // переделать чтобы не было картинок, а была палитра?

        // цвет/настройка скорости/
        JMenuItem color = new JMenuItem("Change color", KeyEvent.VK_O);
        this.add(color);
        color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Choose color", Color.BLACK);
            GameField.setCol(c);
        });

//        JMenu colors = new JMenu("Change color");
//        colors.setMnemonic(KeyEvent.VK_C);
//        this.add(colors);
//        ButtonGroup colorGroup = new ButtonGroup();
//
//        for (PictureCells pictureCells : PictureCells.values()) {
//            JRadioButtonMenuItem c = new JRadioButtonMenuItem(pictureCells.getColor());
//
//            if (pictureCells.name().equals(PictureCells.DEFAULT.name())) {
//                c.setSelected(true);
//            }
//
//            c.addItemListener(e -> {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    setCellFileName(pictureCells.getFileName());
////                    setReLoad(true);
//                }
//            });
//
//            c.setAccelerator(KeyStroke.getKeyStroke(String.valueOf(pictureCells.ordinal() + 1)));
//
//            colorGroup.add(c);
//            colors.add(c);
//        }

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
