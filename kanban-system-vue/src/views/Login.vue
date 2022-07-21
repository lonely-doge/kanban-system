<template>
  <div id="login">
    <div class="container">
      <h2>看板项目管理系统</h2>
      <el-form label-width="100px"
               ref="ruleFormRef"
               :model="ruleForm"
               :rules="rules">
        <el-form-item label="账号："
                      prop="account">
          <el-input type="text"
                    class="account"
                    placeholder="请输入账号"
                    :prefix-icon="User"
                    size="large"
                    v-model="ruleForm.account"></el-input>
        </el-form-item>
        <el-form-item label="密码："
                      prop="password">
          <el-input type="password"
                    class="password"
                    placeholder="请输入密码"
                    v-model="ruleForm.password"
                    :prefix-icon="Lock"
                    size="large"
                    show-password></el-input>
        </el-form-item>
        <el-form-item label-width="0"
                      class="loginBtn">
          <el-button type="primary"
                     size="large"
                     @click="submitForm">登录</el-button>
          <el-button size="large"
                     @click="toRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { unref, ref, reactive } from "vue";
import { ElForm, ElFormItem, ElInput, ElMessage } from "element-plus";
import { User, Lock } from "@element-plus/icons-vue";
import { createRouter, useRouter } from "vue-router";
import { useStore } from "vuex";
import { login } from "api/user.js";
export default {
  name: "Login",
  components: {
    ElForm,
    ElFormItem,
    ElInput,
    User,
    Lock,
    ElMessage,
  },
  setup() {
    const store = useStore();

    const router = useRouter();

    // 绑定表单dom对象
    const ruleFormRef = ref(null);

    // 定义表单校验规则
    const rules = {
      account: [
        {
          required: true,
          message: "请输入用户账号",
          trigger: "blur",
        },
      ],
      password: [
        {
          required: true,
          message: "请输入用户密码",
          trigger: "blur",
        },
      ],
    };

    // 定义响应式数据
    const ruleForm = reactive({
      account: "",
      password: "",
    });

    // 提交表单
    const submitForm = () => {
      ruleFormRef.value.validate((valid) => {
        if (valid) {
          login(ruleForm).then(({ data }) => {
            if (data.code === 200) {
              const { token, userInfo, role } = data.content;
              store.commit("setToken", token);
              store.commit("setUserInfo", userInfo);
              store.commit("setRole", role);
              ElMessage.success(data.msg);
              router.push("/home");
            } else {
              console.log("error");
              ElMessage.error(data.msg);
            }
          });
        } else {
          ElMessage.warning("请输入正确的账号和密码");
        }
      });
    };

    const toRegister = () => {
      router.push("/register");
    };

    return {
      toRegister,
      ruleForm,
      submitForm,
      rules,
      ruleFormRef,
      User,
      Lock,
    };
  },
};
</script>

<style lang="scss" scoped>
#login {
  width: 100vw;
  height: 100vh;
  background: {
    image: url(../assets/images/loginBG.svg);
    repeat: no-repeat;
    position: left bottom;
  }

  .container {
    width: 60%;
    height: 50%;
    position: absolute;
    transform: translate(-50%, -50%);
    left: 50%;
    top: 50%;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba($color: #000000, $alpha: 0.2);
    background-color: #fff;

    h2 {
      text-align: center;
      font-family: "方正悠黑";
      font-size: 45px;
    }

    .el-form {
      .el-form-item {
        margin: 10px 15px;

        &.loginBtn {
          margin: 50px 0;

          .el-button {
            font-size: 20px;
            margin: auto;
            width: 175px;
            height: 70px;
          }
        }

        .el-input {
          padding: 5px 5px;
        }
      }
    }
  }
}
</style>