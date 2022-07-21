<template>
  <div class="comment-item">
    <div class="core-content"
         v-for="(item, i) in list"
         :key="item.id">
      <div class="avatar">
        <el-avatar shape="square"
                   :size="35"
                   @error="errorHandler"
                   :src="baseURL+item.userInfo.id+'.png'" />
      </div>
      <div class="name-label">
        <span v-if="type === 0">{{item.userInfo.nickName}}</span>
        <span class="replier"
              v-if="type === 1">
          {{item.userInfo.nickName}} 回复 {{parent.nickName}}
        </span>
      </div>
      <div class="time">{{item.createTime}}</div>
      <div class="content"
           @click="changeCommenter(item.userInfo, i)">{{item.content}}</div>
      <div class="reply-list"
           v-if="item.replyList">
        <comment-item :list="item.replyList"
                      :type="1"
                      :parent="item.userInfo" />
      </div>
    </div>
  </div>
</template>

<script>
import { ElAvatar } from "element-plus";
import { onBeforeMount, onMounted, ref } from "vue";
import bus from "@/libs/bus.js";
export default {
  name: "CommentItem",
  props: {
    list: Array,
    type: Number,
    parent: Object,
  },
  components: {
    ElAvatar,
  },
  setup(props, { emit }) {
    const baseURL = ref("http://localhost:10086/kanbanSystem/file/exhibit/");
    onBeforeMount(() => {});
    const changeCommenter = (userInfo, i) => {
      bus.emit("changeCommenter", { userInfo: userInfo, index: i });
    };
    const emptySrc = ref(
      "https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
    );
    const errorHandler = (e) => true;
    return {
      baseURL,
      emptySrc,
      errorHandler,
      changeCommenter,
    };
  },
};
</script>

<style scoped lang="scss">
.comment-item {
  .core-content {
    margin: 10px 0;
    .avatar {
      display: inline-block;
      vertical-align: top;
    }
    .name-label {
      display: inline-block;
      font-size: 17px;
      font-weight: bold;
      margin: 0 20px;
    }
    .time {
      display: inline-block;
      font-size: 15px;
      font-weight: 300;
    }
    .content {
      box-sizing: border-box;
      padding: 0 60px;
      font-size: 18px;
      &:hover {
        cursor: pointer;
      }
    }

    .reply-list {
      margin-left: 50px;
    }
  }
}
</style>