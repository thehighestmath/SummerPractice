package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

public class MyJComponent extends JComponent {
    private final static float RADIUS = 40f;
    private final List<Ellipse2D> vertexes = new LinkedList<>();
    private final List<Line2D> edges = new LinkedList<>();
    private Ellipse2D vertex = null;
    private Line2D edge = null;
    private static char name = 'a';

    public List<Ellipse2D> getVertexes() {
        return vertexes;
    }

    public List<Line2D> getEdges() {
        return edges;
    }

    MouseAdapter addVertex = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (name > 'z') {
                return;
            }
            vertex = new Ellipse2D.Double(
                    e.getPoint().getX() - RADIUS / 2,
                    e.getPoint().getY() - RADIUS / 2,
                    RADIUS,
                    RADIUS
            );
            vertexes.add(vertex);
            repaint();
            name++;
        }
    };

    MouseAdapter addEdge = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            edge = new Line2D.Double(e.getPoint(), e.getPoint());
            edges.add(edge);
            repaint();
        }

        public void mouseDragged(MouseEvent e) {
            edge.setLine(edge.getP1(), e.getPoint());
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            edge = null;
            repaint();
        }


    };

    public void clearLastEdge(){
        edges.remove(edges.size() - 1);
        repaint();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.BLACK);

        // толщина линии
        g2d.setStroke(new BasicStroke(3));

        // размер шрифта
        g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 18));

        for (Shape shape : edges) {
            g2d.draw(shape);
        }

        char name = 'a';
        for (Shape shape : vertexes) {
            g2d.draw(shape);
            int x = shape.getBounds().x;
            int y = shape.getBounds().y;

            g2d.drawString(String.valueOf(name++), x + 15, y + 25);
        }


    }
}
