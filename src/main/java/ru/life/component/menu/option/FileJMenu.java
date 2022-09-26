package ru.life.component.menu.option;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class FileJMenu extends JMenu {

    public FileJMenu(String s) {
        super(s);
        createButtons();
    }

    private void createButtons() {
        // тут должны быть общие возможности, открыть/закрыть/сохранить
        this.add(new JMenuItem("Open")).setEnabled(false);
        this.add(new JMenuItem("Save")).setEnabled(false);
        this.addSeparator();

        JMenuItem exit = this.add(new JMenuItem("Exit"));
        exit.addActionListener(e -> System.exit(0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }

}
