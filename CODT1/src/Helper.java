/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Daniel
 * 
 * FUNKTIONEN FUER DAS CONVERTIEREN
 * 
 */
public class Helper {

    // Wandelt den Binaeren String in einen Binären Vektor
    public static Vector<Boolean> convBinaryStringToVector(String strBin) {

        Vector<Boolean> vecBin = new Vector<Boolean>();

        for (int i = 0; i < strBin.length(); i++) {
            if (strBin.charAt(i) == '0') {
                vecBin.add(false);
            }
            if (strBin.charAt(i) == '1') {
                vecBin.add(true);
            }
        }

        return vecBin;
    }

    // Wandelt einen Binaeren Vektor in die Stringrepraesentation um
    public static String convVectorToBinaryString(Vector<Boolean> vecBin) {

        String strBin = "";

        for (int i = 0; i < vecBin.size(); i++) {
            if (vecBin.get(i) == false) {
                strBin += "0";
            }
            if (vecBin.get(i) == true) {
                strBin += "1";
            }
        }

        return strBin;
    }
    
//    /*
//    * Die Funktion nimmt den uebergebenen String und die Hashmap fuer das kodieren der
//    * Zeichen und konvertiert alle Zeichen des Strings in den jeweiligen binaeren Vektor
//    * und fuegt diesen zur Ausgabe hinzu
//    */
//    public static Vector<Boolean> encode(String strText, HashMap<String, Vector<Boolean>> KeyToCode) {
//        
//        // Binaerer Vektor fuer die Rueckgabe
//        Vector<Boolean> vecBinMsg = new Vector<>();
//        
//        // Alle Zeichen des Strings durchlaufen
//        for (int i = 0; i < strText.length(); i++) {
//            
//            // Mit dem Zeichen den zugehoerigen Binaervektor aus der Hashmap ermitteln
//            // und alle Bits des Vektors durchlaufen
//            for (boolean b : KeyToCode.get(strText.substring(i, i))) {
//                
//                // Das Bit zur Rueggabe hinzufuegen
//                vecBinMsg.add(b);
//            }
//        }
//        
//        // Geseamten Binären vektor zurueckgeben
//        return vecBinMsg;
//
//    }
//    
}
