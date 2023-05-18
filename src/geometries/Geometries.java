package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> geometries;

    public Geometries() {
        geometries = new LinkedList<>();
    }

    public Geometries(Intersectable ..._geometries) {
        this.geometries = new LinkedList<>();
        add(_geometries);
    }

    public void add(Intersectable... _geometries) {
        for (Intersectable geometry : _geometries) {
            this.geometries.add(geometry);
        }
    }

    @Override
    public List<Point> findIntersections(Ray _ray) {
        List<Point> intersections = new LinkedList<>();

        for (Intersectable geometry : geometries) {
            List<Point> geometryIntersections = geometry.findIntersections(_ray);
            if (geometryIntersections != null) {
                intersections.addAll(geometryIntersections);
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }

}


