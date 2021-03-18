import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class RegisteredStudentNode {
    String student_matrix_no, course_code, course_index;
}

public class RegisterStudent {

    private RegisteredStudentNode registeredStudentNode[] = new RegisteredStudentNode[200];
    private int count = 0;
    private Student student;
    private Course course;
    private waitinglist waitinglist;

    private String stringSet( String str , int size ){
        int k = size - str.length();
        for ( int i = k ; i > 0 ; i --  ){
            str += " ";
        }
        return str;
    }



    RegisterStudent() {
        waitinglist = new waitinglist();
        student = new Student();
        course = new Course();
        for (int i = 0; i < 200; i++)
            registeredStudentNode[i] = new RegisteredStudentNode();
        readRegisteredStudent();
    }

    private void readRegisteredStudent() {
        try (BufferedReader in = new BufferedReader(new FileReader("registered.txt"))) {
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

    private void writeRegisteredStudents() {
        try {
            FileWriter myWriter = new FileWriter("registered.txt");
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

    public void studentListByCourse( String icourse ) {
        Boolean b = false;
        for ( int i = 0 ; i < course.count ; i++ ){
            b = false;
            
            if ( !course.cI[i].courseCode.equals(icourse) )
                continue;
            System.out.println("Index : "  +  course.cI[i].index +"\n" + "Student name"  + "\t\t\tgender" + "\t\t  nationality\n" );
                for ( int j = 0 ; j < count ; j++ ){
                if ( course.cI[i].courseCode.equals(registeredStudentNode[j].course_code) ){
                        System.out.println(student.getNameGenderNationalityByMatrixNo(registeredStudentNode[j].student_matrix_no));                   
                    b = true;    
                }
            }

            System.out.println("\n\n" );
            
        }
     

    }

    
    public void studentListByIndexNumber( String inum ) {
        Boolean b = false;
        for ( int i = 0 ; i < course.count ; i++ ){
            b = false;
            
            if ( !course.cI[i].index.equals(inum) )
                continue;
            System.out.println(  "Student name"  + "\t\t\tgender" + "\t\t  nationality\n" );
            for ( int j = 0 ; j < count ; j++ ){
                if ( course.cI[i].index.equals(registeredStudentNode[j].course_index) ){
                        System.out.println(student.getNameGenderNationalityByMatrixNo(registeredStudentNode[j].student_matrix_no));                   
                    b = true;    
                }
            }
        }
     
    }



     
    public void studentListByIndexNumber(  ) {
        Boolean b = false;
        for ( int i = 0 ; i < course.count ; i++ ){
            b = false;
            System.out.println(  "Index number: " + course.cI[i].index + "\tCourse name: " + course.cI[i].name );
            for ( int j = 0 ; j < count ; j++ ){
                if ( course.cI[i].index.equals(registeredStudentNode[j].course_index) ){
                        System.out.println(student.getNameGenderNationalityByMatrixNo(registeredStudentNode[j].student_matrix_no));                   
                    b = true;    
                }
            }
            if ( ! b )
                System.out.println("No students found in this course or wrong course code");
        }
    }

    public void displayAll() {
        for (int i = 0; i < count; i++) {
            System.out.println("Course code: " + registeredStudentNode[i].course_code + " ,Course index: "
                    + registeredStudentNode[i].course_index + ", Student's matrix no.: "
                    + registeredStudentNode[i].student_matrix_no);
        }
    }

    public Boolean registerNewStudent(String a_courseCode, String a_matrix, String a_index) {

        if (course.findCourse(a_courseCode) && student.findStudent(a_matrix)) { // check if passes code and student id
                                                                                // is present

            int vacancies = course.availableSlots(a_index) - getCountOfRegisteredStudents(a_index); // check if seats
                                                                                                    // are available
            if (vacancies <= 0) // if seats are unavailable then dont register but add students in waiting list.
            {
                waitinglist.addToList(a_matrix, a_index, a_courseCode);
                return false;
            }
            registeredStudentNode[count].course_code = a_courseCode;
            registeredStudentNode[count].course_index = a_index;
            registeredStudentNode[count].student_matrix_no = a_matrix;
            count++;
            writeRegisteredStudents();
            return true;
        }

        return false;
    }


    public Boolean updateStudent(String a_courseCode, String a_matrix, String a_index) {
        if ( !unregisterStudent(a_matrix, a_index) )
            return false;
        if (course.findCourse(a_courseCode) && student.findStudent(a_matrix)) { // check if passes code and student id
                                                                                // is present

            int vacancies = course.availableSlots(a_index) - getCountOfRegisteredStudents(a_index); // check if seats
                                                                                                    // are available
            if (vacancies <= 0) // if seats are unavailable then dont register but add students in waiting list.
            {
                waitinglist.addToList(a_matrix, a_index, a_courseCode);
                return false;
            }
            registeredStudentNode[count].course_code = a_courseCode;
            registeredStudentNode[count].course_index = a_index;
            registeredStudentNode[count].student_matrix_no = a_matrix;
            count++;
            writeRegisteredStudents();
            return true;
        }

        return false;
    }



    private int getCountOfRegisteredStudents(String index) {
        int c = 0;
        for (int i = 0; i < count; i++)
            if (registeredStudentNode[i].course_index.equals(index))
                c++;
        return c;
    }

    public Boolean unregisterStudent( String a_matrix, String a_index) {
        for (int i = 0; i < count; i++)
            if ( registeredStudentNode[i].course_index.equals(a_index)
                    && registeredStudentNode[i].student_matrix_no.equals(a_matrix)) {
                registeredStudentNode[i] = registeredStudentNode[count - 1];
                count--;
                writeRegisteredStudents();
                System.out.println("Notifying all by mails....");
                waitinglist w = new waitinglist();
                w.sendMailstoIndex(a_index);
                return true;
            }
        return false;
    }

    public String checkRegisteredCourses( String matrix_no ){
        String str = "";
        for ( int i = 0 ; i < count ; i++ ){
            if ( registeredStudentNode[i].student_matrix_no.equals(matrix_no) ) 
                str +=  registeredStudentNode[i].course_code + "\t" +
                     course.getCourseName( registeredStudentNode[i].course_code )   + "\n"; 
        }
        return str;
    }


    public Boolean changeIndexNumber( String a_matrix , String old , String newInd ){
        for ( int i = 0 ; i < count ; i++ ){
            if ( registeredStudentNode[i].student_matrix_no.equals(a_matrix) && 
                    registeredStudentNode[i].course_index.equals(old) )
            {
                registeredStudentNode[i].course_index = newInd;
                return true;
            }

        }
        return false;
    }

    public Boolean SwapIndexNumbers( String student_matrix_1 , String student_matrix_2 , String course_code  ){
        Boolean test = false ;
        Boolean run = false;
        String temp = "";
        for ( int i = 0 ; i < count ; i++ ){
            if ( registeredStudentNode[i].student_matrix_no.equals(student_matrix_1) 
            && registeredStudentNode[i].course_code.equals(course_code)  ){
                temp = registeredStudentNode[i].course_index;
                run = true;
            }
        }
        if ( !run )
            return false;
        String temp2 = "";
        for ( int i = 0 ; i < count ; i++ ){
            if ( registeredStudentNode[i].student_matrix_no.equals(student_matrix_2) && registeredStudentNode[i].course_code.equals(course_code) ){
                temp2 = registeredStudentNode[i].course_index;
                registeredStudentNode[i].course_index = temp;
                if ( run )
                    test = true;
            }
        }

        for ( int i = 0 ; i < count ; i++ ){
            if ( registeredStudentNode[i].student_matrix_no.equals(student_matrix_1) )
                registeredStudentNode[i].course_index = temp2;
        }
        
        writeRegisteredStudents();
        return test;
    }



    public void displayAvailableSlots(){
        System.out.println("Course code\t  Course index\t  Available seats\n");
        for ( int i = 0 ; i < course.count ; i++ ){
            int vacancies = course.availableSlots(course.cI[i].index) - getCountOfRegisteredStudents(course.cI[i].index); // check if seats
            String str = Integer.toString(vacancies);
            System.out.println(  course.cI[i].courseCode + "\t\t  " + course.cI[i].index 
                        +  "\t\t  "   +  str  );
        }
       
        System.out.println("\n\n");
       
    }




}
