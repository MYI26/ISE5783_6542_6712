package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private final List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable... _geometries) {
        add(_geometries);
    }

    public void add(Intersectable... _geometries) {
        this.geometries.addAll(List.of(_geometries));
    }

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


