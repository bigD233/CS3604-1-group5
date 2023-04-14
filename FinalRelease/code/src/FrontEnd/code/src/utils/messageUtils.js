import { ElMessage } from 'element-plus';

function displayError(promise) {
    return new Promise((resolve, reject) => {
        promise
            .then(resp => {
                if (resp.data.code == 0) {
                    resolve(resp.data.data);
                } else {
                    ElMessage.error(resp.data.message);
                    reject(new Error(resp.data.message));
                }
            })
            .catch(err => {
                if (err?.response?.status === 401) {
                    ElMessage.error('未登录');
                } else {
                    ElMessage.error('网络错误');
                }
                reject(err);
            })
    })
};

export { displayError };
