package es.jdl.autoquiz.rest;

import es.jdl.autoquiz.dao.QuizMapper;
import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin") // TODO: will be changed when user control implemented
public class ManageRestService {
    @Autowired
    private QuizMapper dao;

    @PostMapping("/question")
    public Question saveQuestion(@RequestBody Question question) {
        if (question.getQuizId() == null)
            throw new IllegalArgumentException("quizId must be not empty");
        if (question.getId() == null) { // new question
            question.setId(QuizService.generateULID());
            insertQuestion(question);
        } else {
            // update if exists insert if not
            Question q = dao.selectQuestionById(question.getQuizId(), question.getId());
            if (q != null) { // delete & insert or update? ...
                dao.deleteFullQuestion(q.getQuizId(), q.getId());
            }
            insertQuestion(question);
        }
        return question;
    }

    protected void insertQuestion(Question q) {
        dao.insertQuestion(q);
        for (Answer a: q.getAnswers()) {
            a.setQuestionId(q.getId());
            a.setQuizId(q.getQuizId());
            dao.insertAnswer(a);
        }
    }
}
