package ru.life.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.life.component.GameField;

import javax.swing.*;

import static ru.life.constant.Constant.SIZE_HEIGHT;
import static ru.life.constant.Constant.SIZE_WIDTH;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WindowConfig {

    @Bean
    public void createMainWindow() {
        System.setProperty("java.awt.headless", "false");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setTitle("Life");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(SIZE_WIDTH+15, SIZE_HEIGHT+40);
            frame.setLocation(200, 30);
            frame.add(new GameField());
            frame.setVisible(true);
        });

    }
}
