<template>
  <ProjectDetail :isEdit="true" :isNew="true"/>
</template>

<script>
import ProjectDetail from './components/projectDetail.vue'

export default {
  name: 'newForm',
  components: { ProjectDetail }
}
</script>
