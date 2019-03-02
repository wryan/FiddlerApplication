package com.deloitte.fiddler.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamRoleObject;

public interface TeamService {
	
	public StandardTeamSchema updateTeam(String projectId, StandardTeamSchema fd);
	
	public StandardTeamSchema addPersonToRole(String projectId, int roleIndex, String personId);
	
	public StandardTeamSchema removePersonFromRole(String projectId, int roleIndex, String personId);

	public TeamRoleObject getRoleFromTeam(String projectId, int roleIndex);
}
