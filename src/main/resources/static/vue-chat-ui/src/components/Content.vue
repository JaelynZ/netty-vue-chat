<template>
	<div>

    </div>
</template>
<script>
import ChatList from './ChatList'
export default {
  components: {
    'chat-list': ChatList
  },
  methods: {

  }
}
</script>
<style>

.logo{
	float:left;
    width: 120px;
    padding: 5px;
    text-align: center;
}
.searchIpt{
    width: 30%;
    height: 34px;
    cursor: text;
    float: right;
    margin-left: 50%;
    /* margin-top: 15px; */
    text-align: center;
    background-color: #eee;
    border-style: none;
}
.searchBtn{
    background-color: #FF0000;
    color: white
}
</style>
