import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import java.awt.Color; 
import java.awt.Graphics2D; 
import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException; 
import java.util.Hashtable; 
import javax.imageio.ImageIO; 
import javax.swing.JFileChooser; 
import com.google.zxing.BarcodeFormat; 
import com.google.zxing.EncodeHintType; 
import com.google.zxing.WriterException; 
import com.google.zxing.common.BitMatrix; 
import com.google.zxing.qrcode.QRCodeWriter; 
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel; 

import com.google.zxing.datamatrix.encoder.*; 
import com.google.zxing.common.detector.*; 
import com.google.zxing.oned.rss.*; 
import com.google.zxing.oned.rss.expanded.decoders.*; 
import com.google.zxing.*; 
import com.google.zxing.client.result.*; 
import com.google.zxing.pdf417.detector.*; 
import com.google.zxing.qrcode.detector.*; 
import com.google.zxing.maxicode.*; 
import com.google.zxing.pdf417.decoder.ec.*; 
import com.google.zxing.pdf417.*; 
import com.google.zxing.datamatrix.*; 
import com.google.zxing.aztec.*; 
import com.google.zxing.pdf417.decoder.*; 
import com.google.zxing.maxicode.decoder.*; 
import com.google.zxing.oned.rss.expanded.*; 
import com.google.zxing.oned.*; 
import com.google.zxing.common.*; 
import com.google.zxing.pdf417.encoder.*; 
import com.google.zxing.common.reedsolomon.*; 
import com.google.zxing.multi.qrcode.*; 
import com.google.zxing.multi.*; 
import com.google.zxing.qrcode.*; 
import com.google.zxing.aztec.decoder.*; 
import com.google.zxing.qrcode.decoder.*; 
import com.google.zxing.aztec.detector.*; 
import com.google.zxing.aztec.encoder.*; 
import com.google.zxing.multi.qrcode.detector.*; 
import com.google.zxing.qrcode.encoder.*; 
import com.google.zxing.datamatrix.decoder.*; 
import controlP5.*; 
import com.google.zxing.datamatrix.detector.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class QRGen extends PApplet {

/**
 * ControlP5 Textfield
 *
 *
 * find a list of public methods available for the Textfield Controller
 * at the bottom of this sketch.
 *
 * by Andreas Schlegel, 2012
 * www.sojamo.de/libraries/controlp5
 *
 */




















ControlP5 cp5;
Textarea myTextarea;

String textValue = "";

PImage QRImg;

public void setup() {
  size(500, 680);

  PFont font = createFont("arial", 20);

  cp5 = new ControlP5(this);

  cp5.addTextfield("input")
    .setPosition(50, 70)
      .setSize(400, 30)
        .setFont(font)
          .setFocus(true)
            .setColor(color(255, 0, 0))
              ;      

  cp5.addButton("Save")
    .setPosition(120, 110)
      .setSize(100, 50)
        ;

  cp5.addButton("Exit")
    .setPosition(310, 110)
      .setSize(100, 50);

  textFont(font);
}

public void draw() {
  background(0);
  fill(255);
  PFont title = createFont("arial", 50);
  textFont(title);
  text("<INSERT NAME>", 50, 60);
  String string = cp5.get(Textfield.class, "input").getText();
  QRImg = imageText("Input text to create a code");
  if (string.length() > 0) {
    try {
      QRImg = transform(createQRImage(string, 500));
    }
    catch (WriterException e) {
    }
  }
  image(QRImg, 0, 180);
  // text(cp5.get(Textfield.class,"input").getText(), 500,130);
  //textAlign(LEFT,TOP);
}

// public void input(String theText) {
//   // automatically receives results from controller input
//   //println("a textfield event for controller 'input' : "+theText);
// }

// class Listener implements ControlListener {
//   void controlEvent(ControlEvent theEvent) {
//     println(theEvent);
//   }
// }

public void Save() {
  JFileChooser chooser = new JFileChooser();
  chooser.setFileFilter(chooser.getAcceptAllFileFilter());
  int retVal = chooser.showOpenDialog(null);
  if (retVal == JFileChooser.APPROVE_OPTION) {
    println("You chose: " + chooser.getSelectedFile().getName());
    QRImg.save(chooser.getSelectedFile().getAbsolutePath());
  }
}

public void Exit() {
  exit();
}

public PImage imageText(String text) {
  PGraphics g = createGraphics(500, 500);
  g.beginDraw();
  g.text(text, 180, 230);
  g.endDraw();
  return g;
}

public PImage transform(PImage img) {
  //applet.size(img.width, img.height);
  PGraphics g = createGraphics(500, 500);
  g.beginDraw();
  g.noStroke();
  g.image(img, 0, 0);
  g.fill(000, 75);
  float x = 98f;
  float codeSize = 450f; // FIXME this need to be fixed, bigger QR code need bigger codesize
  //println(codeSize);
  float boxSize = 16f / 450 * codeSize;
  float rightOffset = 18f / 450 * codeSize;
  float bottomOffset = 2f / 450 * codeSize;
  // Corner
  g.rect(x, x, boxSize, boxSize);
  g.rect(x + boxSize, x, boxSize, boxSize);
  g.rect(x, x + boxSize, boxSize, boxSize);

  // top right
  g.rect(codeSize - x + rightOffset - boxSize, x, boxSize, boxSize);
  g.rect(codeSize - x + rightOffset, x, boxSize, boxSize);
  g.rect(codeSize - x + rightOffset + boxSize, x + boxSize, boxSize, boxSize);

  // bottom left
  g.rect(x, codeSize - x + bottomOffset, boxSize, boxSize);
  g.rect(x, codeSize - x + bottomOffset + boxSize, boxSize, boxSize);
  g.rect(x, codeSize - x + bottomOffset - boxSize, boxSize, boxSize);
  g.rect(x, codeSize - x + bottomOffset - 2 * boxSize, boxSize, boxSize);
  g.endDraw();
  return g;
}

private PImage createQRImage(String qrCodeText, int size) throws WriterException {
  // Create the ByteMatrix for the QR-Code that encodes the given String
  Hashtable<EncodeHintType, Object> hintMap = new Hashtable<EncodeHintType, Object>();
  hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
  hintMap.put(EncodeHintType.MARGIN, 5);
  QRCodeWriter qrCodeWriter = new QRCodeWriter();
  BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
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

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "QRGen" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
