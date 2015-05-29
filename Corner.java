import processing.core.PVector;
import java.util.Arrays;

public class Corner {
    public PVector[] vertices;
    public PVector   center;
    public PVector[] midpoints;
    public int       markerId;

    public Corner(PVector[] vertices, int markerId) {
        this.vertices = Geometry.sort(vertices);
        this.center = Geometry.center(vertices);
        this.midpoints = Geometry.midpoints(vertices);
        this.markerId = markerId;
    }

    public float distance(PVector p) {
        float d1 = Geometry.perpDist(midpoints[0], midpoints[2], p);
        float d2 = Geometry.perpDist(midpoints[1], midpoints[3], p);
        return Math.min(d1, d2);
    }
    
    public static float distanceSum(Corner[] corners, int index) {
        float sum = 0;
        for (int i = 0; i < corners.length; i++) {
            if (i != index) sum += corners[index].distance(corners[i].center);
        }
        return sum;
    }

    public static void cornerInFront(Corner[] corners) {
        int minIndex = 0;
        float min = distanceSum(corners, 0);
        for (int i = 1; i < corners.length; i++) {
            float sum = distanceSum(corners, i);
            if (sum < min) {
                minIndex = i;
                min = sum;
            }
        }
        Corner tmp = corners[0];
        corners[0] = corners[minIndex];
        corners[minIndex] = tmp;
    }


    public String toString() {
        return ""+vertices[0];
    }

    public static void main(String[] args) {
        Corner[] c = new Corner[3];
        c[0] = new Corner(Geometry.square(3,0), 2);
        c[1] = new Corner(Geometry.square(0,0), 0);
        c[2] = new Corner(Geometry.square(0,3), 1);
        System.out.println(Arrays.toString(c[1].vertices));
        System.out.println(c[1].distance(c[0].center));
        System.out.println(c[1].distance(c[2].center));
        System.out.println(Arrays.toString(c));
        cornerInFront(c);
        System.out.println(Arrays.toString(c));
    }
}
