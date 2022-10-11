package ru.life.component.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.life.component.menu.option.EditJMenu;
import ru.life.component.menu.option.FileJMenu;
import ru.life.component.menu.option.HelpJMenu;
import ru.life.component.menu.option.ViewJMenu;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.List;
import java.awt.*;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MainMenuBar extends JMenuBar {

    private final List<? extends JMenu> jMenus;

    @PostConstruct
    public void init() {
        jMenus.forEach(this::add);
        setBackground(Color.LIGHT_GRAY);
    }

}
