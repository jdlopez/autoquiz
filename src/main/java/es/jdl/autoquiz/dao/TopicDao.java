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
		return ObjectifyService.ofy().load().type(Topic.class).filter ("courses", trialId).list();
	}

	public Topic save(Topic topic) {
		//Key<Topic> ret =
		ObjectifyService.ofy().save().entity(topic).now();
		return topic;
	}

	public void delete(Long topicId) {
		ObjectifyService.ofy().delete().type(Topic.class).id(topicId).now();
	}
	
	

}
