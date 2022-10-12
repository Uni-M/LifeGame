package ru.life.component.menu.option;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.life.component.menu.dialog.OscillatorTemplateDialog;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.KeyEvent;

import static ru.life.constant.MenuOptions.TEMPLATE;

@Component
@Order(4)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TemplateJMenu extends JMenu {

    private final OscillatorTemplateDialog oscillatorTemplateDialog;

    @PostConstruct
    private void init() {
        this.setText(TEMPLATE.getOption());
        this.setMnemonic(KeyEvent.VK_T);
        createButtons();
    }

    private void createButtons() {
        JMenu template = new JMenu("Templates");
        this.add(template);

        createTemplateChooser(template);

    }

    private void createTemplateChooser(JMenu template) {
        JMenuItem oscillators = template.add(new JMenuItem("Oscillators"));
        oscillators.addActionListener(e -> {
            Thread t = new Thread(() -> oscillatorTemplateDialog.setVisible(true));
            t.start();
        });
    }
}
