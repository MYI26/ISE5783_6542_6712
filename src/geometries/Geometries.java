package geometries;

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
     * @param _geometries the geometry's shape
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


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray _ray, double _maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable item : geometries) {
            List<GeoPoint> itemResult = item.findGeoIntersectionsHelper(_ray, _maxDistance);
            if (itemResult != null) {
                if (result == null)
                    result = new LinkedList<>(itemResult);
                else
                    result.addAll(itemResult);
            }
        }
        return result;
    }

}


