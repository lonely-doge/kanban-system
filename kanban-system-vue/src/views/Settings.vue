<template>
  <div id="settings">
    <div class="bg-img"
         :style="{backgroundImage: 'url('+bgImg+')'}">
      <el-upload :action="uploadURL"
                 :on-success="uploadSuccess"
                 :show-file-list="false"
                 :data="fileArgs"
                 :headers="token"
                 :before-upload="beforeUpload"
                 @click="setFileType('BACKGROUND')">
        <el-icon :size="30"
                 color="#fff">
          <Picture />
        </el-icon>
      </el-upload>

    </div>
    <div class="header">
      <el-upload :action="uploadURL"
                 :on-success="uploadSuccess"
                 :show-file-list="false"
                 :data="fileArgs"
                 :headers="token"
                 :before-upload="beforeUpload"
                 @click="setFileType('AVATAR')">
        <el-avatar :src="avatar"
                   :size="250"
                   @error="errorHandler" />
      </el-upload>
    </div>
    <div class="information">
      <el-input v-model="teamId"
                disabled>
        <template #prepend>
          团队id
        </template>
      </el-input>
      <el-input v-model="nickname"
                placeholder="Please input your nickname">
        <template #prepend>
          昵称：
        </template>
      </el-input>
      <el-input v-model="signature"
                placeholder="Please input your signature">
        <template #prepend>
          个性签名：
        </template>
      </el-input>
    </div>
    <div class="option">
      <el-button type="primary"
                 @click="saveProfile">保存</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
import {
  ElAvatar,
  ElButton,
  ElMessage,
  ElInput,
  ElUpload,
  ElIcon,
  ElLoading,
} from "element-plus";
import { Picture } from "@element-plus/icons-vue";
import { useStore } from "vuex";
import { reactive, toRefs, ref } from "vue";
export default {
  name: "Settings",
  components: {
    ElAvatar,
    ElButton,
    ElInput,
    ElUpload,
    ElIcon,
    Picture,
    ElLoading,
    ElMessage,
  },
  setup(props, { emit }) {
    const store = useStore();
    const nickname = ref(store.state.userInfo.nickName);
    const signature = ref(store.state.userInfo.signature);
    const teamId = store.state.userInfo.teamId;
    const fileArgs = ref({
      type: "",
      userId: store.state.userInfo.id,
      boardId: "",
      teamId: "",
    });
    const token = ref({ Authorization: store.state.token });
    const bgImg = ref(
      "http://localhost:10086/kanbanSystem/file/exhibit/" +
        store.state.userInfo.id +
        "-bg.png"
    );
    const uploadURL = ref("http://localhost:10086/kanbanSystem/file/upload");
    const data = reactive({
      avatar:
        "http://localhost:10086/kanbanSystem/file/exhibit/" +
        store.state.userInfo.id +
        ".png",
    });
    const uploadSuccess = (res, file) => {
      if (res.code !== 200) {
        ElMessage.warning(res.msg);
        ElLoading.service().close();
      } else {
        store.commit("updateAvatar", store.state.userInfo.id + ".png");
        ElMessage.success("图片修改成功");
        location.reload();
      }
    };
    const beforeUpload = (file) => {
      ElLoading.service({
        fullscreen: true,
        lock: true,
        text: "正在上传新的图片",
        background: "rgba(0, 0, 0, 0.6)",
      });
    };

    const setFileType = (type) => {
      fileArgs.value.type = type;
    };
    const errorHandler = () => true;

    const saveProfile = () => {};

    const cancel = () => {};
    return {
      teamId,
      saveProfile,
      cancel,
      nickname,
      signature,
      setFileType,
      fileArgs,
      errorHandler,
      beforeUpload,
      uploadURL,
      token,
      uploadSuccess,
      ...toRefs(data),
      bgImg,
    };
  },
};
</script>

<style lang="scss" scoped>
#settings {
  .bg-img {
    overflow: hidden;
    background: {
      repeat: no-repeat;
      size: 100% 100%;
    }
    width: 100%;
    height: 200px;
    position: relative;

    .el-upload {
      .el-icon {
        padding: 10px;
        background-color: rgba($color: #000000, $alpha: 0.3);
        position: absolute;
        right: 10px;
        bottom: 10px;
        &:hover {
          cursor: pointer;
        }
      }
    }
  }

  .header {
    position: relative;
    .el-avatar {
      position: relative;
      bottom: 100px;
    }
  }

  .information {
    .el-input {
      width: 70%;
      font-size: 20px;
      margin: 20px 0;
    }
  }

  .option {
    text-align: right;
    margin: 20px 0;
    padding: 10px 0;

    .el-button {
      font-size: 25px;
      width: 170px;
      height: 75px;
    }
  }
}
</style>