package ru.life.component.menu.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.life.component.GameField;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.KeyEvent;

import static ru.life.constant.MenuOptions.EDIT;

/**
 * Adds main menu buttons to Edit.
 * The buttons perform the following functions:
 * - Start/Pause
 * - Clean
 * - Step forward
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EditJMenu extends JMenu {

    private final GameField gameField;

    @PostConstruct
    private void init() {
        this.setText(EDIT.getOption());
        this.setMnemonic(KeyEvent.VK_E);
        createButtons();
    }

    private void createButtons() {

        JMenuItem start = this.add(new JMenuItem("Start/Pause", KeyEvent.VK_S));
        start.addActionListener(e -> gameField.setPause(!gameField.getPause()));
        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));

        JMenuItem clean = this.add(new JMenuItem("Clean"));
        clean.addActionListener(e -> gameField.setClean(true));
        clean.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

        this.addSeparator();

        JMenuItem step = this.add(new JMenuItem("Step forward", KeyEvent.VK_F));
        step.addActionListener(e -> gameField.setStep(true));
        step.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

    }

}
