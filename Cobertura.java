package MYP.Proyecto2;

/**
 * Proyecto 2 para Modelado y Programación.
 * Índice de cobertura nubosa.
 * @author Juan Manuel Diaz Quiñonez
 */

public class Cobertura {
    /**
     * Main del programa
     * Dada una imagen del cielo en el día, regresa el
     * indice de cobertura nubosa y la misma imagen segmentada
     * si así se especifica en la linea de comandos
     * @param args Nombre del archivo con o sin bandera
     */
    public static void main(String[] args){
            String file = args[0];
            ImageHandler handler = new ImageHandler(file);
            boolean[][] partition = handler.binaryImage();
            double sky = handler.ratio();
            System.out.println("Indice de cobertura nubosa: " + sky);
            if(args.length == 2 && (args[1].equals("s") || args[1].equals("S"))) {
                ImageWriter writer = new ImageWriter(partition);
                String name = args[0].substring(0, args[0].length()-4);
                writer.writePicture(name);
            }
    }
}
