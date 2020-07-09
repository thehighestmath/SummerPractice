package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class MainWindow extends JFrame
        implements MouseListener, MouseMotionListener, ItemListener, ActionListener {

    GridBagConstraints constraints = new GridBagConstraints();

    VisualKruskal visualKruskal = new VisualKruskal();

    Container container = getContentPane();

    JPanel grid = new JPanel(new GridLayout(3, 2));

    JButton step = new JButton("Следующий шаг");
    JButton allSteps = new JButton("Визуализация");
    JButton clear = new JButton("Удалить граф");

    JRadioButton vertexes = new JRadioButton("Ввод вершины / перемещение вершины", true);
    JRadioButton edged = new JRadioButton("Стягивание вершин");
    JRadioButton delete = new JRadioButton("Удалить ребро / Удалить вершину");

    ButtonGroup type = new ButtonGroup();

    JTextArea textArea = new JTextArea(10, 50);
    JScrollPane pane = new JScrollPane(textArea);

    public MainWindow() {
        super("Визуализатор алгоритма Краскала");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 640);
        setResizable(false);
        setVisible(true);

        pane.setPreferredSize(new Dimension(150, 200));
        pane.setBorder(BorderFactory.createTitledBorder("Шаги алгоритма"));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        grid.setPreferredSize(new Dimension(200, 200));

        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        container.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
//        constraints.ipady = 200;
//        constraints.ipadx = 200;
        constraints.gridheight = 3;
        constraints.gridwidth = 3;

        constraints.gridx = 0;  // нулевая ячейка таблицы по вертикали
        constraints.gridy = 0;      // нулевая ячейка таблицы по горизонтали
        container.add(grid, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.ipady = 500;
        constraints.ipadx = 800;
        constraints.gridheight = 4;
        constraints.gridwidth = 4;
        container.add(visualKruskal, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.ipadx = 400;
        constraints.gridheight = 4;
        constraints.gridwidth = 1;
        container.add(pane, constraints);
        setLocationRelativeTo(null);

        type.add(vertexes);
        type.add(edged);
        type.add(delete);

        visualKruskal.setPreferredSize(new Dimension(700, 500));
        visualKruskal.setBorder(BorderFactory.createTitledBorder("Graph"));

//
        grid.add(step, BorderLayout.NORTH);
        grid.add(vertexes, BorderLayout.NORTH);
        grid.add(allSteps, BorderLayout.NORTH);
        grid.add(edged, BorderLayout.NORTH);
        grid.add(clear, BorderLayout.NORTH);

        grid.add(delete, BorderLayout.NORTH);

        vertexes.addItemListener(this);
        edged.addItemListener(this);

        step.addActionListener(this);
        allSteps.addActionListener(this);
        clear.addActionListener(this);
        delete.addActionListener(this);

        visualKruskal.addMouseListener(this);
        visualKruskal.addMouseMotionListener(this);

        Font font = new Font(null, Font.BOLD, 12);
        textArea.setFont(font);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (vertexes.isSelected()) {
            visualKruskal.addVertex.mouseClicked(mouseEvent);
        } else if (edged.isSelected()) {

        } else if (delete.isSelected()) {
            visualKruskal.deleteComponent(mouseEvent.getPoint());
        } else {
            assert false;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            boolean hasFound = visualKruskal.setEdge(mouseEvent.getPoint());
            if (!hasFound) {
                JOptionPane.showMessageDialog(
                        this,
                        "Кажется, что Вы не попали в область вершины, попробуйте ещё раз",
                        "Сообщение",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else if (vertexes.isSelected()) {
            visualKruskal.setMovableVertex(mouseEvent.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            visualKruskal.addEdge(mouseEvent.getPoint());
        } else if (vertexes.isSelected()) {
            visualKruskal.checkCollision();
        }
    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            visualKruskal.continueEdge(mouseEvent);
        } else if (vertexes.isSelected()) {
            visualKruskal.moveVertex(mouseEvent);
        }
    }


    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == allSteps) {
            addStepInfo(visualKruskal.kruskal());
            visualKruskal.repaint();
        } else if (actionEvent.getSource() == clear) {
            visualKruskal.clearGraph();
            addStepInfo(String.valueOf(State.CLEAR));
            addStepInfo("==========================");
        } else if (actionEvent.getSource() == step) {
            List<Object> tuple = visualKruskal.nextStep();
            if (tuple != null) {
                addStepInfo(tuple);
                if(tuple.get(0) == State.END) {
                    addStepInfo("==========================");
                }
                if (tuple.get(0) == State.CLEAR) {
                    addStepInfo(String.valueOf(State.CLEAR));
                    addStepInfo("==========================");
                }
            }
        } else {
            assert false;
        }
    }

    private void addStepInfo(List<Object> tuple) {
        textArea.setText(textArea.getText() + VisualKruskal.getStepInfo(tuple));
    }

    private void addStepInfo(String msg) {
        textArea.setText(textArea.getText() + msg + "\n");
    }
}
