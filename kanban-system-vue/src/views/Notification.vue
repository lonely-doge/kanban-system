<template>
  <div id="notification">
    <div class="title">通知管理界面</div>
    <div class="option-bar">
      <div class="mem-select">
        <el-select v-model="choseMem"
                   placeholder="Select"
                   size="large"
                   value-key="id"
                   multiple>
          <el-option v-for="item in members"
                     :key="item.id"
                     :label="item.nickName"
                     :value="item.id" />
        </el-select>
      </div>
      <el-button type="primary"
                 @click="dialogVisible = true">发送消息</el-button>
      <el-dialog v-model="dialogVisible"
                 title="发送通知"
                 center>
        <el-input v-model="notice"
                  :rows="4"
                  type="textarea"
                  placeholder="请输入通知内容"
                  resize="none" />
        <div class="dialog-opt">
          <el-button type="primary"
                     @click="send">发送</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </div>
      </el-dialog>
    </div>
    <div class="table-container">
      <el-table :data="tableData"
                row-class-name="notice-item"
                stripe
                style="width: 100%">
        <el-table-column prop="origin"
                         label="Sender" />
        <el-table-column prop="message"
                         label="Message" />
        <el-table-column prop="destination"
                         label="Receiver" />
        <el-table-column prop="createTime"
                         label="NoticeTime" />
        <el-table-column prop="updateTime"
                         label="UpdateTime" />
      </el-table>
    </div>
  </div>
</template>

<script>
import {
  ElTable,
  ElTableColumn,
  ElButton,
  ElSelect,
  ElOption,
  ElMessage,
  ElNotification,
} from "element-plus";
import { onBeforeMount, onMounted, ref } from "vue";
import { queryTeamMember } from "api/user.js";
import { sendNotice, noticeList } from "api/system.js";
import { useStore } from "vuex";
export default {
  name: "Notification",
  components: {
    ElTable,
    ElTableColumn,
    ElButton,
    ElSelect,
    ElMessage,
    ElOption,
    ElNotification,
  },
  setup(props, { emit }) {
    const choseMem = ref([]);
    const tableData = ref([]);
    const members = ref([]);
    const notice = ref("");
    const store = useStore();
    const dialogVisible = ref(false);

    onBeforeMount(() => {
      queryTeamMember({
        userId: store.state.userInfo.id,
        teamId: store.state.userInfo.teamId,
      }).then((res) => {
        if (res.data.code === 200) {
          members.value.push(...res.data.content);
        }
      });

      noticeList({
        id: store.state.userInfo.id,
      }).then((res) => {
        if (res.data.code === 200) {
          tableData.value.push(...res.data.content);
        }
      });
    });

    const noticeServer = ref(
      `ws://localhost:10086/kanbanSystem/notice/${store.state.userInfo.id}`
    );

    const socket = ref(new WebSocket(noticeServer.value));

    const send = () => {
      if (!notice.value) ElMessage.warning("通知不可为空");
      sendNotice({
        origin: store.state.userInfo.id,
        destination: choseMem.value,
        message: notice.value,
      }).then((res) => {
        console.log(socket.value);
        if (res.data.code === 200) {
          for (let i = 0; i < choseMem.value.length; i++) {
            let temp = {
              origin: store.state.userInfo.id,
              destination: choseMem.value[i],
              message: notice.value,
            };
            socket.value.send(JSON.stringify(temp));
          }
          ElMessage.success("发送成功");
          // location.reload();
        } else {
          ElMessage.error("发送失败");
        }
      });
    };
    return {
      noticeServer,
      notice,
      dialogVisible,
      members,
      choseMem,
      send,
      tableData,
    };
  },
};
</script>

<style scoped lang="scss">
#notification {
  .title {
    text-align: center;
    font-size: 30px;
    font-weight: 450;
  }

  .option-bar {
    width: 100%;
    height: fit-content;

    .mem-select {
      display: inline-block;
      margin-right: 20px;
    }

    .el-dialog {
      .dialog-opt {
        text-align: center;
        margin: 20px 0 0 0;
      }
    }
  }
}
</style>