package ru.life.component.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.life.component.menu.option.EditJMenu;
import ru.life.component.menu.option.FileJMenu;
import ru.life.component.menu.option.HelpJMenu;
import ru.life.component.menu.option.ViewJMenu;

import javax.swing.*;
import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MainMenuBar extends JMenuBar {

    private final FileJMenu fileJMenu;
    private final EditJMenu editJMenu;
    private final ViewJMenu viewJMenu;
    private final HelpJMenu helpJMenu;

    public void init() {
        this.add(fileJMenu);
        this.add(editJMenu);
        this.add(viewJMenu);
        this.add(helpJMenu);

        setBackground(Color.LIGHT_GRAY);
    }

}
