import { createStore } from 'vuex';
import createVuexAlong from 'vuex-along';

export default createStore({
	state: {
		token: null,
		userInfo: null,
		projectId: null,
		boardDraft: null,
		role: null,
	},
	mutations: {
		delToken(state) {
			state.token = null;
		},
		setToken(state, token) {
			state.token = token;
		},
		setUserInfo(state, userInfo) {
			state.userInfo = userInfo;
		},
		setProjectId(state, projectId) {
			state.projectId = projectId;
		},
		setBoardDraft(state, draft) {
			state.boardDraft = draft;
		},
		updateAvatar(state, avatar) {
			state.userInfo.avatar = avatar;
		},
		setRole(state, role) {
			state.role = role;
		},
	},
	actions: {},
	modules: {},
	plugins: [createVuexAlong()],
});
