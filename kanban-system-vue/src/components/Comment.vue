<template>
  <div id="comment">
    <comment-content :comments="comments"
                     @change="changeCommenter" />
    <comment-txt-area :type="type"
                      :oldComment="oldComment"
                      @submit="addComment"
                      @cancel="cancelComment" />
  </div>
</template>

<script>
import { onMounted, ref, onBeforeUnmount, onBeforeMount } from "vue";
import CommentTxtArea from "components/CommentTxtArea.vue";
import CommentContent from "components/CommentContent.vue";
import { queryComments, insertComment } from "api/system.js";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";
import moment from "moment";
import bus from "@/libs/bus.js";
export default {
  name: "Comment",
  props: {
    boardId: String,
  },
  components: {
    CommentTxtArea,
    CommentContent,
  },
  setup(props, { emit }) {
    const store = useStore();
    const comments = ref([]);
    const type = ref(0);
    const oldComment = ref(null);
    const chooseIndex = ref(0);

    const changeCommenter = ({ userInfo, index }) => {
      oldComment.value = userInfo;
      chooseIndex.value = index;
      type.value = 1;
    };
    onBeforeMount(() => {
      bus.on("changeCommenter", changeCommenter);
      queryComments({ id: props.boardId }).then((res) => {
        if (res.data.code === 200) {
          if (res.data.content != null) {
            comments.value = res.data.content;
          }
        } else {
          ElMessage.error("获取评论失败");
        }
      });
    });
    // 在组件卸载之前移除监听
    onBeforeUnmount(() => {
      bus.off("changeCommenter");
    });

    const addComment = (comment) => {
      // 0 代表评论 1代表回复
      if (type.value === 0) {
        insertComment({
          parentId: 0,
          userInfo: store.state.userInfo,
          boardId: props.boardId,
          content: comment,
          replyList: [],
        }).then((res) => {
          if (res.data.code === 200) {
            comments.value.push({
              id: res.data.content,
              parentId: 0,
              userInfo: store.state.userInfo,
              boardId: props.boardId,
              content: comment,
              replyList: [],
              createTime: moment().format("yyyy-MM-DD HH:mm:ss"),
            });
          } else {
            ElMessage.error("评论失败");
          }
        });
      } else if (type.value === 1) {
        console.log(chooseIndex.value);
        type.value = 0;
        insertComment({
          parentId: comments.value[chooseIndex.value].id,
          userInfo: store.state.userInfo,
          boardId: props.boardId,
          content: comment,
        }).then((res) => {
          if (res.data.code === 200) {
            if (!comments.value[chooseIndex.value].replyList) {
              comments.value[chooseIndex.value].replyList = [];
            }
            comments.value[chooseIndex.value].replyList.push({
              parentId: comments.value[chooseIndex.value].id,
              userInfo: store.state.userInfo,
              boardId: props.boardId,
              content: comment,
              createTime: moment().format("yyyy-MM-DD HH:mm:ss"),
            });
          } else {
            ElMessage.error("评论失败");
          }
        });
      }
    };
    const cancelComment = () => {
      type.value = 0;
    };
    return {
      chooseIndex,
      type,
      oldComment,
      changeCommenter,
      addComment,
      cancelComment,
      comments,
    };
  },
};
</script>

<style scoped lang="scss">
</style>