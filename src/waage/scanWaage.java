package waage;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

public class scanWaage {

    public static SerialPort sp;

    public static String getData() {
        try {
            while (true) {
                while (sp.bytesAvailable() == 0) {
                    Thread.sleep(55);
                }

                System.out.println("Verfügbare Bytes: " + sp.bytesAvailable());
                byte[] readBuffer = new byte[sp.bytesAvailable()];
                int numRead = sp.readBytes(readBuffer, readBuffer.length);
                if (numRead >= 19) {
                    String strReadBArr = new String(readBuffer, StandardCharsets.UTF_8);
                    strReadBArr = strReadBArr.replaceAll("\r", "").replaceAll(" ", "").replaceAll("W:", "").replaceAll("\n", "").replaceAll("\\+", "");
                    String[] gwStrArr = strReadBArr.split("g");
                    System.out.println("ReadyString: " + gwStrArr[gwStrArr.length - 1]);
                    return gwStrArr[gwStrArr.length - 1];
                }

            }
        } catch (Exception e) {
        }
        return "0";
    }

    public static boolean testConnection(SerialPort port) {
        try {
            sp = port;
            sp.openPort();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        if (sp.bytesAvailable() > 0) {
            return true;
        } else {
            sp.closePort();
            return false;
        }
    }
}
