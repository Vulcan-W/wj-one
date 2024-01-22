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
  <div class="hggwbi">
    <div class="hgbi">
      <el-card shadow="hover" >
      <iframe id="hgsj" v-resize="changeMobsfIframe" frameborder="2" height="600px" src="http://localhost:50401/chart/show/c35da655518d0b069f88/" style="border:2px blue;" width="100%" >
      </iframe>
      </el-card>
      <el-card shadow="hover">
      <iframe id="hgsj1" v-resize="changeMobsfIframe" height="600px" src="http://localhost:50401/chart/show/2783c674118cfcc312b5/" style="border:2px blue;" width="100%" >
      </iframe>
      </el-card>


      <el-card shadow="hover">
      <iframe id="hgsj2" v-resize="changeMobsfIframe" height="650px" src="http://localhost:50401/dashboard/show/6b2c23e1118d1f90fe93/" style="border:2px blue;" width="100%" >
      </iframe>
      </el-card>
      <el-card shadow="hover">
      <iframe id="hgsj3" v-resize="changeMobsfIframe" height="650px" src="http://localhost:50401/dashboard/show/399b6f3c118d20ae1de1/" style="border:2px blue;" width="100%" >
      </iframe>
      </el-card>

      <el-card shadow="hover">
        <iframe id="hgsj4" v-resize="changeMobsfIframe" height="600px" src="http://localhost:50401/chart/show/92956c29a18d20efa728/" style="border:2px blue;" width="100%" >
        </iframe>
      </el-card>
      <el-card shadow="hover">
        <el-empty description="连续3年增长，数据不足，敬请期待"></el-empty>
        <iframe id="hgsj4" v-resize="changeMobsfIframe" height="100px" src="" style="border:2px blue;" width="100%" >
        </iframe>
      </el-card>
    </div>
      <el-backtop :bottom="60" visibility-height="300"></el-backtop>
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


