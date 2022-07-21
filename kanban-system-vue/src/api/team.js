import http from './axios.js';

export function addTeam(team) {
	return http({
		url: 'team/addTeam',
		method: 'post',
		data: team,
	});
}

export function queryTeam(teamId) {
	return http({
		url: 'team/queryTeam',
		method: 'post',
		data: teamId,
	});
}

export function teamInfoList() {
	return http({
		url: 'team/teamInfoList',
		method: 'post',
	});
}
