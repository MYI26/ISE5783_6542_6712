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
    /** find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray} in a range of distance
     * @param ray ray pointing towards the graphic object
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
     *
     */
    public final List<GeoPoint> findGeoIntersections(Ray _ray) {
        return findGeoIntersectionsHelper(_ray,Double.POSITIVE_INFINITY);
    }


    /**
     * helper of findGeoIntersections
     * @param ray ray pointing towards the graphic object
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
         * @param geometry the geometry object
         * @param point    the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return o instanceof GeoPoint geoPoint &&  geometry == geoPoint.geometry && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
