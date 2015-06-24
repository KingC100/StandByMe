package standbyme;


import java.net.URL;

/**
 *
 * @author Kiichi
 */
public class GetResourcePath {
    
    /**
     * リソースフォルダのURLを取得
     * @param filePath
     * @return 
     */
    public URL GetResourcePath(String filePath) {
        
        // res + "引数"のURLを生成し、返す。
        URL url = this.getClass().getResource("res/" + filePath);
        return url;
    }
}
