package MModel;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import java.io.IOException;
import javax.microedition.rms.RecordStoreException;
import MControll.Main_Controll;
import MDataStore.DataBase_RMS;
import MModel.ConnectTCPIP_Socket;

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

public class Date_Time {

    private Main_Controll mainControll;
    private MDataStore.DataBase_RMS rms;
    private MModel.ConnectTCPIP_Socket tcpip;

    public String

    /*Kontrollerar om dtmf är sann så är det en java-version*/
    setP_PBX,

    /* Datum och Tid */
    setMounth_30DAY, setDay_30DAY, setYear_30DAY,
    setDay_TODAY, setMounth_TODAY, setYear_TODAY,

    /* Datum och Tid i metoden ControllDate() */
    DBday_30_DAY, DBmounth_30_DAY, DByear_30_DAY, DBday_TODAY,
    DBmounth_TODAY, DByear_TODAY, setViewMounth;

    public int
    /* Int värden datum och tid */
    CounterDays, // antal dagar per licens

    day_TODAY, mounth_TODAY, year_TODAY,
    _30_dayAfter, _30_monthAfter, _30_yearAfter,
    day, mounth, checkYear;

    /* Tid och Datum java-paket */
    public Date today;
    public TimeZone tz = TimeZone.getTimeZone("GMT+1");

    // kontstruktorn
    public Date_Time() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        this.tz = tz;
        today = new Date();
        today.getTime();
        today.toString();

