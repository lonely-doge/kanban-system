<template>
  <div id="board-detail">
    <div class="detail">
      <div class="detail-box">
        <div class="detail-header">
          <el-icon class="close"
                   @click="closeDetail">
            <close-bold />
          </el-icon>
          <!-- <el-icon class="files">
            <Files />
          </el-icon> -->
        </div>
        <el-divider />
        <div class="detail-content">
          <div class="title">
            <el-input v-if="showTitle"
                      v-model="title"
                      @blur="showTitle = false" />
            <div v-else
                 class="name">
              {{title}}
            </div>
            <el-icon @click="editTitle"
                     :size="25"
                     v-if="!showTitle">
              <edit />
            </el-icon>
          </div>
          <div class="basic">
            <div class="stage">
              <el-select v-model="selectStage"
                         :placeholder="selectStage"
                         size="large">
                <el-option v-for="(stage, i) in stages"
                           :key="i"
                           :label="stage"
                           :value="stage" />
              </el-select>
            </div>
            <div class="members">
              <el-dropdown>
                <el-button size="large">
                  团队成员
                  <el-icon class="el-icon--right">
                    <arrow-down />
                  </el-icon>
                </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item v-for="m in item.members"
                                      :key="m.id">{{m.nickName}}</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
            <div class="time-picker">
              <el-date-picker v-model="timeLimit"
                              type="daterange"
                              range-separator="To"
                              start-placeholder="开始时间"
                              end-placeholder="截止时间"
                              size="large" />
            </div>
          </div>
          <task-content :description="item.description" />
          <comment :board-id="item.id" />
          <div class="saveBtn">
            <el-button type="primary"
                       size="large"
                       @click="saveBoard">保存修改</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  CloseBold,
  Edit,
  ChatLineSquare,
  ArrowDown,
  Files,
} from "@element-plus/icons-vue";
import { ref, onMounted, onBeforeMount } from "vue";
import {
  ElIcon,
  ElDivider,
  ElDropdown,
  ElDropdownMenu,
  ElDropdownItem,
  ElDatePicker,
  ElButton,
  ElScrollbar,
  ElSelect,
  ElOption,
  ElMessage,
  ElTabs,
  ElTabPane,
} from "element-plus";
import TaskContent from "components/TaskContent.vue";
import Comment from "components/Comment.vue";
import moment from "moment";
import { updateBoard } from "api/kanban.js";
import { useStore } from "vuex";
export default {
  name: "BoardDetail",
  props: {
    item: Object,
  },
  components: {
    ArrowDown,
    ElSelect,
    ElOption,
    Comment,
    ElIcon,
    CloseBold,
    ElTabs,
    ElTabPane,
    ElDivider,
    Edit,
    ElDropdown,
    ElDropdownMenu,
    ElDropdownItem,
    ElDatePicker,
    ElButton,
    ElScrollbar,
    ChatLineSquare,
    TaskContent,
    Files,
  },
  setup(props, { emit }) {
    const statusMenu = ref(null);
    const timeLimit = ref([
      moment(props.item.startTime),
      moment(props.item.endTime),
    ]);
    const store = useStore();
    const title = ref(props.item.title);
    const visible = ref(false);
    const stages = ref(["TODO", "IN PROCESS", "DONE"]);
    const selectStage = ref(props.item.stage);
    const closeDetail = () => {
      emit("close", false);
    };
    const showTitle = ref(false);
    const editTitle = () => {
      showTitle.value = true;
    };
    const changeStatus = () => {
      if (visible.value) statusMenu.value.handleClose();
      else statusMenu.value.handleOpen();
    };
    const handleVisible = () => {
      visible.value = !visible.value;
    };
    const saveBoard = () => {
      updateBoard({
        id: props.item.id,
        kind: props.item.kind,
        title: title.value,
        stage: selectStage.value,
        description: store.state.boardDraft,
        members: props.item.members,
        projectId: props.item.projectId,
        teamId: props.item.teamId,
        startTime: timeLimit.value[0],
        endTime: timeLimit.value[1],
      }).then((res) => {
        if (res.data.code === 200) {
          ElMessage.success("修改卡片成功");
          location.reload();
        } else {
          ElMessage.error("修改卡片失败");
        }
      });
    };

    return {
      title,
      showTitle,
      editTitle,
      saveBoard,
      selectStage,
      stages,
      timeLimit,
      visible,
      statusMenu,
      handleVisible,
      changeStatus,
      closeDetail,
    };
  },
};
</script>

<style lang="scss" scoped>
#board-detail {
  .detail {
    display: block;
    position: fixed;
    z-index: 999;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba($color: #000, $alpha: 0.3);

    .detail-box {
      width: 70%;
      height: fit-content;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      background-color: #fff;
      border-radius: 5px;

      .detail-header {
        overflow: hidden;
        .files {
          font-size: 20px;
          padding: 10px;
          float: right;
          border-radius: 5px;

          &:hover {
            cursor: pointer;
            background-color: rgba($color: #000, $alpha: 0.3);
            color: #fff;
          }
        }
        .close {
          font-size: 20px;
          padding: 10px;
          float: right;

          &:hover {
            cursor: pointer;
            transform: rotate(180deg);
            transition: 0.3s;
          }
        }
      }

      .el-divider {
        margin: 5px 0;
      }

      .detail-content {
        width: 100%;
        height: fit-content;
        padding: 20px 25px;
        box-sizing: border-box;

        .title {
          & > div {
            display: inline-block;
          }
          .name {
            font-size: 30px;
            vertical-align: baseline;
            padding: 10px 10px;
          }
          .el-icon {
            font-size: 30px;

            &:hover {
              cursor: pointer;
            }
          }
        }

        .basic {
          display: flex;
          & > div {
            margin: 5px 10px;
          }
        }

        .saveBtn {
          text-align: center;
        }
      }
    }
  }
}
</style>