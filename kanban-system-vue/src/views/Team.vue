<template>
  <div id="team">
    <div class="avatar">
      <el-avatar shape="square"
                 :size="50"
                 :src="avatar"
                 @error="errorHandler" />
      <div class="nickname">{{nickname}}</div>
    </div>
    <Chat />
  </div>
</template>

<script>
import { ElScrollbar, ElAvatar } from "element-plus";
import { onBeforeMount, onMounted, reactive, toRefs } from "vue";
import Chat from "views/Chat.vue";
import { useStore } from "vuex";
export default {
  name: "Team",
  components: {
    ElScrollbar,
    Chat,
    ElAvatar,
  },
  setup() {
    onBeforeMount(() => {});
    const store = useStore();
    const data = reactive({
      avatar:
        `http://localhost:10086/kanbanSystem/file/exhibit/` +
        store.state.userInfo.id +
        ".png",
      nickname: store.state.userInfo.nickName,
    });
    const errorHandler = () => true;
    return {
      errorHandler,
      ...toRefs(data),
    };
  },
};
</script>

<style lang="scss" scoped>
#team {
  padding: 20px 10px;
  background-color: #f5f7fb;

  .avatar {
    overflow: hidden;
    margin-bottom: 10px;

    .el-avatar {
      float: right;
      vertical-align: text-bottom;
    }
    .nickname {
      display: block;
      float: right;
      font-size: 20px;
      padding: 0 10px;
    }
  }
}
</style>