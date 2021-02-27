package MYP.Proyecto2;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;


/**
 * Clase para escribir una imagen binarizada
 */
public class ImageWriter{
    private final boolean[][] partition;

    public ImageWriter(boolean[][] partition){
        this.partition = partition;
    }

    /**
     * Genera una imagen en blanco y negro a partir
     * de una matriz booleana
     * @param name El nombre del archivo
     */
    public void writePicture(String name) {
        try {
            var image = new BufferedImage(partition[0].length, partition.length, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < partition.length; i++) {
                for (int j = 0; j < partition[0].length; j++) {
                    Color newColor;
                    if(partition[i][j])
                        newColor = new Color(0, 0, 0);
                    else
                        newColor = new Color(255, 255, 255);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            File output = new File(name+"-seg.jpg");
            ImageIO.write(image, "jpg", output);
        }catch (Exception e){
            System.out.println("Ha ocurrido un error generando la imagen de salida");
            System.exit(1);
        }
    }
}
