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

        //System.out.println("All 3 detected");
        if (cornerCount >= 3) {
            PImage img = nya.pickupMarkerImage(0,
                                               130, 50,
                                               -40, 50,
                                               -40, -120,
                                               130, -120,
                                               500, 500);

            image(img, 0, 0, 300, 300);
        }
    }

    private static PVector center(PVector[] vs) {
        PVector out = new PVector(0, 0);
        for (PVector v : vs) out.add(v);
        out.div(vs.length);
        return out;
    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Driver" });
    }
}
