package ru.life.component.menu.option;

import javax.swing.*;

import java.awt.event.KeyEvent;

import static ru.life.component.GameField.setPause;
import static ru.life.constant.MessageTemplate.HELP_MESSAGE;

public class HelpJMenu extends JMenu {

    public HelpJMenu(String s) {
        super(s);
        this.setMnemonic(KeyEvent.VK_H);
        createButtons();
    }

    /**
     * Adds main menu button to Help.
     * Pressing the button opens a panel with information about the game (and hotkeys?)
     */
    private void createButtons() {

        JMenuItem help = this.add(new JMenuItem("Help", KeyEvent.VK_H));

        help.addActionListener(e -> {
            setPause(true);
            JScrollPane scrollPane = new JScrollPane(createText());
            JOptionPane.showMessageDialog(this,
                    scrollPane,
                    "Game rules",
                    JOptionPane.PLAIN_MESSAGE);
        });
        help.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
    }

    private JTextArea createText() {
        JTextArea text = new JTextArea(25, 60);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setText(HELP_MESSAGE);
        text.setEditable(false);

        return text;
    }

}
