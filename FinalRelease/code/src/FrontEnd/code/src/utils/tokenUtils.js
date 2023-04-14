function saveToken(store, axios, userId, token) {
    store.commit('login', { userId, token });
    axios.defaults.headers.common['Authorization'] = token;
}

function removeToken(store, axios) {
    store.commit('logout');
    axios.defaults.headers.common['Authorization'] = null;
}

export { saveToken, removeToken };
