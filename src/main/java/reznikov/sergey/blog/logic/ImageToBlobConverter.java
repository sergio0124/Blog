package reznikov.sergey.blog.logic;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageToBlobConverter {

    public BufferedImage getImage(byte[] imageBytes) throws IOException {
        InputStream in = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(in);
    }

    public byte[] setImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG" /* for instance */, out);
        return out.toByteArray();
    }

    public String getStringImage(byte[] imageBytes) {
        byte[] imgBytesAsBase64 = Base64.encodeBase64(imageBytes);
        String imgDataAsBase64 = new String(imgBytesAsBase64);
        String imgAsBase64 = "data:image/png;base64," + imgDataAsBase64;
        return imgAsBase64;
    }
}
