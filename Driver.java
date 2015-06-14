import processing.core.*;
import processing.video.*;
import jp.nyatla.nyar4psg.*;
import java.util.Arrays;
import java.util.ArrayList;
import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.*;
import com.google.zxing.qrcode.encoder.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;


public class Driver extends PApplet {

    int numMarkers = 3;

    Capture cam;
    MultiMarker nya;
    String resultStr = "";

    public void setup() {
        size(640, 480, P3D);
        colorMode(RGB, 100);
        println(MultiMarker.VERSION);
        cam=new Capture(this, 640, 480);
        nya=new MultiMarker(this, width, height, "camera_para.dat", NyAR4PsgConfig.CONFIG_PSG);
        //nya.addARMarker("./patterns/corner.pat", 40);
        nya.addNyIdMarker(511, 40);
        nya.addARMarker("./patterns/top_right.pat", 40);
        nya.addARMarker("./patterns/bottom_left.pat", 40);
        //nya.setConfidenceThreshold(0.7);
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
                                               -180, -50,
                                               40, -50,
                                               40, 160,
                                               -180, 160,
                                               500, 500);

            // uncomment for debug - yw
            image(img, 0, 0, 300, 300);

            // decode code
            BufferedImage QRCode = (BufferedImage)img.getNative();
            BinaryBitmap bitmap = new BinaryBitmap(
                new HybridBinarizer(
                    new BufferedImageLuminanceSource(QRCode)
                )
            );

            QRCodeReader reader = new QRCodeReader();

            Result result = null;
            try {
                result = reader.decode(bitmap);
                resultStr = result.getText();
            } catch (ReaderException e) {}

            //System.out.println(resultStr);

            // Superimpose text on marker
            nya.beginTransform(0); // Use the plane of the corner
            pushMatrix();
            //rotate(PI);
            fill(0, 102, 153); // pretty blue :D
            stroke(255, 200, 0);
            translate(40, 40, 20);
            box(130, 130, 40);
            fill(0);
            textSize(21);
            translate(0, 0, 21);
            text(resultStr, -50, -50, 130, 130);
            translate(-40, -40, -41);
            popMatrix();
            nya.endTransform();
        }
        else {
            resultStr = "";
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
