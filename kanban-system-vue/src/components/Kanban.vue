<template>
  <div id="kanban">
    <el-tabs v-model="activeTab"
             class="kanban-tabs"
             @tab-click="changeTab">
      <el-tab-pane label="Boards"
                   name="1">
        <div class="add-stage">
          <el-button type="primary"
                     :icon="CirclePlus"
                     @click="addStage">
            添加阶段
          </el-button>
        </div>
        <el-scrollbar>
          <div class="container">
            <div class="stageBox"
                 v-for="(item, key) in boardsMap"
                 :key="key">
              <div class="top">
                <div class="stage">{{key}}</div>
                <div class="addBtn"
                     @click="showAddBoard(key)"></div>
              </div>
              <div class="box-item">
                <draggable chosenClass="chosen"
                           ghostClass="ghost"
                           handle=".board"
                           :animation="200"
                           :item-key="key"
                           group="kanban"
                           :delay="200"
                           :move="onMove"
                           :list="item"
                           style="min-height:184px;display:block">
                  <template #item="{element}">
                    <Board :stage="element.stage"
                           :kind="element.kind"
                           :title="element.title"
                           :message="element.message"
                           @click="boardDetail(element)" />
                  </template>
                </draggable>
              </div>
            </div>
          </div>
        </el-scrollbar>
      </el-tab-pane>
      <el-tab-pane label="Files"
                   name="2">
        <h2>文件列表</h2>
      </el-tab-pane>
    </el-tabs>

    <board-detail v-if="isShow"
                  @close="close"
                  :item="selectBoard" />
    <add-board v-if="addBoard"
               @closeAddForm="closeAddForm"
               :stage="selectedStage" />
    <stage-board v-if="showStage"
                 @closeStage="closeStage" />
  </div>
</template>
<script>
import Board from "components/Board.vue";
import StageBoard from "components/StageBoard.vue";
import { queryStages } from "api/system.js";
import { queryBoardsByStage, updateStage } from "api/kanban.js";
import { ElMessage, ElScrollbar, ElTabs, ElTabPane } from "element-plus";
import { CirclePlus } from "@element-plus/icons-vue";
import {
  computed,
  onBeforeMount,
  reactive,
  toRefs,
  watch,
  ref,
  onMounted,
  onUnmounted,
} from "vue";
import { useStore } from "vuex";
import draggable from "vuedraggable";
import moment from "moment";
import BoardDetail from "components/BoardDetail.vue";
import AddBoard from "components/AddBoard.vue";
export default {
  name: "Kanban",
  components: {
    StageBoard,
    Board,
    ElMessage,
    draggable,
    BoardDetail,
    AddBoard,
    ElScrollbar,
    ElTabs,
    ElTabPane,
  },
  setup() {
    const data = reactive({
      boardsMap: null,
      isShow: false,
    });
    const activeTab = ref("1");

    const store = useStore();

    const addBoard = ref(false);
    const selectedStage = ref("");
    const showStage = ref(false);
    const showAddBoard = (stage) => {
      addBoard.value = true;
      selectedStage.value = stage;
    };

    onBeforeMount(() => {
      queryBoardsByStage({
        projectId: store.state.projectId,
        teamId: store.state.userInfo.teamId,
      }).then((res) => {
        if (res.data.code === 200) {
          data.boardsMap = res.data.content;
        } else {
          console.log("system error");
          ElMessage.error("系统异常");
        }
      });
    });

    const changeTab = () => {};

    const isDraggable = ref(false);

    const onMove = ({ draggedContext, relatedContext }) => {
      const origin = draggedContext.element;
      const target = relatedContext.component;
      origin.stage = target.itemKey;
      origin.updatedTime = moment().format("yyyy-MM-DD HH:mm:ss");
      updateStage(origin).then(({ data }) => {
        if (data.code === 200) {
          ElMessage.success("更新成功");
        } else {
          ElMessage.error(data.content ? data.content : data.msg);
          location.reload();
        }
      });
      return true;
    };

    const selectBoard = ref(null);

    const boardDetail = (element) => {
      data.isShow = true;
      selectBoard.value = element;
    };

    const close = (value) => {
      data.isShow = value;
    };

    const closeAddForm = (value) => {
      addBoard.value = value;
    };

    const addStage = () => {
      showStage.value = !showStage.value;
    };

    const closeStage = (value) => {
      showStage.value = value;
    };

    return {
      closeStage,
      showStage,
      addStage,
      activeTab,
      changeTab,
      selectBoard,
      closeAddForm,
      addBoard,
      showAddBoard,
      close,
      boardDetail,
      onMove,
      selectedStage,
      CirclePlus,
      ...toRefs(data),
    };
  },
};
</script>

<style lang="scss" scoped>
#kanban {
  .add-stage {
    overflow: hidden;
    .el-button {
      float: right;
    }
    margin: 5px 0;
  }

  .container {
    display: flex;

    position: relative;
    padding: 10px 0;

    .stageBox {
      margin: 0 20px;
      width: 270px;
      display: flex;
      flex-direction: column;
      background-color: #f9fafb;

      .top {
        overflow: hidden;
        margin-bottom: 20px;
        .stage {
          float: left;
          font-size: 17px;
        }
        .addBtn {
          float: right;
          width: 30px;
          height: 30px;
          background-color: #e5e7eb;
          border-radius: 50%;
          position: relative;
          &:hover {
            cursor: pointer;
          }
          &::after {
            content: "";
            border-radius: 20px;
            background-color: #7d808d;
            width: 3px;
            height: 60%;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
          }
          &::before {
            content: "";
            border-radius: 20px;
            background-color: #7d808d;
            width: 60%;
            height: 3px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
          }
        }
      }
      .box-item {
        flex-shrink: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        width: 270px;

        .chosen {
          border: 3px dashed #2663eb;
        }

        .ghost {
          border: 3px dashed #7d808d;
        }
      }
    }
  }
}
</style>