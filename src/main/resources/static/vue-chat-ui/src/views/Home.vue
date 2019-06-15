<template>
  <div class="home">
    <div class="content-width header">
        <chat-header></chat-header>
    </div>

    <div class="content-width">
        <chat-content></chat-content>
    </div>
  </div>
</template>

<script>
import Header from './Header'
import Content from './content/Content'
import qs from 'qs'
import axios from 'axios'
import { getCookie } from '../util/util'
import { setCookie } from '../util/util'
import { delCookie } from '../util/util'

export default {
  name: 'home',
  components: {
    'chat-header': Header,
    'chat-content': Content
  },
  data () {
    return {
      user: []
    }
  },
  created () {
		  if (!getCookie('user')) {
	        // 如果没有登录状态则跳转到登录页
	        this.$router.push('/login')
	      }
  }
}
</script>
