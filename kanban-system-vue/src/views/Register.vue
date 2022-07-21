<template>
  <div id="register">
    <div class="step-progress">
      <el-steps :active="active">
        <el-step title="Step 1"
                 description="个人基本信息" />
        <el-step title="Step 2"
                 description="团队信息" />
        <el-step title="Step 3"
                 description="注册提交" />
      </el-steps>
    </div>
    <el-form ref="ruleFormRef"
             :model="ruleForm"
             :rules="rules"
             label-width="120px"
             class="register-ruleForm">
      <h2>看板项目管理系统</h2>
      <h2>注册</h2>
      <div class="step1"
           v-if="active === 0">
        <el-form-item label="账户"
                      prop="account">
          <el-input v-model="ruleForm.account" />
        </el-form-item>
        <el-form-item label="密码"
                      prop="password">
          <el-input v-model="ruleForm.password"
                    type="password"
                    show-password />
        </el-form-item>
        <el-form-item label="昵称"
                      prop="nickname">
          <el-input v-model="ruleForm.nickname" />
        </el-form-item>
        <el-form-item label="性别"
                      prop="gender">
          <el-radio-group v-model="ruleForm.gender">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="option">
          <el-button type="primary"
                     @click="nextStep(ruleFormRef)">
            下一步
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">
            重置
          </el-button>
        </div>
      </div>
      <div class="step2"
           v-if="active === 1">
        <div class="step2-choose">
          <el-form-item prop="existTeam"
                        label-width="0">
            <el-radio-group v-model="ruleForm.existTeam">
              <el-radio label="createTeam"
                        size="large"
                        border>创建团队</el-radio>
              <el-radio label="joinTeam"
                        size="large"
                        border>加入团队</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="option">
          <el-button type="primary"
                     @click="previous">
            上一步
          </el-button>
          <el-button @click="nextStep(ruleFormRef)">
            下一步
          </el-button>
        </div>
      </div>

      <div class="step3"
           v-if="active === 2">
        <div class="step3-join"
             v-if="ruleForm.existTeam === 'joinTeam'">
          <el-form-item label="团队Id"
                        prop="teamId">
            <el-input v-model="ruleForm.teamId"></el-input>
          </el-form-item>
        </div>
        <div class="step3-create"
             v-if="ruleForm.existTeam === 'createTeam'">
          <el-form-item label="团队账号"
                        prop="teamAccount">
            <el-input v-model="ruleForm.teamAccount"></el-input>
          </el-form-item>
          <el-form-item label="团队名称"
                        prop="teamName">
            <el-input v-model="ruleForm.teamName"></el-input>
          </el-form-item>
        </div>
        <div class="option">
          <el-button type="primary"
                     @click="previous">
            上一步
          </el-button>
          <el-button type="primary"
                     @click="submitForm(ruleFormRef)">
            注册
          </el-button>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import { addTeam, queryTeam } from "api/team.js";
