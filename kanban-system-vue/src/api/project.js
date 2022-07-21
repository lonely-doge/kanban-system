import http from './axios.js';

export function addProject(project) {
	return http({
		url: 'project/addProject',
		method: 'post',
		data: project,
	});
}

export function queryProjects(params) {
	return http({
		url: 'project/queryProjects',
		method: 'post',
		data: params,
	});
}

export function checkProject(params) {
	return http({
		url: 'project/checkProject',
		method: 'post',
		data: params,
	});
}

export function queryProjectMember(params) {
	return http({
		url: 'project/queryProjectMember',
		method: 'post',
		data: params,
	});
}
