package telran.spring.college;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.constraints.AssertFalse.List;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.entity.Lecturer;
import telran.spring.college.entity.Mark;
import telran.spring.college.entity.Student;
import telran.spring.college.entity.SubjectType;
import telran.spring.college.repo.LecturerRepository;
import telran.spring.college.repo.StudentsRepository;
import telran.spring.college.service.CollegeServiceImpl;
import telran.spring.exceptions.NotFoundException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeServiceAddTest {
	private static final long LECTURER_ID = 123;
	private static final long STUDENT_ID = 124;
	
	@Autowired
	CollegeServiceImpl service;
	PersonDto lecturerDto = new PersonDto(LECTURER_ID, "Vasya", LocalDate.now().toString(), null, null);
	PersonDto lecturerDto1 = new PersonDto(null, "Sara", "2000-01-01",null, null);
	PersonDto studentDto = new PersonDto(STUDENT_ID, "Vasya", LocalDate.now().toString(), null, null);
	PersonDto studentDto1 = new PersonDto(null, "Yosef", "2000-01-01", null, null);
	SubjectDto subjectDto = new SubjectDto("S1", "Java", 100, SubjectType.BACK_END, null);
	SubjectDto subjectDto1 = new SubjectDto("S2", "Java", 100, SubjectType.BACK_END, LECTURER_ID);
	SubjectDto subjectDto2 = new SubjectDto("S3", "Java", 100, SubjectType.BACK_END, LECTURER_ID + 10);
	SubjectDto subjectDto3 = new SubjectDto("S1", "Java", 100, SubjectType.BACK_END, null);
 
	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	void addLecturerTest() {
		PersonDto lecturerActual = service.addLecturer(lecturerDto);
		assertEquals(LECTURER_ID, lecturerActual.getId());
		PersonDto lecturerActual1 = service.addLecturer(lecturerDto1);
		assertEquals("Sara", lecturerActual1.getName());
		assertThrows(Exception.class, () -> service.addLecturer(lecturerDto));
	}
	
	@Test
	@Order(2)
	void addStudentTest() {
		PersonDto studentActual = service.addStudent(studentDto);
		assertEquals(STUDENT_ID, studentActual.getId());
		PersonDto studentActual1 = service.addStudent(studentDto1);
		assertEquals("Yosef", studentActual1.getName());
		assertThrows(Exception.class, () -> service.addStudent(studentDto));
	}
	
	@Test
	@Order(3)
	void addSubjectTest() {
		SubjectDto subjectActual = service.addSubject(subjectDto);
		assertEquals(subjectDto.getId(), subjectActual.getId());
		SubjectDto subjectActual1 = service.addSubject(subjectDto1);
		assertEquals(subjectDto1.getId(), subjectActual1.getId());
		assertThrowsExactly(NotFoundException.class, () -> service.addSubject(subjectDto2));
		assertThrowsExactly(IllegalStateException.class, () -> service.addSubject(subjectDto3));
	}
	
	@Test
	@Order(4)
	void addMarkTest() {
		MarkDto markDto = new MarkDto(null, STUDENT_ID, subjectDto.getId(), 100); 
		MarkDto markDtoNoStudent = new MarkDto(null, STUDENT_ID + 10, subjectDto.getId(), 100); 
		MarkDto markDtoNoSubject = new MarkDto(null, STUDENT_ID, "XXX", 100); 
		MarkDto markDtoActual = service.addMark(markDto);
		assertEquals(1, markDtoActual.getId());
		assertThrowsExactly(NotFoundException.class, () -> service.addMark(markDtoNoStudent));
		assertThrowsExactly(NotFoundException.class, () -> service.addMark(markDtoNoSubject));
	}

}
