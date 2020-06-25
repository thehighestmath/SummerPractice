package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame
        implements MouseListener, MouseMotionListener, ItemListener, ActionListener {
    ClassLoader classLoader = this.getClass().getClassLoader();

    MyJComponent graph = new MyJComponent();

    JPanel panel = new MyJPanel();
    Container container = getContentPane();
    JPanel grid = new MyJPanel(new GridLayout(2, 2));

    JButton step = new JButton("Следующий шаг");
    JButton allSteps = new JButton("Визуализация");

    JRadioButton vertexes = new JRadioButton("Ввод вершин", true);
    JRadioButton edged = new JRadioButton("Стягивание вершин");

    ButtonGroup type = new ButtonGroup();

    public MainWindow() {
        super("Визуализатор алгоритма Краскала");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        add(panel);
        type.add(vertexes);
        type.add(edged);
        panel.setLayout(new BorderLayout());

        grid.add(step, BorderLayout.NORTH);
        grid.add(vertexes, BorderLayout.NORTH);
        grid.add(allSteps, BorderLayout.NORTH);
        grid.add(edged, BorderLayout.NORTH);

        vertexes.addItemListener(this);
        edged.addItemListener(this);

        step.addActionListener(this);
        allSteps.addActionListener(this);

        container.add("North", grid);
        container.add("Center", graph);

        graph.addMouseListener(this);
        graph.addMouseMotionListener(this);

        setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (vertexes.isSelected()) {
            graph.mouseAdapter.mouseClicked(mouseEvent);
        } else if (edged.isSelected()) {

        } else {
            assert false;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
