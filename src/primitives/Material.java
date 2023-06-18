package primitives;

/**
 * class foe the material of object represented by its shininess, diffuse and specular
 */
public class Material {
    public Double3 kD = new Double3(0.0);
    public Double3 kS = new Double3(0.0);
    public int nShininess = 0;

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
}