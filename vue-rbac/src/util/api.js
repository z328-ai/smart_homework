import axios from 'axios'
import {Message} from "element-ui";
import router from "@/router";


//请求拦截(无论如何都发送请求成功)
axios.interceptors.request.use(config=>{
    //如果请求不是登录，且token为空，则请求中加token。
    if(config.url!=='/login' && window.sessionStorage.getItem('token')){
        config.headers['Authorization'] = window.sessionStorage.getItem('token');
    }
    console.log('请求成功');
    return config;
},error =>{
    console.log('请求失败');
})
//响应拦截器响应状态码是200为success，其他是error
//一般访问到接口状态码就是200，因为即使是服务器出问题也有异常处理器进行处理返回200状态码(服务器成功处理请求)
axios.interceptors.response.use(success => {
    console.log("响应成功")
//    业务逻辑错误
    if (success.status && success.status ===200) {
        if (success.data.status === 500 || success.data.status === 401) {
            Message.error({message: success.data.message});
            return ;
        }
        if (success.data.message) {
            Message.success({message: success.data.message});
        }
    }
    return success;
}, error => {
    console.log('响应失败');
    if (error.response.data.status == 504 || error.response.data.status == 404) {
        console.log(error.response)
        Message.error({message: '服务器被吃了（；´д｀）ゞ'});
    } else if (error.response.data.status == 403) {
        Message.error(({message: '权限不足，请联系管理员！'}));
    } else if (error.response.data.status == 401) {
        Message.error({message: '尚未未登录，请登录！'});
        router.replace('/');
    } else {
        if (error.response.data.message) {
            Message.error({message: error.response.data.message});
        } else {
            console.log(error.response)
            Message.error({message: '未知错误'});
        }
    }
    return;
})
export const DefineAxios = axios;
