<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'WJDoc',
  data() {
    return {
      url: 'http://doc.wj.vip/wj-vue'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>