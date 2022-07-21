<template>
  <div id="project">
    <div class="container">
      <add-project v-if="showBoard"
                   @closeAddForm="closeAddForm" />
      <div class="empty"
           v-if="projects.length === 0">
        <el-empty description="还没有项目喔！" />
      </div>
      <div class="box"
           v-for="(item, i) in projects"
           :key="i"
           @dblclick="boardDetail(item)">
        <div class="name">{{item.name}}</div>
        <div class="description">{{item.description}}</div>
        <div class="tail">
          <div class="wip">wip: {{item.wip}}</div>
          <div class="check"
               @click="check(item.id)">
            <div v-if="item.id === checkId || item.isCheck">
              <el-icon :size="30"
                       color="#67C23A">
                <circle-check-filled />
              </el-icon>
            </div>
            <div v-else>
              <el-icon :size="30">
                <circle-check />
              </el-icon>
            </div>
          </div>
        </div>
      </div>
      <el-button type="primary"
                 :icon="CirclePlus"
                 size="large"
                 @click="showAddBoard">新建项目</el-button>
    </div>
    <project-detail v-if="detailVisible"
                    :project="popProject"
                    @closeProject="closeProject" />
  </div>
</template>

<script>
import ProjectDetail from "components/ProjectDetail.vue";
import { onBeforeMount, onMounted, ref, watch } from "vue";
import { queryProjects, checkProject } from "api/project.js";
import AddProject from "components/AddProject.vue";
import { useStore } from "vuex";
import { ElIcon, ElMessage, ElEmpty, ElButton } from "element-plus";
import {
  CircleCheckFilled,
  CircleCheck,
  CirclePlus,
} from "@element-plus/icons-vue";
export default {
  name: "Project",
  components: {
    ElIcon,
    CircleCheckFilled,
    CircleCheck,
    ElMessage,
    ElEmpty,
    ElButton,
    CirclePlus,
    AddProject,
    ProjectDetail,
  },
  setup(props, { emit }) {
    const projects = ref([]);
    const store = useStore();
    const checkId = ref(null);
    const showBoard = ref(false);
    const detailVisible = ref(false);
    const popProject = ref(null);
    const boardDetail = (project) => {
      if (store.state.role !== "PM") {
        ElMessage.warning("无权限，请联系项目经理授权");
        return;
      }
      popProject.value = project;
      detailVisible.value = !detailVisible.value;
    };
    onBeforeMount(async () => {
      await queryProjects({ id: store.state.userInfo.teamId }).then((res) => {
        res.data.content.forEach((element) => {
          if (element.isCheck === true) store.state.projectId = element.id;
          projects.value.push(element);
        });
      });
    });
    const check = (itemId) => {
      if (checkId.value === itemId) {
        ElMessage.warning("已选中当前项目");
        return;
      }
      projects.value
        .map((n) => {
          n.isCheck = false;
          return n;
        })
        .filter((n) => {
          return n.id === itemId;
        })
        .map((n) => {
          n.isCheck = true;
          store.commit("setProjectId", n.id);
        });
      checkProject(projects.value).then((res) => {
        if (res.data.code === 200) {
          checkId.value = itemId;
          store.commit("setProjectId", itemId);
          ElMessage.success("更换项目成功");
        } else {
          ElMessage.warning("更换项目失败");
        }
      });
    };
    const showAddBoard = () => {
      showBoard.value = true;
    };
    const closeAddForm = (value) => {
      showBoard.value = value;
    };
    const closeProject = (value) => {
      detailVisible.value = value;
    };
    return {
      detailVisible,
      popProject,
      closeProject,
      boardDetail,
      closeAddForm,
      showAddBoard,
      showBoard,
      checkId,
      check,
      projects,
      CirclePlus,
    };
  },
};
</script>

<style lang="scss" scoped>
#project {
  .container {
    display: flex;
    .box {
      width: 300px;
      height: 200px;
      box-shadow: 0 0 5px rgba($color: #000000, $alpha: 0.2);
      margin: 0 3%;
      padding: 20px 25px;
      border-radius: 10px;
      display: flex;
      flex-direction: column;
      &:hover {
        box-shadow: 0 0 5px rgba($color: #000000, $alpha: 0.3);
      }

      & > div {
        margin: 15px 0;
      }

      .name {
        font-size: 25px;
      }

      .description {
        @include ellipsis();
      }

      .tail {
        .check {
          transition: 0.2s;
          float: right;
          &:hover {
            cursor: pointer;
          }
        }
        .wip {
          float: left;
          font-size: 22px;
        }
      }
    }
  }
}
</style>