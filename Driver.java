import processing.core.*;
import processing.video.*;
import jp.nyatla.nyar4psg.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Driver extends PApplet {

    int numMarkers = 3;

    Capture cam;
    MultiMarker nya;

    public void setup() {
        size(640, 480, P3D);
        colorMode(RGB, 100);
        println(MultiMarker.VERSION);
        cam=new Capture(this, 640, 480);
        nya=new MultiMarker(this, width, height, "camera_para.dat", NyAR4PsgConfig.CONFIG_PSG);
        nya.addARMarker("./patterns/corner.pat", 40);
        nya.addARMarker("./patterns/top_right.pat", 40);
        nya.addARMarker("./patterns/bottom_left.pat", 40);
        nya.setConfidenceThreshold(0.3);
        cam.start();
    }

    public void draw() {
        if (cam.available() !=true) {
            return;
        }
        cam.read();
        nya.detect(cam);
        background(0);
        nya.drawBackground(cam);
        int cornerCount = 0;
        for (int i=0; i<numMarkers; i++) {
            if ((!nya.isExistMarker(i))) {
                continue;
            }
            cornerCount++;
            if (cornerCount >= 3) break;
        }
        if (cornerCount >= 3) {
            //println("before " + Arrays.toString(vertices));
            //println("after " + Arrays.toString(vertices));
            /*for (int j=0; j < vertices.length; j++) {
                PVector vertex = vertices[j];
                rect(vertex.x, vertex.y, 10, 10);
                text(j, vertex.x, vertex.y);
            }*/
            /*ArrayList<PVector> vertexes = new ArrayList<PVector>();
            for (int i = 0 ; i < 3 ; i++) {
                for (PVector aVertex : nya.getMarkerVertex2D(i)) {
                    vertexes.add(aVertex);
                }
            }*/
            PVector[] centers = new PVector[3];
            PVector o = center(nya.getMarkerVertex2D(0));
            for (int i = 0; i < centers.length; i++) {
                centers[i] = center(nya.getMarkerVertex2D(i));
                centers[i].sub(o);
            }
            translate(o.x, o.y);
            //System.out.println(Arrays.toString(nya.getMarkerVertex2D(0)));
            //System.out.println(Arrays.toString(nya.getMarkerVertex2D(1)));
            //System.out.println(Arrays.toString(nya.getMarkerVertex2D(2)));
            //System.out.println(Arrays.toString(centers));
            Transform t = new Transform(centers[1], centers[2]);
            line(0, 0, centers[1].x, centers[1].y);
            line(0, 0, centers[2].x, centers[2].y);
            for (int i = 0; i < 3; i++) {
                PVector[] vertices = nya.getMarkerVertex2D(i);
                for (PVector v : vertices) v.sub(o);
                square(centers[i], 10);
                // label corners in order
                for (int j=0; j < vertices.length; j++) {
                    PVector vertex = vertices[j];
                    PVector v = t.transform(vertex);
                    square(vertex, 10);
                    text(j+coords(v), vertex.x, vertex.y);
                }

                // draw boxes on top of markers
                /*nya.beginTransform(i);
                if (i == 0) {
                    fill(255, 0, 0);
                } else {
                    fill(0, 255, 0);
                }
                translate(0,0,20);
                box(40);
                drawVertex(i);
                nya.endTransform();*/
            }

            /*int[] v = new int[8];
            for (int i = 0; i < 3; i++) {
                PVector vertex = nya.getMarkerVertex2D(i)[0];
                v[2*i] = (int) vertex.x;
                v[2*i + 1] = (int) vertex.y;
            }
            v[6] = v[2];
            v[7] = v[5];
            PImage pm = nya.pickupMarkerImage(
                            0,
                            v[0],
                            v[1],
                            v[2],
                            v[3],
                            v[4],
                            v[5],
                            v[6],
                            v[7],
                            500,
                            500
                        );*/
        }
    }

    private void square(PVector center, int size) {
        rect(center.x - size/2f, center.y - size/2f, size, size);
    }

    private static String coords(PVector v) {
        char x,y;
        if (v.x > 0) x = '+';
        else if (v.x < 0) x = '-';
        else x = '0';
        if (v.y > 0) y = '+';
        else if (v.y < 0) y = '-';
        else y = '0';
        return "<"+x+","+y+">";
    }

    private static PVector center(PVector[] vs) {
        PVector out = new PVector(0, 0);
        for (PVector v : vs) out.add(v);
        out.div(vs.length);
        return out;
    }

    public void drawVertex(int id) {
        PFont font = createFont("FFScala", 32);
        PVector[] i_v=nya.getMarkerVertex2D(id);
        textFont(font,10f);
        stroke(100,0,0);
        for(int i=0; i<4; i++) {
            fill(100,0,0);
            ellipse(i_v[i].x,i_v[i].y,6,6);
            fill(0,0,0);
            text(i + ", ("+i_v[i].x+","+i_v[i].y+")",i_v[i].x,i_v[i].y);
        }
    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Driver" });
        /*PVector[] vs = new PVector[4];
        vs[2] = new PVector(1, 1);
        vs[0] = new PVector(1, 2);
        vs[1] = new PVector(2, 1);
        vs[3] = new PVector(2, 2);
        System.out.println(center(vs));*/
    }
}
