import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tools.database.SSHDatabase;
import tools.database.model.SSHDB;
import tools.utils.AnsiCodes;
import tools.utils.ServerPing;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        try {

            SSHDatabase sshDatabase = new SSHDatabase();
            System.out.println();

            while(true){
                System.out.println("1."+ AnsiCodes.BOLD_TEXT +" List Servers"+AnsiCodes.RESET_TEXT);
                System.out.println("2."+ AnsiCodes.BOLD_TEXT +" Add Server"+AnsiCodes.RESET_TEXT);
                System.out.println("3."+ AnsiCodes.BOLD_TEXT +" Delete Server"+AnsiCodes.RESET_TEXT);
                System.out.println("4."+ AnsiCodes.BOLD_TEXT +" Select Server"+AnsiCodes.RESET_TEXT);
                System.out.println("5."+ AnsiCodes.BOLD_TEXT +" Exit"+AnsiCodes.RESET_TEXT);
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch(choice){
                    case 1:
                        System.out.print(AnsiCodes.CLEAR_SCREEN);
                        System.out.println("==== List of Servers ====");
                        List<SSHDB> sshdbs = sshDatabase.getNamesAndIP();
                        System.out.println(AnsiCodes.BOLD_TEXT);
                        List<Thread> threads = new ArrayList<>();
                        for(SSHDB sshdb : sshdbs){
                            Thread thread = ServerPing.check(sshdb);
                            threads.add(thread);
                        }
                        for(Thread thread : threads){
                            thread.join();
                        }
                        System.out.println(AnsiCodes.RESET_TEXT+"\n");
                        break;
                    case 2:
                        System.out.println(AnsiCodes.CLEAR_SCREEN);
                        System.out.println("==== Add Server ====");
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter ip: ");
                        String ip = scanner.nextLine();
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter port: ");
                        int port = scanner.nextInt();
                        System.out.print("Enter auth type [ 1.PASSWORD 2.KEY ] : ");
                        int authType = scanner.nextInt();

                        if(authType == SSHDatabase.AUTH_TYPE_PASSWORD){
                            System.out.print("Enter password: ");
                            String password = scanner.next();
                            SSHDB sshdb = new SSHDB(null, name, ip, port, authType, username, password);
                            sshDatabase.addSSHDB(sshdb);
                        }else if(authType == SSHDatabase.AUTH_TYPE_KEY){
                            SSHDB sshdb = new SSHDB(null, name, ip, port, authType, username, null);
                            sshDatabase.addSSHDB(sshdb);
                        }
                        else{
                            System.out.println(AnsiCodes.BOLD_TEXT + " " + AnsiCodes.RED_TEXT + "***Invalid auth type***" + AnsiCodes.RESET_TEXT);
                            System.out.println();
                        }

                        System.out.print(AnsiCodes.CLEAR_SCREEN);
                        System.out.println( AnsiCodes.BOLD_TEXT + " "+  AnsiCodes.GREEN_TEXT + " ====Server added successfully==== " + AnsiCodes.RESET_TEXT);
                        System.out.println();
                        break;

                    case 3:
                        System.out.println("\n\n==== Delete Server ====");
                        System.out.print("Enter server id: ");
                        int id = scanner.nextInt();
                        sshDatabase.deleteSSHDB(id);
                        System.out.print(AnsiCodes.CLEAR_SCREEN);
                        System.out.println( AnsiCodes.BOLD_TEXT + " "+  AnsiCodes.GREEN_TEXT + " ====Server deleted successfully==== " + AnsiCodes.RESET_TEXT);
                        System.out.println();
                        break;

                    case 4:
                        System.out.println("\n\n==== View Details ====");
                        System.out.print("Enter server id: ");
                        int id1 = scanner.nextInt();
                        SSHDB sshdb = sshDatabase.getSSHDB(id1);
                        if(sshdb == null){
                            System.out.println(AnsiCodes.BOLD_TEXT + " " + AnsiCodes.RED_TEXT + "Server with id not found" + AnsiCodes.RESET_TEXT);
                            System.out.println();
                            break;
                        }
                        System.out.println(AnsiCodes.BOLD_TEXT);

                        System.out.println("Id: " + sshdb.getId());
                        System.out.println("Server Name: " + sshdb.getName());
                        System.out.println("Username: " + sshdb.getUsername());
                        System.out.println("IP: " + sshdb.getIp());
                        System.out.println("Port: " + sshdb.getPort());
                        System.out.println("Authentication type: " +  (sshdb.getAuthType() == SSHDatabase.AUTH_TYPE_PASSWORD ? "Password" : "Key"));
                        if(sshdb.getAuthType() == SSHDatabase.AUTH_TYPE_PASSWORD){
                            System.out.println("Password: " + sshdb.getPassword());
                        }
                        System.out.println();
                        System.out.println("1. Spawn Shell");
                        System.out.println("2. Back to main menu");
                        System.out.print("Enter your choice: ");
                        int choice1 = scanner.nextInt();
                        if(choice1 == 1){
                            System.out.println("Spawning shell for " + sshdb.getName());
                            if(sshdb.getAuthType() == SSHDatabase.AUTH_TYPE_KEY) {
                                String command[] = {"/bin/bash", "-c", "'ssh " + sshdb.getUsername()  + "@"+ sshdb.getIp() + "'"};
                                for(String s : command) {
                                    System.out.println(s + " ");
                                }   
                                ProcessBuilder processBuilder = new ProcessBuilder(command);
                                processBuilder.start();
                            }
                            else {
                                String command[] = {"/bin/bash", "-c", "'sshpass -p " + sshdb.getPassword() + " ssh " + sshdb.getUsername()  + "@"+ sshdb.getIp() + "'"};
                                for(String s : command) {
                                    System.out.println(s + " ");
                                }   
                                ProcessBuilder processBuilder = new ProcessBuilder(command);
                                processBuilder.start();
                            }

                        }
                        
                        System.out.println(AnsiCodes.RESET_TEXT);
                        System.out.println();
                        break;

                    case 5:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                System.out.println(AnsiCodes.BOLD_TEXT + " Press enter to continue.. "+AnsiCodes.RESET_TEXT);
                scanner.nextLine();
                scanner.nextLine();
            }

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(UnknownHostException e) {
            e.printStackTrace();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            scanner.close();
        }
    }
}