package es.jdl.autoquiz.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.jdl.autoquiz.dao.TrialDao;
import es.jdl.autoquiz.domain.Trial;

@RestController
@RequestMapping ("/trial")
public class TrialServiceRest {
	
	@Autowired
	private TrialDao dao;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public Trial saveTrial(@RequestBody Trial trial, HttpServletRequest request) {
		dao.save(trial);
		return trial;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	// todo: poner filtros
	public List<Trial> list() {
		return dao.selectAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long trialId) {
		dao.delete(trialId);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Trial detalle(@PathVariable("id") Long trialId) {
		return dao.selectById(trialId);
	}

}
