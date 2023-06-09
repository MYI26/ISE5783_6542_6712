package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * this class represent a set of geometries on the principe of composite
 *
 * @author Yona and Aaron
 */
public class Geometries extends Intersectable {
    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * empty constructor
     */
    public Geometries() {
    }

    /**
     * constructor for geometries by all kinds of geometries
     *
     * @param _geometries
     */
    public Geometries(Intersectable... _geometries) {
        add(_geometries);
    }

    /**
     * add method
     *
     * @param _geometries the geometries to add to the set
     */
    public void add(Intersectable... _geometries) {
        this.geometries.addAll(List.of(_geometries));
    }

    /**
     * getting the intersection's points between the ray with the geometries
     *
     * @param _ray (Ray)
     * @return the intersection's points
     */
    @Override
    public List<Point> findIntersections(Ray _ray) {
        List<Point> intersections = null;

        for (Intersectable geometry : geometries) {
            var geometryIntersections = geometry.findIntersections(_ray);
            if (geometryIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(geometryIntersections);
            }
        }

        return intersections;
    }

}


