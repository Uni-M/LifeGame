package ru.life.component.menu.option;

import javax.swing.*;

import static ru.life.component.GameField.setPause;
import static ru.life.constant.Constant.HELP_MESSAGE;

public class HelpJMenu extends JMenu {

    public HelpJMenu (String s) {
        super(s);
        createButtons();
    }

    private void createButtons() {

        // Новое окно с общей информацией, которую закрыть после прочтения можно
        JMenuItem getHelp = this.add(new JMenuItem("Help", 'E'));
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
