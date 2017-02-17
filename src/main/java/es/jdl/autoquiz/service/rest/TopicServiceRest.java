package es.jdl.autoquiz.service.rest;

import com.googlecode.objectify.Key;
import es.jdl.autoquiz.dao.TopicDao;
import es.jdl.autoquiz.dao.TrialDao;
import es.jdl.autoquiz.domain.Topic;
import es.jdl.autoquiz.domain.Trial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping ("/topic")
public class TopicServiceRest {
	
	@Autowired
	private TopicDao dao;
	@Autowired
	private TrialDao daoTrial;

	@RequestMapping (value="{id}", method=RequestMethod.GET)
	public Topic findTopicDetail(@PathVariable("id") Long topicId) {
		return dao.selectById(topicId);
	}

	@RequestMapping (value="/trial/{trialId}/list", method=RequestMethod.GET)
	public List<Topic> findListTopics(@PathVariable("trialId") Long trialId) {
		return dao.selectByTrialId(trialId);
	}

	@RequestMapping (value="/trial/{trialId}/import", method=RequestMethod.POST)
	public String upload(@PathVariable("trialId") Long trialId, @RequestBody List<Topic> topicList) {
		Trial trial = daoTrial.selectById(trialId);
		List<Key<Trial>> courses = Arrays.asList(Key.create(trial));
        StringBuffer salida = new StringBuffer();
		for (Topic t: topicList) {
			t.setCourses(courses);
			dao.save(t);
			salida.append(t.getTopicId()).append(" ");
		}
		return salida.toString();
	}

	@RequestMapping (value="{id}", method=RequestMethod.DELETE)
	public Topic deleteTopic(@PathVariable("id") Long topicId) {
		return dao.delete(topicId);
	}

	@RequestMapping (value="", method=RequestMethod.POST)
	public Topic saveTopic(@RequestBody @Valid Topic topic) {
		return dao.save(topic);
	}

}
