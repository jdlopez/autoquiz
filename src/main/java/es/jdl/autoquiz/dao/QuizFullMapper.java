package es.jdl.autoquiz.dao;

import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuizFullMapper {

    Quiz selectQuizFullById(String id);

    Question selectQuestion(String quizId, String questionId);
}
