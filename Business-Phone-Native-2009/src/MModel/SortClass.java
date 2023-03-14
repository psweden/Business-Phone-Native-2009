package MModel;

import java.io.IOException;

import javax.microedition.rms.*;

import MDataStore.DataBase_RMS;

/**
 * <p>Title: Mobile Extesion</p>
 *
 * <p>Description: All PBX Include</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: mobisma ab</p>
 *
 * @author Peter Albertsson
 * @version 2.0
 */
public class SortClass {


    // Vector för strängar.
    private String[] subStr;

    public String

    switchBoardNumber_PBX,
    setP_PBX,
    lineAccess_PBX,
    countryCode_PBX,
    call_This_Number = "";



    public SortClass() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            this.lineAccess_PBX = rms.getAccessNumber();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            this.countryCode_PBX = rms.getCountryCode();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.switchBoardNumber_PBX = rms.getSwitchBoardNumber();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.setP_PBX = rms.getDTMFsend();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }


        rms = null;
    }

    // Justerar landsiffra som är inmatad! Tar bort '+' och lägger in '00' före landssiffran
    public String checkCallNumber(String checkCallString) {

        MModel.InternNumber intern = new InternNumber();

            DataBase_RMS rms = null;
            try {
                rms = new DataBase_RMS();
            } catch (IOException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreException ex1) {
            }


        String plus = "+";
        String setNumberTo_00 = "00";
        String validateCallString = checkCallString;
        String validateCountryCode = this.countryCode_PBX;
        String setNumberTo_0 = "0";
        String callString = "";

        // --- kontrollerar om egna landsnummret består av '0046' gör om till '0'
        // --- Om '00' är lika med två första tecknen OCH landsnumret är lika med '46'
        if (setNumberTo_00.equals(validateCallString.substring(0, 2)) &&
            validateCountryCode.equals(validateCallString.substring(2, 4))) {

            String setString = validateCallString;
            // ta bort plast 0 - 3 ur strängen....
            String deletePartOfString = setString.substring(4);
            // sätt ihop strängen setStringTotal
            try {
                if (rms.getAccessNumber().equals("NONE")) {

                    this.lineAccess_PBX = "";

                }
            } catch (RecordStoreNotOpenException ex2) {
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreException ex2) {
            }
            String setStringTotal = lineAccess_PBX + setNumberTo_0 + deletePartOfString;

            String setCalledNumber = setNumberTo_0 + deletePartOfString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }

            callString = setStringTotal;
            // 00468293574 == 08293574 tex.

            return callString;

        }

        // --- kontrollerar om egna landsnummret består av '00358' gör om till '0'
        // --- Om '00' är lika med två första tecknen OCH landsnumret är lika med '358'
        else if (setNumberTo_00.equals(validateCallString.substring(0, 2)) &&
                 validateCountryCode.equals(validateCallString.substring(2, 5))) {

            String setString = validateCallString;
            // ta bort plast 0 - 3 ur strängen....
            String deletePartOfString = setString.substring(5);
            // sätt ihop strängen setStringTotal
            try {
                if (rms.getAccessNumber().equals("NONE")) {

                    this.lineAccess_PBX = "";

                }
            } catch (RecordStoreNotOpenException ex2) {
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreException ex2) {
            }

            String setStringTotal = lineAccess_PBX + setNumberTo_0 + deletePartOfString;

            String setCalledNumber = setNumberTo_0 + deletePartOfString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }


            callString = setStringTotal;
            // 003588293574 == 08293574 tex.
            return callString;

        }

        // Om numret innehåller 1 - 5 siffror så är det ett internnummer.
        if (validateCallString.equals(validateCallString.substring(0, 1)) ||
            validateCallString.equals(validateCallString.substring(0, 2)) ||
            validateCallString.equals(validateCallString.substring(0, 3)) ||
            validateCallString.equals(validateCallString.substring(0, 4)) ||
            validateCallString.equals(validateCallString.substring(0, 5))) {

            callString = validateCallString;

            String setCalledNumber = validateCallString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }


            return callString;

        }
        // Om numret startar med '+' OCH '358' är sann så gör om till '0'.
        if (plus.equals(validateCallString.substring(0, 1)) &&
            validateCountryCode.equals(validateCallString.substring(1, 4))) { // Om 358 == 358

            String setString = validateCallString;

            String deletePartOfString = setString.substring(4); // ta bort plast 0 - 3 ur strängen....

            try {
                if (rms.getAccessNumber().equals("NONE")) {

                    this.lineAccess_PBX = "";

                }
            } catch (RecordStoreNotOpenException ex2) {
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreException ex2) {
            }

            String setStringTotal = lineAccess_PBX + setNumberTo_0 + deletePartOfString;

            String setCalledNumber = setNumberTo_0 + deletePartOfString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }


            callString = setStringTotal;

            return callString;

        }

        // Om numret startar med '+' OCH countryCode är lika med '46' så gör om till '0'.
        else if (plus.equals(validateCallString.substring(0, 1)) &&
                 validateCountryCode.equals(validateCallString.substring(1, 3))) {


            String setString = validateCallString;

            String deletePartOfString = setString.substring(3); // ta bort plast 0 - 3 ur strängen....

            try {
                if (rms.getAccessNumber().equals("NONE")) {

                    this.lineAccess_PBX = "";

                }
            } catch (RecordStoreNotOpenException ex2) {
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreException ex2) {
            }

            String setStringTotal = lineAccess_PBX + setNumberTo_0 + deletePartOfString;

            String setCalledNumber = setNumberTo_0 + deletePartOfString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }


            callString = setStringTotal;

            return callString;

        }

        // Om numret startar med '+' OCH countryCode är INTE lika med '46' så gör om till '0047'.
        else if (plus.equals(validateCallString.substring(0, 1)) &&
                 !validateCountryCode.equals(validateCallString.substring(1, 3))) {

            String setString = validateCallString;

            String deletePartOfString = setString.substring(1); // ta bort plats 0 - 1 ur strängen....

            try {
                if (rms.getAccessNumber().equals("NONE")) {

                    this.lineAccess_PBX = "";

                }
            } catch (RecordStoreNotOpenException ex2) {
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreException ex2) {
            }

            String setStringTotal = lineAccess_PBX + setNumberTo_00 + deletePartOfString;

            String setCalledNumber = setNumberTo_00 + deletePartOfString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }


            callString = setStringTotal;

            return callString;

        }
        // Om numret inte liknar '+' så är det riktnummer + telefonnummer.
        if (!plus.equals(validateCallString.substring(0, 1))) {

            try {
                if (rms.getAccessNumber().equals("NONE")) {

                    this.lineAccess_PBX = "";

                }
            } catch (RecordStoreNotOpenException ex2) {
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreException ex2) {
            }

            callString = lineAccess_PBX + validateCallString;

            String setCalledNumber = validateCallString;
            try {
                intern.replaceDialledNumber(setCalledNumber);
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            } catch (IOException ex) {
            }

            return callString;

        }
        rms = null;
        intern = null;
        return callString;
    }


    // Metoden sorterar bort tecken och visar siffror
    public String sortCharToDigits(String s) {

        String sString = s;

        StringBuffer bTecken = new StringBuffer(sString); // Lägg strängen sortString i ett stringbuffer objekt...

        for (int i = 0; i < bTecken.length(); i++) { // räkna upp hela bTecken-strängens innehåll hela dess längd

            char tecken = bTecken.charAt(i); // char tecken är innehållet i hela längden

            /* Tecknen täcker >> Tyska, Dutch, Svenska, Engelska, Norska, Danska.
                Spanska, Italenska, Finska, Franska */

            if ('A' <= tecken && tecken <= 'Z' ||
                'a' <= tecken && tecken <= 'z'
                || tecken == '-' || tecken == '/' || tecken == '\\' ||
                tecken == ':' || tecken == ';'
                || tecken == '.' || tecken == ',' || tecken == '_' ||
                tecken == '|' || tecken == '<'
                || tecken == '>' || tecken == '+' || tecken == '(' ||
                tecken == ')' || tecken == 'å' || tecken == 'Å' ||
                tecken == 'ä' || tecken == 'Ä' || tecken == 'ö' ||
                tecken == 'Ö' ||
                tecken == 'Ü' || tecken == 'ü' || tecken == 'ß' ||
                tecken == 'Æ' || tecken == 'æ' || tecken == 'Ø' ||
                tecken == 'á' || tecken == 'Á' || tecken == 'é' ||
                tecken == 'É' || tecken == 'í' || tecken == 'Í' ||
                tecken == 'œ' || tecken == 'Œ' || tecken == 'š' ||
                tecken == 'Š' || tecken == 'ž' || tecken == 'Ž' ||
                tecken == 'ó' || tecken == 'Ó' || tecken == 'ú' ||
                tecken == 'Ú' || tecken == 'ñ' || tecken == 'Ñ' ||
                tecken == 'ø') {

                bTecken.setCharAt(i, ' '); // lägg in blanksteg i strängen där något av ovanstående tecken finns....
            }

        }

        bTecken.append(' '); // lägger till blanksteg sist i raden så att sista kommer med för att do-satsen ska kunna hitta och sortera...

        String setString = new String(bTecken); // Gör om char-strängen till en string-sträng

        int antal = 0;
        char separator = ' '; // för att kunna sortera i do-satsen

        int index = 0;

        do { // do satsen sorterar ut blankstegen och gör en ny sträng för att jämföra IMEI med...
            ++antal;
            ++index;

            index = setString.indexOf(separator, index);
        } while (index != -1);

        subStr = new String[antal];
        index = 0;
        int slutindex = 0;

        for (int j = 0; j < antal; j++) {

            slutindex = setString.indexOf(separator, index);

            if (slutindex == -1) {
                subStr[j] = setString.substring(index);
            }

            else {
                subStr[j] = setString.substring(index, slutindex);
            }

            index = slutindex + 1;

        }
        String setNumber = "";
        for (int k = 0; k < subStr.length; k++) {

            setNumber += subStr[k]; // Lägg in värdena från subStr[k] i strängen setNumber....
        }

        // System.out.println("Sorterad: " + setNumber);

        String sortedString = setNumber;

        return sortedString;
    }

    // Metoden sorterar bort siffror och 'vissa tecken'
    public String sortDigitsToCharacter(String s) {

        String sString = s;

        StringBuffer bTecken = new StringBuffer(sString); // Lägg strängen sortString i ett stringbuffer objekt...

        for (int i = 0; i < bTecken.length(); i++) { // räkna upp hela bTecken-strängens innehåll hela dess längd

            char tecken = bTecken.charAt(i); // char tecken är innehållet i hela längden

            // Sorterar bort följande tecken.
            if (tecken == '-' || tecken == '/' || tecken == '\\' ||
                tecken == ':' || tecken == ';'
                || tecken == '.' || tecken == ',' || tecken == '_' ||
                tecken == '|' || tecken == '<'
                || tecken == '>' || tecken == '+' || tecken == '(' ||
                tecken == ')' || tecken == '0' || tecken == '1' ||
                tecken == '2' || tecken == '3' || tecken == '4' ||
                tecken == '5' ||
                tecken == '6' || tecken == '7' || tecken == '8' ||
                tecken == '9' || tecken == '*' || tecken == '#') {

                bTecken.setCharAt(i, ' '); // lägg in blanksteg i strängen där något av ovanstående tecken finns....
            }

        }

        bTecken.append(' '); // lägger till blanksteg sist i raden så att sista kommer med för att do-satsen ska kunna hitta och sortera...

        String setString = new String(bTecken); // Gör om char-strängen till en string-sträng

        int antal = 0;
        char separator = '*'; // för att kunna sortera i do-satsen

        int index = 0;

        do { // do satsen sorterar ut blankstegen och gör en ny sträng för att jämföra IMEI med...
            ++antal;
            ++index;

            index = setString.indexOf(separator, index);
        } while (index != -1);

        subStr = new String[antal];
        index = 0;
        int slutindex = 0;

        for (int j = 0; j < antal; j++) {

            slutindex = setString.indexOf(separator, index);

            if (slutindex == -1) {
                subStr[j] = setString.substring(index);
            }

            else {
                subStr[j] = setString.substring(index, slutindex);
            }

            index = slutindex + 1;

        }
        String setNumber = "";
        for (int k = 0; k < subStr.length; k++) {

            setNumber += subStr[k]; // Lägg in värdena från subStr[k] i strängen setNumber....
        }

        // System.out.println("Sorterad: " + setNumber);

        String sortedString = setNumber;

        return sortedString;
    }


}
