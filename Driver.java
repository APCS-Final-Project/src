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
        nya.addARMarker("./patterns/top_right.pat", 40);
        nya.addARMarker("./patterns/corner.pat", 40);
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
            System.out.println("YAY");
            if (cornerCount >= 3) break;
        }

        System.out.println("All 3 detected");
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
            for (int i = 0; i < 3; i++) {
                PVector[] vertices = nya.getMarkerVertex2D(i);
                // label corners in order
                /*for (int j=0; j < vertices.length; j++) {
                    PVector vertex = vertices[j];
                    rect(vertex.x, vertex.y, 10, 10);
                    text(j, vertex.x, vertex.y);
                }*/

                // draw boxes on top of markers
                nya.beginTransform(i);
                if (i == 0) {
                    fill(255, 0, 0);
                } else {
                    fill(0, 255, 0);
                }
                translate(0,0,20);
                box(40);
                drawVertex(i);
                nya.endTransform();
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
    }
}
