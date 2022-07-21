<template>
  <div class="board">
    <span class="kind">{{kind}}</span>
    <div class="title">{{title}}</div>
    <div class="message">{{message}}</div>
  </div>
</template>

<script>
import { ChatDotSquare } from "@element-plus/icons-vue";
import { ElIcon } from "element-plus";
export default {
  name: "Board",
  components: {
    ChatDotSquare,
    ElIcon,
  },
  props: {
    stage: String,
    id: String,
    kind: String,
    title: String,
    message: String,
  },
  setup(props, { emit }) {
    return {};
  },
};
</script>

<style scoped lang="scss">
.board {
  width: 270px;
  position: relative;
  box-shadow: 0 0 5px rgba($color: #000, $alpha: 0.2);
  box-sizing: border-box;
  padding: 10px 15px;
  margin: 10px 0;

  &:hover {
    cursor: pointer;
    box-shadow: 0 0 6px rgba($color: #000000, $alpha: 0.3);
  }

  .title {
    @include ellipsis();
    font-weight: bold;
  }

  span.kind {
    display: inline-block;
    background-color: $design-kind;
    padding: 5% 10%;
    border-radius: 10px;
    color: #fff;
  }

  .message {
    @include ellipsis();
    margin: 30px 0;
  }
}
</style>
