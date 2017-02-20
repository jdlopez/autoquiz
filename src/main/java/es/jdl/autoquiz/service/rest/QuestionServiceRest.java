package es.jdl.autoquiz.service.rest;

import es.jdl.autoquiz.dao.QuestionDao;
import es.jdl.autoquiz.domain.Question;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ddjlo on 20/02/2017.
 */
@RestController
@RequestMapping("/question")
public class QuestionServiceRest {

    private QuestionDao dao;

    @RequestMapping(value="", method = RequestMethod.POST)
    public Question save(@RequestBody Question question) {
        return dao.save(question);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public Question findById(@PathVariable("id") Long questionId) {
        return dao.selectById(questionId);
    }

    @RequestMapping(value="/topic/{topicId}", method = RequestMethod.GET)
    public List<Question> findByTopic(@PathVariable("topicId") Long topicId) {
        return dao.selectByTopicId(topicId);
    }


}
