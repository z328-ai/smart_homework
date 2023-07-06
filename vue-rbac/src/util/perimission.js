
import IndexView from "@/components/IndexView";

export function activeRouter(router,permissions,store){
    let root={
        path: '/mvc_zengzhiqi',
        name: 'indexView',
        component: IndexView,
        children: []
    }
    //通过权限里面信息创建新的路由
    permissions.forEach(p=>{
        let obj = {
            path: p.path,
            name: p.name,
            component: ()=> import("@/views/"+p.name)
        };
        root.children.push(obj);
    })
    //更新存在vuex里面权限
    store.commit('initPermissions',permissions);
    //将新路由放在路由器中
    router.addRoute(root);

}