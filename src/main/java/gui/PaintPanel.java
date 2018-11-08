package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel {
    private ImageIcon imageIcon;
    private ImageIcon resizedIcon;

    private int realHeight;
    private int realWidth;

    private double xRatio = 1;
    private double yRatio = 1;

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

    public void resizeImage(ImageIcon imageIcon, int resizeInPercent){

        int height = (int)(realHeight*((double)resizeInPercent/100));
        int width = (int)(realWidth*((double)resizeInPercent/100));

        //System.out.println("New Height: "+height+" new Width: "+ width+" resize: "+((double)resizeInPercent/100));
        this.resizedIcon = resizeImageIcon(imageIcon,width, height);

        int resizedHeight = this.resizedIcon.getIconHeight();
        int resizedWidth = this.resizedIcon.getIconWidth();

        setJPanelSize(resizedWidth, resizedHeight);

        this.xRatio = (double)resizedWidth/realWidth;
        this.yRatio = (double)resizedHeight/realHeight;

        //System.out.println("xRatio: "+xRatio+" yRatio: "+yRatio);

    }

    private void setJPanelSize(int width, int height){
        setPreferredSize(new Dimension(width,height));
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
        //sample
        int[] coordsTable = transposeCoords(546,117,20);
        graphics2D.fillOval(coordsTable[0],coordsTable[1],coordsTable[2],coordsTable[2]);

    }

    private int [] transposeCoords(int x, int y, int radius){

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
