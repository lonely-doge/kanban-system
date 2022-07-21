import http from './axios.js';

export function queryTeamMember(teamId) {
	return http({
		url: 'user/queryTeamMember',
		method: 'post',
		data: teamId,
	});
}

export function login(params) {
	return http({
		url: 'user/login',
		method: 'post',
		data: params,
	});
}

export function register(params) {
	return http({
		url: 'user/register',
		method: 'post',
		data: params,
	});
}

export function updateProfile(params) {
	return http({
		url: 'user/updateProfile',
		method: 'post',
		data: params,
	});
}
