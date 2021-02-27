package MYP.Proyecto2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Clase para manejar las imágenes de entrada
 */
public class ImageHandler {
    
    private double skyPixels;
    private final String FILE;

    public ImageHandler(String file){
        skyPixels = 0;
        FILE = file;
    }

    /**
     * Decide si un pixel representa cielo o no basado
     * en el umbral r/b >= 0.95
     * @param pixel El pixel a analizar
     * @return <code>true</code> si es cielo, <code>false</code> en otro caso.
     */
    private boolean isSky(int pixel){
        double red = (pixel >> 16) & 0xff;
        double blue = pixel & 0xff;
        return !(blue == 0) && !(red / blue >= 0.95);
    }

    /**
     * Calcula el indice de cobertura nubosa una vez
     * que la imagen ha sido binarizada
     * @return El indice de cobertura nubosa
     */
    public double ratio(){
        long total = 4368*2912;
        total -= (long)(total-Math.PI*Math.pow(1324, 2));
        return 1-(skyPixels/total);
    }

    /**
     * Método que regresa la imagen binarizada
     * @return La imagen binarizada
     */
    public boolean[][] binaryImage(){
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(FILE));
            BufferedImage mask = ImageIO.read(getClass().getResourceAsStream("mask.png"));
            return binarize(image, mask);
        }catch (IOException e){
            System.out.println("Error leyendo la entrada");
            System.exit(1);
        }catch (IllegalArgumentException iae){
            System.out.println("Ingresa un nombre de archivo valido o revisa que " +
                    "se encuentre en Proyecto2");
            System.exit(1);
        }
        return new boolean[][]{};
    }

    /**
     * Analiza la imagen pixel por pixel y la regresa
     * representada como una matriz de booleanos
     * @param image La imagen que será binarizada
     * @param mask La máscara a utilizar
     * @return La imagen binarizada
     */
    private boolean[][] binarize(BufferedImage image, BufferedImage mask){
            int w = image.getWidth();
            int h = image.getHeight();
            int w_mask = mask.getWidth();
            int h_mask = mask.getHeight();
            boolean[][] partition = new boolean[h_mask][w_mask];
            for (int i = 0; i < h_mask; i++) {
                for (int j = 0; j < w_mask; j++) {
                    int pixel = image.getRGB(j + (w - w_mask) / 2, i + (h - h_mask) / 2);
                    pixel = pixel & mask.getRGB(j, i);
                    if (isSky(pixel)) {
                        partition[i][j] = true;
                        skyPixels++;
                    }
                }
            }
            return partition;
    }
}
