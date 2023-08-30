package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.spring.college.dto.IdName;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.StudentMark;
import telran.spring.college.entity.SubjectType;
import telran.spring.college.service.CollegeService;
@SpringBootTest
@Sql(scripts = {"college-read-test-script.sql"})
class CollegeServiceReadTests {
	
	@Autowired
	CollegeService service;
		@Test
		
		void bestStudentsLecturerTest() {
			List<IdName> actual = service.bestStudentsLecturer(321, 2);
			assertEquals(2, actual.size());
			assertEquals(127, actual.get(0).getId());
			assertEquals("Rivka", actual.get(0).getName());
			assertEquals(123, actual.get(1).getId());
			assertEquals("Vasya", actual.get(1).getName());
		}
		
		@Test
		void bestStudentsAvgGreaterTest() {
			List<IdName> actual = service.studentsAvgMarksGreaterCollegeAvg(2);
			assertEquals(2, actual.size());
			assertEquals(127, actual.get(0).getId());
			assertEquals("Rivka", actual.get(0).getName());
			assertEquals(123, actual.get(1).getId());
			assertEquals("Vasya", actual.get(1).getName());
		}
		
		@Test
		void studentsMarks() {
			List<StudentMark> studentsMarks = service.studentsAvgMarks();
			assertEquals(5, studentsMarks.size());
			assertEquals(127, studentsMarks.get(0).getId());
			assertEquals("Rivka", studentsMarks.get(0).getName());
			assertEquals(100, studentsMarks.get(0).getMark());
			assertEquals(126, studentsMarks.get(4).getId());
			assertEquals("David", studentsMarks.get(4).getName());
			assertEquals(0, studentsMarks.get(4).getMark());
		}
	
	@Test
	void markStudentSubjectTest() {
		List<MarkDto> marks = service.marksStudentSubject(124, "S2");
		assertEquals(70,  marks.get(0).getMark());
	}
	
	@Test
	void studentsMarksSubjectTest() {
		List<IdName> students = service.studentMarksSubject(SubjectType.BACK_END, 90);
		assertEquals(2, students.size());
		assertEquals("Vasya", students.get(0).getName());
		assertEquals("Rivka", students.get(1).getName());
	}

}
