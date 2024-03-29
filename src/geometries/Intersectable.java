package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Common abstract class for all graphic objects
 * that intersect with a {@link Ray}
 *
 * @author Yona and Aaron Mimoun
 */
public abstract class Intersectable {


    /**
     * find all intersection {@link Point}s
     * that intersect with a specific {@link Ray}
     *
     * @param _ray ray pointing towards the graphic object
     * @return list of intersection {@link Point}s
     */
    public final List<Point> findIntersections(Ray _ray) {
        var geoList = findGeoIntersections(_ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray} in a range of distance
     *
     * @param ray         ray pointing towards the graphic object
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     *
     * @param _ray ray pointing towards the graphic object
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    public final List<GeoPoint> findGeoIntersections(Ray _ray) {
        return findGeoIntersectionsHelper(_ray, Double.POSITIVE_INFINITY);
    }


    /**
     * helper of findGeoIntersections
     *
     * @param ray         ray pointing towards the graphic object
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     * geo point is the point with the geometry object that it's on
     */
    public static class GeoPoint {
        /**
         * geometry that we will focus
         */
        public Geometry geometry;
        /**
         * the point of the geometry that we will focus
         */
        public final Point point;

        /**
         * constructor for GeoPoint
         *
         * @param _geometry the geometry object
         * @param _point    the point
         */
        public GeoPoint(Geometry _geometry, Point _point) {
            this.geometry = _geometry;
            this.point = _point;
        }

        @Override
        public boolean equals(Object _o) {
            if (this == _o) return true;
            return _o instanceof GeoPoint geoPoint && geometry == geoPoint.geometry && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Find the closest point to the ray's head.
     *
     * @param _ray The ray to run the function on.
     * @return The closest point which was found by GeoPoint format (geometry, point).
     */
    public GeoPoint findClosestIntersection(Ray _ray) {
        List<GeoPoint> list = findGeoIntersections(_ray);
        return list == null ? null : _ray.findClosestGeoPoint(list);
    }
}
