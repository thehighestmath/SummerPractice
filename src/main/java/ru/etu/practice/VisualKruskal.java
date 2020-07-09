package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.LinkedList;
import java.util.List;

public class VisualKruskal extends JComponent {
    private final static float RADIUS = 40f;
    private final List<Ellipse2D> vertexes = new LinkedList<>();
    private final List<Line2D> edges = new LinkedList<>();
    public List<Line2D> resultEdges = new LinkedList<>();

    private Ellipse2D vertex = null;
    private Point2D previousPoint = new Point2D.Double();
    private Line2D edge = null;
    private List<Line2D> movableEdges = new LinkedList<>();
    private Kruskal kruskal;
    private List<Object> tuple;
    private char name;

    public VisualKruskal() {
        super();
        this.kruskal = new Kruskal();
    }

    MouseAdapter addVertex = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            for (Ellipse2D vertexTmp : vertexes) {
                if (vertexTmp.getBounds2D().contains(e.getPoint())) {
                    return;
                }
            }
            name = 'a';
            for (Node tmpChar : kruskal.getInputVertices()) {
                if (name != tmpChar.getName()) {
                    break;
                } else {
                    name++;
                }
            }

            if (name > 'z') {
                return;
            }

            kruskal.getInputVertices().add(name - 'a', new Node(name));
            List<Double> xy = getXY(e);

