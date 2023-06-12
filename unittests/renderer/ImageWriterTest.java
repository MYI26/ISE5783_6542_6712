package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * A class to test the image writer class.
 *
 * @author Yona And Aaron Mimoun
 */
class ImageWriterTest {

    /**
     * A test to test write to image function.
     */
    @Test
    void writeToImageTest() {
        final int width = 801;
        final int height = 501;
        final int step = 50;
        final Color color1 = new Color(255, 0, 0);
        final Color color2 = new Color(204, 255, 153);

        ImageWriter imageWriter = new ImageWriter("test", width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                imageWriter.writePixel(i, j, i % step == 0 || j % step == 0 ? color1 : color2);
        imageWriter.writeToImage();
    }
}