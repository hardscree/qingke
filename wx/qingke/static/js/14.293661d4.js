webpackJsonp([14],{BQ8k:function(e,t){},fjoL:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});o("240R");var i=o("gyMJ"),n={name:"uploaduservideo",components:{},data:function(){},computed:{},beforeCreate:function(){this.$loading.show(),this.vars.refrech||this.$router.replace("/"),document.title="FaceId 身份验证"},mounted:function(){this.vars.nextstep=2;var e=this;i.a.getVideoToken({qkNumb:this.vars.loginInfos.qingke.qkNumb}).then(function(e){window.location.replace("https://api.megvii.com/faceid/lite/do?token="+e.data.token)}).catch(function(t){e.$toast.show(t.message)}),this.$loading.hide()},methods:{}},s=function(){var e=this.$createElement;return(this._self._c||e)("div",{staticClass:"uploaduservideo"})};s._withStripped=!0;var a={render:s,staticRenderFns:[]},r=a;var c=!1;var u=o("VU/8")(n,r,!1,function(e){c||o("BQ8k")},null,null);u.options.__file="src/views/uploaduservideo.vue";t.default=u.exports}});