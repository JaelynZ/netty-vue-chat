<template>
	<div>
         <div class="chat-list">
            <chat-list :friendCallback="selectFriendChatUi"></chat-list>
         </div>
         <div class="chat-content">
           <chat-ui ref="chatUi" :friendObj="friendObj"></chat-ui>
         </div>
    </div>
</template>
<script>
import ChatList from './ChatList'
import ChatUi from './ChatUi'
import { getCookie } from '../../util/util'
export default {
  components: {
    'chat-list': ChatList,
    'chat-ui': ChatUi
  },
  data () {
    return {
      friendObj: {}
    }
  },
  methods: {
    selectFriendChatUi: function (value) {
      this.friendObj = value
      console.log('选中的好友或群组信息为：' + JSON.stringify(this.friendObj))
      this.$refs.chatUi.initChatUi(value)
    }
  },
  mounted () {
    // this.friend = JSON.parse(unescape(getCookie('user')));
  }
}
</script>
<style>
.chat-list{
    width: 250px;
    height: 700px;
    background-color: #FAFAFA;
    float:left;
    border-left: 1px solid;
    border-bottom: 1px solid;
    border-color: #138EEE;
}
.chat-content{
    width: 750px;
    height: 700px;
    background-color: #FAFAFA;
    float:left;
    border-right: 1px solid;
    border-bottom: 1px solid;
    border-color: #138EEE;
}
</style>
