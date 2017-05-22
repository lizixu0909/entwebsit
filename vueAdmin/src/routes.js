import Login from './pages/Login.vue'
import Main from './pages/Main.vue'
import WelCome from './pages/WelCome.vue'
//系统管理
import User from './pages/system/User.vue'
import Resource from './pages/system/Resource.vue'
import Role from './pages/system/Role.vue'
import AccessLog from './pages/system/Log.vue'
let routes = [
  {
    path: '/login',
    component: Login,
    hidden: true, //不显示在导航栏中
  },
  {
    path: '/',
    name: '',
    component: Main,
    hidden: true,
    children: [
      {path: '/welcome', component: WelCome, name: '欢迎页'}
    ]
  },
  {
    path: '/',
    component: Main,
    name: '系统管理',
    icon: 'el-icon-message',
    children: [
      {path: '/system/user', component: User, name: '用户管理'},
      {path: '/system/resource', component: Resource, name: '资源管理'},
      {path: '/system/role', component: Role, name: '角色管理'},
      {path: '/system/log', component: AccessLog, name: '日志管理'},
    ]
  }
]
export default routes;
