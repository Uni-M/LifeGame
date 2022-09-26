package ru.life.component;

import ru.life.constant.PictureCells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import static ru.life.component.GameField.getPause;
import static ru.life.component.GameField.getTimer;
import static ru.life.component.GameField.setCellFileName;
import static ru.life.component.GameField.setPause;
import static ru.life.component.GameField.setReLoad;
import static ru.life.constant.Constant.HELP_MESSAGE;

public class MainMenuBar extends JMenuBar {

    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu view = new JMenu("View");
    JMenu help = new JMenu("Help");

    public MainMenuBar() {
        this.add(file);
        this.add(edit);
        this.add(view);
        this.add(help);

        createButtons();
    }

    private void createButtons() {
        // тут должны быть общие возможности, открыть/закрыть/сохранить
        file.add(new JMenuItem("Open", 'O')).setEnabled(false);
        file.add(new JMenuItem("Save", 'S')).setEnabled(false);
        file.addSeparator();

        JMenuItem exit = file.add(new JMenuItem("Exit", 'E'));
        exit.addActionListener(e -> System.exit(0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));


        // старт/Шаг вперед/шаг назад/пауза/
        JMenuItem start = edit.add(new JMenuItem("Start/Pause"));
        start.addActionListener(e -> setPause(!getPause()));
        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));

        edit.addSeparator();

        edit.add(new JMenuItem("Step forward", 'B')).setEnabled(false);
        edit.add(new JMenuItem("Step back", 'F')).setEnabled(false);


        // цвет/настройка скорости/
        JMenu colors = new JMenu("Change color");
        view.add(colors);
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

            colorGroup.add(c);
            colors.add(c);
        }

        view.addSeparator();

        JMenuItem setSpeed = view.add(new JMenuItem("Speed", 'E'));
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


        // Новое окно с общей информацией, которую закрыть после прочтения можно
        JMenuItem getHelp = help.add(new JMenuItem("Help", 'E'));
        getHelp.addActionListener(e -> {
            setPause(true);
            JOptionPane.showMessageDialog(this,
                    HELP_MESSAGE,
                    "Help",
                    JOptionPane.PLAIN_MESSAGE);
            setPause(false);
        });
    }


}
