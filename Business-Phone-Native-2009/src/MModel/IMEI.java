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
public class IMEI {

    private String imei, imei_prop;

    public IMEI() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        MDataStore.DataBase_RMS rms = new DataBase_RMS();
        this.imei = rms.getIMEI();
        this.imei_prop = rms.getSystemIMEIProperty();
        rms = null;
    }

    /* ===== Övriga kontroll-metoder som bör ligga i huvudklassen ========= */

   public void checkIMEI() throws IOException, RecordStoreNotOpenException,
           InvalidRecordIDException, RecordStoreException {

       MModel.SortClass sort = new SortClass();
       MDataStore.DataBase_RMS rms = new DataBase_RMS();

       String checkIMEI_DIGITS = this.imei;

       System.out.println("Angivet: IMEI nummer i Conf-filen >> " + checkIMEI_DIGITS);

       String checkIMEI_PROPERTY = this.imei_prop;

       System.out.println("Angivet: System-property i Conf-filen >> " + checkIMEI_PROPERTY);


       // imei_string hämtar telefonens IMEI nummer.
       String imei_string = System.getProperty(imei_prop);
       System.out.println("IMEI >>>>>>>>>>>>>>>>>>>>>>>>>>> " + imei_string);

       // sorterar imei_string och jämför med angivet IMEI nummer.
       String imei_send_check = sort.sortCharToDigits(imei_string);

                if (!checkIMEI_DIGITS.equals(imei_send_check)) {

           System.out.println("Angivet: IMEI nummer FALSKT >> " + this.imei + " OCH " + imei_send_check);

           rms.setImeiFalse("1");

                }

       sort = null;
       rms = null;
   }

}
