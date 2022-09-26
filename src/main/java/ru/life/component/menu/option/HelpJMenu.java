package ru.life.component.menu.option;

import javax.swing.*;

import static ru.life.component.GameField.setPause;
import static ru.life.constant.HelpMessage.HELP_MESSAGE;

public class HelpJMenu extends JMenu {

    public HelpJMenu(String s) {
        super(s);
        createButtons();
    }

    private void createButtons() {

        // Новое окно с общей информацией, которую закрыть после прочтения можно
        JMenuItem help = this.add(new JMenuItem("Help"));

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
