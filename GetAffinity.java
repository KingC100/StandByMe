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

    /**
     * types.xmlから一致するタイプ相性情報を取得する。 Type_First
     *
     * @param typeFirst
     * @param typeSecond
     * @return
     */
    public Map<String, String> Get_Types(String typeFirst, String typeSecond) {
        System.out.println("TargetType 1:" + typeFirst + ", 2:" + typeSecond);

        Map<String, String> map_TypeFirst = new HashMap<>();
        Map<String, String> map_TypeSecond = new HashMap<>();
        Map<String, String> map_Result = new HashMap<>();

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

                // タイプ1、タイプ2を合わせたタイプ相性の結果を求める。
                if (typeSecond.equals(((Element) typeInfo.getElementsByTagName("TypeName").item(0)).getTextContent())) {

                    for (String typeList2 : typeList) {
                        // タイプ相性の格納 <"タイプ名", "ダメージ相性">
                        map_TypeSecond.put(typeList2, ((Element) affinity.getElementsByTagName(typeList2).item(0)).getTextContent());

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
    /***
     * タイプ相性計算(タイプ1, タイプ2, とくせい)
     * @param map_TypeFirst
     * @param map_TypeSecond
     * @return 
     */
    public Map<String, String> CalculateAffinity(Map<String, String> map_TypeFirst, Map<String, String> map_TypeSecond) {
        double type1;
        double type2;
        double res;
        Map<String, String> map_Result = new HashMap<>();
        for (int j = 0; j < typeList.length; j++) {

            type1 = Double.parseDouble(map_TypeFirst.get(typeList[j]));
            type2 = Double.parseDouble(map_TypeSecond.get(typeList[j]));
            res = type1 * type2;
            map_Result.put(typeList[j], String.valueOf(res));

            System.out.println(typeList[j] + " : " + map_Result.get(typeList[j]));

//                        System.out.println(typeList[j]);
//            System.out.println("typeSecond : " + typeSecond);
//            System.out.println(" <- " + typeList[k] + " : " + map_TypeSecond.get(typeList[k]));
        }

        return map_Result;

    }

    /**
     * skill.xmlから特性に寄るタイプ相性情報を取得する。
     */
}
