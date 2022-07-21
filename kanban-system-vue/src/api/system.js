import http from './axios.js';

export function queryMenus() {
	return http({
		url: 'system/sidebarMenu',
		method: 'get',
	});
}

export function queryStages() {
	return http({
		url: 'system/stages',
		method: 'get',
	});
}

export function pullMsg(params) {
	return http({
		url: 'system/pullMsg',
		method: 'post',
		data: params,
	});
}

export function queryComments(params) {
	return http({
		url: 'system/queryComments',
		method: 'post',
		data: params,
	});
}

export function insertComment(params) {
	return http({
		url: 'system/addComment',
		method: 'post',
		data: params,
	});
}

export function sendNotice(params) {
	return http({
		url: 'system/addNotice',
		method: 'post',
		data: params,
	});
}

export function noticeList(params) {
	return http({
		url: 'system/noticeList',
		method: 'post',
		data: params,
	});
}

export function queryUserRoles(params) {
	return http({
		url: 'system/queryUserRoles',
		method: 'post',
		data: params,
	});
}

export function queryAllRoles(params) {
	return http({
		url: 'system/queryAllRoles',
		method: 'post',
		data: params,
	});
}

export function queryProjectMembers(params) {
	return http({
		url: 'system/queryProjectMembers',
		method: 'post',
		data: params,
	});
}

export function saveProjectInfo(params) {
	return http({
		url: 'system/saveProjectInfo',
		method: 'post',
		data: params,
	});
}

export function insertStage(params) {
	return http({
		url: 'system/addStage',
		method: 'post',
		data: params,
	});
}
