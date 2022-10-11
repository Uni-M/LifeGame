package ru.life.component.menu.option;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.life.component.GameField;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.life.constant.MenuOptions.FILE;
import static ru.life.constant.MessageTemplate.SAVED_SUCCESS;
import static ru.life.constant.MessageTemplate.SAVING_FAILED;
import static ru.life.constant.MessageTemplate.SAVING_TITLE;
import static ru.life.constant.Size.SIZE_HEIGHT;
import static ru.life.constant.Size.SIZE_WIDTH;

/**
 * Adds main menu buttons to File.
 * The buttons perform the following functions:
 * - Open
 * - Save
 * - Exit
 */
@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileJMenu extends JMenu {

    private final GameField gameField;

    @PostConstruct
    private void init() {
        this.setText(FILE.getOption());
        this.setMnemonic(KeyEvent.VK_F);
        createButtons();
    }

    private void createButtons() {

        createOpenMenuItem();

        JMenu save = new JMenu("Save");
        save.setMnemonic(KeyEvent.VK_S);
        this.add(save);

        createSaveScreenshotMenuItem(save);
        createSaveGifMenuItem(save);
        this.addSeparator();

        createExitMenuItem();

    }

    private void createOpenMenuItem() {
        JMenuItem open = this.add(new JMenuItem("Open", KeyEvent.VK_O));
        open.addActionListener(e -> {

            Thread t = new Thread(() -> {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();

                    Icon icon = new ImageIcon(path);
                    JLabel label = new JLabel(icon);

                    JFrame f = new JFrame("Saved gif");
                    f.getContentPane().add(label);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                }
            });
            t.start();
        });
        open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
    }

    private void createSaveScreenshotMenuItem(JMenu save) {
        JMenuItem saveScreenshot = save.add(new JMenuItem("Saving screenshot", KeyEvent.VK_S));
        saveScreenshot.addActionListener(e -> {

            Thread t = new Thread(() -> {
                try {
                    String fileName = getPathToSave("png");
                    BufferedImage image = new BufferedImage(SIZE_WIDTH, SIZE_HEIGHT,
                            BufferedImage.TYPE_INT_RGB);
                    gameField.paint(image.createGraphics());

                    ImageIO.write(image, "png", new File(fileName));

                    JOptionPane.showMessageDialog(save, SAVED_SUCCESS,
                            SAVING_TITLE, JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    log.error("Fail save screenshot", ex);
                    JOptionPane.showMessageDialog(save, SAVING_FAILED,
                            SAVING_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();
        });
        saveScreenshot.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
    }

    private void createSaveGifMenuItem(JMenu save) {
        JMenuItem saveGif = save.add(new JMenuItem("Save gif", KeyEvent.VK_G));
        saveGif.addActionListener(e -> {

            Thread t = new Thread(() -> {
                AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
                try {
                    String fileName = getPathToSave("gif");
                    int screenshotSpeed = gameField.getTimer().getSpeed();

                    gifEncoder.start(fileName);
                    gifEncoder.setDelay(screenshotSpeed);
                    gifEncoder.setRepeat(20);

                    // TODO добавить запрос количества шагов?
                    for (int i = 0; i < 30; i++) {
                        BufferedImage image = new BufferedImage(SIZE_WIDTH, SIZE_HEIGHT,
                                BufferedImage.TYPE_INT_RGB);
                        gameField.paint(image.createGraphics());
                        gifEncoder.addFrame(image);
                        Thread.sleep(screenshotSpeed);
                    }
                    gifEncoder.finish();
                    JOptionPane.showMessageDialog(save, SAVED_SUCCESS,
                            SAVING_TITLE, JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    log.error("Fail save gif", ex);
                    JOptionPane.showMessageDialog(save, SAVING_FAILED,
                            SAVING_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();

        });
        saveGif.setAccelerator(KeyStroke.getKeyStroke("ctrl G"));
    }

    private void createExitMenuItem() {
        JMenuItem exit = this.add(new JMenuItem("Exit", KeyEvent.VK_E));
        exit.addActionListener(e -> System.exit(0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }

    private String getPathToSave(String type) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(type, type);
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getAbsolutePath();

            if (!fileName.toLowerCase().endsWith("." + type)) {
                fileName = fileName.concat("." + type);
            }
            return fileName;
        }

        return null;
    }

}
