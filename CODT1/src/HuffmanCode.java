/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Daniel
 */
public class HuffmanCode {

    private HashMap<String, Vector<Boolean>> encodeMap;
    private ArrayList<Entry> entries;
    private ArrayList<BinaryTree<Entry>> nodes;
    private BinaryTree<Entry> treeRoot;

    {
        encodeMap = new HashMap<>();
        entries = new ArrayList<>();
        nodes = new ArrayList<>();
    }

        
    public void createInfSrcFromText(String strText) {

        // neue Liste erzeugen
        entries = new ArrayList<>();
        HashMap<Character, Entry> chars = new HashMap<>();

        // alle Zeichen des Übergebenen Textes durchlaufen und haefigkeit des Vorkommens ermitteln
        for (int i = 0; i < strText.length(); i++) {
            // Zeichen ermitteln
            char chrCurr = strText.charAt(i);

            // Wenn das Zeichen einen Eintrag in der Map hat
            if (chars.containsKey(chrCurr)) {
                
                // Den Wert laden, erhöhen und zurückspeichern
                chars.get(chrCurr).count++;
                
            } else {
                Entry e = new Entry(String.valueOf(chrCurr), 0d);
                e.count = 1;
                // Eintrag erstellen
                chars.put(chrCurr, e);
                entries.add(e);
            }
        }
        // Wahrscheinlichkeiten berechnen
        for ( Entry e : entries){
            e.value = (double)e.count / (double)strText.length();
        }

        // Liste der Eintraege sortieren
        Collections.sort(entries);

        // Huffmancode erstellen
        generateHuffmanCode();

        //TESTAUSGABE
        if (CODT1.bTestOutput) {
            for (Entry key : entries) {
                System.out.println(key);
            }
        }

    }
    
    public String getEncodeMap(){
        
        String strRet = "";
        
        for ( Entry e : entries ){
            strRet += e.toString() + "\n";
        }
        
        return strRet;
    }

    public String decodeBitVector(Vector<Boolean> vecCode) {

        String strRet = "";
        BinaryTree<Entry> node = treeRoot;

        for (boolean b : vecCode) {
            
            // Wenn das bit 0 ist, nach links gehen, sonst nach rechts
            if (b == false) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
            
            // wenn ein Blatt erreicht wurde, den Key zur rueckgabe hinzufügen 
            // und dann den Knotenzeiger wieder auf die Wurzel setzen
            if (node.isLeaf()) {
                strRet += node.getValue().key;
                node = treeRoot;
            } 
        }

        return strRet;
    }

    // encoded den übergebenen String
    public Vector<Boolean> encodeString(String strMsg) {

        Vector<Boolean> vecMsgCode = new Vector<>();

        for (int i = 0; i < strMsg.length(); i++) {

            String chr = String.valueOf(strMsg.charAt(i));
            for (boolean b : encodeMap.get(chr)) {
                vecMsgCode.add(b);
            }

        }

        return vecMsgCode;

    }

    public void parseInfSourceFromInput(String strInput) {

        // Map für die einzelnen Chars
        entries = new ArrayList<>();
        HashMap<String, Double> mapInfSource = new HashMap<>();

        // Alle Eintraege durchlaufen
        for (String strEntry : strInput.split(";")) {

            // Key und Value ermitteln
            String strKey = strEntry.split("->")[0];
            double dblVal = Double.valueOf(strEntry.split("->")[1]);

            if (mapInfSource.get(strKey) == null && !strKey.equals("") && dblVal > 0) {

                // Eintrag zur Map hinzufuegen
                mapInfSource.put(strKey, dblVal);
                entries.add(new Entry(strKey, dblVal));

            }
        }

        // sortieren nahc Wahrscheinlichkeit
        Collections.sort(entries);
        
        generateHuffmanCode();
        
        //TESTAUSGABE
        if (CODT1.bTestOutput) {
            for (Entry key : entries) {
                System.out.println(key);
            }
        }
    }

    public double getEntropy() {

        double dblRet = 0;

        for (Entry e : entries) {
            dblRet += e.getEntropy();
        }

        return dblRet;
    }
    
