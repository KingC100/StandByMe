package standbyme;

import java.math.BigDecimal;
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
    public ImageView imageView;

    @FXML
    public GridPane gridPane;

//    @FXML
//    public Label olbl_Name;
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
        engine.load("https://www.google.co.jp/search?safe=off&tbm=isch&q=%E7%B5%A2%E8%BE%BB%E8%A9%9E&ei=RWdtVYrYMsPj8AXOt4LQDA");

        // テキスト変更時にアイコン、グリッドの情報を更新する。
        textField.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {

            pMap = Get_pData(textField.getText());

            // アイコンをセット
            Image image;
            if (Chk_Exists(pMap)) {
                // アイコン取ってくる。
                image = new Image("file:res\\icons\\" + pMap.get("Number") + ".png");
                // アイコンセット。
                imageView.setImage(image);
            } else {
                image = new Image("file:res\\icons\\0.png");
                // アイコンセット。
                imageView.setImage(image);
                return;
            }

//            olbl_Name.setText(pMap.get("Name"));
            olbl_TypeFirst.setText(pMap.get("TypeFirst"));
            olbl_TypeSecond.setText(pMap.get("TypeSecond"));
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

    public String CaliculateSpeed(int bS, int EV, double nature, double stages) {

        double dSpeed = ((((bS * 2) + 31 + EV / 4) * 50 / 100 + 5) * nature);
        int iSpeed = (int) dSpeed;
        iSpeed = (int) (iSpeed * stages);

        String sSpeed = String.valueOf(iSpeed);

        return sSpeed;
    }

    public boolean Chk_Exists(Map<String, String> pMap) {
        // アイコンセット。

        return !"0".equals(pMap.get("Number"));
    }

    @FXML
    public void OnEntered(ActionEvent event) {
        WebEngine engine = webView.getEngine();
        engine.load("https://www.google.co.jp/search?safe=off&tbm=isch&q=%E7%B5%A2%E8%BE%BB%E8%A9%9E&ei=RWdtVYrYMsPj8AXOt4LQDA");
        engine.load("https://www.google.co.jp/webhp?hl=ja#safe=off&hl=ja&q=" + pMap.get("Name") + "対戦考察");
    }

}
