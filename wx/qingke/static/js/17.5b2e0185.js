webpackJsonp([17],{oj1l:function(a,e){},wr1U:function(a,e,t){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=t("woOf"),r=t.n(i),s=t("240R"),n=t("gyMJ"),d={name:"app-userinfo_review",components:{},data:function(){return{username:this.vars.idcard_info.face.qkName,cardnumber:this.vars.idcard_info.face.qkId,birthday:this.vars.idcard_info.face.qkBirth,address:this.vars.idcard_info.face.qkIdAddress,valiDity_start:this.vars.idcard_info.back.qkIdValidity.split("-")[0],valiDity_end:this.vars.idcard_info.back.qkIdValidity.split("-")[1]}},computed:{},beforeCreate:function(){this.$loading.show(this.vars.idcard_info),this.vars.refrech||this.$router.replace("/"),document.title="个体户申请信息"},mounted:function(){this.vars.nextstep=1,this.$loading.hide()},methods:{subUserInfo:function(){var a=this;this.$loading.show(),this.vars.idcard_info.face.qkName=this.$data.username,this.vars.idcard_info.face.qkId=this.$data.cardnumber,this.vars.idcard_info.face.qkBirth=this.$data.birthday,this.vars.idcard_info.face.qkIdAddress=this.$data.address;var e=this.chageDate(this.vars.idcard_info.face.qkBirth);this.vars.idcard_info.face.qkBirth=e.format;var t=""+(parseInt(e.year)+18)+e.month+e.day,i=new Date,d=i.getFullYear(),o=i.getMonth()+1,c=i.getDate(),v=""+d+(o>9?o:"0"+o)+(c>9?c:"0"+c);if(t>v)this.$toast.show("不符合规定年龄，请重新上传身份证");else{if(-1==this.$data.valiDity_end.indexOf("长期")){var h=this.chageDate(this.$data.valiDity_end).format.replace(/-/g,"/"),l=(this.chageDate(this.$data.valiDity_start).format.replace(/-/g,"/"),this.chageDate(v).format.replace(/-/g,"/"));if(new Date(l)>new Date(h))return this.$loading.hide(),void this.$toast.show("不是有效身份证，请重新上传身份证");if((new Date(h)-new Date(l))/864e5<=60)return this.$loading.hide(),void this.$toast.show("距离有效截止日不到两个月，请重新上传身份证")}var u=r()(this.vars.idcard_info.face,this.vars.idcard_info.back);u.qkSex="男"==u.qkSex?0:1,u.qkNumb=this.vars.loginInfos.qingke.qkNumb,n.a.postIdCardInfo(u).then(function(e){a.$loading.hide(),a.vars.nextstep++,s.a.$emit("nextneed")}).catch(function(e){a.$loading.hide(),a.$toast.show(e.message)})}},chageDate:function(a){var e=a.substring(0,4),t=a.substring(4,6),i=a.substring(6,8);return{year:e,month:t,day:i,format:e+"-"+t+"-"+i}}}},o=function(){var a=this,e=a.$createElement,t=a._self._c||e;return t("div",{staticClass:"app app-userinfo_review"},[t("h3",[a._v("请确认以下信息准确，便于审核成功！")]),a._v(" "),t("ul",[t("li",[t("span",[a._v("姓名")]),a._v(" "),t("input",{directives:[{name:"model",rawName:"v-model",value:a.username,expression:"username"}],attrs:{type:"text",placeholder:"请输入姓名"},domProps:{value:a.username},on:{input:function(e){e.target.composing||(a.username=e.target.value)}}})]),a._v(" "),t("li",[t("span",[a._v("身份证号")]),a._v(" "),t("input",{directives:[{name:"model",rawName:"v-model",value:a.cardnumber,expression:"cardnumber"}],attrs:{type:"tel",placeholder:"请输入身份证号"},domProps:{value:a.cardnumber},on:{input:function(e){e.target.composing||(a.cardnumber=e.target.value)}}})]),a._v(" "),t("li",[t("span",[a._v("出生年月")]),a._v(" "),t("input",{directives:[{name:"model",rawName:"v-model",value:a.birthday,expression:"birthday"}],attrs:{type:"text",placeholder:"请输入出生日期"},domProps:{value:a.birthday},on:{input:function(e){e.target.composing||(a.birthday=e.target.value)}}})]),a._v(" "),t("li",[t("span",[a._v("地址")]),a._v(" "),t("p",[t("textarea",{directives:[{name:"model",rawName:"v-model",value:a.address,expression:"address"}],attrs:{placeholder:"请输入出生地址"},domProps:{value:a.address},on:{input:function(e){e.target.composing||(a.address=e.target.value)}}})])]),a._v(" "),t("li",[t("span",[a._v("证件有效期")]),a._v(" "),t("p",[t("b",[a._v(a._s(a.valiDity_start))]),a._v(" "),t("i",[a._v("至")]),a._v(" "),t("b",[a._v(a._s(a.valiDity_end))])])])]),a._v(" "),t("div",{staticClass:"sub_btns"},[t("a",{class:{disabled:!(a.username&&a.cardnumber&&a.birthday&&a.address)},attrs:{href:"javascript:;"},on:{click:a.subUserInfo}},[a._v("下一步")])])])};o._withStripped=!0;var c={render:o,staticRenderFns:[]},v=c;var h=!1;var l=t("VU/8")(d,v,!1,function(a){h||t("oj1l")},null,null);l.options.__file="src/views/userinfo_review.vue";e.default=l.exports}});