<template>
  <div id="add-project">
    <div class="add-project-form">
      <el-form :model="ruleForm"
               :rules="rules"
               ref="ruleFormRef"
               label-width="120px"
               :size="formSize">
        <div class="close-btn"
             @click="closeAddForm">
          <el-icon :size="25">
            <close-bold />
          </el-icon>
        </div>
        <div class="title">
          <h2>添加项目</h2>
        </div>
        <el-form-item label="项目名称"
                      prop="name">
          <el-input v-model="ruleForm.name" />
        </el-form-item>
        <el-form-item label="项目描述"
                      prop="description">
          <el-input v-model="ruleForm.description" />
        </el-form-item>
        <el-form-item label="WIP">
          <el-tooltip effect="dark"
                      content="注: WIP的值一般为参与项目团队人员的两倍"
                      placement="right">
            <el-input-number v-model="wip"
                             :disabled="true" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="团队成员"
                      prop="members">
          <el-select v-model="choseMem"
                     multiple
                     value-key="id"
                     placeholder="Select"
                     style="width: 240px">
            <el-option v-for="item in ruleForm.members"
                       :key="item.id"
                       :value="item"
                       :label="item.nickName" />
          </el-select>
        </el-form-item>
        <div style="width: 100%;height: 1px"></div>
        <div class="subBtn">
          <el-button @click="submitForm(ruleFormRef)"
                     size="large"
                     type="primary">添加项目</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  ElForm,
  ElFormItem,
  ElTooltip,
  ElButton,
  ElSelect,
  ElInput,
  ElIcon,
  ElMessage,
  ElOption,
} from "element-plus";
import { CloseBold } from "@element-plus/icons-vue";
import { onBeforeMount, onMounted, reactive, ref, watch } from "vue";
import { useStore } from "vuex";
import { queryTeamMember } from "api/user.js";
import { addProject } from "api/project.js";
export default {
  name: "AddProject",
  components: {
    ElForm,
    ElFormItem,
    ElButton,
    ElSelect,
    ElInput,
    ElTooltip,
    ElIcon,
    CloseBold,
    ElMessage,
    ElOption,
  },
  setup(props, { emit }) {
    const formSize = ref("default");
    const store = useStore();

    const ruleForm = reactive({
      name: "",
      description: "",
      members: [],
    });
    const choseMem = ref([]);
    const wip = ref(1);
    onBeforeMount(() => {
      queryTeamMember({
        userId: store.state.userInfo.id,
        teamId: store.state.userInfo.teamId,
      }).then((res) => {
        if (res.data.code === 200) {
          ruleForm.members.push(...res.data.content);
        } else {
          ElMessage.error("系统错误");
        }
      });
    });
    const rules = reactive({
      name: [
        {
          required: true,
          message: "Please input project name",
          trigger: "blur",
        },
        {
          min: 3,
          max: 5,
          message: "Length should be 3 to 8",
          trigger: "blur",
        },
      ],
      description: [
        {
          required: true,
          message: "Please input project description",
          trigger: "blur",
        },
        {
          min: 5,
          max: 50,
          message: "Length should be 5 to 50",
          trigger: "blur",
        },
      ],
    });

    const closeAddForm = () => {
      emit("closeAddForm", false);
    };

    watch(choseMem, () => {
      choseMem.value.length === 0
        ? (wip.value = 1)
        : (wip.value = choseMem.value.length * 2);
    });

    const ruleFormRef = ref(null);

    const submitForm = (formRef) => {
      formRef.validate((valid) => {
        if (valid) {
          addProject({
            name: ruleForm.name,
            teamId: store.state.userInfo.teamId,
            description: ruleForm.description,
            wip: wip.value,
            isCheck: true,
            members: choseMem.value,
          }).then((res) => {
            if (res.data.code === 200) {
              ElMessage.success("添加项目成功");
              location.reload();
            } else {
              ElMessage.error("添加项目失败");
            }
          });
        }
      });
    };

    return {
      ruleFormRef,
      submitForm,
      choseMem,
      wip,
      closeAddForm,
      formSize,
      ruleForm,
      rules,
    };
  },
};
</script>

<style scoped lang="scss">
#add-project {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  background-color: rgba($color: #000000, $alpha: 0.6);
  .add-project-form {
    background-color: #fff;
    border-radius: 10px;
    width: 60%;
    height: fit-content;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 20px 20px;

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
      width: 100%;
      text-align: center;
    }
    .subBtn {
      width: 100%;
      text-align: center;
      margin: 20px 0;
      .el-button {
        box-sizing: border-box;
        font-size: 30px;
        width: 55%;
        height: 50px;
        padding: 5px 0;
      }
    }
  }
}
</style>