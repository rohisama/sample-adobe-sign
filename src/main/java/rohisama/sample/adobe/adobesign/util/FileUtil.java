package rohisama.sample.adobe.adobesign.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    
    public static void writeBytesToFile(byte[]bFile, String fileDest) {
        try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
            fileOuputStream.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}