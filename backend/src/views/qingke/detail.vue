<template>
  <div>
    <el-collapse :value="actives">
      <el-collapse-item title="  基本信息" name="1">
        <el-row>
          <el-col :span="6">姓名：{{qingKeInfo.qkName}}</el-col>
          <el-col :span="6">手机号码：{{qingKeInfo.qkPhone}}</el-col>
          <el-col :span="6">微信ID：{{qingKeInfo.qkOpenid}}</el-col>
          <el-col :span="6">创建时间：{{qingKeInfo.qkCreate| parseTime('{y}-{m}-{d}') }}</el-col>
        </el-row>
      </el-collapse-item>

      <el-collapse-item title="营业执照" name="2">
        <el-row>
          <el-col :span="8">
            <el-upload class="upload-demo" :action="uploadUrl"
             :on-success="uploadLicese"
             accept="image/png, image/jpeg"
            :data="{path:'license'}">
            <div v-if="qingKeInfo.qkLicenseStatus==0">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2M</div>
            </div>
            <div v-else><el-tag type="success">重新上传</el-tag></div>
            </el-upload>
            <img :src="absulotImageUrl" style="max-width:100%" />
          </el-col>
          <el-col :span="16">
            <el-row>
              <el-col :span="6" class="el-col-id">个体户名称:</el-col>
              <el-col :span="18"><el-input type="textarea" autosize v-model="qingKeInfo.qkSelfName"></el-input></el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">统一社会信用代码:</el-col>
              <el-col :span="18"><el-input v-model="qingKeInfo.qkCreditCode"></el-input></el-col>
            </el-row>
            <el-row>
              <el-col :span="24" style="text-align:center">
                <el-button type="success" @click="updateInfo('license')" v-if="licenseStatus==0">保存</el-button>
              </el-col>
            </el-row>
          </el-col>
        </el-row>

      </el-collapse-item>
      <el-collapse-item title="  身份证信息" name="3">
        <el-row>
          <el-col :span="8">
            <el-tag>人像图</el-tag>
            <div v-if="qingKeInfo.qkidFrontphotoUrl!=null && qingKeInfo.qkidFrontphotoUrl!=''">
             <img :src="getImageUrl(qingKeInfo.qkidFrontphotoUrl)" style="width:100%"/>
            </div>
            <div v-else class="waitUpload">
              身份证等待上传
            </div>
          </el-col>
          <el-col :span="16">
            <el-row>
              <el-col :span="6" class="el-col-id">姓名：</el-col>
              <el-col :span="18"><el-input v-model="qingKeInfo.qkName"></el-input></el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">性别：</el-col>
              <el-col :span="6">
                <el-select v-model="qingKeInfo.qkSex" placeholder="请选择">
                  <el-option :key="true" label="女" :value="true"/>
                  <el-option :key="false" label="男" :value="false"/>
                </el-select>
              </el-col>
              <el-col :span="6" class="el-col-id">民族：</el-col>
              <el-col :span="6"><el-input v-model="qingKeInfo.qkNation"></el-input></el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">出生日期：</el-col>

              <el-date-picker
                v-model="qingKeInfo.qkBirth"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期" style="padding-left:10px;padding-top:5px">
              </el-date-picker>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">地址：</el-col>
              <el-col :span="18">
                <el-input v-model="qingKeInfo.qkidAddress" type="textarea"
                :rows="2"></el-input>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">身份证号：</el-col>
              <el-col :span="18"><el-input v-model="qingKeInfo.qkId"></el-input></el-col>
            </el-row>

          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-tag>国徽图</el-tag>
            <div v-if="qingKeInfo.qkidBackphotoUrl!=null && qingKeInfo.qkidBackphotoUrl!=''">
             <img :src="getImageUrl(qingKeInfo.qkidBackphotoUrl)" style="width:100%"/>
            </div>
            <div v-else class="waitUpload">
              身份证等待上传
            </div>
          </el-col>
          <el-col :span="16">
            <el-row>
              <el-col :span="6" class="el-col-id">有效期：</el-col>
              <el-col :span="18">
                <span v-if="isNoExpire">{{qingKeInfo.qkidValidity}}</span>
                <el-date-picker
                  v-model="dateRange"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期" style="margin-bottom: 10px;
                vertical-align: middle;" v-else>
                </el-date-picker>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">有效时长：</el-col>
              <el-col :span="18">
                <span v-if="isNoExpire">长期</span>
                <span v-else>{{validYears}}&nbsp;年</span>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">到期时间：</el-col>
              <el-col :span="18">
                {{validDays>=60?"大于60天":"小于60天"}}
              </el-col>
            </el-row>

            <el-row v-if="qingKeInfo.qkidStatus>1">
          <el-col :span="6" class="el-col-id">审核意见：</el-col>
          <el-col :span="18" style="text-align:left">
            <el-button type="success" @click="approval('qkidStatus',true)" :disabled="qingKeInfo.qkidStatus>2 || !auditCheck()" v-if="qingKeInfo.qkidStatus==2 || qingKeInfo.qkidStatus==3">审核通过</el-button>
            <el-button type="danger" @click="approval('qkidStatus',false)" :disabled="qingKeInfo.qkidStatus>2" v-if="qingKeInfo.qkidStatus==2 || qingKeInfo.qkidStatus==4">审核不通过</el-button>
          </el-col>
        </el-row>
          </el-col>
        </el-row>

      </el-collapse-item>
      <el-collapse-item title="活体信息" name="4">
        <el-row>
          <el-col :span="16">
            <!-- <video-player  class="video-player vjs-custom-skin"
              ref="videoPlayer"
              :playsinline="true"
              :options="playerOptions" v-if="qingKeInfo.qkVideoUrl!=null && qingKeInfo.qkVideoUrl!=''">
            </video-player> -->
            <img :src="getImageUrl(qingKeInfo.qkVideoUrl)" v-if="qingKeInfo.qkVideoStatus!=1" style="width:100%">
            <div v-else class="waitUpload">
              视频等待上传
            </div>
          </el-col>
          <el-col :span="8" v-if="qingKeInfo.qkVideoStatus>1">
            <el-row>
              <el-col :span="6" class="el-col-id">匹配相似度：</el-col>
              <el-col :span="18">
                {{qingKeInfo.qkVideoRate}}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" class="el-col-id">审核意见：</el-col>
              <el-col :span="18">
                <el-button type="success" @click="approval('qkVideoStatus',true)" :disabled="qingKeInfo.qkVideoStatus>2" v-if="qingKeInfo.qkVideoStatus==3 || qingKeInfo.qkVideoStatus==2">审核通过</el-button>
                <el-button type="danger" @click="approval('qkVideoStatus',false)" :disabled="qingKeInfo.qkVideoStatus>2" v-if="qingKeInfo.qkVideoStatus==4 || qingKeInfo.qkVideoStatus==2">审核不通过</el-button>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-collapse-item>
      <el-collapse-item title="签名信息" name="5">
        <el-row>
          <el-col :span="16">
            <div v-if="qingKeInfo.qkSignUrl!=null && qingKeInfo.qkSignUrl!=''">
             <img :src="getImageUrl(qingKeInfo.qkSignUrl)" style="width:100%"/>
            </div>
            <div v-else class="waitUpload">
              签名等待上传
            </div>
          </el-col>
          <el-col :span="8" v-if="qingKeInfo.qkSignStatus>1">
            <el-col :span="6" class="el-col-id">审核意见：</el-col>
            <el-col :span="18">
              <el-button type="success" @click="approval('qkSignStatus',true)" :disabled="qingKeInfo.qkSignStatus>2" v-if="qingKeInfo.qkSignStatus==3 || qingKeInfo.qkSignStatus==2">审核通过</el-button>
              <el-button type="danger" @click="approval('qkSignStatus',false)" :disabled="qingKeInfo.qkSignStatus>2" v-if="qingKeInfo.qkSignStatus==4 || qingKeInfo.qkSignStatus==2">审核不通过</el-button>
            </el-col>
          </el-col>
        </el-row>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<style>
  .el-collapse-item__header
  {
    background-color:#909399;
    color:#FFFFFF;
    padding: 0px 0px 0px 10px;
  }
  .el-col{
    padding: 5px 10px 0 10px;
    line-height: 40px;
  }
  .el-collapse-item__content
  {
    padding-bottom: 0px;
  }
  .el-col-id{
    text-align:right;
  }
  .waitUpload{
    line-height: 100px;
    text-align: center;
    font-size: 18px;
    font-weight: 800;
    color:#EBEEF5;
  }
  .el-upload-list__item-name
  {
    display: none!important;
  }
