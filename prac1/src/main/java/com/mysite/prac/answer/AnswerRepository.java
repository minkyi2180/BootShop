package com.mysite.prac.answer;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.prac.question.Question;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	 @Query("select q "
		        + "from Answer q "
		        + "join SiteUser u on q.author=u "
		        + "where u.username = :username "
		        + "order by q.createDate desc ")
		    List<Answer> findCurrentAnswer(@Param("username") String username,
		        Pageable pageable);
}