        // anger hur många dagar programmet ska vara öppet innan det stängs.
        this.CounterDays = 31;

    }
    public int getYear() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);

        return year;
    }
    public int getMonth() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        return month;
    }
    public int getDay() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day;
    }
    public int getDemoDay() {

        // Get today's day
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Räknar fram 30 dagar framåt vilket datum år osv...
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
        long offset = date.getTime();
        offset += CounterDays * MILLIS_PER_DAY;
        date.setTime(offset);
        cal.setTime(date);
        day = cal.get(Calendar.DAY_OF_MONTH);

        return day;

    }
    public int getDemoMonth() {

        // Get today's month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        // Räknar fram 30 dagar framåt vilket datum år osv...
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
        long offset = date.getTime();
        offset += CounterDays * MILLIS_PER_DAY;
        date.setTime(offset);
        cal.setTime(date);
        month = cal.get(Calendar.MONTH);

        return month;

    }
    public int getDemoYear() {

       // Get today's year
       Calendar cal = Calendar.getInstance();
       Date date = new Date();
       cal.setTime(date);
       int year = cal.get(Calendar.YEAR);

       // Räknar fram 30 dagar framåt vilket datum år osv...
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
        long offset = date.getTime();
        offset += CounterDays * MILLIS_PER_DAY;
        date.setTime(offset);
        cal.setTime(date);
        year = cal.get(Calendar.YEAR);

       return year;

   }

    public void setDBDate() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        countXDigitsDay();

        System.out.println("Om 30 dagar är det den >> " + _30_dayAfter +
                           ", och månad >> " + _30_monthAfter +
                           " det är år >> " +
                           _30_yearAfter);

        // konvertera int till string...
        String convertDayAfter = Integer.toString(_30_dayAfter);
        String convertMounthAfter = Integer.toString(_30_monthAfter);
        String convertYearAfter = Integer.toString(_30_yearAfter);

        this.setDay_30DAY = convertDayAfter;
        this.setMounth_30DAY = convertMounthAfter;
        this.setYear_30DAY = convertYearAfter;

    }

    public void setDBDateBack() {

        countThisDay();

        System.out.println("Kontrollerar dagens dautm >> " + day_TODAY +
                           ", och månad >> " + mounth_TODAY + " det är år >> " +
                           year_TODAY);

        // konvertera int till string...
        String convertDayBack = Integer.toString(day_TODAY);
        String convertMounthBack = Integer.toString(mounth_TODAY);
        String convertYearBack = Integer.toString(year_TODAY);

        this.setDay_TODAY = convertDayBack;
        this.setMounth_TODAY = convertMounthBack;
        this.setYear_TODAY = convertYearBack;

    }

    public void countThisDay() { // Räkna ut dagens datum och år.

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Dagens datum är den >> " + day +
                           ", Årets månad är nummer >> " + month +
                           " det är år >> " + year);

        day_TODAY = day;
        mounth_TODAY = month;
        year_TODAY = year;

    }

    // Räkna ut antal dagar framåt, ex 30 dagar.
    public void countXDigitsDay() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Dagens datum är den >> " + day +
                           ", Årets månad är nummer >> " + month +
                           " det är år >> " + year);

        // Räknar fram 30 dagar framåt vilket datum år osv...
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
        long offset = date.getTime();
        offset += CounterDays * MILLIS_PER_DAY;
        date.setTime(offset);
        cal.setTime(date);

        // Now get the adjusted date back
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);

        _30_dayAfter = day;
        _30_monthAfter = month;
        _30_yearAfter = year;

    }
    public void CloseServer() {

        DataBase_RMS rms = null;
        try {
            rms = new DataBase_RMS();
        } catch (IOException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try { // Om värdet innehåller är '1' ett så är det 'inte' en java-version installerad.
            if (rms.getDTMFsend().equals("1")) {

                MModel.ConnectTCPIP_Socket tcpip = new ConnectTCPIP_Socket();
                tcpip.sendLCloseSRV();
                tcpip = null;
            }
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }

        rms = null;
    }

    public void controllDate(String s1, String s2, String s3, String s4,
                             String s5, String s6) throws IOException,
            RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        this.DBday_30_DAY = s1;
        this.DBmounth_30_DAY = s2;
        this.DByear_30_DAY = s3;
        this.DByear_TODAY = s4;
        this.DBmounth_TODAY = s5;
        this.DBday_TODAY = s6;

        // Deklarerar nya variabler med INT-värden.
        Integer controllDBday_TODAY = new Integer(0);
        Integer controllDBmonth_TODAY = new Integer(0);
        Integer controllDByear_TODAY = new Integer(0);

        // Konverterar 'strängar' till INT-värden.
        int INT_Day_TODAY = controllDBday_TODAY.parseInt(DBday_TODAY);
        int INT_Mounth_TODAY = controllDBmonth_TODAY.parseInt(DBmounth_TODAY);
        int INTD_Year_TODAY = controllDByear_TODAY.parseInt(DByear_TODAY);

        // Deklarerar nya variabler med INT-värden.
        Integer controllDBday_30_DAY = new Integer(0);
        Integer controllDBmonth_30_DAY = new Integer(0);
        Integer controllDByear_30_DAY = new Integer(0);

        // Konverterar 'strängar' till INT-värden.
        int INT_Day_30_DAY = controllDBday_30_DAY.parseInt(DBday_30_DAY);
        int INT_Mounth_30_DAY = controllDBmonth_30_DAY.parseInt(DBmounth_30_DAY);
        int INT_Year_30_DAY = controllDByear_30_DAY.parseInt(DByear_30_DAY);

        // Returnerar datum från 1 - 31.
        String Date_TODAY = checkDay().trim();
        // Returnerar månad 0 - 11, (Jan - Dec)
        String Mounth_TODAY = checkMounth().trim();

        // Deklarerar nya variabler med INT-värden.
        Integer controll_Method_DBday_TODAY = new Integer(0);
        Integer controll_Method_DBmounth_TODAY = new Integer(0);

        // Konverterar 'strängar' till INT-värden.
        int INT_Day_Method_TODAY = controll_Method_DBday_TODAY.parseInt(
                Date_TODAY);
        int INT_Mounth_Method_TODAY = controll_Method_DBmounth_TODAY.parseInt(
                Mounth_TODAY);

        // Skickar strängen '2' till stängmetoden i mainControll classen.
        String TWO = "2";

        this.checkYear = getYear();


        // Om tex. dag 5 <= 5 && månad 2 <= 2 && År 2009 == 2009, så har tiden för demon utgått.
        if (INT_Day_30_DAY <= INT_Day_Method_TODAY &&
            INT_Mounth_30_DAY <= INT_Mounth_Method_TODAY &&
            INT_Year_30_DAY == checkYear) {

            MDataStore.DataBase_RMS rms = new DataBase_RMS();
            rms.setTWO(TWO); // Om sant skriv in 2 i databasen och stäng ner programmet.
            rms = null;

            System.out.println("31 - dagars Licensen har gått ut " );
            CloseServer();

        }
        if (INT_Mounth_Method_TODAY == 0) { // Om INTmounthToDay har värdet '0' som är januari

            INT_Mounth_TODAY = 0; // Då innehåller installations-månaden samma värde som nu-månaden.

        }
        // Om installations-månaden är större än 'dagens' månad som är satt i mobilen
        if (INT_Mounth_TODAY > INT_Mounth_Method_TODAY) {

            MDataStore.DataBase_RMS rms = new DataBase_RMS();
            rms.setTWO(TWO);
            // Om sant skriv in 2 i databasen och stäng ner programmet.
            rms = null;

            System.out.println("Användaren skiftar månad >> ställer tillbaka månad " );
            CloseServer();

        }
        // Om installations-månaden är större än 'dagens' månad OCH installationsåret är mindre än det här året.
        if (INT_Mounth_TODAY > INT_Mounth_Method_TODAY &&
            INTD_Year_TODAY < checkYear) {

            MDataStore.DataBase_RMS rms = new DataBase_RMS();
            rms.setTWO(TWO);
            rms = null;
            // Om sant skriv in 2 i databasen och stäng ner programmet.
            System.out.println("Användaren skiftar månad >> ställer tillbaka månad " );
            System.out.println("OCH" );
            System.out.println("Användaren skiftar år >> ställer tillbaka år " );
            CloseServer();


        }
        // Om installations-året är större än året som är satt i mobilen. >> går bakåt i tiden...
        if (INTD_Year_TODAY > checkYear) {

            MDataStore.DataBase_RMS rms = new DataBase_RMS();
            rms.setTWO(TWO);
            rms = null;
            // Om sant skriv in 2 i databasen och stäng ner programmet.
            System.out.println("Användaren skiftar månad >> ställer tillbaka månad " );
            CloseServer();

        }
        /*
         Om installations-dagen är större än ' dagen '
         OCH
         Om installatinsmånaden är större än ' månaden '
         OCH
         Om installations-året är större än ' året '
         */
        if (INT_Day_TODAY > INT_Day_Method_TODAY &&
            INT_Mounth_TODAY > INT_Mounth_Method_TODAY &&
            INTD_Year_TODAY > checkYear) {

            MDataStore.DataBase_RMS rms = new DataBase_RMS();
            rms.setTWO(TWO);
            rms = null;
            // Om sant skriv in 2 i databasen och stäng ner programmet.
            System.out.println("Användaren skiftar install-dag >> ställer fram install-dag " );
            System.out.println("OCH" );
            System.out.println("Användaren skiftar install-månad >> ställer fram install-månad " );
            System.out.println("OCH" );
            System.out.println("Användaren skiftar install-år >> ställer fram install-år " );
            CloseServer();

        }
        /*
         Om installations-månaden är större än ' månaden '
         OCH
         Om installtaions-året är större än ' året '
         */
        if (INT_Mounth_TODAY > INT_Mounth_Method_TODAY &&
            INTD_Year_TODAY > checkYear) {

            MDataStore.DataBase_RMS rms = new DataBase_RMS();
            rms.setTWO(TWO);
            rms = null;
            // Om sant skriv in 2 i databasen och stäng ner programmet.
            System.out.println("Användaren skiftar install-månad >> ställer fram install-månaden " );
            System.out.println("OCH" );
            System.out.println("Användaren skiftar install-år >> ställer fram install-år " );
            CloseServer();

        }

        MDataStore.DataBase_RMS rms = new DataBase_RMS();
        rms.setDataStore();
        rms.upDateDataStore();
        rms = null;

    }

    public String setMounth(String number) throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        setViewMounth = number;

        if (setViewMounth.equals("0")) {

            setViewMounth = "January";
        }
        if (setViewMounth.equals("1")) {

            setViewMounth = "February";
        }
        if (setViewMounth.equals("2")) {

            setViewMounth = "March";
        }
        if (setViewMounth.equals("3")) {

            setViewMounth = "April";
        }
        if (setViewMounth.equals("4")) {

            setViewMounth = "May";
        }
        if (setViewMounth.equals("5")) {

            setViewMounth = "June";
        }
        if (setViewMounth.equals("6")) {

            setViewMounth = "July";
        }
        if (setViewMounth.equals("7")) {

            setViewMounth = "August";
        }
        if (setViewMounth.equals("8")) {

            setViewMounth = "September";
        }
        if (setViewMounth.equals("9")) {

            setViewMounth = "October";
        }
        if (setViewMounth.equals("10")) {

            setViewMounth = "November";
        }
        if (setViewMounth.equals("11")) {

            setViewMounth = "December";
        }

        return setViewMounth;
    }

    public String checkDay() {

        String mobileClock = today.toString();

        String checkDayString = mobileClock.substring(8, 10);

        if (checkDayString.equals("01")) {

            checkDayString = "1";

        } else if (checkDayString.equals("02")) {

            checkDayString = "2";

        } else if (checkDayString.equals("03")) {

            checkDayString = "3";

        } else if (checkDayString.equals("04")) {

            checkDayString = "4";

        } else if (checkDayString.equals("05")) {

            checkDayString = "5";

        } else if (checkDayString.equals("06")) {

            checkDayString = "6";

        } else if (checkDayString.equals("07")) {

            checkDayString = "7";

        } else if (checkDayString.equals("08")) {

            checkDayString = "8";

        } else if (checkDayString.equals("09")) {

            checkDayString = "9";

        }

        return checkDayString;

    }

    public String checkMounth() {

        String mobileClock = today.toString();

        String checkMounthString = mobileClock.substring(4, 7);

        if (checkMounthString.equals("Jan")) {

            checkMounthString = "0";

        } else if (checkMounthString.equals("Feb")) {

            checkMounthString = "1";

        } else if (checkMounthString.equals("Mar")) {

            checkMounthString = "2";

        } else if (checkMounthString.equals("Apr")) {

            checkMounthString = "3";

        } else if (checkMounthString.equals("May")) {

            checkMounthString = "4";

        } else if (checkMounthString.equals("Jun")) {

            checkMounthString = "5";

        } else if (checkMounthString.equals("Jul")) {

            checkMounthString = "6";

        } else if (checkMounthString.equals("Aug")) {

            checkMounthString = "7";

        } else if (checkMounthString.equals("Sep")) {

            checkMounthString = "8";

        } else if (checkMounthString.equals("Oct")) {

            checkMounthString = "9";

        } else if (checkMounthString.equals("Nov")) {

            checkMounthString = "10";

        } else if (checkMounthString.equals("Dec")) {

            checkMounthString = "11";

        }

        return checkMounthString;
    }
    public String getTime() {

        String stringMinutes;

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int year = cal.get(Calendar.YEAR);
        int mounth = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);


        if (minute == 0 || minute == 1 || minute == 2 || minute == 3 ||
            minute == 4
            || minute == 5 || minute == 6 || minute == 7
            || minute == 8 || minute == 9) {

            stringMinutes = Integer.toString(minute);

            String subMinutes = "0";

            stringMinutes = subMinutes + stringMinutes;

            // String time = hour + "." + stringMinutes + " " + day + "/" + mounth + "-" + years;

            String time = year + "." + mounth + "." + day + " " + hour + "." + stringMinutes;

            System.out.println("klockan är >> " + time);

            return time;

        }

        String time = year + "." + mounth + "." + day + " " + hour + "." + minute;

        System.out.println("klockan är >> " + time);

        return time;

    }

    public String getAbsentHour() {

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String hour_s = Integer.toString(hour);
        String absentHour = "";


        if (hour_s.equals("0") || hour_s.equals("1") || hour_s.equals("2") || hour_s.equals("3")
            || hour_s.equals("4") || hour_s.equals("5") || hour_s.equals("6") || hour_s.equals("7")
            || hour_s.equals("8") || hour_s.equals("9")) {

            String setNOLL = "0";
            hour_s = setNOLL + hour_s;
            absentHour = hour_s;

            System.out.println("timmen är >> " + absentHour);

            return absentHour;

        }else

         absentHour = hour_s;

        System.out.println("timmen är >> " + absentHour);

        return absentHour;

    }


    public String getAbsentMinute() {

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int minute = cal.get(Calendar.MINUTE);

        String minute_s = Integer.toString(minute);
        String absentTime = "";


        if (minute_s.equals("0") || minute_s.equals("1") || minute_s.equals("2") || minute_s.equals("3")
            || minute_s.equals("4") || minute_s.equals("5") || minute_s.equals("6") || minute_s.equals("7")
            || minute_s.equals("8") || minute_s.equals("9")) {

            String setNOLL = "0";
            minute_s = setNOLL + minute_s;
            absentTime = minute_s;

            System.out.println("timmen är >> " + absentTime);

            return absentTime;


        }else

         absentTime = minute_s;

        System.out.println("klockan är >> " + absentTime);

        return absentTime;

    }

}
