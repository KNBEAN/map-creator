package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ShapeDrawer {

    public static void drawEdge(Graphics2D g2, int x1, int y1, int x2, int y2, int lineStrokeWidth) {

        AffineTransform defaultTransform = g2.getTransform();

        int arrowSize = (int)Math.ceil(lineStrokeWidth * (4.0/3.0));


        double lineLong = Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));

        int xToArrow = (int)(((lineLong - (lineStrokeWidth * 2 * Math.sqrt(3)/2))*(x2 - x1))/lineLong) + x1;
        int yToArrow = (int)(((lineLong - (lineStrokeWidth * 2 * Math.sqrt(3)/2))*(y2 - y1))/lineLong) + y1;

        g2.setStroke(new BasicStroke(lineStrokeWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.drawLine(x1,y1,xToArrow,yToArrow);

        double angle = Math.atan2(y2 - y1, x2 - x1);
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
    public static void drawLocation(Graphics2D g2,int x, int y, int radius){

        g2.setColor(Color.darkGray);
        Polygon pin = new Polygon();
        pin.addPoint(x,y);
        pin.addPoint((int)Math.ceil(x + (Math.sqrt(3)/2)*radius),(int) (y - (1.5 * radius)));
        pin.addPoint((int)Math.ceil(x - (Math.sqrt(3)/2)*radius),(int) (y - (1.5 * radius)));

        g2.fill(pin);

        g2.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.fillOval(x - radius ,y - 3 * radius ,radius * 2,radius * 2);

        g2.setColor(Color.WHITE);
        g2.fillOval(x - radius/2,(int)(y - 2.5 * radius),radius,radius);

        g2.setColor(Color.BLACK);
    }

}
