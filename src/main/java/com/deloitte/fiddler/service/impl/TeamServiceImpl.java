package com.deloitte.fiddler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.model.StandardTeamSchema;
import com.deloitte.fiddler.repository.TeamRepository;
import com.deloitte.fiddler.service.TeamService;
import com.deloitte.fiddler.util.JSONvalidator;
import com.deloitte.fiddler.util.WebCaller;

@Component
public class TeamServiceImpl implements TeamService {
	
	TeamRepository tr;

	WebCaller wc;

	JSONvalidator jsv;

	@Autowired
	public TeamServiceImpl(TeamRepository trL, WebCaller wcL, JSONvalidator jsvL) {
		this.tr = trL;
		this.wc = wcL;
		this.jsv = jsvL;

	}

	@Override
	public StandardTeamSchema createTeam(String url) {
		if (this.jsv.validateJSON(StandardTeamSchema.class, wc.getWebEntity(Object.class, url))) {
			StandardTeamSchema s = wc.getWebEntity(StandardTeamSchema.class, url);
			s.setTeamId(java.util.UUID.randomUUID().toString());
			return this.tr.save(s);
		} else {
			return null;
		}
	}

	@Override
	public List<StandardTeamSchema> getAllTeams() {
		return this.tr.findAll();
	}

	@Override
	public StandardTeamSchema getTeamByID(String id) {
		return this.tr.findByteamId(id);
	}

	@Override
	public StandardTeamSchema updateTeam(StandardTeamSchema teamSchema) {
		return this.tr.save(teamSchema);
	}

	@Override
	public boolean deleteTeam(String id) {
		this.tr.deleteById(id);
		return true;
	}

}
