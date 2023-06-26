package primitives;

/**
 * class foe the material of object represented by its shininess, diffuse and specular
 */
public class Material {

    /**
     * represents the refractive index of the material at a point of intersection
     */
    public double snellParameter = 1;

    /**
     * transparency constant to calculate the final color that we will appear on the image
     */
    public Double3 kT = Double3.ZERO;

    /**
     * reflexion constant to calculate the final color that we will appear on the image
     */
    public Double3 kR = Double3.ZERO;

    /**
     * diffuse constant to calculate the final color that we will appear on the image
     */
    public Double3 kD = Double3.ZERO;

    /**
     * specular constant to calculate the final color that we will appear on the image
     */
    public Double3 kS = Double3.ZERO;

    /**
     * variable of shininess to calculate the correct color that will appear
     */
    public int nShininess = 1;

    /**
     * setter for shininess
     *
     * @param _nShininess the shininess
     * @return the material
     */
    public Material setShininess(int _nShininess) {
        this.nShininess = _nShininess;
        return this;
    }

    /**
     * setter for kd
     *
     * @param _kD the diffuse as Double3
     * @return the material
     */
    public Material setKd(Double3 _kD) {
        this.kD = _kD;
        return this;
    }

    /**
     * setter for kd
     *
     * @param _kD the diffuse as double
     * @return the material
     */
    public Material setKd(double _kD) {
        this.kD = new Double3(_kD);
        return this;
    }

    /**
     * setter for kS
     *
     * @param _kS the specular as Double3
     * @return the material
     */
    public Material setKs(Double3 _kS) {
        this.kS = _kS;
        return this;
    }

    /**
     * setter for kS
     *
     * @param _kS the specular as double
     * @return the material
     */
    public Material setKs(double _kS) {
        this.kS = new Double3(_kS);
        return this;
    }

    /**
     * setter for kr
     *
     * @param _kR the reflexion as Double3
     * @return the material
     */
    public Material setKr(Double3 _kR) {
        this.kR = _kR;
        return this;
    }

    /**
     * setter for kr
     *
     * @param _kR the reflexion as double
     * @return the material
     */
    public Material setKr(double _kR) {
        this.kR = new Double3(_kR);
        return this;
    }

    /**
     * setter for kt
     *
     * @param _kT the transparency as Double3
     * @return the material
     */
    public Material setKt(Double3 _kT) {
        this.kT = _kT;
        return this;
    }

    /**
     * setter for kt
     *
     * @param _kT the transparency as double
     * @return the material
     */
    public Material setKt(double _kT) {
        this.kT = new Double3(_kT);
        return this;
    }

}