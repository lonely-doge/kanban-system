import axios from 'axios';
import router from '../router/index.js';
import { ElMessage } from 'element-plus';

function http(axiosConfig) {
	const service = axios.create({
		// 设置统一请求前缀
		baseURL: 'http://localhost:10086/kanbanSystem',
		// 设置请求超时时间
		timeout: 50000,
	});

	service.interceptors.request.use(
		config => {
			const vuexAlong = JSON.parse(localStorage.getItem('vuex-along'));
			config.headers.Authorization =
				vuexAlong === null
					? vuexAlong
					: vuexAlong.root === undefined
					? vuexAlong.root
					: vuexAlong.root.token;
			return config;
		},
		error => {
			Promise.reject(error);
		},
	);

	service.interceptors.response.use(
		response => {
			if (response.data.code === 501) {
				console.log('token expired');
				ElMessage.warning('令牌过期，请重新登录');
				window.clearVuexAlong();
				router.push('/login');
			}
			return response;
		},
		error => {
			Promise.reject(error);
		},
	);

	return service(axiosConfig);
}

export default http;
