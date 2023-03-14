package MModel;

import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import MDataStore.DataBase_RMS;
import java.io.IOException;
import javax.microedition.rms.RecordStoreException;

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
public class InternNumber {

    private MModel.Language language;

    public InternNumber() {

    }

    public void setNameDynamicAtribute(String s) throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String setLanguage = s;

        // --- English, prg är satt till engelsk-språk.
        if (rms.getLanguage().equals("2")) {

            // Om det är svenska 'Lägg till', så ändra till engelska.
            if (rms.getInternName(29).equals("0") ||
                rms.getInternName(29).equals(language.genDefaultEdit_2)) {

                rms.setInternName(setLanguage, 29);
            } // Om det inte är 'Add new' eller 'Lägg till', så 'Låt stå kvar' (else)

            if (rms.getInternName(30).equals("0") ||
                rms.getInternName(30).equals(language.genDefaultEdit_2)) {

                rms.setInternName(setLanguage, 30);
            }


        }

        else if (rms.getLanguage().equals("0") || rms.getLanguage().equals("1")
                 || rms.getLanguage().equals("3") ||
                 rms.getLanguage().equals("4")
                 || rms.getLanguage().equals("5") ||
                 rms.getLanguage().equals("6")
                 || rms.getLanguage().equals("7") ||
                 rms.getLanguage().equals("8")
                 || rms.getLanguage().equals("9")) {

            // Om det är engelska 'Add new', så ändra till svenska.
            if (rms.getInternName(29).equals("0") ||
                rms.getInternName(29).equals(language.genDefaultEdit_1)) {

                rms.setInternName(setLanguage, 29);
            } // Om det inte är 'Lägg till' eller 'Add new', så 'Låt stå kvar' (else)

            if (rms.getInternName(30).equals("0") ||
                rms.getInternName(30).equals(language.genDefaultEdit_1)) {

                rms.setInternName(setLanguage, 30);
            }


        }


