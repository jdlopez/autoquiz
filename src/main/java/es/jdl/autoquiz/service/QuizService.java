package es.jdl.autoquiz.service;

import com.github.f4b6a3.ulid.UlidCreator;
import es.jdl.autoquiz.dao.QuizMapper;
import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class QuizService {

    public static String generateULID() {
        return UlidCreator.getUlid().toString();
    }

    public void quizSetIDs(Quiz quiz) {
        if (quiz.getId() == null)
            quiz.setId(generateULID());
        for (Question q: quiz.getQuestions()) {
            if (q.getId() == null)
                q.setId(UlidCreator.getUlid().toString());
            if (q.getAnswernumbering() == null)
                q.setAnswernumbering(quiz.getAnswernumbering());
            if (q.getShuffleanswers() == null)
                q.setShuffleanswers(quiz.isShuffleanswers());
            q.setQuizId(quiz.getId());
            for (Answer a: q.getAnswers()) {
                if (a.getId() == null)
                    a.setId(generateULID());
                a.setQuestionId(q.getId());
                a.setQuizId(quiz.getId());
            } // for answer
        } // for question
    }

    @Autowired
    private QuizMapper dao;

    public void saveQuizDeep(Quiz quiz) {
        quizSetIDs(quiz);
        dao.insertQuiz(quiz);
        if (quiz.getTags() != null && !quiz.getTags().isEmpty())
            for (String t: quiz.getTags())
                addTagToQuiz(quiz.getId(), t);
        for (Question q: quiz.getQuestions()) {
            dao.insertQuestion(q);
            for (Answer a: q.getAnswers()) {
                dao.insertAnswer(a);
            } // answer
            if (q.getTags() != null && !q.getTags().isEmpty())
                for (String t: q.getTags())
                    addTagToQuestion(quiz.getId(), q.getId(), t);

        } // question
    }

    public void addTagToQuiz(String quizId, String tag) {
        try {
            dao.insertTag(tag, null);
        } catch (RuntimeException e) {
            // already exists
            if (e.getCause() instanceof SQLException)
                System.out.println("sql exception!");
        }
        try {
            dao.insertQuizTag(quizId, tag);
        } catch (RuntimeException e) {
            // already exists
        }
    }

    public void addTagToQuestion(String quizId, String questionId, String tag) {
        try {
            dao.insertTag(tag, null);
        } catch (PersistenceException e) {
            // already exists
        }
        try {
            dao.insertQuestionTag(quizId, questionId, tag);
        } catch (PersistenceException e) {
            // already exists
        }
    }

    public Quiz findQuizById(String quizId) {
        return dao.selectQuizById(quizId);
    }

    public int markSingleAnswer(String quizId, String questionId, String answerId, String feedbackOk,
                                 Integer fractionOk, String feedbackError, Integer fractionError) {
        // first mark all as error
        dao.updateAllAnswersFromQuestion(quizId, questionId, fractionError, feedbackError);
        return dao.updateSingleAnswer(quizId, questionId, answerId, fractionOk, feedbackOk);

    }

    public List<Quiz> findUserQuiz(String userName) {
        return dao.selectQuizByUser(userName);
    }
}
