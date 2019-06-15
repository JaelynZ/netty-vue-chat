<template>
	<header>
		<nav class="navbar">
	        <span><img class="headIcon" v-bind:src="user.headIcon"><span class="nickNameSty">{{ user.nickName }}</span></span>

            <button class="btn clickBtn" @click="logout()">退出登录</button>
		</nav>
	</header>
</template>
<script>
import { setCookie } from '../util/util'
import { getCookie } from '../util/util'
import qs from 'qs'
  	import axios from 'axios'
export default {
  data () {
	      return {
	        user: []
	      }
	    },
  methods: {
    logout: function () {
      // 更新用户在线状态
	            axios.post('/apis/user/updateOnlineInfo', { 'id': this.user.id, 'isOnline': 0 })
	            .then(function (response) {
	              return response.data
	            })
	            .then(function (data) {

	            })

	            setCookie('user', null, 0)
      this.$router.push({ path: '/login' })
    }
  },
  mounted () {
    this.user = JSON.parse(unescape(getCookie('user')))
    console.log('头像地址：' + this.user.headIcon)
    	}
}
</script>
<style scoped>

.headIcon{
	float:left;
    width: 80px;
    padding: 5px;
    text-align: center;
}

.clickBtn{
    background-color: #FF0000;
    color: white;
}
.nickNameSty{
    float: left;
    line-height: 80px;
    font-size: 22px;
    margin-left: 10px;
}
</style>
