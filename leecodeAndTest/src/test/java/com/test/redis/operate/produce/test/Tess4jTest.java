package com.test.redis.operate.produce.test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class Tess4jTest {


    @Test
    public void testTess4jOne() {

        System.out.println(System.getProperties());


    }

    @Test
    public void testTess4jTwo() {

        File image = new File("codePicture", "ch3.png");
//        File image = new File("codePicture", "normal3.png");
//        File image = new File("codePicture", "u=1157150348,2447213531&fm=11&gp=0.jpg");
//       File image = new File("codePicture", "u=556694678,1316064338&fm=26&gp=0.jpg");
        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage("chi_sim");
        File tessData = new File("tessdata");
//        System.out.println(tessData.getAbsolutePath());
        tesseract.setDatapath("tessdata");

        String result = null;
        try {
            result = tesseract.doOCR(image);
            System.out.println("这是结果:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public static void main(String args[]) {

        System.out.println(UUID.randomUUID().toString());



    }
}
