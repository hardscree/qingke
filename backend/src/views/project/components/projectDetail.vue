<template>
  <div>
  <el-form ref="postForm" :model="postForm" :rules="rules" status-icon
     style="margin:20px 20px 0px 20px" label-width="110px">
  <el-row>
    <el-col :span="8">
      <el-form-item label="项目名称" prop="pName">
      <el-input v-model="postForm.pName" v-if="isEdit" placeholder="请输入项目名称"
      maxlength=50></el-input>
      <span v-else>{{postForm.pName}}</span>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="项目负责人" prop="pPrincipal">
      <el-input v-model="postForm.pPrincipal" v-if="isEdit"
      placeholder="请输入项目负责人姓名" maxlength="50"></el-input>
      <span v-else>{{postForm.pPrincipal}}</span>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="项目类型">
      <el-radio-group v-model="postForm.pType" v-if="isEdit">
        <el-radio :label="0">不过流水</el-radio>
        <el-radio :label="1">过流水</el-radio>
      </el-radio-group>
      <span v-else>{{postForm.pType===0?"不过流水":"过流水"}}</span>
      </el-form-item>
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="8">
      <el-form-item label="企业名称" prop="etNumb">
      <el-select
        v-model="postForm.etNumb"
        filterable
        allow-create
        default-first-option
        remote clearable
        reserve-keyword
        placeholder="请输入企业关键词"
        :remote-method="searchEnterprise"
        :loading="isLoading" style="width:100%"
        v-if="isEdit">
        <el-option
          v-for="item in enterpriseList"
          :key="item.etNumb"
          :label="item.etSname"
          :value="item.etNumb">
        </el-option>
      </el-select>
      <span v-else>{{postForm.enterpriseName}}</span>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="企业负责人" prop="etPrincipal">
      <el-input v-model="postForm.etPrincipal" v-if="isEdit" placeholder="请输入企业负责人姓名" maxlength="50"></el-input>
      <span v-else>{{postForm.etPrincipal}}</span>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="联系方式" prop="etPhone">
      <el-input v-model="postForm.etPhone" v-if="isEdit" placeholder="请输入企业负责人联系方式" maxlength="50"></el-input>
      <span v-else>{{postForm.etPhone}}</span>
      </el-form-item>
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="16">
      <el-form-item label="项目有效期" prop="dateRange">
        <el-date-picker
        v-model="postForm.dateRange"
        type="daterange"
        value-format="yyyy-MM-dd"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期" style="    margin-bottom: 10px;
        vertical-align: middle;" v-if="isEdit">
        </el-date-picker>

        <span v-else>
          <!-- {{(postForm.beginTime==""|| postForm.beginTime==undefined?"":postForm.beginTime.substring(0,10)) +" 至 " + (postForm.endTime==""?"":postForm.endTime.substring(0,10))}}</span> -->
          {{postForm.beginTime | formatDate('{y}-{m}-{d}')}} &nbsp;至&nbsp;{{postForm.endTime | parseTime('{y}-{m}-{d}')}}
        </span>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="项目人数">
      <el-input-number v-model="postForm.qkQuantity" v-if="isEdit" :controls="false"></el-input-number>
      <span v-else>{{postForm.qkQuantity}}</span>
      </el-form-item>
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="8" style="display: inline-flex;">
      <el-form-item label="服务费">
      <el-input-number v-model="postForm.serviceCharge" v-if="isEdit" :controls="false" :precision="2"></el-input-number>
      <span v-else>{{postForm.serviceCharge}}</span>
      <span>%</span>
      </el-form-item>
    </el-col>
    <el-col :span="8" style="display: inline-flex;">
      <el-form-item label="开户费">
      <el-input-number v-model="postForm.openAccount" v-if="isEdit" :controls="false" :precision="2"></el-input-number>
      <span v-else>{{postForm.openAccount}}</span>
      <span>元</span>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="付款方式">
      <el-radio-group v-model="postForm.paymentType" v-if="isEdit">
        <el-radio :label="0">预付</el-radio>
        <el-radio :label="1">垫付</el-radio>
      </el-radio-group>
      <span v-else>{{postForm.paymentType==0?"预付":"垫付"}}</span>
      </el-form-item>
    </el-col>
  </el-row>
    <el-row v-if="!isNew">
    <el-col :span="8">
      <el-form-item label="创建人">
        {{postForm.pOperator}}
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="创建时间">
        {{ postForm.createTime | parseTime('{y}-{m}-{d}')}}
      </el-form-item>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <el-form-item label="项目内容" prop="pContent" >
        <el-input type="textarea" v-model="postForm.pContent"
        :autosize="{ minRows: 3}" maxlength="500"
        :disabled="!isEdit"></el-input>
      </el-form-item>
    </el-col>
  </el-row>
  <!-- <el-row>
    <el-col :span="8">
      <el-form-item label="项目外包协议" prop="protocol_xm">
      <el-upload class="upload-demo" :action="uploadUrl" :disabled="!isEdit"
      :file-list="imageList1" list-type="picture" :on-preview="handlePictureCardPreview"
      :multiple="true" :on-change="handleChanged1"
      accept="image/png, image/jpeg"
      :data="{protocolType:1,path:'protocol'}">
      <el-button size="small" type="success"><span v-if="isEdit">上传</span>项目外包协议</el-button>
      <div slot="tip" class="el-upload__tip" v-if="isEdit">只能上传jpg/png文件，且不超过2M</div>
      </el-upload>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="个体代办协议" prop="protocol_gt">
      <el-upload class="upload-demo" :action="uploadUrl" :disabled="!isEdit"
      :file-list="imageList2" list-type="picture" :on-preview="handlePictureCardPreview"
      :multiple="true" :on-change="handleChanged2"
      accept="image/png, image/jpeg"
      :data="{protocolType:2,path:'protocol'}">
      <el-button size="small" type="warning"><span v-if="isEdit">上传</span>个体代办协议</el-button>
      <div slot="tip" class="el-upload__tip" v-if="isEdit">只能上传jpg/png文件，且不超过2M</div>
      </el-upload>
      </el-form-item>
    </el-col>
    <el-col :span="8">
      <el-form-item label="平台合作协议" prop="protocol_pt">
      <el-upload class="upload-demo" :action="uploadUrl" :disabled="!isEdit"
      :file-list="imageList3" list-type="picture" :on-preview="handlePictureCardPreview"
      :multiple="true" :on-change="handleChanged3"
      accept="image/png, image/jpeg"
      :data="{protocolType:3,path:'protocol'}">
      <el-button size="small" type="danger"><span v-if="isEdit">上传</span>平台合作协议</el-button>
      <div slot="tip" class="el-upload__tip" v-if="isEdit">只能上传jpg/png文件，且不超过2M</div>
      </el-upload>
      </el-form-item>
    </el-col>
  </el-row> -->
  <!-- <el-form-item v-else label="协议文件">
    <el-col :span="8"><el-button size="small" type="success">查看项目外包协议</el-button></el-col>
    <el-col :span="8"><el-button size="small" type="success">查看项目外包协议</el-button></el-col>
    <el-col :span="8"><el-button size="small" type="success">查看项目外包协议</el-button></el-col>
  </el-form-item> -->

  <el-row>
    <el-col>
      <el-form-item label="项目外包协议" prop="protocol_xm">
        <tinymce v-model="protocol_xm" :disabled="!isEdit"></tinymce>
      </el-form-item>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <el-form-item label="个体代办协议" prop="protocol_gt">
        <tinymce v-model="protocol_gt" :disabled="!isEdit"></tinymce>
      </el-form-item>
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="8">
      <el-form-item label="平台合作协议" prop="protocol_pt">
      <el-upload class="upload-demo" :action="uploadUrl" :disabled="!isEdit"
      :file-list="imageList3" list-type="picture" :on-preview="handlePictureCardPreview"
      :multiple="true" :on-change="handleChanged3"
      accept="image/png, image/jpeg"
      :data="{protocolType:3,path:'protocol'}">
      <el-button size="small" type="danger"><span v-if="isEdit">上传</span>平台合作协议</el-button>
      <div slot="tip" class="el-upload__tip" v-if="isEdit">只能上传jpg/png文件，且不超过2M</div>
      </el-upload>
      </el-form-item>
    </el-col>
  </el-row>
  <!-- <el-row>
    <el-col>
      <el-form-item label="平台合作协议" prop="protocol_pt">
        <tinymce v-model="protocol_pt" :disabled="!isEdit"></tinymce>
      </el-form-item>
    </el-col>
  </el-row> -->
  <el-form-item label="项目二维码" v-if="!isNew">
    <img :src="qrcodeUrl" />
  </el-form-item>

  <el-form-item>
    <el-button type="primary" @click="onSubmit" v-if="isNew">立即创建</el-button>
    <!-- <el-button type="warning" @click="handleReset" v-if="isNew">清空</el-button> -->
    <el-button type="warning" @click="handleDiscard" v-if="postForm.pStatus!=5 && !isNew">作废</el-button>
  </el-form-item>
