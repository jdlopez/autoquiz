package es.jdl.autoquiz.dao;

import com.googlecode.objectify.ObjectifyService;
import es.jdl.autoquiz.domain.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TopicDao {

	public Topic selectById(Long topicId) {
		return ObjectifyService.ofy().load().type(Topic.class).id(topicId).now();
	}

	public List<Topic> selectByTrialId(Long trialId) {
		return ObjectifyService.ofy().load().type(Topic.class).list(); // filter ("courses", Key.create(Trial.class, trialId)).
	}

	public Topic save(Topic topic) {
		//Key<Topic> ret =
		ObjectifyService.ofy().save().entity(topic).now();
		return topic;
	}

	public Topic delete(Long topicId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
