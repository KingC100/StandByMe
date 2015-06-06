/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    String[] typeList = {"ノーマル", "ほのお", "みず", "でんき", "くさ", "こおり", "かくとう", "どく", "じめん",
        "ひこう", "エスパー", "むし", "いわ", "ゴースト", "ドラゴン", "あく", "はがね", "フェアリー"};
    Boolean bl_Type2 = true;

    /**
     * types.xmlから一致するタイプ相性情報を取得する。 Type_First
     *
     * @param typeFirst
     * @param typeSecond
     * @return
     */
    public Map<String, String[]> Get_Types(String typeFirst, String typeSecond) {
        System.out.println("TargetType 1:" + typeFirst + ", 2:" + typeSecond);

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

                    for (String typeList1 : typeList) {
                        // タイプ相性の格納 <"タイプ名", "ダメージ相性"> 
                        map_TypeFirst.put(typeList1, ((Element) affinity.getElementsByTagName(typeList1).item(0)).getTextContent());
                    }
                }

                if (bl_Type2) {
                    // タイプ1、タイプ2を合わせたタイプ相性の結果を求める。
                    if (typeSecond.equals(((Element) typeInfo.getElementsByTagName("TypeName").item(0)).getTextContent())) {

                        for (String typeList2 : typeList) {
                            // タイプ相性の格納 <"タイプ名", "ダメージ相性">
                            map_TypeSecond.put(typeList2, ((Element) affinity.getElementsByTagName(typeList2).item(0)).getTextContent());

                        }
                    }
                }
            }
            map_Result = CalculateAffinity(map_TypeFirst, map_TypeSecond);
            return map_Result;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GetpData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map_Result;
    }

    /**
     * *
     * タイプ相性計算(タイプ1, タイプ2, とくせい)
     *
     * @param map_TypeFirst
     * @param map_TypeSecond
     * @return
     */
    public Map<String, String[]> CalculateAffinity(Map<String, String> map_TypeFirst, Map<String, String> map_TypeSecond) {

        double type1 = 0;
        double type2 = 0;
        double res;

        int typeValues = typeList.length;
        
        String[] x4 = new String[typeValues];
        String[] x2 = new String[typeValues];
        String[] x1 = new String[typeValues];
        String[] x05 = new String[typeValues];
        String[] x025 = new String[typeValues];
        String[] x0 = new String[typeValues];
        int k = 0;
        Map<String, String> map_Result = new HashMap<>();
//        String[][] ary_Result = null;
//        String[] ary_Correct = {"*4", "*2", "1/2", "1/4", "0"};

        for (int j = 0; j < typeValues; j++) {

            type1 = Double.parseDouble(map_TypeFirst.get(typeList[j]));
            if (bl_Type2) {
                type2 = Double.parseDouble(map_TypeSecond.get(typeList[j]));
            } else {
                type2 = 1.0;
            }
            res = type1 * type2;
            String sres = String.valueOf(res);
            switch (sres) {
                case "4.0":
                    x4[j] = typeList[j];
                    System.out.println(" 4.0 : " + x4[j]);
                    break;
                case "2.0":
                    x2[j] = typeList[j];
                    System.out.println(" 2.0 : " + x2[j]);
                    break;
                case "1.0":
                    x1[j] = typeList[j];
                    System.out.println(" 1.0 : " + x1[j]);
                    break;
                case "0.5":
                    x05[j] = typeList[j];
                    System.out.println(" 0.5 : " + x05[j]);
                    break;
                case "0.25":
                    x025[j] = typeList[j];
                    System.out.println(" 0.25 : " + x025[j]);
                    break;
                case "0":
                    x0[j] = typeList[j];
                    System.out.println(" 0 : " + x0[j]);
                    break;
            }
        }

        return null;

    }

}
