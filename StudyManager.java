import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import java.io.FileWriter;
class StudyManager{
  private static Scanner input = new Scanner(System.in);
  private static Student user;
  private static ArrayList<Student> students = Initializer.getStudents();

    
  public StudyManager(){
    Initializer.initIDs();
    flush();
    stepOne();
  }
  //clears the console so that it doesnt get to cramped
  public static void flush(){
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  //stepone will allow the user to login with their id, sign up or get their id, then will proceed to step two given that the information is valid
  public static void stepOne(){
    System.out.println("This is the finals manager (? for help)");
    String response = input.nextLine();
    if(response.equals("?")){
      Helper.getHelp(0);
    }
    if(response.equals("!login")){
      System.out.println("what is your user id?");
      response = input.nextLine();
      user = Initializer.getStudentFromID(Integer.parseInt(response));
      if(user==null){
        System.out.println("ID not found");
      }
      else{
        flush();
        System.out.println("Logged in as "+user.getNameFormatted());
        stepTwo(true);
      }
      
    }
    else if(response.equals("!getID")){
      System.out.print("first name?");
      String fName = input.nextLine();
      System.out.print("last name?");
      String lName = input.nextLine();
      System.out.println(Initializer.getIDFromName(fName,lName));
      stepOne();
    }
    else if(response.equals("!signup")){
      System.out.println("What is your first name?");
      String fName = input.nextLine();
      System.out.print("What is your last name?");
      String lName = input.nextLine();
      System.out.println("What grade are you in? (number)");
      int year = Integer.parseInt(input.nextLine());
      String in = "";
      ArrayList<Course> initCourses = new ArrayList<Course>();
      while(in.toLowerCase().equals("no")==false){
        System.out.println("What classes would you like to enroll in? \n(? for course catalogue)\n(no to exit)");
        in = input.nextLine();
        if(in.equals("?")){
            Initializer.getCourses();
          }
        initCourses = Initializer.enroll(in,initCourses);
      }
      String id= Initializer.generateID();
      user = new Student(lName, fName, id, year, 0);
      user.setCourses(initCourses);
      students.add(user);
      System.out.println("Please enter the following");
      double gpa=calculateGPA();
      user.setGPA(gpa);
      Initializer.addStudent(user);
      flush();
      System.out.println("You have been signed up. Restart to log in");
    }
    else if(response.equals("!exit")){
      flush();
      System.out.println("How could you!");
      System.exit(0);
    }
    else{
      System.out.println("That isn't a command (? for help)");
      stepOne();
    }
  }
  //steptwo will allow the student to get their information and set their information
  public static void stepTwo(boolean firstTime){
    if(firstTime){System.out.println("Now that you're logged in, what would you like to do? (? for help)");}
    else{System.out.println("What would you like to do next? (? for help)");}
    String response = input.nextLine();
    if(response.equals("?")){
      Helper.getHelp(1);
    }
    else if(response.equals("!getInfo")){
      user.printInfo();
      stepTwo(false);
    }
    else if(response.equals("!getCourseInfo")){
      System.out.println("Which course would you like to get the info of?");
      String c = input.nextLine();
      try{Initializer.getCourseFromName(user,c);}
      catch(Exception courseNotFound){
        System.out.println("That course doesn't exist");
        stepTwo(false);
      }
      stepTwo(false);
    }
    else if(response.equals("!updateName")){
      System.out.println("What is your new first name?");
      String fName = input.nextLine();
      System.out.println("What is your new last name?");
      String lName = input.nextLine();
      Initializer.setName(fName,lName,Integer.parseInt(user.getID()));
      flush();
      System.out.println("Name was set to "+fName+" "+lName);
      stepTwo(false);
    }
    else if(response.equals("!updateGPA")){
      System.out.println("Please enter the following");
      Initializer.setGPA(calculateGPA(), Integer.parseInt(user.getID()));
      stepTwo(false);
    }
    else if(response.equals("!updateStudyPlan")){
      System.out.println("What class do you want to change your study plan in?");
      String cName = input.nextLine();
      System.out.println("What is your new study plan?");
      String studyPlan = input.nextLine();
      Initializer.setStudyPlan(studyPlan,cName,Integer.parseInt(user.getID()));
      flush();
      System.out.println("Study Plan in "+cName+ " was set to "+studyPlan);
      stepTwo(false);
    }
    else if(response.equals("!updateFinalCompletion")){
      System.out.println("What class do you want to change your update your final status in?");
      String cName = input.nextLine();
      System.out.println("Have you finished the final in "+ cName+"?");
      String studyPlan = input.nextLine();
      boolean b=false;
      if(studyPlan.charAt(0)=='y'){b=true;}
      Initializer.setFinalCompleted(b,cName,Integer.parseInt(user.getID()));
      flush();
      System.out.println("Final completion in "+cName+ " was set to "+b);
      stepTwo(false);
    }
    else if(response.equals("!calculateGPA")){
      calculateGPA();
    }
    else if(response.equals("!exit")){
      flush();
      System.out.println("How could you!");
      System.exit(0);
    }
    else{
      System.out.println("That isn't a command (? for help)");
      stepTwo(false);
    }
  }
  public static double calculateGPA(){
    double gpa = 0;
      for(int i=0;i<user.getCoursesNames().size();i++){
        System.out.println("What is your grade in "+user.getCoursesNames().get(i)+"?");
        gpa+=Double.parseDouble(input.nextLine());
      }
      gpa/=user.getCoursesNames().size();
      flush();
      System.out.println("Your gpa is "+gpa);
    return gpa;
  }
}