import http from './axios.js';

export function queryKanbanInfo(params) {
	return http({
		url: 'kanban/queryBoards',
		method: 'post',
		data: params,
	});
}

export function queryBoardsByStage(stage) {
	return http({
		url: 'kanban/queryBoards',
		method: 'post',
		data: stage,
	});
}

export function updateStage(board) {
	return http({
		url: 'kanban/updateStage',
		method: 'post',
		data: board,
	});
}

export function addBoard(board) {
	return http({
		url: 'kanban/addBoard',
		method: 'post',
		data: board,
	});
}

export function updateBoard(board) {
	return http({
		url: 'kanban/updateBoard',
		method: 'post',
		data: board,
	});
}
