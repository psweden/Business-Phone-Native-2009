package MView;

import MControll.Main_Controll;


/**
 * <p>Title: Mobile Extension</p>
 *
 * <p>Description: All PBX Include</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: mobisma ab</p>
 *
 * @author Peter Albertsson
 * @version 2.0
 */


import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Image;
import MModel.CONF;
import java.io.IOException;


// A canvas that illustrates drawing on an Image
public class ServerNumber extends Canvas {

    private CONF config;
    private String prg_Name, viewDateString, device_brands, deveice_model, pbx_name;

    // Tar emot värden från huvudclassen i konstruktorn.
    public ServerNumber(String prgname, String ViewDateString, String Device_Brands, String Device_Model, String Pbx_Name) {

        this.viewDateString = ViewDateString;
        this.prg_Name = prgname;
        this.device_brands = Device_Brands;
        this.deveice_model = Device_Model;
        this.pbx_name = Pbx_Name;

    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        // Create an Image the same size as the
        // Canvas.
        Image image = Image.createImage(width, height);
        Graphics imageGraphics = image.getGraphics();

        // Fill the background of the image black
        imageGraphics.setColor(0x000000);
        imageGraphics.fillRect(0, 0, width, height);

        // Draw a pattern of lines
        int count = 10;
        int yIncrement = height/count;
        int xIncrement = width/count;
        /*
        for (int i = 0, x = xIncrement, y = 0; i < count; i++) {
            imageGraphics.setColor(0xC0 + ((128 + 10 * i) << 8) +
                                   ((128 + 10 * i) << 16));
            imageGraphics.drawLine(0, y, x, height);
            y += yIncrement;
            x += xIncrement;
        }*/

        // Add some text
        imageGraphics.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 0,
                                           Font.SIZE_SMALL));
        imageGraphics.setColor(0xffff00);
         imageGraphics.drawString(prg_Name, width / 2, 15,
                                  Graphics.TOP | Graphics.HCENTER);



        try {
            Image image1 = Image.createImage("/mobisma_icon/mexa.png");
            imageGraphics.drawImage(image1, width / 2, 50,
                                    Graphics.TOP | Graphics.HCENTER);
        } catch (IOException ex) {
        }

        imageGraphics.drawString(viewDateString, width / 2, 100, Graphics.TOP | Graphics.HCENTER);
        imageGraphics.setColor(0xffffff);
        imageGraphics.drawString(config.NAME, width/2, 122, Graphics.TOP | Graphics.HCENTER);
        imageGraphics.drawString(config.COMPANYNAME, width/2, 142, Graphics.TOP | Graphics.HCENTER);
        imageGraphics.setColor(0xffff00);
        imageGraphics.drawString("PBX: " + this.pbx_name, width/2, 162, Graphics.TOP | Graphics.HCENTER);
        imageGraphics.drawString("Phone: " + this.device_brands + " " + this.deveice_model, width/2, 180, Graphics.TOP | Graphics.HCENTER);


        // Copy the Image to the screen
        g.drawImage(image, 0, 0, Graphics.TOP | Graphics.LEFT);

    }


}
