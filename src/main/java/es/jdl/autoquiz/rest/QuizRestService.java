package es.jdl.autoquiz.rest;

import es.jdl.autoquiz.dao.QuizFullMapper;
import es.jdl.autoquiz.dao.QuizMapper;
import es.jdl.autoquiz.domain.NameCounter;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class QuizRestService {

    @Autowired
    private QuizMapper dao;
    @Autowired
    private QuizFullMapper daoFull;

    @GetMapping("/quizzes")
    public List<Quiz> getPublicQuiz() {
        return dao.selectQuizPublic();
    }

    @GetMapping("/quiz/{id}")
    public Quiz getFullQuiz(@PathVariable("id") String id) {
        return daoFull.selectQuizFullById(id);
    }

    @GetMapping("/quiz/tag/{tag}")
    public List<Quiz> getTaggedQuizzes(@PathVariable("tag") String tag) {
        return dao.selectQuizByTag(tag);
    }

    @GetMapping("/quiz/tags")
    public List<NameCounter> getQuizCountGroupByTag() {
        return dao.selectQuizCountGroupByTag();
    }

    @GetMapping("/question/tag/{tag}")
    public Question findRandomQuestionByTag(@PathVariable("tag") String tag) {
        // to avoid randomized search in db ... first get all
        List<Question> qLst = dao.selectQuestionByTag(tag);
        // then choose one random
        int randomIdx = ThreadLocalRandom.current().nextInt(0, qLst.size());
        Question q = qLst.get(randomIdx);
        q.setAnswers(dao.selectAnsers(q.getQuizId(), q.getId()));
        return q;
    }

    @GetMapping("/quiz/{quizId}/question/{questionId}")
    public Question findQuestionById(@PathVariable("quizId") String quizId, @PathVariable("questionId") String questionId) {
        return daoFull.selectQuestion(quizId, questionId);
    }
}
