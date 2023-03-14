package MModel;

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

public class CONF {

    // - Inställningar ---

    public static final String MEX = "0"; // mex on '1' eller off '0'
    public static final String SWTBNR = "+46812200749"; // växelnummer
    public static final String LA = "0"; // Linjeaccess
    public static final String CCODE = "46"; // Landsnummer
    public static final String DBG = "0"; // Debug on eller off
    public static final String HGP = "197"; // 'Hangupkey' default
    public static final String VM = "222"; // Operatör röstbrevlåda
    public static final String PCODE = ""; // PINkod
    public static final String ANKN = "202"; // Anknytning
    public static final String LANG = "9"; // Språk
    public static final String PRECODE = "*7"; // Pre edit code
    public static final String PBXVM = "500"; // Växelns röstbrevlådan
    public static final String DEMO = "0"; // Dema ja '1' eller nej '0'
    public static final String COMPANYNAME = "mobisma AB"; // Företagsnamn
    public static final String NAME = "Peter Albertsson"; // Personnamn
    public static final String PRGNAME = "mobismaME"; // Personnamn
    public static final String IMEI2 = "00460101501594500"; // IMEI serienummer 00460101501594500 emulator,  356810032809537, 352921027118984
    public static final String DTMFP = ";postd="; // DTMF-tecken för att sända dtmf. ';postd=' eller '/p'.
    public static final String DL = "1"; // Default språk '1' inte engelska, '0' == engelska.
    public static final String PBRAND = "Sony Ericsson"; // Telefonmärke
    public static final String PMODEL = "Satio"; // Telefonmodell
    public static final String PBXNAME = "Aastra Business Phone"; // Växelnamn
    public static final String IMEIPROPERTY = ""; // System.property för att ta fram IMEI nummer.

    // Växeltyp >> Panasonic '1', Aastra ascotel '2', Aastra BP '3', Aastra md110 '4', Aastra mxone '5', LG '6', Avaya '9'.
    public static final String PBXID = "3";


    public CONF() {


    }


}
