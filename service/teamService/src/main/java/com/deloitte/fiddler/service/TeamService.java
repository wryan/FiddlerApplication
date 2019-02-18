package com.deloitte.fiddler.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.deloitte.fiddler.common.StandardTeamSchema;

public interface TeamService {
	
	public StandardTeamSchema createTeam(StandardTeamSchema p);
	
	public List<StandardTeamSchema> getAllTeams();
	
	public StandardTeamSchema getTeamByID(String id) throws NoSuchElementException ;
	
	public StandardTeamSchema updateTeam(StandardTeamSchema fd);
	
	public boolean deleteTeam(String id);
	
}
