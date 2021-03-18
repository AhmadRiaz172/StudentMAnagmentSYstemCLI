import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
public class ActivePeriod {


    public Date date1;
    public Date date2;
    ActivePeriod(){
        date1 = new Date();
        date2 = new Date();
        read();
    }
    private void read(){
        try (BufferedReader in = new BufferedReader(new FileReader("opendate.txt"))) {
            String str;
            while ((str = in.readLine()) != null) {
                // splitting lines on the basis of token
                String[] tokens = str.split(",");
                String t1[] = tokens[0].split("-");
                String t2[] = tokens[1].split("-");

                String d1 = t1[0];
                String m1 = t1[1];
                String y1 = t1[2];
                String d2 = t2[0];
                String m2 = t2[1];
                String y2 = t2[2];

                date1.setDate(Integer.parseInt(d1));
                date1.setMonth(Integer.parseInt(m1));
                date1.setYear(Integer.parseInt(y1));
                
                date2.setDate(Integer.parseInt(d2));
                date2.setMonth(Integer.parseInt(m2));
                date2.setYear(Integer.parseInt(y2));

            }
        } catch (Exception e) {
            System.out.println("File Read Error");
        }
    
    }


    public Boolean checkActive(){

        Date date = new Date(); 

        date.setYear(date.getYear()+1900);
        date.setMonth(date.getMonth()+1);
       

        if ( date.getYear() >= date1.getYear()  && date.getYear() <= date2.getYear()  )
            if ( date.getMonth() >= date1.getMonth()  && date.getMonth() <= date2.getMonth() )
                if ( date.getDate() >= date1.getDate()  && date.getDate() <= date2.getDate() )
                    return true;

        return false;
    }
    


}
