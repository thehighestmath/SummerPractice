package ru.etu.practice;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class MainWindow extends JFrame
        implements MouseListener, MouseMotionListener, ItemListener, ActionListener {

    public List<Character> getOutVertexes() {
        return outVertexes;
    }

    public List<Edge> getOutEdges() {
        return outEdges;
    }

    private final List<Character> outVertexes = new LinkedList<>();
    private final List<Edge> outEdges = new LinkedList<>();
    private char vertex = 'a';
    private char fromVertex;
    private char toVertex;
    private boolean graphInitiated = false;
    private boolean graphModified = false;

    ClassLoader classLoader = this.getClass().getClassLoader();

    GridBagConstraints constraints = new GridBagConstraints();

    MyJComponent graph = new MyJComponent(this);

    JPanel panel = new MyJPanel();
    Container container = getContentPane();
    JPanel grid = new MyJPanel(new GridLayout(3, 2));
    JPanel grid2 = new MyJPanel(new GridBagLayout());

    JButton step = new JButton("Следующий шаг");
    JButton allSteps = new JButton("Визуализация");
    JButton clear = new JButton("Удалить граф");

    JRadioButton vertexes = new JRadioButton("Ввод вершины / перемещение вершины", true);
    JRadioButton edged = new JRadioButton("Стягивание вершин");

    ButtonGroup type = new ButtonGroup();

    JTextArea textArea = new JTextArea(10, 50);
    JScrollPane pane = new JScrollPane(textArea);

    Graph graphStep = new Graph();
    List<Edge> outEdgesStep = graphStep.getOutputEdges();
    List<Ellipse2D> vertexesStep = graph.getVertexes();
    List<Line2D> lines2DStep = new LinkedList<>();
    int stepID = 0;

    public MainWindow() {
        super("Визуализатор алгоритма Краскала");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 640);
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
        container.add(graph, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 4;
        constraints.gridy = 3;
//        constraints.ipady = 500;
        constraints.ipadx = 400;
        constraints.gridheight = 4;
        constraints.gridwidth = 1;
        container.add(pane, constraints);
        setLocationRelativeTo(null);

//        add(panel);
        type.add(vertexes);
        type.add(edged);
//        panel.setLayout(new BorderLayout());
//
        graph.setPreferredSize(new Dimension(700, 500));
        graph.setBorder(BorderFactory.createTitledBorder("Graph"));

//
        grid.add(step, BorderLayout.NORTH);
        grid.add(vertexes, BorderLayout.NORTH);
        grid.add(allSteps, BorderLayout.NORTH);
        grid.add(edged, BorderLayout.NORTH);
        grid.add(clear, BorderLayout.NORTH);
//
//        grid2.add(graph, BorderLayout.WEST);
//        grid2.add(textArea, BorderLayout.EAST);
//
        vertexes.addItemListener(this);
        edged.addItemListener(this);

        step.addActionListener(this);
        allSteps.addActionListener(this);
        clear.addActionListener(this);
//
//        container.add("North", grid);
//        container.add("South", grid2);
////        container.add("East", textArea);
//
        graph.addMouseListener(this);
        graph.addMouseMotionListener(this);

        Font font = new Font(null, Font.BOLD, 12);
        textArea.setFont(font);
//
//        setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (vertexes.isSelected()) {
            boolean emptyArea = true;
            List<Ellipse2D> vertexes = graph.getVertexes();
            for (Ellipse2D vertex : vertexes) {
                if (vertex.getBounds2D().contains(mouseEvent.getPoint())) {
                    emptyArea = false;
                    break;
                }
            }
            if (emptyArea) {// в методе addVertex требуется вместо return прописать выброс исключения и отлавливать его здесь
                graph.addVertex.mouseClicked(mouseEvent);
                outVertexes.add(vertex++);
                graphModified = graphInitiated;
            }
        } else if (edged.isSelected()) {

        } else {
            assert false;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            List<Ellipse2D> vertexes = graph.getVertexes();
            boolean hasFound = false;
            char i = 0;
            for (Ellipse2D vertex : vertexes) {
                if (vertex.getBounds2D().contains(mouseEvent.getPoint())) {
                    hasFound = true;
                    graph.setEdge(
                            vertex.getBounds().getCenterX(),
                            vertex.getBounds().getCenterY()
                    );
                    break;
                }
                i++;
            }
            if (hasFound) {
                fromVertex = (char) ('a' + i);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Кажется, что Вы не попали в область вершины, попробуйте ещё раз",
                        "Сообщение",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else if (vertexes.isSelected()) {
            boolean hasFound = false;
            List<Ellipse2D> vertexes = graph.getVertexes();
            for (Ellipse2D vertex : vertexes) {
                if (vertex.getBounds2D().contains(mouseEvent.getPoint())) {
                    graph.chooseMovableVertex(vertex);
                    graph.chooseMovableEdges();
                    hasFound = true;
                    break;
                }
            }
            if (!hasFound) {
                graph.chooseMovableVertex(null);
            }

        }
    }

    private void setEdge(List<Edge> edges) {
        String result;
        boolean isFirst = true;
        do {
            if (!isFirst) {
                JOptionPane.showMessageDialog(
                        this,
                        "Нужно ввести положительно число.\n" +
                                "Попробуйте еще раз",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            result = JOptionPane.showInputDialog(
                    this,
                    "<html><h2>Введите вес ребра");
            if (result == null) {
                graph.clearLastEdge();
                return;
            }
            isFirst = false;
        } while (!result.matches("\\d+") || Integer.parseInt(result) == 0);

        int distance = Integer.parseInt(result);
        Edge edge = new Edge(fromVertex, toVertex, distance);
        edges.add(edge);
        graphModified = graphInitiated;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            List<Ellipse2D> vertexes = graph.getVertexes();
            boolean hasFound = false;
            char i = 0;
            for (Ellipse2D vertex : vertexes) {
                if (vertex.getBounds2D().contains(mouseEvent.getPoint())) {
                    hasFound = true;
                    if (!graph.releasedEdge(vertex.getBounds().getCenterX(), vertex.getBounds().getCenterY())) {
                        graph.clearLastEdge();
                        JOptionPane.showMessageDialog(
                                this,
                                "Ввод петель не возможен.",
                                "Сообщение",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                    break;
                }
                i++;
            }
            toVertex = (char) ('a' + i);
            if (outEdges.size() > 0) {
                Edge currentEdge = new Edge(fromVertex, toVertex, 0);
                for (Edge outEdge : outEdges) {
                    if (currentEdge.equals(outEdge)) {
                        graph.clearLastEdge();
                        JOptionPane.showMessageDialog(
                                this,
                                "Кажется, что Вы ребро между этими вершинами уже существует",
                                "Сообщение",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }
            }

            if (hasFound) {
                setEdge(outEdges);
                graph.repaint();
            } else {
                graph.clearLastEdge();
                JOptionPane.showMessageDialog(
                        this,
                        "Кажется, что Вы не попали в область вершины, попробуйте ещё раз",
                        "Сообщение",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else if (vertexes.isSelected()) {
            graph.checkCollision();
            graph.freeMovableVertex();
        }
    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            graph.continueEdge(mouseEvent);
        } else if (vertexes.isSelected()) {
            graph.moveVertex(mouseEvent.getPoint());
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
        if (actionEvent.getSource() == allSteps) {// переписать этот шаг
            graphStep.initGraph(outEdges, outVertexes);
            graphStep.kraskal();
            List<Edge> outEdges = graphStep.getOutputEdges();
            List<Ellipse2D> vertexes = this.graph.getVertexes();
            List<Line2D> lines2D = new LinkedList<>();
            for (Edge edge : outEdges) {
                addLine2d(edge, vertexes, lines2D);
            }

            this.graph.resultEdges = new LinkedList<>(lines2D);
            this.graph.repaint();
        } else if (actionEvent.getSource() == clear) {
            graph.clearScene();
            outEdges.clear();
            outVertexes.clear();
            vertex = 'a';
            graphInitiated = false;
            graphModified = false;
        } else if (actionEvent.getSource() == step) {
            if (graphInitiated) {
                if (graphModified) {
                    /*
                    need to delete result and back to start
                     */
                    stepID = 0;
                    graphInitiated = false;
                    graphModified = false;
                    graphStep.clearOutput();
                    graph.clearResult();
                    addStepInfo(String.valueOf(State.CLEAR));
                    addStepInfo("===============");
                    actionPerformed(actionEvent);
                } else {
                    /*
                    do another step
                    TODO
                    +требуется добавить метод(задача на третью итерацию), возвращающий рассматриваемое ребро
                    в метод покраски нужно передавать состояние в которое зашел алгоритм
                     новая компонента и соединение компонент - зеленый
                     остовное дерево - синий
                     цикл - красный
                     При вызове следующего шага или визуализации, требуется стереть все красные ребра. А зеленые перекрасить в синий

                     +Избавиться от проверки stepID < outEdgesStep.size() и заменить её на проверку State
                     */
                    List<Object> tuple = graphStep.nextStep();
                    tuple.add(graphStep.getValue());
                    addStepInfo(tuple);
                    System.out.println(stepID);
                    if (stepID < outEdgesStep.size()) {
                        Edge edge = outEdgesStep.get(stepID);
                        addLine2d(edge, vertexesStep, this.graph.resultEdges);
                        stepID++;
                        this.graph.repaint();
                    }
                    if (tuple.get(0) == State.END) {
                        graphModified = true;
                        addStepInfo("============================");
                    }
                }
            } else {
                /*
                init graph and do first step
                 */
                graphStep.initGraph(outEdges, outVertexes);
                stepID = 0;
                graphInitiated = true;
                outEdgesStep = graphStep.getOutputEdges();
                vertexesStep = graph.getVertexes();
                actionPerformed(actionEvent);
            }
        } else {
            assert false;
        }
    }

    private void addLine2d(Edge edge, List<Ellipse2D> vertexes, List<Line2D> lines2D) {
        char from = edge.from;
        char to = edge.to;
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        char i = 0;
        for (Ellipse2D vertex : vertexes) {
            Rectangle2D rectangle2D = vertex.getBounds2D();
            double x = rectangle2D.getCenterX();
            double y = rectangle2D.getCenterY();
            if (from == (char) ('a' + i)) {
                x1 = x;
                y1 = y;
            } else if (to == (char) ('a' + i)) {
                x2 = x;
                y2 = y;
            }
            i++;
        }
        Point2D pointFrom = new Point2D.Double(x1, y1);
        Point2D pointTo = new Point2D.Double(x2, y2);
        Line2D line2D = new Line2D.Double(
                pointFrom, pointTo
        );
        lines2D.add(line2D);
    }

    private void addStepInfo(List<Object> tuple) {
        State state = (State) tuple.get(0);
        StringBuilder addText = new StringBuilder();
        if (state == State.SORT) {
            addText.append(state);
        } else if (state == State.END) {
            assert tuple.size() > 1;
            addText.append(state);
            addText.append("\n");
            int value = (int) tuple.get(1);
            addText.append("Minimum spanning tree weight is ");
            addText.append(value);
        }
        else if (state == State.LOOP){
            assert tuple.size() > 1;
            addText.append(state);
            addText.append(" | ");
            Edge edge = (Edge) tuple.get(1);
            addText.append(edge);
            addText.append(" will not be added");
        }else {
            assert tuple.size() > 1;
            addText.append(state);
            addText.append(" | ");
            Edge edge = (Edge) tuple.get(1);
            addText.append(edge);
            addText.append(" added");
        }
        textArea.setText(textArea.getText() + addText.toString() + "\n");

    }

    private void addStepInfo(String msg) {
        textArea.setText(textArea.getText() + msg + "\n");
    }
}
