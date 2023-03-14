package MControll;

import MControll.Main_Controll;

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

import javax.microedition.rms.*;



  /**
   * IntegerFilter class
   */
class IntegerFilter implements RecordFilter{

      /**
       * Used toward the end of this example, when creating a
       * RecordEnumerator to only index through the integer records.
       */
      public boolean matches(byte[] candidate)
             throws IllegalArgumentException {

          /*
           * Only show integers
           */
          return candidate[0] == 'I';
      }
}
