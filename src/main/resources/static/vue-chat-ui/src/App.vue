<template>
  <div id="app">
    <div class="content-width">
      <router-view></router-view>
    </div>

  </div>
</template>

<script>
import Header from './views/Header'
import Content from './views/content/Content'

export default {
  components: {
    'chat-header': Header,
    'chat-content': Content
  },
  data () {
    return {

    }
  },
  mounted () {

  }
}
</script>

<style>
.content-width{
    width: 1000px;
    height: 100%;
    margin-right: auto;
    margin-left: auto;
}
.header{
  background-color: #138EEE;
}
</style>
