package waage;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

public class scanWaage {

    public static SerialPort sp;

    public static String getData() {
        try {
            while (true) {
                // Wenn keine Bytes angekommen sind warte 55 ms
                while (sp.bytesAvailable() == 0) {
                    Thread.sleep(55);
                }

                byte[] readBuffer = new byte[sp.bytesAvailable()];
                int numRead = sp.readBytes(readBuffer, readBuffer.length);
                // Wenn genug Bytes da sind (19 liefert fast immer genug datensätze)
                if (numRead >= 19) {
                    String strReadBArr = new String(readBuffer, StandardCharsets.UTF_8);
                    // Alles auser die Zahl ansich wird durch "" ersetzt
                    strReadBArr = strReadBArr.replaceAll("\r", "").replaceAll(" ", "").replaceAll("W:", "").replaceAll("\n", "").replaceAll("\\+", "");
                    // nach dem gramm zeichen beginnt ein neuer wert
                    String[] gwStrArr = strReadBArr.split("g");
                    return gwStrArr[gwStrArr.length - 1];
                }

            }
        } catch (Exception ignored) {

        }
        return "0";
    }

    // Test ob die Waage daten sendet
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
