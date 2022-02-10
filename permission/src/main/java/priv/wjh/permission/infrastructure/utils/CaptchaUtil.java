package priv.wjh.permission.infrastructure.utils;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author wangjunhao
 */
@Component
public class CaptchaUtil {
    @Autowired
    private Producer producer;

    public String getText() {
        return this.producer.createText();
    }

    public String getCode(String text) throws IOException {
        BufferedImage image = this.producer.createImage(text);
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", tmp);
        byte[] bytes = tmp.toByteArray();
        String base64 = Base64.getEncoder().encodeToString(bytes).trim();
        base64 = base64.replaceAll("\n", "").replaceAll("\r", "");
        return base64;
    }
}

