package telran.spring.college.service;

import java.util.List;

import telran.spring.college.dto.IdName;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.StudentMark;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.entity.SubjectType;

public interface CollegeService {
	PersonDto addStudent(PersonDto person);
	PersonDto addLecturer(PersonDto person);
	SubjectDto addSubject(SubjectDto subject);
	MarkDto addMark(MarkDto mark);
	List<IdName> bestStudentsLecturer(long lecturerId, int nStudents);
	
	//list students (id and name) who have avg mark greater than avg mark of college and number marks not less than nMarksThreshold
	List<IdName> studentsAvgMarksGreaterCollegeAvg(int nMarksThreshold);
	
	List<StudentMark> studentsAvgMarks();
	
	SubjectDto updateHours(String subjectId, int hours);
	SubjectDto updateLecturer(String subjectId, Long lectureId);
	List<PersonDto> removeStudentsLessMarks(int nMarks);
	List<MarkDto> marksStudentSubject(long studentId, String subjectId);
	List<IdName> studentMarksSubject(SubjectType type, int mark);
	
}