</el-form>
<!-- <el-dialog :visible="showProtocol"><ProtocolImage></ProtocolImage></el-dialog> -->
<el-dialog :visible.sync="showProtocol">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>
</div>
</template>


<script>
import { fetchDetail,addProject,makeQrcode,discard } from '@/api/project'
import { search } from '@/api/enterprise'
import { parseTime } from '@/utils'
import ProtocolImage from '@/components/protocolImage'
import Tinymce from '@/components/Tinymce'
const projectType = [{value:0,label:"不过流水"},{value:1,label:"过流水"}]
const defaultForm = {
    pNumb: 0,
    etNumb: "",
    pName: "",
    pType: 0,
    serviceCharge: 0,
    openAccount: 0,
    paymentType: 0,
    pPrincipal: "",
    etPrincipal: "",
    etPhone: "",
    createTime: "",
    beginTime: "",
    endTime: "",
    updateTime: "",
    pStatus: -1,
    qkQuantity: 0,
    qkLink: 0,
    pContent: "",
    pOperator: "admin",
    enterpriseName: "",
    protocolList:[],

  }
export default {
  props: {
    isEdit: {
      type: Boolean,
      default: false
    },
    isNew: {
      type: Boolean,
      default: false
    }
  },
  components: { ProtocolImage,Tinymce},
  data() {
    var validateProtocol = (rule, value, callback) => {
      let protocol_content = null;
      switch(rule.field)
      {
        case "protocol_xm":
          protocol_content = this.protocol_xm;
          break;
        case "protocol_gt":
          protocol_content = this.protocol_gt;
          break;
        case "protocol_pt":
          if(this.imageList3!=null && this.imageList3.length>0)
            protocol_content = "has";
          else
            protocol_content = this.protocol_pt;
          break;
      }
      if (protocol_content === null || protocol_content=="") {
        callback(new Error('请输入协议文件'));
      } else {
        callback();
      }
    };


    return {
      qrcodeUrl:"",
      protocol_xm:"",
      protocol_gt:"",
      protocol_pt:"",
      postForm: {

      },
      rules:{
        pName: [
          { required: true, message: '项目名称不能为空', trigger: 'blur' }
        ],
        etNumb: [
          { required: true, message: '请选择企业', trigger: 'change'}
        ],
        pPrincipal: [
          { required: true, message: '项目负责人不能为空', trigger: 'blur'}
        ],
        etPrincipal: [
          { required: true, message: '企业负责人不能为空', trigger: 'blur'}
        ],
        etPhone: [
          { required: true, message: '联系人不能为空', trigger: 'blur'}
        ],
        pContent: [
          { required: true, message: '项目内容不能为空', trigger: 'blur'}
        ],
        dateRange: [
          { required: true, message: '请选择有效期', trigger: 'change'}
        ],
        protocol_xm:[
          { validator:validateProtocol,trigger:'change',required:true}
        ],
        protocol_gt:[
          { validator:validateProtocol,trigger:'change' ,required:true}
        ],
        protocol_pt:[
          { validator:validateProtocol,trigger:'change',required:true }
        ]
      },
      uploadUrl:process.env.BASE_API+'/util/upload',
      enterpriseList:[],
      isLoading:false,
      showProtocol:false,
      imageList1:[],
      imageList2:[],
      imageList3:[],
      dialogImageUrl:""
    };
  },
  filters:{
    formatDate:function(value,format)
    {
      return parseTime(value,format);
    }
  },
  created(){

    if (!this.isNew) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.postForm = Object.assign({}, defaultForm)
    }
  },
  computed: {
    visitedViews() {
      return this.$store.state.tagsView.visitedViews
    }
  },
  methods: {
    onSubmit() {
      this.$refs["postForm"].validate(valid=>{
        if(valid)
        {
          this.postForm.protocolList = [];
          this.postForm.protocolList.push({
            proContent:this.protocol_xm,proType:1
          });
          this.postForm.protocolList.push({
            proContent:this.protocol_gt,proType:2
          });
          // this.postForm.protocolList.push({
          //   proContent:this.protocol_pt,proType:3
          // });
          // this.fillProtocolList(this.imageList1,1);
          // this.fillProtocolList(this.imageList2,2);
          this.fillProtocolList(this.imageList3,3);
          this.postForm.pStatus = 4;
          if(this.postForm.dateRange.length>1)
          {
            this.postForm.beginTime = this.postForm.dateRange[0];
            this.postForm.endTime = this.postForm.dateRange[1];
          }
          if(typeof(this.postForm.etNumb)!="number")
          {
            this.postForm.enterpriseName = this.postForm.etNumb;
            this.postForm.etNumb = -1;
          }
          addProject(this.postForm).then(res=>{
            let id = res.data.data;
            this.$message({
              message: '项目发布成功！',
              type: 'success'
            });
            makeQrcode(id).then(res=>{
              if(res.data.code==200)
              {
                this.$message({
                  message: '二维码生成成功！',
                  type: 'success'
                });
              }
            })
            this.closeSelectedTag(this.$route,id);
          });
        }
      })
    },
    fillProtocolList(imgList,type)
    {
      imgList.forEach(element=>{
        let protocol = {
          proPhotoUrl:element.response.data[0].url,
          proType:type
        }
        this.postForm.protocolList.push(protocol);
      });
    },
    fetchData(id) {
      fetchDetail(id).then(response => {
        // console.log(response);
        this.postForm = response.data.data;
        this.initImageList();
        this.qrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+this.postForm.wxTicket;
      }).catch(err => {
        console.log(err)
      })
    },
    initImageList()
    {

      if(this.postForm.protocolList!=null)
      {
        this.imageList1 = [];
        this.imageList2 = [];
        this.imageList3 = [];
        let index1=0;
        let index2=0;
        let index3=0;
        this.postForm.protocolList.forEach(element=>{

          if(element.proType===1)
          {
            index1++;
            this.imageList1.push({name:index1.toString(),url:process.env.BASE_API+"/"+element.proPhotoUrl})
            this.protocol_xm = element.proContent;
          }
          else if(element.proType==2)
          {
            index2++;
            this.protocol_gt = element.proContent;
            this.imageList2.push({name:index2.toString(),url:process.env.BASE_API+"/"+element.proPhotoUrl})
          }
          else if(element.proType===3)
          {
            index3++;
            this.protocol_pt = element.proContent;
            this.imageList3.push({name:index3.toString(),url:process.env.BASE_API+"/"+element.proPhotoUrl})
          }
        });
      }
    },
    searchEnterprise(key)
    {
      if(key!='')
      {
        this.isLoading = true;
        search(key).then(res=>{
          this.enterpriseList = res.data.data;
          // console.log(this.enterpriseList);
          this.isLoading = false;
        })
      }
    },
    handleChanged1(file, fileList) {

      file.type=1;
      this.imageList1 = fileList;
      // console.log("filelistAfter=",this.imageList1);
    },

    handleChanged2(file, fileList) {

      file.type=2;
      this.imageList2 = fileList;
        //console.log("filelistAfter=",this.fileList3);
    },
    handleChanged3(file, fileList) {

      file.type=3;
      this.imageList3 = fileList;
        //console.log("filelistAfter=",this.fileList3);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.showProtocol = true;
    },
    closeSelectedTag(view,id) {
      this.$store.dispatch('delView', view).then(({ visitedViews }) => {
            this.$router.push('/project/detail/'+id);
      })
    },
    handleReset()
    {
      this.$refs["postForm"].resetFields();
    },
    handleDiscard()
    {
      this.$confirm('您确定将'+this.postForm.pName+'作废，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        discard(this.postForm.pNumb).then(res=>{
          if(res.data.data === true)
          {
            this.$message({
              type: 'success',
              message: this.postForm.pName+'已作废！'
            });
            this.postForm.pStatus = 5;
          }
          else
          {
            this.$message({
              type: 'error',
              message: this.postForm.pName+'作废失败！'
            });
          }

        })

      })
    }
  }
};
</script>
<style scoped>
  #span {
    font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
    font-size: 14px;
  }
</style>

