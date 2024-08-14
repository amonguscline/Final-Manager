import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
class Initializer{
  private static ArrayList<Student> students = new ArrayList<Student>();
  private static ArrayList<Course> courses = new ArrayList<Course>();
  private Scanner input = new Scanner(System.in);
  public static void Initializer(){
    
  }
  //initializes the course ids that are in the courses.txt file so that later they can be accessed and compared to courses of an instance of a student and can add courses to a student object
  public static void initIDs(){
    try{
      Scanner fileReader = new Scanner(new File("Courses.txt"));
      for(int i=0;fileReader.hasNextLine();i++){
        List<String> l = Arrays.asList(fileReader.nextLine().split(","));
        courses.add(new Course(l.get(0),l.get(1),Boolean.parseBoolean(l.get(2)),Boolean.parseBoolean(l.get(3))));
      }
    }catch(Exception fileNotFound){;}
  }
  //gets the student information from students.txt
  public static ArrayList<Student> getStudents(){
    try{
      Scanner studentReader = new Scanner(new File("Students.txt"));
      for(int i=0;studentReader.hasNextLine();i++){
        List<String> l = Arrays.asList(studentReader.nextLine().split("_"));
        students.add(new Student(l.get(0),l.get(1),l.get(2),Integer.parseInt(l.get(3)),Double.parseDouble(l.get(4))));
        List<String> ids = Arrays.asList(l.get(5).split(","));
        for(int i2=0;i2<ids.size();i2++){
          List<String> courseInfo = Arrays.asList(ids.get(i2).split(";"));
          for(int j=0;j<courses.size();j++){
            if(courseInfo.get(0).equals(courses.get(j).getID())){
  getStudentFromID(Integer.parseInt(l.get(2))).getCourses().get(j).initInfo(courseInfo.get(1).toString(), Boolean.parseBoolean(courseInfo.get(2)));
            }
          }
        }                                    
      }
    }
    catch(Exception fileNotFound){System.out.println("error woo");}
    return students;
  }
  //gets a students id from a first and last name
  public static String getIDFromName(String fName, String lName){
    for(int i=0;i<students.size();i++){
      if(students.get(i).getName().equals(fName+""+lName)){
        return students.get(i).getID();
      }
    }
    return "Student not found";
  }
  //gets a student object from their id
  public static Student getStudentFromID(int id){
    for(int i=0;i<students.size();i++){
      if(Integer.parseInt(students.get(i).getID())==id){
        return students.get(i);
      }
    }
    return null;
  }
  //sets a students name and writes it to students.txt
  public static void setName(String fName, String lName, int lineNumber){
    try{
      File file = new File("Students.txt");
      List<String> l = Files.readAllLines(file.toPath());
      List<String> lin = Arrays.asList(l.get(lineNumber-1).split("_"));
      ArrayList<String> line = new ArrayList<String>();
      for(int i=0;i<lin.size();i++){
        line.add(lin.get(i));
      }
      line.set(0,lName);
      line.set(1,fName);
      l.set(lineNumber-1, String.join("_",line));
      Files.write(file.toPath(),l);
      getStudentFromID(Integer.parseInt(lin.get(2))).setName(fName,lName);
    }catch(Exception cryToSleep){System.out.println("error");}
  }
  //sets a students gpa and writes it to students.txt
  public static void setGPA(double gpa, int lineNumber){
    try{
      File file = new File("Students.txt");
      List<String> l = Files.readAllLines(file.toPath());
      List<String> lin = Arrays.asList(l.get(lineNumber-1).split("_"));
      ArrayList<String> line = new ArrayList<String>();
      for(int i=0;i<lin.size();i++){
        line.add(lin.get(i));
      }
      line.set(4,""+gpa);
      l.set(lineNumber-1, String.join("_",line));
      Files.write(file.toPath(),l);
      getStudentFromID(Integer.parseInt(lin.get(2))).setGPA(gpa);
    }catch(Exception cryToSleep){System.out.println("error");}
  }
  //gets a course object from a student object based on the name of the course
  public static void getCourseFromName(Student s, String cName){
    for(int i=0;i<courses.size();i++){
      if(s.getCourses().get(i).getName().equals(cName)){
        s.getCourses().get(i).getCourseInfo();
      }
    }
  }
  //gets the course id from a course given the name of the course
  public static String getCourseIDFromName(String cName){
    for(int i=0;i<courses.size();i++){
      if(courses.get(i).getName().equals(cName)){
        return courses.get(i).getID();
      }
    }
    return null;
  }
  //sets a student's course's study plan and writes it into students.txt
  public static void setStudyPlan(String sp,String cName,int lineNumber){
    try{
      int cIndex=0;
      File file = new File("Students.txt");
      List<String> l = Files.readAllLines(file.toPath());
      List<String> li = Arrays.asList(l.get(lineNumber-1).split("_"));
      List<String> lin = Arrays.asList(li.get(5).split(","));
      String temp = "";
      for(int i=0;i<lin.size();i++){
        temp+=lin.get(i)+";";
      }
      lin = Arrays.asList(temp.split(";"));
      ArrayList<String> line = new ArrayList<String>();
      int indexPointer = 0;
      for(int i=0;i<lin.size();i++){
        if(lin.get(i).equals(getCourseIDFromName(cName))){
          indexPointer=i+1;
          cIndex=i/3;
        }
        line.add(lin.get(i));
      }
      line.set(indexPointer,sp);
      String f ="";
      for(int j=0;j<line.size();j++){
        f+=line.get(j);
        if(j%3==2){f+=",";}
        else{f+=";";}
      }
      f=f.substring(0,f.length()-1);
      li.set(5,f);
      l.set(lineNumber-1, String.join("_",li));
      Files.write(file.toPath(),l);
      getStudentFromID(Integer.parseInt(li.get(2))).getCourses().get(cIndex).setStudyPlan(sp);
    }catch(Exception cryToSleep){System.out.println("error");}
  }
  //updates the status of finalcompleted in a given course of a student object
  public static void setFinalCompleted(boolean b,String cName, int lineNumber){
    try{
      int cIndex=0;
      File file = new File("Students.txt");
      List<String> l = Files.readAllLines(file.toPath());
      List<String> li = Arrays.asList(l.get(lineNumber-1).split("_"));
      List<String> lin = Arrays.asList(li.get(5).split(","));
      String temp = "";
      for(int i=0;i<lin.size();i++){
        temp+=lin.get(i)+";";
      }
      lin = Arrays.asList(temp.split(";"));
      ArrayList<String> line = new ArrayList<String>();
      int indexPointer = 0;
      for(int i=0;i<lin.size();i++){
        if(lin.get(i).equals(getCourseIDFromName(cName))){
          indexPointer=i+2;
          cIndex=i/3;
        }
        line.add(lin.get(i));
      }
      line.set(indexPointer,""+b);
      String f ="";
      for(int j=0;j<line.size();j++){
        f+=line.get(j);
        if(j%3==2){f+=",";}
        else{f+=";";}
      }
      f=f.substring(0,f.length()-1);
      li.set(5,f);
      l.set(lineNumber-1, String.join("_",li));
      Files.write(file.toPath(),l);
      getStudentFromID(Integer.parseInt(li.get(2))).getCourses().get(cIndex).setFinalCompleted(b);
    }catch(Exception cryToSleep){System.out.println("error");}
  }
  //adds a student to students.txt
  public static void addStudent(Student s){
    try{
      File file = new File("Students.txt");
      List<String> l = Files.readAllLines(file.toPath());
      l.add(s.getInfoForDatabase());
      Files.write(file.toPath(),l);
      System.out.println(s.getInfoForDatabase());
    }
    catch(Exception err){System.out.println("file not found");}
  }
  //gives a list of courses that will be put into a new student object. this function is called recursively in studymanager until there are no more classes that the student would like to be enrolled in
  public static ArrayList<Course> enroll(String cName, ArrayList<Course> iCourse){
    ArrayList<Course> initCourses = iCourse;
    for(int i=0;i<courses.size();i++){
      if(cName.equals(courses.get(i).getName())){
        Course curcourse = courses.get(i);
        initCourses.add(new Course(curcourse.getID(),curcourse.getName(),curcourse.getIsAp(),curcourse.getHasFinal()));
      }
    }
    return initCourses;
  }
  //gets the default course names for the student to pick what to enroll in
  public static void getCourses(){
    for(int i=0;i<courses.size();i++){
      System.out.println(courses.get(i).getName());
    }
  }
  //generates an id for the student based on what line number the students information will be written to
  public static String generateID(){
    int id = 1;
    try{
      Scanner file = new Scanner(new File("Students.txt"));
      while(file.hasNextLine()){
        id++;
        file.nextLine();
      }
      file.close();
    }catch(Exception e){System.out.println("err");}
    return ""+id;
  }
}