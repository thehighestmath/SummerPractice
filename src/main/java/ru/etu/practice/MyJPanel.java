package ru.etu.practice;

import javax.swing.*;
import java.awt.*;

public class MyJPanel extends JPanel {
    public MyJPanel(LayoutManager var1, boolean var2) {
        super(var1, var2);
    }

    public MyJPanel(LayoutManager var1) {
        this(var1, true);
    }

    public MyJPanel(boolean var1) {
        this(new FlowLayout(), var1);
    }

    public MyJPanel() {
        this(true);
    }
}
