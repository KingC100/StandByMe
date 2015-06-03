package standbyme;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import static standbyme.GetpData.Get_pData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Kiichi
 */
public class FXMLDocumentController implements Initializable {

    Map<String, String> pMap = new HashMap<>();

    @FXML
    public Button bontan;

    @FXML
    public TextField textField;

    @FXML
    public ImageView img_Pokemon;

    @FXML
    public ImageView img_TypeFirst;

    @FXML
    public ImageView img_TypeSecond;

    @FXML
    public GridPane gridpane_Top;
    @FXML
    public GridPane gridpane_Bottom;

    @FXML
    public Label olbl_Number; // 隠し項目
    @FXML
    public Label olbl_TypeFirst;
    @FXML
    public Label olbl_TypeSecond;
    @FXML
    public Label olbl_SkillFirst;
    @FXML
    public Label olbl_SkillSecond;
    @FXML
    public Label olbl_SkillDream;
    @FXML
    public Label olbl_H;
    @FXML
    public Label olbl_A;
    @FXML
    public Label olbl_B;
    @FXML
    public Label olbl_C;
    @FXML
    public Label olbl_D;
    @FXML
    public Label olbl_S;
    @FXML
    public Label olbl_SV;
    @FXML
    public Label olbl_SU;
    @FXML
    public Label olbl_S0;
    @FXML
    public Label olbl_SV1;
    @FXML
    public Label olbl_SV2;
    @FXML
    public Label olbl_s;

    @FXML
    public MenuItem btn_SearchBattle; // 隠し項目
    @FXML
    public MenuItem btn_SearchWiki; // 隠し項目
    @FXML
    public MenuItem btn_SearchDic; // 隠し項目

//    @FXML
//    public WebView webView;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btn_SearchBattle.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_SearchWiki.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_SearchDic.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));

