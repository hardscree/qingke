<template>
  <ProjectDetail :isEdit="false" :isNew="false"/>
</template>

<script>
import ProjectDetail from './components/projectDetail.vue'

export default {
  name: 'EditForm',
  components: { ProjectDetail }
}
</script>
