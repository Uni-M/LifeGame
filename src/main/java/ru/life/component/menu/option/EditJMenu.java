package ru.life.component.menu.option;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static ru.life.component.GameField.getPause;
import static ru.life.component.GameField.setClean;
import static ru.life.component.GameField.setPause;
import static ru.life.component.GameField.setStep;

public class EditJMenu extends JMenu {

    public EditJMenu(String s) {
        super(s);
        createButtons();
    }

    private void createButtons() {

        // старт/Шаг вперед/шаг назад/пауза/
        JMenuItem start = this.add(new JMenuItem("Start/Pause"));
        start.addActionListener(e -> setPause(!getPause()));
        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));

        JMenuItem clean = this.add(new JMenuItem("Clean"));
        clean.addActionListener(e -> setClean(true));
        clean.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

        this.addSeparator();

        JMenuItem step = this.add(new JMenuItem("Step forward"));
        step.addActionListener(e -> setStep(true));
        step.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

    }

}
