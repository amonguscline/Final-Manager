//I don't really know why i made this, it was to declutter but then the studymanager file got cluttered anyways
class Helper{
  //tells the user what commands to do based on what step of the process they are on
  public static void getHelp(int helpIndex){
    if(helpIndex==0){
      System.out.println("!login: log into an account (requires ID)\n!getID: get your id\n!signup: sign up for this service\n!exit: what does this button do");
      StudyManager.stepOne();
    }
    if(helpIndex==1){
      System.out.println("!getInfo: gets your information\n!getCourseInfo: gets a course's info\n!updateName: update your name\n!updateGPA: update your gpa\n!updateStudyPlan: update your study plan\n!updateFinalCompletion: mark a final exam as completed\n!calculateGPA: calculate your gpa\n!exit: what does this button do");
      StudyManager.stepTwo(false);
    }
  }
}