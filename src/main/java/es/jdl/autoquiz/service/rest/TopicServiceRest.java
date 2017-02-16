package es.jdl.autoquiz.service.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.jdl.autoquiz.dao.TopicDao;
import es.jdl.autoquiz.domain.Topic;

@RestController
@RequestMapping ("/topic")
public class TopicServiceRest {
	
	@Autowired
	private TopicDao dao;

	@RequestMapping (value="{id}", method=RequestMethod.GET)
	public Topic findTopicDetail(@PathVariable("id") Long topicId) {
		return dao.selectById(topicId);
	}

	@RequestMapping (value="/list", method=RequestMethod.GET)
	public List<Topic> findListTopics(@PathVariable("trialId") Long trialId) {
		return null;
	}

	@RequestMapping (value="", method=RequestMethod.POST)
	public Topic saveTopic(@PathVariable("trialId") Long trialId, @RequestBody @Valid Topic topic) {
		return null;
	}

	@RequestMapping (value="{id}", method=RequestMethod.GET)
	public Topic deleteTopic(@PathVariable("trialId") Long trialId, @PathVariable("id") Long topicId) {
		return null;
	}

}
