PImage img;

void setup() {
  noStroke();
  img = loadImage("../QR.png");
  img.resize(500,500);
  size(img.width, img.height);
}

void draw() {
  image(img, 0, 0); 
  //fill(255, 51, 15);
  fill(#000000, 75);
  drawBoxes(98, 450);
  /*rect(26,26,25,25);
  rect(425,26,25,25);
  rect(26,399,25,25);*/
  save("../QR_mod.png");
  exit();
}

void drawBoxes(float x, float codeSize) {
  float boxSize = 16. / 450 * codeSize;
  float offset = 18. / 450 * codeSize;
  rect(x, x, boxSize, boxSize);
  rect(codeSize - x + offset, x, boxSize, boxSize);
  rect(x, codeSize - x, boxSize, boxSize);
}
