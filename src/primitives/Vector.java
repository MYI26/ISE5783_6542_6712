package primitives;

public class Vector extends Point{

public Vector(Double3 _xys){ super(_xyz); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.xyz.equals(other.xyz);
        return false;
    }

}
