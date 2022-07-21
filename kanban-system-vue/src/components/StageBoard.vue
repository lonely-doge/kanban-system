<template>
  <div id="stage-board">
    <div class="container">
      <div class="header">
        <el-icon @click="closeStage">
          <CloseBold />
        </el-icon>
      </div>
      <el-divider />
      <div class="body">
        <div class="title">
          <h3>添加阶段</h3>
        </div>
        <el-input v-model="stage">
          <template #prepend>
            阶段名称
          </template>
        </el-input>
        <div class="opt-btn">
          <el-button type="primary"
                     @click="addStage">添加</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ElIcon, ElDivider, ElInput, ElMessage } from "element-plus";
import { CloseBold } from "@element-plus/icons-vue";
import { ref } from "vue";
import { insertStage } from "api/system.js";
export default {
  name: "StageBoard",
  components: {
    ElIcon,
    CloseBold,
    ElDivider,
  },
  setup(props, { emit }) {
    const stage = ref("");
    const closeStage = () => {
      emit("closeStage", false);
    };
    const addStage = () => {
      insertStage({ name: stage.value }).then((res) => {
        if (res.data.code === 200) {
          ElMessage.success("添加成功");
          location.reload();
        } else {
          ElMessage.error(res.data.msg);
        }
      });
    };
    return {
      addStage,
      closeStage,
      stage,
    };
  },
};
</script>

<style lang="scss" scoped>
#stage-board {
  position: fixed;
  z-index: 6;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba($color: #000000, $alpha: 0.6);

  .container {
    width: 400px;
    height: fit-content;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border-radius: 10px;
    padding: 10px 20px;

    .header {
      overflow: hidden;
      font-size: 30px;
      .el-icon {
        display: block;
        float: right;
        &:hover {
          cursor: pointer;
          transform: rotate(90deg);
        }
      }
    }

    .el-divider {
      margin: 10px 0;
    }

    .body {
      .title {
        text-align: center;
      }
      .opt-btn {
        text-align: center;
        margin: 20px 0;
      }
    }
  }
}
</style>