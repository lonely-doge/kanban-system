<template>
  <div id="dashboard">
    <Kanban />
  </div>
</template>

<script>
import Kanban from "components/Kanban.vue";
export default {
  name: "Dashboard",
  components: {
    Kanban,
  },
  setup() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
</style>