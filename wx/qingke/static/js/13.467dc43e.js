webpackJsonp([13],{Lfjg:function(t,s){},SRM8:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var n=e("240R"),a=e("gyMJ"),i={name:"agreement_sign",components:{},data:function(){return{userAgree:!0,option:{penColor:"rgb(0, 0, 0)",backgroundColor:"rgba(0,0,0,0)"},pngData:null}},computed:{},beforeCreate:function(){this.$loading.show(),this.vars.refrech||this.$router.replace("/"),document.title="个体户签名"},mounted:function(){this.vars.nextstep=3,this.$loading.hide()},methods:{save:function(){var t=this;this.$refs.signature.isEmpty()?this.$toast.show("请您签名"):(this.$loading.show(),this.$data.pngData=this.$refs.signature.save(),a.a.postContract({pNumb:this.vars.loginInfos.projectQingke.pNumb,data:this.$data.pngData,qkNumb:this.vars.loginInfos.qingke.qkNumb}).then(function(s){t.vars.nextstep++,n.a.$emit("nextneed")}).catch(function(s){t.$toast.show(s.message)}))},clear:function(){this.$refs.signature.clear()}}},r=function(){var t=this,s=t.$createElement,e=t._self._c||s;return e("div",{staticClass:"app agreement_sign"},[e("div",{staticClass:"sign_content"},[e("h3",[t._v("\n      请在下方签名区签字！\n    ")]),t._v(" "),e("div",{staticClass:"sign_area_content"},[e("div",{staticClass:"sign_box"},[e("p",[t._v("签名区")]),t._v(" "),e("vueSignature",{ref:"signature",attrs:{id:"sign_box_area",sigOption:t.option}})],1),t._v(" "),e("div",{staticClass:"sign_btn"},[e("a",{staticClass:"cancel",attrs:{href:"javascript:;"},on:{click:t.clear}}),t._v(" "),e("a",{staticClass:"confirm",attrs:{href:"javascript:;"},on:{click:t.save}})])])])])};r._withStripped=!0;var o={render:r,staticRenderFns:[]},c=o;var u=!1;var g=e("VU/8")(i,c,!1,function(t){u||e("Lfjg")},null,null);g.options.__file="src/views/agreement_sign.vue";s.default=g.exports}});