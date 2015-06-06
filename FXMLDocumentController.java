package standbyme;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Kiichi
 */
public class FXMLDocumentController implements Initializable {

    Map<String, String> pMap = new HashMap<>();
    GetpData gpd = new GetpData();
    GetResourcePath getResourcePath = new GetResourcePath();

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

    // 隠し項目
    @FXML
    public MenuItem btn_SearchBattle;
    @FXML
    public MenuItem btn_SearchWiki;
    @FXML
    public MenuItem btn_SearchDic;
    @FXML
    public MenuItem btn_Debug;

//    @FXML
//    public WebView webView;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btn_SearchBattle.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_SearchWiki.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_SearchDic.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));

        // Debug.
        this.btn_Debug.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));

        // テキスト変更時にアイコン、グリッドの情報を更新する。
        textField.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {

            // ポケモンの基礎情報をMapへ格納
            pMap = gpd.Get_pData(textField.getText());

            String typeFirst = pMap.get("TypeFirst");
            String typeSecond = pMap.get("TypeSecond");

            // アイコンをセット
            Image image_pIcon, image_Type1, image_Type2, img_Empty = null;
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
                URL url_Empty = getResourcePath.GetResourcePath("gifs/0.png");
                img_Empty = new Image(url_Empty.toString());

                img_Pokemon.setImage(img_Empty);
                img_TypeFirst.setImage(img_Empty);
                img_TypeSecond.setImage(img_Empty);
                return;
            } else {
                // ポケモンのアイコンをセット             
                URL url_pIcon = getResourcePath.GetResourcePath("gifs/" + pMap.get("Number") + ".gif");
                image_pIcon = new Image(url_pIcon.toString());
                img_Pokemon.setImage(image_pIcon);

                // タイプのアイコンをセット
                URL url_Type1 = getResourcePath.GetResourcePath("types/" + typeFirst + ".gif");
                image_Type1 = new Image(url_Type1.toString());
                img_TypeFirst.setImage(image_Type1);

                if (typeSecond.equals("nothing")) {
                    img_TypeSecond.setImage(img_Empty);
                } else {
                    URL url_Type2 = getResourcePath.GetResourcePath("types/" + typeSecond + ".gif");
                    image_Type2 = new Image(url_Type2.toString());
                    img_TypeSecond.setImage(image_Type2);
                }
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
            olbl_SV.setText(CalculateSpeed(bS, 31, 252, 1.1, 1));
            // 準速
            olbl_SU.setText(CalculateSpeed(bS, 31, 252, 1.0, 1));
            // 無振り無補正
            olbl_S0.setText(CalculateSpeed(bS, 31, 0, 1.0, 1));
            // 最速+1
            olbl_SV1.setText(CalculateSpeed(bS, 31, 252, 1.1, 1.5));
            // 最速+2
            olbl_SV2.setText(CalculateSpeed(bS, 31, 252, 1.1, 2));
            // 最遅
            olbl_s.setText(CalculateSpeed(bS, 0, 0, 0.9, 1));
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
    public String CalculateSpeed(int bS, int IV, int EV, double nature, double stages) {

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

    /* 以下3つは検索機能 */
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

    @FXML
    public void On_Debug(ActionEvent event) throws IOException, URISyntaxException {
        GetAffinity ga = new GetAffinity();
//        Map<String, String[]> eee = ga.Get_Types("はがね", "nothing");
        Map<String, String[]> sss = ga.Get_Types("ほのお", "いわ");
    }

}
