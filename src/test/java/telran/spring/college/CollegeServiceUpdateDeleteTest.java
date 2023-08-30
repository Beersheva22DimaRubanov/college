package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.college.dto.PersonDto;
import telran.spring.college.entity.Subject;
import telran.spring.college.repo.StudentsRepository;
import telran.spring.college.repo.SubjectRepository;
import telran.spring.college.service.CollegeService;
import telran.spring.exceptions.NotFoundException;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeServiceUpdateDeleteTest {
	static final String SUBJECT_ID = "S1";
	static final Long LECTURER_ID = 421L;
	private static final int HOURS = 200;
	private static final Long STUDENT_REMOVED_ID_0 = 126L;
	private static final Long STUDENT_REMOVED_ID_1 = 124L;
	@Autowired
	CollegeService collegeService;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	StudentsRepository studentRepository;
	
	@Test
	@Order(1)
	@Sql(scripts = {"college-read-test-script.sql"})
	void updateHours() {
		collegeService.updateHours(SUBJECT_ID, HOURS);
		assertThrows(NotFoundException.class, ()-> collegeService.updateHours(SUBJECT_ID+10, HOURS));
		}
	
	@Test
	@Order(3)
	@Sql(scripts = {"college-read-test-script.sql"})
	void updateLecturer() {
		collegeService.updateLecturer(SUBJECT_ID, LECTURER_ID);
		assertThrows(NotFoundException.class, ()-> collegeService.updateHours(SUBJECT_ID+10, HOURS));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly = true)
	void updateHoursTest() {
		Subject subject = subjectRepository.findById(SUBJECT_ID).get();
		assertEquals(HOURS, subject.getHours());
	}
	
	@Test
	@Order(4)
	@Transactional(readOnly = true)
	void updateLecturerTest() {
		Subject subject = subjectRepository.findById(SUBJECT_ID).get();
		assertEquals(LECTURER_ID, subject.getLecturer().getId());
	}
	
	@Test
	@Order(5)
	void removeStudentsNoMark() {
		List<PersonDto> studentsRemoved = collegeService.removeStudentsLessMarks(1);
		assertEquals(1, studentsRemoved.size());
	}
	
	@Test
	@Order(6)
	@Transactional(readOnly = true)
	void removeStudentsNoMarkTest() {
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_0).orElse(null));
	}
	
	@Test
	@Order(7)
	@Sql(scripts = {"college-read-test-script.sql"})
	@Transactional(readOnly = false, propagation = Propagation.NEVER)
	void removeStudentsLessMark() {
		List<PersonDto> studentRemoved = collegeService.removeStudentsLessMarks(3);
		assertEquals(2, studentRemoved.size());
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_0).orElse(null));
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_1).orElse(null));
	}

}
