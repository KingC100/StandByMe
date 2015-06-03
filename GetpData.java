package standbyme;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
public class GetpData {

    /**
     * pData.xmlからポケモンの情報を取得する。
     *
     * @param pName
     * @return
     */
    public static Map<String, String> Get_pData(String pName) {

        Map<String, String> map = new HashMap<>();

        String pTypeSecond;

        DocumentBuilder builder;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            builder = dbfactory.newDocumentBuilder();
            Document doc = builder.parse(new FileInputStream("res\\pData.xml"));
            Element root = doc.getDocumentElement();


            // <Name>ノード対して検索を掛け、 pNameと一致したノードの基礎情報を取得する。
            Element pokemon, types, individuals, skills;

            // <Pokemon>ノード
            NodeList nodeList = root.getElementsByTagName("Pokemon");

            // <Pokemon>内で<Name>が一致するNodeの<Number>を取得する。
            for (int i = 0; i < nodeList.getLength(); i++) {

                // <Pokemon>ルート
                pokemon = (Element) root.getElementsByTagName("Pokemon").item(i);

                // pName(入力)とNodeのNameが一致した場合、Numberを返す。
                if (pName.equals(((Element) pokemon.getElementsByTagName("Name").item(0)).getTextContent())) {

                    // <Types>ルート
                    types = (Element) pokemon.getElementsByTagName("Types").item(0);

                    // <Individuals>ルート
                    individuals = (Element) pokemon.getElementsByTagName("Individuals").item(0);

                    // <Skills>ルート
                    skills = (Element) pokemon.getElementsByTagName("Skills").item(0);

                    // タイプ1, 2が同一の場合は1のみ表示(元データによる都合)。
                    pTypeSecond = ((Element) types.getElementsByTagName("TypeSecond").item(0)).getTextContent();
                    if (((Element) types.getElementsByTagName("TypeFirst").item(0)).getTextContent().equals(pTypeSecond)) {
                        pTypeSecond = "";
                    }
                    
                    // Mapへ値を格納する。
                    map.put("Number", ((Element) pokemon.getElementsByTagName("Number").item(0)).getTextContent());
                    map.put("Name", ((Element) pokemon.getElementsByTagName("Name").item(0)).getTextContent());
                    map.put("TypeFirst", ((Element) types.getElementsByTagName("TypeFirst").item(0)).getTextContent());
                    map.put("TypeSecond", pTypeSecond);
                    map.put("HP",((Element) individuals.getElementsByTagName("HP").item(0)).getTextContent());
                    map.put("A", ((Element) individuals.getElementsByTagName("A").item(0)).getTextContent());
                    map.put("B", ((Element) individuals.getElementsByTagName("B").item(0)).getTextContent());
                    map.put("C", ((Element) individuals.getElementsByTagName("C").item(0)).getTextContent());
                    map.put("D", ((Element) individuals.getElementsByTagName("D").item(0)).getTextContent());
                    map.put("S", ((Element) individuals.getElementsByTagName("S").item(0)).getTextContent());
                    map.put("SkillFirst", ((Element) skills.getElementsByTagName("SkillFirst").item(0)).getTextContent());
                    map.put("SkillSecond", ((Element) skills.getElementsByTagName("SkillSecond").item(0)).getTextContent());
                    map.put("SkillDream", ((Element) skills.getElementsByTagName("SkillDream").item(0)).getTextContent());
                    return map;
                }
            }
            // 一致なしの場合、0を返す。
            map.put("Number", "0");

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex);
        }
        return map;
    }

}
