package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamMemberObject;
import com.deloitte.fiddler.common.TeamRoleObject;
import com.deloitte.fiddler.repository.TeamRepository;
import com.deloitte.fiddler.service.PersonService;
import com.deloitte.fiddler.service.TeamService;

@Component
public class TeamServiceImpl implements TeamService {

	TeamRepository tr;
	
	PersonService ps;


	@Autowired
	public TeamServiceImpl(TeamRepository trL, PersonService psL) {
		this.tr = trL;
		this.ps = psL;

	}

	public StandardTeamSchema getTeamByID(String id) throws NoSuchElementException {
		Optional<StandardTeamSchema> team  = this.tr.findById(id);
			if(!team.isPresent()) {
				throw new NoSuchElementException("cannot find team with id " + id);
			}
		return team.get();
	}

	public boolean deleteTeam(String id) {
		this.tr.deleteById(id);
		return true;

	}

	@Override
	public StandardTeamSchema createTeam(StandardTeamSchema p) {

		p.setTeamId(null);
		return this.tr.save(p);

		
	}

	public StandardTeamSchema updateTeam(StandardTeamSchema fp) {
		this.getTeamByID(fp.getTeamId());
		return this.tr.save(fp);

	}

	public List<StandardTeamSchema> getAllTeams() {
		return this.tr.findAll();
	}
	
	public TeamRoleObject getRoleFromTeam(String teamId, int roleIndex) {
		return this.getTeamByID(teamId).getTeamRoleList().get(roleIndex);
	}

	public StandardTeamSchema addPersonToRole(String teamId, int roleIndex, String personId) {
		
		
		
		TeamMemberObject tm = this.ps.getPersonById(personId);
		StandardTeamSchema team = this.getTeamByID(teamId);
		TeamRoleObject roleObject = team.getTeamRoleList().get(roleIndex);
		if(!roleObject.getTeamMembersInRole().stream().anyMatch(a -> a.getTeamMemberID().equals(tm.getTeamMemberID()))) {

			roleObject.getTeamMembersInRole().add(tm);
			return this.tr.save(team);
		} else {
			throw new NoSuchElementException(tm.getTeamMemberName() + " is already in the role " + roleObject.getTeamRole());
		}
	}




}
