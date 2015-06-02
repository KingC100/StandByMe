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
     * pData xmlからポケモンの情報を取得します。
     *
     * @param pName
     * @return
     */
    public static Map<String, String> Get_pData(String pName) {

        Map<String, String> map = new HashMap<>();

        // develop
        String pNumber = null;
        String pTypeFirst;
        String pTypeSecond;
        String pHP;
        String pA;
        String pB;
        String pC;
        String pD;
        String pS;
        String pSkillFirst;
        String pSkillSecond;
        String pSkillDream;

        DocumentBuilder builder;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            builder = dbfactory.newDocumentBuilder();
            Document doc = builder.parse(new FileInputStream("res\\pData.xml"));
            Element root = doc.getDocumentElement();

            /**
             * Nameで検索を掛け、 一致したノードのName取得
             */
            Element pokemon, types, individuals, skills;
            Element name, number, typeFirst, typeSecond, HP, A, B, C, D, S, skillFirst, skillSecond, skillDream;

            // <Pokemon>
            NodeList nodeList = root.getElementsByTagName("Pokemon");

            // <Pokemon>内で<Name>が一致するNodeの<Number>を取得する.
            for (int i = 0; i < nodeList.getLength(); i++) {

                // <Pokemon>ルート
                pokemon = (Element) root.getElementsByTagName("Pokemon").item(i);

                name = (Element) pokemon.getElementsByTagName("Name").item(0);

                // pName(入力)とNodeのNameが一致した場合、Numberを返す.
                if (pName.equals(name.getTextContent())) {
                    number = (Element) pokemon.getElementsByTagName("Number").item(0);

                    // <Types>ルート
                    types = (Element) pokemon.getElementsByTagName("Types").item(0);
                    typeFirst = (Element) types.getElementsByTagName("TypeFirst").item(0);
                    typeSecond = (Element) types.getElementsByTagName("TypeSecond").item(0);

                    // <Individuals>ルート
                    individuals = (Element) pokemon.getElementsByTagName("Individuals").item(0);
                    HP = (Element) individuals.getElementsByTagName("HP").item(0);
                    A = (Element) individuals.getElementsByTagName("A").item(0);
                    B = (Element) individuals.getElementsByTagName("B").item(0);
                    C = (Element) individuals.getElementsByTagName("C").item(0);
                    D = (Element) individuals.getElementsByTagName("D").item(0);
                    S = (Element) individuals.getElementsByTagName("S").item(0);

                    // <Skills>ルート
                    skills = (Element) pokemon.getElementsByTagName("Skills").item(0);
                    skillFirst = (Element) skills.getElementsByTagName("SkillFirst").item(0);
                    skillSecond = (Element) skills.getElementsByTagName("SkillSecond").item(0);
                    skillDream = (Element) skills.getElementsByTagName("SkillDream").item(0);

                    
                    // 特性1, 2が同一の場合は1のみ表示(元データによる都合)。
                    pSkillFirst = skillFirst.getTextContent();
                    pSkillSecond = skillSecond.getTextContent();
                    if(pSkillFirst.equals(pSkillSecond)){
                        pSkillSecond = "";
                    }

                    // Mapへ値を格納する。
                    map.put("Number", number.getTextContent());
                    map.put("Name", name.getTextContent());
                    map.put("TypeFirst", typeFirst.getTextContent());
                    map.put("TypeSecond", typeSecond.getTextContent());
                    map.put("HP", HP.getTextContent());
                    map.put("A", A.getTextContent());
                    map.put("B", B.getTextContent());
                    map.put("C", C.getTextContent());
                    map.put("D", D.getTextContent());
                    map.put("S", S.getTextContent());
                    map.put("SkillFirst", skillFirst.getTextContent());
                    map.put("SkillSecond", skillSecond.getTextContent());
                    map.put("SkillDream", skillDream.getTextContent());

                    pNumber = number.getTextContent();
                    pTypeFirst = typeFirst.getTextContent();
                    pTypeSecond = typeSecond.getTextContent();
                    pHP = HP.getTextContent();
                    pA = A.getTextContent();
                    pB = B.getTextContent();
                    pC = C.getTextContent();
                    pD = D.getTextContent();
                    pS = S.getTextContent();
//                    pSkillFirst = skillFirst.getTextContent();
//                    pSkillSecond = skillSecond.getTextContent();
                    pSkillDream = skillDream.getTextContent();

                    System.out.println(pNumber + "\n"
                      + pTypeFirst + "\n"
                      + pTypeSecond + "\n"
                      + pHP + "\n"
                      + pA + "\n"
                      + pB + "\n"
                      + pC + "\n"
                      + pD + "\n"
                      + pS + "\n"
                      + pSkillFirst + "\n"
                      + pSkillSecond + "\n"
                      + pSkillDream + "\n");

                    return map;
                }
            }

            /**
             * 一致無しの場合
             */
            // アイコンに？(0.png)の表示
//            pNumber = "0";
            map.put("Number", "0");

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex);
        }
        return map;
    }

}