        // テキスト変更時にアイコン、グリッドの情報を更新する。
        textField.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {

            // ポケモンの基礎情報をMapへ格納
            pMap = Get_pData(textField.getText());

            String typeFirst = pMap.get("TypeFirst");
            String typeSecond = pMap.get("TypeSecond");

            // アイコンをセット
            Image image, type1, type2, img_empty;
            if (Name_NotExists(pMap)) {
                // ハズレの場合
                olbl_Number.setText("");
                olbl_SkillFirst.setText("");
                olbl_SkillSecond.setText("");
                olbl_SkillDream.setText("");
                olbl_H.setText("");
                olbl_A.setText("");
                olbl_B.setText("");
                olbl_C.setText("");
                olbl_D.setText("");
                // "?"アイコンセット。
                img_empty = new Image("file:res\\gifs\\0.png");
                img_Pokemon.setImage(img_empty);
                img_TypeFirst.setImage(img_empty);
                img_TypeSecond.setImage(img_empty);
                return;
            } else {
                // ポケモンのアイコンをセット
                image = new Image("file:res\\gifs\\" + pMap.get("Number") + ".gif");
                img_Pokemon.setImage(image);
                // タイプのアイコンをセット
                type1 = new Image("file:res\\types\\" + typeFirst + ".gif");
                img_TypeFirst.setImage(type1);
                type2 = new Image("file:res\\types\\" + typeSecond + ".gif");
                img_TypeSecond.setImage(type2);
            }

            // 基礎情報(文字)をセット
            olbl_Number.setText(pMap.get("Number"));
            olbl_SkillFirst.setText(pMap.get("SkillFirst"));
            olbl_SkillSecond.setText(pMap.get("SkillSecond"));
            olbl_SkillDream.setText(pMap.get("SkillDream"));
            olbl_H.setText(pMap.get("HP"));
            olbl_A.setText(pMap.get("A"));
            olbl_B.setText(pMap.get("B"));
            olbl_C.setText(pMap.get("C"));
            olbl_D.setText(pMap.get("D"));

            // すばやさ計算
            String baseSpeed = pMap.get("S");
            olbl_S.setText(baseSpeed);

            int bS = Integer.parseInt(baseSpeed);
            // 最速
            olbl_SV.setText(CaliculateSpeed(bS, 31, 252, 1.1, 1));
            // 準速
            olbl_SU.setText(CaliculateSpeed(bS, 31, 252, 1.0, 1));
            // 無振り無補正
            olbl_S0.setText(CaliculateSpeed(bS, 31, 0, 1.0, 1));
            // 最速+1
            olbl_SV1.setText(CaliculateSpeed(bS, 31, 252, 1.1, 1.5));
            // 最速+2
            olbl_SV2.setText(CaliculateSpeed(bS, 31, 252, 1.1, 2));
            // 最遅
            olbl_s.setText(CaliculateSpeed(bS, 0, 0, 0.9, 1));
        });

    }

    /**
     * *
     * すばやさ計算 ((種族値×2+個体値+努力値÷4)×レベル÷100+5)×せいかく補正
     *
     * @param bS　種族値
     * @param IV　個体値
     * @param EV　努力値
     * @param nature　性格補正
     * @param stages　その他補正
     * @return
     */
    public String CaliculateSpeed(int bS, int IV, int EV, double nature, double stages) {

        double dSpeed = ((((bS * 2) + IV + EV / 4) * 50 / 100 + 5) * nature);
        int iSpeed = (int) dSpeed;
        iSpeed = (int) (iSpeed * stages);

        String sSpeed = String.valueOf(iSpeed);

        return sSpeed;
    }

    /**
     * *
     * 入力された名前が存在するか確認する。
     *
     * @param pMap
     * @return
     */
    public boolean Name_NotExists(Map<String, String> pMap) {
        return "0".equals(pMap.get("Number"));
    }
    
    public static final URL getJarFileURL(Object object) {
    try {
        //ClassLoader から JAR URL の形式の文字列を作る
        final ClassLoader loader = object.getClass().getClassLoader();
        final String name = object.getClass().getName().replace('.', '/') + ".class";
        final URL url = loader.getResource(name);
        //パターンマッチングで URL 部分だけ抽出(ただし、"jar:～.jar!/" しか考慮してない)
        final Pattern p = Pattern.compile("^jar\\:(.+?\\.jar)\\!\\/(.*)");  
        final Matcher m = p.matcher(url.toString());
        if (m.matches()) {
            final MatchResult res = m.toMatchResult();
            return new URL(res.group(1));
        }
    } catch (Exception e) {
        //MalformedURLException, ClassCastException, NullPointerException, ...
    }
    return null;    //取得失敗した場合はすべて null
}

    public static final String toJarURL(String fileName, Object context) {
        final URL url = getJarFileURL(context);  //jar の url を取得
        if (url == null) {
            return null;    //失敗
        }
        return "jar:" + url.toString() + "!/" + fileName;  //JAR URL 構文にするだけ
    }

    /**
     * *
     * 第六世代対戦考察
     *
     * @param event
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @FXML
    public void On_SearchBattle(ActionEvent event) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        String uriString = "https://www.google.co.jp/webhp?hl=ja#safe=off&hl=ja&q=" + textField.getText() + "対戦考察";
        URI uri = new URI(uriString);
        desktop.browse(uri);

    }

    /**
     * *
     * wiki
     *
     * @param event
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @FXML
    public void On_SearchWiki(ActionEvent event) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        String uriString = "http://wiki.xn--rckteqa2e.com/wiki/" + textField.getText();
        URI uri = new URI(uriString);
        desktop.browse(uri);

    }

    /**
     * *
     * 図鑑
     *
     * @param event
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @FXML
    public void On_SearchDic(ActionEvent event) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        String uriString = "http://yakkun.com/xy/zukan/n" + olbl_Number.getText();
        URI uri = new URI(uriString);
        desktop.browse(uri);

    }

}
