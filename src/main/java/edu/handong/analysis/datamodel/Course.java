package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVRecord;

public class Course {

	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course(CSVRecord courseInfo) {
		
		this.studentId = courseInfo.get(0);
		this.yearMonthGraduated =courseInfo.get(1);
		this.firstMajor = courseInfo.get(2);
		this.secondMajor = courseInfo.get(3);
		this.courseCode = courseInfo.get(4);
		this.courseName = courseInfo.get(5);
		this.courseCredit = courseInfo.get(6);
		this.yearTaken = Integer.parseInt(courseInfo.get(7));
		this.semesterCourseTaken = Integer.parseInt(courseInfo.get(8));
	}

	public void courseAnalysis() {
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getYearMonthGraduated() {
		return yearMonthGraduated;
	}

	public void setYearMonthGraduated(String yearMonthGraduated) {
		this.yearMonthGraduated = yearMonthGraduated;
	}

	public String getFirstMajor() {
		return firstMajor;
	}

	public void setFirstMajor(String firstMajor) {
		this.firstMajor = firstMajor;
	}

	public String getSecondMajor() {
		return secondMajor;
	}

	public void setSecondMajor(String secondMajor) {
		this.secondMajor = secondMajor;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseCredit() {
		return courseCredit;
	}

	public void setCourseCredit(String courseCredit) {
		this.courseCredit = courseCredit;
	}

	public int getYearTaken() {
		return yearTaken;
	}

	public void setYearTaken(int yearTaken) {
		this.yearTaken = yearTaken;
	}

	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}

	public void setSemesterCourseTaken(int semesterCourseTaken) {
		this.semesterCourseTaken = semesterCourseTaken;
	}

}
