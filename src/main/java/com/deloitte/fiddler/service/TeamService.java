package com.deloitte.fiddler.service;

import java.util.List;

import com.deloitte.fiddler.model.StandardTeamSchema;

public interface TeamService {
	
	public StandardTeamSchema createTeam(String url);
	
	public List<StandardTeamSchema> getAllTeams();
	
	public StandardTeamSchema getTeamByID(String id);
	
	public StandardTeamSchema updateTeam(StandardTeamSchema teamSchema);
	
	public boolean deleteTeam(String id);

}
