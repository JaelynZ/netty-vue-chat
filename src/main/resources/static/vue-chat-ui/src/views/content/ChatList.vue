<template>
	<div>
        <div style="width: 100%;height: 50px;">
            <div class="chat-type chat-type-left" v-bind:style="(chatType == 1 ? checkChatTypeStyle : false)" @click="selectChatType(1)">好友</div>
            <div class="chat-type" v-bind:style="(chatType == 2 ? checkChatTypeStyle : false)" @click="selectChatType(2)">群组</div>
        </div>

        <div v-for="friend in friends" style="width: 100%;height: 100%;" v-if="chatType == 1">
            <div class="friendAreaSty" @click="selectFriend(friend)" v-bind:style="(checkUserId == friend.id ? checkUserStyle : false)">
              <span><img class="headIconSty" v-bind:src="friend.headIcon"> </span>
              <span class="nickNameSty">{{ friend.nickName }}</span>
          </div>
        </div>

        <div v-for="group in groups" style="width: 100%;height: 100%;" v-if="chatType == 2">
            <div class="friendAreaSty" @click="selectGroup(group)" v-bind:style="(checkGroupId == group.id ? checkGroupStyle : false)">
              <span><img class="headIconSty" v-bind:src="group.groupIcon"> </span>
              <span class="nickNameSty">{{ group.groupName }}</span>
          </div>
        </div>
  </div>
</template>
<script>
import { getCookie } from '../../util/util'
import axios from 'axios'
export default {
  props: {
    friendCallback: {
      type: Function,
      default (friend) {}
    }
  },
  data () {
    return {
      user: {},
      friends: [],
      groups: [],
      groupUsers: [],
      checkUserId: 0,
      checkGroupId: 0,
      chatType: 1,
      checkChatTypeStyle: { 'background-color': 'yellow' },
      checkUserStyle: { 'background-color': '#EBEBEC' },
      checkGroupStyle: { 'background-color': '#EBEBEC' }
    }
  },
  methods: {
    selectFriend: function (friend) {
      this.checkUserId = friend.id

      var friendObj = {}
      friendObj.chatType = 1
      friendObj.friend = friend
      this.friendCallback(friendObj)
    },
    selectGroup: function (group) {
      this.checkGroupId = group.id
      this.getUserList()
    },
    selectChatType: function (value) {
      this.chatType = value

      if (value == 1) {
        // 查询好友列表
        this.getFriendList()
      } else {
        // 查询群组列表
        this.getFriendGroup()
      }
    },
    getFriendList: function () {
      let self = this
      // 获取好友列表
      axios.post('/apis/friend/getFriendListByUserId', { 'id': self.user.id })
        .then(function (response) {
          return response.data
        })
        .then(function (data) {
          self.friends = data.data
          // 默认选中第一个好友,没有好友默认自己
          var friendObj = {}
          friendObj.chatType = 1

          if (self.friends != null) {
            let friend = self.friends[0]
            self.checkUserId = friend.id

            friendObj.friend = friend
            self.friendCallback(friendObj)
          } else {
            self.checkUserId = user.id

            friendObj.friend = user
            self.friendCallback(friendObj)
          }
        })
    },
    getFriendGroup: function () {
      let self = this
      // 获取群组列表
      axios.post('/apis/friend/getFriendGroupListByUserId', { 'id': self.user.id })
        .then(function (response) {
          return response.data
        })
        .then(function (data) {
          self.groups = data.data
          // 默认选中第一个好友,没有好友默认自己
          if (self.groups != null) {
            let group = self.groups[0]
            // self.friendCallback(group);
            self.checkGroupId = group.id
            self.getUserList()
          }
        })
    },
    getUserList: function () {
      let self = this
      // 获取群组的好友列表
      axios.post('/apis/friend/getUserListByGroupId', { 'id': self.checkGroupId })
        .then(function (response) {
          return response.data
        })
        .then(function (data) {
          self.groupUsers = data.data

          var friendObj = {}
          friendObj.chatType = 2
          friendObj.friends = self.groupUsers
          friendObj.groupId = self.checkGroupId
          self.friendCallback(friendObj)
        })
    }
  },
  mounted () {
    this.user = JSON.parse(unescape(getCookie('user')))
    this.getFriendList()
  }
}
</script>
<style scoped>
.chat-type-left{
    border-right: 1px solid;
    border-color: #D1D1D1;
}
.chat-type{
    width: 124px;
    height: 50px;
    float: left;
    text-align: center;
    vertical-align: middle;
    line-height: 50px;
    cursor: pointer;
    border-bottom: 1px solid;
    border-color: #D1D1D1;
}
.friendAreaSty{
    width: 250px;
    height: 70px;
    background-color: #FAFAFA;
    border-bottom: 1px solid;
    border-color: #D1D1D1;
    text-align: center;
    vertical-align: middle;
    display:table-cell;
    cursor: pointer;
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
.headIconSty{
    float:left;
    width: 50px;
    padding: 5px;
    text-align: center;
}
.nickNameSty{
    float: left;
    line-height: 60px;
    font-size: 18px;
    margin-left: 10px;
}
</style>
