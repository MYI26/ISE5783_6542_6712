package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * interface that will be used for all the intersections points.
 */
public abstract class Intersectable {

    /**
     * return a list of all the intersection point between the ray and the geometry
     *
     * @param _ray
     * @return
     */

    public List<Point> findIntersections(Ray _ray) {
        return null;
    }

    /**
     * geo point is the point with the geometry object that it's on
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

        /**
         * find all intersection points {@link Point}
         * that intersect with a specific ray{@link Ray}
         *
         * @param _ray ray pointing towards the graphic object
         * @return immutable list of intersection geo points {@link GeoPoint}
         */
        public final List<GeoPoint> findGeoIntersections(Ray _ray) {
            return findGeoIntersectionsHelper(_ray);
        }

        /**
         * help the func` findGeoIntersections that find all intersection points {@link Point}
         * that intersect with a specific ray{@link Ray}
         *
         * @param _ray ray pointing towards the graphic object
         * @return immutable list of intersection geo points {@link GeoPoint}
         */
        protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray _ray);
    }


}
