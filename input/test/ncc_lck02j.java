import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

class ncc_lck02j {
  static DateFormat format =
      DateFormat.getDateInstance(DateFormat.MEDIUM);
 
  public Date parse(String str) throws ParseException {
    synchronized (getClass()) { // Intend to synchronizes on Base.class
      return format.parse(str);
    }
  }
 
  public Date doSomething(String str) throws ParseException {
    return new Helper().doSomethingAndParse(str);
  }
 
  private class Helper {
    public Date doSomethingAndParse(String str) throws ParseException {
      synchronized (getClass()) { // Synchronizes on Helper.class
        // ...
        return format.parse(str);
      }
    }
  }
}
