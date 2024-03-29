package renderer;

import primitives.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Image writer class combines accumulation of pixel color matrix and finally
 * producing a non-optimized jpeg image from this matrix. The class although is
 * responsible for holding image related parameters of View Plane - pixel matrix
 * size and resolution
 *
 * @author Dan
 */
public class ImageWriter {

    private int nX;
    private int nY;

    private static final String FOLDER_PATH = System.getProperty("user.dir") + "/images";

    private BufferedImage image;
    private String imageName;

    private Logger logger = Logger.getLogger("ImageWriter");

    // ***************** Constructors ********************** //

    /**
     * Image Writer constructor accepting image name and View Plane parameters,
     *
     * @param _imageName the name of jpeg file
     * @param _nX        amount of pixels by Width
     * @param _nY        amount of pixels by height
     */
    public ImageWriter(String _imageName, int _nX, int _nY) {
        this.imageName = _imageName;
        this.nX = _nX;
        this.nY = _nY;

        image = new BufferedImage(_nX, _nY, BufferedImage.TYPE_INT_RGB);
    }

    // ***************** Getters/Setters ********************** //

    /**
     * View Plane Y axis resolution
     *
     * @return the amount of vertical pixels
     */
    public int getNy() {
        return nY;
    }

    /**
     * View Plane X axis resolution
     *
     * @return the amount of horizontal pixels
     */
    public int getNx() {
        return nX;
    }

    // ***************** Operations ******************** //

    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public void writeToImage() {
        try {
            File file = new File(FOLDER_PATH + '/' + imageName + ".png");
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error", e);
            throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
        }
    }

    /**
     * The function writePixel writes a color of a specific pixel into pixel color
     * matrix
     *
     * @param _xIndex X-axis index of the pixel
     * @param _yIndex Y-axis index of the pixel
     * @param _color  final color of the pixel
     */
    public void writePixel(int _xIndex, int _yIndex, Color _color) {
        image.setRGB(_xIndex, _yIndex, _color.getColor().getRGB());
    }

}
