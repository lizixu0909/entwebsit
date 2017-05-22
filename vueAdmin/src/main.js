import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import NProgress from 'nprogress'//页面顶部进度条
import 'nprogress/nprogress.css'
import 'font-awesome/css/font-awesome.min.css'
import routes from './routes'

import util from './commons/util'
import App from './App'

Vue.use(VueRouter)
Vue.use(ElementUI)

Vue.prototype.$http = axios

axios.interceptors.request.use(function (request) {
  // post提交 data存在 并且 data不是FormData对象时对数据进行json化处理
  request.data = util.parseParam(request.data);
  if (request.method === 'post' && request.data && request.data.constructor !== FormData) {
    request.headers['Content-Type'] = 'application/x-www-form-urlencoded';
  }
  request.url = 'api/' + request.url;
  //设置请求头，方便在服务器端判断是否是ajax请求
  request.headers['X-Requested-With'] = 'XMLHttpRequest';
  return request;
}, function (error) {
  return Promise.reject(error);
});

axios.interceptors.response.use((response) => {
  if (response.data.code == -1) {
    router.replace('/login');
    return Promise.reject(response);
  } else if (response.data.code == -2 || response.data.code > 0) {
    alert(response.data.msg);
    return Promise.reject(response);
  } else {
    return response;
  }
}, function (error) {
  if (error.response.data.erron == -1) { //未登录
    router.replace('/login');
  } else {
    if (error.response.status === 404) {
      alert('请求地址[' + error.response.request.responseURL + ']不存在');
    } else {
      alert('错误代码[' + error.response.data.erron + ']: ' + error.response.data.data);
    }
  }
  return Promise.reject(error);
});
// axios.defaults.baseURL = (process.env.NODE_ENV !== 'production' ? config.dev.httpUrl : config.build.httpUrl);

const router = new VueRouter({
  routes
})


NProgress.configure({showSpinner: false});

router.beforeEach((to, from, next) => {
  NProgress.start();
  if (sessionStorage.getItem('user') == null && to.path != '/login') {
    next({path: '/login'});
  }else{
    //判断是否包含该权限
  }
  next();
})

router.afterEach(transition => {
  NProgress.done();
});

new Vue({
  router,
  components: {App},
  render: h => h(App)
}).$mount('#app');
