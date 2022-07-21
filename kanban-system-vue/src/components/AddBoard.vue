<template>
  <div id="add-board">
    <div class="add-form">
      <div class="close-btn"
           @click="closeAddForm">
        <el-icon :size="25">
          <close-bold />
        </el-icon>
      </div>
      <span class="title">添加卡片</span>
      <el-form ref="addBoardFormRef"
               :model="ruleForm"
               :rules="rules"
               label-width="120px">
        <el-form-item label="任务名称"
                      prop="taskName">
          <el-input v-model="ruleForm.taskName"></el-input>
        </el-form-item>
        <el-form-item label="任务简述"
                      prop="message">
          <el-input v-model="ruleForm.message"></el-input>
        </el-form-item>
        <el-form-item label="负责成员"
                      prop="respMember">
          <el-select v-model="chooseMem"
                     multiple
                     value-key="id"
                     placeholder="选择团队成员">
            <el-option v-for="item in ruleForm.respMember"
                       :key="item.id"
                       :value="item"
                       :label="item.nickName" />
          </el-select>
        </el-form-item>
        <el-form-item label="卡片类型">
          <el-select v-model="type"
                     placeholder="请选择卡片类型">
            <el-option v-for="(item, i) in ruleForm.cardTypes"
                       :key="i"
                       :label="item"
                       :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间期限">
          <el-date-picker v-model="timeLimit"
                          type="daterange"
                          range-separator="To"
                          start-placeholder="Start date"
                          end-placeholder="End date" />
        </el-form-item>

        <task-content @richTxt="richTxt" />
        <div class="subBtn">
          <button type="button"
                  @click="createCard(addBoardFormRef)">创建卡片</button>
        </div>
      </el-form>

    </div>
  </div>
</template>

<script>
import {
  ElIcon,
  ElFormItem,
  ElSelect,
  ElOption,
  ElForm,
  ElInput,
  ElMessage,
  ElDatePicker,
} from "element-plus";
import { CloseBold } from "@element-plus/icons-vue";
import { onBeforeMount, onMounted, reactive, ref } from "vue";
import TaskContent from "components/TaskContent.vue";
import { queryProjectMember } from "api/project.js";
import { addBoard } from "api/kanban.js";
import { useStore } from "vuex";
export default {
  name: "AddBoard",
  props: {
    stage: String,
  },
  components: {
    ElIcon,
    CloseBold,
    ElFormItem,
    ElForm,
    ElInput,
    ElSelect,
    ElOption,
    ElDatePicker,
    TaskContent,
    ElMessage,
  },
  setup(props, { emit }) {
    const store = useStore();
    const closeAddForm = () => {
      emit("closeAddForm", false);
    };
    const addBoardFormRef = ref(null);
    const ruleForm = reactive({
      taskName: "",
      respMember: [],
      cardTypes: ["Design", "Develop", "Test"],
      message: "",
    });
    const chooseMem = ref([]);
    const type = ref("");
    const timeLimit = ref(null);
    const rules = reactive({
      taskName: [
        {
          required: true,
          message: "Please input task name",
          trigger: "blur",
        },
      ],
      respMember: [
        {
          required: true,
          message: "Please choose responsible member",
          trigger: "change",
        },
      ],
      message: [
        {
          required: true,
          message: "Please input task message",
          trigger: "blur",
        },
      ],
    });
    onBeforeMount(() => {
      queryProjectMember({
        id: store.state.projectId,
      }).then((res) => {
        if (res.data.code === 200) {
          ruleForm.respMember.push(...res.data.content);
        } else {
          ElMessage.warning(res.data.msg);
        }
      });
    });
    const description = ref("");
    const richTxt = (value) => {
      description.value = value;
    };
    const createCard = (formRef) => {
      formRef.validate((valid) => {
        if (valid) {
          addBoard({
            kind: type.value,
            title: ruleForm.taskName,
            message: ruleForm.message,
            stage: props.stage,
            members: chooseMem.value,
            description: description.value,
            teamId: store.state.userInfo.teamId,
            projectId: store.state.projectId,
            startTime: timeLimit.value[0],
            endTime: timeLimit.value[1],
          }).then((res) => {
            if (res.data.code === 200) {
              location.reload();
              ElMessage.success("添加卡片成功");
            } else {
              ElMessage.error("添加卡片失败");
            }
          });
        }
      });
    };
    return {
      richTxt,
      description,
      type,
      chooseMem,
      timeLimit,
      createCard,
      rules,
      closeAddForm,
      addBoardFormRef,
      ruleForm,
    };
  },
};
</script>

<style scoped lang="scss">
#add-board {
  background-color: rgba($color: #000000, $alpha: 0.4);
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100vw;
  height: 100vh;
  z-index: 3;
  .add-form {
    width: 60%;
    height: fit-content;
    background-color: #fff;
    border-radius: 5px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    padding: 15px 20px;

    .close-btn {
      overflow: hidden;
      margin-bottom: 20px;
      .el-icon {
        float: right;
        &:hover {
          cursor: pointer;
          transform: rotate(180deg);
          transition: 0.2s;
        }
      }
    }

    .title {
      display: block;
      font-size: 25px;
      text-align: center;
      margin: 15px 0;
    }

    .subBtn {
      text-align: center;
      button {
        background-color: #0984e3;
        color: #fff;
        font-size: 27px;
        width: 40%;
        height: fit-content;
        text-align: center;
        border-radius: 10px;
        padding: 10px 0;
        outline: none;
        border: none;

        &:hover {
          cursor: pointer;
          background-color: lighten($color: #0984e3, $amount: 3%);
        }
      }
    }
  }
}
</style>