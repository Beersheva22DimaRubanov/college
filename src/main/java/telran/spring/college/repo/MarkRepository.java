package telran.spring.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.spring.college.dto.IdName;
import telran.spring.college.entity.Mark;
import telran.spring.college.entity.SubjectType;

public interface MarkRepository extends JpaRepository<Mark, Integer> {
	
	List<Mark> findByStudentIdAndSubjectId(long studentId, String subjectId);
	
}
