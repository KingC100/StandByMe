package standbyme;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Kiichi
 */
public class FXMLDocumentController implements Initializable {

    // ポケモンの情報を持つ
    // 例 : pMap("TypeFirst", "ほのお")
    Map<String, String> pMap = new HashMap<>();
    GetResourcePath getResourcePath = new GetResourcePath();

    @FXML
    private TextField textField;

    /**
     * アイコン
     */
    @FXML
    private ImageView img_Pokemon;
    @FXML
    private ImageView img_TypeFirst;
    @FXML
    private ImageView img_TypeSecond;

    /**
     * 基本情報
     */
    @FXML
    private Label olbl_Number; // 隠し項目
    @FXML
    private Label olbl_SkillFirst;
    @FXML
    private Label olbl_SkillSecond;
    @FXML
    private Label olbl_SkillDream;
    @FXML
    private Label olbl_H;
    @FXML
    private Label olbl_A;
    @FXML
    private Label olbl_B;
    @FXML
    private Label olbl_C;
    @FXML
    private Label olbl_D;
    @FXML
    private Label olbl_S;
    @FXML
    private Label olbl_SV;
    @FXML
    private Label olbl_SU;
    @FXML
    private Label olbl_S0;
    @FXML
    private Label olbl_SV1;
    @FXML
    private Label olbl_SV2;
    @FXML
    private Label olbl_s;

    /**
     * x4 ImageView(1,0) ~ ImageView(9,0)
     */
    @FXML
    private ImageView img_x4_1;
    @FXML
    private ImageView img_x4_2;
    @FXML
    private ImageView img_x4_3;
    @FXML
    private ImageView img_x4_4;
    @FXML
    private ImageView img_x4_5;
    @FXML
    private ImageView img_x4_6;
    @FXML
    private ImageView img_x4_7;
    @FXML
    private ImageView img_x4_8;
    @FXML
    private ImageView img_x4_9;
    @FXML
    private ImageView img_x4_10;

    /**
     * x2 ImageView(1,1) ~ ImageView(9,1)
     */
    @FXML
    private ImageView img_x2_1;
    @FXML
    private ImageView img_x2_2;
    @FXML
    private ImageView img_x2_3;
    @FXML
    private ImageView img_x2_4;
    @FXML
    private ImageView img_x2_5;
    @FXML
    private ImageView img_x2_6;
    @FXML
    private ImageView img_x2_7;
    @FXML
    private ImageView img_x2_8;
    @FXML
    private ImageView img_x2_9;
    @FXML
    private ImageView img_x2_10;

    // 等倍(x1)は表示しない。
    //特性等での補正がない限り、無ければ等倍として見る。
    /**
     * x0.5 ImageView(1,2) ~ ImageView(9,2)
     */
    @FXML
    private ImageView img_x05_1;
    @FXML
    private ImageView img_x05_2;
    @FXML
    private ImageView img_x05_3;
    @FXML
    private ImageView img_x05_4;
    @FXML
    private ImageView img_x05_5;
    @FXML
    private ImageView img_x05_6;
    @FXML
    private ImageView img_x05_7;
    @FXML
    private ImageView img_x05_8;
    @FXML
    private ImageView img_x05_9;
    @FXML
    private ImageView img_x05_10;

    /**
     * x0.25 ImageView(1,3) ~ ImageView(9,3)
     */
    @FXML
    private ImageView img_x025_1;
    @FXML
    private ImageView img_x025_2;
    @FXML
    private ImageView img_x025_3;
    @FXML
    private ImageView img_x025_4;
    @FXML
    private ImageView img_x025_5;
    @FXML
    private ImageView img_x025_6;
    @FXML
    private ImageView img_x025_7;
    @FXML
    private ImageView img_x025_8;
    @FXML
    private ImageView img_x025_9;
    @FXML
    private ImageView img_x025_10;

    /**
     * x0 ImageView(1,4) ~ ImageView(9,4)
     */
    @FXML
    private ImageView img_x0_1;
    @FXML
    private ImageView img_x0_2;
    @FXML
    private ImageView img_x0_3;
    @FXML
    private ImageView img_x0_4;
    @FXML
    private ImageView img_x0_5;
    @FXML
    private ImageView img_x0_6;
    @FXML
    private ImageView img_x0_7;
    @FXML
    private ImageView img_x0_8;
    @FXML
    private ImageView img_x0_9;
    @FXML
    private ImageView img_x0_10;

// 隠し項目
    @FXML
    private MenuItem btn_SearchBattle;
    @FXML
    private MenuItem btn_SearchWiki;
    @FXML
    private MenuItem btn_SearchDic;
    @FXML
    private MenuItem btn_Debug;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Set_ShortCutKey();

