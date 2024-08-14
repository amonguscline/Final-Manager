import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.*;
class Student{
  private String sID;
  private String lName;
  private String fName;
  private int year;
  private double gpa;
  private ArrayList<Course> courses = new ArrayList<Course>();
  private Scanner input = new Scanner(System.in);
  
  public Student(String lName, String fName, String sID, int year, double gpa){
    this.lName = lName;
    this.fName = fName;
    this.sID = sID;
    this.year = year;
    this.gpa = gpa;
    this.initIDs();
  }

  //Initializes the ids of the courses and gives each student their courses in an arraylist of courses
  public void initIDs(){
    try{
      Scanner fileReader = new Scanner(new File("Courses.txt"));
      Scanner studentReader = new Scanner(new File("Students.txt"));
      for(int j=0;studentReader.hasNextLine();j++){
        List<String> underscoreSplit = Arrays.asList(studentReader.nextLine().split("_"));
        List<String> commaSplit = Arrays.asList(underscoreSplit.get(5).split(","));
        String temp = "";
        for(int i=0;i<commaSplit.size();i++){
          temp+=commaSplit.get(i)+";";
        }
        List<String> semicolonSplit = Arrays.asList(temp.split(";"));
        // List<String> semiSplit = new ArrayList<String>();
        // for(int i=0;i<semicolonSplit.size();i+=3){
        //   semiSplit.add(semicolonSplit.get(i));
        // }
        for(int i=0;fileReader.hasNextLine();i++){
          List<String> l = Arrays.asList(fileReader.nextLine().split(","));
          for(int q=0;q<semicolonSplit.size();q++){
            if(semicolonSplit.get(q).equals(l.get(0))){
              courses.add(new Course(l.get(0),l.get(1),Boolean.parseBoolean(l.get(2)),Boolean.parseBoolean(l.get(3))));
              courses.get(i).initInfo(semicolonSplit.get(q+1),Boolean.parseBoolean(semicolonSplit.get(q+2)));
            }
          }
        }
      }
    }catch(Exception fileNotFound){System.out.println("error");}
  }
  //prints out students info
  public void printInfo(){
    System.out.println("Student Name: "+this.fName+" "+this.lName+"\nStudent ID: "+this.sID+"\nStudent Year Grade: "+this.year+"\nStudent GPA: "+this.gpa+"\nStudent Courses: "+this.getCoursesNames());
  }
  public String getName(){return this.fName+""+this.lName;}
  public String getNameFormatted(){return this.fName+" "+this.lName;}
  //formats the data for the instance of a student so that it can be written to the database
  public String getInfoForDatabase(){
    String finalData = this.lName+"_"+this.fName+"_"+this.sID+"_"+this.year+"_"+this.gpa+"_";
    for(int i=0;i<courses.size();i++){
      if(i<courses.size()-1){finalData+=courses.get(i).getID()+";;false,";}
      else{finalData+=courses.get(i).getID()+";;false";}
    }
    return finalData;
  }
  public String getID(){return this.sID;}
  //gets the names of the courses that the student has
  public ArrayList<String> getCoursesNames(){
    ArrayList<String> courseNames=new ArrayList<String>();
    for(int i=0;i<courses.size();i++){
      courseNames.add(courses.get(i).getName());
    }
    return courseNames;
  }
  public void setName(String fName, String lName){
    this.fName = fName;
    this.lName = lName;
  }
  public void setGPA(double gpa){this.gpa=gpa;}
  public ArrayList<Course> getCourses(){
    return courses;
    }
  public void setCourses(ArrayList<Course> c){
    for(int i=0;i<c.size();i++){
      courses.add(c.get(i));
    }
  }
}