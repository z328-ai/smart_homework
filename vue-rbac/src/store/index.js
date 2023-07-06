import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    permissions:[]
  },
  mutations: {
    initPermissions(state,data){
     state.permissions =data;
    }
  },
  actions: {
  }
})
