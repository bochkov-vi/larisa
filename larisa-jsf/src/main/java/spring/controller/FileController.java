package spring.controller;

import larisa.entity.File;
import larisa.repository.FileRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by home on 28.02.17.
 */

@Controller
public class FileController {

    @Autowired
    FileRepository fileRepository;

    public FileController() {

    }

    @RequestMapping("file/{id}/{width}x{height}")
    @ResponseBody
    public void showFile(HttpServletResponse response, @PathVariable("id") Integer id, @PathVariable("width") Integer scaledWidth, @PathVariable("height") Integer scaledHeight) throws IOException {
        File file = fileRepository.findOne(id);

        if (scaledWidth != null && scaledWidth > 0 && scaledHeight != null && scaledHeight > 0) {
            try (InputStream in = new ByteArrayInputStream(file.getData())) {
                BufferedImage originalImage = ImageIO.read(in);
                double[] size = new double[]{originalImage.getWidth(), originalImage.getHeight()};
                double[] crop = new double[]{originalImage.getWidth(), originalImage.getHeight()};
                if (scaledWidth / size[0] > scaledHeight / size[1]) {
                    crop[0] = scaledWidth;
                    crop[1] = size[1] * scaledWidth / size[0];
                } else {
                    crop[0] = crop[0] * scaledHeight / crop[1];
                    crop[1] = scaledHeight;
                }
                /*double[] crop1 = new double[]{size[0], scaledWidth * size[1] / size[0]};
                double[] crop2 = new double[]{scaledHeight * size[0] / size[1], size[1]};
                try {
                    crop = Ordering.natural().<double[]>onResultOf(c -> c[0] + c[1]).min(Iterables.filter(Lists.newArrayList(crop1, crop2), c -> c[0] >= scaledWidth && c[1] >= scaledHeight));
                } catch (Exception e) {
                }
                if (crop == null) {
                    crop = new double[]{scaledWidth, scaledHeight};
                }*/

                int x = (int) ((scaledWidth - crop[0]) / 2);
                int y = (int) ((scaledHeight - crop[1]) / 2);
                BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
                Image img = originalImage;
                img = img.getScaledInstance((int) crop[0], (int) crop[1], Image.SCALE_SMOOTH);
                Graphics2D g = scaled.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                g.setComposite(AlphaComposite.Clear);
                g.fillRect(0, 0, scaledWidth, scaledHeight);
                /*((Graphics2D)g).setComposite(AlphaComposite.Clear);
                g.fillRect(x,y,scaledWidth,scaledHeight);*/
                g.drawImage(img, x, y, (int) crop[0], (int) crop[1], null);
                g.dispose();
                response.setContentType("image/png");

                ImageIO.write(scaled, "PNG", response.getOutputStream());
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setContentType(file.getType());
        IOUtils.write(file.getData(), response.getOutputStream());

    }
}
