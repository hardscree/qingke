<template>
  <div style="margin:10px 10px 10px 10px;">
    <el-card shadow="always" style="margin:0px 10px 0px 10px">
      <el-row>
        <el-col :span="6">
          项目名称：{{projectEntity.pName}}
        </el-col>
        <el-col :span="6">
          项目责任人：{{projectEntity.pPrincipal}}
        </el-col>
        <el-col :span="6">
          项目人数：{{projectEntity.qkQuantity}}
        </el-col>
        <el-col :span="6">
          客户负责人：{{projectEntity.etPrincipal}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          企业名称：{{projectEntity.enterpriseName}}
        </el-col>
        <el-col :span="6">
          联系方式：{{projectEntity.etPhone}}
        </el-col>
        <el-col :span="8">
          项目有效期：{{projectEntity.beginTime | parseTime("{y}-{m}-{d}")}}&nbsp;至&nbsp;{{projectEntity.endTime | parseTime("{y}-{m}-{d}")}}
        </el-col>
        <el-col :span="4">
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-document" @click="handleDownload" :disabled="downloadLoading">导出</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-row style="margin-top:10px">
      <el-col>
      <el-input placeholder="姓名" v-model="filters.name" style="width: 100px;" class="filter-item" />
      <el-input placeholder="身份证" v-model="filters.idCode" style="width: 200px;" class="filter-item" />
      <el-select v-model="filters.qkStatus" placeholder="轻客状态" clearable class="filter-item" style="width: 130px">
        <el-option  key="0" label="已注册" value="0"/>
        <el-option  key="1" label="审核中" value="1"/>
        <el-option  key="2" label="审核通过" value="2"/>
        <el-option  key="3" label="审核不通过" value="3"/>
      </el-select>
      <el-select v-model="filters.qkSignStatus" placeholder="项目签名状态" clearable class="filter-item" style="width: 130px">
        <el-option  key="1" label="未提交" value="1"/>
        <el-option  key="2" label="已提交" value="2"/>
        <el-option  key="3" label="审核通过" value="3"/>
        <el-option  key="4" label="审核不通过" value="4"/>
      </el-select>
      <el-button type="primary" @click="handleFilter">筛选</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
    <el-table :data=qingKeList border>
      <el-table-column label="姓名" prop="qkName"></el-table-column>
      <el-table-column label="关注时间">
        <template slot-scope="scope">
          {{scope.row.focusTime | parseTime("{y}-{m}-{d}")}}
        </template>
      </el-table-column>
      <el-table-column label="身份证号">
        <template slot-scope="scope">
          {{scope.row.qkId}}
        </template>
      </el-table-column>
      <el-table-column label="轻客状态">
        <template slot-scope="scope">
          {{getQingkeStateText(scope.row)}}
        </template>
      </el-table-column>
      <!-- <el-table-column label="状态更改时间">
        <template slot-scope="scope">
          {{scope.row.qkId}}
        </template>
      </el-table-column> -->
      <el-table-column label="签名状态">
        <template slot-scope="scope">
          {{getSignStateText(scope.row.qkSignStatus)}}
        </template>
      </el-table-column>
      <el-table-column label="签名更新时间">
        <template slot-scope="scope">
          {{scope.row.statusUpdatetime| parseTime("{y}-{m}-{d}")}}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" v-if="scope.row.qkSignStatus==2" @click="showSingWindow(scope.row)" size="small">审核签名</el-button>
        </template>
      </el-table-column>
    </el-table>
    </el-col>
    </el-row>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit"  @pagination="getQingKeList" :pageSizes="[5,10,20,50]"/>
    <el-dialog :visible.sync="showSign">
      <el-row>
        <el-col>
          <img :src="signUrl" width="500px"></img>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-button type="success" @click="approve(3)">审核通过</el-button>
          <el-button type="success" @click="approve(4)">审核不通过</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>
<style scoped>
  .el-col{
    padding: 10px;
  }
</style>

<script>
import {getProjectQingkeList,audit} from "@/api/project"
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import {parseTime} from '@/utils'
export default {
  directives: { waves },
  components: { Pagination },
  data(){
    return {
      downloadLoading:false,
      projectEntity:{},
      currentQingkd:{},
      showSign:false,
      signUrl:'',
      page:0,
      limit:10,
      filters:{},
      filtedList:[]

    }
  },
  created(){
    const id = this.$route.params && this.$route.params.id;
    this.getList(id);
  },
  computed:{
    total:function()
    {
      if(this.filtedList===undefined)
        return 0;
      return this.filtedList.length;
    },
    qingKeList:function()
    {
      return this.getQingKeList();
    }
  },
  methods:{
    getList(id)
    {
      getProjectQingkeList(id).then(res=>{
        console.log(res);
        this.projectEntity = res.data.data;
        this.page = 0;
        this.filtedList = this.filteList();
        console.log(this.filtedList)
      })
    },
    filteList()
    {

      if(this.projectEntity.qingkeList!=undefined)
      {
        return this.projectEntity.qingkeList.filter(element=>{
          try{
          if(this.filters.name!=null && this.filters.name!="")
          {
            if(element.qkName.indexOf(this.filters.name)<0)
              return false;
          }
          if(this.filters.idCode!=null && this.filters.idCode!="")
          {
            if(element.qkId.indexOf(this.filters.idCode)<0)
              return false;
          }
          switch(this.filters.qkStatus)
          {

            case "0":
              if(element.qkidStatus!=1 || element.qkVideoStatus!=1)
                return false;
              break;
            case "1":
              if(element.qkidStatus==element.qkVideoStatus)
                return false;
              break;
            case "2":
              if(element.qkidStatus!==3 || element.qkVideoStatus!=3)
                return false;
              break;
            case "3":
              if(element.qkidStatus!==4 || element.qkVideoStatus!=4)
                return false;
              break;
          }
          if(this.filters.qkSignStatus!="" && this.filters.qkSignStatus!=undefined && this.filters.qkSignStatus != element.qkSignStatus)
            return false;
          return true;
          }
          catch(e)
          {
            return false;
          }
        })
      }

    },
    getQingKeList()
    {
      if(this.filtedList!=undefined)
      {
        if(this.page<=0)
        {
          this.page=1;
        }
        let start = (this.page-1)*this.limit;
        let end = (this.page)*this.limit;
        //debugger;
        console.log("this.filetdList",this.filtedList);
        return this.filtedList.slice(start,end);
      }
    },
    getQingkeStateText(row)
    {
      let stateText = "";
      if(row.qkidStatus==3 && row.qkVideoStatus==3)
      {
        stateText="审核通过";
      }
      else if(row.qkidStatus==4 && row.qkVideoStatus==4)
      {
        stateText =  "审核不通过";
      }
      else if(row.qkidStatus==1 && row.qkVideoStatus==1 )
      {
        stateText= "已注册"
      }
      else
      {
        stateText = "审核中"
      }
      if(row.qkLicenseStatus==0)
      {
        stateText=stateText+",未上传营业执照";
      }
      else
      {
        stateText=stateText+",已上传营业执照";
      }
      return stateText;
    },
    getSignStateText(state)
    {
      if(state<1 || state >4)
        return "未知状态";
      let stateText=["未提交","已提交","审核通过","审核不通过"];
      return stateText[state - 1];
    },
    showSingWindow(row)
    {
      this.showSign = true;
      this.signUrl = process.env.BASE_IMGURL+"/"+row.qkSignUrl;
      this.currentQingkd = row;
    },
    approve(state)
    {

      if(state===4)
      {
        this.$confirm('您确定该人信息不通过审核?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.approveAction(state);
        }).catch(() => {

        });
      }
      else
      {
        this.approveAction(state);
      }
    },
    approveAction(state)
    {
      let data = {
        projectId:this.projectEntity.pNumb,
        qingkeId:this.currentQingkd.qkNumb,
        state:state
      }
      audit(data).then(res=>{
        if(res.data.data==true)
        {
          this.currentQingkd.qkSignStatus = state;
          this.$message({
            message: '签名信息审核成功！',
            type: 'success'
          });
          this.showSign = false;
        }
      })
    },
    handleDownload() {
      this.downloadLoading = true;
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['姓名',  '身份证号']
          const filterVal = ['qkName', 'qkId']
          const list = this.projectEntity.qingkeList
          const headStyle = {
            font:{
              name: "等线",
              sz: 12,
              color: "FF00FF88"
            },
            fill: {
              fgColor: {
                rgb: "FFFFFF00"
              }
            },
            border:{
              top: {
                style:'thin'},
              bottom:{
                style:'thin'},
              left:{
                style:'thin'},
              right:{
                style:'thin'}
            }
          };
          const data = this.formatJson(filterVal, list);
          const today = new Date();
          let filename = this.projectEntity.pName+"-"+"轻客列表-"+parseTime(today,'{y}{m}{d}{h}{i}{s}');
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: filename,
            autoWidth: true,
            bookType: "xlsx",
            title:this.projectEntity.pName+"-"+"轻客列表",
            headStyle:headStyle
          })
          this.downloadLoading = false
        })


    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        switch(j)
        {
          case "qkSex":
            return (v[j]===true?"女":"男");
          case "qkLicenseUrl":
            return v[j]===null?"":process.env.BASE_IMGURL+"/"+v[j];
          case "qkidStatus":
            return this.getStateText(v[j]);
          case "qkCreate":
          case "qkBirth":
            return parseTime(v[j],"{y}-{m}-{d}")
          default:
            return v[j];
        }
      }))
    },
    handleFilter()
    {
      this.page = 0;
      this.filtedList = this.filteList();
    }
  }
}
</script>


