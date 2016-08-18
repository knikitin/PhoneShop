package phonesshop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by kostya.nikitin on 8/17/2016.
 */
public interface FilesOperationsService {
    boolean exists(File file);
    boolean delete(File file);
    void copy(MultipartFile file, String filename)  throws IOException;
    String getToString(String ROOT, String filename);
}
