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

    ClassLoader classLoader = this.getClass().getClassLoader();

    MyJComponent graph = new MyJComponent(this);

    JPanel panel = new MyJPanel();
    Container container = getContentPane();
    JPanel grid = new MyJPanel(new GridLayout(3, 2));

    JButton step = new JButton("Следующий шаг");
    JButton allSteps = new JButton("Визуализация");
    JButton clear = new JButton("Удалить граф");

    JRadioButton vertexes = new JRadioButton("Ввод вершины / перемещение вершины", true);
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
        type.add(clear);
        panel.setLayout(new BorderLayout());

        grid.add(step, BorderLayout.NORTH);
        grid.add(vertexes, BorderLayout.NORTH);
        grid.add(allSteps, BorderLayout.NORTH);
        grid.add(edged, BorderLayout.NORTH);
        grid.add(clear, BorderLayout.NORTH);

        vertexes.addItemListener(this);
        edged.addItemListener(this);

        step.addActionListener(this);
        allSteps.addActionListener(this);
        clear.addActionListener(this);

        container.add("North", grid);
        container.add("Center", graph);

        graph.addMouseListener(this);
        graph.addMouseMotionListener(this);

        setLocationRelativeTo(null);
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
                outVertexes.add(vertex++);
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
            isFirst = false;
        } while (result == null || !result.matches("\\d+") || Integer.parseInt(result) == 0);

        int distance = Integer.parseInt(result);
        Edge edge = new Edge(fromVertex, toVertex, distance);
        edges.add(edge);
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
        if (actionEvent.getSource() == allSteps) {
            Graph graph = new Graph();
            graph.initGraph(outEdges, outVertexes);
            graph.kraskal();
            List<Edge> outEdges = graph.getOutputEdges();
            List<Ellipse2D> vertexes = this.graph.getVertexes();
            List<Line2D> lines2D = new LinkedList<>();
            for (Edge edge : outEdges) {
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

            this.graph.resultEdges = new LinkedList<>(lines2D);
            this.graph.repaint();
        } else if (actionEvent.getSource() == clear) {
            graph.clearScene();
            outEdges.clear();
            outVertexes.clear();
            vertex = 'a';
        }
    }
}
