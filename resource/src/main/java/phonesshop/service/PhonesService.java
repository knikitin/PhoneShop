package phonesshop.service;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * Created by kostya.nikitin on 8/4/2016.
 */
public class PhonesService {
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    public static ResponseEntity<?> deleteImgPhone(String root, long id){
        String filename =  Long.toString(id, 16)+".jpg";
        logger.debug("Delete image for phone with id =" + id );
        try {
            File f1 = new File(root + "/" + filename);
            if (f1.exists()) {
                if (f1.delete())
                    return ResponseEntity.ok().build();
                else
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("You failed to delete " + filename );
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
