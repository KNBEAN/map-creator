package gui;

import data.implementations.EdgeWithCoordinates;
import data.implementations.LocationWithCoordinates;
import data.implementations.Node;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class PaintPanel extends JPanel {

    private static Color DEFAUL_COLOR = Color.BLACK;
    private ImageIcon imageIcon;
    private ImageIcon resizedIcon;

    private int realHeight;
    private int realWidth;

    private double xRatio = 1;
    private double yRatio = 1;

    private List<EdgeWithCoordinates> edgesArray;
    private List<Node> nodesArray;
    private List<LocationWithCoordinates> locationsArray;

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
        this.edgesArray = edgeWithCoordinates;
        this.nodesArray = nodeArray;
        this.locationsArray = locationWithCoordinates;
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

        //Edges
        ArrayList<EdgeWithCoordinates> edges = new ArrayList<>();
        try{
            for (int i = 0; i < edgesArray.size(); i++){

                int [] scaleFrom = scaleCoords(edgesArray.get(i).getFromX(), edgesArray.get(i).getFromY(),5);
                int [] scaleTo = scaleCoords(edgesArray.get(i).getToX(), edgesArray.get(i).getToY(),5);

                edges.add(new EdgeWithCoordinates(scaleFrom[0],scaleFrom[1],scaleTo[0],scaleTo[1], edgesArray.get(i).getLength()));

                if (edgesArray.get(i).getLength() == 1 )
                    graphics2D.setColor(Color.GREEN);
                else
                    graphics2D.setColor(DEFAUL_COLOR);

                ShapeDrawer.drawEdge(graphics2D,edges.get(i),scaleTo[2]);
            }
        }catch (NullPointerException ex){
            System.out.println("No edges to draw");
        }

        graphics2D.setColor(DEFAUL_COLOR);
        //Nodes
        try{
            for (Node n : nodesArray){
                int [] scaled = scaleCoords(n.getX(),n.getY(),10);
                ShapeDrawer.drawNode(graphics2D,scaled[0],scaled[1],scaled[2]);
            }
        }catch (NullPointerException ex){
            System.out.println("No nodes to draw");
        }

        graphics2D.setColor(DEFAUL_COLOR);
        ArrayList<LocationWithCoordinates> locations = new ArrayList<>();
        //Locations
        try {
            for (int i = 0; i < locationsArray.size(); i++){
                int [] scaled = scaleCoords(locationsArray.get(i).getX(), locationsArray.get(i).getY(),15);
                locations.add(new LocationWithCoordinates(scaled[0],scaled[1]));

                ShapeDrawer.drawLocation(graphics2D,locations.get(i),scaled[2]);
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
