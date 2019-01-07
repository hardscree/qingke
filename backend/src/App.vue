<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default{
  name: 'App',
  mounted(){
    let lang='zh'
    this.$i18n.locale = lang
    this.$store.dispatch('setLanguage', lang)
  }
}
</script>
