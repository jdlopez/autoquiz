package es.jdl.autoquiz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import es.jdl.autoquiz.domain.Trial;

@Repository
public class TrialDao {

	public Trial save(Trial trial) {
		Key<Trial> ret = ObjectifyService.ofy().save().entity(trial).now();
		// https://github.com/objectify/objectify/wiki/BasicOperations
		// seguroooo??
		return trial;
	}

	public List<Trial> selectAll() {
		return ObjectifyService.ofy().load().type(Trial.class).list();
	}

	public void delete(Long trialId) {
		ObjectifyService.ofy().delete().type(Trial.class).id(trialId).now();
	}

	public Trial selectById(Long trialId) {
		return ObjectifyService.ofy().load().type(Trial.class).id(trialId).now();
	}

}
