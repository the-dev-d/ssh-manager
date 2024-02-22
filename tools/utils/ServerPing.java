package tools.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import tools.database.model.SSHDB;

public class ServerPing implements Runnable{

    private SSHDB sshdb;

    public ServerPing(SSHDB sshdb) {
        this.sshdb = sshdb;
    }
    
    public static Thread check(SSHDB sshdb) throws Exception {

        ServerPing serverPing = new ServerPing(sshdb);
        Thread thread = new Thread(serverPing);
        thread.start();
        return thread;
    }

    public void run() {
        Socket socket = null;
        try {
            InetSocketAddress sshdbAddress = new InetSocketAddress(sshdb.getIp(), sshdb.getPort());
            socket = new Socket();
            socket.connect(sshdbAddress, 3000);
            System.out.println(AnsiCodes.BOLD_TEXT + " [" + this.sshdb.getId() + "] " + this.sshdb.getName() + AnsiCodes.GREEN_TEXT + " \tactive" + AnsiCodes.RESET_TEXT);

        }catch(IOException e){
            System.out.println(AnsiCodes.BOLD_TEXT + " [" + this.sshdb.getId() + "] "+ this.sshdb.getName() + AnsiCodes.RED_TEXT + " \tinactive" + AnsiCodes.RESET_TEXT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
