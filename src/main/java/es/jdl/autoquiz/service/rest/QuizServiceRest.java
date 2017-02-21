package es.jdl.autoquiz.service.rest;

import es.jdl.autoquiz.dao.QuestionDao;
import es.jdl.autoquiz.dao.UserDao;
import es.jdl.autoquiz.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ddjlo on 21/02/2017.
 */
@RestController
@RequestMapping("/quiz")
public class QuizServiceRest {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public List<Long> generateRandom(@PathVariable("trialId") Long trialId, HttpServletRequest request) {
        List<Question> questions = questionDao.selectByTrial(trialId);
        ArrayList<Long> ids = new ArrayList<>(questions.size());
        for (Question q: questions)
            ids.add(q.getQuestionId());
        Collections.shuffle(ids, new Random());
        return ids;
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public void storeQuiz(@RequestBody List<Long> questionIds, HttpServletRequest request) {
        userDao.saveQuestionList(request.getUserPrincipal().getName(), questionIds);
    }
}
