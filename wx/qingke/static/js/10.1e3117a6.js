webpackJsonp([10],{"3gIl":function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=i("IXO9"),r=i("240R"),a={name:"review_fail",components:{"v-banner":n.a},data:function(){return{fail_msg:"请点击修改重新上传"}},computed:{},beforeCreate:function(){this.$loading.show(),this.vars.refrech||this.$router.replace("/"),document.title="待审核"},mounted:function(){this.$loading.hide()},methods:{modify:function(){this.vars.fromReviewError=!0,r.a.$emit("nextneed")}}},s=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app app-review-fail"},[n("v-banner",[n("img",{attrs:{src:i("eww6"),alt:""}})]),e._v(" "),n("h3",[e._v("审核信息未通过")]),e._v(" "),n("p",[e._v(e._s(e.fail_msg))]),e._v(" "),n("a",{attrs:{href:"javascript:;"},on:{click:e.modify}},[e._v("点我修改")])],1)};s._withStripped=!0;var o={render:s,staticRenderFns:[]},c=o;var v=!1;var f=i("VU/8")(a,c,!1,function(e){v||i("zCg1")},null,null);f.options.__file="src/views/review_fail.vue";t.default=f.exports},eww6:function(e,t,i){e.exports=i.p+"static/img/review_wait_banner.2b4629e.jpg"},zCg1:function(e,t){}});