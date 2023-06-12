package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * Camera class represents the camera through which we see the scene.
 */
public class Camera {

    /**
     * The point of view of the camera.
     */
    private final Point location;

    /**
     * vTo - The "to" direction in the camera, where the scene is.
     */
    private final Vector vTo;

    /**
     * vUp - The "up" direction in the camera.
     */
    private final Vector vUp;

    /**
     * vRight - The "right" direction in the camera.
     */
    private final Vector vRight;

    // The attributes of the view plane:
    /**
     * The width of the view plane.
     */
    private double width;

    /**
     * The height of the view plane.
     */
    private double height;

    /**
     * The distance between the p0 and the view plane (in the direction of vTo).
     */
    private double distance;

    private ImageWriter imageWriter;
    private RayTraceBase rayTracer;

    /**
     * Constructs an instance of Camera with point and to and up vectors.
     *
     * @param _pt  The point of view of the camera.
     * @param _vTo The "to" direction of the camera, where the scene is.
     * @param _vUp The "up" direction of the camera.
     */
    public Camera(Point _pt, Vector _vTo, Vector _vUp) {

        if (!(_vUp.dotProduct(_vTo) == 0))
            throw new IllegalArgumentException("vTo and vUp have to be orthogonal!!!");
        location = _pt;
        vUp = _vUp.normalize();
        vTo = _vTo.normalize();
        vRight = _vTo.crossProduct(vUp).normalize();
    }

    /**
     * get the point of view of the camera.
     *
     * @return the point of view of the camera.
     */
    @SuppressWarnings("unused")
    public Point getLocation() {
        return location;
    }

    /**
     * get the vector for the camera’s direction to the right
     *
     * @return the vector for the camera’s direction to the right
     */
    @SuppressWarnings("unused")
    public Vector getVRight() {
        return vRight;
    }

    /**
     * get the vector for the camera’s direction to front
     *
     * @return the vector for the camera’s direction to front
     */
    @SuppressWarnings("unused")
    public Vector getVTo() {
        return vTo;
    }

    /**
     * get the vector for the camera’s direction up
     *
     * @return the vector for the camera’s direction up
     */
    @SuppressWarnings("unused")
    public Vector getVUp() {
        return vUp;
    }

    /**
     * get the width of the view plane
     *
     * @return The width from the view plane.
     */
    @SuppressWarnings("unused")
    public double getWidth() {
        return width;
    }

    /**
     * get the height of the view plane
     *
     * @return The height from the view plane.
     */
    @SuppressWarnings("unused")
    public double getHeight() {
        return height;
    }

    /**
     * init the view plane by the width and height
     *
     * @param _width  The number to set as the view plane's width.
     * @param _height The number to set as the view plane's height.
     * @return The current instance (Builder pattern).
     */
    public Camera setVPSize(double _width, double _height) {
        width = _width;
        height = _height;
        return this;
    }

    /**
     * init the distance of the view plane
     *
     * @param _distance The number to set as the distance between the p0 and the view plane.
     * @return The current instance (Builder pattern).
     */
    public Camera setVPDistance(double _distance) {
        distance = _distance;
        return this;
    }

    /**
     * Creates a ray that goes through a given pixel
     *
     * @param _nX number of pixels on X axis in the view plane
     * @param _nY number of pixels on Y axis in the view plane
     * @param _j  X coordinate of the pixel
     * @param _i  Y coordinate of the pixel
     * @return The ray from the camera to the pixel
     */
    public Ray constructRay(int _nX, int _nY, int _j, int _i) {
        Point imgCenter = location.add(vTo.scale(distance));
        double rY = height / _nY, rX = width / _nX;
        double iY = -(_i - (_nY - 1d) / 2) * rY;
        double jX = (_j - (_nX - 1d) / 2) * rX;
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
    @SuppressWarnings("unused")
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    /**
     * Returns the ray tracer associated with the camera.
     *
     * @return The ray tracer
     */
    @SuppressWarnings("unused")
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
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, IMAGE_WRITER);
        if (location == null || vTo == null || vUp == null || vRight == null || width == 0 || height == 0 || distance == 0)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, CAMERA);
        if (rayTracer == null)
            throw new MissingResourceException(RESOURCE, CAMERA_CLASS, RAY_TRACER);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                castRay(nX, nY, j, i);
    }

    /**
     * the method cast a ray through a pixel and color the pixel with the color of the ray
     *
     * @param nX number of pixels on X axis in the view plane
     * @param nY number of pixels on Y axis in the view plane
     * @param j  X coordinate of the pixel
     * @param i  Y coordinate of the pixel
     */
    private void castRay(int nX, int nY, int j, int i) {
        this.imageWriter.writePixel(j, i, this.rayTracer.traceRay(this.constructRay(nX, nY, j, i)));
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

