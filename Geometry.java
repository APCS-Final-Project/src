import processing.core.PVector;
import java.util.Arrays;

public abstract class Geometry {
    public static float perpDist(PVector a, PVector b, PVector p) {
        float dy = b.y - a.y;
        float dx = b.x - a.x;
        return Math.abs(dy*p.x - dx*p.y + b.x*a.y - b.y*a.x) / (float) Math.sqrt(dy*dy + dx*dx);
    }

    public static PVector midpoint(PVector a, PVector b) {
        return new PVector((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    public static PVector[] square(int x, int y) {
        PVector[] out = {new PVector(x, y), new PVector(x+1,y), new PVector(x+1, y+1), new PVector(x, y+1)};
        return out;
    }

    public static PVector center(PVector[] vertices) {
        float x = 0, y = 0;
        for (PVector v : vertices) {
            x += v.x;
            y += v.y;
        }
        return new PVector( x/vertices.length, y/vertices.length);
    }

    public static PVector[] midpoints(PVector[] vertices) {
        PVector[] out = new PVector[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            out[i] = Geometry.midpoint(vertices[i], vertices[(i+1)%vertices.length]);
        }
        return out;
    }

    public static PVector[] sort(PVector[] in) {
        PVector[] vertices = Arrays.copyOf(in, in.length);
        PVector o = center(vertices);
        for (int focus = 1; focus < vertices.length; focus++) {
            PVector toIns = vertices[focus];
            int i;
            for (i = focus-1; i >= 0 && angle(o, toIns) < angle(o, vertices[i]); i--) {
                vertices[i+1] = vertices[i];
            }
            vertices[i+1] = toIns;
        }
        return vertices;
    }

    public static float angle(PVector o, PVector p) {
        double x = (double) p.x - o.x;
        double y = (double) p.y - o.y;
        float angle = (float) Math.atan2(y,x);
        if (angle < 0) angle += 2*Math.PI;
        return angle;
    }

    public static void main(String[] args) {
        System.out.println(perpDist(new PVector(0, 0), new PVector(1, 1), new PVector(7,2))); // 3.53553
        System.out.println(perpDist(new PVector(1, ((float)-5)/3), new PVector(3, 1), new PVector(1,3))); // 2.8

        System.out.println(midpoint(new PVector(18, 45), new PVector(91, 41))); // (54.5, 43)

        PVector[] a = {new PVector(5,1), new PVector(2,1), new PVector(5,7), new PVector(32,4), new PVector(5,10)};
        System.out.println(center(a)); // (9.8, 4,6)

        System.out.println(Arrays.toString(midpoints(square(0,0))));
        System.out.println(Arrays.toString(sort(a))); // (5,1) => 11.3; (2,1) => 26; (5,7) => 54; (32, 4) => 7; (5, 10) => 63
        // (32,4), (5,1), (2,1), (5,7), (5,10)
    }
}
