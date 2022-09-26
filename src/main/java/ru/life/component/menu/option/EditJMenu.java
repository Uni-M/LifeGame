package ru.life.component.menu.option;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static ru.life.component.GameField.getPause;
import static ru.life.component.GameField.setPause;

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

        this.addSeparator();

        this.add(new JMenuItem("Step forward", 'B')).setEnabled(false);
        this.add(new JMenuItem("Step back", 'F')).setEnabled(false);

    }


}
