package ru.life.component.menu;

import ru.life.component.menu.option.EditJMenu;
import ru.life.component.menu.option.FileJMenu;
import ru.life.component.menu.option.HelpJMenu;
import ru.life.component.menu.option.ViewJMenu;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {
        this.add(new FileJMenu("File"));
        this.add(new EditJMenu("Edit"));
        this.add(new ViewJMenu("View"));
        this.add(new HelpJMenu("Help"));

    }
}