</style>
<script>
import {fetchDetail,update,audit,getVideoUrl} from '@/api/qingke.js'
import { parseTime,stringToDate } from '@/utils'
// import 'video.js/dist/video-js.css'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
import { videoPlayer } from 'vue-video-player'
export default {
  components: {
    videoPlayer
  },
  data()
  {
    return{
      qingKeInfo:{},
      licenseStatus:0,
      uploadUrl:process.env.BASE_API+'/util/upload',
      actives:["1","2","3","4","5"],
      dateRange:[],
      playerOptions: {
        // videojs options
        muted: false,
        loop: false,
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [{
          type: "",
          src: ""
        }],

        notSupportedMessage: '此视频暂无法播放，请稍后再试',
      }
    }
  },
  created(){
    const id = this.$route.params && this.$route.params.id;
    this.getInfo(id);
  },
  computed:{
    isNoExpire:function(){
      if(this.qingKeInfo.qkidValidity===undefined || this.qingKeInfo.qkidValidity===null)
        return false;
      if(this.qingKeInfo.qkidValidity.indexOf("长期")>=0)
        return true;
      else
        return false;
    },
    absulotImageUrl:function(){
      if(this.qingKeInfo.qkLicenseUrl!=undefined && this.qingKeInfo.qkLicenseUrl!=null)
      {
        return process.env.BASE_IMGURL+"/"+this.qingKeInfo.qkLicenseUrl;
      }
      else
      {
        return "";
      }
    },
    validYears:function()
    {
      if(this.dateRange.length>1)
      {
        return parseInt(this.dateRange[1].getFullYear() - this.dateRange[0].getFullYear());
      }
    },
    age:function()
    {
      var toDay= new Date();
      var birth = stringToDate(this.qingKeInfo.qkBirth);
      return parseInt(toDay.getFullYear()-birth.getFullYear());

    },
    validDays:function()
    {
      if(this.isNoExpire)
        return 100;
      if(this.dateRange.length>1)
      {
        let toDay = new Date();
        let diff = this.dateRange[1].getTime() - toDay.getTime();
        return parseInt(diff/1000/60/60/24);
      }
      return 0;
    }
  },
  methods:{
    getInfo(id)
    {
      fetchDetail(id).then(res=>{
        this.qingKeInfo = res.data.data;
        this.dateRange=[];
        this.licenseStatus = this.qingKeInfo.qkLicenseStatus;
        this.qingKeInfo.qkBirth = parseTime(this.qingKeInfo.qkBirth,"{y}-{m}-{d}")
        
        if(this.qingKeInfo.qkidValidity!=null && this.qingKeInfo.qkidValidity!="")
        {
          let dates = this.qingKeInfo.qkidValidity.split('-')

          dates.forEach(element => {
            // console.log(element);
            let dateString=element
            if(element.length==8)
            {
              dateString = element.substring(0,4)+"/"+element.substring(4,6)+"/"+element.substring(6,8);

              this.dateRange.push(new Date(dateString));
            }
          });
        }
        // this.qingKeInfo.qkVideoUrl="https://wx.aivplus.com/qkimg/video/12_13667234-63d9-4505-aab5-81fe2def320b.mp4";
        if(this.qingKeInfo.qkVideoUrl!=null && this.qingKeInfo.qkVideoUrl!="")
        {
          this.playerOptions.sources[0].src = process.env.BASE_IMGURL+"/"+this.qingKeInfo.qkVideoUrl;
        }
        else if(this.qingKeInfo.qkVideoStatus!=1 && this.qingKeInfo.qkVideoStatus!=4)
        {
          getVideoUrl(this.qingKeInfo.qkNumb).then(res=>{
            this.qingKeInfo.qkVideoUrl = res.data.data;
            this.playerOptions.sources[0].src = process.env.BASE_IMGURL+"/"+this.qingKeInfo.qkVideoUrl;
          });
        }
        
        // this.playerOptions.sources[0].src = "https://wx.aivplus.com/qkimg/video/12_13667234-63d9-4505-aab5-81fe2def320b.mp4";
        // console.log(this.playerOptions);
      });
    },
    uploadLicese(response, file, fileList)
    {

      this.qingKeInfo.qkLicenseStatus=1;
      this.qingKeInfo.qkLicenseUrl = response.data[0].url;
    },
    getImageUrl(url)
    {
      if(url===undefined || url===null)
      {
        return '';
      }
      if(url.indexOf("http")===0)
      {
        return url;
      }
      let ret = process.env.BASE_IMGURL+"/"+url;
      return ret;
    },
    updateInfo(auditType)
    {
      if(this.dateRange.length>1)
      {
        this.qingKeInfo.qkidValidity = parseTime(this.dateRange[0],"{y}{m}{d}")+"-"+parseTime(this.dateRange[1],"{y}{m}{d}")
      }
      audit(auditType,this.qingKeInfo).then(res=>{
        // console.log(res);
        if(res.data.data == true)
        {
          this.qkLicenseStatus = this.qingKeInfo.qkLicenseStatus;
          this.$message({message:'更新成功.',type:'success'});
        }
        else{
          this.$message({message:'更新失败',type:'danger'});
        }
      })
    },
    auditCheck()
    {
      try
      {
        if(this.validDays<60)
        {
          return false;
        }
        let toDay = new Date();
        //let age = this.age;
        if(this.qingKeInfo.qkSex)
        {

          if(this.age>59 || this.age<18 )
            return false;
        }
        else{
          if(this.age>55 || this.age<18 )
            return false;
        }
      }
      catch(e)
      {
        return false;
      }
      return true;
    },
    approval(stateProperty,state)
    {
      if(state)
      {
        this.approvalAction(stateProperty,state);
      }
      else
      {
        this.$confirm('您确定该人信息不通过审核?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.approvalAction(stateProperty,state);
        }).catch(() => {
          
        });
      }
    },
    approvalAction(stateProperty,state)
    {
      let auditType="";
      if(state)
      {
        this.qingKeInfo[stateProperty] = 3;
      }
      else
      {
        this.qingKeInfo[stateProperty] = 4;
        
      }
      switch(stateProperty)
      {
        case "qkSignStatus":
          auditType = "sign";
          break;
        case "qkVideoStatus":
          auditType = "video";
          break;
        case "qkidStatus":
          auditType = "id";
          break;
      }
      this.updateInfo(auditType);

    }
  }

}
</script>

