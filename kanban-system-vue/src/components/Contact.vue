<template>
  <div id="contact">
    <el-scrollbar height="100%">
      <div class="member-item"
           v-for="(item, i) in members"
           :key="item.id"
           @click="choose(i)"
           :class="{active: isActive(i)}">
        <el-avatar shape="square"
                   :size="50"
                   :src="baseURL+item.id+'.png'"
                   @error="errorHandler"></el-avatar>
        <span class="username">{{item.nickName}}</span>
      </div>
    </el-scrollbar>
  </div>
</template>

<script>
import { queryTeamMember } from "api/user.js";
import { computed, onBeforeMount, reactive, toRefs } from "vue";
import { useStore } from "vuex";
import { ElMessage, ElAvatar, ElScrollbar } from "element-plus";
export default {
  name: "Contact",
  components: {
    ElMessage,
    ElAvatar,
    ElScrollbar,
  },
  setup(props, { emit }) {
    const store = useStore();

    const data = reactive({
      members: [],
      baseURL: "http://localhost:10086/kanbanSystem/file/exhibit/",
      active: -1,
    });
    onBeforeMount(() => {
      queryTeamMember({
        teamId: store.state.userInfo.teamId,
        userId: store.state.userInfo.id,
      }).then((res) => {
        if (res.data.code === 200) {
          data.members.push(...res.data.content);
        } else {
          ElMessage.error("获取团队成员失败");
        }
      });
    });

    const isActive = (index) => {
      return data.active === index;
    };

    const choose = (index) => {
      data.active = index;
      emit("choose", data.members[index]);
    };

    const errorHandler = () => true;

    return {
      errorHandler,
      isActive,
      choose,
      ...toRefs(data),
    };
  },
};
</script>

<style lang="scss" scoped>
#contact {
  width: 25%;
  float: left;
  border-right: 2px solid $base-border;
  box-sizing: border-box;
  .member-item {
    width: 100%;
    padding: 5px 10px;
    border-bottom: 1px solid $base-border;
    position: relative;
    box-sizing: border-box;

    &.active {
      background-color: rgba($color: #000000, $alpha: 0.2);
    }

    &.active:hover {
      background-color: rgba($color: #000000, $alpha: 0.2);
    }

    &:hover {
      transition: 0.2s;
      background-color: rgba($color: #000000, $alpha: 0.05);
      cursor: pointer;
    }

    .username {
      margin: 3px 20px;
      font-size: 17px;
      vertical-align: top;
    }
  }
}
</style>
