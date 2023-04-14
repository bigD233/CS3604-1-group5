import { createStore } from 'vuex';

const store = createStore({
    state: {
        userId: Number(window.localStorage.getItem('userId')),
        token: window.localStorage.getItem('token'),
        isNormalRole: window.localStorage.getItem('isNormalRole') === 'true',
        isAdminRole: window.localStorage.getItem('isAdminRole') === 'true',
        userTab: window.localStorage.getItem('userTab'),
    },
    mutations: {
        login(state, data) {
            state.userId = data.userId;
            state.token = data.token;
            window.localStorage.setItem('userId', data.userId);
            window.localStorage.setItem('token', data.token);
        },
        setRoles(state, data) {
            state.isNormalRole = data.isNormalRole;
            state.isAdminRole = data.isAdminRole;
            window.localStorage.setItem('isNormalRole', data.isNormalRole);
            window.localStorage.setItem('isAdminRole', data.isAdminRole);
        },
        setUserTab(state, data) {
            state.userTab = data;
            window.localStorage.setItem('userTab', data);
        },
        logout(state) {
            state.userId = null;
            state.token = null;
            state.isNormalRole = null;
            state.isAdminRole = null;
            state.userTab = null;
            window.localStorage.removeItem('userId');
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('isNormalRole');
            window.localStorage.removeItem('isAdminRole');
            window.localStorage.removeItem('userTab');
        },
    },
})

export default store;
