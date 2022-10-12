package ru.life.component.menu.dialog;

import javax.swing.*;
import java.net.MalformedURLException;

public abstract class Oscillator {

    protected abstract String getName();

    protected abstract boolean[][] create();

    protected abstract JLabel getLabel() throws MalformedURLException;
}
