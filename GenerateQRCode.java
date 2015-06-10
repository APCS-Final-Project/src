import processing.core.*;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
 
import javax.imageio.ImageIO;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class GenerateQRCode extends PApplet {
    static PImage img, out;
    static String text;

    public void setup() {
      //size(500, 500);
      //img = loadImage("/home/yicheng-wang/github/src/QR.png");
      img.resize(500, 500);
      out = transform(img);
      out.save("./QR_supermod.png");
      exit();
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

    private static PImage createQRImage(String qrCodeText, int size) throws WriterException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable<EncodeHintType,Object> hintMap = new Hashtable<EncodeHintType,Object>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 5);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
                BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
                BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
 
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);
 
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // Convert to PImage -- yeech
        PImage out = new PImage(image.getWidth(), image.getHeight(), PConstants.ARGB);
        image.getRGB(0, 0, out.width, out.height, out.pixels, 0, out.width);
        out.updatePixels();
        return out;
    }



    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter what you want to encode please: ");
        text = sc.nextLine();
        try {
            img = createQRImage(text, 500);
        } catch (WriterException e) {}
        PApplet.main(new String[] { "--present", "AddSquare" });
    } 
}
