package ru.life.component.menu.dialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.life.Oscillator;
import ru.life.component.GameField;
import ru.life.constant.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.life.constant.Size.DIALOG_FRAME_SIZE_HEIGHT;
import static ru.life.constant.Size.DIALOG_FRAME_SIZE_WIDTH;


@Component
public class OscillatorTemplateDialog extends JDialog {

    private final GameField gameField;
    private final List<? extends Oscillator> oscillators;

    public OscillatorTemplateDialog(@Autowired GameField gameField,
                                    @Autowired List<? extends Oscillator> oscillators) {
        super((Dialog) null, "Oscillators", true);
        this.gameField = gameField;
        this.oscillators = oscillators;
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(DIALOG_FRAME_SIZE_WIDTH, DIALOG_FRAME_SIZE_HEIGHT);
        this.add(new JScrollPane(createScrollPane()));
    }

    private JPanel createScrollPane() {
        JPanel panel = new JPanel();

        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);

        AtomicInteger constr = new AtomicInteger(1);
        ButtonGroup sizeGroup = new ButtonGroup();

        oscillators.forEach(oscillator -> {
            GridBagConstraints constraint1 = new GridBagConstraints();
            constraint1.weightx = 0;
            constraint1.weighty = 0;
            constraint1.gridx = 0;
            constraint1.gridy = constr.get();
            constraint1.gridheight = 1;
            constraint1.gridwidth = 1;

            JRadioButtonMenuItem s = new JRadioButtonMenuItem(oscillator.getName());
            s.addItemListener(es -> {
                if (es.getStateChange() == ItemEvent.SELECTED) {
                    gameField.setState(GameState.PAUSE);
                    gameField.setCells(oscillator.create());
                    s.setSelected(false);
                }
            });

            sizeGroup.add(s);
            panel.add(s, constraint1);

            try {

                GridBagConstraints constraint2 = new GridBagConstraints();
                constraint2.weightx = 0;
                constraint2.weighty = 0;
                constraint2.gridx = 1;
                constraint2.gridy = constr.getAndIncrement();
                constraint2.gridheight = 1;
                constraint2.gridwidth = 1;

                JLabel label = oscillator.getLabel();
                panel.add(label, constraint2);
            } catch (IOException e) {
                // TODO обработку сделать
            }
        });

        return panel;
    }

}
