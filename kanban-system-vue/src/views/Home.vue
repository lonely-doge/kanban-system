<template>
  <div id="home">
    <BasicLayout />
  </div>
</template>

<script>
import BasicLayout from "layouts/BasicLayout.vue";
import { ElNotification } from "element-plus";
import { ref, onMounted, onBeforeMount } from "vue";
import { useStore } from "vuex";
export default {
  name: "Home",
  components: {
    BasicLayout,
    ElNotification,
  },
  setup() {
    const store = useStore();
    const noticeServer = ref(
      `ws://localhost:10086/kanbanSystem/notice/${store.state.userInfo.id}`
    );

    const socket = ref(null);

    const reconnect = () => {
      console.log("start to reconnect noticeServer");
      setTimeout(function () {
        //没连接上会一直重连，设置延迟避免请求过多
        socket.value = new WebSocket(noticeServer.value);
      }, 3000);
    };

    onBeforeMount(() => {
      // 渲染界面时，根据用户id获取websocket连接
      socket.value = new WebSocket(noticeServer.value);
      socket.value.onmessage = (e) => {
        ElNotification({
          title: "项目经理",
          message: JSON.parse(e.data).message,
          duration: 0,
        });
      };

      socket.value.onopen = () => {
        console.log("notice socket is on open");
      };

      socket.value.onclose = () => {
        console.log("notice socket is on close");
        reconnect();
      };

      socket.value.onerror = () => {
        console.log("notice socket is on error");
        reconnect();
      };
    });
    return {
      noticeServer,
      reconnect,
    };
  },
};
</script>

<style lang="scss" scoped>
</style>