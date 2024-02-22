import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

class test {

    public static void main(String[] args) {
        String ipAddress = "118.139.179.251" ;

        if (isSSHRunning(ipAddress)) {
            System.out.println("SSH is running on " + ipAddress);
        } else {
            System.out.println("SSH is not running on " + ipAddress);
        }
    }

    public static boolean isSSHRunning(String ipAddress) {
        int sshPort = 22; // Default SSH port

        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress(ipAddress, sshPort), 1000);
            System.out.println("SSH is running on " + ipAddress);
            return true;
        } catch (IOException e) {
            // If an exception occurs, SSH is not running
            return false;
        }
    }
}
