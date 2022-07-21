<template>
  <div id="comment-txt-area">
    <h3>发表评论</h3>
    <b v-if="type">你回复{{oldComment.nickName}}</b>
    <el-input v-model="commentText"
              :rows="2"
              type="textarea"
              resize="none"
              placeholder="Please input comment content" />
    <div class="option">
      <el-button size="default"
                 type="primary"
                 @click="addComment">发表</el-button>
      <el-button size="default"
                 @click="cancelComment">取消</el-button>
    </div>
  </div>
</template>

<script>
import { ElButton, ElInput } from "element-plus";
import { ref } from "vue";
export default {
  name: "CommentTxtArea",
  props: {
    type: Number,
    oldComment: Object,
  },
  components: {
    ElButton,
    ElInput,
  },
  setup(props, { emit }) {
    const commentText = ref("");
    const addComment = () => {
      emit("submit", commentText.value);
      commentText.value = "";
    };
    const cancelComment = () => {
      emit("cancel");
      commentText.value = "";
    };
    return {
      commentText,
      addComment,
      cancelComment,
    };
  },
};
</script>

<style scoped lang="scss">
#comment-txt-area {
  .option {
    margin: 20px 0;
  }
}
</style>