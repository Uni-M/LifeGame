package ru.life.component.menu.option;

import ru.life.component.GameTimer;
import ru.life.constant.PictureCells;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import static ru.life.component.GameField.getTimer;
import static ru.life.component.GameField.setCellFileName;
import static ru.life.component.GameField.setReLoad;

public class ViewJMenu extends JMenu {

    public ViewJMenu(String s) {
        super(s);
        this.setMnemonic(KeyEvent.VK_V);
        createButtons();
    }

    private void createButtons() {

        // цвет/настройка скорости/
        JMenuItem colors = new JMenuItem("Change color", KeyEvent.VK_C);
        this.add(colors);
        ButtonGroup colorGroup = new ButtonGroup();

        for (PictureCells pictureCells : PictureCells.values()) {
            JRadioButtonMenuItem c = new JRadioButtonMenuItem(pictureCells.getColor());

            if (pictureCells.name().equals(PictureCells.DEFAULT.name())) {
                c.setSelected(true);
            }

            c.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setCellFileName(pictureCells.getFileName());
                    setReLoad(true);
                }
            });

            c.setAccelerator(KeyStroke.getKeyStroke(String.valueOf(pictureCells.ordinal()+1)));

            colorGroup.add(c);
            colors.add(c);
        }

        this.addSeparator();

        // TODO переделать и добавить KeyStroke
        JMenuItem setSpeed = this.add(new JMenuItem("Speed", KeyEvent.VK_S));
        setSpeed.addActionListener(e -> {
            GameTimer timer = getTimer();
            JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, 1000, timer.getSpeed());
            slider.setMajorTickSpacing(100);
            slider.setPaintTicks(true);
            slider.setSnapToTicks(true);

            slider.addChangeListener(changeEvent -> {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    timer.setSpeed(theSlider.getValue());
                    timer.setReStartTimer(true);
                }
            });
            JOptionPane.showMessageDialog(this, slider, "Set speed", JOptionPane.PLAIN_MESSAGE);
        });
    }

}
