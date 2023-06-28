package renderer;

import primitives.*;
import multiThreading.threadPool;

import java.util.List;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static primitives.Util.*;

import static primitives.Util.isZero;

/**
 * This class represents a camera in 3D space.
 */
public class Camera {
    private Point p0;
    private Vector vTo, vUp, vRight;
    private double height, width, distance;
    private ImageWriter imageWriter;
    private RayTraceBase rayTracer;
    private boolean useAntiAliasing = false;
    private int numOfRays = 9;       // cast 81 rays in real
    // private boolean threadedRendering = false;
    private PixelManager pixelManager;

    private threadPool<Pixel> threadPool = null;
    /**
     * Next pixel of the scene
     */
    private Pixel nextPixel = null;

    /**
     * Contractor for the camera object.
     *
     * @param p0  - Point to set the p0 of the camera.
     * @param vTo - Vector to set the vTo of the camera.
     * @param vUp - Vector to set the vUp of the camera.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vUp and vTo must be vertical");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    //region Getters
    /**
     * The function returns the point of the camera
     *
     * @return point on the camera.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * The function returns the vTo vector of the camera
     *
     * @return vTo of camera.
     */
    public Vector getVto() {
        return vTo;
    }

    /**
     * The function returns the vUp vector of the camera
     *
     * @return vUp of camera.
     */
    public Vector getVup() {
        return vUp;
    }

    /**
     * The function returns the vRight vector of the camera
     *
     * @return vRight of camera.
     */
    public Vector getVright() {
        return vRight;
    }

    /**
     * The function returns the height of the view plane.
     *
     * @return the height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * The function returns the width of the view plane.
     *
     * @return the width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * The function returns the distance between the camera and the view plane.
     *
     * @return the distance between the camera and the view plane.
     */
    public double getDistance() {
        return distance;
    }
    //endregion

