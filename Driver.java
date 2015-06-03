import processing.core.*;
import processing.video.*;
import jp.nyatla.nyar4psg.*;
import java.util.Arrays;

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
        //nya.addARMarker("patt.hiro",80);//id=0
        //nya.addARMarker("patt.kanji",80);//id=1
        //nya.addARMarker("/home/jake/Downloads/ARToolKit/bin/qr_corner.patt", 10);//id=0
        //for (int i = 0; i < numMarkers; i++) {
        nya.addARMarker("./patterns/corner.pat", 40);
        nya.addARMarker("./patterns/top_right.pat", 40);
        nya.addARMarker("./patterns/bottom_left.pat", 40);
        //}
        //nya.addARMarker("patt.hiro", 40);
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
            for (int i = 0; i < 3; i++) {
                nya.beginTransform(i);
                if (i == 0) {
                    fill(255, 0, 0);
                } else {
                    fill(0, 255, 0);
                }
                translate(0,0,20);
                box(40);
                nya.endTransform();
            }
        }
    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Driver" });
    }
}