import { register } from "api/user.js";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import {
  ElInput,
  ElFormItem,
  ElButton,
  ElRadioGroup,
  ElRadio,
  ElCascader,
  ElSteps,
  ElStep,
  ElMessage,
} from "element-plus";
export default {
  name: "Register",
  components: {
    ElInput,
    ElFormItem,
    ElButton,
    ElRadioGroup,
    ElRadio,
    ElCascader,
    ElSteps,
    ElStep,
    ElMessage,
  },
  setup(props, { emit }) {
    const formSize = ref("default");
    const active = ref(0);
    const ruleFormRef = ref(null);
    const charValidator = (rule, value, callback) => {
      if (/[a-zA-z0-9]$/.test(value) === false) {
        ElMessage.warning("Please input english character or number!");
      } else {
        callback();
      }
    };
    const rules = reactive({
      account: [
        {
          required: true,
          message: "Please input account",
          trigger: "blur",
        },
        {
          min: 5,
          max: 10,
          message: "Length should be 5 to 10",
          trigger: "blur",
        },
        {
          validator: charValidator,
        },
      ],
      password: [
        {
          required: true,
          message: "Please input password",
          trigger: "blur",
        },
        {
          min: 8,
          max: 12,
          message: "Length should be 8 to 12",
          trigger: "blur",
        },
        {
          type: "string",
          message: "password must be string",
        },
      ],
      nickname: [
        {
          required: true,
          message: "Please input nickname",
          trigger: "blur",
        },
        {
          min: 2,
          max: 8,
          message: "Length should be 2 to 8",
          trigger: "blur",
        },
      ],
      gender: [
        {
          required: true,
          message: "Please choose your gender",
          trigger: "change",
        },
      ],
      teamAccount: [
        {
          required: true,
          message: "Please input team account",
          trigger: "blur",
        },
        {
          validator: charValidator,
        },
      ],
      teamName: [
        {
          required: true,
          message: "Please input team name",
          trigger: "blur",
        },
        {
          min: 3,
          max: 8,
          message: "Length should be 3 to 8",
          trigger: "blur",
        },
      ],
      teamId: [
        {
          required: true,
          message: "Please input team Id",
          trigger: "blur",
        },
        {
          validator: charValidator,
        },
      ],
      existTeam: [
        {
          required: true,
          message: "Please choose your situation",
          trigger: "change",
        },
      ],
    });
    const ruleForm = reactive({
      account: "",
      password: "",
      nickname: "",
      gender: "",
      teamId: "",
      teamAccount: "",
      teamName: "",
      existTeam: "",
    });
    const router = useRouter();
    const store = useStore();
    const submitForm = (formRef) => {
      formRef.validate((valid) => {
        if (valid) {
          if (ruleForm.existTeam === "createTeam") {
            addTeam({
              account: ruleForm.teamAccount,
              name: ruleForm.teamName,
            }).then((res) => {
              if (res.data.code === 200) {
                register({
                  account: ruleForm.account,
                  password: ruleForm.password,
                  nickName: ruleForm.nickname,
                  gender: ruleForm.gender,
                  teamId: res.data.content,
                  type: "create",
                }).then((res) => {
                  if (res.data.code === 200) {
                    store.commit("setUserInfo", res.data.content);
                    router.push("/login");
                    ElMessage.success("注册成功");
                  }
                });
              } else {
                ElMessage.error("创建团队失败");
                return;
              }
            });
          } else if (ruleForm.existTeam === "joinTeam") {
            queryTeam({ id: ruleForm.teamId }).then((res) => {
              if (res.data.code === 200) {
                register({
                  type: "join",
                  account: ruleForm.account,
                  password: ruleForm.password,
                  nickName: ruleForm.nickname,
                  gender: ruleForm.gender,
                  teamId: ruleForm.teamId,
                }).then((res) => {
                  store.commit("setUserInfo", res.data.content);
                  router.push("/login");
                  ElMessage.success("注册成功");
                });
              } else {
                ElMessage.warning("该团队不存在");
                return;
              }
            });
          }
        }
      });
    };

    const resetForm = (formRef) => {
      formRef.resetFields();
    };

    const nextStep = (formRef) => {
      formRef.validate((valid) => {
        if (valid) active.value += 1;
      });
    };
    const previous = () => {
      active.value -= 1;
    };
    return {
      previous,
      nextStep,
      active,
      formSize,
      ruleFormRef,
      rules,
      ruleForm,
      submitForm,
      resetForm,
    };
  },
};
</script>

<style scoped lang="scss">
#register {
  background-image: url(../assets/images/registerBG.svg);
  background-repeat: no-repeat;
  background-position: right bottom;
  width: 100vw;
  height: 100vh;
  overflow-y: hidden;

  .step-progress {
    .el-steps {
      padding: 0 10px;
      width: 60%;
      margin: 30px auto;
    }
  }

  .register-ruleForm {
    width: 50%;
    height: fit-content;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba($color: #000000, $alpha: 0.2);
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    padding: 20px 10px;

    h2 {
      text-align: center;
      font-size: 30px;
    }
    .el-form-item {
      .el-input {
        width: 90%;
      }
    }
    .option {
      width: 100%;
      text-align: center;
    }

    .step2 {
      .step2-choose {
        .el-form-item {
          .el-radio-group {
            margin: 0 auto;
          }
        }
      }
    }
  }
}
</style>