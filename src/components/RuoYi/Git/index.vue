<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'WJGit',
  data() {
    return {
      url: 'https://gitee.com/vulcanw/wj-mes'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>