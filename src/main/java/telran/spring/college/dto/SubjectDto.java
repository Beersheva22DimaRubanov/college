package telran.spring.college.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import telran.spring.college.entity.SubjectType;

@AllArgsConstructor
@Data
public class SubjectDto {
	String id;
	String name;
	int hours;
	SubjectType subjectType;
	Long lecturerId;
}
