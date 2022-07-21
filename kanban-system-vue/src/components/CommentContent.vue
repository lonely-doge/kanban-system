<template>
  <div id="comment-content">
    <h4>评论</h4>
    <p v-if="!comments">暂无评论</p>
    <div v-else>
      <el-scrollbar max-height="120px"
                    min-height="40px"
                    always>
        <comment-item :list="comments"
                      :type="0" />
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
import { ElScrollbar } from "element-plus";
import CommentItem from "components/CommentItem.vue";
export default {
  name: "CommentContent",
  props: {
    comments: Array,
  },
  components: {
    ElScrollbar,
    CommentItem,
  },
  setup(props, { emit }) {
    return {};
  },
};
</script>

<style scoped lang="scss">
</style>