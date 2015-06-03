package standbyme;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import static standbyme.GetpData.Get_pData;

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
    public GridPane gridPane;
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
    public WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // グリッドに罫線表示
        gridPane.setGridLinesVisible(true);

        WebEngine engine = webView.getEngine();
        engine.load("http://animewallpaperstock.com/wallpaper/01a/amagami/solo/ayatsuji-tsukasa0002.jpg");

        // テキスト変更時にアイコン、グリッドの情報を更新する。
        textField.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {

            // ポケモンの基礎情報をMapへ格納
            pMap = Get_pData(textField.getText());

            // 基礎情報(文字)をセット
            String typeFirst = pMap.get("TypeFirst");
            String typeSecond = pMap.get("TypeSecond");
            olbl_SkillFirst.setText(pMap.get("SkillFirst"));
            olbl_SkillSecond.setText(pMap.get("SkillSecond"));
            olbl_SkillDream.setText(pMap.get("SkillDream"));
            olbl_H.setText(pMap.get("HP"));
            olbl_A.setText(pMap.get("A"));
            olbl_B.setText(pMap.get("B"));
            olbl_C.setText(pMap.get("C"));
            olbl_D.setText(pMap.get("D"));

            // アイコンをセット
            Image image, type1, type2,img_empty;
            if (Name_NotExists(pMap)) {
                // ハズレの場合
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

            // すばやさ計算
            String baseSpeed = pMap.get("S");
            olbl_S.setText(baseSpeed);

            int bS = Integer.parseInt(baseSpeed);
            // 最速
            olbl_SV.setText(CaliculateSpeed(bS, 252, 1.1, 1));
            // 準速
            olbl_SU.setText(CaliculateSpeed(bS, 252, 1.0, 1));
            // 無振り無補正
            olbl_S0.setText(CaliculateSpeed(bS, 0, 1.0, 1));
            // 最速+1
            olbl_SV1.setText(CaliculateSpeed(bS, 252, 1.1, 1.5));
            // 最速+2
            olbl_SV2.setText(CaliculateSpeed(bS, 252, 1.1, 2));
        });
    }

    /**
     * *
     * すばやさ計算 ((種族値×2+個体値+努力値÷4)×レベル÷100+5)×せいかく補正
     *
     * @param bS
     * @param EV
     * @param nature
     * @param stages
     * @return
     */
    public String CaliculateSpeed(int bS, int EV, double nature, double stages) {

        double dSpeed = ((((bS * 2) + 31 + EV / 4) * 50 / 100 + 5) * nature);
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

    /**
     * *
     * Searchボタン
     *
     * @param event
     */
    @FXML
    public void OnEntered(ActionEvent event) {
        WebEngine engine = webView.getEngine();;
        engine.load("https://www.google.co.jp/webhp?hl=ja#safe=off&hl=ja&q=" + textField.getText() + "対戦考察");
    }

}