            vertex = new Ellipse2D.Double(
                    xy.get(0) - RADIUS / 2,
                    xy.get(1) - RADIUS / 2,
                    RADIUS,
                    RADIUS
            );
            vertexes.add(name - 'a', vertex);
            repaint();
        }
    };

    private void setEdge(Node from, Node to) {
        String result;
        boolean isFirst = true;
        do {
            if (!isFirst) {
                JOptionPane.showMessageDialog(
                        this,
                        "Нужно ввести положительно число от 1 до 99999999.\n" +
                                "Попробуйте еще раз",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            result = JOptionPane.showInputDialog(
                    this,
                    "<html><h2>Введите вес ребра");
            if (result == null) {
                clearLastEdge();
                return;
            }
            isFirst = false;
        } while (!result.matches("\\d{1,8}") || Integer.parseInt(result) == 0);

        int distance = Integer.parseInt(result);
        kruskal.getGraph().addEdge(from, to, distance);

    }

    public void addEdge(Point2D point2D) {
        Node fromVertex = null, toVertex = null;
        for (Ellipse2D vertex : vertexes) {
            if (vertex.getBounds2D().contains(point2D)) {
                toVertex = kruskal.getGraph().getInputVertices().get(vertexes.indexOf(vertex));
                if (!releasedEdge(vertex.getBounds().getCenterX(), vertex.getBounds().getCenterY())) {
                    clearLastEdge();
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

        }
        for (Ellipse2D vertexFrom : vertexes) {
            if (vertexFrom.getBounds().contains(previousPoint)) {
                fromVertex = kruskal.getGraph().getInputVertices().get(vertexes.indexOf(vertexFrom));

                break;
            }
        }
        if (fromVertex == null || toVertex == null) {
            clearLastEdge();
            JOptionPane.showMessageDialog(
                    this,
                    "Кажется, что Вы не попали в область вершины, попробуйте ещё раз",
                    "Сообщение",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (kruskal.getGraph().getInputEdges().size() > 0) {
            Edge currentEdge = new Edge(fromVertex, toVertex, 0);
            for (Edge outEdge : kruskal.getGraph().getInputEdges()) {
                if (currentEdge.equalsWay(outEdge)) {
                    clearLastEdge();
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
        setEdge(fromVertex, toVertex);
        repaint();
    }

    public void deleteComponent(Point2D e) {
        for (Ellipse2D vertex : vertexes) {
            if (vertex.contains(e)) {
                final char cr = kruskal.getInputVertices().get(vertexes.indexOf(vertex)).getName();
                kruskal.getInputVertices().remove(vertexes.indexOf(vertex));
                resultEdges.removeIf(elem -> vertex.contains(elem.getP1()) || vertex.contains(elem.getP2()));
                kruskal.getOutputEdges().removeIf(elem -> elem.from.getName() == cr || elem.to.getName() == cr);
                edges.removeIf(elem -> vertex.contains(elem.getP1()) || vertex.contains(elem.getP2()));
                kruskal.getGraph().getInputEdges().removeIf(elem -> elem.from.getName() == cr || elem.to.getName() == cr);
                kruskal.getInputEdges().removeIf(elem -> elem.from.getName() == cr || elem.to.getName() == cr);
                vertexes.remove(vertex);
                kruskal.getGraph().isModified = true;
                repaint();
                break;
            }
        }

        for (Line2D edge : edges) {
            if (edge.intersects(e.getX() - 3, e.getY() - 3, 6, 6)) {
                kruskal.getGraph().getInputEdges().remove(edges.indexOf(edge));
                edges.remove(edge);
                kruskal.getGraph().isModified = true;
                repaint();
                break;
            }
        }

        for (Line2D edge : resultEdges) {
            if (edge.intersects(e.getX() - 3, e.getY() - 3, 6, 6)) {
                resultEdges.remove(edge);
                kruskal.getGraph().isModified = true;
                repaint();
                break;
            }
        }

        repaint();
    }

    public void setMovableVertex(Point e) {
        chooseMovableVertex(null);
        for (Ellipse2D vertex : vertexes) {
            if (vertex.getBounds2D().contains(e)) {
                chooseMovableVertex(vertex);
                chooseMovableEdges();
                break;
            }
        }
    }

    public boolean setEdge(Point e) {
        for (Ellipse2D vertex : vertexes) {
            if (vertex.getBounds2D().contains(e)) {
                previousPoint = new Point2D.Double(vertex.getCenterX(), vertex.getCenterY());
                edge = new Line2D.Double(previousPoint, previousPoint);
                edges.add(edge);
                repaint();
                return true;
            }
        }
        return false;
    }

    public void continueEdge(MouseEvent e) {
        List<Double> xy = getXY(e);
        edge.setLine(edge.getP1(), new Point2D.Double(xy.get(0), xy.get(1)));
        repaint();
    }

    private boolean releasedEdge(double x, double y) {
        Point2D point2D = new Point2D.Double(x, y);
        if (point2D.equals(edge.getP1())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ввод петель не возможен.",
                    "Сообщение",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        for (Ellipse2D vertexTmp : vertexes) {
            if (vertexTmp.contains(point2D)) {
                edge.setLine(edge.getP1(), point2D);
                edge = null;
                repaint();
                return true;
            }
        }
        return false;
    }

    public void clearLastEdge() {
        edges.remove(edges.size() - 1);
        repaint();
    }

    public void checkCollision() {
        if (vertex != null) {
            for (Ellipse2D anotherVertex : vertexes) {
                if (!anotherVertex.equals(vertex) && (anotherVertex.getBounds2D().intersects(vertex.getBounds2D()) || anotherVertex.equals(vertex))) {
                    moveVertex(previousPoint);
                    break;
                }
            }
            for (Ellipse2D vertex : vertexes) {
                for (Line2D edge : edges) {
                    if (!(vertex.getBounds2D().contains(edge.getP1()) || vertex.getBounds2D().contains(edge.getP2()))) {
                        if (edge.intersects(vertex.getBounds2D())) {
                            moveVertex(previousPoint);
                            break;
                        }
                    }
                }
            }
            freeMovableVertex();
        }
    }

    private List<Double> getXY(MouseEvent event) {
        List<Double> xy = new LinkedList<>();
        double x = event.getPoint().getX();
        double y = event.getPoint().getY();

        if (x < 5 + RADIUS / 2) {
            x = 5 + RADIUS / 2;
        } else if (x > 650 - RADIUS / 2) {
            x = 650 - RADIUS / 2;
        }

        if (y < 10 + RADIUS / 2) {
            y = 10 + RADIUS / 2;
        } else if (y > 530 - RADIUS / 2) {
            y = 530 - RADIUS / 2;
        }

        xy.add(x);
        xy.add(y);

        return xy;
    }

    private void freeMovableVertex() {
        movableEdges.clear();
        vertex = null;
    }

    private void chooseMovableVertex(Ellipse2D chosenVertex) {
        vertex = chosenVertex;
        if (chosenVertex != null) {
            previousPoint.setLocation(vertex.getCenterX(), vertex.getCenterY());
        }
    }

    private void getMovableEdges(List<Line2D> edges) {
        for (Line2D line : edges) {
            if (vertex.getBounds2D().contains(line.getP1())) {
                previousPoint = line.getP1();
                line.setLine(line.getP2(), previousPoint);
                movableEdges.add(line);
            } else if (vertex.getBounds2D().contains(line.getP2())) {
                movableEdges.add(line);
            }
        }
    }

    private void chooseMovableEdges() {
        getMovableEdges(edges);
        getMovableEdges(resultEdges);
    }

    public void moveVertex(MouseEvent mouseEvent) {
        if (vertex == null)
            return;

        List<Double> xy = getXY(mouseEvent);

        double x = xy.get(0);
        double y = xy.get(1);

        for (Line2D line : movableEdges) {
            line.setLine(line.getP1(), new Point2D.Double(x, y));
        }

        vertex.setFrame(x - RADIUS / 2, y - RADIUS / 2, RADIUS, RADIUS);
        repaint();
    }

    private void moveVertex(Point2D point2D) {
        if (vertex == null)
            return;
        for (Line2D line : movableEdges) {
            line.setLine(line.getP1(), point2D);
        }
        vertex.setFrame(point2D.getX() - RADIUS / 2, point2D.getY() - RADIUS / 2, RADIUS, RADIUS);
        repaint();
    }

    public List<Object> nextStep() {
        if (kruskal.isStarted) {
            if (kruskal.getGraph().isModified) {
                    /*
                    delete result and back to start
                     */
                resultEdges.clear();
                kruskal.clearOutput();
                repaint();
                kruskal.getGraph().isModified = false;
                return nextStep();
            } else {
                    /*
                    next step
                     */
                tuple = kruskal.nextStep();
                if (tuple.get(0) == State.APPEND || tuple.get(0) == State.NEW_COMPONENT || tuple.get(0) == State.CONNECT_COMPONENTS) {
                    Edge edge = kruskal.getOutputEdges().get(kruskal.getOutputEdges().size() - 1);
                    addLine2d(edge);
                }
                if (tuple.get(0) == State.END) {
                    kruskal.getGraph().isModified = true;
                }
                repaint();
            }
        } else {
                /*
                init graph and do first step
                 */
            tuple = null;
            kruskal.isStarted = true;
            return nextStep();
        }
        return tuple;
    }

    private void clearData() {
        kruskal.clearOutput();
        kruskal.isStarted = false;
        kruskal.getGraph().isModified = false;
    }

    public String kruskal() {
        if (kruskal.getGraph().isModified) {
            clearData();
            tuple = null;
            return kruskal();
        }

        String result = kruskal.kruskal();
        tuple = kruskal.nextStep();
        List<Edge> outEdges = kruskal.getOutputEdges();
        for (Edge edge : outEdges) {
            addLine2d(edge);
        }
        kruskal.getGraph().isModified = true;
        return result;
    }

    public void clearGraph() {
        kruskal.clear();
        vertexes.clear();
        edges.clear();
        resultEdges.clear();
        repaint();
    }

    public static String getStepInfo(List<Object> tuple) {
        return Kruskal.addStepInfo(tuple);
    }

    private void addLine2d(Edge edge) {
        Node from = edge.from;
        Node to = edge.to;
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        for (Ellipse2D vertex : vertexes) {
            Rectangle2D rectangle2D = vertex.getBounds2D();
            double x = rectangle2D.getCenterX();
            double y = rectangle2D.getCenterY();
            if (from == kruskal.getGraph().getInputVertices().get(vertexes.indexOf(vertex))) {
                x1 = x;
                y1 = y;
            } else if (to == kruskal.getGraph().getInputVertices().get(vertexes.indexOf(vertex))) {
                x2 = x;
                y2 = y;
            }
        }
        Point2D pointFrom = new Point2D.Double(x1, y1);
        Point2D pointTo = new Point2D.Double(x2, y2);
        Line2D line2D = new Line2D.Double(
                pointFrom, pointTo
        );
        resultEdges.add(line2D);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.BLACK);

        // толщина линии
        g2d.setStroke(new BasicStroke(3));

        // размер шрифта
        g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 18));

        int i = 0;
        int from, to;
        Line2D loop = null;
        if (tuple != null && tuple.size() == 2 && tuple.get(0) == State.LOOP) {
            from = kruskal.getGraph().getInputVertices().indexOf(((Edge) tuple.get(1)).from);
            to = kruskal.getGraph().getInputVertices().indexOf(((Edge) tuple.get(1)).to);
            loop = new Line2D.Double(vertexes.get(to).getBounds().getCenterX(),
                    vertexes.get(to).getBounds().getCenterY(),
                    vertexes.get(from).getBounds().getCenterX(),
                    vertexes.get(from).getBounds().getCenterY());
        }

        for (Line2D edge : edges) {
            if (tuple != null && tuple.get(0) == State.LOOP &&
                    (edge.getP1().equals(loop.getP1()) && edge.getP2().equals(loop.getP2()) || edge.getP1().equals(loop.getP2()) && edge.getP2().equals(loop.getP1())))
                g2d.setPaint(Color.RED);
            else
                g2d.setPaint(Color.BLACK);
            g2d.draw(edge);
        }


        // толщина линии
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.BLUE);

        for (Line2D edge : resultEdges) {
            g2d.draw(edge);
        }

        if (tuple != null && tuple.size() == 2) {
            if (tuple.get(0) == State.NEW_COMPONENT || tuple.get(0) == State.APPEND) {
                g2d.setPaint(Color.GREEN);
                g2d.draw(resultEdges.get(resultEdges.size() - 1));
            } else if (tuple.get(0) == State.END && resultEdges.size() > 0) {
                g2d.setPaint(Color.BLUE);
                g2d.draw(resultEdges.get(resultEdges.size() - 1));
            }
        }

        // толщина линии
        g2d.setStroke(new BasicStroke(3));

        // размер шрифта
        g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 18));

        for (Ellipse2D vertex : vertexes) {
            g2d.setPaint(Color.WHITE);
            g2d.fill(vertex);
            g2d.setPaint(Color.BLACK);
            g2d.draw(vertex);
            int x = vertex.getBounds().x;
            int y = vertex.getBounds().y;

            g2d.drawString(String.valueOf(kruskal.getGraph().getInputVertices().get(vertexes.indexOf(vertex))), x + 15, y + 25);
        }

        for (Line2D edge : edges) {
            if (kruskal.getGraph().getInputEdges().size() > i) {
                Edge outEdge = kruskal.getGraph().getInputEdges().get(i++);
                g2d.setPaint(Color.RED);
                g2d.drawString(
                        String.valueOf(outEdge.distance),
                        (float) (0.5 * (edge.getX1() + edge.getX2())),
                        (float) (0.5 * (edge.getY1() + edge.getY2()))
                );
            }
        }
    }
}
