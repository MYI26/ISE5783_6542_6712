package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

public class Camera {
    private final Point location;
    private final Vector vTo;
    private final Vector vUp;
    private final Vector vRight;
    private double width;
    private double height;
    private double distance;
    private Vector direction;
    private ImageWriter imageWriter;
    private RayTraceBase rayTracer;


    public Camera(Point _pt, Vector _vTo, Vector _vUp) {

        if (!(_vUp.dotProduct(_vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        location = _pt;
        vUp = _vUp.normalize();
        vTo = _vTo.normalize();
        vRight = _vTo.crossProduct(vUp).normalize();
    }

    public Point getLocation() {
        return location;
    }

    public Vector getvRight() {
        return vRight;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Camera setVPSize(double _width, double _height) {
        width = _width;
        height = _height;
        return this;
    }

    public Camera setVPDistance(double _distance) {
        distance = _distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        Point imgCenter = location.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
        Point ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
        Vector ijV = ijP.subtract(location);
        return new Ray(location, ijV);
    }

    /**
     * Sets the image writer for the camera.
     *
     * @param imageWriter The image writer to set
     * @return This camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the ray tracer for the camera.
     *
     * @param rayTracer The ray tracer to set
     * @return This camera object
     */
    public Camera setRayTracer(RayTraceBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Returns the image writer associated with the camera.
     *
     * @return The image writer
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    /**
     * Returns the ray tracer associated with the camera.
     *
     * @return The ray tracer
     */
    public RayTraceBase getRayTracer() {
        return rayTracer;
    }

    private final String RESOURCE = "Renderer resource not set";
    private final String CAMERA_CLASS = "Camera";
    private final String IMAGE_WRITER = "Image writer";
    private final String CAMERA = "Camera";
    private final String RAY_TRACER = "Ray tracer";

    /**
     * Renders the image using the configured image writer and ray tracer.
     * Throws MissingResourcesException if any of the fields is null.
     * Throws UnsupportedOperationException as the implementation is not yet provided.
     *
     * @throws UnsupportedOperationException indicating that the method implementation is not yet provided
     */
    public void renderImage() {
        try {
            if (imageWriter == null)
                throw new MissingResourceException(RESOURCE, CAMERA_CLASS, IMAGE_WRITER);
            if (location == null || vTo == null || vUp == null || vRight == null || width == 0 || height == 0 || distance == 0)
                throw new MissingResourceException(RESOURCE, CAMERA_CLASS, CAMERA);
            if (rayTracer == null)
                throw new MissingResourceException(RESOURCE, CAMERA_CLASS, RAY_TRACER);
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                this.imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
    }

    private Color castRay(int nX, int nY, int j, int i) {
        return this.rayTracer.traceRay(this.constructRay(nX, nY, j, i));
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param interval grid's interval
     * @param color    grid's color
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, IMAGE_WRITER);

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE, CAMERA, IMAGE_WRITER);

        imageWriter.writeToImage();
    }
}

