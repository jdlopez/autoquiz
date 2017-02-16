package es.jdl.autoquiz.dao;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.ObjectifyService;

import es.jdl.autoquiz.domain.Topic;

@Repository
public class TopicDao {

	public Topic selectById(Long topicId) {
		return ObjectifyService.ofy().load().type(Topic.class).id(topicId).now();
	}

}