    //region Setters (Builder Pattern)
    /**
     *  The function sets the size of the view plane.
     *
     * @param width  - The new width of the view plane
     * @param height - The new height of the view plane
     * @return the updated camera with the new updated values.
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     *  The function sets the distance between the camera and the view plane.
     *
     * @param distance - The new distance between the camera and the view plane
     * @return the updated camera with the new updated values.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     *  The function sets the imageWriter of the camera.
     *
     * @param imageWriter - The new imageWriter
     * @return the updated camera with the new updated values.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     *  The function sets the rayTracer of the camera.
     *
     * @param rayTracer - The new rayTracer
     * @return the updated camera with the new updated values.
     */
    public Camera setRayTracer(RayTraceBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     *  The function sets the number of aliasing rays of the camera.
     *
     * @param nRays - The new number of aliasing rays
     * @return the updated camera with the new updated values.
     */
    public Camera setAliasingRays(int nRays) {
        if (nRays < 1)
            throw new IllegalArgumentException("The number of rays must be greater then 0!");
        numOfRays = nRays;
        return this;
    }

    /**
     *  The function sets the enabling option for the anti aliasing of the camera.
     *
     * @param useAntiAliasing - The new use anti aliasing value
     * @return the updated camera with the new updated values.
     */
    public Camera setUseAntiAliasing(boolean useAntiAliasing) {
        this.useAntiAliasing = useAntiAliasing;
        return this;
    }
    //endregion

    //region Rendering methods
    /**
     * Renders the image pixel by pixel into the imageWriter
     */
    public Camera renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");

        if (rayTracer == null)
            throw new MissingResourceException("Missing tracer object!", "RayTracerBase", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        pixelManager = new PixelManager(nY, nX, 100);
        BiConsumer<Integer, Integer> writePixel = (j, i) -> {
            Color color = calcAveragePixelColor(nX, nY, j, i);
            imageWriter.writePixel(j, i, color);
            pixelManager.pixelDone();
        };

        if ( threadPool != null) {
            nextPixel = new Pixel(0, 0);
            threadPool.execute();

            printPercentMultithreaded(); // blocks the main thread until finished and prints the progress

            threadPool.join();

            IntStream.range(0, nY).parallel().forEach(i ->
                    IntStream.range(0, nX).parallel().forEach(j -> {
                        writePixel.accept(j, i);
                    }));
            return this;
        }
        else {
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    writePixel.accept(j, i);
                }
            }
        }
        return this;
    }

    /**
     * Print a grid on the image
     *
     * @param interval The width & height of a grid cell in pixels
     * @param color The color of the grid
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
            }
        }
        return this;
    }

    /**
     * Change the actual image file according to the imageWriter object
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Missing image writer object!", "ImageWriter", "");

        imageWriter.writeToImage();
    }
    //endregion

    //region Rays methods
    /** Constructs a ray for a given pixel in the view plane.
     *
     * @param nX the number of pixels in the x-axis direction of the view plane
     * @param nY the number of pixels in the y-axis direction of the view plane
     * @param j the index of the pixel on the x-axis
     * @param i the index of the pixel on the y-axis
     * @return a Ray object for the given pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));
        double rY = height / nY;
        double rX = width / nX;
        Point pIJ = pc;
        double jX = (j - (nX - 1d) / 2) * rX;
        if (!Util.isZero(jX)) {
            pIJ = pIJ.add(vRight.scale(jX));
        }
        double iY = -(i - (nY - 1d) / 2) * rY;
        if (!Util.isZero(iY)) {
            pIJ = pIJ.add(vUp.scale(iY));
        }
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0, vIJ);
    }

    /**
     * Constructs a list of rays for a given pixel coordinate.
     * If anti-aliasing is enabled, multiple rays are generated in random directions
     * within each subpixel.
     *
     * @param nX The total number of pixels along the x-axis.
     * @param nY The total number of pixels along the y-axis.
     * @param j The x-coordinate of the current pixel.
     * @param i The y-coordinate of the current pixel.
     * @return A list of Ray objects representing the rays for the specified pixel coordinate.
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        if (!useAntiAliasing)
            return List.of(constructRayThroughPixel(nX, nY, j, i));

        // Choosing the biggest scalar to scale the vectors.
        double rY = height / (2 * nY * numOfRays * 0.05 * distance),
                rX = width / (2 * nX * numOfRays * 0.05 * distance);

        List<Ray> rays = new LinkedList<>();
        // Constructing (rays * rays) rays in random directions.
        for (int k = 0; k < numOfRays; k++) {
            for (int l = 0; l < numOfRays; l++) {

                // Construct a ray to the middle of the current subpixel.
                Ray ray = constructRayThroughPixel(nX * numOfRays, nY * numOfRays, numOfRays * j + k, numOfRays * i + l);

                // Create a random direction vector.
                Vector rnd = getsRandomVector(rY, rX);

                // Create a new ray with the new random vector to the ray.
                rays.add(new Ray(ray.getPoint(), ray.getDir().add(rnd)));
            }
        }
        return rays;
    }

    /**
     * Calculates the average pixel color for a given pixel coordinate by tracing multiple rays
     * and averaging the resulting colors.
     *
     * @param nX The total number of pixels along the x-axis.
     * @param nY The total number of pixels along the y-axis.
     * @param j The y-coordinate of the current pixel.
     * @param i The x-coordinate of the current pixel.
     * @return The average Color calculated from the traced rays.
     */
    private Color calcAveragePixelColor(int nX, int nY, int j, int i) {
        List<Ray> rays = constructRays(nX, nY, j, i);
        Color color = Color.BLACK;
        for (Ray ray : rays)
            color = color.add(rayTracer.traceRay(ray));
        return color.reduce(rays.size());
    }

    /**
     * The function creates a ray from the camera to a pixel and finds the ray's color.
     *
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param j X coordinate of the pixel
     * @param i Y coordinate of the pixel
     * @return The color of the ray from the camera to the pixel
     */
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRayThroughPixel(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }
    //endregion

    /**
     *  Generates a random vector within specified ranges.
     *
     * @param min The minimum value for the vector components
     * @param max The maximum value for the vector components
     * @return A randomly generated Vector object
     */
    private Vector getsRandomVector(double min, double max) {
        boolean successeeded = false;
        Vector returned = null;
        while (!successeeded) {
            try {
                returned = vUp.scale(random(-min, min)).add(vRight.scale(random(-max, max)));
                successeeded = true;
            } catch (IllegalArgumentException ignored) { }
        }
        return returned;
    }

    /**
     * Chaining method for setting number of threads.
     * If set to 1, the render won't use the thread pool.
     * If set to greater than 1, the render will use the thread pool with the given threads.
     * If set to 0, the thread pool will pick the number of threads.
     *
     * @param threads number of threads to use
     * @return the current render
     * @throws IllegalArgumentException when threads is less than 0
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0) {
            throw new IllegalArgumentException("threads can be equals or greater to 0");
        }

        // run as single threaded without the thread pool
        if (threads == 1) {
            threadPool = null;
            return this;
        }

        threadPool = new threadPool<Pixel>() // the thread pool choose the number of threads (in0 case threads is 0)
                .setParamGetter(this::getNextPixel)
                .setTarget(this::renderImageMultithreaded);
        if (threads > 0) {
            threadPool.setNumThreads(threads);
        }

        return this;
    }

    /**
     * Returns the next pixel to draw on multithreaded rendering.
     * If finished to draw all pixels, returns {@code null}.
     */
    private synchronized Pixel getNextPixel() {

        // notifies the main thread in order to print the percent
        notifyAll();


        Pixel result = new Pixel();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // updates the row of the next pixel to draw
        // if got to the end, returns null
        if (nextPixel.col >= nX) {
            if (++nextPixel.row >= nY) {
                return null;
            }
            nextPixel.col = 0;
        }

        result.col = nextPixel.col++;
        result.row = nextPixel.row;
        return result;
    }

    /**
     * Renders a given pixel on multithreaded rendering.
     * If the given pixel is null, returns false which means kill the thread.
     *
     * @param p the pixel to render
     */
    private boolean renderImageMultithreaded(Pixel p) {
        if (p == null) {
            return false; // kill the thread
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        this.imageWriter.writePixel(p.col, p.row, castRay(nX, nY, p.col, p.row));
        return true; // continue the rendering
    }

    /**
     * Must run on the main thread.
     * Prints the percent on multithreaded rendering.
     */
    private void printPercentMultithreaded() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        int pixels = nX * nY;
        int lastPercent = -1;

        while (nextPixel.row < nY) {
            // waits until got update from the rendering threads
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            int currentPixel = nextPixel.row * nX + nextPixel.col;
            lastPercent = printPercent(currentPixel, pixels, lastPercent);
        }
    }

    /**
     * Prints the progress in percents only if it is greater than the last time printed the progress.
     *
     * @param currentPixel the index of the current pixel
     * @param pixels       the number of pixels in the image
     * @param lastPercent  the percent of the last time printed the progress
     * @return If printed the new percent, returns the new percent. Else, returns {@code lastPercent}.
     */
    private int printPercent(int currentPixel, int pixels, int lastPercent) {
        int percent = currentPixel * 100 / pixels;
        if (percent > lastPercent) {
            System.out.printf("%02d%%\n", percent);
            System.out.flush();
            return percent;
        }
        return lastPercent;
    }

    /**
     * Helper class to represent a pixel to draw in a multithreading rendering.
     */
    private static class Pixel {
        public int col, row;

        public Pixel(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public Pixel() {
        }
    }
}