import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class CourseInstance {
    String name,courseCode, school, index, vacancey;
}

public class Course {
    
    public CourseInstance cI[] = new CourseInstance[100];
    public int count = 0 ;

    Course(){
        for ( int i = 0 ; i < 100 ; i++ )
            cI[i] = new CourseInstance();
        readCourses();    
    }

    public void readCourses(){
        try (BufferedReader in = new BufferedReader(new FileReader("course.txt"))) {
            String str;
            while ((str = in.readLine()) != null) {
                // splitting lines on the basis of token
                String[] tokens = str.split(",");
                cI[count].name = tokens[0];
                cI[count].courseCode = tokens[1];
                cI[count].school = tokens[2];
                cI[count].index = tokens[3];
                cI[count].vacancey = tokens[4];
                count++; /* Incrementing the count of students read */
            }
        } catch (Exception e) {
            System.out.println("File Read Error");
        }
    
    }

    public void displayAllCourses(){
        for ( int i = 0 ; i < count ; i++ )
            System.out.println( "Course code: " + cI[i].courseCode + " ,Course index: " + cI[i].index  + " ,Course name: " + 
                cI[i].name + " ,School: " + cI[i].school + " ,Vacancies: " + cI[i].vacancey  );
    }


    public Boolean addCourse( String a_code, String a_index, String a_name, String a_school, String a_vacancies ){
        if ( findCourse(a_code) )
            return false ;
        cI[count].index = a_index;
        cI[count].name = a_name;
        cI[count].courseCode = a_code;
        cI[count].school = a_school;
        cI[count].vacancey = a_vacancies;
        count ++;
        writeToFile();
        return true;
    }


    // This function will update course. If course was not previously prseent it will add it
    public void updateCourse( String a_code, String a_index, String a_name, String a_school, String a_vacancies ){
        removeCourse(a_code);
        cI[count].index = a_index;
        cI[count].name = a_name;
        cI[count].courseCode = a_code;
        cI[count].school = a_school;
        cI[count].vacancey = a_vacancies;
        count ++;
        writeToFile();
    }

    // this function will delte course if it is present
    public void removeCourse( String a_course ){
        for ( int i = 0 ; i < count ; i++ )
            if ( cI[i].courseCode.equals(a_course)  ){                
                cI[i] = cI[count -1];
                count -- ;
            }
        writeToFile();    
    }


    private void writeToFile(){
        try {
            FileWriter myWriter = new FileWriter("course.txt");
            String line = "";
            for (int i = 0; i < count; i++) {
                line = cI[i].name + "," + cI[i].courseCode + "," + cI[i].school + "," + cI[i].index + "," + cI[i].vacancey + "\n";
                myWriter.write(line);
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }
    
    // to find vacancies in given class
    // if it ruturns -1 then index is not found
    public int availableSlots( String index_o ){
        for ( int i = 0 ; i < count ; i++ )
            if ( cI[i].index.equals(index_o ))
                return  Integer.parseInt(cI[i].vacancey) ;
        return -1;
    }


    public String getCourseName( String code ){
        for ( int i = 0 ; i < count ; i++ )
            if( cI[i].courseCode.equals(code) )
                return cI[i].name;
        return "";
    }

    
    public Boolean findCourse( String CC ){
        for ( int i = 0 ; i < count ; i++ )
            if( cI[i].courseCode.equals(CC) )
                return true;
        return false;
    }




}
