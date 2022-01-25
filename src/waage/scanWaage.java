package waage;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

public class scanWaage {

    public static SerialPort sp;

    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        sp = ports[0];
        for (SerialPort port : ports) {
            System.out.println("Port: " + port.getDescriptivePortName());
        }
        String s = getData();
        System.out.println(s);
    }

    public static void getCOMP(String cport) {
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort comPort = null;
        for (SerialPort p : ports) {
            System.out.println("Port: " + p.getPortDescription());

            if (p.getDescriptivePortName().contains(cport)) {
                comPort = p;

                System.out.println("?: " + p.getSystemPortName());
                System.out.println("Gefundener Port: " + comPort);
            }
        }
        if (comPort == null) {
            System.out.println("Could not find COMPort");
        } else {
            sp = comPort;
        }
    }

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
//            e.printStackTrace();
        }
        return "Keine Daten";
    }


    public static void openPort() {
        getCOMP("COM4");
        sp.openPort();
    }
}
