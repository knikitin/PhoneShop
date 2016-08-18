package phonesshop.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

/**
 * Created by kostya.nikitin on 8/17/2016.
 */
@Service
public class FilesOperationsServiceImpl implements FilesOperationsService{
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Override
    public boolean exists(File file){
        logger.debug("Test existing of file :"+file.getName());
        return file.exists();
    };

    @Override
    public boolean delete(File file){
        logger.debug("Deleting the file:"+file.getName());
        return file.delete();
    };

    @Override
    public void copy(MultipartFile file, String filename) throws IOException {
        logger.debug("Saving the file:"+filename);
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(new File(filename)));
        FileCopyUtils.copy(file.getInputStream(), stream);
        stream.close();
   };

    @Override
    public String getToString(String ROOT, String filename){
        logger.debug("Get the file:"+filename);
        return Paths.get(ROOT,filename).toString();
   };

}
