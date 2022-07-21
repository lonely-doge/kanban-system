<template>
  <div id="chat">
    <Contact @choose="choose" />
    <div v-if="contact">
      <Dialog :contact="contact"
              :msgList="msgList" />
    </div>
  </div>
</template>

<script>
import Contact from "components/Contact.vue";
import Dialog from "components/Dialog.vue";
import { reactive, toRefs } from "vue";
import { pullMsg } from "api/system.js";
import { useStore } from "vuex";
export default {
  name: "Chat",
  components: {
    Contact,
    Dialog,
  },
  setup() {
    const store = useStore();

    const data = reactive({
      contact: null,
      msgList: [],
    });
    const choose = (user) => {
      data.contact = user;
      pullMsg({ origin: store.state.userInfo.id, destination: user.id })
        .then((res) => {
          data.msgList = res.data.content;
        })
        .catch((err) => {
          console.log(err);
        });
    };
    return {
      ...toRefs(data),
      choose,
    };
  },
};
</script>

<style lang="scss" scoped>
#chat {
  background-color: #fff;
  overflow: hidden;
}
</style>