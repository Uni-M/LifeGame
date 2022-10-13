package ru.life.component.menu.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.life.component.GameField;
import ru.life.component.menu.dialog.OscillatorTemplateDialog;
import ru.life.constant.GameState;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.KeyEvent;

import static ru.life.constant.MenuOptions.EDIT;

/**
 * Adds main menu buttons to Edit.
 * The buttons perform the following functions:
 * - Start/Pause
 * - Step forward
 * - Templates
 * - Clean
 */
@Component
@Order(2)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EditJMenu extends JMenu {

    private final GameField gameField;
    private final OscillatorTemplateDialog oscillatorTemplateDialog;

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


        JMenuItem step = this.add(new JMenuItem("Step forward", KeyEvent.VK_F));
        step.addActionListener(e -> gameField.setState(GameState.STEP));
        step.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        this.addSeparator();

        JMenu template = new JMenu("Templates");
        this.add(template);
        createTemplateChooser(template);

        this.addSeparator();


        JMenuItem clean = this.add(new JMenuItem("Clean"));
        clean.addActionListener(e -> gameField.setState(GameState.CLEAN));
        clean.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));

    }

    private void createTemplateChooser(JMenu template) {
        JMenuItem oscillators = template.add(new JMenuItem("Oscillators"));
        oscillators.addActionListener(e -> {
            Thread t = new Thread(() -> oscillatorTemplateDialog.setVisible(true));
            t.start();
        });
    }

}
