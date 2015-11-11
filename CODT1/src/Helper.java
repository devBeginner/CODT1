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
 */
public class Helper {

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

    public static Vector<Boolean> encode(String strText, HashMap<String, Vector<Boolean>> KeyToCode) {

        Vector<Boolean> vecBinMsg = new Vector<>();

        for (int i = 0; i < strText.length(); i++) {
            for (boolean b : KeyToCode.get(strText.substring(i, i))) {
                vecBinMsg.add(b);
            }
        }

        return vecBinMsg;

    }
    
}
