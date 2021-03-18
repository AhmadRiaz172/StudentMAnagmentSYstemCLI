import java.util.Scanner;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        display();   
        //RegistrationPeriod registrationPeriod = new RegistrationPeriod();
        //registrationPeriod.checkPeriod();
    
      
    }

    public static void display() {
        drawTitleName();
        //User name: test , Password: 123456
        //User name: Admin , Paswword: 1234
        Login login = new Login();
        if (!login.loginUser()){ // if login fails program will terminate
            System.out.println("OOPS login failed");
            return;
        }
        System.out.println("Login successfull....");

        int num = 0;
        while (num != 1 && num != 2) { // input validation

            clearScreen();
            drawTitleName();
            System.out.println("Please press: \n  1 for admin\n  2 for student");
            Scanner scan = new Scanner(System.in);
            num = scan.nextInt();

        }

        if (num == 1) {
            // Admin
            clearScreen();
            while (true) {
                System.out.println(
                        "Please press: \n 1 to add/update Student\n 2 to Print student list by Index Number\n 3 to Add a course\n 4 to Update a course\n 5 to Print Student list by index\n 6 to Print Student list by course\n 7 to remove student from the course\n 8 to LogOut");
                Scanner scan = new Scanner(System.in);
                num = scan.nextInt();
                clearScreen();
                if (num == 1) {
                    Student student = new Student();
                    String s_name, s_matrix, s_gender, s_nationality, s_email;
                    System.out.println("Enter Student Name:");
                    Scanner scan1 = new Scanner(System.in);
                    s_name = scan1.nextLine();
                    System.out.println("Enter Student Matrix no:");
                    Scanner scan2 = new Scanner(System.in);
                    s_matrix = scan2.nextLine();
                    System.out.println("Enter Student gender:");
                    Scanner scan3 = new Scanner(System.in);
                    s_gender = scan3.nextLine();
                    System.out.println("Enter Student nationality:");
                    Scanner scan4 = new Scanner(System.in);
                    s_nationality = scan4.nextLine();
                    System.out.println("Enter Student email:");
                    Scanner scan5 = new Scanner(System.in);
                    s_email = scan4.nextLine();
                    if ( student.AddStudens(s_name, s_matrix, s_gender, s_nationality,s_email) ){// Trying to add new student by given details
                        System.out.println("Trying to send email please wait");
                        SendEmail sendEmail = new SendEmail();
                        sendEmail.Send(s_email, "Hello,\n" + s_name +" you are added in your collage system by admin.");//Email will be sent if student added
                    }else {
                        System.out.println("OOPS.... unable to add student to list. Student may be already present");
                    }

                } else if (num == 2) {
                    RegisterStudent rs = new RegisterStudent();
                    rs.studentListByIndexNumber();
                } else if (num == 3 || num == 4) {
                    Course course = new Course();
                    String a_code, a_index, a_name, a_school, a_vacancies;
                    System.out.println("Enter Course code: ");
                    Scanner scan1 = new Scanner(System.in);
                    a_code = scan1.nextLine();
                    System.out.println("Enter Course index:");
                    Scanner scan2 = new Scanner(System.in);
                    a_index = scan2.nextLine();
                    System.out.println("Enter Course name: ");
                    Scanner scan3 = new Scanner(System.in);
                    a_name = scan3.nextLine();
                    System.out.println("Enter School:");
                    Scanner scan4 = new Scanner(System.in);
                    a_school = scan4.nextLine();
                    System.out.println("Enter vacacies available:");
                    Scanner scan5 = new Scanner(System.in);
                    a_vacancies = scan5.nextLine();
                    if (num == 3) {
                        if (course.addCourse(a_code, a_index, a_name, a_school, a_vacancies)) // This will try to add new course
                            System.out.println("Course Added sucessfully....");
                        else 
                            System.out.println("OOPS.. unable to add course. It may already be present");    
                    } else {
                        course.updateCourse(a_code, a_index, a_name, a_school, a_vacancies); // this function will try to update course
                    }
                } else if (num == 5) {
                    String inum;
                    System.out.println("Enter Index number:");
                    Scanner s1can5 = new Scanner(System.in);
                    inum = s1can5.nextLine();
                    RegisterStudent rs1 = new RegisterStudent();
                    rs1.studentListByIndexNumber(inum);  // this function will print student list by index number 
                } else if (num == 6) {
                    String inum;
                    System.out.println("Enter course number:");
                    Scanner s1can5 = new Scanner(System.in);
                    inum = s1can5.nextLine();
                    RegisterStudent rs = new RegisterStudent();
                    rs.studentListByCourse(inum);// This function will print student list by course
                }else if ( num == 7 ){
                    String inum;
                    System.out.println("Enter course index number:");
                    Scanner s1can5 = new Scanner(System.in);
                    inum = s1can5.nextLine();
                    String mnum;
                    System.out.println("Enter student matrix number:");
                    Scanner s1can6 = new Scanner(System.in);
                    mnum = s1can6.nextLine();
                    RegisterStudent rs = new RegisterStudent();
                    rs.unregisterStudent(mnum, inum);
                }
                else if (num == 8) {
                    break;
                }
            }
        } else if (num == 2) {

            
            // Student
            clearScreen();

            ActivePeriod activePeriod = new ActivePeriod();
            if ( ! activePeriod.checkActive()  ){ // This function will check if registrartion period is active
                System.out.println("OOPS....Your registration period is inactive.Logging out now\n");
                return;
            }

            while (true) {
             
                System.out.println(
                        "Please press: \n 1 to Add Course\n 2 to Drop Course\n 3 to Check/Print Courses Registered\n 4 to Check Vacancies available\n 5 to Change Index Number of Course\n 6 to swop Index number with another student\n 7 to LogOut");
                Scanner scan = new Scanner(System.in);
                num = scan.nextInt();
                clearScreen();
                if (num == 1) {
                    RegisterStudent rs = new RegisterStudent();
                    String a_courseCode, a_matrix, a_index;
                    System.out.println("Enter Course code:");
                    Scanner scan1 = new Scanner(System.in);
                    a_courseCode = scan1.nextLine();
                    System.out.println("Enter Student Matrix:");
                    Scanner scan2 = new Scanner(System.in);
                    a_matrix = scan2.nextLine();
                    System.out.println("Enter Course index:");
                    Scanner scan3 = new Scanner(System.in);
                    a_index = scan3.nextLine();
                    Boolean check = rs.registerNewStudent(a_courseCode, a_matrix, a_index); // Student will be registered in a course
                    if (check) {
                        Student student = new Student();
                        String mail = student.getEmailAddress(a_matrix);
                        System.out.println("successfully added\n");
                        SendEmail sendEmail = new SendEmail();
                        sendEmail.Send(mail, "Hi, Student your course + "  + a_courseCode + " is registered." ); // this will send email notification
                    } else {
                        System.out.println("Invalid Information given so course not added\n");
                    }
                } else if (num == 2) {
                    RegisterStudent rs = new RegisterStudent();
                    String a_courseCode, a_matrix, a_index;
                    System.out.println("Enter Course code:");
                    Scanner scan1 = new Scanner(System.in);
                    a_courseCode = scan1.nextLine();
                    System.out.println("Enter Student Matrix:");
                    Scanner scan2 = new Scanner(System.in);
                    a_matrix = scan2.nextLine();
                    System.out.println("Enter Course index:");
                    Scanner scan3 = new Scanner(System.in);
                    a_index = scan3.nextLine();
                    Boolean check = rs.unregisterStudent( a_matrix, a_index);
                    if (check) {// if course will be sucessfully unregistered
                        System.out.println("Course unregistered......\n");
                        waitinglist wait = new waitinglist();
                        System.out.println("Trying to send email please wait");
                        wait.sendMailstoIndex(a_index);// all waiting sstudents will recieve emails
                    } else {
                        System.out.println("OOPS... unable to unregister course. It may not be found in the system\n");
                    }
                    /* Drop Course */
                } else if (num == 3) {
                    RegisterStudent rs = new RegisterStudent();
                    String matrix_no;
                    System.out.println("Enter matrix no:");
                    Scanner scan1 = new Scanner(System.in);
                    matrix_no = scan1.nextLine();
                    System.out.println(rs.checkRegisteredCourses(matrix_no)); // Student will be able to check all registed courses
                } else if (num == 4) {
                    RegisterStudent rs = new RegisterStudent();
                    rs.displayAvailableSlots(); // slots of all courses will be displayed
                } else if (num == 5) {
                    RegisterStudent rs = new RegisterStudent();
                    String a_matrix, old, newInd;
                    System.out.println("Enter Student matrix no:");
                    Scanner scan1 = new Scanner(System.in);
                    a_matrix = scan1.nextLine();
                    System.out.println("Enter old index:");
                    Scanner scan2 = new Scanner(System.in);
                    old = scan2.nextLine();
                    System.out.println("Enter new index:");
                    Scanner scan3 = new Scanner(System.in);
                    newInd = scan3.nextLine();
                    if(rs.changeIndexNumber(a_matrix, old, newInd)){
                        Student student  = new Student();
                        SendEmail sendEmail = new SendEmail();
                        System.out.println("Index changed....\nTrying to send email please wait");
                        
                        sendEmail.Send( student.getEmailAddress(a_matrix) , "Hello, Your index number is changed form "  + old + " to "  + newInd  +"." );
                    }
                } else if (num == 6) {
                    RegisterStudent rs = new RegisterStudent();
                    String student_matrix_1, student_matrix_2, course_code;
                    System.out.println("Enter Student 1 matrix no:");
                    Scanner scan1 = new Scanner(System.in);
                    student_matrix_1 = scan1.nextLine();
                    System.out.println("Enter Student 2 matrix no:");
                    Scanner scan2 = new Scanner(System.in);
                    student_matrix_2 = scan1.nextLine();
                    System.out.println("Enter course code:");
                    Scanner scan3 = new Scanner(System.in);
                    course_code = scan3.nextLine();
                    rs.SwapIndexNumbers(student_matrix_1, student_matrix_2, course_code); // on Swaping of index no.
                } else if (num == 7) {
                    break;
                }
            }
        }

    }

    public static void clearScreen() {
        
        System.out.print("*********************************************************************************\n");
        //System.out.flush();
    }

    public static void drawTitleName() {
        System.out.println("*************************  College registration system  *************************\n");
    }

}