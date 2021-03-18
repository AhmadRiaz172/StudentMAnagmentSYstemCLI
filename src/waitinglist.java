import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class waitinglist {

    private RegisteredStudentNode registeredStudentNode[] = new RegisteredStudentNode[200];
    private int count = 0;

    waitinglist() {
        for (int i = 0; i < 200; i++)
            registeredStudentNode[i] = new RegisteredStudentNode();
        readRegisteredStudent();
    }

    private void readRegisteredStudent() {
        try (BufferedReader in = new BufferedReader(new FileReader("waiting.txt"))) {
            String str;
            while ((str = in.readLine()) != null) {
                // splitting lines on the basis of token
                String[] tokens = str.split(",");
                registeredStudentNode[count].course_code = tokens[0];
                registeredStudentNode[count].course_index = tokens[1];
                registeredStudentNode[count].student_matrix_no = tokens[2];
                count++; /* Incrementing the count of students read */
            }
        } catch (Exception e) {
            System.out.println("File Read Error");
        }
    }

    private void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter("waiting.txt");
            String line = "";
            for (int i = 0; i < count; i++) {
                line = registeredStudentNode[i].course_code + "," + registeredStudentNode[i].course_index + ","
                        + registeredStudentNode[i].student_matrix_no + "\n";
                myWriter.write(line);
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    // this function will add student at the end of waiting list
    public void addToList(String s_matrix, String c_index, String c_code) {
        if ( checkDuplicate(s_matrix, c_index, c_code) )
            return;
        registeredStudentNode[count].course_code = c_code;
        registeredStudentNode[count].course_index = c_index;
        registeredStudentNode[count].student_matrix_no = s_matrix;
        count++;
        writeToFile();
    }

    public RegisteredStudentNode getFirst(String index) {

        RegisteredStudentNode re = new RegisteredStudentNode();

        if (count == 0)
            return re;
        if (registeredStudentNode[count - 1].course_index.equals(index)){
            re.course_code = registeredStudentNode[0].course_code;
            re.course_index = registeredStudentNode[0].course_index;
            re.student_matrix_no = registeredStudentNode[0].student_matrix_no;
            return re ;
        }


        Boolean b = false;

        for (int i = 0; i < count - 1; i++) {
            if (registeredStudentNode[i].course_index.equals(index)){
                re.course_code = registeredStudentNode[0].course_code;
                re.course_index = registeredStudentNode[0].course_index;
                re.student_matrix_no = registeredStudentNode[0].student_matrix_no;
                b = true;
            }
            if (b)
                registeredStudentNode[i] = registeredStudentNode[i + 1];
        }

        return re;
    }

    public void removeFirst(String index) {
        if (count == 0)
            return;
        if (registeredStudentNode[count-1].course_index.equals(index)){
            count -- ;
            writeToFile();
            return ;
        }
   
        Boolean b = false;
   
        for (int i = 0; i < count - 1; i++) {
            if (registeredStudentNode[i].course_index.equals(index))
                b = true;
            if (b)
                registeredStudentNode[i] = registeredStudentNode[i + 1];
        }
        count--;
        writeToFile();
    }

    public Boolean removeFromWaitingList(String s_matrix) {
        if (count == 0)
            return false;
        if (registeredStudentNode[count-1].student_matrix_no.equals(s_matrix)){
            count -- ;
            return false;
        }

        Boolean b = false;
        for (int i = 0; i < count - 1; i++) {
            if (registeredStudentNode[i].student_matrix_no.equals(s_matrix))
                b = true;
            if (b)
                registeredStudentNode[i] = registeredStudentNode[i + 1];
        }
        count--;
        return b;
    }


    private Boolean checkDuplicate(String s_matrix, String c_index, String c_code){
        for ( int i = 0 ; i < count ; i++ ){
            if ( registeredStudentNode[i].course_code.equals(c_code) && 
                    registeredStudentNode[i].course_index.equals(c_index) && registeredStudentNode[i].student_matrix_no.equals(s_matrix)  )
                return true;
        }
        return false;
    }

    public void sendMailstoIndex ( String a_index ){
        for ( int i = 0 ; i < count ; i ++ ){
            if ( registeredStudentNode[i].course_index.equals(a_index) ){
                Student student = new Student();
                String mail = student.getEmailAddress(registeredStudentNode[i].student_matrix_no);
                SendEmail sendEmail = new SendEmail();
                sendEmail.Send(mail, "Hi,\nCourse you were previously trying to register having course index no. "
                    + registeredStudentNode[i].course_index + 
                        " have available vacancies now. Please login and grab your seat untill someone else gets it." );

            }

        }
    }





}
