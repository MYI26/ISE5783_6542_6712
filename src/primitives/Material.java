package primitives;

/**
 * class foe the material of object represented by its shininess, diffuse and specular
 */
public class Material {
    public double SnellParameter = 1;
    /**
     *
     */
    public Double3 kT = Double3.ZERO;
    /**
     *
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
     * setter for kd
     *
     * @param _kD the diffuse
     * @return the material
     */
    public Material setKd(double _kD) {
        this.kD = new Double3(_kD);
        return this;
    }

    /**
     * setter for ks
     *
     * @param _kS the specular
     * @return the material
     */
    public Material setKs(double _kS) {
        this.kS = new Double3(_kS);
        return this;
    }

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
     * @param _kD the diffuse
     * @return the material
     */
    public Material setKd(Double3 _kD) {
        this.kD = _kD;
        return this;
    }

    /**
     * setter for ks
     *
     * @param _kS the specular
     * @return the material
     */
    public Material setKs(Double3 _kS) {
        this.kS = _kS;
        return this;
    }

    /**
     *
     * @param kT
     * @return
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     *
     * @param kR
     * @return
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     *
     * @param kT
     * @return
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     *
     * @param kR
     * @return
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}