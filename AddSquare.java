import processing.core.*;
import java.awt.Color;

public class AddSquare extends PApplet {
    PImage img;

    public void setup() {
        img = loadImage("./QR.png");
        img.resize(500, 500);
    }

    public void draw() {
        transform(img);
        //image(out, 0, 0);
        //exit();
    }

    public void transform(PImage img) {
        //applet.size(img.width, img.height);
        noStroke();
        image(img, 0, 0);
        fill(000, 75);
        float x = 98;
        float codeSize = 450;
        float boxSize = 16f / 450 * codeSize;
        float offset = 18f / 450 * codeSize;
        rect(x, x, boxSize, boxSize);
        rect(codeSize - x + offset, x, boxSize, boxSize);
        rect(x, codeSize - x, boxSize, boxSize);
    }

    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "AddSquare" });
    } 
}
