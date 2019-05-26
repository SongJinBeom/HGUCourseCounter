package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;


public class Student {
	private String studentId =  null;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>();
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String, Integer>(); 
	
	public Student(String studentId) {
		this.studentId = studentId;
	}
	
	
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);	
	}//this method add the parameter course to ArrayList courseTaken
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		String key;
		int entire =1;
		
		for(Course c1 : coursesTaken) {
			key = c1.getYearTaken() +"-"+ c1.getSemesterCourseTaken();
		
			if(!semestersByYearAndSemester.containsKey(key)) {
				semestersByYearAndSemester.put(key, entire);
				entire++;
			}
		}
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester){
		int count=0;
		HashMap<String,Integer> sBAS = getSemestersByYearAndSemester();
		
		for(Course c1 : coursesTaken) {
			String tempKey = c1.getYearTaken() + "-" + c1.getSemesterCourseTaken();
			
			int tempInfo = sBAS.get(tempKey);
			if(tempInfo == semester) {
				count++;
			}
		}
		return count;
	}
	
}
