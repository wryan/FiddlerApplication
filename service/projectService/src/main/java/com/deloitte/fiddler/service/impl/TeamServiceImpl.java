package com.deloitte.fiddler.service.impl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamRoleObject;
import com.deloitte.fiddler.service.ProjectService;
import com.deloitte.fiddler.service.TeamService;

@Component
public class TeamServiceImpl implements TeamService {

	ProjectService ps;

	@Autowired
	public TeamServiceImpl(ProjectService psL) {
		this.ps = psL;

	}

	public TeamRoleObject getRoleFromTeam(String teamId, int roleIndex) {
		return this.ps.getProjectByID(teamId).getTeam().getTeamRoleList().get(roleIndex);
	}

	public StandardTeamSchema addPersonToRole(String projectId, int roleIndex, String personId) {
		System.out.println("here "+ projectId + " - " + roleIndex + " - " + personId);
		StandardProjectInformationSchema proj = this.ps.getProjectByID(projectId);
		TeamRoleObject roleObject = proj.getTeam().getTeamRoleList().get(roleIndex);
		if (!roleObject.getTeamMembersInRole().stream().anyMatch(a -> a.equals(personId))) {
			System.out.println("blah "+ projectId + " - " + roleIndex + " - " + personId);

			roleObject.getTeamMembersInRole().add(personId);
			return this.ps.updateProject(proj).getTeam();
		} else {
			throw new NoSuchElementException(personId + " is already in the role " + roleObject.getTeamRole());
		}
	}

	public StandardTeamSchema removePersonFromRole(String projectId, int roleIndex, String personId) {
		StandardProjectInformationSchema proj = this.ps.getProjectByID(projectId);
		TeamRoleObject roleObject = proj.getTeam().getTeamRoleList().get(roleIndex);
		if (roleObject.getTeamMembersInRole().stream().anyMatch(a -> a.equals(personId))) {

			roleObject.getTeamMembersInRole().remove(personId);
			return this.ps.updateProject(proj).getTeam();
		} else {
			throw new NoSuchElementException(personId + " is not in the role " + roleObject.getTeamRole());
		}
	}

	@Override
	public StandardTeamSchema updateTeam(String projectId, StandardTeamSchema fd) {
		StandardProjectInformationSchema proj = this.ps.getProjectByID(projectId);
		proj.setTeam(fd);
		return this.ps.updateProject(proj).getTeam();
	}

	@Override
	public StandardTeamSchema removeRole(String projectId, int roleIndex) {
		StandardProjectInformationSchema proj = this.ps.getProjectByID(projectId);
		proj.getTeam().getTeamRoleList().remove(roleIndex);
		return this.ps.updateProject(proj).getTeam();
	}

	@Override
	public StandardTeamSchema addRole(String projectId, String roleName) {
		TeamRoleObject newRole = new TeamRoleObject();
		newRole.setTeamRole(roleName);
		StandardProjectInformationSchema proj = this.ps.getProjectByID(projectId);
		proj.getTeam().getTeamRoleList().add(newRole);
		return this.ps.updateProject(proj).getTeam();
	}

}
