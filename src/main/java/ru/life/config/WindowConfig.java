package ru.life.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.life.component.GameField;
import ru.life.component.menu.MainMenuBar;

import javax.swing.*;
import java.awt.*;

import static ru.life.constant.Size.FRAME_SIZE_HEIGHT;
import static ru.life.constant.Size.FRAME_SIZE_WIDTH;

@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WindowConfig {

    private final GameField gameField;
    private final MainMenuBar mainMenuBar;

    @Bean
    public void createMainWindow() {
        System.setProperty("java.awt.headless", "false");

        SwingUtilities.invokeLater(() -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();

            JFrame frame = new JFrame();
            frame.setTitle("Life");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setBounds((dimension.width - FRAME_SIZE_WIDTH) / 2,
                    (dimension.height - FRAME_SIZE_HEIGHT) / 2,
                    FRAME_SIZE_WIDTH,
                    FRAME_SIZE_HEIGHT);
            frame.add(gameField);
            frame.setVisible(true);
            frame.setJMenuBar(mainMenuBar);
            frame.revalidate();
            frame.setResizable(false);

        });
    }

}
