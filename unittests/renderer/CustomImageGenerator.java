package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class CustomImageGenerator {

    @Test
    public void generateCustomImage() {
        int width = 400;
        int height = 400;

        Scene scene = new Scene("Custom Image");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(width, height).setVPDistance(1000)
                .setRayTracer(new RayTracerBasic(scene));

        // Set up the scene
        scene.setAmbientLight(new AmbientLight(new Color(245, 245, 220), 0.2));

        // Create geometries
        Geometries geometries = new Geometries();

        // Add the plan
        Plane plane = (Plane) new Plane(
                new Point(0, 0, 0),
                new Vector(0, 1, 0))
                .setEmission(new Color(245, 245, 220))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(plane);

        // Add the spheres
        Sphere sphere1 = (Sphere) new Sphere(new Point(-100, 100, -80), 40.)
                .setEmission(new Color(0, 0, 255)) // Partially transparent blue
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(sphere1);

        Sphere sphere2 = (Sphere) new Sphere(new Point(0, 0, -100), 60.)
                .setEmission(new Color(0, 0, 200)) // Darker blue
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(sphere2);

        Sphere sphere3 = (Sphere) new Sphere(new Point(100, -100, -120), 80.0)
                .setEmission(new Color(0, 0, 150)) // Darkest blue
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(sphere3);

        // Add the cube
        Polygon cube = (Polygon) new Polygon(
                new Point(-70, -70, -150),
                new Point(-70, -50, -150),
                new Point(-50, -50, -150),
                new Point(-50, -70, -150))
                .setEmission(new Color(0, 255, 0)) // Partially transparent green
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(cube);

        // Set geometries
        scene.geometries = geometries;

        // Add lights
        scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(0, 0, -1)));

        // Set up the camera and render the image
        camera.setImageWriter(new ImageWriter("customImage", width, height))
                .renderImage()
                .writeToImage();
    }
}
