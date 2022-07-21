<template>
  <div id="task-content">
    <QuillEditor theme="snow"
                 toolbar="minimal"
                 ref="editor"
                 contentType="html"
                 :options="editorOption"
                 v-model:content="content"
                 @input="syncContent" />
    <div class="option-btn">
      <el-button class="save"
                 @click="saveDesc"
                 large="default"
                 type="primary">保存</el-button>
      <el-button class="cancel"
                 @click="cancel"
                 large="default">取消</el-button>
    </div>
  </div>
</template>

<script>
import "quill/dist/quill.snow.css";
import { QuillEditor } from "@vueup/vue-quill";
import { onBeforeMount, onMounted, reactive, ref } from "vue";
import { useStore } from "vuex";
export default {
  name: "TaskContent",
  props: {
    description: String,
  },
  components: {
    QuillEditor,
  },
  setup(props, { emit }) {
    const editor = ref(null);
    const content = ref("");
    const store = useStore();

    const editorOption = reactive({
      options: {
        placeholder: "请输入正文",
      },
    });

    onBeforeMount(() => {
      content.value = props.description;
    });
    const saveDesc = () => {
      // 保存编辑器的内容为草稿，同步到vuex
      store.commit("setBoardDraft", content.value);
    };
    const cancel = () => {
      // 清空编辑器的内容
      editor.value.setContents("");
    };
    const syncContent = () => {
      store.commit("setBoardDraft", content.value);
    };
    return {
      syncContent,
      editorOption,
      content,
      editor,
      saveDesc,
      cancel,
    };
  },
};
</script>

<style lang="scss">
#task-content {
  margin: 20px 0;
  .ql-container {
    height: 100px;
  }

  .option-btn {
    padding: 15px 20px;
  }
}
</style>