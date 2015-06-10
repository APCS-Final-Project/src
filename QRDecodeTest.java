import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.*;
import com.google.zxing.qrcode.encoder.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

public class QRDecodeTest {
    public static void main(String[] args) throws IOException {
        /*String data = "Jake";

        //get a byte matrix for the data
        BitMatrix matrix;
        com.google.zxing.Writer writer = new QRCodeWriter();
        try {
            matrix = writer.encode(data, BarcodeFormat.QR_CODE, 1000, 1000);
        } catch (com.google.zxing.WriterException e) {
        //exit the method
            return;
        }

        //generate an image from the byte matrix
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        byte[][] array = matrix.getArray();

        //create buffered image to draw to
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //iterate through the matrix and draw the pixels to the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = array[y][x] & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }

//write the image to the output stream
        ImageIO.write(image, "png", outputStream);*/
        BufferedImage image = ImageIO.read(new File("QR_mod.png"));
        BinaryBitmap bitmap = new BinaryBitmap(
            new HybridBinarizer(
                new BufferedImageLuminanceSource(image)
            )
        );

        QRCodeReader reader = new QRCodeReader();

        Result result;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            throw new RuntimeException();
        }

        System.out.println(result.getText());
    }
}
