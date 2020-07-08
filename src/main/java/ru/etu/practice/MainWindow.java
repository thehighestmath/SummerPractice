package ru.etu.practice;

import javax.swing.*;
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

    public List<Node> getOutVertexes() {
        return outVertexes;
    }

    public List<Edge> getOutEdges() {
        return outEdges;
    }

    private final List<Node> outVertexes = new LinkedList<>(); // вершины графа
    private final List<Edge> outEdges = new LinkedList<>();
    private Node fromVertex;
    private Node toVertex;
    private boolean graphInitiated = false;
    private boolean graphModified = false;

    ClassLoader classLoader = this.getClass().getClassLoader();

    GridBagConstraints constraints = new GridBagConstraints();

    VisualGraph graph = new VisualGraph(outEdges, outVertexes);

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

    Graph graphStep = new Graph();
//    List<Edge> outEdgesStep = graphStep.getOutputEdges();
    List<Ellipse2D> vertexesStep = graph.getVertexes();
    int stepID = 0;

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
//        type.add(clear);
        type.add(delete);

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
        grid.add(delete, BorderLayout.NORTH);

        vertexes.addItemListener(this);
        edged.addItemListener(this);

        step.addActionListener(this);
        allSteps.addActionListener(this);
        clear.addActionListener(this);
        delete.addActionListener(this);
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
            if (emptyArea) {
                graph.addVertex.mouseClicked(mouseEvent);
                char vertexChar = graph.getLastAddedVertex();
                if (vertexChar <= 'z') {
                    outVertexes.add(new Node(vertexChar));
                    graphModified = true;
                }
            }
        } else if (edged.isSelected()) {

        } else if (delete.isSelected()) {
            for (Ellipse2D vertex : graph.getVertexes()) {
                if (vertex.contains(mouseEvent.getPoint())) {
//                    final char cr = outVertexes.get(graph.getVertexes().indexOf(vertex));
//                    outVertexes.remove(graph.getVertexes().indexOf(vertex));
//                    graph.getEdges().removeIf(elem -> vertex.contains(elem.getP1()) || vertex.contains(elem.getP2()));
//                    outEdges.removeIf(elem -> elem.from == cr || elem.to == cr);
//                    graph.getResultEdgesEdges().removeIf(elem -> vertex.contains(elem.getP1()) || vertex.contains(elem.getP2()));
//                    graphStep.getOutputEdges().removeIf(elem -> elem.from == cr || elem.to == cr);
//                    graph.getVertexes().remove(vertex);
//                    graphModified = true;
//                    repaint();
                    break;
                }
            }

            for (Line2D edge : graph.getEdges()) {
                if (edge.intersects(mouseEvent.getX() - 3, mouseEvent.getY() - 3, 6, 6)) {
                    outEdges.remove(graph.getEdges().indexOf(edge));
                    graph.getEdges().remove(edge);
                    graphModified = true;
                    repaint();
                    break;
                }
            }

            for (Line2D edge : graph.getResultEdgesEdges()) {
                if (edge.intersects(mouseEvent.getX() - 3, mouseEvent.getY() - 3, 6, 6)) {
                    graph.getResultEdgesEdges().remove(edge);
                    graphModified = true;
                    repaint();
                    break;
                }
            }

            repaint();
        } else {
            assert false;
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (edged.isSelected()) {
            List<Ellipse2D> vertexes = graph.getVertexes();
            boolean hasFound = false;
            for (Ellipse2D vertex : vertexes) {
                if (vertex.getBounds2D().contains(mouseEvent.getPoint())) {
                    hasFound = true;
                    graph.setEdge(
                            vertex.getBounds().getCenterX(),
                            vertex.getBounds().getCenterY()
                    );
//                    fromVertex = outVertexes.get(vertexes.indexOf(vertex));
                    break;
                }
            }
            if (!hasFound) {
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
                    toVertex = outVertexes.get(vertexes.indexOf(vertex));
                    break;
                }
            }
            if (outEdges.size() > 0) {
                Edge currentEdge = new Edge(fromVertex, toVertex, 0);
                for (Edge outEdge : outEdges) {
                    if (currentEdge.equals(outEdge)) {
                        graph.clearLastEdge();
                        JOptionPane.showMessageDialog(
                                this,
                                "Кажется, что ребро между этими вершинами уже существует",
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
            graph.moveVertex(mouseEvent);
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

    private void clearData() {
        stepID = 0;
        graphInitiated = false;
        graphModified = false;
//        graphStep.clearOutput();
        graph.clearResult();
        addStepInfo(String.valueOf(State.CLEAR));
        addStepInfo("===============");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == allSteps) {// переписать этот шаг
            if (graphModified) {
                clearData();
                graph.setTuple(null);
            }

            graphStep.initGraph(outEdges, outVertexes);
            Kruskal kruskal = new Kruskal(graphStep);
            addStepInfo(kruskal.kruskal());
//            addStepInfo(graphStep.kruskal());
//            List<Object> tuple = graphStep.nextStep() ;
//            graph.setTuple(tuple);
            List<Edge> outEdges = kruskal.getOutputEdges();
            List<Ellipse2D> vertexes = this.graph.getVertexes();
            List<Line2D> lines2D = new LinkedList<>();
            for (Edge edge : outEdges) {
                addLine2d(edge, vertexes, lines2D);
            }

            this.graph.resultEdges = new LinkedList<>(lines2D);
            this.graph.repaint();
            graphModified = true;
        } else if (actionEvent.getSource() == clear) {
            graph.clearScene();
            clearData();
            outEdges.clear();
            outVertexes.clear();
//            outEdgesStep.clear();
        } else if (actionEvent.getSource() == step) {
            if (graphInitiated) {
                if (graphModified) {
                    /*
                    delete result and back to start
                     */
                    clearData();
                    actionPerformed(actionEvent);
                } else {
                    /*
                    next step
                     */
//                    List<Object> tuple = graphStep.nextStep();
//                    addStepInfo(tuple);
//                    graph.setTuple(tuple);
                    //System.out.println(stepID);
//                    if (stepID < outEdgesStep.size()) {
//                        Edge edge = outEdgesStep.get(stepID);
//                        addLine2d(edge, vertexesStep, this.graph.resultEdges);
//                        stepID++;
//                    }
//                    if (tuple.get(0) == State.END) {
//                        graphModified = true;
//                        addStepInfo("===========================");
//                    }
//                    graph.repaint();
                }
            } else {
                /*
                init graph and do first step
                 */
                graphStep.initGraph(outEdges, outVertexes);
                graph.setTuple(null);
                stepID = 0;
                graphInitiated = true;
//                outEdgesStep = graphStep.getOutputEdges();
                vertexesStep = graph.getVertexes();
                actionPerformed(actionEvent);
            }
        } else {
            assert false;
        }
    }

    private void addLine2d(Edge edge, List<Ellipse2D> vertexes, List<Line2D> lines2D) {
        Node from = edge.from;
        Node to = edge.to;
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        for (Ellipse2D vertex : vertexes) {
            Rectangle2D rectangle2D = vertex.getBounds2D();
            double x = rectangle2D.getCenterX();
            double y = rectangle2D.getCenterY();
            if (from == outVertexes.get(vertexes.indexOf(vertex))) {
                x1 = x;
                y1 = y;
            } else if (to == outVertexes.get(vertexes.indexOf(vertex))) {
                x2 = x;
                y2 = y;
            }
        }
        Point2D pointFrom = new Point2D.Double(x1, y1);
        Point2D pointTo = new Point2D.Double(x2, y2);
        Line2D line2D = new Line2D.Double(
                pointFrom, pointTo
        );
        lines2D.add(line2D);
    }

    private void addStepInfo(List<Object> tuple) {
        textArea.setText(textArea.getText() + Kruskal.addStepInfo(tuple));
    }

    private void addStepInfo(String msg) {
        textArea.setText(textArea.getText() + msg + "\n");
    }
}
