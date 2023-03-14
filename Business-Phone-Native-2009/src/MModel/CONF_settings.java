package MModel;

import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import MDataStore.DataBase_RMS;
import java.io.IOException;
import javax.microedition.rms.RecordStoreException;
import MControll.Main_Controll;
import MDataStore.DataBase_RMS;
import MModel.CONF;
import MModel.Language;

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
public class CONF_settings {

    private MControll.Main_Controll mainControll;
    private MDataStore.DataBase_RMS rms;
    private MModel.CONF conf;
    private MModel.Language language;

    public String
            /* PBX Settings */
            lineAccess_PBX,
    switchBoardNumber_PBX,
    countryCode_PBX,
    extensionNumber_PBX,
    HGP_PBX,
    pinCodeNumber_PBX,
    precode_PBX,
    voiceMailSwitchboard_PBX,
    voiceMailOperator_PBX,
    mexONOFF_PBX,
    checkStatus_PBX,
    dbg_PBX,
    demo_PBX,
    companyName_PBX,
    userName_PBX,
    countryName_PBX,
    iconNumber_PBX,
    lang_PBX,
    pbx_ID,
    prg_Name,
    accessCode_PBX,
    setP_PBX,
    imei,
    default_lang,
    eng_lang,
    device_brands,
    deveice_model,
    pbx_name,
    systemImeiProperty;

