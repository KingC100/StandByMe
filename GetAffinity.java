package standbyme;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Kiichi
 */
public class GetAffinity {

    // タイプ2の有無
    Boolean bl_Type2 = true;

    /**
     * types.xmlから一致するタイプ相性情報を取得する。 Type_First
     *
     * @param typeFirst
     * @param typeSecond
     * @return
     */
    public Map<String, String[]> Get_Types(String typeFirst, String typeSecond) {

        if (typeSecond.equals("nothing")) {
            bl_Type2 = false;
        }

        Map<String, String> map_TypeFirst = new HashMap<>();
        Map<String, String> map_TypeSecond = new HashMap<>();
        Map<String, String[]> map_Result = new HashMap<>();

        DocumentBuilder builder;
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();

        try {
            builder = dbfactory.newDocumentBuilder();
            GetResourcePath getResourcePath = new GetResourcePath();
            URL url = getResourcePath.GetResourcePath("xml/Types.xml");
            Document doc = builder.parse(url.openStream());

            Element root = doc.getDocumentElement();

            Element typeInfo;
            Element affinity;
            NodeList nodeList = root.getElementsByTagName("TypeInfo");
            for (int i = 0; i < nodeList.getLength(); i++) {

                typeInfo = (Element) root.getElementsByTagName("TypeInfo").item(i);

                affinity = (Element) typeInfo.getElementsByTagName("Affinity").item(0);

                // タイプ1の相性を取得する。
                if (typeFirst.equals(((Element) typeInfo.getElementsByTagName("TypeName").item(0)).getTextContent())) {

                    for (String typeList1 : Const.typeAry) {
                        // タイプ相性の格納 <"タイプ名", "ダメージ相性"> 
                        map_TypeFirst.put(typeList1, ((Element) affinity.getElementsByTagName(typeList1).item(0)).getTextContent());
                    }
                }

                // タイプ2が存在するか判定
                if (bl_Type2) {
                    // タイプ2が存在する場合

                    // タイプ2のタイプ名が正規のものか判定
                    if (typeSecond.equals(((Element) typeInfo.getElementsByTagName("TypeName").item(0)).getTextContent())) {

                        // タイプ1、タイプ2を合わせたタイプ相性の結果を求める。
                        for (String typeList2 : Const.typeAry) {
                            // タイプ相性の格納 <"タイプ名", "ダメージ相性">
                            map_TypeSecond.put(typeList2, ((Element) affinity.getElementsByTagName(typeList2).item(0)).getTextContent());
                        }
                    }
                }
            }
            // Mapへ格納
            map_Result = CalculateAffinity(map_TypeFirst, map_TypeSecond);
            return map_Result;

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GetpData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map_Result;
    }

    /**
     * *
     * タイプ相性計算(タイプ1, タイプ2)
     *
     * @param map_TypeFirst
     * @param map_TypeSecond
     * @return
     */
    public Map<String, String[]> CalculateAffinity(Map<String, String> map_TypeFirst, Map<String, String> map_TypeSecond) {

        double type1;
        double type2;
        double res;

        String[] x4 = new String[Const.typeTotalNumber];
        String[] x2 = new String[Const.typeTotalNumber];
        String[] x1 = new String[Const.typeTotalNumber];
        String[] x05 = new String[Const.typeTotalNumber];
        String[] x025 = new String[Const.typeTotalNumber];
        String[] x0 = new String[Const.typeTotalNumber];
        Map<String, String[]> map_Result = new HashMap<>();

        // タイプ総数だけ回す。
        for (int j = 0; j < Const.typeTotalNumber; j++) {

            // タイプ1、タイプ2を掛けあわせたタイプ相性を計算。
            type1 = Double.parseDouble(map_TypeFirst.get(Const.typeAry[j]));
            // タイプ2が存在するか
            if (bl_Type2) {
                // 存在する場合
                type2 = Double.parseDouble(map_TypeSecond.get(Const.typeAry[j]));
            } else {
                // 存在しない場合、1.0
                type2 = 1.0;
            }
            // 合計値
            res = type1 * type2;
            String sres = String.valueOf(res);

            // タイプ相性ごとに振り分ける。
            switch (sres) {
                case "4.0":
                    x4[j] = Const.typeAry[j];
                    break;
                case "2.0":
                    x2[j] = Const.typeAry[j];
                    break;
                case "1.0":
                    x1[j] = Const.typeAry[j];
                    break;
                case "0.5":
                    x05[j] = Const.typeAry[j];
                    break;
                case "0.25":
                    x025[j] = Const.typeAry[j];
                    break;
                case "0.0":
                    x0[j] = Const.typeAry[j];
                    break;
            }
        }

        // 格納
        map_Result.put("x4", x4);
        map_Result.put("x2", x2);
        map_Result.put("1", x1);
        map_Result.put("1/2", x05);
        map_Result.put("1/4", x025);
        map_Result.put("x0", x0);

        return map_Result;

    }
}
