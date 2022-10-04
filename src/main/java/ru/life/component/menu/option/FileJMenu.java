package ru.life.component.menu.option;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ru.life.constant.Size.SIZE_HEIGHT;
import static ru.life.constant.Size.SIZE_WIDTH;

public class FileJMenu extends JMenu {

    public FileJMenu(String s) {
        super(s);
        this.setMnemonic(KeyEvent.VK_F);
        createButtons();
    }

    /**
     * Adds main menu buttons to File.
     * The buttons perform the following functions:
     * - Open
     * - Save
     * - Exit
     */
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
                    String fileName = JOptionPane.showInputDialog(null, "Save file",
                            null, JOptionPane.INFORMATION_MESSAGE);

                    if (!fileName.toLowerCase().endsWith(".png")) {
                        JOptionPane.showMessageDialog(null, "Error: file name must end with \".png\".",
                                null, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                            BufferedImage image = new BufferedImage(SIZE_WIDTH,
                                    SIZE_HEIGHT, BufferedImage.TYPE_INT_RGB);
//                            WindowConfig.getFrame().paint(image.createGraphics()); // TODO realize
//                        gameField.paint(image.createGraphics());
                       ImageIO.write(image, "png", new File(fileName));
                        }
                        JOptionPane.showMessageDialog(null, "Screen captured successfully.",
                                null, JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            t.start();

        });


        JMenuItem saveGif = save.add(new JMenuItem("Save gif", KeyEvent.VK_G));
        saveGif.addActionListener(e -> {

            Thread t = new Thread(() -> {
                AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();

                try {
                    String fileName = JOptionPane.showInputDialog(null, "Save file",
                            null, JOptionPane.INFORMATION_MESSAGE);
                    gifEncoder.start(fileName);
                    gifEncoder.setDelay(500);   // 1 frame per 1/2 sec
                    gifEncoder.setRepeat(10);

                    if (!fileName.toLowerCase().endsWith(".gif")) {
                        JOptionPane.showMessageDialog(null, "Error: file name must end with \".gif\".",
                                null, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (int i = 0; i < 10; i++) {
                            BufferedImage image = new BufferedImage(SIZE_WIDTH,
                                    SIZE_HEIGHT, BufferedImage.TYPE_INT_RGB);
//                            WindowConfig.getFrame().paint(image.createGraphics());
//                            gameField.paint(image.createGraphics());  // TODO realize
                            gifEncoder.addFrame(image);
                            Thread.sleep(500);
                        }
                        gifEncoder.finish();
                        JOptionPane.showMessageDialog(null, "Screen captured successfully.",
                                null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                }
            });
            t.start();

        });

        this.addSeparator();

        JMenuItem exit = this.add(new JMenuItem("Exit", KeyEvent.VK_E));
        exit.addActionListener(e -> System.exit(0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
    }

}
