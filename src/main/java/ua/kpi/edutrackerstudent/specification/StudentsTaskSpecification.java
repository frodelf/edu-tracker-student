package ua.kpi.edutrackerstudent.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ua.kpi.edutrackerentity.entity.StudentsTask;
import ua.kpi.edutrackerstudent.dto.studenttask.StudentsTaskRequestForFilter;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;

public class StudentsTaskSpecification implements Specification<StudentsTask> {
    private StudentsTaskRequestForFilter filter;
    private Long studentId;

    public StudentsTaskSpecification(StudentsTaskRequestForFilter studentsTaskRequestForFilter, Long id) {
        this.filter = studentsTaskRequestForFilter;
        this.studentId = id;
    }

    @Override
    public Predicate toPredicate(Root<StudentsTask> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(filter.getCourseId())) {
            predicates.add(criteriaBuilder.equal(root.get("task").get("course").get("id"), filter.getCourseId()));
        }
        if (nonNull(filter.getTaskId())) {
            predicates.add(criteriaBuilder.equal(root.get("task").get("id"), filter.getTaskId()));
        }
        if (nonNull(filter.getStatus())) {
            predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
        }
        predicates.add(criteriaBuilder.equal(root.get("student").get("id"), studentId));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
