package telran.spring.college.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.SubjectDto;

@Entity
@Table(name="subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {
	@Id
	String id;
	String name;
	int hours;
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	SubjectType subjectType;
	@ManyToOne
	@JoinColumn(name ="lecturer_id", nullable = true)
	Lecturer lecturer;
	
	public SubjectDto build() {
		return new SubjectDto(id, name, hours, subjectType, lecturer == null? null: lecturer.id);
	}
	
	static public Subject of(SubjectDto subject) {
		return new Subject(subject.getId(), subject.getName(), subject.getHours(), 
				subject.getSubjectType(), null);
	}
}
