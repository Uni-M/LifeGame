package ru.life.component.menu.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.life.component.GameField;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.KeyEvent;

import static ru.life.constant.MenuOptions.HELP;
import static ru.life.constant.MessageTemplate.HELP_MESSAGE;

/**
 * Adds main menu button to Help.
 * Pressing the button opens a panel with information about the game (and hotkeys?)
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HelpJMenu extends JMenu {

    private final GameField gameField;

    @PostConstruct
    private void init() {
        this.setText(HELP.getOption());
        this.setMnemonic(KeyEvent.VK_H);
        createButtons();
    }

    private void createButtons() {

        JMenuItem help = this.add(new JMenuItem("Help", KeyEvent.VK_H));

        help.addActionListener(e -> {
            gameField.setPause(true);
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
