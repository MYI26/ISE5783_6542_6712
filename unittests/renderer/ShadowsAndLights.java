package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class ShadowsAndLights {

    private Scene  scene  = new Scene("Shadows and Lights");
    private Camera camera = new Camera(new Point(0, 200, 2000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(200, 200).setVPDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));

    @Test
    public void shadowsAndLights() {
        // Set up the scene
        scene.lights.add( //
                new SpotLight(new Color(0, 60, 250), new Point(1000, 0, 0), new Vector(-1, 0, 0)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.setBackground(new Color(BLACK));

        // Create geometries
        Geometries geometries = new Geometries();
        Triangle triangle1=(Triangle) new Triangle(
                new Point(-100, 0, -100),
                new Point(0, 0, 0),
                new Point(0, 300, 0)).setEmission(new Color(250, 0, 0))
                .setMaterial(new Material().setKd(0).setKs(0.5).setShininess(0));
        geometries.add(triangle1);

        Triangle triangle2=(Triangle) new Triangle(
                new Point(0, 0, 0),
                new Point(0, 300, 0),
                new Point(100, 0, -100)).setEmission(new Color(250, 0, 0))
                .setMaterial(new Material().setKd(0).setKs(0.9).setShininess(100));
        geometries.add(triangle2);

        Sphere sphere1 = (Sphere) new Sphere(new Point(100, 150, 0), 50.)
                .setEmission(new Color(0, 250, 0))
                .setMaterial(new Material().setKd(0.9).setKs(0.5).setKt(0.2).setShininess(60));
        geometries.add(sphere1);

        Sphere sphere = (Sphere) new Sphere(new Point(180, 150, 0), 30.)
                .setEmission(new Color(0, 250, 250))
                .setMaterial(new Material().setKd(0.9).setKs(0.5).setKt(0.2).setShininess(60));
        geometries.add(sphere);



/*
        // Plan
        Plane plane = (Plane) new Plane(
                new Point(0, 0, -200),
                new Vector(300, 0, 300))
                .setEmission(new Color(245, 245, 50))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(plane);

        // Spheres
        Sphere sphere1 = (Sphere) new Sphere(new Point(-100, 100, -80), 40.)
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(sphere1);

        Sphere sphere2 = (Sphere) new Sphere(new Point(100, 100, -120), 60.)
                .setEmission(new Color(0, 255, 0))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(sphere2);

        Sphere sphere3 = (Sphere) new Sphere(new Point(0, -100, -100), 80.0)
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(sphere3);

        // Additional forms
        Cylinder cylinder = (Cylinder) new Cylinder(
                new Ray(new Point(-70, -70, -150), new Vector(0, 1, 0)),
                30, 100)
                .setEmission(new Color(255, 255, 0))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(cylinder);

        Triangle triangle = (Triangle) new Triangle(
                new Point(-150, 0, -150),
                new Point(-100, -100, -150),
                new Point(-50, 0, -150))
                .setEmission(new Color(255, 0, 255))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        geometries.add(triangle);*/

        scene.geometries = geometries;

        // Add lights
        scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(0, 0, -1)));

        // Set up the camera and render the image
        camera.setImageWriter(new ImageWriter("shadowsAndLights", 400, 400))
                .renderImage()
                .writeToImage();
    }
}
