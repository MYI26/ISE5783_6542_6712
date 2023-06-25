/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.*;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void fiveSpheresOnPlane() {
        Camera camera = new Camera(new Point(0, 0, 5000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        //scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //

                new Sphere(new Point(-200, 100, -500), 100d).setEmission(new Color(0, 0, 255)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(0)),
                new Sphere(new Point(-100, 80, -400), 80d).setEmission(new Color(0, 30, 255)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(0).setKt(0.6)),
                new Sphere(new Point(0, 60, -180), 60d).setEmission(new Color(0, 60, 255)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Sphere(new Point(100, 40, -120), 40d).setEmission(new Color(0, 90, 255)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(0)),
                new Sphere(new Point(200, 20, -100), 20d).setEmission(new Color(0, 120, 255)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(0))
        );

        scene.geometries.add( //
                new Plane(new Point(0, 0, -500), new Vector(0, 0, 1)).setEmission(new Color(230, 30, 55)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
        );

        //scene.lights.add( //
                //new SpotLight(new Color(1000, 600, 0), new Point(-200, -200, 500), new Vector(-1, -1, -2)) //
                        //.setKl(0.0004).setKq(0.0000006));

        ImageWriter imageWriter = new ImageWriter("reflectionFiveSpheresOnPlane", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of 20 spheres with gradient from intense blue to flashy green, resting on a gray plane
     * The light source is at a 45-degree angle from the normal vector of the plane.
     */
    @Test
    public void twentySpheresOnPlane() {
        Camera camera = new Camera(new Point(0, 0, 5000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(500, 500).setVPDistance(3000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));

        // Create gradient colors from blue to green
        Color startColor = new Color(0, 0, 255); // Intense blue
        Color endColor = new Color(0, 255, 0); // Flashy green
        double colorStep = 1.0 / 20; // Divide the gradient into 20 steps

        // Create 20 spheres with gradient colors
        for (int i = 0; i < 20; i++) {
            Color sphereColor = startColor.interpolate(startColor,endColor, i * colorStep);
            double radius = 100 - i * 4; // Decrease the radius gradually
            scene.geometries.add(new Sphere(new Point(0, 0, -radius - 100), radius)
                    .setEmission(sphereColor).setMaterial(new Material()));
        }

        scene.geometries.add( //
                new Plane(new Point(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(100, 100, 100)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
        );

        scene.lights.add( //
                new PointLight(new Color(1000, 600, 0), new Point(-1000, 1000, -2000))
                        .setKl(0.0004).setKq(0.0000006));

        ImageWriter imageWriter = new ImageWriter("gradientSpheresOnPlane", 800, 800);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
