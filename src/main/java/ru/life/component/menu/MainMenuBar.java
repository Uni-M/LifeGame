package ru.life.component.menu;

import ru.life.component.menu.option.EditJMenu;
import ru.life.component.menu.option.FileJMenu;
import ru.life.component.menu.option.HelpJMenu;
import ru.life.component.menu.option.ViewJMenu;

import static ru.life.constant.MenuOptions.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {
        this.add(new FileJMenu(FILE.getOption()));
        this.add(new EditJMenu(EDIT.getOption()));
        this.add(new ViewJMenu(VIEW.getOption()));
        this.add(new HelpJMenu(HELP.getOption()));

        setBackground(Color.LIGHT_GRAY);
    }
}
