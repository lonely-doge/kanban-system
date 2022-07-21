<template>
  <div id="dialog">
    <div class="nickname"
         v-if="contact">{{contact.nickName}}</div>
    <el-divider></el-divider>
    <div class="content">
      <el-scrollbar height="470px"
                    ref="content">
        <div v-if="msgList.length">
          <div v-for="(msg, i) in msgList"
               :key="i">
            <div class="msg">
              <div class="time">{{msg.time}}</div>
              <div v-if="msg.origin === contact.id"
                   class="msg-left">
                <div class="avatar">
                  <el-avatar shape="square"
                             :size="50"
                             :src="baseURL+contact.id+'.png'"
                             @error="errorHandler" />
                </div>
                <div class="bubble bubble-msg-left">
                  {{ msg.message }}
                </div>
              </div>
              <div v-else
                   class="msg-right">
                <div class="avatar">
                  <el-avatar shape="square"
                             :size="50"
                             :src="baseURL+avatar"
                             @error="errorHandler" />
                </div>
                <div class="bubble bubble-msg-right">
                  {{ msg.message }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-scrollbar>
    </div>
    <el-divider></el-divider>
    <div class="bottom">
      <div class="text-box">
        <textarea class="message-text"
                  v-model="msg"
                  placeholder="请输入消息"
                  wrap="soft"
                  @keydown.enter="sendMsg">
        </textarea>
      </div>
      <div class="send">
        <button @click="sendMsg"
                title="按下Enter键发送">发送</button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  onBeforeMount,
  onMounted,
  ref,
  reactive,
  toRefs,
  watch,
  nextTick,
} from "vue";
import { queryTeamMember } from "api/user.js";
import { pullMsg } from "api/system.js";
import { useStore } from "vuex";
import { ElScrollbar, ElAvatar, ElDivider, ElMessage } from "element-plus";
import moment from "moment";
export default {
  name: "Dialog",
  props: {
    contact: Object,
    msgList: Array,
  },
  components: {
    ElScrollbar,
    ElAvatar,
    ElDivider,
  },
  setup(props, { emit }) {
    const store = useStore();
    const content = ref(null);
    const data = reactive({
      msg: "",
      socket: null,
      bubbleMsg: "",
      interval: null,
      isEmptyText: true,
      url: `ws://localhost:10086/kanbanSystem/chat/${store.state.userInfo.id}`,
      baseURL: "http://localhost:10086/kanbanSystem/file/exhibit/",
      avatar: store.state.userInfo.id + ".png",
    });

    const reconnect = () => {
      console.log("start to reconnect");
      setTimeout(function () {
        //没连接上会一直重连，设置延迟避免请求过多
        data.socket = new WebSocket(data.url);
      }, 3000);
    };

    onBeforeMount(() => {
      // 渲染界面时，根据用户id获取websocket连接
      data.socket = new WebSocket(data.url);

      data.socket.onmessage = (e) => {
        props.msgList.push(JSON.parse(e.data));
      };

      data.socket.onopen = () => {
        console.log("chat socket is on open");
      };

      data.socket.onclose = () => {
        console.log("chat socket is on close");
        reconnect();
      };

      data.socket.onerror = () => {
        console.log("chat socket is on error");
        reconnect();
      };

      // 每隔一段时间自动从信箱中获取信息
      // data.interval = setInterval(() => {
      //   pullMsg({
      //     origin: store.state.userInfo.id,
      //     destination: props.contact.id,
      //   }).then((res) => {
      //     if (res.data === 200) props.msgList = res.data.content;
      //   });
      // }, 3000);
    });

    const sendMsg = (e) => {
      if (e) e.preventDefault();
      if (!data.msg) {
        ElMessage.warning("信息不可为空！");
        return;
      }
      const entity = {
        origin: store.state.userInfo.id,
        destination: props.contact.id,
        message: data.msg,
        time: moment().format("yyyy-MM-DD HH:mm:ss"),
      };
      data.socket.send(JSON.stringify(entity));
      props.msgList.push(entity);
      data.msg = "";
    };

    watch(props, () => {
      nextTick(() => {
        content.value.setScrollTop(content.value.wrap$.scrollHeight);
      });
    });

    const errorHandler = () => true;

    return {
      errorHandler,
      content,
      sendMsg,
      ...toRefs(data),
    };
  },
};
</script>

<style lang="scss" scoped>
#dialog {
  width: 75%;
  height: 100%;
  float: right;

  .nickname {
    text-align: center;
    padding-top: 10px;
    font-size: 20px;
  }

  .content {
    .el-scrollbar {
      padding: 10px 5px;
    }

    .msg {
      margin: 25px 10px;

      & .bubble {
        width: fit-content;
        height: auto;
        border-radius: 5px;
        padding: 5px 15px;
        font-size: 18px;
        transition: 0.2s;
      }

      .time {
        font-size: 15px;
        text-align: center;
        color: #adadae;
      }

      .msg-left {
        overflow: hidden;
        .avatar {
          float: left;
        }
        .bubble-msg-left {
          float: left;
          color: #4b4b4b;
          background-color: #f5f7fb;
          margin-left: 10px;
        }
      }

      .msg-right {
        overflow: hidden;
        .avatar {
          float: right;
        }
        .bubble-msg-right {
          float: right;
          color: #fff;
          background-color: #1e6eff;
          margin-right: 10px;
        }
      }
    }
  }

  .bottom {
    .text-box {
      text-align: center;

      textarea {
        font-family: "Microsoft YaHei", sans-serif;
        font-size: 17px;
        resize: none;
        outline: none;
        border: none;
        box-sizing: border-box;
        width: 100%;
        height: 100px;
      }
    }
    .send {
      box-sizing: border-box;
      overflow: hidden;

      button {
        width: 70px;
        height: 35px;
        float: right;
        outline: none;
        border: none;
        background-color: #2663eb;
        border-radius: 5px;
        color: #fff;
        margin: 5px 20px;
        &:hover {
          cursor: pointer;
          background-color: lighten($color: #2663eb, $amount: 5%);
          transition: 0.2s;
        }
      }
    }
  }
}
</style>