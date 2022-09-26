package ru.life.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.life.component.GameField;
import ru.life.component.menu.MainMenuBar;

import javax.swing.*;
import java.awt.*;

import static ru.life.constant.Constant.FRAME_SIZE_HEIGHT;
import static ru.life.constant.Constant.FRAME_SIZE_WIDTH;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WindowConfig {

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
            frame.add(new GameField());
            frame.setVisible(true);
            frame.setJMenuBar(new MainMenuBar());
            frame.revalidate();

        });
    }
}
