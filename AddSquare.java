import processing.core.*;
import java.awt.Color;

public class AddSquare extends PApplet {
    PImage img, out;

    public void setup() {
      //size(500, 500);
      img = loadImage("/home/yicheng-wang/github/src/QR.png");
      img.resize(500, 500);
      out = transform(img);
      out.save("./QR_supermod.png");
    }

    public void draw() {
      //image(out, 0, 0);
      //exit();
    }

    public PImage transform(PImage img) {
      //applet.size(img.width, img.height);
      PGraphics g = createGraphics(500, 500);
      g.beginDraw();
      g.noStroke();
      g.image(img, 0, 0);
      g.fill(000, 75);
      float x = 98;
      float codeSize = 450;
      float boxSize = 16f / 450 * codeSize;
      float offset = 18f / 450 * codeSize;
      g.rect(x, x, boxSize, boxSize);
      g.rect(codeSize - x + offset, x, boxSize, boxSize);
      g.rect(x, codeSize - x, boxSize, boxSize);
      g.endDraw();
      return g;
    }


    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "AddSquare" });
    } 
}
