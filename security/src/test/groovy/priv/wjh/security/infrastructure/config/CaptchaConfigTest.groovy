package priv.wjh.security.infrastructure.config

import priv.wjh.security.infrastructure.config.CaptchaConfig

import javax.imageio.ImageIO
import spock.lang.Specification

class CaptchaConfigTest extends Specification {
    def captcha = new CaptchaConfig().getDefaultCaptcha()

    def "test"(){
        given:
        def image = captcha.createImage("1254")
        ByteArrayOutputStream tmp = new ByteArrayOutputStream()//io流
        ImageIO.write(image, "jpg", tmp)//写入流中
        byte[] bytes = tmp.toByteArray()//转换成字节

        String png_base64 = Base64.getEncoder().encodeToString((bytes)).trim()//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "")//删除 \r\n

        System.out.println("值为："+"data:image/jpg;base64,"+png_base64)
    }
}