        // タイプアイコンをImageで作成
//        TypesIcon();
        // テキスト変更時にアイコン、グリッドの情報を更新する。
        textField.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {

            // ポケモンの基礎情報をMapへ格納
            GetpData gpd = new GetpData();
            pMap = gpd.Get_pData(textField.getText());

            String typeFirst = pMap.get("TypeFirst");
            String typeSecond = pMap.get("TypeSecond");

            // アイコンをセット
            Image image_pIcon, image_Type1, image_Type2, img_Empty = null;
            // 入力名が存在するか
            if (Name_NotExists(pMap)) {
                // 入力名が存在しない場合
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
                // タイプ相性表を更新
                Set_Affinity("nothing", "nothing");
                return;

            } else {
                // 入力名が存在する場合
                // ポケモンのアイコンをセット             
                URL url_pIcon = getResourcePath.GetResourcePath("gifs/" + pMap.get("Number") + ".gif");
                image_pIcon = new Image(url_pIcon.toString());
                img_Pokemon.setImage(image_pIcon);

                // タイプのアイコンをセット
                URL url_Type1 = getResourcePath.GetResourcePath("types/" + typeFirst + ".gif");
                image_Type1 = new Image(url_Type1.toString());
                img_TypeFirst.setImage(image_Type1);

                // タイプ2が存在するか
                if (typeSecond.equals("nothing")) {
                    // 存在しない場合、
                    // タイプ2へ空画像の設定
                    img_TypeSecond.setImage(img_Empty);
                } else {
                    // タイプ2が存在する場合
                    // アイコンを取得して設定
                    URL url_Type2 = getResourcePath.GetResourcePath("types/" + typeSecond + ".gif");
                    image_Type2 = new Image(url_Type2.toString());
                    img_TypeSecond.setImage(image_Type2);
                }

                // タイプ相性表を更新
                Set_Affinity(typeFirst, typeSecond);
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

    /**
     * ショートカットキーの設定。
     */
    public void Set_ShortCutKey() {
        this.btn_SearchBattle.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_SearchWiki.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_SearchDic.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));
        this.btn_Debug.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN, KeyCombination.SHORTCUT_ANY));

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

    /**
     * *
     * タイプ相性の計算、グリッドへの表示 ※. 等倍、特性については触れず。
     *
     * @param typeFirst
     * @param typeSecond
     */
    public void Set_Affinity(String typeFirst, String typeSecond) {

        // タイプ相性の計算
        GetAffinity ga = new GetAffinity();
        Map<String, String[]> mapAf;
        List list_x4 = null;
        List list_x2 = null;
        List list_x05 = null;
        List list_x025 = null;
        List list_x0 = null;

        List<String> emp_list = new ArrayList<>();
        for (int i = 0; i < Const.typeTotalNumber; i++) {
            emp_list.add("0");
        }

        // タイプ相性表更新
        // ポケモン名が存在するか
        if (Name_NotExists(pMap)) {
            // 存在ない場合、空画像をセットする
            list_x4 = emp_list;
            list_x2 = emp_list;
            list_x05 = emp_list;
            list_x025 = emp_list;
            list_x0 = emp_list;
        } else {
            // 存在する場合、タイプの画像をセットする
            mapAf = ga.Get_Types(typeFirst, typeSecond);

            list_x4 = GetAffinityList(mapAf, "x4");
            list_x2 = GetAffinityList(mapAf, "x2");
            list_x05 = GetAffinityList(mapAf, "1/2");
            list_x025 = GetAffinityList(mapAf, "1/4");
            list_x0 = GetAffinityList(mapAf, "x0");
        }

        /**
         * x4 ImageView(1,0) ~ ImageView(9,0)
         */
        img_x4_1.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((0)) + ".gif").toString()));
        img_x4_2.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((1)) + ".gif").toString()));
        img_x4_3.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((2)) + ".gif").toString()));
        img_x4_4.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((3)) + ".gif").toString()));
        img_x4_5.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((4)) + ".gif").toString()));
        img_x4_6.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((5)) + ".gif").toString()));
        img_x4_7.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((6)) + ".gif").toString()));
        img_x4_8.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((7)) + ".gif").toString()));
        img_x4_9.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((8)) + ".gif").toString()));
        img_x4_10.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x4.get((9)) + ".gif").toString()));

        /**
         * x2 ImageView(1,1) ~ ImageView(9,1)
         */
        img_x2_1.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((0)) + ".gif").toString()));
        img_x2_2.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((1)) + ".gif").toString()));
        img_x2_3.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((2)) + ".gif").toString()));
        img_x2_4.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((3)) + ".gif").toString()));
        img_x2_5.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((4)) + ".gif").toString()));
        img_x2_6.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((5)) + ".gif").toString()));
        img_x2_7.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((6)) + ".gif").toString()));
        img_x2_8.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((7)) + ".gif").toString()));
        img_x2_9.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((8)) + ".gif").toString()));
        img_x2_10.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x2.get((9)) + ".gif").toString()));

        /**
         * x0.5 ImageView(1,2) ~ ImageView(9,2)
         */
        img_x05_1.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((0)) + ".gif").toString()));
        img_x05_2.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((1)) + ".gif").toString()));
        img_x05_3.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((2)) + ".gif").toString()));
        img_x05_4.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((3)) + ".gif").toString()));
        img_x05_5.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((4)) + ".gif").toString()));
        img_x05_6.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((5)) + ".gif").toString()));
        img_x05_7.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((6)) + ".gif").toString()));
        img_x05_8.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((7)) + ".gif").toString()));
        img_x05_9.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((8)) + ".gif").toString()));
        img_x05_10.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x05.get((9)) + ".gif").toString()));

        /**
         * x0.25 ImageView(1,3) ~ ImageView(9,3)
         */
        img_x025_1.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((0)) + ".gif").toString()));
        img_x025_2.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((1)) + ".gif").toString()));
        img_x025_3.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((2)) + ".gif").toString()));
        img_x025_4.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((3)) + ".gif").toString()));
        img_x025_5.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((4)) + ".gif").toString()));
        img_x025_6.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((5)) + ".gif").toString()));
        img_x025_7.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((6)) + ".gif").toString()));
        img_x025_8.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((7)) + ".gif").toString()));
        img_x025_9.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((8)) + ".gif").toString()));
        img_x025_10.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x025.get((9)) + ".gif").toString()));

        /**
         * x0 ImageView(1,4) ~ ImageView(9,4)
         */
        img_x0_1.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((0)) + ".gif").toString()));
        img_x0_2.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((1)) + ".gif").toString()));
        img_x0_3.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((2)) + ".gif").toString()));
        img_x0_4.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((3)) + ".gif").toString()));
        img_x0_5.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((4)) + ".gif").toString()));
        img_x0_6.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((5)) + ".gif").toString()));
        img_x0_7.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((6)) + ".gif").toString()));
        img_x0_8.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((7)) + ".gif").toString()));
        img_x0_9.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((8)) + ".gif").toString()));
        img_x0_10.setImage(new Image(getResourcePath.GetResourcePath("types/" + list_x0.get((9)) + ".gif").toString()));
    }

    /**
     * タイプ相性リストを取得
     *
     * @param mapAf
     * @param correction
     * @return
     *
     * example: ResultList = {"いわ","ドラゴン","フェアリー", "0","0"....}
     */
    public List<String> GetAffinityList(Map<String, String[]> mapAf, String correction) {

        // 取得するタイプ相性補正の一覧を取得
        String[] types = mapAf.get(correction);
        List<String> lst = new ArrayList<>();
        // 総数計算のためのカウンタ
        int p = Const.typeTotalNumber; // 18

        // 一致した数を数える
        for (int i = 0; i < Const.typeTotalNumber; i++) {
            // タイプが一致するか
            if (Const.typeAry[i].equals((types[i]))) {
                // 一致した場合、リストへそのタイプ名を格納
                lst.add(Const.typeAry[i]);
                // カウンタから1引く
                p--;
            }
        }

        // リストの空部分を"0"で埋める
        while (p != 0) {
            lst.add("0");
            p--;
        }
        return lst;
    }
}