        rms = null;
    }

    public void setInternButtonName(String s) throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String extensionDefaultAddNew_DEF = s;

        // --- English, prg är satt till engelsk-språk.
        if (rms.getLanguage().equals("2")) {

            // Om det är svenska 'Lägg till', så ändra till engelska.
            if (rms.getInternName(51).equals("0") ||
                rms.getInternName(51).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 51);
            } // Om det inte är 'Add new' eller 'Lägg till', så 'Låt stå kvar' (else)

            if (rms.getInternName(52).equals("0") ||
                rms.getInternName(52).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 52);
            }

            if (rms.getInternName(53).equals("0") ||
                rms.getInternName(53).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 53);

            }

            if (rms.getInternName(54).equals("0") ||
                rms.getInternName(54).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 54);
            }

            if (rms.getInternName(55).equals("0") ||
                rms.getInternName(55).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 55);
            }

            if (rms.getInternName(56).equals("0") ||
                rms.getInternName(56).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 56);
            }

            if (rms.getInternName(57).equals("0") ||
                rms.getInternName(57).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 57);
            }

            if (rms.getInternName(58).equals("0") ||
                rms.getInternName(58).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 58);
            }

            if (rms.getInternName(59).equals("0") ||
                rms.getInternName(59).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 59);
            }

            if (rms.getInternName(60).equals("0") ||
                rms.getInternName(60).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 60);
            }

            if (rms.getInternName(61).equals("0") ||
                rms.getInternName(61).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 61);
            }

            if (rms.getInternName(62).equals("0") ||
                rms.getInternName(62).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 62);
            }

            if (rms.getInternName(63).equals("0") ||
                rms.getInternName(63).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 63);
            }

            if (rms.getInternName(64).equals("0") ||
                rms.getInternName(64).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 64);
            }

            if (rms.getInternName(65).equals("0") ||
                rms.getInternName(65).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 65);
            }

            if (rms.getInternName(66).equals("0") ||
                rms.getInternName(66).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 66);
            }

            if (rms.getInternName(67).equals("0") ||
                rms.getInternName(67).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 67);
            }

            if (rms.getInternName(68).equals("0") ||
                rms.getInternName(68).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 68);
            }

            if (rms.getInternName(69).equals("0") ||
                rms.getInternName(69).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 69);
            }

            if (rms.getInternName(70).equals("0") ||
                rms.getInternName(70).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 70);
            }

            if (rms.getInternName(71).equals("0") ||
                rms.getInternName(71).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 71);
            }

            if (rms.getInternName(72).equals("0") ||
                rms.getInternName(72).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 72);
            }

            if (rms.getInternName(73).equals("0") ||
                rms.getInternName(73).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 73);
            }

            if (rms.getInternName(74).equals("0") ||
                rms.getInternName(74).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 74);
            }

            if (rms.getInternName(75).equals("0") ||
                rms.getInternName(75).equals(language.extensionDefaultAddNew_2)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 75);
            }
        }

        else if (rms.getLanguage().equals("0") || rms.getLanguage().equals("1")
                 || rms.getLanguage().equals("3") ||
                 rms.getLanguage().equals("4")
                 || rms.getLanguage().equals("5") ||
                 rms.getLanguage().equals("6")
                 || rms.getLanguage().equals("7") ||
                 rms.getLanguage().equals("8")
                 || rms.getLanguage().equals("9")) {

            // Om det är engelska 'Add new', så ändra till svenska.
            if (rms.getInternName(51).equals("0") ||
                rms.getInternName(51).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 51);
            } // Om det inte är 'Lägg till' eller 'Add new', så 'Låt stå kvar' (else)

            if (rms.getInternName(52).equals("0") ||
                rms.getInternName(52).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 52);
            }

            if (rms.getInternName(53).equals("0") ||
                rms.getInternName(53).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 53);
            }

            if (rms.getInternName(54).equals("0") ||
                rms.getInternName(54).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 54);
            }

            if (rms.getInternName(55).equals("0") ||
                rms.getInternName(55).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 55);
            }

            if (rms.getInternName(56).equals("0") ||
                rms.getInternName(56).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 56);
            } // Om det inte är 'Lägg till' eller 'Add new', så 'Låt stå kvar' (else)

            if (rms.getInternName(57).equals("0") ||
                rms.getInternName(57).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 57);
            }

            if (rms.getInternName(58).equals("0") ||
                rms.getInternName(58).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 58);
            }

            if (rms.getInternName(59).equals("0") ||
                rms.getInternName(59).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 59);
            }

            if (rms.getInternName(60).equals("0") ||
                rms.getInternName(60).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 60);
            }

            if (rms.getInternName(61).equals("0") ||
                rms.getInternName(61).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 61);
            }

            if (rms.getInternName(62).equals("0") ||
                rms.getInternName(62).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 62);
            }

            if (rms.getInternName(63).equals("0") ||
                rms.getInternName(63).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 63);
            }

            if (rms.getInternName(64).equals("0") ||
                rms.getInternName(64).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 64);
            }

            if (rms.getInternName(65).equals("0") ||
                rms.getInternName(65).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 65);
            }

            if (rms.getInternName(66).equals("0") ||
                rms.getInternName(66).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 66);
            }

            if (rms.getInternName(67).equals("0") ||
                rms.getInternName(67).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 67);
            }

            if (rms.getInternName(68).equals("0") ||
                rms.getInternName(68).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 68);
            }

            if (rms.getInternName(69).equals("0") ||
                rms.getInternName(69).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 69);
            }

            if (rms.getInternName(70).equals("0") ||
                rms.getInternName(70).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 70);
            }

            if (rms.getInternName(71).equals("0") ||
                rms.getInternName(71).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 71);
            }

            if (rms.getInternName(72).equals("0") ||
                rms.getInternName(72).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 72);
            }

            if (rms.getInternName(73).equals("0") ||
                rms.getInternName(73).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 73);
            }

            if (rms.getInternName(74).equals("0") ||
                rms.getInternName(74).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 74);
            }

            if (rms.getInternName(75).equals("0") ||
                rms.getInternName(75).equals(language.extensionDefaultAddNew_1)) {

                rms.setInternName(extensionDefaultAddNew_DEF, 75);
            }

        }

        rms = null;
    }

    public void replaceDialledNumber(String s) throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();
        MModel.Date_Time date = new Date_Time();

        String dialledNumbers = s;
        String check_0 = "";
        String time = "\n" + date.getTime();

        String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "",
                s6 = "", s7 = "", s8 = "", s9 = "", s10 = "",
                s11 = "", s12 = "", s13 = "", s14 = "", s15 = "",
                s16 = "", s17 = "", s18 = "", s19 = "", s20 = "",
                s21 = "", s22 = "", s23 = "", s24 = "", s25 = "";

        // om plats 76 är lika med '0' skriv in värdet på plats 76.
        check_0 = rms.getInternName(76);

        if (check_0.equals("0")) {
            time = "\n" + date.getTime();
            rms.setInternName(dialledNumbers + time, 76);

        }

        /* skriv in s på första plats '76' OCH förflytta alla andra neråt ett steg,
           tills plats 100. (plats 100 skrivs över) */
        else if (!check_0.equals("0")) {

            time = "\n" + date.getTime();
            s1 = rms.getInternName(76);
            rms.setInternName(dialledNumbers + time, 76);

            s2 = rms.getInternName(77);
            rms.setInternName(s1, 77);

            s3 = rms.getInternName(78);
            rms.setInternName(s2, 78);

            s4 = rms.getInternName(79);
            rms.setInternName(s3, 79);

            s5 = rms.getInternName(80);
            rms.setInternName(s4, 80);

            s6 = rms.getInternName(81);
            rms.setInternName(s5, 81);

            s7 = rms.getInternName(82);
            rms.setInternName(s6, 82);

            s8 = rms.getInternName(83);
            rms.setInternName(s7, 83);

            s9 = rms.getInternName(84);
            rms.setInternName(s8, 84);

            s10 = rms.getInternName(85);
            rms.setInternName(s9, 85);

            s11 = rms.getInternName(86);
            rms.setInternName(s10, 86);

            s12 = rms.getInternName(87);
            rms.setInternName(s11, 87);

            s13 = rms.getInternName(88);
            rms.setInternName(s12, 88);

            s14 = rms.getInternName(89);
            rms.setInternName(s13, 89);

            s15 = rms.getInternName(90);
            rms.setInternName(s14, 90);

            s16 = rms.getInternName(91);
            rms.setInternName(s15, 91);

            s17 = rms.getInternName(92);
            rms.setInternName(s16, 92);

            s18 = rms.getInternName(93);
            rms.setInternName(s17, 93);

            s19 = rms.getInternName(94);
            rms.setInternName(s18, 94);

            s20 = rms.getInternName(95);
            rms.setInternName(s19, 95);

            s21 = rms.getInternName(96);
            rms.setInternName(s20, 96);

            s22 = rms.getInternName(97);
            rms.setInternName(s21, 97);

            s23 = rms.getInternName(98);
            rms.setInternName(s22, 98);

            s24 = rms.getInternName(99);
            rms.setInternName(s23, 99);

            s25 = rms.getInternName(100);
            rms.setInternName(s24, 100);

        }
        date = null;
        rms = null;
    }


    public String getPerson(int p) throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {
        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String person = "";
        int get_id = getID(p);

        person = rms.getInternName(get_id);

        rms = null;

        return person;
    }

    public int getID(int p) {

        int ID_p = p;

        if (ID_p == 0) {

            return 76;
        }
        if (ID_p == 1) {

            return 77;
        }
        if (ID_p == 2) {

            return 78;
        }
        if (ID_p == 3) {

            return 79;
        }
        if (ID_p == 4) {

            return 80;
        }
        if (ID_p == 5) {

            return 81;
        }
        if (ID_p == 6) {

            return 82;
        }
        if (ID_p == 7) {

            return 83;
        }
        if (ID_p == 8) {

            return 84;
        }
        if (ID_p == 9) {

            return 85;
        }
        if (ID_p == 10) {

            return 86;
        }
        if (ID_p == 11) {

            return 87;
        }
        if (ID_p == 12) {

            return 88;
        }
        if (ID_p == 13) {

            return 89;
        }
        if (ID_p == 14) {

            return 90;
        }

        if (ID_p == 15) {

            return 91;
        }
        if (ID_p == 16) {

            return 92;
        }
        if (ID_p == 17) {

            return 93;
        }
        if (ID_p == 18) {

            return 94;
        }
        if (ID_p == 19) {

            return 95;
        }

        if (ID_p == 20) {

            return 96;
        }
        if (ID_p == 21) {

            return 97;
        }
        if (ID_p == 22) {

            return 98;
        }
        if (ID_p == 23) {

            return 99;
        }
        if (ID_p == 24) {

            return 100;
        }

        return 0;
    }

    public String getInternPerson(int p) throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {
        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String person = "";
        int get_id = getInternID(p);

        person = rms.getInternName(get_id);

        rms = null;

        return person;
    }

    public int getInternID(int p) {

        int ID_p = p;

        if (ID_p == 0) {

            return 51;
        }
        if (ID_p == 1) {

            return 52;
        }
        if (ID_p == 2) {

            return 53;
        }
        if (ID_p == 3) {

            return 54;
        }
        if (ID_p == 4) {

            return 55;
        }
        if (ID_p == 5) {

            return 56;
        }
        if (ID_p == 6) {

            return 57;
        }
        if (ID_p == 7) {

            return 58;
        }
        if (ID_p == 8) {

            return 59;
        }
        if (ID_p == 9) {

            return 60;
        }
        if (ID_p == 10) {

            return 61;
        }
        if (ID_p == 11) {

            return 62;
        }
        if (ID_p == 12) {

            return 63;
        }
        if (ID_p == 13) {

            return 64;
        }
        if (ID_p == 14) {

            return 65;
        }

        if (ID_p == 15) {

            return 66;
        }
        if (ID_p == 16) {

            return 67;
        }
        if (ID_p == 17) {

            return 68;
        }
        if (ID_p == 18) {

            return 69;
        }
        if (ID_p == 19) {

            return 70;
        }

        if (ID_p == 20) {

            return 71;
        }
        if (ID_p == 21) {

            return 72;
        }
        if (ID_p == 22) {

            return 73;
        }
        if (ID_p == 23) {

            return 74;
        }
        if (ID_p == 24) {

            return 75;
        }






        return 0;
    }


}
