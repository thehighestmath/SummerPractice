package ru.etu.practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class MyJComponent extends JComponent {
    private final static float RADIUS = 40f;
    private final List<Shape> shapes = new LinkedList<>();
    private Shape currentShape = null;
    private static char name = 'a';

    public List<Shape> getShapes() {
        return shapes;
    }

//    {
//        MouseAdapter mouseAdapter = new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                if (name > 'z') {
//                    return;
//                }
//                currentShape = new Ellipse2D.Double(
//                        e.getPoint().getX() - RADIUS / 2,
//                        e.getPoint().getY() - RADIUS / 2,
//                        RADIUS,
//                        RADIUS
//                );
//                shapes.add(currentShape);
//                repaint();
//                name++;
//            }
//        };
//        addMouseListener(mouseAdapter);
//        addMouseMotionListener(mouseAdapter);
//    }

    MouseAdapter mouseAdapter = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (name > 'z') {
                return;
            }
            currentShape = new Ellipse2D.Double(
                    e.getPoint().getX() - RADIUS / 2,
                    e.getPoint().getY() - RADIUS / 2,
                    RADIUS,
                    RADIUS
            );
            shapes.add(currentShape);
            repaint();
            name++;
        }
    };

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.BLACK);

        // толщина линии
        g2d.setStroke(new BasicStroke(3));

        // размер шрифта
        g2d.setFont(new Font("TimesNewRoman", Font.BOLD, 18));

        char name = 'a';
        for (Shape shape : shapes) {
            g2d.draw(shape);
            int x = shape.getBounds().x;
            int y = shape.getBounds().y;

            g2d.drawString(String.valueOf(name++), x + 15, y + 25);
        }
    }
}
