<style>
.hgbi {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  }
.el-card {
  margin: 10px;
}

</style>
<template>
  <div class="hgbi">
    <el-card shadow="hover" >
    <iframe frameborder="2" style="border:2px blue;" width="100%" height="800px" v-resize="changeMobfsIframe" src="http://localhost:50401/chart/show/c35da655518d0b069f88/" id="hgsj" >
    </iframe>
    </el-card>
    <el-card shadow="hover">
    <iframe style="border:2px blue;" width="100%" height="800px" v-resize="changeMobfsIframe" src="http://localhost:50401/chart/show/2783c674118cfcc312b5/" id="hgsj1" >
    </iframe>
    </el-card>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  mounted() {
    this.changeMobsfIframe()
  },
  methods: {

   changeMobsfIframe() {
  const deviceWidth = document.body.clientWidth;
  const deviceHeight = document.body.clientHeight;

  this.style.width = (Number(deviceWidth) - 10) + 'px';
  this.style.height = (Number(deviceHeight) - 20) + 'px';

  this.style.width = (Number(deviceWidth) - 10) + 'px';
  this.style.height = (Number(deviceHeight) - 20) + 'px';
    }
  }
}
</script>


