package MControll;

/**
 * <p>Title: Mobile Extension</p>
 *
 * <p>Description: Aastra Business Phone PBX Include</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: mobisma ab</p>
 *
 * @author Peter Albertsson
 * @version 2.0
 */

/*Import av java-paket*/
/* DataStore */
import java.io.*;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.*;

/* Model */
import MModel.Date_Time;
import MView.*;
import MDataStore.DataBase_RMS;
import MModel.CONF;
import MModel.CONF_settings;
import MModel.ConnectTCPIP_Socket;
import MModel.Methods;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import MModel.SortClass;
import MModel.InternNumber;


/* Klassen Main_Controll startar här. */
public class Main_Controll extends MIDlet implements ItemStateListener,
        CommandListener,

        Runnable {
    /*
     - Konfigueringsfilen CONF och Language --- initierar programmets
         - Språk, Ikoner och annan information.
     */
    private MModel.CONF conf;
    private MModel.CONF_settings conf_S;
    private MModel.Language language;
    private MModel.Date_Time dateTime;
    private MDataStore.DataBase_RMS rms;
    private MView.AboutUs aboutUs;
    private MView.HelpInfo helpInfo;
    private MView.ServerNumber serverNumber;
    private MModel.ConnectTCPIP_Socket tcpip;
    private MModel.Methods methods;

    // Portnummer för TCP/IP connection
    private static String url = "socket://127.0.0.1:8100";

    public static final int CONFDATA = 1;
    public static final int LOGDATA = 2;
    public static final int IMEIDATA = 3;
    public static final int LOGSIZE = 4;

    private static String inturl = "socket://127.0.0.1:8100";
    private static String ext_url =
            "http://www.mobisma.com:80/socketapi/mobilesock.php";
    private static String confdata;
    private static String logdata;
    private static String imei;
    private static int logfilesize;
    private static int icount;
    private static String logrequest;
    private int requestwhat;
    public ChoiceGroup radioButtons = new ChoiceGroup("", Choice.EXCLUSIVE);
    private ChoiceGroup editButtons = new ChoiceGroup("", Choice.EXCLUSIVE);
    private int defaultIndex, editButtonIndex;

    public int IDInternNumber;
    private boolean isInitialized;
    private boolean splashIsShown;

    // Public strängar.
    public String
            checkAlert,
    ViewDateString,
    editNEWAbsent,
    editHHTTMMTT = "",
    mexOnOff,
    absentChooseOneTwo;

    /* PBX Settings */
    public String
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
    prg_Name,
    CheckTwo,
    pbx_type,
    eng_lang,
    device_brands,
    deveice_model,
    pbx_name,
    absentStatus,
    emptyPrecense = "",


    // Ny hänvisning
    editAbsentName_1,
    editAbsentName_2,
    editAbsentDTMF_1,
    editAbsentDTMF_2,
    setAbsentNAMEString,
    edit_HHMM_TTMM_1,
    edit_HHMM_TTMM_2;

    private Ticker absentStatusTicker;

    private String

            request, // TCP/IP connection
    ResponceMessage,

    /*Tid och Datum*/
    setYear_30DAY, setDay_30DAY, setMounth_30DAY, setMounthName_30DAY,
    setDay_TODAY, setMounth_TODAY, setYear_TODAY, setMounthNameToday;

    /* Listor/Menyer i prg */
    public List

            debug_List, linePrefix_List,
    pbx_List, mainList, language_List, absentList, absentEditList,
    absentListChoose, groupList, voiceMailPBXList, callForwardList;

    /* Övriga settings som Display, Alert, Commands och Form osv. */
    private Display display;

    public Alert alertEditSettings, alertRestarting,
    alertExit, alertON, alertOFF, alertMexAlreadyONOFF,
    alertDebugONOFF, alertSendOKNOK, alertSENDDebug, alertLogOutDebug,
    alertExpernceLicense;

    private Form
            // AutoAccess settings.
            AutoAccessSettingsForm,
    AutoAccessSettingsNOPrefixForm,

    // Koppla samtal
    connectPhoneForm, connectEditForm,
    connectRenameForm, connectRenameEditForm,

    // Hänvisningsformer.
    lunchForm, // - Lunch åter
    outForm, // - Tillfälligt ute
    meetingForm, // - Sammanträde
    travelForm, // - Tjänsteresa
    sickForm, // - Sjuk
    vacationForm, // - Semester
    goneForDayForm, // - Gått för dagen
    newAbsent_1Form, // - Ny hänvisning
    newAbsent_2Form, // - Ny hänvisning

    // Editera ny hänvisning
    editAbsentForm, // - Lägg till ny hänvisning

    // Grupp former.
    loginGroupForm, logoffGroupForm,

    // PBX Röstbrevlåda form.
    voiceEditForm_PBX,

    // Operatör Röstbrevlåda (resarverar ett nummer)
    voiceOperatorMessageForm,

    // Ange landsnummer form.
    countryForm,

    // Vidarekoppling
    callforwardpresentForm, // Intern vidarekoppling
    transferForwardCallForm, // Extern vidarekoppling
    aborttransfercallForm, // Abryt extern vidarekoppling

    // Log-data till server
    logDataForm;

    private Command
            // AutoAccess kommandon.
            AutoAccessBackCommand, AutoAccessCancelCommand,
    AutoAccessSaveCommand,
    AutoAccessSaveNOPrefixCommand, AutoAccessBackNOPrefixCommand,
    AutoAccessCancelNOPrefixCommand,

    // Koppla samtal kommando
    connectSendCommand, connectBackCommand, connectEditRenameCommand,
    connectEditBackCommand, connectEditCancelCommand,
    connectEditSaveCommand, connectRenameBackCommand,
    connectEditRenameBackCommand, connectEditRenameCancelCommand,
    connectEditRenameSaveCommand, connectResumeCommand,

    // Vidarekoppla kommando
    callForwardListBackCommand,

    // Röstbrevlåda PBX, kommandon
    voiceEditSaveCommand_PBX, voiceEditBackcommand_PBX,
    voiceEditCancelCommand_PBX,

    // Röstbrevlåda Operatör, kommandon
    voiceOperatorMessageSaveCommand, voiceOperatorMessageCancelCommand,
    voiceOperatorMessageBackCommand,

    // Röstbrevlådan listkommando
    voiceMailPBXListBackCommand,

    // Country kommandon
    countryBackCommand, countryCancelCommand, countrySaveCommand,

    // MainList kommandon
    mainListEditCommand, mainListaboutMobismaCommand, mainListExitCommand,

    // Hänvisning kommandon
    BackCommandAbsentList, // - kommando till huvudmenyn för hänvisning.
    lunchBackCommand, lunchSendCommand, // - Lunch åter
    outBackCommand, outSendCommand, // - Tillfälligt ute
    meetingBackCommand, meetingSendCommand, // - Sammanträde
    travelBackCommand, travelSendCommand, // - Tjänsteresa
    sickBackCommand, sickSendCommand, // - Sjuk
    vacationBackCommand, vacationSendCommand, // - Semester
    goneForDayBackCommand, goneForDaySendCommand, // - Gått för dagen
    // - Ta bort hänvisning se absentList plats 6.
    newAbsent_1BackCommand, newAbsent_1SendCommand, // - Ny hänvisning
    newAbsent_2BackCommand, newAbsent_2SendCommand, // - Ny hänvisning

    // Editera ny hänvisning (Form) kommandon
    editAbsentBackCommand, editAbsentSaveCommand,
    editAbsentCancelCommand,

    // Editera ny hänvisning (List) kommandon
    editAbsentListBackCommand,
    editAbsentListCancelCommand, editTimeDateCancelCommand,

    // Grupp kommandon
    groupBackCommand, loginGroupSendCommand,
    loginGroupBackCommand, logoffGroupSendCommand,
    logoffGroupBackCommand,

    // Fristående kommandon.
    GraphicsBackCommand, goGraphicsBackCommand,
    GraphicsAboutCommand, GraphicsHelpCommand,

    // pbx_List kommando.
    pbx_ListCancelCommand,

    // language_List kommando
    languageListBackCommand,

    // Alert-Exit 'confirm' Ja eller Nej
    confirmExitYESCommand, confirmExitNOCommand,
    confirmOnYESCommand, confirmOnCancelCommand,
    confirmOffYESCommand, confirmOffCancelCommand,

    // Alert-Experince Licens
    licensYESCommand,

    // kommando för trådarna RUN.
    thCmd,

    // Kommando för vidarekoppling
    // (Intern vidarekoppling)
    CallForwardSendCommand, CallForwardBackCommand,
    // (Extern vidarekoppling)
    transferCallForwardBackCommand, transferCallForwardSendCommand,
    // (Abryt extern vidarekoppling)
    abortTransferCallForwardSendCommand, abortTransferCallForwardBackCommand,

    // Log-data till server
    logDataLogInCommand,
    logDataCancelLogInCommand, debugListLogOutCommand,

    // linePrefix_List
    linePrefixBackCommand;

    public TextField
            // Koppla samtal textfält
            connectTextField,
    connectEditNameTextField, connectEditExtensionTextField,
    connectEditRenameNameTextField, connectEditRenameExtensionTextField,


    // HänvisningsTextField.
    lunchTextField, // - Lunch åter
    outTextField, // - Tillfälligt ute
    meetingTextField, // - Sammanträde
    travelTextField, // - Tjänsteresa
    sickTextField, // - Sjuk
    vacationTextField, // - Semester
    goneForDayTextField, // - Gått för dagen
    newAbsent_1TextField, // - Ny hänvisning
    newAbsent_2TextField, // - Ny hänvisning

    // Editera ny hänvisning
    editAbsentName_TextField, // - Lägg till namn för ny hänvisning
    editAbsentNewDTMF_TextField, // - Lägg till DTMF sträng

    // AutoAccess textfält tillhör AutoAccessSettingsForm
    AutoAccessLineAccessTextField, AutoAccessSwitchBoardTextField,
    AutoAccessNOPrefixSwitchBoardTextField,

    // PBX'ns textfält för röstbrevlåda tillhör voiceEditForm_PBX
    voiceMailPBXTextField_PBX,

    // Operatör voicemail tillhör voiceOperatorMessageForm
    voiceOperatorMessageTextField,

    // Textfältet tillhör countryForm
    countryTextField,

    // Textfältet tillhör loginGroupForm
    loginGroupTextField,

    // Textfältet tillhör logoffGroupForm
    logoffGroupTextField,

    // Textfält vidarekoppling
    callforwardpresentTextField, // Intern vidarekoppling
    transferForwardCallownNumberTextField, // Extern vidarekoppling
    transferForwardCallnewNumberTextField, // Extern vidarekoppling
    aborttransfercallTextField, // Avbryt extern vidarekoppling

    // Log-data till server
    logDataTextField;

    //================== D E F A U L T - Språk =================================

    public String

            settingsDefaultAbout_DEF,
    SettingsDefaultAccessPINcode_DEF,
    extensionDefaultAddNew_DEF,
    callForwardDefaultAllCalls_DEF,
    absentDefaultAtExt_DEF,
    settingsDefaultAutoAccess_DEF,
    genDefaultBack_DEF,
    absentDefaultBackAt_DEF,
    callForwardDefaultBusy_DEF,
    callForwardDefaultBusyNoAnswer_DEF,
    callDefaultCall_DEF,
    extensionDefaultCall_DEF,
    callForwardDefault_DEF,
    msgDefaultCallistIsEmpty_DEF,
    genDefaultCancel_DEF,
    alertDefaultCantAddAnymoreExt_DEF,
    alertDefaultCouldntAddChangesEmtpyField_DEF,
    alertDefaultCouldntAddRecord_DEF,
    alsertDefaultChangesSave_DEF,
    mgsDefaultContactListIsEmpty_DEF,
    alertDefaultCountryCodeError_DEF,
    settingsDefaultCountryCode_DEF,
    genDefaultDelete_DEF,
    genDefaultDeleteAll_DEF,
    dialledCallsDefault_DEF,
    callForwardDefaultDontDisturb_DEF,
    genDefaultEdit_DEF,
    settingsDefaultEditPBXAccess_DEF,
    absentDefaultEditPresence_DEF,
    voiceMailDefaultEditVoicemail_DEF,
    enterDefaultEngerCharacter_DEF,
    enterDefaultEnterExtension_DEF,
    enterDefaultEnterGroupNumber_DEF,
    enterDefaultEnterHHMM_DEF,
    enterDefaultEnterNumber_DEF,
    alertDefaultErrorChangeTo_DEF,
    alertDefaultError_DEF,
    enterDefaultEnterMMDD_DEF,
    exitDefaultExitTheProgramYesOrNo_DEF,
    genDefaultExit_DEF,
    settingsDefaultExtension_DEF,
    callExtensionDefaultExtensionWasAdded_DEF,
    callForwardDefaultExternCalls_DEF,
    absentDefaultGoneHome_DEF,
    groupsDefaultGroups_DEF,
    settingsDefaultHelp_DEF,
    absentDefaultInAMeeting_DEF,
    alertDefaultInfo_DEF,
    alertDefaultInstedOf_DEF,
    callForwardDefaultInternCalls_DEF,
    settingsDefaultLanguage_DEF,
    settingsDefaultLineAccess_DEF,
    groupsDefaultLoginAllGroups_DEF,
    groupsDefaultLoginSpecificGroup_DEF,
    groupsDefaultLogoutAllGroups_DEF,
    groupsDefaultLogoutSpecificGroup_DEF,
    alertDefaultMaxSize_DEF,
    genDefaultMinimise_DEF,
    callExtensionDefaultName_DEF,
    exitDefaultNo_DEF,
    callForwardDefaultNoAnswer_DEF,
    settingsDefaultOptions_DEF,
    absentDefaultOutUntil_DEF,
    absentDefaultPersonalAtt_DEF,
    settingsDefaultPINcode_DEF,
    settingsDefaultPreEditCode_DEF,
    callForwardDefaultRemove_DEF,
    absentDefaultRemovePresence_DEF,
    exitDefaultRestartProgram_DEF,
    alertDefaultSaveChanges_DEF,
    genDefaultSave_DEF,
    settingsDefaultSelectCountryCode_DEF,
    genDefaultSelect_DEF,
    genDefaultSend_DEF,
    absentDefaultSetPresence_DEF,
    settingsDefaultSettings_DEF,
    callExtensionDefaultSurname_DEF,
    settingsDefaultSwitchboardNumber_DEF,
    absentDefaultSystemAttOne_DEF,
    absentDeafaultSystemAttTwo_DEF,
    absentDeafaultWillReturnSoon_DEF,
    alertDefaultWrongInputTryAgain_DEF,
    voiceMailDefaultVoiceMail_DEF,
    genDefaultYes_DEF,

    accessPBXDefault_DEF,
    autoAccessDefault_DEF,
    accessViaPINCodeDefault_DEF,
    dialDefault_DEF,

    alertExitMEXOnMessage_DEF,
    AlertMessageExitText_DEF,
    alertMessageMEXOn_DEF,
    alertMessageMEXOff_DEF,
    alertMessageMexServerInfo_DEF,
    alertMessageMexAlreadyON_DEF,
    alertMessageMexAlreadyOFF_DEF,
    mainListAttributMexOn_DEF,
    mainListAttributMexOff_DEF,
    operatorVoicemail_DEF,

    absentTimeOfReturn_DEF,
    absentDateOfReturn_DEF,
    absentLunch_DEF,
    absentMeeting_DEF,
    absentVacation_DEF,
    absentIllness_DEF,

    callForwardTransfer_DEF,
    callForwardPermForward_DEF,
    callForwardInterForward_DEF,
    callForwardExternForward_DEF,
    callForwardCancelExtern_DEF,
    callForwardCancelPermIntern_DEF,

    transferBack_DEF,

    textYourNumber_DEF,
    textNewNumber_DEF,

    voiceMailActivate_DEF,
    voiceMailDeActivate_DEF,
    voiceMailListen_DEF,

    version_DEF,
    system_DEF,

    programExitON_DEF,
    programExitOFF_DEF;


    /* Konstruktorn startar här. */

    public Main_Controll() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        //------------ kontrollerar om CONF.java är satt -----------------------
        MModel.CONF_settings conf_S = new CONF_settings();
        conf_S.setConfSettings();
        conf_S = null;

        // --- SplashScreen
        display = Display.getDisplay(this);

        /*TCP_IP_SOCKET*/
        this.ResponceMessage = "";

        /*RMS DB objekt hämtar in egenskaperna här*/
        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        /* PBX Settings, attribut från RMS DB */
        this.lineAccess_PBX = rms.lineAccess_PBX;
        this.HGP_PBX = rms.HGP_PBX;
        this.precode_PBX = rms.precode_PBX;
        this.voiceMailSwitchboard_PBX = rms.voiceMailSwitchboard_PBX;
        this.switchBoardNumber_PBX = rms.switchBoardNumber_PBX;
        this.countryCode_PBX = rms.countryCode_PBX;
        this.extensionNumber_PBX = rms.extensionNumber_PBX;
        this.pinCodeNumber_PBX = rms.pinCodeNumber_PBX;
        this.voiceMailOperator_PBX = rms.voiceMailOperator_PBX;
        this.mexONOFF_PBX = rms.mexONOFF_PBX;
        this.checkStatus_PBX = rms.checkStatus_PBX;
        this.dbg_PBX = rms.dbg_PBX;
        this.demo_PBX = rms.demo_PBX;
        this.companyName_PBX = rms.companyName_PBX;
        this.userName_PBX = rms.userName_PBX;
        this.countryName_PBX = rms.countryName_PBX;
        this.iconNumber_PBX = rms.iconNumber_PBX;
        this.lang_PBX = rms.lang_PBX;
        this.CheckTwo = rms.CheckTwo;
        this.prg_Name = rms.getPrgName();
        this.pbx_type = rms.getPBXType();
        this.eng_lang = rms.getENGlang();
        this.absentStatus = rms.getAbsentStatus();
        this.device_brands = rms.getPhoneBrands();
        this.deveice_model = rms.getPhoneModel();
        this.pbx_name = rms.getPbxName();


        // Ny hänvisning
        this.editAbsentName_1 = rms.getEditAbsentName_1();
        this.editAbsentName_2 = rms.getEditAbsentName_2();
        this.editAbsentDTMF_1 = rms.getEditAbsentDTMF_1();
        this.editAbsentDTMF_2 = rms.getEditAbsentDTMF_2();
        this.edit_HHMM_TTMM_1 = rms.getHHMMTTMM_1();
        this.edit_HHMM_TTMM_2 = rms.getHHMMTTMM_2();

        /*Datum och Tid objekt hämtar in egenskaperna här*/
        MModel.Date_Time dateTime = new Date_Time();

        /*Settings för metoden ControllDateTime() */
        this.setDay_30DAY = rms.getDay_30_DAY();
        this.setMounth_30DAY = rms.getMounth_30_DAY();
        this.setMounthName_30DAY = dateTime.setMounth(setMounth_30DAY);
        this.setYear_30DAY = rms.getYear_30_DAY();

        this.setDay_TODAY = rms.getDay_TODAY();
        this.setMounth_TODAY = rms.getMounth_TODAY();
        this.setMounthNameToday = rms.getMounth_TODAY();
        this.setMounthNameToday = dateTime.setMounth(setMounth_TODAY);
        this.setYear_TODAY = rms.getYear_TODAY();

        /* ================== SPRÅK =============================  */

        // --- Default språk, Engelska. Alltid andraspråk i prg.

        if (this.lang_PBX.equals("2")) { // English >> Default

            settingsDefaultAbout_DEF = language.settingsDefaultAbout_1;
            SettingsDefaultAccessPINcode_DEF = language.
                                               SettingsDefaultAccessPINcode_1;
            extensionDefaultAddNew_DEF = language.extensionDefaultAddNew_1;
            callForwardDefaultAllCalls_DEF = language.
                                             callForwardDefaultAllCalls_1;
            absentDefaultAtExt_DEF = language.absentDefaultAtExt_1;
            settingsDefaultAutoAccess_DEF = language.
                                            settingsDefaultAutoAccess_1;
            genDefaultBack_DEF = language.genDefaultBack_1;
            absentDefaultBackAt_DEF = language.absentDefaultBackAt_1;
            callForwardDefaultBusy_DEF = language.callForwardDefaultBusy_1;
            callForwardDefaultBusyNoAnswer_DEF =
                    language.callForwardDefaultBusyNoAnswer_1;
            callDefaultCall_DEF = language.callDefaultCall_1;
            extensionDefaultCall_DEF = language.extensionDefaultCall_1;
            callForwardDefault_DEF = language.callForwardDefault_1;
            msgDefaultCallistIsEmpty_DEF = language.msgDefaultCallistIsEmpty_1;
            genDefaultCancel_DEF = language.genDefaultCancel_1;
            alertDefaultCantAddAnymoreExt_DEF =
                    language.alertDefaultCantAddAnymoreExt_1;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    language.alertDefaultCouldntAddChangesEmtpyField_1;
            alertDefaultCouldntAddRecord_DEF = language.
                                               alertDefaultCouldntAddRecord_1;
            alsertDefaultChangesSave_DEF = language.alertDefaultChangesSave_1;
            mgsDefaultContactListIsEmpty_DEF = language.
                                               mgsDefaultContactListIsEmpty_1;
            alertDefaultCountryCodeError_DEF = language.
                                               alertDefaultCountryCodeError_1;
            settingsDefaultCountryCode_DEF = language.
                                             settingsDefaultCountryCode_1;
            genDefaultDelete_DEF = language.genDefaultDelete_1;
            genDefaultDeleteAll_DEF = language.genDefaultDeleteAll_1;
            dialledCallsDefault_DEF = language.dialledCallsDefault_1;
            callForwardDefaultDontDisturb_DEF =
                    language.callForwardDefaultDontDisturb_1;
            genDefaultEdit_DEF = language.genDefaultEdit_1;
            settingsDefaultEditPBXAccess_DEF = language.
                                               settingsDefaultEditPBXAccess_1;
            absentDefaultEditPresence_DEF = language.
                                            absentDefaultEditPresence_1;
            voiceMailDefaultEditVoicemail_DEF =
                    language.voiceMailDefaultEditVoicemail_1;
            enterDefaultEngerCharacter_DEF = language.
                                             enterDefaultEngerCharacter_1;
            enterDefaultEnterExtension_DEF = language.
                                             enterDefaultEnterExtension_1;
            enterDefaultEnterGroupNumber_DEF = language.
                                               enterDefaultEnterGroupNumber_1;
            enterDefaultEnterHHMM_DEF = language.enterDefaultEnterHHMM_1;
            enterDefaultEnterNumber_DEF = language.enterDefaultEnterNumber_1;
            alertDefaultErrorChangeTo_DEF = language.
                                            alertDefaultErrorChangeTo_1;
            alertDefaultError_DEF = language.alertDefaultError_1;
            enterDefaultEnterMMDD_DEF = language.enterDefaultEnterMMDD_1;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    language.exitDefaultExitTheProgramYesOrNo_1;
            genDefaultExit_DEF = language.genDefaultExit_1;
            settingsDefaultExtension_DEF = language.settingsDefaultExtension_1;
            callExtensionDefaultExtensionWasAdded_DEF =
                    language.callExtensionDefaultExtensionWasAdded_1;
            callForwardDefaultExternCalls_DEF =
                    language.callForwardDefaultExternCalls_1;
            absentDefaultGoneHome_DEF = language.absentDefaultGoneHome_1;
            groupsDefaultGroups_DEF = language.groupsDefaultGroups_1;
            settingsDefaultHelp_DEF = language.settingsDefaultHelp_1;
            absentDefaultInAMeeting_DEF = language.absentDefaultInAMeeting_1;
            alertDefaultInfo_DEF = language.alertDefaultInfo_1;
            alertDefaultInstedOf_DEF = language.alertDefaultInstedOf_1;
            callForwardDefaultInternCalls_DEF =
                    language.callForwardDefaultInternCalls_1;
            settingsDefaultLanguage_DEF = language.settingsDefaultLanguage_1;
            settingsDefaultLineAccess_DEF = language.
                                            settingsDefaultLineAccess_1;
            groupsDefaultLoginAllGroups_DEF = language.
                                              groupsDefaultLoginAllGroups_1;
            groupsDefaultLoginSpecificGroup_DEF =
                    language.groupsDefaultLoginSpecificGroup_1;
            groupsDefaultLogoutAllGroups_DEF = language.
                                               groupsDefaultLogoutAllGroups_1;
            groupsDefaultLogoutSpecificGroup_DEF =
                    language.groupsDefaultLogoutSpecificGroup_1;
            alertDefaultMaxSize_DEF = language.alertDefaultMaxSize_1;
            genDefaultMinimise_DEF = language.genDefaultMinimise_1;
            callExtensionDefaultName_DEF = language.callExtensionDefaultName_1;
            exitDefaultNo_DEF = language.exitDefaultNo_1;
            callForwardDefaultNoAnswer_DEF = language.
                                             callForwardDefaultNoAnswer_1;
            settingsDefaultOptions_DEF = language.settingsDefaultOptions_1;
            absentDefaultOutUntil_DEF = language.absentDefaultOutUntil_1;
            absentDefaultPersonalAtt_DEF = language.absentDefaultPersonalAtt_1;
            settingsDefaultPINcode_DEF = language.settingsDefaultPINcode_1;
            settingsDefaultPreEditCode_DEF = language.
                                             settingsDefaultPreEditCode_1;
            callForwardDefaultRemove_DEF = language.callForwardDefaultRemove_1;
            absentDefaultRemovePresence_DEF = language.
                                              absentDefaultRemovePresence_1;
            exitDefaultRestartProgram_DEF = language.
                                            exitDefaultRestartProgram_1;
            alertDefaultSaveChanges_DEF = language.alertDefaultSaveChanges_1;
            genDefaultSave_DEF = language.genDefaultSave_1;
            settingsDefaultSelectCountryCode_DEF =
                    language.settingsDefaultSelectCountryCode_1;
            genDefaultSelect_DEF = language.genDefaultSelect_1;
            genDefaultSend_DEF = language.genDefaultSend_1;
            absentDefaultSetPresence_DEF = language.absentDefaultSetPresence_1;
            settingsDefaultSettings_DEF = language.settingsDefaultSettings_1;
            callExtensionDefaultSurname_DEF = language.
                                              callExtensionDefaultSurname_1;
            settingsDefaultSwitchboardNumber_DEF =
                    language.settingsDefaultSwitchboardNumber_1;
            absentDefaultSystemAttOne_DEF = language.
                                            absentDefaultSystemAttOne_1;
            absentDeafaultSystemAttTwo_DEF = language.
                                             absentDeafaultSystemAttTwo_1;
            absentDeafaultWillReturnSoon_DEF = language.
                                               absentDeafaultWillReturnSoon_1;
            alertDefaultWrongInputTryAgain_DEF =
                    language.alertDefaultWrongInputTryAgain_1;
            voiceMailDefaultVoiceMail_DEF = language.
                                            voiceMailDefaultVoiceMail_1;
            genDefaultYes_DEF = language.genDefaultYes_1;

            accessPBXDefault_DEF = language.accessPBXDefault_1;
            autoAccessDefault_DEF = language.autoAccessDefault_1;
            accessViaPINCodeDefault_DEF = language.accessViaPINCodeDefault_1;
            dialDefault_DEF = language.dialDefault_1;

            alertExitMEXOnMessage_DEF = language.alertExitMEXOnMessage_1;
            alertMessageMEXOn_DEF = language.alertMessageMEXOn_1;
            alertMessageMEXOff_DEF = language.alertMessageMEXOff_1;
            alertMessageMexServerInfo_DEF = language.
                                            alertMessageMexServerInfo_1;
            alertMessageMexAlreadyON_DEF = language.alertMessageMexAlreadyON_1;
            alertMessageMexAlreadyOFF_DEF = language.
                                            alertMessageMexAlreadyOFF_1;
            mainListAttributMexOn_DEF = language.mainListAttributMexOn_1;
            mainListAttributMexOff_DEF = language.mainListAttributMexOff_1;
            operatorVoicemail_DEF = language.operatorVoicemail_1;

            AlertMessageExitText_DEF = language.AlertMessageExitText_1;

            absentTimeOfReturn_DEF = language.absentTimeOfReturn_1;
            absentDateOfReturn_DEF = language.absentDateOfReturn_1;
            absentLunch_DEF = language.absentLunch_1;
            absentMeeting_DEF = language.absentMeeting_1;
            absentVacation_DEF = language.absentVacation_1;
            absentIllness_DEF = language.absentIllness_1;

            callForwardTransfer_DEF = language.callForwardTransfer_1;
            callForwardPermForward_DEF = language.callForwardPermForward_1;
            callForwardInterForward_DEF = language.callForwardInterForward_1;
            callForwardExternForward_DEF = language.callForwardExternForward_1;
            callForwardCancelExtern_DEF = language.callForwardCancelExtern_1;
            callForwardCancelPermIntern_DEF = language.
                                              callForwardCancelPermIntern_1;

            textYourNumber_DEF = language.textYourNumber_1;
            textNewNumber_DEF = language.textNewNumber_1;

            voiceMailActivate_DEF = language.voiceMailActivate_1;
            voiceMailDeActivate_DEF = language.voiceMailDeActivate_1;
            voiceMailListen_DEF = language.voiceMailListen_1;

            version_DEF = language.version_1;
            system_DEF = language.system_1;

            transferBack_DEF = language.transferBack_1;

            programExitON_DEF = language.programExitON_1;
            programExitOFF_DEF = language.programExitOFF_1;

        }

        // --- Övriga språk beroende på vilket nummer som är satt i DB.

        /* Danish, Dutch, Finnish, French, German,
           Norwegian, Italian, Spanish, Swedish */
        if (this.lang_PBX.equals("0") || this.lang_PBX.equals("1")
            || this.lang_PBX.equals("3") || this.lang_PBX.equals("4")
            || this.lang_PBX.equals("5") || this.lang_PBX.equals("6")
            || this.lang_PBX.equals("7") || this.lang_PBX.equals("8")
            || this.lang_PBX.equals("9")) {

            settingsDefaultAbout_DEF = language.settingsDefaultAbout_2;
            SettingsDefaultAccessPINcode_DEF = language.
                                               SettingsDefaultAccessPINcode_2;
            extensionDefaultAddNew_DEF = language.extensionDefaultAddNew_2;
            callForwardDefaultAllCalls_DEF = language.
                                             callForwardDefaultAllCalls_2;
            absentDefaultAtExt_DEF = language.absentDefaultAtExt_2;
            settingsDefaultAutoAccess_DEF = language.
                                            settingsDefaultAutoAccess_2;
            genDefaultBack_DEF = language.genDefaultBack_2;
            absentDefaultBackAt_DEF = language.absentDefaultBackAt_2;
            callForwardDefaultBusy_DEF = language.callForwardDefaultBusy_2;
            callForwardDefaultBusyNoAnswer_DEF =
                    language.callForwardDefaultBusyNoAnswer_2;
            callDefaultCall_DEF = language.callDefaultCall_2;
            extensionDefaultCall_DEF = language.extensionDefaultCall_2;
            callForwardDefault_DEF = language.callForwardDefault_2;
            msgDefaultCallistIsEmpty_DEF = language.msgDefaultCallistIsEmpty_2;
            genDefaultCancel_DEF = language.genDefaultCancel_2;
            alertDefaultCantAddAnymoreExt_DEF =
                    language.alertDefaultCantAddAnymoreExt_2;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    language.alertDefaultCouldntAddChangesEmtpyField_2;
            alertDefaultCouldntAddRecord_DEF = language.
                                               alertDefaultCouldntAddRecord_2;
            alsertDefaultChangesSave_DEF = language.alertDefaultChangesSave_2;
            mgsDefaultContactListIsEmpty_DEF = language.
                                               mgsDefaultContactListIsEmpty_2;
            alertDefaultCountryCodeError_DEF = language.
                                               alertDefaultCountryCodeError_2;
            settingsDefaultCountryCode_DEF = language.
                                             settingsDefaultCountryCode_2;
            genDefaultDelete_DEF = language.genDefaultDelete_2;
            genDefaultDeleteAll_DEF = language.genDefaultDeleteAll_2;
            dialledCallsDefault_DEF = language.dialledCallsDefault_2;
            callForwardDefaultDontDisturb_DEF =
                    language.callForwardDefaultDontDisturb_2;
            genDefaultEdit_DEF = language.genDefaultEdit_2;
            settingsDefaultEditPBXAccess_DEF = language.
                                               settingsDefaultEditPBXAccess_2;
            absentDefaultEditPresence_DEF = language.
                                            absentDefaultEditPresence_2;
            voiceMailDefaultEditVoicemail_DEF =
                    language.voiceMailDefaultEditVoicemail_2;
            enterDefaultEngerCharacter_DEF = language.
                                             enterDefaultEngerCharacter_2;
            enterDefaultEnterExtension_DEF = language.
                                             enterDefaultEnterExtension_2;
            enterDefaultEnterGroupNumber_DEF = language.
                                               enterDefaultEnterGroupNumber_2;
            enterDefaultEnterHHMM_DEF = language.enterDefaultEnterHHMM_2;
            enterDefaultEnterNumber_DEF = language.enterDefaultEnterNumber_2;
            alertDefaultErrorChangeTo_DEF = language.
                                            alertDefaultErrorChangeTo_2;
            alertDefaultError_DEF = language.alertDefaultError_2;
            enterDefaultEnterMMDD_DEF = language.enterDefaultEnterMMDD_2;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    language.exitDefaultExitTheProgramYesOrNo_2;
            genDefaultExit_DEF = language.genDefaultExit_2;
            settingsDefaultExtension_DEF = language.settingsDefaultExtension_2;
            callExtensionDefaultExtensionWasAdded_DEF =
                    language.callExtensionDefaultExtensionWasAdded_2;
            callForwardDefaultExternCalls_DEF =
                    language.callForwardDefaultExternCalls_2;
            absentDefaultGoneHome_DEF = language.absentDefaultGoneHome_2;
            groupsDefaultGroups_DEF = language.groupsDefaultGroups_2;
            settingsDefaultHelp_DEF = language.settingsDefaultHelp_2;
            absentDefaultInAMeeting_DEF = language.absentDefaultInAMeeting_2;
            alertDefaultInfo_DEF = language.alertDefaultInfo_2;
            alertDefaultInstedOf_DEF = language.alertDefaultInstedOf_2;
            callForwardDefaultInternCalls_DEF =
                    language.callForwardDefaultInternCalls_2;
            settingsDefaultLanguage_DEF = language.settingsDefaultLanguage_2;
            settingsDefaultLineAccess_DEF = language.
                                            settingsDefaultLineAccess_2;
            groupsDefaultLoginAllGroups_DEF = language.
                                              groupsDefaultLoginAllGroups_2;
            groupsDefaultLoginSpecificGroup_DEF =
                    language.groupsDefaultLoginSpecificGroup_2;
            groupsDefaultLogoutAllGroups_DEF = language.
                                               groupsDefaultLogoutAllGroups_2;
            groupsDefaultLogoutSpecificGroup_DEF =
                    language.groupsDefaultLogoutSpecificGroup_2;
            alertDefaultMaxSize_DEF = language.alertDefaultMaxSize_2;
            genDefaultMinimise_DEF = language.genDefaultMinimise_2;
            callExtensionDefaultName_DEF = language.callExtensionDefaultName_2;
            exitDefaultNo_DEF = language.exitDefaultNo_2;
            callForwardDefaultNoAnswer_DEF = language.
                                             callForwardDefaultNoAnswer_2;
            settingsDefaultOptions_DEF = language.settingsDefaultOptions_2;
            absentDefaultOutUntil_DEF = language.absentDefaultOutUntil_2;
            absentDefaultPersonalAtt_DEF = language.absentDefaultPersonalAtt_2;
            settingsDefaultPINcode_DEF = language.settingsDefaultPINcode_2;
            settingsDefaultPreEditCode_DEF = language.
                                             settingsDefaultPreEditCode_2;
            callForwardDefaultRemove_DEF = language.callForwardDefaultRemove_2;
            absentDefaultRemovePresence_DEF = language.
                                              absentDefaultRemovePresence_2;
            exitDefaultRestartProgram_DEF = language.
                                            exitDefaultRestartProgram_2;
            alertDefaultSaveChanges_DEF = language.alertDefaultSaveChanges_2;
            genDefaultSave_DEF = language.genDefaultSave_2;
            settingsDefaultSelectCountryCode_DEF =
                    language.settingsDefaultSelectCountryCode_2;
            genDefaultSelect_DEF = language.genDefaultSelect_2;
            genDefaultSend_DEF = language.genDefaultSend_2;
            absentDefaultSetPresence_DEF = language.absentDefaultSetPresence_2;
            settingsDefaultSettings_DEF = language.settingsDefaultSettings_2;
            callExtensionDefaultSurname_DEF = language.
                                              callExtensionDefaultSurname_2;
            settingsDefaultSwitchboardNumber_DEF =
                    language.settingsDefaultSwitchboardNumber_2;
            absentDefaultSystemAttOne_DEF = language.
                                            absentDefaultSystemAttOne_2;
            absentDeafaultSystemAttTwo_DEF = language.
                                             absentDeafaultSystemAttTwo_2;
            absentDeafaultWillReturnSoon_DEF = language.
                                               absentDeafaultWillReturnSoon_2;
            alertDefaultWrongInputTryAgain_DEF =
                    language.alertDefaultWrongInputTryAgain_2;
            voiceMailDefaultVoiceMail_DEF = language.
                                            voiceMailDefaultVoiceMail_2;
            genDefaultYes_DEF = language.genDefaultYes_2;

            accessPBXDefault_DEF = language.accessPBXDefault_2;
            autoAccessDefault_DEF = language.autoAccessDefault_2;
            accessViaPINCodeDefault_DEF = language.accessViaPINCodeDefault_2;
            dialDefault_DEF = language.dialDefault_2;

            alertExitMEXOnMessage_DEF = language.alertExitMEXOnMessage_2;
            alertMessageMEXOn_DEF = language.alertMessageMEXOn_2;
            alertMessageMEXOff_DEF = language.alertMessageMEXOff_2;
            alertMessageMexServerInfo_DEF = language.
                                            alertMessageMexServerInfo_2;
            alertMessageMexAlreadyON_DEF = language.alertMessageMexAlreadyON_2;
            alertMessageMexAlreadyOFF_DEF = language.
                                            alertMessageMexAlreadyOFF_2;
            mainListAttributMexOn_DEF = language.mainListAttributMexOn_2;
            mainListAttributMexOff_DEF = language.mainListAttributMexOff_2;
            operatorVoicemail_DEF = language.operatorVoicemail_2;

            AlertMessageExitText_DEF = language.AlertMessageExitText_2;

            absentTimeOfReturn_DEF = language.absentTimeOfReturn_2;
            absentDateOfReturn_DEF = language.absentDateOfReturn_2;
            absentLunch_DEF = language.absentLunch_2;
            absentMeeting_DEF = language.absentMeeting_2;
            absentVacation_DEF = language.absentVacation_2;
            absentIllness_DEF = language.absentIllness_2;

            callForwardTransfer_DEF = language.callForwardTransfer_2;
            callForwardPermForward_DEF = language.callForwardPermForward_2;
            callForwardInterForward_DEF = language.callForwardInterForward_2;
            callForwardExternForward_DEF = language.callForwardExternForward_2;
            callForwardCancelExtern_DEF = language.callForwardCancelExtern_2;
            callForwardCancelPermIntern_DEF = language.
                                              callForwardCancelPermIntern_2;

            textYourNumber_DEF = language.textYourNumber_2;
            textNewNumber_DEF = language.textNewNumber_2;

            voiceMailActivate_DEF = language.voiceMailActivate_2;
            voiceMailDeActivate_DEF = language.voiceMailDeActivate_2;
            voiceMailListen_DEF = language.voiceMailListen_2;

            version_DEF = language.version_2;
            system_DEF = language.system_2;

            transferBack_DEF = language.transferBack_2;

            programExitON_DEF = language.programExitON_2;
            programExitOFF_DEF = language.programExitOFF_2;

        }

        /* ================== DEBUG-VY ================================= */

        // Form
        logDataForm = new Form("DeBug - Login");
        logDataTextField = new TextField(settingsDefaultPINcode_DEF, "", 4,
                                         TextField.NUMERIC);

        logDataLogInCommand = new Command("Login", Command.OK, 1);
        logDataCancelLogInCommand = new Command(genDefaultCancel_DEF,
                                                Command.CANCEL, 2);

        logDataForm.addCommand(logDataLogInCommand);
        logDataForm.addCommand(logDataCancelLogInCommand);
        logDataForm.setCommandListener(this);

        // Debug list
        debug_List = new List("Debug - On/Off", Choice.IMPLICIT);

        try {
            Image image1debug = Image.createImage("/prg_icon/on24.png");
            Image image2debug = Image.createImage("/prg_icon/off24.png");
            Image image3debug = Image.createImage("/prg_icon/bortrest24.png");

            debug_List.append("Debug On", image1debug);
            debug_List.append("Debug Off", image2debug);
            debug_List.append("Send Log", image3debug);

        } catch (IOException ex4) {
        }
        //alertLogOutDebug
        debugListLogOutCommand = new Command("Logout", Command.BACK, 2);

        debug_List.addCommand(debugListLogOutCommand);
        debug_List.setCommandListener(this);

        // linePrefix_List linePrefixBackCommand

        linePrefix_List = new List(settingsDefaultLineAccess_DEF,
                                   Choice.IMPLICIT);

        linePrefix_List.append(genDefaultYes_DEF, null);
        linePrefix_List.append(exitDefaultNo_DEF, null);

        linePrefixBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);

        linePrefix_List.addCommand(linePrefixBackCommand);
        linePrefix_List.setCommandListener(this);

        /* ================== KOPPLA SAMTAL ============================ */

        connectPhoneForm = new Form(callForwardTransfer_DEF);
        connectTextField = new TextField(enterDefaultEnterNumber_DEF, "", 5,
                                         TextField.PHONENUMBER);

        connectSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        connectResumeCommand = new Command(transferBack_DEF, Command.OK, 2);
        connectBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 3);
        connectEditRenameCommand = new Command(genDefaultEdit_DEF, Command.OK, 4);

        connectPhoneForm.addCommand(connectSendCommand);
        connectPhoneForm.addCommand(connectResumeCommand);
        connectPhoneForm.addCommand(connectBackCommand);
        connectPhoneForm.addCommand(connectEditRenameCommand);
        connectPhoneForm.setCommandListener(this);
        connectPhoneForm.setItemStateListener(this);

        // --- Edit connectEditForm

        connectEditForm = new Form(callForwardTransfer_DEF);

        connectEditNameTextField = new TextField(callExtensionDefaultName_DEF,
                                                 "", 30, TextField.SENSITIVE);
        connectEditExtensionTextField = new TextField(
                settingsDefaultExtension_DEF, "", 5, TextField.PHONENUMBER);

        connectEditSaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);
        connectEditBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                             2);
        connectEditCancelCommand = new Command(genDefaultCancel_DEF,
                                               Command.CANCEL, 3);

        connectEditForm.addCommand(connectEditSaveCommand);
        connectEditForm.addCommand(connectEditBackCommand);
        connectEditForm.addCommand(connectEditCancelCommand);
        connectEditForm.setCommandListener(this);

        // --- Rename connectRenameForm

        connectRenameForm = new Form(callForwardTransfer_DEF);

        connectRenameBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                               2);

        connectRenameForm.addCommand(connectRenameBackCommand);
        connectRenameForm.setCommandListener(this);
        connectRenameForm.setItemStateListener(this);

        // --- Edit connectRenameEditForm

        connectRenameEditForm = new Form(callForwardTransfer_DEF);

        connectEditRenameNameTextField = new TextField(
                callExtensionDefaultName_DEF, "", 30, TextField.SENSITIVE);
        connectEditRenameExtensionTextField = new TextField(
                settingsDefaultExtension_DEF, "", 5, TextField.PHONENUMBER);

        connectEditRenameSaveCommand = new Command(genDefaultSave_DEF,
                Command.OK, 1);
        connectEditRenameBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);
        connectEditRenameCancelCommand = new Command(genDefaultCancel_DEF,
                Command.CANCEL, 3);

        connectRenameEditForm.addCommand(connectEditRenameSaveCommand);
        connectRenameEditForm.addCommand(connectEditRenameBackCommand);
        connectRenameEditForm.addCommand(connectEditRenameCancelCommand);
        connectRenameEditForm.setCommandListener(this);

        /* ================== HÄNVISNING ================================ */

        //--- AbsentList, lista för hänvisning i prg.

        //---- Lunch åter

        lunchForm = new Form(absentLunch_DEF);
        lunchTextField = new TextField(enterDefaultEnterHHMM_DEF, "", 4,
                                       TextField.NUMERIC);

        lunchSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        lunchBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        lunchForm.addCommand(lunchSendCommand);
        lunchForm.addCommand(lunchBackCommand);
        lunchForm.setCommandListener(this);

        //---- Tillfälligt ute

        outForm = new Form(absentTimeOfReturn_DEF);
        outTextField = new TextField(enterDefaultEnterHHMM_DEF, "", 4,
                                     TextField.NUMERIC);

        outSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        outBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        outForm.addCommand(outSendCommand);
        outForm.addCommand(outBackCommand);
        outForm.setCommandListener(this);

        //--- Sammanträde

        meetingForm = new Form(absentMeeting_DEF);
        meetingTextField = new TextField(enterDefaultEnterHHMM_DEF, "", 4,
                                         TextField.NUMERIC);

        meetingSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        meetingBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        meetingForm.addCommand(meetingSendCommand);
        meetingForm.addCommand(meetingBackCommand);
        meetingForm.setCommandListener(this);

        //--- Tjänsteresa

        travelForm = new Form(absentDateOfReturn_DEF);
        travelTextField = new TextField(enterDefaultEnterMMDD_DEF, "", 4,
                                        TextField.NUMERIC);

        travelSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        travelBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        travelForm.addCommand(travelSendCommand);
        travelForm.addCommand(travelBackCommand);
        travelForm.setCommandListener(this);

        //--- Sjuk

        sickForm = new Form(absentIllness_DEF);
        sickTextField = new TextField(enterDefaultEnterMMDD_DEF, "", 4,
                                      TextField.NUMERIC);

        sickSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        sickBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        sickForm.addCommand(sickSendCommand);
        sickForm.addCommand(sickBackCommand);
        sickForm.setCommandListener(this);

        //--- Semester

        vacationForm = new Form(absentVacation_DEF);
        vacationTextField = new TextField(enterDefaultEnterMMDD_DEF, "", 4,
                                          TextField.NUMERIC);

        vacationSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        vacationBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        vacationForm.addCommand(vacationSendCommand);
        vacationForm.addCommand(vacationBackCommand);
        vacationForm.setCommandListener(this);

        //--- Gått för dagen

        goneForDayForm = new Form(absentDefaultGoneHome_DEF);
        goneForDayTextField = new TextField(enterDefaultEnterMMDD_DEF, "", 4,
                                            TextField.NUMERIC);

        goneForDaySendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        goneForDayBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        goneForDayForm.addCommand(goneForDaySendCommand);
        goneForDayForm.addCommand(goneForDayBackCommand);
        goneForDayForm.setCommandListener(this);

        //--- Redigera (dynamiska attribut)

        newAbsent_1Form = new Form("");

        newAbsent_1SendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        newAbsent_1BackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                             2);
        newAbsent_1Form.addCommand(newAbsent_1SendCommand);
        newAbsent_1Form.addCommand(newAbsent_1BackCommand);
        newAbsent_1Form.setCommandListener(this);

        //--- Redigera (dynamiska attribut)

        newAbsent_2Form = new Form("");

        newAbsent_2SendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        newAbsent_2BackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                             2);
        newAbsent_2Form.addCommand(newAbsent_2SendCommand);
        newAbsent_2Form.addCommand(newAbsent_2BackCommand);
        newAbsent_2Form.setCommandListener(this);

        /* =================== GRUPPER ================================= */

        //--- GroupList, lista för att välja svarsgrupper i PBX'en.

        groupList = new List("", Choice.IMPLICIT); // skapar en lista
        groupList.setTitle(groupsDefaultGroups_DEF);

        groupBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 1);

        try {

            Image imageGroup = Image.createImage("/prg_icon/konf24.png");

            groupList.append(groupsDefaultLoginAllGroups_DEF, imageGroup);
            groupList.append(groupsDefaultLogoutAllGroups_DEF, imageGroup);
            groupList.append(groupsDefaultLoginSpecificGroup_DEF, imageGroup);
            groupList.append(groupsDefaultLogoutSpecificGroup_DEF, imageGroup);

            groupList.addCommand(groupBackCommand);
            groupList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //--- logoutGroup

        logoffGroupForm = new Form(groupsDefaultLogoutSpecificGroup_DEF);
        logoffGroupTextField = new TextField(enterDefaultEnterGroupNumber_DEF,
                                             "", 5, TextField.NUMERIC);


        logoffGroupSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        logoffGroupBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                             2);

        logoffGroupForm.addCommand(logoffGroupSendCommand);
        logoffGroupForm.addCommand(logoffGroupBackCommand);
        logoffGroupForm.setCommandListener(this);

        //--- loginGroup

        loginGroupForm = new Form(groupsDefaultLoginSpecificGroup_DEF);
        loginGroupTextField = new TextField(enterDefaultEnterGroupNumber_DEF,
                                            "", 5, TextField.NUMERIC);


        loginGroupSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        loginGroupBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);

        loginGroupForm.addCommand(loginGroupSendCommand);
        loginGroupForm.addCommand(loginGroupBackCommand);
        loginGroupForm.setCommandListener(this);

        /* =================== VIDAREKOPPLA ============================== */

        callForwardList = new List(callForwardDefault_DEF, Choice.IMPLICIT);

        try {
            Image image1b = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image2b = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image3b = Image.createImage(
                    "/prg_icon/taborthanvisning24.png");
            Image image4b = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image5b = Image.createImage(
                    "/prg_icon/taborthanvisning24.png");

            callForwardList.append(callForwardPermForward_DEF, image1b);
            callForwardList.append(callForwardInterForward_DEF, image2b);
            callForwardList.append(callForwardExternForward_DEF, image4b);
            callForwardList.append(callForwardCancelExtern_DEF, image5b);
            callForwardList.append(callForwardCancelPermIntern_DEF, image3b);

            callForwardListBackCommand = new Command(genDefaultBack_DEF,
                    Command.BACK, 1);

            callForwardList.addCommand(callForwardListBackCommand);
            callForwardList.setCommandListener(this);
        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        // --- Intern vidarekoppling

        callforwardpresentForm = new Form(callForwardInterForward_DEF);

        callforwardpresentTextField = new TextField(enterDefaultEnterNumber_DEF,
                "", 32,
                TextField.PHONENUMBER);

        CallForwardSendCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        CallForwardBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                             2);

        callforwardpresentForm.addCommand(CallForwardSendCommand);
        callforwardpresentForm.addCommand(CallForwardBackCommand);
        callforwardpresentForm.setCommandListener(this);

        // --- Extern vidarekoppling

        transferForwardCallForm = new Form(callForwardExternForward_DEF);

        transferForwardCallownNumberTextField = new TextField(
                textYourNumber_DEF + ": ",
                "", 20,
                TextField.PHONENUMBER);
        transferForwardCallnewNumberTextField = new TextField(textNewNumber_DEF +
                ": ",
                "", 20,
                TextField.PHONENUMBER);

        transferCallForwardSendCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        transferCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        transferForwardCallForm.addCommand(transferCallForwardBackCommand);
        transferForwardCallForm.addCommand(transferCallForwardSendCommand);
        transferForwardCallForm.setCommandListener(this);

        // --- Abryt extern vidarekoppling

        aborttransfercallForm = new Form(callForwardCancelExtern_DEF);

        aborttransfercallTextField = new TextField(textYourNumber_DEF + ": ",
                "", 32,
                TextField.PHONENUMBER);

        abortTransferCallForwardSendCommand = new Command(genDefaultSend_DEF,
                Command.OK,
                1);
        abortTransferCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK,
                2);

        aborttransfercallForm.addCommand(abortTransferCallForwardSendCommand);
        aborttransfercallForm.addCommand(abortTransferCallForwardBackCommand);
        aborttransfercallForm.setCommandListener(this);

        /* =================== RÖSTBREVLÅDOR ============================= */

        //---- Operatörs Röstbrevlåda, lägger in nummer till voicemail.

        voiceOperatorMessageForm = new Form(voiceMailDefaultVoiceMail_DEF);

        voiceOperatorMessageTextField = new TextField(
                enterDefaultEnterNumber_DEF + ":", "", 32,
                TextField.PHONENUMBER);

        voiceOperatorMessageSaveCommand = new Command(genDefaultSave_DEF,
                Command.OK, 1);
        voiceOperatorMessageCancelCommand = new Command(genDefaultCancel_DEF,
                Command.CANCEL, 2);
        voiceOperatorMessageBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 3);

        voiceOperatorMessageForm.addCommand(voiceOperatorMessageBackCommand);
        voiceOperatorMessageForm.addCommand(voiceOperatorMessageCancelCommand);
        voiceOperatorMessageForm.addCommand(voiceOperatorMessageSaveCommand);
        voiceOperatorMessageForm.setCommandListener(this);

        //--- Röstbrevlåda PBX, vxl'ens egna voicemail.

        voiceEditForm_PBX = new Form(voiceMailDefaultEditVoicemail_DEF);
        voiceMailPBXTextField_PBX = new TextField(enterDefaultEnterNumber_DEF,
                                                  "", 4, TextField.NUMERIC);

        voiceEditSaveCommand_PBX = new Command(genDefaultSave_DEF, Command.OK,
                                               1);
        voiceEditCancelCommand_PBX = new Command(genDefaultCancel_DEF,
                                                 Command.CANCEL, 2);
        voiceEditBackcommand_PBX = new Command(genDefaultBack_DEF, Command.BACK,
                                               3);

        voiceEditForm_PBX.addCommand(voiceEditSaveCommand_PBX);
        voiceEditForm_PBX.addCommand(voiceEditBackcommand_PBX);
        voiceEditForm_PBX.addCommand(voiceEditCancelCommand_PBX);
        voiceEditForm_PBX.setCommandListener(this);

        // --- Röstbrevlåda PBX, voiceMailPBXList

        voiceMailPBXList = new List(voiceMailDefaultVoiceMail_DEF,
                                    Choice.IMPLICIT);

        try {
            Image voiceImage = Image.createImage("/prg_icon/rostbrevlada24.png");

            voiceMailPBXList.append(voiceMailActivate_DEF, voiceImage);
            voiceMailPBXList.append(voiceMailDeActivate_DEF, voiceImage);
            voiceMailPBXList.append(voiceMailListen_DEF, voiceImage);

        } catch (IOException ex4) {
        }

        voiceMailPBXListBackCommand = new Command(genDefaultBack_DEF,
                                                  Command.BACK, 2);

        voiceMailPBXList.addCommand(voiceMailPBXListBackCommand);
        voiceMailPBXList.setCommandListener(this);

        /* ================== REDIGERA/EDIT ============================ */

        //--- pbx_List, editerar olika 'settings' i prg. som vxlnr osv...

        pbx_List = new List(settingsDefaultEditPBXAccess_DEF, Choice.IMPLICIT);

        pbx_List.append(accessPBXDefault_DEF, null);
        pbx_List.append(operatorVoicemail_DEF, null);
        pbx_List.append(voiceMailDefaultEditVoicemail_DEF, null);
        pbx_List.append(absentDefaultEditPresence_DEF, null);
        pbx_List.append(settingsDefaultLanguage_DEF, null);
        pbx_List.append(system_DEF, null);

        pbx_ListCancelCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);

        pbx_List.addCommand(pbx_ListCancelCommand);
        pbx_List.setCommandListener(this);

        //--- language_List, editerar att byta språk i programmet.

        language_List = new List(settingsDefaultLanguage_DEF, Choice.IMPLICIT);
        try {
            String iconPath = "/prg_icon/" + rms.getIconNumber() + ".png";

            System.out.println("ICONPATH >> " + iconPath);

            Image imageEng = Image.createImage("/prg_icon/2.png");
            Image imageIcon = Image.createImage(iconPath);

            language_List.append("English", imageEng);

            // --- om eng_lang är satt till '2' så visa aldrig annat språk.
            if(!this.eng_lang.equals("2")){

                language_List.append(this.countryName_PBX, imageIcon);

            }

            languageListBackCommand = new Command(genDefaultBack_DEF,
                                                  Command.BACK, 2);

            language_List.addCommand(languageListBackCommand);
            language_List.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //--- AutoAccessSettingsForm, editerar autologin på PBX'en.

        AutoAccessSettingsForm = new Form(autoAccessDefault_DEF);

        AutoAccessLineAccessTextField = new TextField(
                settingsDefaultLineAccess_DEF, "", 32,
                TextField.NUMERIC);

        AutoAccessSwitchBoardTextField = new TextField(
                settingsDefaultSwitchboardNumber_DEF, "",
                32,
                TextField.PHONENUMBER);

        AutoAccessBackCommand = new Command(genDefaultBack_DEF,
                                            Command.BACK, 2);
        AutoAccessCancelCommand = new Command(genDefaultCancel_DEF,
                                              Command.CANCEL,
                                              3);
        AutoAccessSaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);

        AutoAccessSettingsForm.addCommand(AutoAccessBackCommand);
        AutoAccessSettingsForm.addCommand(AutoAccessCancelCommand);
        AutoAccessSettingsForm.addCommand(AutoAccessSaveCommand);
        AutoAccessSettingsForm.setCommandListener(this);

        //--- AutoAccessSettingsNOPrefixForm, editerar autologin på PBX'en.

        AutoAccessSettingsNOPrefixForm = new Form(autoAccessDefault_DEF);

        AutoAccessNOPrefixSwitchBoardTextField = new TextField(
                settingsDefaultSwitchboardNumber_DEF, "",
                32,
                TextField.PHONENUMBER);

        AutoAccessBackNOPrefixCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);
        AutoAccessCancelNOPrefixCommand = new Command(genDefaultCancel_DEF,
                Command.CANCEL,
                3);
        AutoAccessSaveNOPrefixCommand = new Command(genDefaultSave_DEF,
                Command.OK, 1);

        AutoAccessSettingsNOPrefixForm.addCommand(AutoAccessBackNOPrefixCommand);
        AutoAccessSettingsNOPrefixForm.addCommand(
                AutoAccessCancelNOPrefixCommand);
        AutoAccessSettingsNOPrefixForm.addCommand(AutoAccessSaveNOPrefixCommand);
        AutoAccessSettingsNOPrefixForm.setCommandListener(this);

        // --- countryForm, editera och byt önskat landsnummer i prg.

        countryForm = new Form(settingsDefaultCountryCode_DEF);

        countryTextField = new TextField(settingsDefaultSelectCountryCode_DEF,
                                         "",
                                         4,
                                         TextField.NUMERIC);

        countryBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                         2);
        countryCancelCommand = new Command(genDefaultCancel_DEF, Command.CANCEL,
                                           3);
        countrySaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);

        countryForm.addCommand(countryBackCommand);
        countryForm.addCommand(countryCancelCommand);
        countryForm.addCommand(countrySaveCommand);
        countryForm.setCommandListener(this);

        // --- absentEditList, välj HHMM eller TTMM.
        // absentListChoose

        absentListChoose = new List(absentDefaultEditPresence_DEF,
                                    Choice.IMPLICIT);
        absentListChoose.append(enterDefaultEnterHHMM_DEF, null);
        absentListChoose.append(enterDefaultEnterMMDD_DEF, null);

        editTimeDateCancelCommand = new Command(genDefaultCancel_DEF,
                                                Command.CANCEL, 1);

        absentListChoose.addCommand(editTimeDateCancelCommand);
        absentListChoose.setCommandListener(this);

        // --- editAbsentForm, editera, DTFM-sträng, namn osv.

        editAbsentForm = new Form(absentDefaultEditPresence_DEF);

        editAbsentName_TextField = new TextField(callExtensionDefaultName_DEF,
                                                 "", 12,
                                                 TextField.INITIAL_CAPS_WORD);
        editAbsentNewDTMF_TextField = new TextField(
                enterDefaultEngerCharacter_DEF, "", 15,
                TextField.PHONENUMBER);

        editAbsentSaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);
        editAbsentBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        editAbsentCancelCommand = new Command(genDefaultCancel_DEF,
                                              Command.CANCEL, 3);

        editAbsentForm.addCommand(editAbsentBackCommand);
        editAbsentForm.addCommand(editAbsentCancelCommand);
        editAbsentForm.addCommand(editAbsentSaveCommand);
        editAbsentForm.setCommandListener(this);

        /* ================== ALERT's ================================== */

        // --- alertON, Visas då prg 'Mex on' är aktiv.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertON = new Alert(mainListAttributMexOn_DEF, "", alertInfo, AlertType.INFO);
            alertON.setString(mainListAttributMexOn_DEF + "?");
            alertON.setTimeout(Alert.FOREVER);

            confirmOnYESCommand = new Command(genDefaultYes_DEF,
                                                Command.OK, 1);
            confirmOnCancelCommand = new Command(genDefaultCancel_DEF,
                                               Command.CANCEL, 2);

            alertON.addCommand(confirmOnYESCommand);
            alertON.addCommand(confirmOnCancelCommand);
            alertON.setCommandListener(this);


        } catch (IOException ex11) {
        }

        //--- alertOFF, Visas då prg 'Mex off' är aktiv.

        try {

            Image alertMexOff = Image.createImage("/prg_icon/info.png");
            alertOFF = new Alert(mainListAttributMexOff_DEF, "", alertMexOff, AlertType.INFO);
            alertOFF.setString(mainListAttributMexOff_DEF + "?");
            alertOFF.setTimeout(Alert.FOREVER);

            confirmOffYESCommand = new Command(genDefaultYes_DEF,
                                                Command.OK, 1);
            confirmOffCancelCommand = new Command(genDefaultCancel_DEF,
                                               Command.CANCEL, 2);

            alertOFF.addCommand(confirmOffYESCommand);
            alertOFF.addCommand(confirmOffCancelCommand);
            alertOFF.setCommandListener(this);


        } catch (IOException ex11) {
        }


        //--- alertEditSettings, Visas då något i prg sparas om.

        Image alertEditSettingImage = Image.createImage(
                "/prg_icon/save.png");
        alertEditSettings = new Alert(alertDefaultSaveChanges_DEF,
                                      alsertDefaultChangesSave_DEF,
                                      alertEditSettingImage,
                                      AlertType.CONFIRMATION);

        alertEditSettings.setTimeout(2000);

        //--- alertDebugONOFF, Visas då prg 'Debug' är ON el. OFF

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertDebugONOFF = new Alert(alertMessageMexServerInfo_DEF, "",
                                        alertInfo,
                                        AlertType.INFO);
            alertDebugONOFF.setTimeout(1000);

        } catch (IOException ex11) {
        }

        //--- alertSendOKNOK, Visas då prg 'Sänder till webservern' är Ok el fel.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertSendOKNOK = new Alert(alertMessageMexServerInfo_DEF, "",
                                       alertInfo,
                                       AlertType.INFO);
            alertSendOKNOK.setTimeout(Alert.FOREVER);

        } catch (IOException ex11) {
        }

        //--- alertSENDDebug, Visas då Debug-logg skickas.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertSENDDebug = new Alert(alertMessageMexServerInfo_DEF,
                                       "Sending Log!\nWait for response.",
                                       alertInfo, AlertType.INFO);
            alertSENDDebug.setTimeout(1500);

        } catch (IOException ex11) {
        }

        //--- alertLogOutDebug, Visas då Debug-logg skickas.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertLogOutDebug = new Alert(alertMessageMexServerInfo_DEF,
                                         "Logout!", alertInfo, AlertType.INFO);
            alertLogOutDebug.setTimeout(1000);

        } catch (IOException ex11) {
        }

        //--- alertMexAlreadyONOFF, Visas då prg 'Mex on/off' redan är aktiv.

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertMexAlreadyONOFF = new Alert(alertMessageMexServerInfo_DEF, "",
                                             alertInfo,
                                             AlertType.INFO);
            alertMexAlreadyONOFF.setTimeout(Alert.FOREVER);

        } catch (IOException ex11) {
        }

        //--- alertRestarting, Visad då prg måste startas om, tex språkbyte.


        Image alertRestartImage = Image.createImage("/prg_icon/restart.png");
        alertRestarting = new Alert(alertDefaultSaveChanges_DEF,
                                    exitDefaultRestartProgram_DEF,
                                    alertRestartImage,
                                    AlertType.CONFIRMATION);

        alertRestarting.setTimeout(Alert.FOREVER);

        /* ================== FRISTÅENDE KOMMANDON ========================= */

        GraphicsAboutCommand = new Command(version_DEF, Command.HELP, 3);
        goGraphicsBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        GraphicsHelpCommand = new Command(settingsDefaultHelp_DEF, Command.HELP,
                                          3);

        /* ================== LICENS KONTROLL ============================= */

        // Om licensen är en demo-licens '1' kontrollera datumet.
        if (demo_PBX.equals("1")) {
            ControllDateTime();
        } else if (!demo_PBX.equals("1")) {

            this.ViewDateString = "Enterprise License";

        }
        dateTime = null;
        rms = null;

    } // Konstruktorn slutar här.


    /* ================== FORM settings ==================================== */

    // --- Edit Koppla samtal
    public Form getConnectEditForm() {

        connectEditForm.deleteAll();
        connectEditNameTextField.setString("");
        connectEditForm.append(connectEditNameTextField);
        connectEditExtensionTextField.setString("");
        connectEditForm.append(connectEditExtensionTextField);
        return connectEditForm;
    }

    // --- Editra 'listan' Koppla samtal
    public Form getConnectRenameEditForm() {

        connectRenameEditForm.deleteAll();
        connectRenameEditForm.append(connectEditRenameNameTextField);
        connectRenameEditForm.append(connectEditRenameExtensionTextField);
        return connectRenameEditForm;
    }

    // --- Koppla samtal
    public Form getConnectPhoneForm() {

        connectPhoneForm.deleteAll();
        connectPhoneForm.append(connectTextField);

        try {
            connectPhoneForm.append(getRadioButton());
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }

        return connectPhoneForm;
    }

    public Form getRenameForm() {

        connectRenameForm.deleteAll();

        try {
            connectRenameForm.append(getEditButton());
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }

        return connectRenameForm;
    }

    // --- Hänvisning forms.

    public Form getLunchForm() { // - Lunch åter

        lunchForm.deleteAll();
        lunchForm.append(lunchTextField);

        return lunchForm;
    }

    public Form getOutForm() { // - Tillfälligt ute

        outForm.deleteAll();
        outForm.append(outTextField);

        return outForm;
    }

    public Form getMeetingForm() { // - Sammanträde

        meetingForm.deleteAll();
        meetingForm.append(meetingTextField);

        return meetingForm;
    }

    public Form getTravelForm() { // - Tjänsteresa

        travelForm.deleteAll();
        travelForm.append(travelTextField);

        return travelForm;
    }

    public Form getSickForm() { // - Sjuk

        sickForm.deleteAll();
        sickForm.append(sickTextField);

        return sickForm;
    }

    public Form getVacationForm() { // - Semester

        vacationForm.deleteAll();
        vacationForm.append(vacationTextField);

        return vacationForm;
    }

    public Form getGoneForDayForm() { // - Gått för dagen

        goneForDayForm.deleteAll();
        goneForDayForm.append(goneForDayTextField);

        return goneForDayForm;
    }

    public Form getNewAbsent_1Form() { // - Lägg till ny hänvisning

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        newAbsent_1Form.deleteAll();

        try {

            newAbsent_1Form.setTitle(rms.getEditAbsentName_1());

            if (rms.getHHMMTTMM_1().equals("1")) {

                newAbsent_1TextField = new TextField(enterDefaultEnterHHMM_DEF,
                        "", 4, TextField.NUMERIC);

            } else if (rms.getHHMMTTMM_1().equals("2")) {

                newAbsent_1TextField = new TextField(enterDefaultEnterMMDD_DEF,
                        "", 4, TextField.NUMERIC);

            }
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        newAbsent_1Form.append(newAbsent_1TextField);

        rms = null;
        return newAbsent_1Form;
    }

    public Form getNewAbsent_2Form() { // - Lägg till ny hänvisning

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        newAbsent_2Form.deleteAll();

        try {

            newAbsent_2Form.setTitle(rms.getEditAbsentName_2());

            if (rms.getHHMMTTMM_2().equals("1")) {

                newAbsent_2TextField = new TextField(enterDefaultEnterHHMM_DEF,
                        "", 4, TextField.NUMERIC);

            } else if (rms.getHHMMTTMM_2().equals("2")) {

                newAbsent_2TextField = new TextField(enterDefaultEnterMMDD_DEF,
                        "", 4, TextField.NUMERIC);

            }
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        newAbsent_2Form.append(newAbsent_2TextField);

        rms = null;
        return newAbsent_2Form;

    }

    public Form getEditAbsentForm() {

        editAbsentForm.deleteAll();
        editAbsentForm.append(editAbsentName_TextField);
        editAbsentForm.append(editAbsentNewDTMF_TextField);

        return editAbsentForm;
    }

    // --- Sätter värden i AutoAccessSettingsNOPrefixForm.
    public Form getAutoAccessNOPrefixForm() {

        AutoAccessSettingsNOPrefixForm.deleteAll();

        AutoAccessNOPrefixSwitchBoardTextField.setString(switchBoardNumber_PBX);
        AutoAccessSettingsNOPrefixForm.append(
                AutoAccessNOPrefixSwitchBoardTextField);

        return AutoAccessSettingsNOPrefixForm;
    }


    // --- Sätter värden i AutoAccessSettingsForm.
    public Form getAutoAccessSettingForm() {

        AutoAccessSettingsForm.deleteAll();

        if (this.lineAccess_PBX.equals("NONE")) {

            this.lineAccess_PBX = "9";
        }

        AutoAccessLineAccessTextField.setString(lineAccess_PBX);
        AutoAccessSettingsForm.append(AutoAccessLineAccessTextField);

        AutoAccessSwitchBoardTextField.setString(switchBoardNumber_PBX);
        AutoAccessSettingsForm.append(AutoAccessSwitchBoardTextField);

        return AutoAccessSettingsForm;

    }

    // --- Sätter värden i countryForm.
    public Form getCountryForm() {

        countryForm.deleteAll();
        try {
            countryTextField.setString(this.countryCode_PBX);
        } catch (Exception ex) {
        }
        countryForm.append(countryTextField);

        return countryForm;
    }

    // --- Login Form DeBug
    public Form getLogInForm() {

        logDataForm.deleteAll();
        logDataForm.append(logDataTextField);

        return logDataForm;
    }

    // --- Sätter värden i voiceOperatorMessageForm.
    public Form getOperatorVoiceMessageForm() {

        voiceOperatorMessageForm.deleteAll();
        voiceOperatorMessageTextField.setString(voiceMailOperator_PBX);
        voiceOperatorMessageForm.append(voiceOperatorMessageTextField);

        return voiceOperatorMessageForm;
    }

    // --- Sätter värden i voiceEditForm_PBX.
    public Form getPBXVoiceEditForm() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        voiceEditForm_PBX.deleteAll();
        voiceMailPBXTextField_PBX.setString(voiceMailSwitchboard_PBX);
        voiceEditForm_PBX.append(voiceMailPBXTextField_PBX);

        return voiceEditForm_PBX;
    }

    // --- Sätter och visar logoffGroupForm.
    public Form getLogoffGroupForm() {

        logoffGroupForm.deleteAll();
        logoffGroupForm.append(logoffGroupTextField);

        return logoffGroupForm;
    }

    // --- Sätter och visar loginGroupForm.
    public Form getLoginGroupForm() {

        loginGroupForm.deleteAll();
        loginGroupForm.append(loginGroupTextField);

        return loginGroupForm;
    }

    // --- Sätter och visar callforwardpresentForm (Intern vidarekoppling)
    public Form getCallForwardPresentForm() {

        callforwardpresentForm.deleteAll();
        callforwardpresentForm.append(callforwardpresentTextField);

        return callforwardpresentForm;
    }

    // --- Sätter och visar transferForwardCallForm (Extern vidarekoppling)
    public Form getTransferCallForwardForm() {

        transferForwardCallForm.deleteAll();
        transferForwardCallForm.append(transferForwardCallownNumberTextField);
        transferForwardCallForm.append(transferForwardCallnewNumberTextField);

        return transferForwardCallForm;
    }

    // --- Sätter och visar aborttransfercallForm (Avbryt Extern vidarekoppling)
    public Form getAbortTransferCallForm() {

        aborttransfercallForm.deleteAll();
        aborttransfercallForm.append(aborttransfercallTextField);

        return aborttransfercallForm;
    }

    public Alert getAlertExpernceLisence() {

        // --- 31-dagars licens har gått ut.

        try {
            Image alertExitImage = Image.createImage("/prg_icon/exit2_64.png");
            alertExpernceLicense = new Alert("License expired",
                                             "Your license has expired\n" +
                                             "Please contact your PBX dealer"
                                             + "\n\nwww.mobisma.com",
                                             alertExitImage,
                                             AlertType.CONFIRMATION);

            alertExpernceLicense.setTimeout(Alert.FOREVER);
            licensYESCommand = new Command("Ok", Command.EXIT, 1);

            alertExpernceLicense.addCommand(licensYESCommand);
            alertExpernceLicense.setCommandListener(this);

        } catch (IOException ex5) {
        }

        return alertExpernceLicense;
    }

    public Alert getAlertExit() {

        // --- Exit alert. Visas då prg avslutas.
        try {
            Image alertExitImage = Image.createImage("/prg_icon/exit2_64.png");
            alertExit = new Alert(genDefaultExit_DEF,
                                  "",
                                  alertExitImage, AlertType.CONFIRMATION);

            alertExit.setTimeout(Alert.FOREVER);

            confirmExitYESCommand = new Command(genDefaultYes_DEF,
                                                Command.EXIT, 1);
            confirmExitNOCommand = new Command(exitDefaultNo_DEF,
                                               Command.OK, 2);

            alertExit.addCommand(confirmExitYESCommand);
            alertExit.addCommand(confirmExitNOCommand);
            alertExit.setCommandListener(this);

        } catch (IOException ex5) {
        }

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            if (rms.getMexONOFF().equals("1")) {
                alertExit.setString(programExitON_DEF);
            }
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            if (rms.getMexONOFF().equals("0")) {

                alertExit.setString(programExitOFF_DEF);

            }
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        rms = null;

        return alertExit;

    }




     /* ================== LIST settings ==================================== */

    public List getAbsentEditList() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        // --- absentEditList, välj att editera attribut 1 eller 2.

        absentEditList = new List(absentDefaultEditPresence_DEF,
                                  Choice.IMPLICIT);
        absentEditList.append("", null);
        absentEditList.append("", null);

        editAbsentListBackCommand = new Command(genDefaultBack_DEF,
                                                Command.BACK, 1);
        editAbsentListCancelCommand = new Command(genDefaultCancel_DEF,
                                                  Command.CANCEL, 2);

        absentEditList.addCommand(editAbsentListBackCommand);
        absentEditList.addCommand(editAbsentListCancelCommand);
        absentEditList.setCommandListener(this);

        String absent_1 = null;
        try {
            absent_1 = rms.getEditAbsentName_1();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        String absent_2 = null;
        try {
            absent_2 = rms.getEditAbsentName_2();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        absentEditList.set(0, "1: " + absent_1, null);
        absentEditList.set(1, "2: " + absent_2, null);

        rms = null;
        return absentEditList;

    }

    public List getAbsentList() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        try {
            this.absentStatus = rms.getAbsentStatus();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }

        //--- AbsentList, lista för hänvisning i prg.

        absentList = new List("", Choice.IMPLICIT); // skapar en lista

        try {
            if (rms.getAbsentStatus().equals("0")) {
                absentList.setTicker(null);
                absentStatusTicker = new Ticker(" ");
                absentList.setTicker(absentStatusTicker);

            } else if (!rms.getAbsentStatus().equals("0")) {
                absentList.setTicker(null);
                absentStatusTicker = new Ticker(absentStatus);
                absentList.setTicker(absentStatusTicker);

            }
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }

        BackCommandAbsentList = new Command(genDefaultBack_DEF, Command.BACK, 2);


        try {

            Image image1c = Image.createImage("/prg_icon/lunch24.png");
            Image image2c = Image.createImage("/prg_icon/ute24.png");
            Image image3c = Image.createImage("/prg_icon/konference24.png");
            Image image4c = Image.createImage("/prg_icon/bortrest24.png");
            Image image5c = Image.createImage("/prg_icon/sjuk24.png");
            Image image6c = Image.createImage("/prg_icon/semester24.png");
            Image image7c = Image.createImage("/prg_icon/upptagen24.png");
            Image image8c = Image.createImage(
                    "/prg_icon/taborthanvisning24.png");
            Image image9c = Image.createImage("/prg_icon/samtalslista24.png");

            absentList.append(absentLunch_DEF, image1c);
            absentList.append(absentTimeOfReturn_DEF, image2c);
            absentList.append(absentMeeting_DEF, image3c);
            absentList.append(absentDateOfReturn_DEF, image4c);
            absentList.append(absentIllness_DEF, image5c);
            absentList.append(absentVacation_DEF, image6c);
            absentList.append(absentDefaultGoneHome_DEF, image7c);
            absentList.append(absentDefaultRemovePresence_DEF, image8c);

            absentList.append("", null);
            absentList.append("", null);

            absentList.addCommand(BackCommandAbsentList);
            absentList.setCommandListener(this);

            String absent_1 = rms.getEditAbsentName_1();
            String absent_2 = rms.getEditAbsentName_2();

            if (absent_1.equals("0")) {

                absentList.set(8, editAbsentName_1, image9c); // mex on

            } else if (!absent_1.equals("0")) {

                absentList.set(8, absent_1, image9c); // mex on

            }
            if (absent_2.equals("0")) {

                absentList.set(9, editAbsentName_2, image9c); // mex off

            } else if (!absent_2.equals("0")) {

                absentList.set(9, absent_2, image9c); // mex off

            }

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        } catch (RecordStoreNotOpenException ex) {
            /** @todo Handle this exception */
        } catch (InvalidRecordIDException ex) {
            /** @todo Handle this exception */
        } catch (RecordStoreException ex) {
            /** @todo Handle this exception */
        }

        rms = null;
        return absentList;
    }


    public List getMainList() {

        /* ================== HUVUDMENY/LISTA ============================ */

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        // Huvudlistan, förstamenyn visas när prg startat.
        mainListEditCommand = new Command(genDefaultEdit_DEF, Command.OK, 1);
        mainListaboutMobismaCommand = new Command(settingsDefaultAbout_DEF,
                                                  Command.OK, 2);
        mainListExitCommand = new Command(genDefaultExit_DEF, Command.OK, 3);

        mainList = new List(prg_Name, Choice.IMPLICIT);

        try {
            Image image1c = Image.createImage("/prg_icon/systemphone24.png");
            Image image4a = Image.createImage("/prg_icon/hanvisa24.png");
            Image image5a = Image.createImage("/prg_icon/konference24.png");
            Image image6a = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image7a = Image.createImage("/prg_icon/rostbrevlada24.png");
            Image image8a = Image.createImage("/prg_icon/on24.png");
            Image image9a = Image.createImage("/prg_icon/off24.png");
            Image image10a = Image.createImage("/prg_icon/minimera24.png");

            mainList.append(callForwardTransfer_DEF, image1c);
            mainList.append(absentDefaultSetPresence_DEF, image4a);
            mainList.append(groupsDefaultGroups_DEF, image5a);
            mainList.append(callForwardDefault_DEF, image6a);
            mainList.append(voiceMailDefaultVoiceMail_DEF, image7a);

            mainList.append("", null);

            mainList.append(genDefaultMinimise_DEF, image10a);

            mainList.addCommand(mainListExitCommand);
            mainList.addCommand(mainListEditCommand);
            mainList.addCommand(mainListaboutMobismaCommand);
            mainList.setCommandListener(this);

            // kod ...
            String mex = rms.getMexONOFF();

            if (mex.equals("1")) {

                mainList.set(5, mainListAttributMexOn_DEF, image8a); // mex on
            } else if (mex.equals("0")) {

                mainList.set(5, mainListAttributMexOff_DEF, image9a); // mex off

            }

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        } catch (RecordStoreNotOpenException ex) {
            /** @todo Handle this exception */
        } catch (InvalidRecordIDException ex) {
            /** @todo Handle this exception */
        } catch (RecordStoreException ex) {
            /** @todo Handle this exception */
        }

        rms = null;
        return mainList;
    }

    // --- Sätter och visar listan
    public List getLanguageList() {

        return language_List;
    }

    // --- Sätter och visar listan
    public List getSettingsList() {

        return pbx_List;
    }


    /* ================== ITEM ChoiceGroup ==================================== */

    public ChoiceGroup getRadioButton() throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String s = this.extensionDefaultAddNew_DEF;
        MModel.InternNumber intern = new InternNumber();
        intern.setInternButtonName(s);

        radioButtons.deleteAll();

        radioButtons.append(rms.getInternName(51), null);
        radioButtons.append(rms.getInternName(52), null);
        radioButtons.append(rms.getInternName(53), null);
        radioButtons.append(rms.getInternName(54), null);
        radioButtons.append(rms.getInternName(55), null);
        radioButtons.append(rms.getInternName(56), null);
        radioButtons.append(rms.getInternName(57), null);
        radioButtons.append(rms.getInternName(58), null);
        radioButtons.append(rms.getInternName(59), null);
        radioButtons.append(rms.getInternName(60), null);
        radioButtons.append(rms.getInternName(61), null);
        radioButtons.append(rms.getInternName(62), null);
        radioButtons.append(rms.getInternName(63), null);
        radioButtons.append(rms.getInternName(64), null);
        radioButtons.append(rms.getInternName(65), null);
        radioButtons.append(rms.getInternName(66), null);
        radioButtons.append(rms.getInternName(67), null);
        radioButtons.append(rms.getInternName(68), null);
        radioButtons.append(rms.getInternName(69), null);
        radioButtons.append(rms.getInternName(70), null);
        radioButtons.append(rms.getInternName(71), null);
        radioButtons.append(rms.getInternName(72), null);
        radioButtons.append(rms.getInternName(73), null);
        radioButtons.append(rms.getInternName(74), null);
        defaultIndex = radioButtons.append(rms.getInternName(75), null);
        radioButtons.setSelectedIndex(defaultIndex, true);
        radioButtons.setLabel(enterDefaultEnterExtension_DEF);

        String s1 = rms.getInternName(51);
        String s2 = rms.getInternName(52);
        String s3 = rms.getInternName(53);
        String s4 = rms.getInternName(54);
        String s5 = rms.getInternName(55);

        String s6 = rms.getInternName(56);
        String s7 = rms.getInternName(57);
        String s8 = rms.getInternName(58);
        String s9 = rms.getInternName(59);
        String s10 = rms.getInternName(60);

        String s11 = rms.getInternName(61);
        String s12 = rms.getInternName(62);
        String s13 = rms.getInternName(63);
        String s14 = rms.getInternName(64);
        String s15 = rms.getInternName(65);

        String s16 = rms.getInternName(66);
        String s17 = rms.getInternName(67);
        String s18 = rms.getInternName(68);
        String s19 = rms.getInternName(69);
        String s20 = rms.getInternName(70);

        String s21 = rms.getInternName(71);
        String s22 = rms.getInternName(72);
        String s23 = rms.getInternName(73);
        String s24 = rms.getInternName(74);
        String s25 = rms.getInternName(75);


        radioButtons.set(0, s1, null);
        radioButtons.set(1, s2, null);
        radioButtons.set(2, s3, null);
        radioButtons.set(3, s4, null);
        radioButtons.set(4, s5, null);

        radioButtons.set(5, s6, null);
        radioButtons.set(6, s7, null);
        radioButtons.set(7, s8, null);
        radioButtons.set(8, s9, null);
        radioButtons.set(9, s10, null);

        radioButtons.set(10, s11, null);
        radioButtons.set(11, s12, null);
        radioButtons.set(12, s13, null);
        radioButtons.set(13, s14, null);
        radioButtons.set(14, s15, null);

        radioButtons.set(15, s16, null);
        radioButtons.set(16, s17, null);
        radioButtons.set(17, s18, null);
        radioButtons.set(18, s19, null);
        radioButtons.set(19, s20, null);

        radioButtons.set(20, s21, null);
        radioButtons.set(21, s22, null);
        radioButtons.set(22, s23, null);
        radioButtons.set(23, s24, null);
        radioButtons.set(24, s25, null);


        intern = null;
        rms = null;
        return radioButtons;
    }

    public ChoiceGroup getEditButton() throws IOException,
            RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();

        String s = this.extensionDefaultAddNew_DEF;

        editButtons.deleteAll();

        editButtons.append(rms.getInternName(51), null);
        editButtons.append(rms.getInternName(52), null);
        editButtons.append(rms.getInternName(53), null);
        editButtons.append(rms.getInternName(54), null);
        editButtons.append(rms.getInternName(55), null);
        editButtons.append(rms.getInternName(56), null);
        editButtons.append(rms.getInternName(57), null);
        editButtons.append(rms.getInternName(58), null);
        editButtons.append(rms.getInternName(59), null);
        editButtons.append(rms.getInternName(60), null);
        editButtons.append(rms.getInternName(61), null);
        editButtons.append(rms.getInternName(62), null);
        editButtons.append(rms.getInternName(63), null);
        editButtons.append(rms.getInternName(64), null);
        editButtons.append(rms.getInternName(65), null);
        editButtons.append(rms.getInternName(66), null);
        editButtons.append(rms.getInternName(67), null);
        editButtons.append(rms.getInternName(68), null);
        editButtons.append(rms.getInternName(69), null);
        editButtons.append(rms.getInternName(70), null);
        editButtons.append(rms.getInternName(71), null);
        editButtons.append(rms.getInternName(72), null);
        editButtons.append(rms.getInternName(73), null);
        editButtons.append(rms.getInternName(74), null);
        editButtonIndex = editButtons.append(rms.getInternName(75), null);
        editButtons.setSelectedIndex(editButtonIndex, true);
        editButtons.setLabel(genDefaultEdit_DEF);

        String s1 = rms.getInternName(51);
        String s2 = rms.getInternName(52);
        String s3 = rms.getInternName(53);
        String s4 = rms.getInternName(54);
        String s5 = rms.getInternName(55);

        String s6 = rms.getInternName(56);
        String s7 = rms.getInternName(57);
        String s8 = rms.getInternName(58);
        String s9 = rms.getInternName(59);
        String s10 = rms.getInternName(60);

        String s11 = rms.getInternName(61);
        String s12 = rms.getInternName(62);
        String s13 = rms.getInternName(63);
        String s14 = rms.getInternName(64);
        String s15 = rms.getInternName(65);

        String s16 = rms.getInternName(66);
        String s17 = rms.getInternName(67);
        String s18 = rms.getInternName(68);
        String s19 = rms.getInternName(69);
        String s20 = rms.getInternName(70);

        String s21 = rms.getInternName(71);
        String s22 = rms.getInternName(72);
        String s23 = rms.getInternName(73);
        String s24 = rms.getInternName(74);
        String s25 = rms.getInternName(75);


        editButtons.set(0, s1, null);
        editButtons.set(1, s2, null);
        editButtons.set(2, s3, null);
        editButtons.set(3, s4, null);
        editButtons.set(4, s5, null);

        editButtons.set(5, s6, null);
        editButtons.set(6, s7, null);
        editButtons.set(7, s8, null);
        editButtons.set(8, s9, null);
        editButtons.set(9, s10, null);

        editButtons.set(10, s11, null);
        editButtons.set(11, s12, null);
        editButtons.set(12, s13, null);
        editButtons.set(13, s14, null);
        editButtons.set(14, s15, null);

        editButtons.set(15, s16, null);
        editButtons.set(16, s17, null);
        editButtons.set(17, s18, null);
        editButtons.set(18, s19, null);
        editButtons.set(19, s20, null);

        editButtons.set(20, s21, null);
        editButtons.set(21, s22, null);
        editButtons.set(22, s23, null);
        editButtons.set(23, s24, null);
        editButtons.set(24, s25, null);


        rms = null;
        return editButtons;
    }


    public void itemStateChanged(Item item) {

        MModel.InternNumber intern = new InternNumber();

        if (item == radioButtons) {

            // ID int-värde av valt nummer 0 - 19 av id-nummren
            int ID = radioButtons.getSelectedIndex();

            // Sätter rätt id för hämta rätt anknytning, tex plats 0 = 51 i DB.
            String person = null;
            try {
                person = intern.getInternPerson(ID);
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            System.out.println("Returvärde person >> " + person);

            // Om ID platsen == '0' i DB öppna edit-formen | editera
            if (person.equals("0") || person.equals(null) ||
                person.equals(extensionDefaultAddNew_DEF)) {

                Display.getDisplay(this).setCurrent(getConnectEditForm());
                ID = intern.getInternID(ID);
                this.IDInternNumber = ID;

            }
            // Om ID platsen != '0' så koppla samtal
            else if (!person.equals("0")) { // Om ID platsen != '0' i DB >> Koppla samtalet.

                /* Plockar ut index och person sorterar bort allt utom >> siffror
                 Använder sedan siffrorna getPersonIDs för att koppla samtal */
                String getPersonIDs = radioButtons.getString(radioButtons.
                        getSelectedIndex());
                String internNumber = new String(getPersonIDs);
                MModel.SortClass sort = new SortClass();
                internNumber = sort.sortCharToDigits(internNumber);
                sort = null;

                Methods methods = null;
                try {
                    methods = new Methods();
                } catch (InvalidRecordIDException ex) {
                } catch (RecordStoreNotOpenException ex) {
                } catch (RecordStoreException ex) {
                } catch (IOException ex) {
                }
                methods.getConnectPhoneCallSEND = internNumber.trim();
                methods.ConnectPhoneCall();
                methods = null;

                System.out.println("INDEX ID >> " + ID);
                System.out.println("PersonID  >> " + getPersonIDs);
                System.out.println("Internnummer (kopplar till)  >> " +
                                   internNumber);

            }

        } else if (item == editButtons) {

            // ID int-värde av valt nummer 0 - 19 av id-nummren
            int ID = editButtons.getSelectedIndex();

            // Sätter rätt id för hämta rätt anknytning, tex plats 0 = 51 i DB.
            String person = null;
            try {
                person = intern.getInternPerson(ID);
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            System.out.println("Returvärde person >> " + person);
            MModel.SortClass sort = new SortClass();
            String name = sort.sortDigitsToCharacter(person);
            String number = sort.sortCharToDigits(person);
            sort = null;

            connectEditRenameNameTextField.setString(name.trim());
            connectEditRenameExtensionTextField.setString(number.trim());

            // Om ID platsen == '0' i DB öppna edit-formen | editera
            Display.getDisplay(this).setCurrent(getConnectRenameEditForm());
            ID = intern.getInternID(ID);
            this.IDInternNumber = ID;

        }

        intern = null;
    }


    /* ===== Huvudklassens tre metoder, main osv. ========================== */

    public void startApp() {

        startSplash();
    }


    public void pauseApp() {

    }

    public void destroyApp(boolean unconditional) {

    }

    // --- Sätter java.lang.string
    public String toString(String b) {

        String s = b;

        return s;    }



    /* ===== List-Kommandon =========================================== */

    public void commandAction(Command c, Displayable d) {
        Thread th = new Thread(this);
        thCmd = c;
        th.start();
        if (d.equals(mainList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(mainList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Koppla samtal

                        Display.getDisplay(this).setCurrent(getConnectPhoneForm());

                        break;

                    case 1: // Hänvisa

                        Display.getDisplay(this).setCurrent(getAbsentList());

                        break;

                    case 2: // Grupper

                        Display.getDisplay(this).setCurrent(groupList);

                        break;

                    case 3: // Vidarekoppla

                        Display.getDisplay(this).setCurrent(callForwardList);

                        break;

                    case 4: // Röstbrevlåda

                        Display.getDisplay(this).setCurrent(voiceMailPBXList);

                        break;

                    case 5: // Mex på

                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex1) {
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }
                        try {
                            mexOnOff = rms.getMexONOFF();
                        } catch (RecordStoreNotOpenException ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        rms = null;

                        if (mexOnOff.equals("0")) {

                            Display.getDisplay(this).setCurrent(alertON);

                        }

                        if (mexOnOff.equals("1")) {

                            Display.getDisplay(this).setCurrent(alertOFF);

                        }

                        break;

                    case 6: // Minimera

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.Minimize();
                        methods = null;

                        break;

                    }
                }

            }

        }
        if (d.equals(linePrefix_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(linePrefix_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // AutoAccess med linjeprefix

                        Display.getDisplay(this).setCurrent(
                                getAutoAccessSettingForm());

                        break;

                    case 1: // AutoAccess utan linjeprefix

                        Display.getDisplay(this).setCurrent(
                                getAutoAccessNOPrefixForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(absentList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(absentList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Lunch åter

                        Display.getDisplay(this).setCurrent(getLunchForm());

                        break;

                    case 1: // Tillfälligt ute

                        Display.getDisplay(this).setCurrent(getOutForm());

                        break;

                    case 2: // Sammanträde

                        Display.getDisplay(this).setCurrent(getMeetingForm());

                        break;

                    case 3: // Tjänsteresa

                        Display.getDisplay(this).setCurrent(getTravelForm());

                        break;

                    case 4: // Sjuk

                        Display.getDisplay(this).setCurrent(getSickForm());

                        break;

                    case 5: // Semester

                        Display.getDisplay(this).setCurrent(getVacationForm());

                        break;

                    case 6: // Gått för dagen

                        Display.getDisplay(this).setCurrent(getGoneForDayForm());

                        break;

                    case 7: // Ta bort hänvisning
                        Methods methods = null;
                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        DataBase_RMS rms = null;
                        try {
                            rms = new DataBase_RMS();
                        } catch (IOException ex3) {
                        } catch (RecordStoreNotOpenException ex3) {
                        } catch (InvalidRecordIDException ex3) {
                        } catch (RecordStoreException ex3) {
                        }
                        rms.setAbsentStatus("0");
                        Display.getDisplay(this).setCurrent(getAbsentList());

                        methods.removeAbsent();
                        methods = null;
                        rms = null;
                        break;

                    case 8: // Lägg till ny hänvisning

                        int p = absentList.getSelectedIndex();
                        System.out.println("getselected index >> " + p);

                        if (p == 8 && this.editAbsentDTMF_1.equals("0")) {

                            Display.getDisplay(this).setCurrent(
                                    absentListChoose);
                            this.editNEWAbsent = "1";
                        }

                        else {

                            Display.getDisplay(this).setCurrent(
                                    getNewAbsent_1Form());
                        }

                        break;

                    case 9: // Lägg till ny hänvisning

                        int pp = absentList.getSelectedIndex();
                        System.out.println("getselected index >> " + pp);

                        if (pp == 9 && this.editAbsentDTMF_2.equals("0")) {

                            Display.getDisplay(this).setCurrent(
                                    absentListChoose);
                            this.editNEWAbsent = "2";
                        }

                        else {

                            Display.getDisplay(this).setCurrent(
                                    getNewAbsent_2Form());
                        }

                        break;

                    }
                }

            }

        }

        if (d.equals(absentEditList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(absentEditList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0:

                        Display.getDisplay(this).setCurrent(absentListChoose);
                        this.editNEWAbsent = "1";

                        break;

                    case 1:

                        Display.getDisplay(this).setCurrent(absentListChoose);
                        this.editNEWAbsent = "2";
                        break;

                    }
                }

            }

        }
        if (d.equals(callForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(callForwardList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0:

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.callForwardToNumber();
                        methods = null;

                        break;

                    case 1:

                        Display.getDisplay(this).setCurrent(
                                getCallForwardPresentForm());

                        break;

                    case 2:

                        Display.getDisplay(this).setCurrent(
                                getTransferCallForwardForm());

                        break;

                    case 3:

                        Display.getDisplay(this).setCurrent(
                                getAbortTransferCallForm());

                        break;

                    case 4:

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.callForwardRemoveInternExtern();
                        methods = null;

                        break;

                    }
                }

            }

        }

        if (d.equals(voiceMailPBXList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(voiceMailPBXList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Beställ

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.voiceMailSet();
                        methods = null;

                        break;

                    case 1: // Avbeställ

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.voiceMailRemove();
                        methods = null;

                        break; // Lyssna

                    case 2:

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.voiceMailListen();
                        methods = null;

                        break;

                    }
                }

            }

        }

        if (d.equals(absentListChoose)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(absentListChoose)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0:

                        Display.getDisplay(this).setCurrent(getEditAbsentForm());
                        this.editHHTTMMTT = "1";

                        break;

                    case 1:

                        Display.getDisplay(this).setCurrent(getEditAbsentForm());
                        this.editHHTTMMTT = "2";
                        break;

                    }
                }

            }

        }

        if (d.equals(debug_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(debug_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Debug On

                        Thread d_on = new Thread() {

                            public void run() {

                                try {
                                    String don = "d," + "1";

                                    sendMexOnOffMessage(don);

                                    System.out.println("Degub ON >> " + don);

                                } catch (Exception ex) {
                                }

                            }
                        };
                        d_on.start();

                        break;

                    case 1: // Debug Off

                        Thread d_off = new Thread() {

                            public void run() {

                                try {
                                    String doff = "d," + "0";

                                    sendMexOnOffMessage(doff);

                                    System.out.println("Degub OFF >> " + doff);

                                } catch (Exception ex) {
                                }

                            }
                        };
                        d_off.start();

                        break;

                    case 2: // Send Log

                        Thread send_log = new Thread() {

                            public void run() {

                                try {

                                    sendLogdata();

                                    System.out.println("Send log >> ");

                                } catch (Exception ex) {
                                }

                            }
                        };
                        send_log.start();

                        Display.getDisplay(this).setCurrent(alertSENDDebug);

                        break;

                    }
                }
            }

        }

        if (d.equals(pbx_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(pbx_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Access PBX

                        Display.getDisplay(this).setCurrent(linePrefix_List);

                        break;

                    case 1: // Operatör röstbrevlådan

                        Display.getDisplay(this).setCurrent(
                                getOperatorVoiceMessageForm());

                        break;
                    case 2: // Redigera röstbrevlådan

                        try {
                            Display.getDisplay(this).setCurrent(
                                    getPBXVoiceEditForm());
                        } catch (InvalidRecordIDException ex13) {
                        } catch (RecordStoreNotOpenException ex13) {
                        } catch (RecordStoreException ex13) {
                        }

                        break
                                ;

                    case 3: // Redigera attribut

                        Display.getDisplay(this).setCurrent(getAbsentEditList());

                        break;
                    case 4: // Språk

                        Display.getDisplay(this).setCurrent(getLanguageList());

                        break;

                    case 5: // System

                        Display.getDisplay(this).setCurrent(getLogInForm());

                        break;

                    }
                }

            }

        }
        if (d.equals(groupList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(groupList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // inlogg alla grupper

                        System.out.println("Inlogg alla grupper");

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.logInAllGroups();
                        methods = null;

                        break;

                    case 1: // urlogg alla grupper

                        System.out.println("Urlogg alla grupper");

                        try {
                            methods = new Methods();
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (RecordStoreException ex) {
                        } catch (IOException ex) {
                        }
                        methods.logOffAllGroups();
                        methods = null;

                        break;

                    case 2: // inlogg grupp

                        System.out.println("Inlogg grupp");
                        Display.getDisplay(this).setCurrent(getLoginGroupForm());

                        break;

                    case 3: // urlogg grupp

                        System.out.println("Urlogg grupp");
                        Display.getDisplay(this).setCurrent(getLogoffGroupForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(language_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(language_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // English >> Default
                        this.lang_PBX = "2";
                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 1: // Språk_2
                        this.lang_PBX = iconNumber_PBX;
                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    }
                }

            }

        }

    }

    /* ===== Knapp-Kommandon =========================================== */

    public void run() {
        try {
            if (thCmd.getCommandType() == Command.EXIT) {
                notifyDestroyed();

            }

            /* ------- Send - Commands --------------------- */

            else if(thCmd == licensYESCommand){

                notifyDestroyed();

            }


            else if (thCmd == lunchSendCommand
                     || thCmd == outSendCommand
                     || thCmd == meetingSendCommand
                     || thCmd == travelSendCommand
                     || thCmd == sickSendCommand
                     || thCmd == vacationSendCommand
                     || thCmd == goneForDaySendCommand
                     || thCmd == newAbsent_1SendCommand
                     || thCmd == newAbsent_2SendCommand) {

                String absentNUM = "";
                String getFromTextField = "";
                String absentBP = "*23*";
                String absentSEND = "";

                if (thCmd == lunchSendCommand) {
                    absentNUM = "1";
                    getFromTextField = lunchTextField.getString();
                    lunchTextField.setString("");
                } else if (thCmd == outSendCommand) {
                    absentNUM = "2";
                    getFromTextField = outTextField.getString();
                    outTextField.setString("");
                } else if (thCmd == meetingSendCommand) {
                    absentNUM = "3";
                    getFromTextField = meetingTextField.getString();
                    meetingTextField.setString("");
                } else if (thCmd == travelSendCommand) {
                    absentNUM = "4";
                    getFromTextField = travelTextField.getString();
                    travelTextField.setString("");
                } else if (thCmd == sickSendCommand) {
                    absentNUM = "5";
                    getFromTextField = sickTextField.getString();
                    sickTextField.setString("");
                } else if (thCmd == vacationSendCommand) {
                    absentNUM = "6";
                    getFromTextField = vacationTextField.getString();
                    vacationTextField.setString("");
                } else if (thCmd == goneForDaySendCommand) {
                    absentNUM = "7";
                    getFromTextField = goneForDayTextField.getString();
                    goneForDayTextField.setString("");
                }

                // - Remove se absentList plats 6.

                else if (thCmd == newAbsent_1SendCommand) {
                    absentNUM = "8";
                    getFromTextField = newAbsent_1TextField.getString();
                    newAbsent_1TextField.setString("");
                } else if (thCmd == newAbsent_2SendCommand) {
                    absentNUM = "9";
                    getFromTextField = newAbsent_2TextField.getString();
                    newAbsent_2TextField.setString("");
                }
                if (absentNUM.equals("8") && !absentNUM.equals("9")) {

                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("8")) {

                            MDataStore.DataBase_RMS rms = new DataBase_RMS();
                            String setDTMF = rms.getEditAbsentDTMF_1();
                            absentSEND = setDTMF + getFromTextField + "#";

                            String dynamicNameOne = rms.getEditAbsentName_1();
                            String reNameOne = dynamicNameOne + " " +
                                               getFromTextField;
                            setAbsentStatusString(reNameOne);
                            Display.getDisplay(this).setCurrent(getAbsentList());

                            rms = null;

                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;
                        }

                        getNewAbsent_1Form();

                    }

                }
                if (absentNUM.equals("9") && !absentNUM.equals("8")) {

                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("9")) {

                            MDataStore.DataBase_RMS rms = new DataBase_RMS();
                            String setDTMF = rms.getEditAbsentDTMF_2();
                            absentSEND = setDTMF + getFromTextField + "#";
                            String dynamicNameTwo = rms.getEditAbsentName_2();
                            String reNameTwo = dynamicNameTwo + " " +
                                               getFromTextField;
                            setAbsentStatusString(reNameTwo);
                            Display.getDisplay(this).setCurrent(getAbsentList());

                            rms = null;

                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;
                        }

                        getNewAbsent_2Form();

                    }
                }
                if (!absentNUM.equals("8") && !absentNUM.equals("9")) {

                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("1")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";
                            String lunch = absentLunch_DEF + " " + getFromTextField;
                            setAbsentStatusString(lunch);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getLunchForm();

                    }
                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("2")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";

                            String out = absentTimeOfReturn_DEF + " " +
                                         getFromTextField;
                            setAbsentStatusString(out);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getOutForm();

                    }
                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("3")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";

                            String meeting = absentMeeting_DEF + " " +
                                             getFromTextField;
                            setAbsentStatusString(meeting);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getMeetingForm();

                    }
                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("4")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";

                            String travel = absentDateOfReturn_DEF + " " +
                                            getFromTextField;
                            setAbsentStatusString(travel);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getTravelForm();

                    }
                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("5")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";

                            String sick = absentIllness_DEF + " " +
                                          getFromTextField;
                            setAbsentStatusString(sick);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getSickForm();

                    }
                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("6")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";

                            String vacation = absentVacation_DEF + " " +
                                              getFromTextField;
                            setAbsentStatusString(vacation);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getVacationForm();

                    }
                    if (!getFromTextField.equals("")) {

                        if (absentNUM.equals("7")) {

                            absentSEND = absentBP + absentNUM +
                                         getFromTextField + "#";

                            String gone = absentDefaultGoneHome_DEF + " " +
                                          getFromTextField;
                            setAbsentStatusString(gone);
                            Display.getDisplay(this).setCurrent(getAbsentList());


                            MModel.Methods methods = new Methods();
                            methods.getAbsentSEND = absentSEND;
                            methods.sendAbsent();
                            methods = null;

                        }

                        getGoneForDayForm();

                    }
                }
            }

            /* ------- Login - Commands --------------------- */

            else if (thCmd == logDataLogInCommand) {

                if (logDataTextField.getString().equals("")) {

                    Display.getDisplay(this).setCurrent(getLogInForm());

                } else if (logDataTextField.getString().equals("4321")) {

                    Display.getDisplay(this).setCurrent(debug_List);

                }

                logDataTextField.setString("");
            }

            /* ------- Send - Commands --------------------- */

            // Koppla samtal send kommando
            else if (thCmd == connectSendCommand) {

                if (!connectTextField.getString().equals("")) {

                    MModel.Methods methods = new Methods();
                    methods.getConnectPhoneCallSEND = connectTextField.
                            getString();
                    methods.ConnectPhoneCall();
                    methods = null;
                    connectTextField.setString("");
                }

                getConnectPhoneForm();
            }
            else if (thCmd == connectResumeCommand) {

                    MModel.Methods methods = new Methods();
                    methods.getConnectPhoneCallSEND = "";
                    methods.ConnectPhoneCall();
                    methods = null;
                    connectTextField.setString("");
            }


            // Intern vidarekoppling send kommando
            else if (thCmd == CallForwardSendCommand) {

                if (!callforwardpresentTextField.getString().equals("")) {

                    MModel.Methods methods = new Methods();
                    methods.getCallForwardInternSEND =
                            callforwardpresentTextField.getString();
                    methods.callForwardIntern();
                    methods = null;
                    callforwardpresentTextField.setString("");
                }

                getCallForwardPresentForm();

            }
            // Extern vidarekoppling send kommando
            else if (thCmd == transferCallForwardSendCommand) {

                if (!transferForwardCallownNumberTextField.getString().equals(
                        "")) {

                    if (!transferForwardCallnewNumberTextField.getString().
                        equals("")) {

                        MModel.Methods methods = new Methods();
                        methods.getCallForwardExternSEND_1 =
                                transferForwardCallownNumberTextField.
                                getString();
                        methods.getCallForwardExternSEND_2 =
                                transferForwardCallnewNumberTextField.
                                getString();
                        methods.callForwardExtern();
                        methods = null;

                        transferForwardCallownNumberTextField.setString("");
                        transferForwardCallnewNumberTextField.setString("");
                    }
                }

                getTransferCallForwardForm();

            }
            // Ta bort extern vidarekoppling send kommando
            else if (thCmd == abortTransferCallForwardSendCommand) {

                if (!aborttransfercallTextField.getString().equals("")) {

                    MModel.Methods methods = new Methods();
                    methods.getCallForwardExternRemoveSEND =
                            aborttransfercallTextField.getString();
                    methods.callForwardRemoveExtern();
                    methods = null;
                    aborttransfercallTextField.setString("");
                }

                getAbortTransferCallForm();

            }
            // Login send kommando
            else if (thCmd == loginGroupSendCommand) {

                MModel.Methods methods = new Methods();
                methods.loginSpeficGroups = loginGroupTextField.getString();
                if (loginGroupTextField.getString().equals("")) {

                    getLoginGroupForm();

                }

                methods.logInSpecificGroups();
                methods = null;
                loginGroupTextField.setString("");

            }
            // Logoff send kommando
            else if (thCmd == logoffGroupSendCommand) {
                MModel.Methods methods = new Methods();
                methods.logoffSpeficGroups = logoffGroupTextField.getString();
                if (logoffGroupTextField.getString().equals("")) {

                    getLogoffGroupForm();

                }

                methods.logOffSpecificGroups();
                methods = null;
                logoffGroupTextField.setString("");

            }

            /* ------- Koppla samtal kommandon ------------- */

            else if (thCmd == connectEditRenameCommand) {

                Display.getDisplay(this).setCurrent(getRenameForm());

            }

            /* ------- Save - Commands --------------------- */

            else if (thCmd == connectEditRenameSaveCommand) {

                if (!connectEditRenameNameTextField.getString().equals("")) {

                    if (!connectEditRenameExtensionTextField.getString().equals(
                            "")) {

                        Display.getDisplay(this).setCurrent(this.
                                alertEditSettings,
                                getMainList());

                        MDataStore.DataBase_RMS rms = new DataBase_RMS();

                        String a = connectEditRenameNameTextField.getString();
                        String b = ": ";
                        String c = connectEditRenameExtensionTextField.
                                   getString();

                        String person = a + b + c;
                        int p = this.IDInternNumber;
                        rms.setInternName(person, p);

                        System.out.println(rms.getInternName(p));
                        System.out.println("this.IDInternNumber >> " +
                                           this.IDInternNumber);

                        rms.setDataStore();
                        rms.upDateDataStore();
                        rms = null;

                    }
                }

                getConnectRenameEditForm();

            }

            // Sparar värden för Intern-nummer

            else if (thCmd == connectEditSaveCommand) {

                if (!connectEditNameTextField.getString().equals("")) {

                    if (!connectEditExtensionTextField.getString().equals("")) {

                        Display.getDisplay(this).setCurrent(this.
                                alertEditSettings,
                                getMainList());

                        MDataStore.DataBase_RMS rms = new DataBase_RMS();

                        String a = connectEditNameTextField.getString();
                        String b = ": ";
                        String c = connectEditExtensionTextField.getString();

                        String person = a + b + c;
                        int p = this.IDInternNumber;
                        rms.setInternName(person, p);

                        System.out.println(rms.getInternName(p));
                        System.out.println("this.IDInternNumber >> " +
                                           this.IDInternNumber);

                        rms.setDataStore();
                        rms.upDateDataStore();
                        rms = null;

                    }
                }

                getConnectEditForm();

            }

            // Sparar nya värden för editera dynamiska attribut. (hänvisning)
            else if (thCmd == editAbsentSaveCommand) {

                MDataStore.DataBase_RMS rms = new DataBase_RMS();

                if (editNEWAbsent.equals("1")) {

                    String name_1 = editAbsentName_TextField.getString();
                    String dtmf_1 = editAbsentNewDTMF_TextField.getString();

                    if (name_1.equals("") || dtmf_1.equals("") ||
                        name_1.equals("") && dtmf_1.equals("")) {

                        Display.getDisplay(this).setCurrent(getEditAbsentForm());

                    } else if (!name_1.equals("") && !dtmf_1.equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings,
                                getMainList());
                        rms.setEditAbsentName_1(name_1);
                        rms.setEditAbsentDTMF_1(dtmf_1);
                        this.editAbsentDTMF_1 = dtmf_1;

                        if (editHHTTMMTT.equals("1")) {

                            rms.setHHMMTTMM_1("1");

                        } else if (editHHTTMMTT.equals("2")) {

                            rms.setHHMMTTMM_1("2");

                        }

                    }

                    editAbsentName_TextField.setString("");
                    editAbsentNewDTMF_TextField.setString("");

                }

                if (editNEWAbsent.equals("2")) {

                    String name_2 = editAbsentName_TextField.getString();
                    String dtmf_2 = editAbsentNewDTMF_TextField.getString();

                    if (name_2.equals("") || dtmf_2.equals("") ||
                        name_2.equals("") && dtmf_2.equals("")) {

                        Display.getDisplay(this).setCurrent(getEditAbsentForm());

                    } else if (!name_2.equals("") && !dtmf_2.equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings,
                                getMainList());
                        rms.setEditAbsentName_2(name_2);
                        rms.setEditAbsentDTMF_2(dtmf_2);
                        this.editAbsentDTMF_2 = dtmf_2;

                        if (editHHTTMMTT.equals("1")) {

                            rms.setHHMMTTMM_2("1");

                        } else if (editHHTTMMTT.equals("2")) {

                            rms.setHHMMTTMM_2("2");

                        }

                    }

                    editAbsentName_TextField.setString("");
                    editAbsentNewDTMF_TextField.setString("");

                }

                rms = null;
            }
            // Sparar nya värden för autoaccessNOPrefix
            else if (thCmd == AutoAccessSaveNOPrefixCommand) {

                if (!AutoAccessNOPrefixSwitchBoardTextField.getString().equals(
                        "")) {

                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            getMainList());
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    this.lineAccess_PBX = "NONE";
                    this.switchBoardNumber_PBX =
                            AutoAccessNOPrefixSwitchBoardTextField.getString();
                    rms.setLineAccess(lineAccess_PBX);
                    rms.setSwitchBoardNumber(switchBoardNumber_PBX);
                    rms.setDataStore();
                    rms.upDateDataStore();
                    this.lineAccess_PBX = rms.getAccessNumber();
                    rms = null;

                    MModel.Methods methods = new Methods();
                    methods.RefreshServer();
                    methods = null;
                }

                getAutoAccessNOPrefixForm();

            }
            // Sparar nya värden för autoaccess
            else if (thCmd == AutoAccessSaveCommand) {

                if (!AutoAccessLineAccessTextField.getString().equals("")) {

                    if (!AutoAccessSwitchBoardTextField.getString().equals("")) {

                        Display.getDisplay(this).setCurrent(alertEditSettings,
                                getMainList());
                        MDataStore.DataBase_RMS rms = new DataBase_RMS();
                        this.lineAccess_PBX = AutoAccessLineAccessTextField.
                                              getString();
                        this.switchBoardNumber_PBX =
                                AutoAccessSwitchBoardTextField.getString();

                        rms.setLineAccess(lineAccess_PBX);
                        rms.setSwitchBoardNumber(switchBoardNumber_PBX);
                        rms.setDataStore();
                        rms.upDateDataStore();
                        rms = null;

                        MModel.Methods methods = new Methods();
                        methods.RefreshServer();
                        methods = null;

                    }

                }

                getAutoAccessSettingForm();

            }
            // Sparar värden för Språk.
            else if (thCmd == countrySaveCommand) {

                if (!countryTextField.getString().equals("")) {

                    Display.getDisplay(this).setCurrent(alertRestarting,
                            getMainList());

                    MDataStore.DataBase_RMS rms = new DataBase_RMS();

                    rms.setLanguage(this.lang_PBX);
                    countryCode_PBX = countryTextField.getString();
                    rms.setCountryCode(countryCode_PBX);

                    String rename_1 = rms.getEditAbsentDTMF_1();
                    String rename_2 = rms.getEditAbsentDTMF_2();

                    if (rename_1.equals("0") && rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_1(language.genDefaultEdit_1);

                    }
                    if (rename_2.equals("0") && rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_2(language.genDefaultEdit_1);

                    }

                    if (rename_1.equals("0") && !rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_1(language.genDefaultEdit_2);

                    }
                    if (rename_2.equals("0") && !rms.getLanguage().equals("2")) {

                        rms.setEditAbsentName_2(language.genDefaultEdit_2);

                    }

                    rms.setAbsentStatus("0");

                    rms = null;
                }
                getCountryForm();

            }

            // Sparar värden för voicemail pbx.
            else if (thCmd == voiceEditSaveCommand_PBX) {

                if (!voiceMailPBXTextField_PBX.getString().equals("")) {

                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            getMainList());
                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    voiceMailSwitchboard_PBX = voiceMailPBXTextField_PBX.
                                               getString();
                    rms.setVoiceMailSwitchBoard(voiceMailSwitchboard_PBX);
                    rms = null;

                }

                getPBXVoiceEditForm();

            }
            // Sparar värden för voicemail operatör
            else if (thCmd == voiceOperatorMessageSaveCommand) {

                if (!voiceOperatorMessageTextField.getString().equals("")) {

                    MDataStore.DataBase_RMS rms = new DataBase_RMS();
                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            getMainList());
                    voiceMailOperator_PBX = voiceOperatorMessageTextField.
                                            getString();
                    rms.setVoiceMailOperator(voiceMailOperator_PBX);
                    rms = null;
                }

                getOperatorVoiceMessageForm();
            }

            /* ------- View - Commands Graphics --------------------- */

            else if (thCmd == mainListEditCommand) {

                Display.getDisplay(this).setCurrent(getSettingsList());

            } else if (thCmd == GraphicsAboutCommand) {

                GraphicsBackCommand = new Command(genDefaultBack_DEF,
                                                  Command.OK, 2);
                String sendName = prg_Name + " " + version_DEF;
                Displayable k = new AboutUs(sendName);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(GraphicsBackCommand);
                k.setCommandListener(this);

            } else if (thCmd == GraphicsHelpCommand) {

                GraphicsBackCommand = new Command(genDefaultBack_DEF,
                                                  Command.OK, 2);
                String sendName = prg_Name + " " + version_DEF;
                Displayable k = new HelpInfo(sendName);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(GraphicsBackCommand);
                k.setCommandListener(this);

            } else if (thCmd == GraphicsBackCommand) {

                String sendName = prg_Name + " " + version_DEF;
                Displayable k = new ServerNumber(sendName, ViewDateString,
                                                 device_brands, deveice_model,
                                                 pbx_name);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goGraphicsBackCommand);
                k.addCommand(GraphicsAboutCommand);
                k.addCommand(GraphicsHelpCommand);
                k.setCommandListener(this);

            } else if (thCmd == mainListaboutMobismaCommand) {

                String sendName = prg_Name + " " + version_DEF;
                Displayable k = new ServerNumber(sendName, ViewDateString,
                                                 device_brands, deveice_model,
                                                 pbx_name);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goGraphicsBackCommand);
                k.addCommand(GraphicsAboutCommand);
                k.addCommand(GraphicsHelpCommand);
                k.setCommandListener(this);

            }


            /* ------- Back - Commands --------------------- */

            else if (thCmd == editAbsentBackCommand) {

                Display.getDisplay(this).setCurrent(getAbsentEditList());
            }

            else if (thCmd == connectEditRenameBackCommand) {

                Display.getDisplay(this).setCurrent(getRenameForm());

            }

            else if (thCmd == connectEditBackCommand
                     || thCmd == connectRenameBackCommand
                     || thCmd == connectEditRenameCancelCommand) {

                Display.getDisplay(this).setCurrent(getConnectPhoneForm());

            }

            else if (thCmd == groupBackCommand
                     || thCmd == confirmExitNOCommand
                     || thCmd == voiceEditCancelCommand_PBX
                     || thCmd == voiceOperatorMessageCancelCommand
                     || thCmd == pbx_ListCancelCommand
                     || thCmd == BackCommandAbsentList
                     || thCmd == AutoAccessCancelCommand
                     || thCmd == AutoAccessCancelNOPrefixCommand
                     || thCmd == editAbsentListCancelCommand
                     || thCmd == editAbsentCancelCommand
                     || thCmd == goGraphicsBackCommand
                     || thCmd == voiceMailPBXListBackCommand
                     || thCmd == callForwardListBackCommand
                     || thCmd == connectBackCommand
                     || thCmd == connectEditCancelCommand
                     || thCmd == confirmOnCancelCommand
                     || thCmd == confirmOffCancelCommand) {

                Display.getDisplay(this).setCurrent(getMainList());

            } else if (thCmd == AutoAccessBackCommand
                       || thCmd == AutoAccessBackNOPrefixCommand
                       || thCmd == linePrefixBackCommand
                       || thCmd == voiceOperatorMessageBackCommand
                       || thCmd == voiceEditBackcommand_PBX
                       || thCmd == editAbsentListBackCommand
                       || thCmd == languageListBackCommand
                       || thCmd == countryCancelCommand
                       || thCmd == logDataCancelLogInCommand) {

                Display.getDisplay(this).setCurrent(pbx_List);

            } else if (thCmd == debugListLogOutCommand) {

                Display.getDisplay(this).setCurrent(alertLogOutDebug, pbx_List);

            } else if (thCmd == editTimeDateCancelCommand) {

                Display.getDisplay(this).setCurrent(getMainList());

            } else if (thCmd == countryBackCommand) {

                Display.getDisplay(this).setCurrent(language_List);

            } else if (thCmd == loginGroupBackCommand
                       || thCmd == logoffGroupBackCommand) {

                Display.getDisplay(this).setCurrent(groupList);

            } else if (thCmd == lunchBackCommand
                       || thCmd == outBackCommand
                       || thCmd == meetingBackCommand
                       || thCmd == travelBackCommand
                       || thCmd == sickBackCommand
                       || thCmd == vacationBackCommand
                       || thCmd == goneForDayBackCommand
                       || thCmd == newAbsent_1BackCommand
                       || thCmd == newAbsent_2BackCommand) {

                Display.getDisplay(this).setCurrent(getAbsentList());
            }

            else if (thCmd == CallForwardBackCommand
                     || thCmd == transferCallForwardBackCommand
                     || thCmd == abortTransferCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(callForwardList);

            }

            /*

               confirmOnYESCommand, confirmOnCancelCommand,
               confirmOffYESCommand, confirmOffCancelCommand,

             */
            /*
                         YES

                         setCall();

                         System.out.println("MEXON >>>> ");


                         NO

                         Mexoff();
                         String close_mexoff = "e" + ",";

                         sendMexOnOffMessage(close_mexoff);

                         System.out.println("MEXOFF >>>> " + close_mexoff);


                         */

                        else if (thCmd == confirmOffYESCommand) {

                            Mexoff();
                            String close_mexoff = "e" + ",";

                            sendMexOnOffMessage(close_mexoff);

                            System.out.println("MEXOFF >>>> " + close_mexoff);

                        }

                        else if (thCmd == confirmOnYESCommand) {

                            setCall();

                            System.out.println("MEXON >>>> ");

                        }

            /* ------- Exit - Commands --------------------- */

            else if (thCmd == confirmExitYESCommand) {

                notifyDestroyed();

            } else if (thCmd == mainListExitCommand) {

                Display.getDisplay(this).setCurrent(getAlertExit());

            }

        } catch (Exception ex) {
        }
    }

    /* **************************************************************************************************** */
    /* ===== Övriga kontroll-metoder som bör ligga i huvudklassen ========= */

    public void Mexon() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        rms.setMexONOFF("1");
        rms = null;
        Display.getDisplay(this).setCurrent(getMainList());

    }

    public void Mexoff() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        rms.setMexONOFF("0");
        rms = null;
        Display.getDisplay(this).setCurrent(getMainList());

    }


    public void setCall() {

        if (pbx_type.equals("3") && !this.lineAccess_PBX.equals("NONE")) {

            CallAutoAccess();

            // AutoAccess OCH med tom linjeprefix
        } else if (pbx_type.equals("3") && this.lineAccess_PBX.equals("NONE")) {

            CallNoLinjePrefix();

        }

    }

    // --- AutoAccess utan linjeprefix --> '2'
    public void CallNoLinjePrefix() {

        Mexon();

        Thread noPrefix = new Thread() {

            public void run() {

                try {
                    String start_mexon_01 = "s," +
                                            switchBoardNumber_PBX + "," +
                                            "" + ","
                                            + countryCode_PBX + "," +
                                            voiceMailOperator_PBX + ",";

                    sendMexOnOffMessage(start_mexon_01);

                    System.out.println("Utan LinjePrefix MexON >> " +
                                       start_mexon_01);

                } catch (Exception ex) {
                }

            }
        };
        noPrefix.start();

    }

    // --- AutoAccess --> '3'
    public void CallAutoAccess() {

        Mexon();

        Thread autoAccess = new Thread() {

            public void run() {

                try {
                    String start_mexon_01 = "s," +
                                            switchBoardNumber_PBX + "," +
                                            lineAccess_PBX + ","
                                            + countryCode_PBX + "," +
                                            voiceMailOperator_PBX + ",";

                    sendMexOnOffMessage(start_mexon_01);

                    System.out.println("AutoAccess MexON >> " +
                                       start_mexon_01);

                } catch (Exception ex) {
                }

            }
        };
        autoAccess.start();

    }

    // --- skickar in värde till sendMexOnOff()
    public void sendMexOnOffMessage(String message) {
        this.request = message;
        new Thread() {
            public void run() {
                sendMexOnOff();
            }
        }.start();
    }

    // --- metoden skickar in om mex är on | off
    public void sendMexOnOff() {
        try {
            StreamConnection conn = (StreamConnection) Connector.open(url);
            OutputStream out = conn.openOutputStream();
            byte[] buf = request.getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data);
            String response = new String(data, 0, actualLength);
            setAlertMEXONOFF(response);
            in.close();
            conn.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }

    // --- metoden ger olika Alert's, beroende på vilket värde som skickas från servern
    public void setAlertMEXONOFF(String resp) {

        boolean exist = false;

        this.ResponceMessage = resp;

        String controll = this.ResponceMessage.substring(0, 2);
        /*
                 if (controll.equals("sN")) { // Redan på
            exist = true;
            String setStringResponse = alertMessageMexAlreadyON_DEF;

            setStringResponse.length();

            alertMexAlreadyONOFF.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertMexAlreadyONOFF);

                 }

                 if (controll.equals("eN")) { // Redan avstängd
            exist = true;
            String setStringResponse = alertMessageMexAlreadyOFF_DEF;

            setStringResponse.length();

            alertMexAlreadyONOFF.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertMexAlreadyONOFF);

                 }*/

        if (controll.equals("sO")) { // Mex on
            exist = true;
            //alertON.setString("Vill du sätta på MEX?"/*alertMessageMEXOn_DEF*/);

            //Display.getDisplay(this).setCurrent(alertON);

        }

        if (controll.equals("eO")) { // Mex off
            exist = true;
            //alertOFF.setString("Vill du stänga av MEX?"/*alertMessageMEXOff_DEF*/);

            //Display.getDisplay(this).setCurrent(alertOFF);


        }
        if (controll.equals("fO")) { // Mex on, stänger prg
            exist = true;
            /* alertExit.setString(
                     programExitON_DEF);

             Display.getDisplay(this).setCurrent(alertExit);*/

        }

        if (controll.equals("fF")) { // Mex off, stänger prg
            exist = true;
            /*alertExit.setString(
                    programExitOFF_DEF);

                         Display.getDisplay(this).setCurrent(alertExit);*/

        }

        if (controll.equals("dO")) { // Debug på
            exist = true;
            String setStringResponse = "Debug on";

            setStringResponse.length();

            alertDebugONOFF.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertDebugONOFF);

        }

        if (controll.equals("dN")) { // Debug på (fel)
            exist = true;
            String setStringResponse = "Debug on error";

            setStringResponse.length();

            alertDebugONOFF.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertDebugONOFF);

        }

        if (controll.equals("pO")) { // Debug av
            exist = true;
            String setStringResponse = "Debug off";

            setStringResponse.length();

            alertDebugONOFF.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertDebugONOFF);

        }

        if (controll.equals("pN")) { // Debug av (fel)
            exist = true;
            String setStringResponse = "Debug off error";

            setStringResponse.length();

            alertDebugONOFF.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertDebugONOFF);

        }

        if (!exist) { // boolean

            String setStringResponse = resp;

            setStringResponse.length();

            alertSendOKNOK.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertSendOKNOK);

        }

    }


    // --- metoden kontrollerar datum för demo-licenser
    public void ControllDateTime() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        MModel.Date_Time dateTime = new Date_Time();

        String s1 = setDay_30DAY; // Dag (30 dagar framåt i tiden)
        String s2 = setMounth_30DAY; // Månad (30 dagar framåt i tiden)
        String s3 = setYear_30DAY; // År (30 dagar framåt i tiden)
        String s4 = setYear_TODAY; // År (dagens datum)
        String s5 = setMounth_TODAY; // Månad (dagens datum)
        String s6 = setDay_TODAY; // Dag (dagens datum)

        dateTime.controllDate(s1, s2, s3, s4, s5, s6);
        this.ViewDateString = setDay_30DAY + " " + setMounthName_30DAY + " " +
                              setYear_30DAY;
        String licensStart = setDay_TODAY + " " + setMounthNameToday + " " +
                             setYear_TODAY;

        System.out.println("Licensen Startdatum >> " + licensStart);
        System.out.println("Licensen gäller till >> " + ViewDateString);
        System.out.println("VÄRDET PLATS getTWO(); >> " + CheckTwo);
        dateTime = null;
    }

    /* ==================== SERVER Debug ======================================== */

    private String URLEncode(String s) {

        StringBuffer sbuf = new StringBuffer();
        int ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            switch (ch) {
            case ' ': {
                sbuf.append("+");
                break;
            }
            case '!': {
                sbuf.append("%21");
                break;
            }
            case '*': {
                sbuf.append("%2A");
                break;
            }
            case '\'': {
                sbuf.append("%27");
                break;
            }
            case '(': {
                sbuf.append("%28");
                break;
            }
            case ')': {
                sbuf.append("%29");
                break;
            }
            case ';': {
                sbuf.append("%3B");
                break;
            }
            case ':': {
                sbuf.append("%3A");
                break;
            }
            case '@': {
                sbuf.append("%40");
                break;
            }
            case '&': {
                sbuf.append("%26");
                break;
            }
            case '=': {
                sbuf.append("%3D");
                break;
            }
            case '+': {
                sbuf.append("%2B");
                break;
            }
            case '$': {
                sbuf.append("%24");
                break;
            }
            case ',': {
                sbuf.append("%2C");
                break;
            }
            case '/': {
                sbuf.append("%2F");
                break;
            }
            case '?': {
                sbuf.append("%3F");
                break;
            }
            case '%': {
                sbuf.append("%25");
                break;
            }
            case '#': {
                sbuf.append("%23");
                break;
            }
            case '[': {
                sbuf.append("%5B");
                break;
            }
            case ']': {
                sbuf.append("%5D");
                break;
            }
            default:
                sbuf.append((char) ch);
            }
        }
        return sbuf.toString();
    }

    public void sendLogdata() {

        logdata = "";
        sendMessageInt("k,IMEI,", IMEIDATA);
        sendMessageInt("i,", CONFDATA);
        sendMessageInt("u", LOGSIZE);

        int bufsize = 256;
        for (icount = 0; icount < logfilesize; icount = icount + bufsize) {
            logrequest = "j," + icount + ",";
            sendMessageInt(logrequest, LOGDATA);
        }

        sendLogdataExt();
    }

    public void sendRequestInt(String message, int what) {
        this.request = message;
        this.requestwhat = what;
        new Thread() {
            public void run() {
                sendMessageInt(request, requestwhat);
            }
        }.start();
    }


    public void sendMessageInt(String message, int what) {
        try {
            StreamConnection conn = (StreamConnection) Connector.open(inturl);
            OutputStream out = conn.openOutputStream();
            byte[] buf = message.getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[4096];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data);
            String response = new String(data, 0, actualLength);
            switch (what) {
            case CONFDATA:
                confdata = response;
                break;
            case LOGDATA:
                logdata = logdata + response;
                break;
            case IMEIDATA:
                imei = response;
                break;
            case LOGSIZE:
                logfilesize = Integer.parseInt(response);
                break;
            }
            in.close();
            conn.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void sendLogdataExt() {
        try {
            HttpConnection conn = (HttpConnection)
                                  Connector.open(ext_url);
            String submitstring = "imei=" + URLEncode(imei) + "&confdata=" +
                                  URLEncode(confdata) + "&logdata=" +
                                  URLEncode(logdata) + "&logfilesize=" +
                                  logfilesize + "&icount=" + icount +
                                  "&Submit=Submit";

            byte[] data = submitstring.getBytes();

            conn.setRequestMethod(HttpConnection.POST);
            conn.setRequestProperty("User-Agent",
                                    "Profile/MIDP-1.0 Configuration/CLDC-1.0");
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setRequestProperty("Content-Type",
                                    "application/x-www-form-urlencoded");
            OutputStream os = conn.openOutputStream();
            os.write(data);
            os.close();

            byte[] data2 = new byte[2048];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data2);
            String response = new String(data2, 0, actualLength);
            setAlertMEXONOFF(response);

            in.close();
            conn.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setAbsentStatusString(String s) {

        String presenceName = s;

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        Date_Time date = null;
        try {
            date = new Date_Time();
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreNotOpenException ex1) {
        } catch (RecordStoreException ex1) {
        } catch (IOException ex1) {
        }

        String minut = date.getAbsentMinute();
        String hour = date.getAbsentHour();
        int intYear = date.getYear();
        String year = Integer.toString(intYear);
        int intMounth = date.getMonth();
        String mounth = Integer.toString(intMounth);
        int intDay = date.getDay();
        String day = Integer.toString(intDay);

        String setAbsentStatusTime = presenceName + ":" + " " + hour + "." +
                                     minut + " " + year + "." + mounth + "." +
                                     day;
        System.out.println("Set STATUS >> " + setAbsentStatusTime);

        rms.setAbsentStatus(setAbsentStatusTime);

        date = null;
        rms = null;
    }


    // ***************** Splash-Screen ********************

    // --- Metoder
    public void startSplash() {

        try {

            if (!splashIsShown) {
                String sendName = prg_Name + " " + version_DEF;
                Displayable k = new SplashCanvas(sendName, ViewDateString);
                display.setCurrent(k);

            }

            doTimeConsumingInit();

            if (true) {
                // Game loop
            }

        } catch (Exception ex) {
        }

    }


    private void doTimeConsumingInit() {
        // Just mimic some lengthy initialization for 10 secs
        long endTime = System.currentTimeMillis() + 3000;
        while (System.currentTimeMillis() < endTime) {}

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            if (rms.getTWO().equals("2")) {

                Display.getDisplay(this).setCurrent(getAlertExpernceLisence());

            } else {

                // init the game's main Displyable (here a Form mainList)
                isInitialized = true;
                Display.getDisplay(this).setCurrent(getMainList());

            }
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        rms = null;

    }

    // --- Två egna klasser
    public class SplashScreen implements Runnable {
        private SplashCanvas splashCanvas;

        public void run() {
            String sendName = prg_Name + " " + version_DEF;
            splashCanvas = new SplashCanvas(sendName, ViewDateString);
            display.setCurrent(splashCanvas);
            splashCanvas.repaint();
            splashCanvas.serviceRepaints();
            while (!isInitialized) {
                try {
                    Thread.yield();
                } catch (Exception e) {}
            }

        }

    }


    public class SplashCanvas extends Canvas {

        private String prg_Name, viewDateString;

        // Tar emot värden från huvudclassen i konstruktorn.
        public SplashCanvas(String name, String ViewDateString) {

            this.viewDateString = ViewDateString;
            this.prg_Name = name;

        }

        protected void paint(Graphics g) {
            int width = getWidth();
            int height = getHeight();

            // Create an Image the same size as the
            // Canvas.
            Image image = Image.createImage(width, height);
            Graphics imageGraphics = image.getGraphics();

            // Fill the background of the image black
            imageGraphics.setColor(0x000000);
            imageGraphics.fillRect(0, 0, width, height);

            // Draw a pattern of lines
            int count = 10;
            int yIncrement = height / count;
            int xIncrement = width / count;
            for (int i = 0, x = xIncrement, y = 0; i < count; i++) {
                imageGraphics.setColor(0xC0 + ((128 + 10 * i) << 8) +
                                       ((128 + 10 * i) << 16));
                imageGraphics.drawLine(0, y, x, height);
                y += yIncrement;
                x += xIncrement;
            }

            // Add some text
            imageGraphics.setFont(Font.getFont(Font.FACE_PROPORTIONAL, 0,
                                               Font.SIZE_SMALL));
            imageGraphics.setColor(0xffff00);
            imageGraphics.drawString(prg_Name, width / 2, 15,
                                     Graphics.TOP | Graphics.HCENTER);

            try {
                Image image1 = Image.createImage("/mobisma_icon/mexa.png");
                imageGraphics.drawImage(image1, width / 2, 50,
                                        Graphics.TOP | Graphics.HCENTER);
            } catch (IOException ex) {
            }

            imageGraphics.drawString(viewDateString, width / 2, 100,
                                     Graphics.TOP | Graphics.HCENTER);

            imageGraphics.setColor(0xffffff);
            imageGraphics.drawString("mobisma AB", width / 2, 120,
                                     Graphics.TOP | Graphics.HCENTER);
            imageGraphics.drawString("All Rights Reserved © | 2008", width / 2,
                                     140, Graphics.TOP | Graphics.HCENTER);

            // Copy the Image to the screen
            g.drawImage(image, 0, 0, Graphics.TOP | Graphics.LEFT);

            splashIsShown = true;
        }


    }


}
