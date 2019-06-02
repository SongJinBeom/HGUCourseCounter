package edu.handong.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String, Student> students;

	String inPath;
	String outPath;
	String analysis;
	String courseCode;
	String startYear;
	String endYear;
	boolean help;
	String courseName;

	/**
	 * This method runs our analysis logic to save the number courses taken by each
	 * student per semester in a result file. Run method must not be changed!!
	 * 
	 * @param args
	 */

	public void run(String[] args) {
		Options options = createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				printHelp(options);
				System.exit(0);
				return;

			}
//			try {
//				// when there are not enough arguments from CLI, it throws the
//				// NotEnoughArgmentException which must be defined by you.
//				if (args.length < 2)
//					throw new NotEnoughArgumentException();
//
//			} catch (NotEnoughArgumentException e) {
//				System.out.println(e.getMessage());
//				System.exit(0);
//			}

			String dataPath = inPath; // csv file to be analyzed
			String resultPath = outPath; // the file path where the results are saved.
			ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);

			students = loadStudentCourseRecords(lines, startYear, endYear);

			// To sort HashMap entries by key values so that we can save the results by
			// student ids in ascending order.
			Map<String, Student> sortedStudents = new TreeMap<String, Student>(students);

			if (analysis.equals("1")) { // a == 1 option

				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);

				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath);

			} else // a == 2 option
			{
				// Year,Semester,CourseCode, CourseName,TotalStudents,StudentsTaken,Rate

				HashMap<String, Integer> result = new HashMap<String, Integer>();
				HashMap<String, Integer> analysisResult = new HashMap<String, Integer>();
				ArrayList<String> linesToBeSaved = new ArrayList<String>();

				for (CSVRecord temp : lines) {
					if (temp.get(4).contentEquals(courseCode)) {
						courseName = temp.get(5);
						break;
						// courseName get
					}
				}

				result = inputCourse(courseCode, sortedStudents);
				analysisResult = analysisCourse(sortedStudents, result);

				linesToBeSaved = finalResult(result, analysisResult);
				Utils.writeAFile(linesToBeSaved, resultPath);
			}
		}
	}

	private HashMap<String, Integer> inputCourse(String courseCode, Map<String, Student> sortedStudents) {

		HashMap<String, Integer> result = new HashMap<String, Integer>();

		Student s1 = new Student(null);

		for (String key : sortedStudents.keySet()) {
			s1 = sortedStudents.get(key);
			for (Course c1 : s1.getCoursesTaken()) {

				String tempString = c1.getYearTaken() + "-" + c1.getSemesterCourseTaken(); // 2002-1
				if (c1.getCourseCode().equals(courseCode)) {
					if (result.isEmpty()) {
						result.put(tempString, 1);
					} else {
						if (result.containsKey(tempString)) {
							result.put(tempString, result.get(tempString) + 1);
						} else {
							result.put(tempString, 1);
						}
					}
				}
			}
		}

		return result;
	}

	private HashMap<String, Integer> analysisCourse(Map<String, Student> sortedStudents,
			HashMap<String, Integer> result) {

		HashMap<String, Integer> analysisResult = new HashMap<String, Integer>();

		for (String resultKey : result.keySet()) {
			analysisResult.put(resultKey, 0);
			for (String studentKey : sortedStudents.keySet()) {
				if (sortedStudents.get(studentKey).getSemestersByYearAndSemester().containsKey(resultKey)) {
					analysisResult.put(resultKey, analysisResult.get(resultKey) + 1);
				}
			}
		}

		return analysisResult;
	}

	private ArrayList<String> finalResult(HashMap<String, Integer> result, HashMap<String, Integer> analysisResult) {
		Map<String, Integer> finalResult = new TreeMap<String, Integer>(result);

		ArrayList<String> finalArray = new ArrayList<String>();

		String category = "Year,Semester,CourseCode, CourseName,TotalStudents,StudentsTaken,Rate";

		finalArray.add(category);

		for (String resultKey : finalResult.keySet()) {
			float rate = ((float) result.get(resultKey) / (float) analysisResult.get(resultKey)) * 100;

			String tempString = resultKey.split("-")[0] + "," + resultKey.split("-")[1] + "," + courseCode + ","
					+ courseName + "," + analysisResult.get(resultKey) + "," + result.get(resultKey) + ","
					+ String.format("%.1f", rate) + "%";
			finalArray.add(tempString);
		}

		return finalArray;
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a
	 * student id and the corresponding object is an instance of Student. The
	 * Student instance have all the Course instances taken by the student.
	 * 
	 * @param lines
	 * @return
	 */

	private HashMap<String, Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines, String startyear,
			String endyear) {

		// TODO: Implement this method
		HashMap<String, Student> tempHashMap = new HashMap<String, Student>();

		Student student1 = null;
		Course course1 = null;

		for (CSVRecord tempString : lines) {
			course1 = new Course(tempString);

			int start = Integer.parseInt(startYear);
			int end = Integer.parseInt(endyear);
			int tempYear = course1.getYearTaken();

			if (tempYear >= start && tempYear <= end) {
				if (!tempHashMap.containsKey(course1.getStudentId())) {
					student1 = new Student(course1.getStudentId());
				}

				student1.addCourse(course1);
				tempHashMap.put(course1.getStudentId(), student1);
			}
		}

		return tempHashMap; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each
	 * semester. The result file look like this: StudentID,
	 * TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9 0001,14,2,8 ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In
	 * the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */

	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {

		// TODO: Implement this method
		ArrayList<String> tempArrayList = new ArrayList<String>();
		String category = "Student,TotalNumberOfSemestersRegistered,Semester,NumCoursesTakenInTheSemester";

		tempArrayList.add(category);
		Student s1 = new Student(null);

		String info = null;
		int semester = 1;

		for (String key : sortedStudents.keySet()) {

			semester = 1;
			s1 = sortedStudents.get(key);
			while (semester <= s1.getSemestersByYearAndSemester().size()) {

				info = key + "," + s1.getSemestersByYearAndSemester().size() + "," + semester + ","
						+ s1.getNumCourseInNthSementer(semester);

				tempArrayList.add(info);

				semester++;
			}
		}

		return tempArrayList; // do not forget to return a proper variable.
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);

			inPath = cmd.getOptionValue("i");
			outPath = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			courseCode = cmd.getOptionValue("c");
			startYear = cmd.getOptionValue("s");
			endYear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	private Options createOptions() {
		// TODO Auto-generated method stub
		Options options = new Options();

		options.addOption(Option.builder("i").longOpt("input").desc("Set an input file path").hasArg()
				.argName("Input Path").required().build());

		options.addOption(Option.builder("o").longOpt("output").desc("Set an output file path").hasArg()
				.argName("Output Path").required().build());

		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year").hasArg()
				.argName("Analysis option").required().build());

		options.addOption(Option.builder("c").longOpt("course").desc("Course code for '-a 2' option").hasArg()
				.argName("course code")
				// .required()
				.build());

		options.addOption(Option.builder("s").longOpt("startyear").desc("Set the start year for analysis e.g., -s 2002")
				.hasArg().argName("Start year for analysis").build());

		options.addOption(Option.builder("e").longOpt("endyear").desc("Set the end year for analysis e.g., -e 2005")
				.hasArg().argName("End year for analysis").build());

		options.addOption(Option.builder("h").longOpt("help").desc("Show a Help page")
				// .hasArg()
				.argName("Path name to display")
				// .required()
				.build());

		return options;
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = "";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}
