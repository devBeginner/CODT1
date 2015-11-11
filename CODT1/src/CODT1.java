/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Vector;


/**
 *
 * @author Daniel
 */
public class CODT1 {

    public static final boolean bTestOutput = false;

    private static final String strText
            = "es war einmal eine kleine suesse dirn, die hatte jedermann lieb,\n"
            + "der sie nur ansah, am allerliebsten aber ihre grossmutter, die\n"
            + "wusste gar nicht, was sie alles dem kind geben sollte. einmal\n"
            + "schenkte sie ihm ein kaeppchen von rothem sammet, und\n"
            + "weil ihm das so wohl stand, und es nichts anders mehr tragen\n"
            + "wollte, hiess es nur das rohtkaeppchen.";

    private static String strTest;

    private static String strInfSource;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Aufgabe 1
        if (false) {
            strTest = strText;
            HuffmanCode huff = new HuffmanCode();
            huff.createInfSrcFromText(strText);

            Vector<Boolean> vecCode = huff.encodeString(strTest);
            String strDec = huff.decodeBitVector(vecCode);

            System.out.println("The String:\n" + strTest + "\nproduces the code:\n" + Helper.convVectorToBinaryString(vecCode));
            System.out.println("\nThe code:\n" + Helper.convVectorToBinaryString(vecCode) + "\nproduces the text:\n" + strDec);
            System.out.println("\nThe entropy is: " + huff.getEntropy());
            System.out.println("The average length is: " + huff.getAvgLength());
            System.out.println("\nThe encoding map is:\n" + huff.getEncodeMap());
        }

        // AUFGABE 2
        if (true) {
            strTest = "14203214221013124123032000000000000000000";
            strInfSource = "0->0.5;1->0.1;2->0.05;3->0.25;4->0.1";
            HuffmanCode huff = new HuffmanCode();
            huff.parseInfSourceFromInput(strInfSource);

            Vector<Boolean> vecCode = huff.encodeString(strTest);
            String strDec = huff.decodeBitVector(vecCode);
            
            System.out.println("The String:\n" + strTest + "\nproduces the code:\n" + Helper.convVectorToBinaryString(vecCode));
            System.out.println("\nThe code:\n" + Helper.convVectorToBinaryString(vecCode) + "\nproduces the text:\n" + strDec);
            System.out.println("\nThe entropy is: " + huff.getEntropy());
            System.out.println("The average length is: " + huff.getAvgLength());
            System.out.println("\nThe encoding map is:\n" + huff.getEncodeMap());
        }
    }

}
