package ru.life.component.menu.option;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class FileJMenu extends JMenu {

    public FileJMenu(String s) {
        super(s);
        this.setMnemonic(KeyEvent.VK_F);
        createButtons();
    }

    private void createButtons() {
        // тут должны быть общие возможности, открыть/закрыть/сохранить
        JMenuItem open = this.add(new JMenuItem("Open", KeyEvent.VK_O));
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);
            File file = fileChooser.getSelectedFile();
        });
        open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));


        JMenuItem save = this.add(new JMenuItem("Save", KeyEvent.VK_S));
        save.addActionListener((e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(this);
        }));
        save.setEnabled(false);
        this.addSeparator();

        JMenuItem exit = this.add(new JMenuItem("Exit", KeyEvent.VK_E));
        exit.addActionListener(e -> System.exit(0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }

}
