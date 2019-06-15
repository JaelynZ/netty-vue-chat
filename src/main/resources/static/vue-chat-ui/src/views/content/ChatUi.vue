<template>
	<div>
      <div class="get-msg-area">
          <!-- <textarea id="responseText" class="chat-history-style" readonly="readonly" ref="chatHistory"></textarea> -->
          <div class="chat-history-style" id="chat-history">
              <br>
              <div class="single-chat-msg-area" v-bind:style="changeHeightByChatMsg(history.message.length,1)"
                    v-if="friendObj.chatType == 1" v-for="history in msgHistories">
                  <img v-show="history.fromUserId == user.id ? false : true" class="chat-icon"  @click="showUserInfo(history.fromUserId)"
                       v-bind:src="history.headIcon" >
                  <img v-show="history.fromUserId == user.id ? true : false" class="chat-icon"
                       v-bind:style="history.fromUserId == user.id ? rightIcon : null" @click="showUserInfo(history.fromUserId)"
                       v-bind:src="history.headIcon">
                  <span class="chat-msg" v-bind:style="history.fromUserId == user.id ? rightMsg : null">{{ history.message }}</span>

              </div>
              <div class="single-chat-msg-area" v-bind:style="changeHeightByChatMsg(history.message.length,2)"
                     v-if="friendObj.chatType == 2" v-for="history in msgHistories">
                  <img v-show="history.userId == user.id ? false : true" class="chat-icon"  @click="showUserInfo(history.userId)"
                       v-bind:src="history.headIcon" >
                  <img v-show="history.userId == user.id ? true : false" class="chat-icon"
                       v-bind:style="history.userId == user.id ? rightIcon : null" @click="showUserInfo(history.userId)"
                       v-bind:src="history.headIcon">
                  <span class="chat-nickName" v-show="history.userId == user.id ? false : true">{{history.nickName}}</span>
                  <span class="chat-msg" v-bind:style="history.userId == user.id ? rightMsg : leftMsg">{{ history.message }}</span>
              </div>
          </div>
      </div>
      <div class="send-msg-area">
          <textarea name="message" class="chat-msg-style" placeholder="你想说的话" ref="chatMsg" @keyup.enter="sendMsg()"></textarea>
          <input type="button" value="发送消息" @click="sendMsg()" class="send-msg-btn">
      </div>
    </div>
</template>
<script>
import { getCookie } from '../../util/util'
import axios from 'axios'

// flag: 1连接，2关闭，3接收消息，4发送消息

