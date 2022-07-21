<template>
  <div id="project-detail">
    <div class="container">
      <div class="header">
        <span class="title">项目详情</span>
        <el-icon class="close"
                 @click="closeProject">
          <close-bold />
        </el-icon>
        <el-divider />
      </div>
      <div class="content">
        <div class="title">
          <el-input v-if="showTitle"
                    v-model="title"
                    autofocus="true"
                    @blur="showTitle = false" />
          <span v-else
                class="name">
            {{title}}
          </span>
          <el-icon @click="editTitle"
                   :size="25"
                   v-if="!showTitle">
            <edit />
          </el-icon>
        </div>
        <div class="describe">
          <el-input v-if="showDescribe"
                    v-model="describe"
                    autofocus="true"
                    @blur="showDescribe = false" />
          <span v-else
                class="name">
            {{describe}}
          </span>
          <el-icon @click="editDescribe"
                   :size="25"
                   v-if="!showDescribe">
            <edit />
          </el-icon>
        </div>
        <div class="select-member">
          <el-select multiple
                     value-key="id"
                     v-model="choseMem">
            <el-option v-for="item in members"
                       :key="item.id"
                       :label="item.nickName"
                       :value="item" />
          </el-select>
        </div>
        <el-container>
          <el-aside>
            <div class="members">
              <h5>项目成员</h5>
              <el-scrollbar height="300px">
                <el-radio class="member"
                          v-for="item in members"
                          :key="item.id"
                          v-model="member"
                          :label="item.id"
                          size="large"
                          border
                          @click="chooseMem(item.id)">{{item.nickName}}</el-radio>
              </el-scrollbar>
            </div>
          </el-aside>
          <el-main>
            <div class="roles">
              <h5>项目角色</h5>
              <el-scrollbar height="300px">
                <el-checkbox-group v-model="checkRoles"
                                   @change="changeRoles">
                  <div class="checkbox-item">
                    <el-checkbox v-for="item in roles"
                                 :key="item.roleId"
                                 :label="item.name"
                                 size="large"
                                 border
                                 class="role"
                                 :checked="checkRole(item.roleId)" />
                  </div>
                </el-checkbox-group>
              </el-scrollbar>
            </div>
          </el-main>
        </el-container>
        <div class="option-btn">
          <el-button type="primary"
                     size="large"
                     @click="saveProject">保存</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { CloseBold, Edit } from "@element-plus/icons-vue";
import {
  ElIcon,
  ElSelect,
  ElContainer,
  ElAside,
  ElMain,
  ElScrollbar,
  ElOption,
  ElCheckboxGroup,
  ElMessage,
} from "element-plus";
import { onBeforeMount, onMounted, ref } from "vue";
import {
  queryUserRoles,
  queryProjectMembers,
  queryAllRoles,
  saveProjectInfo,
} from "api/system.js";
import { useStore } from "vuex";
export default {
  name: "ProjectDetail",
  props: {
    project: Object,
  },
  components: {
    CloseBold,
    ElIcon,
    Edit,
    ElSelect,
    ElContainer,
    ElAside,
    ElMain,
    ElScrollbar,
    ElOption,
    ElCheckboxGroup,
    ElMessage,
  },
  setup(props, { emit }) {
    const title = ref(props.project.name);
    const showTitle = ref(false);
    const showDescribe = ref(false);
    const roles = ref([]);
    const checkRoles = ref([]);
    const members = ref([]);
    const member = ref(null);
    const describe = ref(props.project.description);
    const store = useStore();
    const userRoles = ref([]);
    const currentUser = ref("");
    const choseMem = ref([]);
    const editTitle = () => {
      showTitle.value = true;
    };
    const editDescribe = () => {
      showDescribe.value = true;
    };
    const closeProject = () => {
      emit("closeProject", false);
    };

    onBeforeMount(async () => {
      await queryProjectMembers({
        id: props.project.id,
      }).then((res) => {
        if (res.data.code === 200) {
          members.value.push(...res.data.content);
          choseMem.value = members.value;
        }
      });
      await queryAllRoles({
        id: store.state.userInfo.id,
      }).then((res) => {
        if (res.data.code === 200) {
          roles.value = [];
          console.log(res.data.content);
          roles.value.push(...res.data.content);
        }
      });
    });

    const chooseMem = async (id) => {
      currentUser.value = id;
      await queryUserRoles({
        id: id,
      }).then((res) => {
        if (res.data.code === 200) {
          checkRoles.value.push(...res.data.content);
        }
      });
    };

    const changeRoles = (value) => {
      console.log(value);
    };

    const checkRole = (roleId) => {
      return checkRoles.value.filter((n) => {
        return roleId === n.roleId;
      }).length === 1
        ? true
        : false;
    };

    const saveProject = () => {
      saveProjectInfo({
        projectId: props.project.id,
        title: title.value,
        describe: describe.value,
        userId: currentUser.value,
        members: members.value,
        roles: checkRoles.value
      }).then((res) => {
        if (res.data.code === 200) {
          ElMessage.success("修改成功");
          location.reload();
        } else {
          ElMessage.error("修改失败");
        }
      });
    };

    return {
      choseMem,
      userRoles,
      saveProject,
      checkRole,
      changeRoles,
      editDescribe,
      showDescribe,
      describe,
      chooseMem,
      checkRoles,
      member,
      title,
      showTitle,
      editTitle,
      closeProject,
      roles,
      members,
    };
  },
};
</script>

<style scoped lang="scss">
#project-detail {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba($color: #000000, $alpha: 0.6);
  .container {
    width: 800px;
    height: fit-content;
    background-color: #fff;
    position: absolute;
    transform: translate(-50%, -50%);
    left: 50%;
    top: 50%;
    border-radius: 10px;

    .header {
      padding: 5px 10px;
      text-align: center;
      .title {
        font-size: 27px;
        font-weight: 450;
        vertical-align: super;
      }
      .close {
        font-size: 30px;
        float: right;
        &:hover {
          cursor: pointer;
          transform: rotate(180deg);
          transition: 0.2s;
        }
      }
      .el-divider {
        margin: 10px 0;
      }
    }

    .content {
      padding: 5px 10px;
      .title {
        margin: 10px 0;
        .name {
          font-size: 23px;
          vertical-align: super;
        }
        .el-icon {
          font-size: 27px;
          margin-left: 20px;
          &:hover {
            cursor: pointer;
          }
        }
      }

      .describe {
        margin: 10px 0;
        .name {
          font-size: 23px;
          vertical-align: super;
        }
        .el-icon {
          font-size: 27px;
          margin-left: 20px;
          &:hover {
            cursor: pointer;
          }
        }
      }

      .members {
        .member.el-radio {
          .el-radio__input {
            font-size: 20px;
          }
          .el-radio__label {
            font-size: 20px;
          }
        }
      }

      .roles {
        .role.el-checkbox {
          width: 170px;
          text-align: center;
          margin: 15px 10px;
          font-size: 20px;
        }
      }

      .option-btn {
        width: 100%;
        text-align: center;
        margin: 20px 0;

        .el-button {
          font-size: 25px;
          width: 300px;
          height: 50px;
        }
      }
    }
  }
}
</style>