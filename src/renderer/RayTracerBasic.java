package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A basic implementation of a ray tracer class.
 */

public class RayTracerBasic extends RayTraceBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;


    private static final double DELTA = 0.1;
    /**
     * Constructs a new instance of ray tracer with a given scene.
     *
     * @param scene The given scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * calculate the color at a specific point
     *
     * @param geoPoint a geo point
     * @param ray a ray
     * @return the color at this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcLocalEffects(geoPoint, ray).add(scene.ambientLight.getIntensity());
    }


    /**
     * calculated light contribution from all light sources
     *
     * @param geoPoint the geo point we calculate the color of
     * @param ray      ray from the camera to the point
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;


        Material mat = geoPoint.geometry.getMaterial();
        Color color = geoPoint.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            if (l != null) {
                double nl = alignZero(n.dotProduct(l));
                if (nl * nv > 0 ){
                    if(unshaded(lightSource,geoPoint, l,n,nl)) {
                        Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                        color = color.add(calcDiffusive(mat.kD, nl, lightIntensity),
                                calcSpecular(mat.kS, l, n, nl, v, mat.nShininess, lightIntensity));
                    }
                }
            }
        }


        return color;
    }

    /**
     * Calculate the Specular component of the light at this point
     *
     * @param ks             specular component
     * @param l              direction from light to point
     * @param n              normal from the object at the point
     * @param nl             dot-product n*l
     * @param v              direction from the camera to the point
     * @param nShininess     shininess level
     * @param lightIntensity light intensity
     * @return the Specular component at the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double vr = alignZero(v.dotProduct(r));
        return vr >= 0 ? Color.BLACK : lightIntensity.scale(ks.scale(Math.pow(-vr, nShininess)));
    }

    /**
     * Calculate the diffusive component of the light at this point
     *
     * @param kd             diffusive component
     * @param nl             dot-product n*l
     * @param lightIntensity light intensity
     * @return the diffusive component at the point
     */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(nl > 0 ? nl : -nl));
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            //ray did not intersect any geometrical object
            return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }
    /**
     * Checking for shading between a point and the light source
     *
     * @param _light the light source
     * @param _gp    the peo point which is shaded or not
     * @param _l     direction from light to point
     * @param _n     normal from the object at the point
     * @param _nl    dot-product n*l
     * @return if the point is unshaded (true) or not
     */
    private boolean unshaded(LightSource _light, GeoPoint _gp, Vector _l, Vector _n, double _nl) {
        Vector lightDirection = _l.scale(-1); // from point to light source
        Vector epsVector = _n.scale(_nl < 0 ? DELTA : -1 * DELTA);
        Point point = _gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,_light.getDistance(_gp.point));
        return intersections == null;
    }
    /**
     * construct the refracted ray of the point on the geometry
     *
     * @param n     normal vector
     * @param point on the geometry
     * @return new ray
     */
    private Ray constructRefractedRay(Point point, Vector l, Vector n) {
        return new Ray(point, l, n);
    }

    /**
     * @param point point on the geometry
     * @param n     normal vector
     * @param l
     * @return new ray
     */
    private Ray constructReflectedRay(Point point, Vector n, Vector l) {

        double vn = alignZero(n.dotProduct(l));
        Vector r = l.subtract(n.scale(2 * vn).normalize());
        // move the head
        return new Ray(point, n, r);
    }
    /**
     * find the closest intersection point of the ray with the geometry
     *
     * @param ray on the geometry
     * @return the closest geo point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null || intersections.size() == 0) {
            return null;
        } else {
            return ray.findClosestGeoPoint(intersections);
        }
    }
}