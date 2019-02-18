package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.repository.TeamRepository;
import com.deloitte.fiddler.service.TeamService;

@Component
public class TeamServiceImpl implements TeamService {

	TeamRepository tr;


	@Autowired
	public TeamServiceImpl(TeamRepository trL) {
		this.tr = trL;

	}

	public StandardTeamSchema getTeamByID(String id) throws NoSuchElementException {
		StandardTeamSchema proj = null;
			proj = this.tr.findByteamId(id);
			if(proj == null) {
				throw new NoSuchElementException("cannot find team with id " + id);
			}
		return proj;
	}

	public boolean deleteTeam(String id) {
		this.tr.deleteById(id);
		return true;

	}

	@Override
	public StandardTeamSchema createTeam(StandardTeamSchema p) {

			p.setTeamId(java.util.UUID.randomUUID().toString());
			return this.tr.save(p);

		
	}

	@Override
	public StandardTeamSchema updateTeam(StandardTeamSchema fp) {
		return this.tr.save(fp);

	}

	@Override
	public List<StandardTeamSchema> getAllTeams() {
		return this.tr.findAll();
	}


}