var socket
export default {
  data () {
    return {
      msg: {},
      user: {},
      msgHistories: [],
      rightIcon: { 'float': 'right' },
      rightMsg: { 'margin-right': '10px', 'float': 'right' },
      leftMsg: { 'max-width': '580px' }
    }
  },
  props: {
    friendObj: {
      type: Object,
      required: true
    }
  },
  methods: {
    changeHeightByChatMsg: function (strLength, chatType) {
      let height
      if (chatType == 1) {
        height = Math.ceil(strLength / 43) * 40
      } else {
        height = Math.ceil(strLength / 38) * 40
      }
      return { 'height': height + 'px' }
    },
    showUserInfo: function (userId) {
      console.log('查看用户信息:' + userId)
    },
    setBottomPosition: function (id) {
      // 滚动条滚动到底部
      var area = document.getElementById(id)
      console.log('scrollTop:' + area.scrollTop + '----scrollHeight:' + area.scrollHeight)
      area.scrollTop = area.scrollHeight
    },
    initChatUi: function (friendObj) {
      // 清空聊天列表
      // this.$refs.chatHistory.value = '';
      this.$refs.chatMsg.value = ''

      let self = this
      let param = {}
      if (friendObj.chatType == 1) {
        // 个人聊天
        param = { 'fromUserId': self.user.id, 'toUserId': friendObj.friend.id, 'chatType': 1 }
      } else {
        // 群聊
        param = { 'groupId': friendObj.groupId, 'chatType': 2 }
      }

      axios.post('/apis/msg/getChatHistory', param)
        .then(function (response) {
          return response.data
        })
        .then(function (data) {
          // 填充聊天记录
          if (data.data != null) {
            self.msgHistories = data.data
            console.log(self.msgHistories)
            /* for(var i= data.data.length -1; i>=0; i--){
                                var msgInfo = data.data[i];
                                var info = msgInfo.nickName + "[" + msgInfo.createDate + "]：" + msgInfo.message;
                                self.$refs.chatHistory.value = self.$refs.chatHistory.value + '\n' + info;
                            } */
            self.setBottomPosition('chat-history')
          }
        })
    },
    sendMsg: function () {
      console.log('你聊天的好友为:' + JSON.stringify(this.friendObj))
      if (!getCookie('user')) {
        // 如果没有登录状态则跳转到登录页
        this.$router.push('/login')
      }

      if (this.$refs.chatMsg.value == '') {
        this.tip('消息内容不能为空')
        return
      }

      this.msg.flag = 4

      this.msg.chatType = this.friendObj.chatType

      if (this.msg.chatType == 1) {
        this.msg.friend = this.friendObj.friend
      } else {
        this.msg.friends = this.friendObj.friends
        this.msg.groupId = this.friendObj.groupId
      }

      if (!window.WebSocket) {
        return
      }
      if (socket.readyState == WebSocket.OPEN) {
        this.msg.content = this.$refs.chatMsg.value
        console.log(JSON.stringify(this.msg))
        socket.send(JSON.stringify(this.msg))
      } else {
        this.tip('连接没有开启.')
      }

      this.$refs.chatMsg.value = ''
    },
    tip: function (msg) {
      this.$ckq.layer({
        message: msg,
        time: 1500
      })
    }
  },
  mounted () {
    console.log('聊天初始化')
    let self = this

    let user = unescape(getCookie('user'))
    if (user != '' || user != null) {
      self.msg.user = JSON.parse(user)
      self.user = self.msg.user
    } else {
      this.tip('未登录，请先登录.')
      return
    }

    if (!window.WebSocket) {
      window.WebSocket = window.MozWebSocket
    }
    if (window.WebSocket) {
      socket = new WebSocket('ws://localhost:8080/ws')
      socket.onmessage = function (event) {
        var msgInfo = JSON.parse(event.data)

        var myDate = new Date()
        var time = myDate.getFullYear() + '-' + myDate.getMonth() + '-' + myDate.getDate() + ' ' + myDate.getHours() + ':' +
                                myDate.getMinutes() + ':' + myDate.getSeconds()

        let history = {}

        if (self.friendObj.chatType == 1) {
          if ((self.user.id == msgInfo.friend.id && self.friendObj.friend.id == msgInfo.user.id) ||
                        (self.user.id == msgInfo.user.id && self.friendObj.friend.id == msgInfo.friend.id)) {
            history.fromUserId = msgInfo.user.id
            history.toUserId = msgInfo.friend.id
            history.createDate = time
            history.headIcon = msgInfo.user.headIcon
            history.message = msgInfo.content

            self.msgHistories.push(history)

            // var info = msgInfo.user.nickName + "[" + time + "]：" + msgInfo.content;

            // console.log(info);

            // self.$refs.chatHistory.value = self.$refs.chatHistory.value + '\n' + info;
          }
        } else {
          history.groupId = msgInfo.groupId
          history.userId = msgInfo.user.id
          history.createDate = time
          history.headIcon = msgInfo.user.headIcon
          history.nickName = msgInfo.user.nickName
          history.message = msgInfo.content

          self.msgHistories.push(history)

          // var info = msgInfo.user.nickName + "[" + time + "]：" + msgInfo.content;

          // console.log(info);

          // self.$refs.chatHistory.value = self.$refs.chatHistory.value + '\n' + info;
        }

        self.setBottomPosition('chat-history')
      }
      socket.onopen = function (event) {
        self.msg.content = '连接开启!'
        self.msg.flag = 1
        socket.send(JSON.stringify(self.msg))
      }
      socket.onclose = function (event) {
        /* self.msg.content = "连接被关闭!";
                    self.msg.flag = 2;
                    socket.send(JSON.stringify(self.msg)); */
      }
    } else {
      this.tip('你的浏览器不支持 WebSocket！')
    }

    self.setBottomPosition('chat-history')
  }
}
</script>
<style>
.single-chat-msg-area{
    width: 100%;
    height: 40px;
    margin-bottom: 5px;
}
.chat-icon{
    float:left;
    width: 40px;
    padding: 5px;
    text-align: center;
}
.chat-nickName{
    float: left;
    font-size: 12px;
    margin-left: 5px;
    color: #808080;
    max-width: 60px;
}
.chat-time{
    float: left;
    line-height: 40px;
    font-size: 18px;
    margin-left: 10px;
}
.chat-msg{
    float: left;
    line-height: 40px;
    margin-left: 10px;
    border: 0.5px solid;
    border-color: #EEEEEE;
    height: 100%;
    border-radius: 30px;
    font-size: 15px;
    max-width: 650px;
    background-color: #EEEEEE;
}
input:focus, textarea:focus {

    outline: none;

}
.chat-list{
    width: 250px;
    height: 700px;
    background-color: grey;
    float:left;
}
.chat-content{
    width: 750px;
    height: 700px;
    background-color: green;
    float:left;
}
.chat-history-style{
    width: 748px;
    height: 550px;
    border-color: #EAEAEA;
    overflow:auto;
}
.chat-msg-style{
    width: 748px;
    height: 110px;
    border-color: #EAEAEA;
}
.send-msg-area{
    width: 750px;
    height: 150px;
    text-align: center;
    vertical-align: middle;
}
.get-msg-area{
    width: 750px;
    height: 550px;
    text-align: center;
    vertical-align: middle;
}
.send-msg-btn{
    margin-right: 5px;
    margin-top: -1px;
    width: 150px;
    height: 30px;
    float: right;
    background-color: #008CBA;
    border: none;
    color: #FFFFFF;
    /* padding: 15px 32px; */
    text-align: center;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
    /* margin: 16px 0 !important; */
    /* text-decoration: none; */
    /* display: inline-block; */
    font-weight: 400;
    line-height: 30px;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    background-image: none;
    border-radius: 4px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}
</style>
