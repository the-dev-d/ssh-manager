package tools.utils;

import java.io.File;
import java.util.UUID;

public class FileCache {

    private File cacheFolder;

    public FileCache() {
        this.cacheFolder = new File(System.getProperty("user.home") + "/.sshcache");
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs();
        }
    }

    public String saveFile(byte[] data, String filename) {
        File file = new File(cacheFolder, UUID.randomUUID().toString());
        return file.getAbsolutePath();
    }

    public void clearCache(){
        for(File file : cacheFolder.listFiles()){
            file.delete();
        }
    }
    
}
