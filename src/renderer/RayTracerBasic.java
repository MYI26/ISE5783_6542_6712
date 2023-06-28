package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A basic implementation of a ray tracer class.
 */
public class RayTracerBasic extends RayTraceBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * field helper for the refraction if snellParameter=1 same material
     * else other material
     */
    private double snellParameter = 1;

    /**
     * Constructs a new instance of ray tracer with a given scene.
     *
     * @param _scene The given scene.
     */
    public RayTracerBasic(Scene _scene) {
        super(_scene);
    }

    /**
     * It calculates the color of a given point on a given geometry, by adding the emission of the geometry to the local
     * effects of the geometry, and then adding the global effects of the geometry if the recursion level is greater than 1
     *
     * @param _geoPoint The closest point of intersection between the ray and the scene.
     * @param _ray      the ray that hit the geometry
     * @param _level    the recursion level.
     * @param _k        the ratio of the current ray's color to the color of the previous ray.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint _geoPoint, Ray _ray, int _level, Double3 _k) {
        Vector v = _ray.getDir();
        Vector n = _geoPoint.geometry.getNormal(_geoPoint.point);
        double nv = n.dotProduct(v);
        if (isZero(nv))
            return Color.BLACK;

        Color color = calcLocalEffects(_geoPoint, v, n, nv);
        return 1 == _level ? color : color.add(calcGlobalEffects(_geoPoint, v, n, nv, _level, _k));
    }

    /**
     * > The function calculates the color of a given point on a given ray, by calculating the color of the point on the
     * ray, and adding the ambient light to it
     *
     * @param _gp  The point on the surface of the object that we're calculating the color for.
     * @param _ray the ray that hit the object
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint _gp, Ray _ray) {
        return calcColor(_gp, _ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());

    }

    /**
     * calculated light contribution from all light sources
     *
     * @param _gp the geo point we calculate the color of
     * @param _v  the direction of the ray that caused the intersection
     * @param _n  the normal to the geometry surface at the intersection point
     * @param _nv dot-product of (n,v)
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint _gp, Vector _v, Vector _n, double _nv) {
        Color color = _gp.geometry.getEmission();
        Material material = _gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(_gp.point);
            double nl = _n.dotProduct(l);
            if (nl * _nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(_gp.point).scale(transparency(_gp, lightSource, l, _n));
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, _n, l, nl, _v)));
            }
        }
        return color;
    }

    @Override
    public Color traceRay(Ray _ray) {
        GeoPoint geoPoint = scene.geometries.findClosestIntersection(_ray);
        return geoPoint == null ? scene.background : calcColor(geoPoint, _ray);
    }

    /**
     * Checks if a point is unshaded by finding any intersections between the point and the light source.
     *
     * @param _gp The GeoPoint representing the point to check.
     * @param _l  The Vector representing the direction from the point to the light source.
     * @param _n  The Vector representing the normal at the point.
     * @param _nl The dot product between the normal vector and the direction vector from the point to the light source.
     * @return {@code true} if the point is unshaded, {@code false} otherwise.
     */
    @SuppressWarnings("unused")
    private boolean unshaded(GeoPoint _gp, Vector _l, Vector _n, double _nl, LightSource _light) {
        // Compute the direction from the point to the light source
        Vector lightDirection = _l.scale(-1);
        // Create  ray from  offset point to  light source
        Ray lightRay = new Ray(_gp.point, lightDirection, _n);

        // Find  intersections between  ray and  geometries
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, _light.getDistance(_gp.point));
        // Check if the intersections list is empty
        return intersections == null;
    }

    /**
     * This function calculate the reflected ray by several given parameters.
     *
     * @param _gp A given GeoPoint to calculate the reflected ray on its point.
     * @param _v  A given vector to calculate the reflected vector with it.
     * @param _n  The normal to the geometry.
     * @return The reflected vector.
     */
    Ray constructReflectedRay(GeoPoint _gp, Vector _v, Vector _n, double _nv) {
        Vector r = _v.subtract(_n.scale(2 * (_nv)));
        return new Ray(_gp.point, r, _n);
    }

    /***
     * This function calculate the refracted ray by several given parameters.
     * @param _gp A given GeoPoint to calculate the refracted ray on its point.
     * @param _v A given vector to calculate the refracted vector with it.
     * @param _n The normal to the geometry.
     * @return The refracted vector.
     */
    Ray constructRefractedRay(GeoPoint _gp, Vector _v, Vector _n, double _nv) {
        Material material = _gp.geometry.getMaterial();
        // Without snell low:
        if (snellParameter == material.snellParameter)
            return new Ray(_gp.point, _v, _n);

        // With snell low:
        double Theta1 = Math.acos(-_nv);
        double SinTheta2 = Math.sin(Theta1) * snellParameter / material.snellParameter;
        // There's a full reflected.
        if (SinTheta2 * SinTheta2 > 1 || isZero(SinTheta2 * SinTheta2 - 1)) {
            snellParameter = material.snellParameter;
            return constructReflectedRay(_gp, _v, _n, _nv);
        }
        double Theta2 = Math.asin(SinTheta2);
        Vector nvn = _n.crossProduct(_v).crossProduct(_n).normalize();
        Vector r = _n.scale(-Math.cos(Theta2)).add(nvn.scale(SinTheta2));
        snellParameter = material.snellParameter;
        return new Ray(_gp.point, r, _n);
    }

    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param _gp    the intersection point
     * @param _v     the direction of the ray that caused the intersection
     * @param _n     the normal to the geometry surface at the intersection point
     * @param _nv    dot-product of (n,v)
     * @param _level the number of the recursive calls
     *               to calculate the next reflections and
     *               refractions
     * @param _k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */
    private Color calcGlobalEffects(GeoPoint _gp, Vector _v, Vector _n, double _nv, int _level, Double3 _k) {
        Material material = _gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(_gp, _v, _n, _nv), _level, _k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(_gp, _v, _n, _nv), _level, _k, material.kT));
    }

    /**
     * It calculates the color of the reflection or the transparency ray
     *
     * @param _ray   the ray of reflection or transparency
     * @param _level the recursion level.
     * @param _k     aggregated global attenuation factor
     * @param _kx    transparency or reflection attenuation factor
     * @return The color of the ray
     */
    private Color calcGlobalEffect(Ray _ray, int _level, Double3 _k, Double3 _kx) {
        Double3 kkx = _k.product(_kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        GeoPoint gp = scene.geometries.findClosestIntersection(_ray);
        return (gp == null ? scene.background : calcColor(gp, _ray, _level - 1, kkx)).scale(_kx);
    }

    /***
     * This function calculate the strength of the shadow in a certain point.
     * @param _gp The point to calculate the strength of the shadow at.
     * @param _ls The light source which we calculate the shadow for.
     * @param _l A vector from the camera to the point.
     * @param _n A normal vector to the geometry's surface at the point gp.
     * @return The shadow's strength.
     */
    private Double3 transparency(GeoPoint _gp, LightSource _ls, Vector _l, Vector _n) {
        Ray ray = new Ray(_gp.point, _l.scale(-1), _n);
        List<GeoPoint> list = scene.geometries.findGeoIntersections(ray, _ls.getDistance(_gp.point));
        if (list == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        for (GeoPoint GP1 : list) {
            ktr = ktr.product(GP1.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;
    }

    /**
     * "Calculate the diffusive component of the light intensity in calcLocalEffect by multiplying the intensity of the light by the diffusive
     * coefficient of the material and the dot product of the light vector and the normal vector."
     * <p>
     * The function takes in the diffusive coefficient of the material, the light vector, the normal vector, and the
     * intensity of the light in calcLocalEffect. It returns the diffusive component of the light intensity
     *
     * @param _material content the diffuse coefficient of the material.
     * @param _nl       the vector from the point on the surface to the light source scaled by the normal vector of the surface
     * @return The color of the diffuse reflection.
     */
    Double3 calcDiffusive(Material _material, double _nl) {
        return _material.kD.scale(_nl < 0 ? -_nl : _nl);
    }

    /**
     * "Calculate the specular component of the light's contribution to the color of the surface at the given point,  in calcLocalEffects ."
     * <p>
     * The function use the following parameters:
     * <p>
     * * kS: The specular reflectivity of the surface.
     * * l: The direction of the light.
     * * n: The normal of the surface at the point.
     * * v: The direction of the viewer.
     * * nShininess: The shininess of the surface.
     * * intensity: The intensity of the light
     *
     * @param _l        the vector from the point to the light source
     * @param _n        normal vector
     * @param _v        the vector from the point to the camera
     * @param _material content the shininess and the specular coefficient of the material.
     * @return The color of the point on the surface of the object.
     */
    Double3 calcSpecular(Material _material, Vector _n, Vector _l, double _nl, Vector _v) {
        Vector r = _l.subtract(_n.scale(2 * _nl));
        double vr = alignZero(_v.dotProduct(r));
        return vr >= 0 ? Double3.ZERO : _material.kS.scale(Math.pow(-vr, _material.nShininess));
    }
}