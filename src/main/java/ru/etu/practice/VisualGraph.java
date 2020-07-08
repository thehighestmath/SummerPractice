package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class VisualGraph extends JComponent {
    private final static float RADIUS = 40f;
    private final List<Ellipse2D> vertexes = new LinkedList<>();
    private final List<Line2D> edges = new LinkedList<>();
    public List<Line2D> resultEdges = new LinkedList<>();

    private Ellipse2D vertex = null;
    private Point2D previousPoint = new Point2D.Double();
    private Line2D edge = null;
    private List<Line2D> movableEdges = new LinkedList<>();
    private List<Edge> outEdges;
    private List<Object> tuple;
    private char name;
    private final List<Node> charVertexes;

    public VisualGraph(List<Edge> edgesList, List<Node> characterList) {
        super();
        outEdges = edgesList;
        charVertexes = characterList;
    }

    public List<Ellipse2D> getVertexes() {
        return vertexes;
    }

    public List<Line2D> getEdges() {
        return edges;
    }

    public List<Line2D> getResultEdgesEdges() {
        return resultEdges;
    }

    public char getLastAddedVertex() {
        return name;
    }

    public void setTuple(List<Object> tuple) {
        this.tuple = tuple;
    }

    MouseAdapter addVertex = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            name = 'a';
            for (Node tmpChar : charVertexes) {
                if (name != tmpChar.getName()) break;
                else name++;
            }

            if (name > 'z') {
                return;
            }

            charVertexes.add(name - 'a', new Node(name));
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

    public void setEdge(double x, double y) {
        Point2D point2D = new Point2D.Double(x, y);
        edge = new Line2D.Double(point2D, point2D);
        edges.add(edge);
        repaint();
    }

    public void continueEdge(MouseEvent e) {
        List<Double> xy = getXY(e);
        edge.setLine(edge.getP1(), new Point2D.Double(xy.get(0), xy.get(1)));
        repaint();
    }

    public boolean releasedEdge(double x, double y) {
        Point2D point2D = new Point2D.Double(x, y);
        if (point2D.equals(edge.getP1()))
            return false;
        edge.setLine(edge.getP1(), point2D);
        edge = null;
        repaint();
        return true;
    }

    public void clearLastEdge() {
        edges.remove(edges.size() - 1);
        repaint();
    }

    public void checkCollision() {
        if (vertex != null) {
            for (Ellipse2D anotherVertex : vertexes) {
                if (!anotherVertex.equals(vertex) && anotherVertex.getBounds2D().intersects(vertex.getBounds2D())) {
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

    public void freeMovableVertex() {
        movableEdges.clear();
        vertex = null;
    }

    public void chooseMovableVertex(Ellipse2D chosenVertex) {
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

    public void chooseMovableEdges() {
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

    public void moveVertex(Point2D point2D) {
        if (vertex == null)
            return;
        for (Line2D line : movableEdges) {
            line.setLine(line.getP1(), point2D);
        }
        vertex.setFrame(point2D.getX() - RADIUS / 2, point2D.getY() - RADIUS / 2, RADIUS, RADIUS);
        repaint();
    }

    public void clearResult() {
        resultEdges.clear();
        repaint();
    }

    public void clearScene() {
        vertexes.clear();
        edges.clear();
        clearResult();
        repaint();
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
            from = charVertexes.indexOf(((Edge) tuple.get(1)).from);
            to = charVertexes.indexOf(((Edge) tuple.get(1)).to);
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

            g2d.drawString(String.valueOf(charVertexes.get(vertexes.indexOf(vertex))), x + 15, y + 25);
        }

        for (Line2D edge : edges) {
            if (outEdges.size() > i) {
                Edge outEdge = outEdges.get(i++);
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
