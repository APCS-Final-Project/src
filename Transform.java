import processing.core.PVector;

public class Transform {
    private final PVector a;
    private final PVector b;
    private final float det;

    public Transform(PVector a, PVector b) {
        this.a = a.get();
        this.b = b.get();
        det = 1 / (a.x*b.y - a.y*b.x);
    }

    public PVector transform(PVector p) {
        return new PVector(
                det * (b.y*p.x - b.x*p.y),
                det * (a.x*p.y - a.y*p.x)
                );
    }

    public static void main(String[] args) {
        Transform t = new Transform(new PVector(1, 2), new PVector(-1, 7));
        System.out.println(t.transform(new PVector(4, 3)));
    }
}
