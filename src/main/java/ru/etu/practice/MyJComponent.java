package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

public class MyJComponent extends JComponent {
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
    private final List<Character> charVertexes;

    public MyJComponent(List<Edge> edgesList, List<Character> characterList) {
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

    MouseAdapter addVertex = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            name = 'a';
            for (char tmpChar : charVertexes) {
                if (name != tmpChar) break;
                else name++;
            }

            if (name > 'z') {
                return;
            }

            charVertexes.add(name - 'a', name);

            vertex = new Ellipse2D.Double(
                    e.getPoint().getX() - RADIUS / 2,
                    e.getPoint().getY() - RADIUS / 2,
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
        edge.setLine(edge.getP1(), e.getPoint());
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
        if (vertex != null)
            for (Ellipse2D anotherVertex : vertexes) {
                if (!anotherVertex.equals(vertex) && anotherVertex.getBounds2D().intersects(vertex.getBounds2D())) {
                    moveVertex(previousPoint);
                    break;
                }
            }
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

    public void moveVertex(Point2D mouseEvent) {
        if (vertex == null)
            return;
        for (Line2D line : movableEdges) {
            line.setLine(line.getP1(), mouseEvent);
        }
        vertex.setFrame(mouseEvent.getX() - RADIUS / 2, mouseEvent.getY() - RADIUS / 2, RADIUS, RADIUS);
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
        for (Line2D edge : edges) {
            g2d.setPaint(Color.BLACK);
            g2d.draw(edge);
            if (outEdges.size() > i) {
                Edge outEdge = outEdges.get(i++);
                Rectangle2D rectangle2D = new Rectangle2D.Double(
                        (0.5 * (edge.getX1() + edge.getX2())) - 50 / 2f,
                        (0.5 * (edge.getY1() + edge.getY2())) - 30 / 2f,
                        50, 30
                );
//                g2d.fill(rectangle2D);
//                g2d.draw(rectangle2D);
                g2d.setPaint(Color.RED);
                g2d.drawString(
                        String.valueOf(outEdge.distance),
                        (float) (0.5 * (edge.getX1() + edge.getX2())),
                        (float) (0.5 * (edge.getY1() + edge.getY2()))
                );
            }
        }

        // толщина линии
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.BLUE);

        for (Line2D edge : resultEdges) {
            g2d.draw(edge);
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
    }
}
