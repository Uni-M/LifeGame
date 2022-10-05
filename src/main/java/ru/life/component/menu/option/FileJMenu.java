package ru.life.component.menu.option;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import static ru.life.constant.Size.SIZE_HEIGHT;
import static ru.life.constant.Size.SIZE_WIDTH;

/**
 * Adds main menu buttons to File.
 * The buttons perform the following functions:
 * - Open
 * - Save
 * - Exit
 */
@Component
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

        JMenu save = new JMenu("Save");
        save.setMnemonic(KeyEvent.VK_S);
        this.add(save);

        JMenuItem saveScreenshot = save.add(new JMenuItem("Save screenshot", KeyEvent.VK_S));
        saveScreenshot.addActionListener(e -> {
            Thread t = new Thread(() -> {
                try {
                    String fileName = getPathToSave("png");
                    BufferedImage image = new BufferedImage(SIZE_WIDTH,
                            SIZE_HEIGHT, BufferedImage.TYPE_INT_RGB);
                    gameField.paint(image.createGraphics());
                    ImageIO.write(image, "png", new File(fileName));

                    JOptionPane.showMessageDialog(null, "Screen captured successfully.",
                            null, JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ioException) {
                    ioException.printStackTrace(); // TODO change
                }
            });
            t.start();
        });
        saveScreenshot.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));


        JMenuItem saveGif = save.add(new JMenuItem("Save gif", KeyEvent.VK_G));
        saveGif.addActionListener(e -> {

            Thread t = new Thread(() -> {
                AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();

                try {
                    String fileName = getPathToSave("gif");

                    gifEncoder.start(fileName);
                    gifEncoder.setDelay(500);   // 1 frame per 1/2 sec
                    gifEncoder.setRepeat(10);

                    for (int i = 0; i < 10; i++) {
                        BufferedImage image = new BufferedImage(SIZE_WIDTH, SIZE_HEIGHT,
                                BufferedImage.TYPE_INT_RGB);
                        gameField.paint(image.createGraphics());
                        gifEncoder.addFrame(image);
                        Thread.sleep(500);
                    }
                    gifEncoder.finish();
                    JOptionPane.showMessageDialog(null, "Screen captured successfully.",
                            null, JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    //TODO add logic
                }
            });
            t.start();

        });
        saveGif.setAccelerator(KeyStroke.getKeyStroke("ctrl G"));


        this.addSeparator();

        JMenuItem exit = this.add(new JMenuItem("Exit", KeyEvent.VK_E));
        exit.addActionListener(e -> System.exit(0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }


    private String getPathToSave(String type) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(type, type);
        fileChooser.addChoosableFileFilter(filter);

        int retVal = fileChooser.showSaveDialog(this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
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
