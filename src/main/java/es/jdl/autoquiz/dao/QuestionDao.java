package es.jdl.autoquiz.dao;

import com.googlecode.objectify.ObjectifyService;
import es.jdl.autoquiz.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ddjlo on 20/02/2017.
 */
@Service
public class QuestionDao {

    public Question save(Question question) {
        ObjectifyService.ofy().save().entity(question).now();
        return question;
    }

    public Question selectById(Long questionId) {
        return ObjectifyService.ofy().load().type(Question.class).id(questionId).now();
    }

    public List<Question> selectByTopicId(Long topicId) {
        return ObjectifyService.ofy().load().type(Question.class).ancestor(topicId).list();
    }

    public List<Question> selectByTrial(Long trialId) {
        // todo: filter trialId trial->topic->question
        return ObjectifyService.ofy().load().type(Question.class).list();
    }
}
