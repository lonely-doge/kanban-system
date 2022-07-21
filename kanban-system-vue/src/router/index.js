import { createRouter, createWebHistory } from 'vue-router';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';

const routes = [
	{
		path: '/',
		name: 'Home',
		component: () => import('views/Home.vue'),
		meta: {
			auth: false,
		},
		alias: '/home',
		redirect: '/Project',
		children: [
			{
				path: '/Project',
				name: 'Project',
				component: () => import('views/Project.vue'),
			},
			{
				path: '/Dashboard',
				name: 'Dashboard',
				component: () => import('views/Dashboard.vue'),
			},
			{
				path: '/Team',
				name: 'Team',
				component: () => import('views/Team.vue'),
				children: [
					{
						path: '/Chat',
						name: 'Chat',
						component: () => import('views/Chat.vue'),
					},
				],
			},
			{
				path: '/Settings',
				name: 'Settings',
				component: () => import('views/Settings.vue'),
			},
			{
				path: '/Notification',
				name: 'Notification',
				component: () => import('views/Notification.vue'),
			},
		],
	},
	{
		path: '/Login',
		name: 'Login',
		component: () => import('views/Login.vue'),
	},
	{
		path: '/Register',
		name: 'Register',
		component: () => import('views/Register.vue'),
	},
	{
		path: '/Admin',
		name: 'Admin',
		component: () => import('views/Admin.vue'),
	},

	// 404 页面，务必放在最后
	{
		path: '/:catchAll(.*)',
		name: 'NotFound',
		component: () => import('views/NotFound.vue'),
	},
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

/**
 * @param to要跳转到的路径
 * @param from从哪个路径来
 * @param next往下执行的回调
 */
router.beforeEach((to, from, next) => {
	const vuexAlong = JSON.parse(localStorage.getItem('vuex-along'));
	if (to.path !== from.path) NProgress.start();
	if (to.path === '/login' || to.path === '/register' || to.path === '/admin') next();
	else {
		if (vuexAlong && vuexAlong.root) next();
		else next('/login');
	}
});

router.afterEach(() => {
	NProgress.done();
});

export default router;
