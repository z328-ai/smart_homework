import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI, {Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {DefineAxios} from "./util/api";


Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.$axios = DefineAxios;
//配置路由守卫
router.beforeEach(((to, from, next) => {
  //如果去登录页或者注册直接放行
  if(to.path === '/' || to.path === '/register'){
    next();
    return ;
  }
//  如果是其他页面需要判断是否token，没有话说明没有登录，直接跳转到登录页
  if(window.sessionStorage.getItem("token")){
    next();
  }else{
      Message.error("未登录");
      next('/');
  }
}))
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
