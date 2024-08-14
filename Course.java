class Course{
  private String ID;
  private String name;
  private boolean isAP;
  private boolean hasFinal;
  private String studyPlan;
  private boolean finalCompleted;
  
  public Course(String ID, String name, boolean isAP, boolean hasFinal){
    this.ID=ID;
    this.name=name;
    this.isAP=isAP;
    this.hasFinal=hasFinal;
    this.studyPlan="";
    this.finalCompleted=false;
  }
  //sets study plan and final completion status, used when creating the students course objects
  public void initInfo(String studyPlan, boolean finalCompleted){
    this.studyPlan = studyPlan;
    this.finalCompleted = finalCompleted;
  }
  //prints out the course info so that it can be read by the user
  public void getCourseInfo(){System.out.println("Course Name: "+ this.name+"\nStudy plan: "+this.studyPlan+"\nIs it an AP: "+this.isAP+"\nHas a final: "+this.hasFinal+"\nFinal completed: "+this.finalCompleted);}
  public String getID(){return ID;}
  public String getName(){return name;}
  public boolean getIsAp(){return isAP;}
  public boolean getHasFinal(){return hasFinal;}
  public String getStudyPlan(){return studyPlan;}
  public void setStudyPlan(String sp){this.studyPlan=sp;}
  public void setFinalCompleted(boolean f){this.finalCompleted=f;}
  public boolean getFinalCompleted(){return finalCompleted;}
}