    public double getAvgLength(){
        double dblRet = 0;

        for (Entry e : entries) {
            dblRet += e.value * e.code.size();
        }

        return dblRet;
    }
    
    private void generateHuffmanCode() {

        // KnotenListe erstellen
        ArrayList<Entry> copy = new ArrayList<>();

        HashMap<String, BinaryTree<Entry>> treeMap = new HashMap<>();

        // Knoten für alle Einträge erstellen und kopieren
        for (Entry e : entries) {
            e.node = new BinaryTree<>(e);

            treeMap.put(e.key, e.node);

            Entry cpy = new Entry(e.key, e.value);
            cpy.node = e.node;
            copy.add(cpy);
        }

        // Solange neue Bäume erstellen, bis nur nochh zwei übrig sind 
        while (copy.size() > 1) {

            // Baum erstellen mit summierten werten und dafür einen Entry erstellen
            treeRoot = new BinaryTree<>(
                    new Entry(copy.get(0).key + copy.get(1).key, copy.get(0).value + copy.get(1).value));
            treeRoot.setLeft(treeMap.get(copy.get(0).key));
            treeRoot.setRight(treeMap.get(copy.get(1).key));
            treeMap.put(copy.get(0).key + copy.get(1).key, treeRoot);

            // Die beiden entries aus der kopie löschen und den summierten entry hinzufuegen
            copy.remove(1);
            copy.remove(0);
            copy.add(treeRoot.getValue());

            // entraege neu sortieren
            Collections.sort(copy);

        }

        // Die Codewörter ermitteln und in den Entries eintragen
        calculateCode(treeRoot);
        generateEncodingMap(treeRoot);

        //TESTAUSGABE
        System.out.println(treeRoot.toString());

    }

    // Funktion, die rekursiv alle Knoten durchläuft und die Keys und zugehörifgen
    // Codes (bool-Vektoren) zu einer Hashmap hinzufügt, die für das codieren verwendet wird.
    private void generateEncodingMap(BinaryTree<Entry> root) {

        // Bei einem Blattknoten den key und bool Vektor in der Hashmap speichern
        // und dann die Funktion beenden
        if (root.isLeaf()) {
            encodeMap.put(root.getValue().key, root.getValue().code);
            return;
        }

        // Wenn ein Linker knoten existiert, hierfür die Funktion rekursiv aufrufen
        if (root.hasLeft()) {
            generateEncodingMap(root.getLeft());
        }

        // Wenn ein rechter knoten existiert, hierfür die Funktion rekursiv aufrufen
        if (root.hasRight()) {
            generateEncodingMap(root.getRight());
        }
    }

    // Startfunktion für das Rekursive durchlaufen der Blätter und das zusammensetzen der Codes
    private static BinaryTree<Entry> calculateCode(BinaryTree<Entry> root) {

        // Wenn es kein Blatt ist
        if (root.isInner()) {

            // Wenn es ein Linkes Blatt gibt
            if (root.hasLeft()) {

                // In allen Blattknoten unterhalb des linken Blattes ein false (0) hinzufügen
                // Sodass deren erstes Bit eine 0 enthält
                calculateCode(root.getLeft(), false);

                // Dann diese funktion für das Linke Blatt aufrufen
                calculateCode(root.getLeft());
            }

            // Analog zum linken Blatt, falls ein rechter Knoten existiert
            if (root.hasRight()) {
                calculateCode(root.getRight(), true);
                calculateCode(root.getRight());
            }
        }
        return root;
    }

    // Funktion, die das übergebene bit in allen Codes der darunterliegenden Blattknoten 
    // hinzufügt. 
    private static void calculateCode(BinaryTree<Entry> root, boolean bAddBit) {

        // Wenn es kein Blatt ist, dann das Bit an die Kinder weitergeben
        // sofern sie existieren
        if (root.isInner()) {
            if (root.hasLeft()) {
                calculateCode(root.getLeft(), bAddBit);
            }
            if (root.hasRight()) {
                calculateCode(root.getRight(), bAddBit);
            }

            // Wenn es ein Blattknoten ist, dann das übergebene Bit dem eintrag hinzufügen
        } else {
            // Bit vorne an den Vektor anhängen
            root.getValue().code.add(bAddBit);
        }
    }
}
