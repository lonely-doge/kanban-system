<template>
  <div id="admin">
    <h2 style="text-align:center;margin: 0;">管理员界面</h2>
    <div class="teams">
      <div class="team-item"
           v-for="team in teamList"
           :key="team.id">
        <div class="teamId">
          <span class="label">
            团队id：
          </span>
          <span class="msg">
            {{team.id}}
          </span>
        </div>
        <div class="account">
          <span class="label">
            团队账号：
          </span>
          <span class="msg">
            {{team.account}}
          </span>
        </div>
        <div class="name">
          <span class="label">团队名称：</span>
          <span class="msg">{{team.name}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { teamInfoList } from "api/team.js";
import { queryTeamMember } from "api/user.js";
import { onBeforeMount, onMounted, ref } from "vue";
import { ElMessage, ElSelect, ElOption } from "element-plus";
export default {
  name: "Admin",
  components: {
    ElMessage,
    ElSelect,
    ElOption,
  },
  setup(props, { emit }) {
    const teamList = ref([]);
    const members = ref([]);
    onBeforeMount(() => {
      teamInfoList().then((res) => {
        if (res.data.code === 200) {
          teamList.value.push(...res.data.content);
        } else {
          ElMessage.error("获取团队列表失败");
        }
      });
    });
    return {
      teamList,
    };
  },
};
</script>

<style lang="scss" scoped>
#admin {
  width: 100vw;
  height: 100vh;
  max-height: 100vh;
  box-sizing: border-box;
  .teams {
    padding: 30px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    .team-item {
      margin: 20px 40px;
      width: 350px;
      height: 180px;
      border-radius: 10px;
      box-shadow: 0 0 5px rgba($color: #000000, $alpha: 0.3);
      padding: 20px 40px;

      &:hover {
        cursor: pointer;
        box-shadow: 0 0 7px rgba($color: #000000, $alpha: 0.4);
      }
      & div {
        margin: 10px 0;
        span.label {
          font-size: 22px;
          font-weight: bold;
        }
        span.msg {
          font-size: 17px;
        }
      }
    }
  }
}
</style>