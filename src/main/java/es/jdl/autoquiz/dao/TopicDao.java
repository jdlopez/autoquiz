package es.jdl.autoquiz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.ObjectifyService;

import es.jdl.autoquiz.domain.Topic;

@Repository
public class TopicDao {

	public Topic selectById(Long topicId) {
		return ObjectifyService.ofy().load().type(Topic.class).id(topicId).now();
	}

	public List<Topic> selectByTrialId(Long trialId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Topic save(Topic topic) {
		// TODO Auto-generated method stub
		return null;
	}

	public Topic delete(Long topicId) {
		// TODO Auto-generated method stub
		return null;
	}

}
