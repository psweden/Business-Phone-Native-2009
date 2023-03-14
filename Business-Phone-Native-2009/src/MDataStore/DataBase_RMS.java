package MDataStore;

import MControll.Settings;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import java.io.IOException;
import MControll.Main_Controll;
import MModel.Date_Time;
import MModel.CONF;
import MModel.Language;

/**
 * <p>Title: Mobile Extension</p>
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
public class DataBase_RMS {

    /* Initiering av Databasen */
    public RecordStore recStore = null;
    static final String REC_STORE = "Data_Store_attendant_145";

    private MControll.Main_Controll mainControll;
    private MModel.Date_Time dateTime;
    private MModel.CONF conf;
    private MModel.Language language;

    public String

    // Sätter internnummer och namn
    InternName,

    /* PBX Settings */
    // 1. År (30 dagar framåt i tiden)
    // 2. Månad (30 dagar framåt i tiden)
    // 3. Dag (30 dagar framåt i tiden)
    lineAccess_PBX,
    switchBoardNumber_PBX,
    // 6. År (dagens datum)
    // 7. Månad (dagens datum)
    // 8. Dag (dagens datum)
    extensionNumber_PBX,
    pinCodeNumber_PBX,
    pbx_Type,
    voiceMailOperator_PBX,
    dbg_PBX,
    iconNumber_PBX,
    countryCode_PBX,
    mexONOFF_PBX,
    HGP_PBX,
    voiceMailSwitchboard_PBX,
    lang_PBX,
    demo_PBX,
    checkStatus_PBX,
    companyName_PBX,
    userName_PBX,
    precode_PBX,
    countryName_PBX,
    pbx_ID,
    prg_Name,
    transferCall,
    setP_PBX,
    imei,
    imeiFalse,
    default_lang,
    eng_lang,
    device_brands,
    deveice_model,
    pbx_name,
    absentStatus,
    systemImeiProperty,

    // Ny hänvisning
    rename_1,
    rename_2,
    rename_3,
    editAbsentName_1,
    editAbsentName_2,
    editAbsentName_3,
    editAbsentDTMF_1,
    editAbsentDTMF_2,
    editAbsentDTMF_3,
    edit_HHMM_TTMM_1,
    edit_HHMM_TTMM_2,
    edit_HHMM_TTMM_3;

    /*Tid och Datum*/
    public String

    setYear_30DAY, setDay_30DAY, setMounth_30DAY, //  DB sparad
    setDay_TODAY, setMounth_TODAY, setYear_TODAY, //  DB sparad
    CheckTwo; //  DB sparad

    /* Konstruktorn börjar här */
    public DataBase_RMS() throws InvalidRecordIDException,
    RecordStoreNotOpenException, RecordStoreException, IOException {

        /* Initierar värden för datum och tid */
        MModel.Date_Time dateTime = new Date_Time();
        // konvertera int till string...
        int INT30Year_TODAY = dateTime.getDemoYear();
        this.setYear_30DAY = Integer.toString(INT30Year_TODAY);

        // konvertera int till string...
        int INT30Month_TODAY = dateTime.getDemoMonth();
        this.setMounth_30DAY = Integer.toString(INT30Month_TODAY);

        // konvertera int till string...
        int INT30Day_TODAY = dateTime.getDemoDay();
        this.setDay_30DAY = Integer.toString(INT30Day_TODAY);

        // konvertera int till string...
        int INTYear_TODAY = dateTime.getYear();
        this.setYear_TODAY = Integer.toString(INTYear_TODAY);

        // konvertera int till string...
        int INTMonth_TODAY = dateTime.getMonth();
        this.setMounth_TODAY = Integer.toString(INTMonth_TODAY);

        // konvertera int till string...
        int INTDay_TODAY = dateTime.getDay();
        this.setDay_TODAY = Integer.toString(INTDay_TODAY);

        openRecStore();
        try {
            this.setYear_30DAY = getYear_30_DAY();
        } catch (RecordStoreNotOpenException ex17) {
        } catch (InvalidRecordIDException ex17) {
        } catch (RecordStoreException ex17) {
        }
        try {
            this.setMounth_30DAY = getMounth_30_DAY();
        } catch (RecordStoreNotOpenException ex16) {
        } catch (InvalidRecordIDException ex16) {
        } catch (RecordStoreException ex16) {
        }
        try {
            this.setDay_30DAY = getDay_30_DAY();
        } catch (RecordStoreNotOpenException ex15) {
        } catch (InvalidRecordIDException ex15) {
        } catch (RecordStoreException ex15) {
        }
        try {
            this.lineAccess_PBX = getAccessNumber();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            this.switchBoardNumber_PBX = getSwitchBoardNumber();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.setYear_TODAY = getYear_TODAY();
        } catch (RecordStoreNotOpenException ex18) {
        } catch (InvalidRecordIDException ex18) {
        } catch (RecordStoreException ex18) {
        }
        try {
            this.setMounth_TODAY = getMounth_TODAY();
        } catch (RecordStoreNotOpenException ex19) {
        } catch (InvalidRecordIDException ex19) {
        } catch (RecordStoreException ex19) {
        }
        try {
            this.setDay_TODAY = getDay_TODAY();
        } catch (RecordStoreNotOpenException ex20) {
        } catch (InvalidRecordIDException ex20) {
        } catch (RecordStoreException ex20) {
        }
        try {
            this.extensionNumber_PBX = getExtensionNumber();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.pinCodeNumber_PBX = getPinCodeNumber();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        try {
            this.pbx_Type = getPBXType();
        } catch (RecordStoreNotOpenException ex14) {
        } catch (InvalidRecordIDException ex14) {
        } catch (RecordStoreException ex14) {
        }
        try {
            this.voiceMailOperator_PBX = getVoiceMailOperator();
        } catch (RecordStoreNotOpenException ex5) {
        } catch (InvalidRecordIDException ex5) {
        } catch (RecordStoreException ex5) {
        }
        try {
            this.dbg_PBX = getDeBug();
        } catch (RecordStoreNotOpenException ex7) {
        } catch (InvalidRecordIDException ex7) {
        } catch (RecordStoreException ex7) {
        }
        try {
            this.iconNumber_PBX = getIconNumber();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }
        try {
            this.countryCode_PBX = getCountryCode();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        try {
            this.mexONOFF_PBX = getMexONOFF();
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }
        try {
            this.HGP_PBX = getKeyCode();
        } catch (RecordStoreNotOpenException ex4) {
        } catch (InvalidRecordIDException ex4) {
        } catch (RecordStoreException ex4) {
        }
        try {
            this.voiceMailSwitchboard_PBX = getVoiceEditCode();
        } catch (RecordStoreNotOpenException ex10) {
        } catch (InvalidRecordIDException ex10) {
        } catch (RecordStoreException ex10) {
        }
        try {
            this.lang_PBX = getLanguage();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        try {
            this.demo_PBX = getDemoStatus();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }
        try {
            this.checkStatus_PBX = getPBXStatus();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }
        try {
            this.companyName_PBX = getCompanyName();
        } catch (RecordStoreNotOpenException ex9) {
        } catch (InvalidRecordIDException ex9) {
        } catch (RecordStoreException ex9) {
        }
        try {
            this.userName_PBX = getUserName();
        } catch (RecordStoreNotOpenException ex11) {
        } catch (InvalidRecordIDException ex11) {
        } catch (RecordStoreException ex11) {
        }
        try {
            this.editAbsentName_3 = getEditAbsentName_3();
        } catch (RecordStoreNotOpenException ex8) {
        } catch (InvalidRecordIDException ex8) {
        } catch (RecordStoreException ex8) {
        }
        try {
            this.editAbsentDTMF_3 = getEditAbsentDTMF_3();
        } catch (RecordStoreNotOpenException ex12) {
        } catch (InvalidRecordIDException ex12) {
        } catch (RecordStoreException ex12) {
        }
        try {
            this.edit_HHMM_TTMM_3 = getHHMMTTMM_3();
        } catch (RecordStoreNotOpenException ex13) {
        } catch (InvalidRecordIDException ex13) {
        } catch (RecordStoreException ex13) {
        }
        try {
            this.precode_PBX = getPreEditCode();
        } catch (RecordStoreNotOpenException ex10) {
        } catch (InvalidRecordIDException ex10) {
        } catch (RecordStoreException ex10) {
        }
        try {
            this.countryName_PBX = getCountryName();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }
        try {
            this.CheckTwo = getTWO();
        } catch (RecordStoreNotOpenException ex21) {
        } catch (InvalidRecordIDException ex21) {
        } catch (RecordStoreException ex21) {
        }
        try {
            this.editAbsentName_1 = getEditAbsentName_1();
        } catch (RecordStoreNotOpenException ex22) {
        } catch (InvalidRecordIDException ex22) {
        } catch (RecordStoreException ex22) {
        }
        try {
            this.editAbsentName_2 = getEditAbsentName_2();
        } catch (RecordStoreNotOpenException ex23) {
        } catch (InvalidRecordIDException ex23) {
        } catch (RecordStoreException ex23) {
        }
        try {
            this.editAbsentDTMF_1 = getEditAbsentDTMF_1();
        } catch (RecordStoreNotOpenException ex24) {
        } catch (InvalidRecordIDException ex24) {
        } catch (RecordStoreException ex24) {
        }
        try {
            this.editAbsentDTMF_2 = getEditAbsentDTMF_2();
        } catch (RecordStoreNotOpenException ex25) {
        } catch (InvalidRecordIDException ex25) {
        } catch (RecordStoreException ex25) {
        }
        try {
            this.edit_HHMM_TTMM_1 = getHHMMTTMM_1();
        } catch (RecordStoreNotOpenException ex26) {
        } catch (InvalidRecordIDException ex26) {
        } catch (RecordStoreException ex26) {
        }
        try {
            this.edit_HHMM_TTMM_2 = getHHMMTTMM_2();
        } catch (RecordStoreNotOpenException ex26) {
        } catch (InvalidRecordIDException ex26) {
        } catch (RecordStoreException ex26) {
        }
        try {
            this.pbx_ID = getPbxID();
        } catch (RecordStoreNotOpenException ex26) {
        } catch (InvalidRecordIDException ex26) {
        } catch (RecordStoreException ex26) {
        }
        try {
            this.prg_Name = getPrgName();
        } catch (RecordStoreNotOpenException ex27) {
        } catch (InvalidRecordIDException ex27) {
        } catch (RecordStoreException ex27) {
        }
        try {
            this.eng_lang = getENGlang();
        } catch (RecordStoreNotOpenException ex27) {
        } catch (InvalidRecordIDException ex27) {
        } catch (RecordStoreException ex27) {
        }

        closeRecStore();

        /* Uppdaterar och refreshar databasen */
        setDataStore();
        upDateDataStore();

    }

    /* SET - - - METODER  --------------------------------------------   */

    public void setLineAccess(String s) {
        openRecStore();
        lineAccess_PBX = s;

        try {
            recStore.setRecord(4, lineAccess_PBX.getBytes(),
                               0,
                               lineAccess_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setSwitchBoardNumber(String s) {
        openRecStore();
        switchBoardNumber_PBX = s;

        try {
            recStore.setRecord(5, switchBoardNumber_PBX.getBytes(),
                               0,
                               switchBoardNumber_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setExtensionNumber(String s) {
        openRecStore();
        extensionNumber_PBX = s;

        try {
            recStore.setRecord(9, extensionNumber_PBX.getBytes(),
                               0,
                               extensionNumber_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setPINCode(String s) {
        openRecStore();
        pinCodeNumber_PBX = s;

        try {
            recStore.setRecord(10, pinCodeNumber_PBX.getBytes(),
                               0,
                               pinCodeNumber_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setAccessTypeTo(String s) {
        openRecStore();
        pbx_Type= s;

        try {
            recStore.setRecord(11, pbx_Type.getBytes(),
                               0,
                               pbx_Type.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setVoiceMailOperator(String s) {
        openRecStore();
        voiceMailOperator_PBX = s;

        try {
            recStore.setRecord(12, voiceMailOperator_PBX.getBytes(),
                               0,
                               voiceMailOperator_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setDebugONOFF(String s) {
        openRecStore();
        dbg_PBX = s;

        try {
            recStore.setRecord(13, dbg_PBX.getBytes(),
                               0,
                               dbg_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setIconNumber(String s) {
        openRecStore();
        iconNumber_PBX = s;

        try {
            recStore.setRecord(14, iconNumber_PBX.getBytes(),
                               0,
                               iconNumber_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setCountryCode(String s) {
        openRecStore();
        countryCode_PBX = s;

        try {
            recStore.setRecord(15, countryCode_PBX.getBytes(),
                               0,
                               countryCode_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setMexONOFF(String s) {
        openRecStore();
        mexONOFF_PBX = s;

        try {
            recStore.setRecord(16, mexONOFF_PBX.getBytes(),
                               0,
                               mexONOFF_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setKeyCode(String s) {
        openRecStore();
        HGP_PBX = s;

        try {
            recStore.setRecord(17, HGP_PBX.getBytes(),
                               0,
                               HGP_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setVoiceMailSwitchBoard(String s) {
        openRecStore();
        voiceMailSwitchboard_PBX = s;

        try {
            recStore.setRecord(18, voiceMailSwitchboard_PBX.getBytes(),
                               0,
                               voiceMailSwitchboard_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setLanguage(String s) {
        openRecStore();
        lang_PBX = s;

        try {
            recStore.setRecord(19, lang_PBX.getBytes(),
                               0,
                               lang_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setDemoLicens(String s) {
        openRecStore();
        demo_PBX = s;

        try {
            recStore.setRecord(20, demo_PBX.getBytes(),
                               0,
                               demo_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setCheckStatusPBX(String s) {
        openRecStore();
        checkStatus_PBX = s;

        try {
            recStore.setRecord(21, checkStatus_PBX.getBytes(),
                               0,
                               checkStatus_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setCompanyName(String s) {
        openRecStore();
        companyName_PBX = s;

        try {
            recStore.setRecord(22, companyName_PBX.getBytes(),
                               0,
                               companyName_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setUserName(String s) {
        openRecStore();
        userName_PBX = s;

        try {
            recStore.setRecord(23, userName_PBX.getBytes(),
                               0,
                               userName_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }

   public void setEditAbsentName_3(String s) {

        openRecStore();
        editAbsentName_3 = s;

        try {
            recStore.setRecord(24, editAbsentName_3.getBytes(),
                               0,
                               editAbsentName_3.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setEditAbsentDTMF_3(String s) {

        openRecStore();
        editAbsentDTMF_3 = s;

        try {
            recStore.setRecord(25, editAbsentDTMF_3.getBytes(),
                               0,
                               editAbsentDTMF_3.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setHHMMTTMM_3(String s) {

       openRecStore();
       edit_HHMM_TTMM_3 = s;

       try {
           recStore.setRecord(26, edit_HHMM_TTMM_3.getBytes(),
                              0,
                              edit_HHMM_TTMM_3.length());
       } catch (Exception e) {

       }closeRecStore();
   }

    public void setPreCode(String s) {
        openRecStore();
        precode_PBX = s;

        try {
            recStore.setRecord(27, precode_PBX.getBytes(),
                               0,
                               precode_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setCountryName(String s) {
        openRecStore();
        countryName_PBX = s;

        try {
            recStore.setRecord(28, countryName_PBX.getBytes(),
                               0,
                               countryName_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setEditAbsentName_1(String s) {

        openRecStore();
        editAbsentName_1 = s;

        try {
            recStore.setRecord(29, editAbsentName_1.getBytes(),
                               0,
                               editAbsentName_1.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setEditAbsentName_2(String s) {

        openRecStore();
        editAbsentName_2 = s;

        try {
            recStore.setRecord(30, editAbsentName_2.getBytes(),
                               0,
                               editAbsentName_2.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setEditAbsentDTMF_1(String s) {

        openRecStore();
        editAbsentDTMF_1 = s;

        try {
            recStore.setRecord(31, editAbsentDTMF_1.getBytes(),
                               0,
                               editAbsentDTMF_1.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setEditAbsentDTMF_2(String s) {

        openRecStore();
        editAbsentDTMF_2 = s;

        try {
            recStore.setRecord(32, editAbsentDTMF_2.getBytes(),
                               0,
                               editAbsentDTMF_2.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setHHMMTTMM_1(String s) {

        openRecStore();
        edit_HHMM_TTMM_1 = s;

        try {
            recStore.setRecord(33, edit_HHMM_TTMM_1.getBytes(),
                               0,
                               edit_HHMM_TTMM_1.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setHHMMTTMM_2(String s) {

        openRecStore();
        edit_HHMM_TTMM_2 = s;

        try {
            recStore.setRecord(34, edit_HHMM_TTMM_2.getBytes(),
                               0,
                               edit_HHMM_TTMM_2.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setPbx_ID(String s) {

        openRecStore();
        pbx_ID = s;

        try {
            recStore.setRecord(35, pbx_ID.getBytes(),
                               0,
                               pbx_ID.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setPrgName(String s) {

        openRecStore();
        prg_Name = s;

        try {
            recStore.setRecord(36, prg_Name.getBytes(),
                               0,
                               prg_Name.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setTransferCall(String s) {

        openRecStore();
        transferCall = s;

        try {
            recStore.setRecord(37, transferCall.getBytes(),
                               0,
                               transferCall.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setDTMFsend(String s) {

        openRecStore();
        setP_PBX = s;

        try {
            recStore.setRecord(38, setP_PBX.getBytes(),
                               0,
                               setP_PBX.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setIMEI(String s) {

        openRecStore();
        imei = s;

        try {
            recStore.setRecord(39, imei.getBytes(),
                               0,
                               imei.length());
        } catch (Exception e) {

        }closeRecStore();
    }


    public void setRename_1(String s) {

        openRecStore();
        rename_1 = s;

        try {
            recStore.setRecord(40, rename_1.getBytes(),
                               0,
                               rename_1.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setRename_2(String s) {

        openRecStore();
        rename_2 = s;

        try {
            recStore.setRecord(41, rename_2.getBytes(),
                               0,
                               rename_2.length());
        } catch (Exception e) {

        }closeRecStore();
    }
    public void setRename_3(String s) {

        openRecStore();
        rename_3 = s;

        try {
            recStore.setRecord(42, rename_3.getBytes(),
                               0,
                               rename_3.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setDefaultLanguage(String s) {

        openRecStore();
        default_lang = s;

        try {
            recStore.setRecord(43, default_lang.getBytes(),
                               0,
                               default_lang.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setENGlang(String s) {

        openRecStore();
        eng_lang = s;

        try {
            recStore.setRecord(44, eng_lang.getBytes(),
                               0,
                               eng_lang.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setPhoneBrands(String s) {

        openRecStore();
        device_brands = s;

        try {
            recStore.setRecord(45, device_brands.getBytes(),
                               0,
                               device_brands.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setPhoneModel(String s) {

        openRecStore();
        this.deveice_model = s;

        try {
            recStore.setRecord(46, deveice_model.getBytes(),
                               0,
                               deveice_model.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setAbsentStatus(String s) {

        openRecStore();
        this.absentStatus = s;

        try {
            recStore.setRecord(47, absentStatus.getBytes(),
                               0,
                               absentStatus.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setPbxName(String s) {

        openRecStore();
        this.pbx_name = s;

        try {
            recStore.setRecord(48, pbx_name.getBytes(),
                               0,
                               pbx_name.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setImeiFalse(String s) {

        openRecStore();
        this.imeiFalse = s;

        try {
            recStore.setRecord(49, imeiFalse.getBytes(),
                               0,
                               imeiFalse.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setSystemIMEIProperty(String s) {

        openRecStore();
        this.systemImeiProperty = s;

        try {
            recStore.setRecord(50, systemImeiProperty.getBytes(),
                               0,
                               systemImeiProperty.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    // Internnummer DB poster, plats 51 - 70
    public void setInternName(String s, int p) {

        openRecStore();
        InternName = s;
        int IDInternNumber = p;

        try {
            recStore.setRecord(IDInternNumber, InternName.getBytes(),
                               0,
                               InternName.length());
        } catch (Exception e) {

        }closeRecStore();
    }

    public void setTWO(String s) {
        openRecStore();
         CheckTwo = s;

         System.out.println("setTWO(); tar emot värdet >> " + CheckTwo);

         try {
             recStore.setRecord(111, CheckTwo.getBytes(),
                                0,
                                CheckTwo.length());
         } catch (Exception e) {

         }closeRecStore();

    }

    /* GET - - - METODER  --------------------------------------------   */

    public String getYear_30_DAY() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 1

        openRecStore();

        byte a[] = recStore.getRecord(1);
        setYear_30DAY = new String(a, 0, a.length);

        closeRecStore();

        return setYear_30DAY;

    }
    public String getMounth_30_DAY() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 2

        openRecStore();

        byte b[] = recStore.getRecord(2);
        setMounth_30DAY = new String(b, 0, b.length);

        closeRecStore();

        return setMounth_30DAY;

    }
    public String getDay_30_DAY() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 3

        openRecStore();

        byte c[] = recStore.getRecord(3);
        setDay_30DAY = new String(c, 0, c.length);

        closeRecStore();

        return setDay_30DAY;

    }
    public String getAccessNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 4

        openRecStore();

        byte d[] = recStore.getRecord(4);
        lineAccess_PBX = new String(d, 0, d.length);

        closeRecStore();

        return lineAccess_PBX;

    }
    public String getSwitchBoardNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 5

        openRecStore();

        byte e[] = recStore.getRecord(5);
        switchBoardNumber_PBX = new String(e, 0, e.length);

        closeRecStore();

        return switchBoardNumber_PBX;

    }
    public String getYear_TODAY() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 6

        openRecStore();

        byte a[] = recStore.getRecord(6);
        setYear_TODAY = new String(a, 0, a.length);

        closeRecStore();

        return setYear_TODAY;

    }
    public String getMounth_TODAY() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 7

        openRecStore();

        byte b[] = recStore.getRecord(7);
        setMounth_TODAY = new String(b, 0, b.length);

        closeRecStore();

        return setMounth_TODAY;

    }
    public String getDay_TODAY() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 8

        openRecStore();

        byte c[] = recStore.getRecord(8);
        setDay_TODAY = new String(c, 0, c.length);

        closeRecStore();

        return setDay_TODAY;

    }
    public String getExtensionNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 9

        openRecStore();

        byte j[] = recStore.getRecord(9);
        extensionNumber_PBX = new String(j, 0, j.length);

        closeRecStore();

        return extensionNumber_PBX;

    }
    public String getPinCodeNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 10

        openRecStore();

        byte k[] = recStore.getRecord(10);
        pinCodeNumber_PBX = new String(k, 0, k.length);

        closeRecStore();

        return pinCodeNumber_PBX;

    }
    public String getPBXType() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 11

        openRecStore();

        byte l[] = recStore.getRecord(11);
        pbx_Type = new String(l, 0, l.length);

        closeRecStore();

        return pbx_Type;

    }
    public String getVoiceMailOperator() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 12

        openRecStore();

        byte l[] = recStore.getRecord(12);
        voiceMailOperator_PBX = new String(l, 0, l.length);

        closeRecStore();

        return voiceMailOperator_PBX;

    }
    public String getDeBug() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 13

        openRecStore();

        byte l[] = recStore.getRecord(13);
        dbg_PBX = new String(l, 0, l.length);

        closeRecStore();

        return dbg_PBX;

    }
    public String getIconNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 14

        openRecStore();

        byte l[] = recStore.getRecord(14);
        iconNumber_PBX = new String(l, 0, l.length);

        closeRecStore();

        return iconNumber_PBX;

    }
    public String getCountryCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 15

        openRecStore();

        byte a[] = recStore.getRecord(15);
        countryCode_PBX = new String(a, 0, a.length);

        closeRecStore();

        return countryCode_PBX;

    }
    public String getMexONOFF() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 16

        openRecStore();

        byte l[] = recStore.getRecord(16);
        mexONOFF_PBX = new String(l, 0, l.length);

        closeRecStore();

        return mexONOFF_PBX;

    }
    public String getKeyCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 17

        openRecStore();

        byte a1[] = recStore.getRecord(17);
        HGP_PBX = new String(a1, 0, a1.length);

        closeRecStore();

        return HGP_PBX;

    }
    public String getVoiceEditCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 18

        openRecStore();

        byte mTextVoiceEditCode[] = recStore.getRecord(18);
        voiceMailSwitchboard_PBX = new String(mTextVoiceEditCode, 0,
                                              mTextVoiceEditCode.length);

        closeRecStore();

        return voiceMailSwitchboard_PBX;
    }
    public String getLanguage() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 19

        openRecStore();

        byte nlanguage[] = recStore.getRecord(19);
        lang_PBX = new String(nlanguage, 0, nlanguage.length);

        closeRecStore();

        return lang_PBX;
    }
    public String getDemoStatus() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 20

        openRecStore();

        byte l[] = recStore.getRecord(20);
        demo_PBX = new String(l, 0, l.length);

        closeRecStore();

        return demo_PBX;

    }
    public String getPBXStatus() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 21

        openRecStore();

        byte l[] = recStore.getRecord(21);
        checkStatus_PBX = new String(l, 0, l.length);

        closeRecStore();

        return checkStatus_PBX;

    }
    public String getCompanyName() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException { // 22

        openRecStore();

        byte l[] = recStore.getRecord(22);
        companyName_PBX = new String(l, 0, l.length);

        closeRecStore();

        return companyName_PBX;

    }
    public String getUserName() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException { // 23

       openRecStore();

       byte l[] = recStore.getRecord(23);
       userName_PBX = new String(l, 0, l.length);

       closeRecStore();

       return userName_PBX;

   }
   public String getEditAbsentName_3() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException { // 24

       openRecStore();

       byte l[] = recStore.getRecord(24);
       editAbsentName_3 = new String(l, 0, l.length);

       closeRecStore();

       return editAbsentName_3;

   }
   public String getEditAbsentDTMF_3() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException { // 25

       openRecStore();

       byte l[] = recStore.getRecord(25);
       editAbsentDTMF_3 = new String(l, 0, l.length);

       closeRecStore();

       return editAbsentDTMF_3;
   }
   public String getHHMMTTMM_3() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException { // 26

       openRecStore();

       byte l[] = recStore.getRecord(26);
       edit_HHMM_TTMM_3 = new String(l, 0, l.length);

       closeRecStore();

       return edit_HHMM_TTMM_3;
   }
   public String getPreEditCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte mTextPreEditCode[] = recStore.getRecord(27);
        precode_PBX = new String(mTextPreEditCode, 0,
                                 mTextPreEditCode.length);

        closeRecStore();

        return precode_PBX;
    }
    public String getCountryName() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(28);
        countryName_PBX = new String(l, 0, l.length);

        closeRecStore();

        return countryName_PBX;

    }
    public String getEditAbsentName_1() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(29);
        editAbsentName_1 = new String(l, 0, l.length);

        closeRecStore();

        return editAbsentName_1;

    }
    public String getEditAbsentName_2() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(30);
        editAbsentName_2 = new String(l, 0, l.length);

        closeRecStore();

        return editAbsentName_2;

    }
    public String getEditAbsentDTMF_1() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(31);
        editAbsentDTMF_1 = new String(l, 0, l.length);

        closeRecStore();

        return editAbsentDTMF_1;

    }
    public String getEditAbsentDTMF_2() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(32);
        editAbsentDTMF_2 = new String(l, 0, l.length);

        closeRecStore();

        return editAbsentDTMF_2;

    }
    public String getHHMMTTMM_1() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(33);
        edit_HHMM_TTMM_1 = new String(l, 0, l.length);

        closeRecStore();

        return edit_HHMM_TTMM_1;

    }
    public String getHHMMTTMM_2() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(34);
        edit_HHMM_TTMM_2 = new String(l, 0, l.length);

        closeRecStore();

        return edit_HHMM_TTMM_2;

    }

    public String getPbxID() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(35);
        pbx_ID = new String(l, 0, l.length);

        closeRecStore();

        return pbx_ID;

    }

    public String getPrgName() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(36);
        prg_Name = new String(l, 0, l.length);

        closeRecStore();

        return prg_Name;
    }
    public String getTransferCall() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(37);
        transferCall = new String(l, 0, l.length);

        closeRecStore();

        return transferCall;
    }

    public String getDTMFsend() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(38);
        setP_PBX = new String(l, 0, l.length);

        closeRecStore();

        return setP_PBX;
    }

    public String getIMEI() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(39);
        imei = new String(l, 0, l.length);

        closeRecStore();

        return imei;
    }


    public String getRename_1() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(40);
       rename_1 = new String(l, 0, l.length);

       closeRecStore();

       return rename_1;
   }
   public String getRename_2() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(41);
       rename_2 = new String(l, 0, l.length);

       closeRecStore();

       return rename_2;
   }
   public String getRename_3() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(42);
       rename_3 = new String(l, 0, l.length);

       closeRecStore();

       return rename_3;
   }

   public String getDefaultLanguage() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(43);
       default_lang = new String(l, 0, l.length);

       closeRecStore();

       return default_lang;
   }

   public String getENGlang() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(44);
       eng_lang = new String(l, 0, l.length);

       closeRecStore();

       return eng_lang;
   }

   public String getPhoneBrands() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(45);
       device_brands = new String(l, 0, l.length);

       closeRecStore();

       return device_brands;
   }

   public String getPhoneModel() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(46);
       deveice_model = new String(l, 0, l.length);

       closeRecStore();

       return deveice_model;
   }

   public String getAbsentStatus() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(47);
       absentStatus = new String(l, 0, l.length);

       closeRecStore();

       return absentStatus;
   }

   public String getPbxName() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(48);
       pbx_name = new String(l, 0, l.length);

       closeRecStore();

       return pbx_name;
   }

   public String getImeiFalse() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(49);
       imeiFalse = new String(l, 0, l.length);

       closeRecStore();

       return imeiFalse;
   }

   public String getSystemIMEIProperty() throws InvalidRecordIDException,
           RecordStoreNotOpenException, RecordStoreException {

       openRecStore();

       byte l[] = recStore.getRecord(50);
       systemImeiProperty = new String(l, 0, l.length);

       closeRecStore();

       return systemImeiProperty;
   }

    // Internnummer DB poster, plats 51 - 70
    public String getInternName(int p) throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        int IDInternNumber = p;

        openRecStore();

        byte l[] = recStore.getRecord(IDInternNumber);
        InternName = new String(l, 0, l.length);

        closeRecStore();

        return InternName;
    }

    public String getTWO() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte j[] = recStore.getRecord(111);
        this.CheckTwo = new String(j, 0, j.length);

        closeRecStore();

        return this.CheckTwo;

    }

    /* Andra metoder öppna och stäng update read record RSM - metoder */

    public void readRecordsUpdate() {
        try {
            System.out.println("Number of records: " + recStore.getNumRecords());

            if (recStore.getNumRecords() > 0) {
                RecordEnumeration re = recStore.enumerateRecords(null, null, false);
                while (re.hasNextElement()) {
                    String str = new String(re.nextRecord());
                    System.out.println("Record: " + str);
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void readRecords() {
        try {
            // Intentionally small to test code below
            byte[] recData = new byte[5];
            int len;

            for (int i = 1; i <= recStore.getNumRecords(); i++) {
                // Allocate more storage if necessary
                if (recStore.getRecordSize(i) > recData.length) {
                    recData = new byte[recStore.getRecordSize(i)];
                }

                len = recStore.getRecord(i, recData, 0);
                if (Settings.debug) {
                    System.out.println("Record ID#" + i + ": " +
                                       new String(recData, 0, len));
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void writeRecord(String str) {
        byte[] rec = str.getBytes();

        try {
            System.out.println("sparar ");
            recStore.addRecord(rec, 0, rec.length);
            System.out.println("Writing record: " + str);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public void openRecStore() {
        try {
            // The second parameter indicates that the record store
            // should be created if it does not exist
            recStore = RecordStore.openRecordStore(REC_STORE, true);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void closeRecStore() {
        try {
            recStore.closeRecordStore();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /* DATA BASEN  --------------------------------------------   */

    public void setDataStore() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreNotOpenException,
            RecordStoreException, IOException {

        openRecStore();
        readRecords();
        readRecordsUpdate();

        // om innehållet i databasen är '0' så spara elementen i databasen.
        if (recStore.getNumRecords() == 0) {

            writeRecord(setYear_30DAY);    // 1. År (30 dagar framåt i tiden)
            writeRecord(setMounth_30DAY);  // 2. Månad (30 dagar framåt i tiden)
            writeRecord(setDay_30DAY);     // 3. Dag (30 dagar framåt i tiden)
            writeRecord("9");              // 4. Linje-Access
            writeRecord("+44");            // 5. Växelnummer
            writeRecord(setYear_TODAY);    // 6. År (dagens datum)
            writeRecord(setMounth_TODAY);  // 7. Månad (dagens datum)
            writeRecord(setDay_TODAY);     // 8. Dag (dagens datum)
            writeRecord("0");              // 9. Anknytningsnummer
            writeRecord("0");              // 10. Pinkod
            writeRecord("0");              // 11. Autologin | PINkodlogin
            writeRecord("0");              // 12. Röstbrevlåda Operatör
            writeRecord("0");              // 13. Debug plats
            writeRecord("0");              // 14. Icon nummer
            writeRecord("44");             // 15. Landsnummer
            writeRecord("0");              // 16. mexONOFF
            writeRecord("0");              // 17. keycode
            writeRecord("0");              // 18. Röstbrevlåda PBX
            writeRecord("0");              // 19. Default, Språk engelska
            writeRecord("0");              // 20. Demo Licens
            writeRecord("0");              // 21. Check_PBX_Status kontrollerar om config-settings är satt
            writeRecord("0");              // 22. Företagsnamn
            writeRecord("0");              // 23. Användarnamn
            writeRecord("0");              // 24. Ny hänvisning 3, Namn.
            writeRecord("0");              // 25. Ny hänvisning 3, DTMF sträng.
            writeRecord("0");              // 26. Ny hänvisning 3, Datum/Tid.
            writeRecord("*7");             // 27. PreEditCode 'default'
            writeRecord("English");        // 28. Landsnamn >> English 'default'
            writeRecord("0");              // 29. Ny hänvisning 1, Namn.
            writeRecord("0");              // 30. Ny hänvisning 2, Namn.
            writeRecord("0");              // 31. Ny hänvisning 1, DTMF sträng.
            writeRecord("0");              // 32. Ny hänvisning 2, DTMF sträng.
            writeRecord("0");              // 33. Ny hänvisning 1, Datum/Tid.
            writeRecord("0");              // 34. Ny hänvisning 2, Datum/Tid.
            writeRecord("0");              // 35  PBX ID/TYP tex. Aastra = 2.
            writeRecord("0");              // 36. Program Namn tex. mobismaSC
            writeRecord("0");              // 37. Tecken för 'Transfer call'
            writeRecord("0");              // 38. Tecken för 'skicka DTMF signaler'
            writeRecord("0");              // 39. IMEI, telefonens serienummer
            writeRecord("0");              // 40. rename_1 '1' betyder att plats 24 är renamed
            writeRecord("0");              // 41. rename_2 '1' betyder att plats 29 är renamed
            writeRecord("0");              // 42. rename_3 '1' betyder att plats 30 är renamed
            writeRecord("0");              // 43. Default Language '1' inte engelska, '0' engelska
            writeRecord("0");              // 44. Sätt språket till Engelska, ta bort val i languageList.
            writeRecord("0");              // 45. DeviceBrands (Telefonmärke, tex Nokia).
            writeRecord("0");              // 46. DeveiceModel (Telefonmodel, tex E51).
            writeRecord("0");              // 47. Hänvisning, status tex, Lunch åter: 13.00, el. Semester: 25/6-09
            writeRecord("0");              // 48. Växelnamn tex, Panasonic.
            writeRecord("0");              // 49. Om IMEI är sant '0', falskt '1'.
            writeRecord("0");              // 50. System.getProperty("com.sonyericsson.imei"). sant '0', falskt '1'.

            writeRecord("0");              // 51. Internnummer 1
            writeRecord("0");              // 52. Internnummer 2
            writeRecord("0");              // 53. Internnummer 3
            writeRecord("0");              // 54. Internnummer 4
            writeRecord("0");              // 55. Internnummer 5
            writeRecord("0");              // 56. Internnummer 6
            writeRecord("0");              // 57. Internnummer 7
            writeRecord("0");              // 58. Internnummer 8
            writeRecord("0");              // 59. Internnummer 9
            writeRecord("0");              // 60. Internnummer 10
            writeRecord("0");              // 61. Internnummer 11
            writeRecord("0");              // 62. Internnummer 12
            writeRecord("0");              // 63. Internnummer 13
            writeRecord("0");              // 64. Internnummer 14
            writeRecord("0");              // 65. Internnummer 15
            writeRecord("0");              // 66. Internnummer 16
            writeRecord("0");              // 67. Internnummer 17
            writeRecord("0");              // 68. Internnummer 18
            writeRecord("0");              // 69. Internnummer 19
            writeRecord("0");              // 70. Internnummer 20
            writeRecord("0");              // 71. Internnummer 21
            writeRecord("0");              // 72. Internnummer 22
            writeRecord("0");              // 73. Internnummer 23
            writeRecord("0");              // 74. Internnummer 24
            writeRecord("0");              // 75. Internnummer 25

            writeRecord("0");              // 76. Callednumber 1
            writeRecord("0");              // 77. Callednumber 2
            writeRecord("0");              // 78. Callednumber 3
            writeRecord("0");              // 79. Callednumber 4
            writeRecord("0");              // 80. Callednumber 5
            writeRecord("0");              // 81. Callednumber 6
            writeRecord("0");              // 82. Callednumber 7
            writeRecord("0");              // 83. Callednumber 8
            writeRecord("0");              // 84. Callednumber 9
            writeRecord("0");              // 85. Callednumber 10
            writeRecord("0");              // 86. Callednumber 11
            writeRecord("0");              // 87. Callednumber 12
            writeRecord("0");              // 88. Callednumber 13
            writeRecord("0");              // 89. Callednumber 14
            writeRecord("0");              // 90. Callednumber 15
            writeRecord("0");              // 91. Callednumber 16
            writeRecord("0");              // 92. Callednumber 17
            writeRecord("0");              // 93. Callednumber 18
            writeRecord("0");              // 94. Callednumber 19
            writeRecord("0");              // 95. Callednumber 20
            writeRecord("0");              // 96. Callednumber 21
            writeRecord("0");              // 97. Callednumber 22
            writeRecord("0");              // 98. Callednumber 23
            writeRecord("0");              // 99. Callednumber 24
            writeRecord("0");              // 100. Callednumber 25

            writeRecord("0");              // 101. Ledig plats.
            writeRecord("0");              // 102. Ledig plats.
            writeRecord("0");              // 103. Ledig plats.
            writeRecord("0");              // 104. Ledig plats.
            writeRecord("0");              // 105. Ledig plats.
            writeRecord("0");              // 106. Ledig plats.
            writeRecord("0");              // 107. Ledig plats.
            writeRecord("0");              // 108. Ledig plats.
            writeRecord("0");              // 109. Ledig plats.
            writeRecord("0");              // 110. Ledig plats.


            // SET TWO plats 111
            writeRecord("1");              // 111. Demolicens utgått == "2"
        }

        // sätter nummer i fönstret under inställningar...

        byte d[] = recStore.getRecord(4);
        lineAccess_PBX = new String(d, 0, d.length);

        byte e[] = recStore.getRecord(5);
        switchBoardNumber_PBX = new String(e, 0, e.length);

        byte j[] = recStore.getRecord(9);
        extensionNumber_PBX = new String(j, 0, j.length);

        byte k[] = recStore.getRecord(10);
        pinCodeNumber_PBX = new String(k, 0, k.length);

        byte l[] = recStore.getRecord(11);
        pbx_Type = new String(l, 0, l.length);

        byte mvoiceMessage[] = recStore.getRecord(12); // operatör
        voiceMailOperator_PBX = new String(mvoiceMessage, 0,
                                           mvoiceMessage.length);

        byte mlanguage[] = recStore.getRecord(15);
        countryCode_PBX = new String(mlanguage, 0, mlanguage.length);

        byte m[] = recStore.getRecord(17);
        HGP_PBX = new String(m, 0, m.length);

        byte nlanguage[] = recStore.getRecord(19);
        lang_PBX = new String(nlanguage, 0, nlanguage.length);


        byte mTextVoiceEditCode[] = recStore.getRecord(18); // PBX
        voiceMailSwitchboard_PBX = new String(mTextVoiceEditCode, 0,
                                              mTextVoiceEditCode.length);

        byte mTextPreEditCode[] = recStore.getRecord(27);
        precode_PBX = new String(mTextPreEditCode, 0,
                                 mTextPreEditCode.length);

        byte Rename_1[] = recStore.getRecord(29);
        editAbsentName_1 = new String(Rename_1, 0,
                                            Rename_1.length);

        byte Rename_2[] = recStore.getRecord(30);
        editAbsentName_2 = new String(Rename_2, 0,
                                            Rename_2.length);

        closeRecStore();
    }

    // Om något inputfönster(post) i databasen är tom sätt tillbaka värdet...
    // Det finns totalt 15 olika 'sätt' som databasen kan ha tomma poster med 4 värden.
    // Stämmer med den diskreta matematiken enligt KTH ;-)
    public void upDateDataStore() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        openRecStore();

        String setBackAccessNumberRecord = lineAccess_PBX;
        String setBackSwitchBoardNumberRecord = switchBoardNumber_PBX;
        String setBackExtensionNumberRecord = extensionNumber_PBX;
        String setBackPinCodeNumberRecord = pinCodeNumber_PBX;
        String setBackKeyCodeRecord = HGP_PBX;
        String setBackLanguageRecord = lang_PBX;
        String setBackLanguageCallNumberRecord = countryCode_PBX;
        String setBackpreEditCode = precode_PBX;
        String setBackvoiceEditCode = voiceMailSwitchboard_PBX;
        String setBackvoiceMessage = voiceMailOperator_PBX;

        // FAll 1 alla 4 poster är tomma. spar in alla tomma värden igen.
        if (recStore.getRecord(4) == null && recStore.getRecord(5) == null &&
            recStore.getRecord(9) == null && recStore.getRecord(10) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null &&
                   recStore.getRecord(9) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(9) == null &&
                   recStore.getRecord(10) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null &&
                   recStore.getRecord(10) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(5) == null && recStore.getRecord(9) == null &&
                   recStore.getRecord(10) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(9) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(10) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(5) == null && recStore.getRecord(10) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(5) == null && recStore.getRecord(9) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(9) == null && recStore.getRecord(10) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(9) == null && recStore.getRecord(12) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(18, setBackvoiceEditCode.getBytes(),
                               0, setBackvoiceEditCode.length());

        } else if (recStore.getRecord(4) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());

        } else if (recStore.getRecord(5) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());

        } else if (recStore.getRecord(9) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(10) == null) {
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(17) == null) {
            recStore.setRecord(17, setBackKeyCodeRecord.getBytes(), 0,
                               setBackKeyCodeRecord.length());

        } else if (recStore.getRecord(19) == null) {
            recStore.setRecord(19, setBackLanguageRecord.getBytes(), 0,
                               setBackLanguageRecord.length());

        } else if (recStore.getRecord(15) == null) {
            recStore.setRecord(15, setBackLanguageCallNumberRecord.getBytes(),
                               0,
                               setBackLanguageCallNumberRecord.length());

        } else if (recStore.getRecord(27) == null) {
            recStore.setRecord(27, setBackpreEditCode.getBytes(),
                               0,
                               setBackpreEditCode.length());

        } else if (recStore.getRecord(18) == null) {
            recStore.setRecord(18, setBackvoiceEditCode.getBytes(),
                               0,
                               setBackvoiceEditCode.length());

        } else if (recStore.getRecord(9) == null && recStore.getRecord(12) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(18, setBackvoiceEditCode.getBytes(),
                               0, setBackvoiceEditCode.length());

        }

        else if (recStore.getRecord(12) == null) {
            recStore.setRecord(12, setBackvoiceMessage.getBytes(),
                               0,
                               setBackvoiceMessage.length());

        }

        closeRecStore();
    }

}
