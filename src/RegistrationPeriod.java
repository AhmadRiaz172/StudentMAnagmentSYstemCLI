import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

public class RegistrationPeriod {
    
    // this function will check if date on which function is called is in between the eligible dates.
    public Boolean checkPeriod( ){

        Date dstart = new Date();
        Date dend = new Date();
        Date dnow = new Date();



        // this part will read the openndate file in specific formate
        try (BufferedReader in = new BufferedReader(new FileReader("opendate.txt"))) {
            String str;
            while ((str = in.readLine()) != null) {
                // splitting lines on the basis of token
                String[] tokens = str.split(",");
                String [] d1 = tokens[0].split("-");
                String [] d2 = tokens[1].split("-");
                
                int a = Integer.parseInt(d1[0]);
                dstart.setDate(a);
                a = Integer.parseInt(d1[1]);
                dstart.setMonth(a);
                a = Integer.parseInt(d1[2]);
                dstart.setYear(a);

                a = Integer.parseInt(d2[0]);
                dend.setDate(a);
                a = Integer.parseInt(d2[1]);
                dend.setMonth(a);
                a = Integer.parseInt(d2[2]);
                dend.setYear(a);

            }
        } catch (Exception e) {
            System.out.println("File Read Error");
        }

        

        if ( dnow.before(dend) && dnow.after(dstart)  )
            return true; 

        return true;
    }




}
