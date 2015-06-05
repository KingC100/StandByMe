package standbyme;


import java.net.URL;

/**
 *
 * @author Kiichi
 */
public class GetResourcePath {
    
    public URL GetResourcePath(String filePath) {
        URL url = this.getClass().getResource("res/" + filePath);
        return url;
    }
}