    public CONF_settings() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();
        this.checkStatus_PBX = rms.getPBXStatus();
        rms = null;

    }

    public void setConfSettings() throws IOException {

        if (!checkStatus_PBX.equals("1")) {

            try {
                setPBXConfig();
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreNotOpenException ex2) {
            } catch (RecordStoreException ex2) {
            }
        }
    }

    public void setPBXConfig() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException, IOException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        this.mexONOFF_PBX = conf.MEX;
        this.switchBoardNumber_PBX = conf.SWTBNR;
        this.lineAccess_PBX = conf.LA;
        this.countryCode_PBX = conf.CCODE;
        this.dbg_PBX = conf.DBG;
        this.HGP_PBX = conf.HGP;
        this.voiceMailOperator_PBX = conf.VM;
        this.pinCodeNumber_PBX = conf.PCODE;
        this.extensionNumber_PBX = conf.ANKN;
        this.lang_PBX = conf.LANG;
        this.precode_PBX = conf.PRECODE;
        this.voiceMailSwitchboard_PBX = conf.PBXVM;
        this.demo_PBX = conf.DEMO;
        this.companyName_PBX = conf.COMPANYNAME;
        this.userName_PBX = conf.NAME;
        this.checkStatus_PBX = "1";
        this.pbx_ID = conf.PBXID;
        this.prg_Name = conf.PRGNAME;
        this.setP_PBX = conf.DTMFP;
        this.imei = conf.IMEI2;
        this.default_lang = conf.DL;
        this.device_brands = conf.PBRAND;
        this.deveice_model = conf.PMODEL;
        this.pbx_name = conf.PBXNAME;
        this.systemImeiProperty = conf.IMEIPROPERTY;

        if (lang_PBX.equals("0")) {

            this.countryName_PBX = "Danish";
            this.iconNumber_PBX = "0";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("1")) {

            this.countryName_PBX = "Dutch";
            this.iconNumber_PBX = "1";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("2")) {

            this.countryName_PBX = "English";
            this.iconNumber_PBX = "2";
            this.eng_lang = "2";

            rms.setENGlang(eng_lang);
            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("3")) {

            this.countryName_PBX = "Finnish";
            this.iconNumber_PBX = "3";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("4")) {

            this.countryName_PBX = "French";
            this.iconNumber_PBX = "4";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("5")) {

            this.countryName_PBX = "German";
            this.iconNumber_PBX = "5";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("6")) {

            this.countryName_PBX = "Norwegian";
            this.iconNumber_PBX = "6";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("7")) {

            this.countryName_PBX = "Italian";
            this.iconNumber_PBX = "7";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("8")) {

            this.countryName_PBX = "Spanish";
            this.iconNumber_PBX = "8";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }
        if (lang_PBX.equals("9")) {

            this.countryName_PBX = "Swedish";
            this.iconNumber_PBX = "9";

            rms.setIconNumber(iconNumber_PBX);
            rms.setCountryName(countryName_PBX);
        }


        if (mexONOFF_PBX.equals(null) || mexONOFF_PBX.equals("")) {

            mexONOFF_PBX = "0";
        }
        if (switchBoardNumber_PBX.equals(null) ||
            switchBoardNumber_PBX.equals("")) {

            switchBoardNumber_PBX = "+44";
        }
        if (lineAccess_PBX.equals(null) || lineAccess_PBX.equals("")
            || lineAccess_PBX.equals(" ")) {

            lineAccess_PBX = "NONE";
        }
        if (countryCode_PBX.equals(null) || countryCode_PBX.equals("")) {

            countryCode_PBX = "44";
        }
        if (dbg_PBX.equals(null) || dbg_PBX.equals("")) {

            dbg_PBX = "0";
        }
        if (HGP_PBX.equals(null) || HGP_PBX.equals("")) {

            HGP_PBX = "197";
        }
        if (voiceMailOperator_PBX.equals(null) ||
            voiceMailOperator_PBX.equals("")) {

            voiceMailOperator_PBX = "0";
        }
        if (extensionNumber_PBX.equals(null) || extensionNumber_PBX.equals("")) {

            extensionNumber_PBX = "0";
        }
        if (lang_PBX.equals(null) || lang_PBX.equals("")) {

            lang_PBX = "2";
        }
        if (demo_PBX.equals(null) || demo_PBX.equals("")) {

            demo_PBX = "0";
        }
        if (companyName_PBX.equals(null) || companyName_PBX.equals("")) {

            companyName_PBX = "Company";
        }
        if (userName_PBX.equals(null) || userName_PBX.equals("")) {

            userName_PBX = "Name";
        }

        if (pbx_ID.equals("1")) { // Pansonic växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }

            else if (!pinCodeNumber_PBX.equals(null) ||
                     !pinCodeNumber_PBX.equals("")) {

                rms.setPINCode(pinCodeNumber_PBX);
                String setPINCodeTo = "5";
                rms.setAccessTypeTo(setPINCodeTo);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("2")) { // Aastra Ascotel växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }

            else if (!pinCodeNumber_PBX.equals(null) ||
                     !pinCodeNumber_PBX.equals("")) {

                rms.setPINCode(pinCodeNumber_PBX);
                String setPINCodeTo = "4";
                rms.setAccessTypeTo(setPINCodeTo);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("3")) { // Aastra Business Phone växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("4")) { // Aastra MD 110 växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("5")) { // Aastra MX-one växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("6")) { // LG växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("9")) { // Avaya växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }
        }

        if (pbx_ID.equals("10")) { // Siemens växel.

            if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

                pinCodeNumber_PBX = "0";
                String setAutoAccessTo = "3";
                rms.setAccessTypeTo(setAutoAccessTo);
                rms.setPINCode(pinCodeNumber_PBX);
                rms.setPbx_ID(pbx_ID);

            }
        }



        if (precode_PBX.equals(null) || precode_PBX.equals("")) {

            precode_PBX = "*7";
        }
        if (voiceMailSwitchboard_PBX.equals(null) ||
            voiceMailSwitchboard_PBX.equals("")) {

            voiceMailSwitchboard_PBX = "0";
        }

        // --- Om default är '0' starta programmet i engelska. (id = 1, Panasonic)
        if(default_lang.equals(null) || default_lang.equals("")
           || default_lang.equals("0") && pbx_ID.equals("1")){

            lang_PBX = "2";

            rms.setLanguage(lang_PBX);
            rms.setDefaultLanguage(default_lang);
            rms.setEditAbsentName_1(language.absentDefaultSystemAttOne_1);
            rms.setEditAbsentName_2(language.absentDeafaultSystemAttTwo_1);
            rms.setEditAbsentName_3(language.absentDefaultPersonalAtt_1);


        }
        // --- Om default är '0' starta programmet i engelska. (id != 1, Panasonic)
        if(default_lang.equals(null) || default_lang.equals("")
           || default_lang.equals("0") && !pbx_ID.equals("1")){

            lang_PBX = "2";

            rms.setLanguage(lang_PBX);
            rms.setDefaultLanguage(default_lang);
            rms.setEditAbsentName_1(language.genDefaultEdit_1);
            rms.setEditAbsentName_2(language.genDefaultEdit_1);
            rms.setEditAbsentName_3(language.genDefaultEdit_1);



        }


        if (!lang_PBX.equals("2") && !pbx_ID.equals("1")) {

            rms.setEditAbsentName_1(language.genDefaultEdit_2);
            rms.setEditAbsentName_2(language.genDefaultEdit_2);
            rms.setEditAbsentName_3(language.genDefaultEdit_2);

        }
        if (lang_PBX.equals("2") && !pbx_ID.equals("1")) {

            rms.setEditAbsentName_1(language.genDefaultEdit_1);
            rms.setEditAbsentName_2(language.genDefaultEdit_1);
            rms.setEditAbsentName_3(language.genDefaultEdit_1);
        }
        if (!lang_PBX.equals("2") && pbx_ID.equals("1")) {

            rms.setEditAbsentName_1(language.absentDefaultSystemAttOne_2);
            rms.setEditAbsentName_2(language.absentDeafaultSystemAttTwo_2);
            rms.setEditAbsentName_3(language.absentDefaultPersonalAtt_2);

        }
        if (lang_PBX.equals("2") && pbx_ID.equals("1")) {

            rms.setEditAbsentName_1(language.absentDefaultSystemAttOne_1);
            rms.setEditAbsentName_2(language.absentDeafaultSystemAttTwo_1);
            rms.setEditAbsentName_3(language.absentDefaultPersonalAtt_1);

        }


        // År.    plats 1
        // Månad. plats 2
        // Datum. plats 3

        rms.setLineAccess(lineAccess_PBX);
        rms.setSwitchBoardNumber(switchBoardNumber_PBX);

        // År.    plats 6
        // Månad. plats 7
        // Dag.   plats 8

        rms.setExtensionNumber(extensionNumber_PBX);

        // Plats 10. Sätter PINkoden ovan i if-satsen.
        // Plats 11. Sätter login typen AutoAccess eller PINCodeAccess

        rms.setVoiceMailOperator(voiceMailOperator_PBX);
        rms.setDebugONOFF(dbg_PBX);

        // Plats 14. Sätter iconnummer se ovan i if-satsen.

        rms.setCountryCode(countryCode_PBX);
        rms.setMexONOFF(mexONOFF_PBX);
        rms.setKeyCode(HGP_PBX);
        rms.setVoiceMailSwitchBoard(voiceMailSwitchboard_PBX);
        rms.setLanguage(lang_PBX);
        rms.setDemoLicens(demo_PBX);

        if (checkStatus_PBX.equals(null) || checkStatus_PBX.equals("")) {

            checkStatus_PBX = "1";
        }

        rms.setCheckStatusPBX(checkStatus_PBX);
        rms.setCompanyName(companyName_PBX);
        rms.setUserName(userName_PBX);
        rms.setPreCode(precode_PBX);

        if(setP_PBX.equals(null) || setP_PBX.equals("")){

            rms.setDTMFsend("1");

        }else

            rms.setDTMFsend(setP_PBX);

        if(imei.equals(null) || imei.equals("")){

            rms.setDTMFsend("1");

        } else

            rms.setIMEI(imei);

        if (device_brands.equals(null) || device_brands.equals("")) {

            rms.setPhoneBrands("Nokia");

        } else

            rms.setPhoneBrands(device_brands);

        if (deveice_model.equals(null) || deveice_model.equals("")) {

            rms.setPhoneModel("E71");

        } else

            rms.setPhoneModel(deveice_model);

        if (pbx_name.equals(null) || pbx_name.equals("")) {

            rms.setPbxName("Panasonic");

        } else

            rms.setPbxName(pbx_name);

        if(systemImeiProperty.equals(null) || systemImeiProperty.equals("")){

            rms.setSystemIMEIProperty("1");

        } else

            rms.setSystemIMEIProperty(systemImeiProperty);

        // Plats 28. Sätter landsnamn se ovan i if-satsen.

        rms.setPrgName(prg_Name);
        if(pbx_ID.equals("1")){

            String setTransferCall = "#";
            rms.setTransferCall(setTransferCall);

        }
        else if(pbx_ID.equals("3")){

            String setTransferCall = "1";
            rms.setTransferCall(setTransferCall);

        }

        else if(pbx_ID.equals("6")){

            String setTransferCall = "*";
            rms.setTransferCall(setTransferCall);

        }


        try {
            rms.upDateDataStore();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            rms.setDataStore();
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreNotOpenException ex2) {
        } catch (RecordStoreException ex2) {
        }

        rms = null;
    }

}
