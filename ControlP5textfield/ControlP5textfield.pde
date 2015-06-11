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


import controlP5.*;
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

ControlP5 cp5;
Textarea myTextarea;

String textValue = "";

void setup() {
  size(1000, 500);

  PFont font = createFont("arial", 20);

  cp5 = new ControlP5(this);

  cp5.addTextfield("input")
    .setPosition(10, 50)
      .setSize(400, 50)
        .setFont(font)
          .setFocus(true)
            .setColor(color(255, 0, 0))
              ;      

  cp5.addButton("Enter")
    .setValue(0)
      .setPosition(500, 50)
        .setSize(100, 50)
          ; 

  textFont(font);
}

void draw() {
  background(0);
  fill(255);
  String string = cp5.get(Textfield.class, "input").getText();
  PImage img = imageText("Input text to create a code");
  if (string.length() > 0) {
    try {
      img = transform(createQRImage(string, 500));
    } 
    catch (WriterException e) {
    }
  }
  image(img, 0, 0);
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

public PImage imageText(String text) {
  PGraphics g = createGraphics(500, 500);
  g.beginDraw();
  g.text(text, 250, 250);
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
  float x = 98;
  float codeSize = 450;
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

