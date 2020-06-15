package com.kqds.util.sys.other;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import sun.misc.BASE64Encoder;

public class BarcodeUtil
{
  public static String generateBarCode128(String strBarCode, String dimension, String barheight)
  {
    try
    {
      ByteArrayOutputStream outputStream = null;
      BufferedImage bi = null;
      
      JBarcode productBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
      

      productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());
      
      productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());
      
      productBarcode.setWideRatio(Double.valueOf(30.0D).doubleValue());
      
      productBarcode.setShowText(true);
      
      productBarcode.setTextPainter(BaseLineTextPainter.getInstance());
      

      bi = productBarcode.createBarcode(strBarCode);
      
      outputStream = new ByteArrayOutputStream();
      ImageIO.write(bi, "jpg", outputStream);
      BASE64Encoder encoder = new BASE64Encoder();
      

      return encoder.encode(outputStream.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "encodeError";
  }
  
  public static String generateBarCode(String strBarCode, String dimension, String barheight)
  {
    try
    {
      ByteArrayOutputStream outputStream = null;
      BufferedImage bi = null;
      int len = strBarCode.length();
      JBarcode productBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
      
      String barCode = strBarCode.substring(0, len - 1);
      String code = strBarCode.substring(len - 1, len);
      

      String checkCode = productBarcode.calcCheckSum(barCode);
      if (!code.equals(checkCode)) {
        return "checkCodeError";
      }
      productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());
      
      productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());
      
      productBarcode.setWideRatio(Double.valueOf(25.0D).doubleValue());
      

      productBarcode.setShowCheckDigit(true);
      




      bi = productBarcode.createBarcode(barCode);
      
      outputStream = new ByteArrayOutputStream();
      ImageIO.write(bi, "jpg", outputStream);
      BASE64Encoder encoder = new BASE64Encoder();
      

      return encoder.encode(outputStream.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "encodeError";
  }
  
  public static void main(String[] args)
    throws InvalidAtributeException
  {}
}
