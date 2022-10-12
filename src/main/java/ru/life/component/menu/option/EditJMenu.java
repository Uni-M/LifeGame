package ru.life.component.menu.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.life.component.GameField;
import ru.life.constant.GameState;

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
@Order(2)
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
        start.addActionListener(e -> {
            gameField.setState(gameField.getState().nextState());
        });
        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));

        JMenuItem clean = this.add(new JMenuItem("Clean"));
        clean.addActionListener(e -> gameField.setState(GameState.CLEAN));
        clean.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

        this.addSeparator();

        JMenuItem step = this.add(new JMenuItem("Step forward", KeyEvent.VK_F));
        step.addActionListener(e -> gameField.setState(GameState.STEP));
        step.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

    }

}
