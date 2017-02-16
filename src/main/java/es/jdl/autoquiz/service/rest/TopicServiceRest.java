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

	@RequestMapping (value="/trial/{trialId}/list", method=RequestMethod.GET)
	public List<Topic> findListTopics(@PathVariable("trialId") Long trialId) {
		return dao.selectByTrialId(trialId);
	}

	@RequestMapping (value="", method=RequestMethod.POST)
	public Topic saveTopic(@RequestBody @Valid Topic topic) {
		return dao.save(topic);
	}

	@RequestMapping (value="{id}", method=RequestMethod.DELETE)
	public Topic deleteTopic(@PathVariable("id") Long topicId) {
		return dao.delete(topicId);
	}

	@RequestMapping (value="/import", method=RequestMethod.POST)
	public Topic saveTopic(@RequestBody @Valid Topic topic) {
		return dao.save(topic);
	}

}
