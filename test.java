

class test {

    public static void main(String[] args) {
        
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("gnome-terminal", "--", "bash", "-c", "ssh devd@64.227.169.214");
            processBuilder.start();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
