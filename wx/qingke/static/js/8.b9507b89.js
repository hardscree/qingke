webpackJsonp([8],{"9qQr":function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n={name:"review_wait",components:{"v-banner":i("IXO9").a},data:function(){return{}},computed:{},beforeCreate:function(){this.$loading.show(),this.vars.refrech||this.$router.replace("/"),document.title="待审核"},mounted:function(){this.$loading.hide()},methods:{}},r=function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"app app-review-wait"},[t("v-banner",[t("img",{attrs:{src:i("eww6"),alt:""}})]),this._v(" "),t("h3",[this._v("资料已提交，预计3-5个工作日内审核完成")]),this._v(" "),t("p",[this._v("审核结果会通过短信告知或登录轻客微信服务号查看")])],1)};r._withStripped=!0;var a={render:r,staticRenderFns:[]},s=a;var o=!1;var c=i("VU/8")(n,s,!1,function(e){o||i("O3No")},null,null);c.options.__file="src/views/review_wait.vue";t.default=c.exports},O3No:function(e,t){},eww6:function(e,t,i){e.exports=i.p+"static/img/review_wait_banner.2b4629e.jpg"}});