package gui;

import data.implementations.EdgeWithCoordinates;
import data.implementations.LocationWithCoordinates;
import data.implementations.Node;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class PaintPanel extends JPanel {
    private ImageIcon imageIcon;
    private ImageIcon resizedIcon;

    private int realHeight;
    private int realWidth;

    private double xRatio = 1;
    private double yRatio = 1;

    private List<EdgeWithCoordinates> edgeWithCoordinates;
    private List<Node> nodeArray;
    private List<LocationWithCoordinates> locationArray;

    public PaintPanel() {
        this.imageIcon = new ImageIcon();
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        this.resizedIcon = imageIcon;
        realHeight = this.imageIcon.getIconHeight();
        realWidth = this.imageIcon.getIconWidth();

        setJPanelSize(realWidth,realHeight);
    }

    public void resizeImage(ImageIcon imageIcon, int scaleInPercent){

        int resizedHeight = (int)(realHeight*((double)scaleInPercent/100));
        int resizedWidth = (int)(realWidth*((double)scaleInPercent/100));

        this.resizedIcon = resizeImageIcon(imageIcon,resizedWidth, resizedHeight);

        setJPanelSize(resizedWidth, resizedHeight);

        this.xRatio = (double)resizedWidth/realWidth;
        this.yRatio = (double)resizedHeight/realHeight;

    }

    private void setJPanelSize(int width, int height){
        setPreferredSize(new Dimension(width,height));
    }

    public void setGraphData(List<EdgeWithCoordinates> edgeWithCoordinates, List<Node> nodeArray, List<LocationWithCoordinates> locationWithCoordinates){
        this.edgeWithCoordinates= edgeWithCoordinates;
        this.nodeArray = nodeArray;
        this.locationArray = locationWithCoordinates;
    }

    @Override
    public void repaint() {
        super.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        resizedIcon.paintIcon(this,graphics2D,0,0);


        try{
            for (EdgeWithCoordinates e : edgeWithCoordinates){
                int [] scaleFrom = scaleCoords(e.getFromX(),e.getFromY(),5);
                int [] scaleTo = scaleCoords(e.getToX(),e.getToY(),5);

                ShapeDrawer.drawEdge(graphics2D,scaleFrom[0],scaleFrom[1],scaleTo[0],scaleTo[1],scaleTo[2]);
            }
        }catch (NullPointerException ex){
            System.out.println("No edges to draw");
        }

        try{
            for (Node n : nodeArray){
                int [] scaled = scaleCoords(n.getX(),n.getY(),10);
                ShapeDrawer.drawNode(graphics2D,scaled[0],scaled[1],scaled[2]);
            }
        }catch (NullPointerException ex){
            System.out.println("No nodes to draw");
        }

        try {
            for (LocationWithCoordinates l : locationArray){
                int [] scaled = scaleCoords(l.getX(),l.getY(),15);
                ShapeDrawer.drawLocation(graphics2D,scaled[0],scaled[1],scaled[2]);
            }
        }catch (NullPointerException ex){
            System.out.println("No locations to draw");
        }
    }

    private int [] scaleCoords(int x, int y, int radius){

        int[] table = new int[3];
        table[0] = (int)(x*xRatio);
        table[1] = (int)(y*yRatio);
        table[2] = (int)(radius*xRatio);

        return table;
    }

    private ImageIcon resizeImageIcon(ImageIcon imageIcon, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
        graphics2D.dispose();

        return new ImageIcon(bufferedImage, imageIcon.getDescription());
    }
}
