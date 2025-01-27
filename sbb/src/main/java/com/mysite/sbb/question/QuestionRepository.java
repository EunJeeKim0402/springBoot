package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	Question findBySubject(String subject); // 엔티티의 subject값으로 데이터 조회
	Question findBySubjectAndContent(String subject, String content); // subject, content 열과 일치하는 데이터 조회
	List<Question> findBySubjectLike(String subject); // 특정 문자열을 포함하는 데이터 조회(like)
	Page<Question> findAll(Pageable pageable);
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	// 기존의 검색 jpa를 쿼리로 구현
	@Query("select "
			+ "distinct q "
			+ "from Question q "
			+ "left outer join SiteUser u1 on q.author = u1 "
			+ "left outer join Answer a on a.question = q "
			+ "left outer join SiteUser u2 on a.author = u2 "
			+ "where "
			+ "    q.subject like %:kw% "
			+ "    or q.content like %:kw% "
			+ "    or u1.username like %:kw% "
			+ "    or a.content like %:kw% "
			+ "    or u2.username like %:kw% ")
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
