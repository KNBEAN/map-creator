package gui;

import data.implementations.EdgeWithCoordinates;
import data.implementations.LocationWithCoordinates;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ShapeDrawer {

    public static void drawEdge(Graphics2D g2, EdgeWithCoordinates edge, int lineStrokeWidth) {

        AffineTransform defaultTransform = g2.getTransform();

        int arrowSize = (int)Math.ceil(lineStrokeWidth * (4.0/3.0));


        double lineLong = Math.sqrt(Math.pow(edge.getToX()-edge.getFromX(),2) + Math.pow(edge.getToY()-edge.getFromY(),2));

        int xToArrow = (int)(((lineLong - (lineStrokeWidth * 2 * Math.sqrt(3)/2))*(edge.getToX() - edge.getFromX()))/lineLong) + edge.getFromX();
        int yToArrow = (int)(((lineLong - (lineStrokeWidth * 2 * Math.sqrt(3)/2))*(edge.getToY() - edge.getFromY()))/lineLong) + edge.getFromY();

        g2.setStroke(new BasicStroke(lineStrokeWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.drawLine(edge.getFromX(),edge.getFromY(),xToArrow,yToArrow);

        double angle = Math.atan2(edge.getToY() - edge.getFromY(), edge.getToX() - edge.getFromX());
        AffineTransform transform = g2.getTransform();
        transform.translate(xToArrow, yToArrow);
        transform.rotate(angle - Math.PI/2);
        g2.setTransform(transform);


        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, arrowSize);
        arrowHead.addPoint(-arrowSize, -(int)(arrowSize/Math.sqrt(3)));
        arrowHead.addPoint(arrowSize, -(int)(arrowSize/Math.sqrt(3)));
        g2.fill(arrowHead);

        g2.setTransform(defaultTransform);
    }

    public static void drawNode(Graphics2D g2, int x, int y,int radius){
        g2.fillOval(x - (int)((radius*1.5)/2),y - (int)((radius*1.5)/2),(int)(radius*1.5),(int)(radius*1.5));
    }
    public static void drawLocation(Graphics2D g2, LocationWithCoordinates location, int radius){

        g2.setColor(Color.darkGray);
        Polygon pin = new Polygon();
        pin.addPoint(location.getX(),location.getY());
        pin.addPoint((int)Math.ceil(location.getX() + (Math.sqrt(3)/2)*radius),(int) (location.getY() - (1.5 * radius)));
        pin.addPoint((int)Math.ceil(location.getX() - (Math.sqrt(3)/2)*radius),(int) (location.getY() - (1.5 * radius)));

        g2.fill(pin);

        g2.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.fillOval(location.getX() - radius ,location.getY() - 3 * radius ,radius * 2,radius * 2);

        g2.setColor(Color.WHITE);
        g2.fillOval(location.getX() - radius/2,(int)(location.getY() - 2.5 * radius),radius,radius);

        g2.setColor(Color.BLACK);
    }

}
