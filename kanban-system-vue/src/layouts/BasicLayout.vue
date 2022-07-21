<template>
  <el-container direction="horizontal">
    <!-- 中央区域 -->
    <el-main>
      <el-container direction="horizontal">
        <!-- 左侧导航栏 -->
        <el-aside :width="sideWidth">
          <el-menu :default-active="currentMenu"
                   :collapse="data.isFold"
                   active-text-color="#409EFF"
                   router>
            <el-menu-item v-for="(menu, name, i) in data.menus"
                          :key="i"
                          :index="'/'+menu.name"
                          :route="menu.name">
              {{menu.name}}
            </el-menu-item>
          </el-menu>
        </el-aside>
        <!-- 内容显示区域 -->
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import {
  ElHeader,
  ElFooter,
  ElContainer,
  ElAside,
  ElMain,
  ElMenu,
  ElMenuItem,
} from "element-plus";
import { Expand, Fold } from "@element-plus/icons-vue";
import { computed, onBeforeMount, onMounted, reactive, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { queryMenus } from "api/system.js";
import { useStore } from "vuex";
export default {
  name: "BasicLayout",
  components: {
    ElHeader,
    ElFooter,
    ElContainer,
    ElAside,
    ElMain,
    ElMenu,
    ElMenuItem,
    Expand,
    Fold,
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const data = reactive({
      isFold: false,
      menus: [],
    });

    const currentMenu = ref(route.path);

    watch(
      () => route.path,
      (newPath, oldPath) => {
        currentMenu.value = newPath;
      }
    );

    onBeforeMount(() => {
      queryMenus().then((res) => {
        let menus = res.data.content.filter((n) => {
          if (store.state.role === "Dev" && n.name === "Notification")
            return false;
          else return true;
        });
        data.menus.push(...menus);
      });
    });

    const changeFold = () => {
      data.isFold = !data.isFold;
    };

    const sideWidth = computed(() => {
      return data.isFold ? "65px" : "200px";
    });

    return {
      currentMenu,
      data,
      changeFold,
      sideWidth,
    };
  },
};
</script>

<style lang="scss" scoped>
.el-container {
  height: 100%;

  .el-header {
    background-color: lightblue;
    margin: 10px 20px;

    .el-icon {
      cursor: pointer;
      font-size: 20px;
    }
  }
  .el-main {
    .el-container {
      .el-aside {
        transition: 0.2s;
        border-right: 1px solid #dcdfe6;

        .el-menu-item {
          border-radius: 5px;
          margin: 5px 2px;
          &:hover {
            background-color: rgba($color: #000000, $alpha: 0.1);
            transition: 0.4s;
          }
        }
      }
    }
  }
}
</style>