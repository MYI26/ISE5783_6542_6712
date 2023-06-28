package renderer;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Testing shadows and lights
 *
 * @author Dan
 */
public class ShadowsAndLights {

    private Scene scene = new Scene("Shadows and Lights");
    private Camera camera = new Camera(new Point(300, 400, 3200), new Vector(-0.1, -0.11, -1), new Vector(0, 9.090909090909090909090, -1))
            .setVPSize(200, 200).setVPDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));

    /**
     * test the test to verify if the connection between shadows and lights works well
     */
    @Test
    public void shadowsAndLights_UseAntiAliasing() {
        //scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        // Set up the scene
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(1000, 150, 0), new Vector(-1, 0, 0)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.setBackground(new Color(30, 30, 30));

        // Create geometries
        Geometries geometries = new Geometries();
        Triangle triangle1 = (Triangle) new Triangle(
                new Point(-100, 0, -100),
                new Point(0, 0, 0),
                new Point(0, 300, 0)).setEmission(new Color(100, 0, 0))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100));
        geometries.add(triangle1);

        Triangle triangle2 = (Triangle) new Triangle(
                new Point(0, 0, 0),
                new Point(0, 300, 0),
                new Point(100, 0, -100)).setEmission(new Color(100, 0, 0))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100));
        geometries.add(triangle2);

        Sphere sphere1 = (Sphere) new Sphere(new Point(100, 150, 0), 50.)
                .setEmission(new Color(0, 250, 0))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.).setShininess(30));
        geometries.add(sphere1);

        Sphere sphere = (Sphere) new Sphere(new Point(-100, 150, 0), 30.)
                .setEmission(new Color(0, 0, 250))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.).setShininess(30).setKr(0.3));
        geometries.add(sphere);

        Sphere sphere2 = (Sphere) new Sphere(new Point(0, 50, 30), 30.)
                .setEmission(new Color(YELLOW))
                .setMaterial(new Material().setKd(.5).setKs(0.5).setKt(0.).setShininess(800).setKr(0.7));
        geometries.add(sphere2);

        Sphere sphere3 = (Sphere) new Sphere(new Point(0, 300, 0), 40.)
                .setEmission(new Color(MAGENTA))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.99).setShininess(30));
        geometries.add(sphere3);

        Plane plane1 = (Plane) new Plane(new Point(0, 150, -1200), new Vector(0, 0, 1))
                .setEmission(new Color(BLACK))
                .setMaterial(new Material().setKd(.1).setKs(0.1).setKt(0.).setShininess(800).setKr(0.4));
        geometries.add(plane1);

        Sphere sphere4 = (Sphere) new Sphere(new Point(-100, 190, 1500), 70.)
                .setEmission(new Color(DARK_GRAY))
                .setMaterial(new Material().setKd(0.1).setKs(0.8).setKt(0.).setShininess(900));
        geometries.add(sphere4);

        Sphere sphere5 = (Sphere) new Sphere(new Point(-100, 90, 1000), 70.)
                .setEmission(new Color(0, 78, 120))
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.).setShininess(300));
        geometries.add(sphere5);

        Plane plane2 = (Plane) new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                .setEmission(new Color(0, 34, 43))
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.).setShininess(200).setKr(0.4));
        geometries.add(plane2);

        scene.geometries = geometries;

        // Add lights
        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(0, 0, -1)));

        // Set up the camera and render the image
        camera.setImageWriter(new ImageWriter("shadowsAndLights_UseAntiAliasing", 400, 400))
                .setUseAntiAliasing(true)
                .setAliasingRays(10)
                .setMultithreading(4)
                .renderImage()
                .writeToImage();
    }

    /**
     * test the test to verify if the connection between shadows and lights works well
     */
    @Test
    public void shadowsAndLights_NoUseAntiAliasing() {
        //scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        // Set up the scene
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(1000, 150, 0), new Vector(-1, 0, 0)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.setBackground(new Color(30, 30, 30));

        // Create geometries
        Geometries geometries = new Geometries();
        Triangle triangle1 = (Triangle) new Triangle(
                new Point(-100, 0, -100),
                new Point(0, 0, 0),
                new Point(0, 300, 0)).setEmission(new Color(100, 0, 0))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100));
        geometries.add(triangle1);

        Triangle triangle2 = (Triangle) new Triangle(
                new Point(0, 0, 0),
                new Point(0, 300, 0),
                new Point(100, 0, -100)).setEmission(new Color(100, 0, 0))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100));
        geometries.add(triangle2);

        Sphere sphere1 = (Sphere) new Sphere(new Point(100, 150, 0), 50.)
                .setEmission(new Color(0, 250, 0))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.).setShininess(30));
        geometries.add(sphere1);

        Sphere sphere = (Sphere) new Sphere(new Point(-100, 150, 0), 30.)
                .setEmission(new Color(0, 0, 250))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.).setShininess(30).setKr(0.3));
        geometries.add(sphere);

        Sphere sphere2 = (Sphere) new Sphere(new Point(0, 50, 30), 30.)
                .setEmission(new Color(YELLOW))
                .setMaterial(new Material().setKd(.5).setKs(0.5).setKt(0.).setShininess(800).setKr(0.7));
        geometries.add(sphere2);

        Sphere sphere3 = (Sphere) new Sphere(new Point(0, 300, 0), 40.)
                .setEmission(new Color(MAGENTA))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.99).setShininess(30));
        geometries.add(sphere3);

        Plane plane1 = (Plane) new Plane(new Point(0, 150, -1200), new Vector(0, 0, 1))
                .setEmission(new Color(BLACK))
                .setMaterial(new Material().setKd(.1).setKs(0.1).setKt(0.).setShininess(800).setKr(0.4));
        geometries.add(plane1);

        Sphere sphere4 = (Sphere) new Sphere(new Point(-100, 190, 1500), 70.)
                .setEmission(new Color(DARK_GRAY))
                .setMaterial(new Material().setKd(0.1).setKs(0.8).setKt(0.).setShininess(900));
        geometries.add(sphere4);

        Sphere sphere5 = (Sphere) new Sphere(new Point(-100, 90, 1000), 70.)
                .setEmission(new Color(0, 78, 120))
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.).setShininess(300));
        geometries.add(sphere5);

        Plane plane2 = (Plane) new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                .setEmission(new Color(0, 34, 43))
                .setMaterial(new Material().setKd(0.3).setKs(0.3).setKt(0.).setShininess(200).setKr(0.4));
        geometries.add(plane2);

        scene.geometries = geometries;

        // Add lights
        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(0, 0, -1)));

        // Set up the camera and render the image
        camera.setImageWriter(new ImageWriter("shadowsAndLights_NoUseAntiAliasing", 400, 400))
                .renderImage()
                .setMultithreading(4)
                .writeToImage();
    }
}
