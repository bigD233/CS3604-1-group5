import { createApp } from 'vue';
import ElementPlus from 'element-plus';
import App from './App.vue';
import router from './router';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import store from './store';
import axios from 'axios';
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import 'element-plus/dist/index.css';

import Prism from 'prismjs';

VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});

axios.defaults.baseURL = 'http://localhost:8443/api';
axios.defaults.headers.common['Authorization'] = store.state.token;

const app = createApp(App);

app.provide('axios', axios);
app.use(router);
app.use(store);
app.use(ElementPlus);
app.use(VueMarkdownEditor);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

app.mount('#app');
