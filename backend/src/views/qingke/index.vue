<template>
    <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="姓名" v-model="listQuery.name" style="width: 200px;" class="filter-item" />

      <el-select v-model="listQuery.qkidStatus" placeholder="身份证状态" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in approvalStates" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <!-- <el-select v-model="listQuery.qkidStatus" placeholder="身份证号码" clearable class="filter-item" style="width: 130px"> -->
        <el-input placeholder="身份证号码" v-model="listQuery.idCode" style="width: 200px;" class="filter-item" />
      <!-- </el-select> -->
      <el-select v-model="listQuery.qkVideoStatus" placeholder="活体状态" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in approvalStates" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-select v-model="listQuery.qkLicenseStatus" placeholder="营业执照状态" clearable class="filter-item" style="width: 130px">
        <el-option key="0" label="未上传" value="0"/>
        <el-option key="1" label="已上传" value="1"/>
      </el-select>
      <el-input placeholder="联系电话" v-model="listQuery.qkPhone" style="width: 200px;" class="filter-item" />
      <el-date-picker v-model="listQuery.dateRange"  type="daterange"
      range-separator="至"
      start-placeholder="加入日期-开始"
      end-placeholder="加入日期结束" style="margin-bottom: 10px; vertical-align: middle;">
      </el-date-picker>

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="getList">{{ $t('table.search') }}</el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-document" @click="handleDownload" :disabled="downloadLoading">导出</el-button>
      <!-- <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">{{ $t('table.add') }}</el-button> -->
    </div>

    <el-table

      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;">
      <el-table-column label="姓名" align="center" min-width="90px">
        <template slot-scope="scope">
          <span>{{ scope.row.qkName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" min-width="110px">
        <template slot-scope="scope">
          <!-- <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.title }}</span> -->
          {{ scope.row.qkPhone }}
        </template>
      </el-table-column>
      <el-table-column label="身份证号"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.qkId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态"  align="center" width="170px">
        <template slot-scope="scope">
            <el-row>
              <el-col :span="12" class="col-state-title">身份证:</el-col>
              <el-col :span="12" class="col-state-state">{{getStateText(scope.row.qkidStatus)}}</el-col>
              <!-- <el-tag :hit="false">{{getStateText(scope.row.qkidStatus)}}</el-tag> -->
            </el-row>
            <el-row>
              <el-col :span="12" class="col-state-title">活体:</el-col>
              <el-col :span="12" class="col-state-state">{{getStateText(scope.row.qkVideoStatus)}}</el-col>
            </el-row>
             <!-- <el-row>
              <el-col :span="12" class="col-state-title">签名:</el-col>
              <el-col :span="12" class="col-state-state">{{getStateText(scope.row.qkSignStatus)}}</el-col>
            </el-row> -->


        </template>
      </el-table-column>
      <el-table-column label="营业执照" align="center" width="90px">
        <template slot-scope="scope">
          {{ scope.row.qkLicenseStatus==0?"未上传":"已上传"}}
          <!-- <span v-if="scope.row.pageviews" class="link-type" @click="handleFetchPv(scope.row.pageviews)">{{ scope.row.pageviews }}</span>
          <span v-else>0</span> -->
        </template>
      </el-table-column>

      <el-table-column label="个体户名称" align="center" min-width="150px">
        <template slot-scope="scope">
          {{scope.row.qkSelfName}}
          <!-- <span v-if="scope.row.pageviews" class="link-type" @click="handleFetchPv(scope.row.pageviews)">{{ scope.row.pageviews }}</span>
          <span v-else>0</span> -->
        </template>
      </el-table-column>
      <el-table-column label="加入时间" align="center" width="95px">
        <template slot-scope="scope">
          {{ scope.row.qkCreate | parseTime('{y}-{m}-{d}') }}
          <!-- <span v-if="scope.row.pageviews" class="link-type" @click="handleFetchPv(scope.row.pageviews)">{{ scope.row.pageviews }}</span>
          <span v-else>0</span> -->
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <router-link :to="'/qingke/detail/'+scope.row.qkNumb">
          <el-button type="primary" size="mini">{{ $t('详情') }}</el-button>
          </router-link>
          <!-- <el-button v-if="scope.row.status!='published'" size="mini" type="success" @click="approval(scope.row)">审核
          </el-button>

          <el-button v-if="scope.row.status!='deleted'"  type="danger" @click="upload(scope.row)">上传执照
          </el-button> -->
        </template>
      </el-table-column>

    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
  </div>
</template>
<style scoped>
  .col-state-title{
    text-align: right;
  }
  .col-state-state{
    text-align: left;
    padding-left: 5px;
  }
  .el-upload-list{
    display: none;
  }
</style>

<script>
import waves from '@/directive/waves' // Waves directive
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { fetchList,exportExcel } from '@/api/qingke'
import { parseTime,dateDiff } from '@/utils'
export default {
  name: 'QingkeList',
  components: { Pagination },
  directives: { waves },
  data(){
    return{
      downloadLoading:false,
      listQuery:{
        name:"",
        qkidStatus:"",
        qkVideoStatus:"",
        qkLicenseStatus:"",
        qkPhone:"",
        startDate:null,
        endDate:null,
        limit:10,
        page:1,
        offset:0,
        dateRange:[]
      },
      dateRange:"",
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      approvalStates:[
        {
          value:"1",label:"未提交"
        },
        {
          value:"2",label:"已提交"
        },
        {
          value:"3",label:"审核通过"
        },
        {
          value:"4",label:"审核不通过"
        }]
    }
  },
  created()
  {
    this.getList();
  },
  methods:
  {
    getList(){
      if(this.listQuery.dateRange.length>1)
      {
        this.listQuery.startDate = parseTime(this.listQuery.dateRange[0],'{y}-{m}-{d} 00:00:00');
        this.listQuery.endDate = parseTime(this.listQuery.dateRange[1],'{y}-{m}-{d} 23:59:59');
      }

      fetchList(this.listQuery).then(res=>{
        // console.log(res);
        this.list = res.data.data.qingkeList;
        this.total = res.data.data.total;
      });
    },
    approval(element){},
    upload(element){},
    getStateText(state)
    {
      for(let i=0;i<this.approvalStates.length;i++)
      {
        // console.log("value=",this.approvalStates[i].value,"\r\n","state=",state)
        if(this.approvalStates[i].value==state.toString())
        {
          return this.approvalStates[i].label;
        }
      }
    },
    handleDownload() {
      this.listLoading = true
      if(this.dateRange.length>0)
      {
        this.listQuery.createDateStart = this.dateRange[0]
        this.listQuery.createDateEnd = this.dateRange[1]
      }
      this.downloadLoading = true;
      exportExcel(this.listQuery).then(res=>{

        let exportList = res.data.data;

        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['用户ID', '姓名', '联系电话', '微信openID', '身份证号','性别','出生',
          '住址','年龄','身份证有效期','身份证有效时间','身份认证状态','加入时间','首个合作企业名称',
          '首个合作项目名称','项目编号','个体户名称','统一信用代码','营业执照图片链接','税务登记','银行代扣代缴',
          '申请代缴']
          const filterVal = ['qkNumb', 'qkName', 'qkPhone', 'qkOpenid', 'qkId','qkSex',
          'qkBirth','qkidAddress','age','qkidValidity','expire','qkidStatus','qkCreate',
          'enterprisenName','projectName','projectId','qkSelfName','qkCreditCode',
          'qkLicenseUrl','tax','taxAgent','askAgent']
          const list = exportList
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
          let filename = "轻客平台导出信息表"+parseTime(today,'{y}{m}{d}{h}{i}{s}');
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: filename,
            autoWidth: true,
            bookType: "xlsx",
            title:"轻客平台导出信息表",
            headStyle:headStyle
          })
          this.downloadLoading = false
        })
      })


    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        switch(j)
        {
          case "qkSex":
            return (v[j]===true?"女":"男");
          case "qkLicenseUrl":
            return v[j]==null?"":process.env.BASE_IMGURL+"/"+v[j];
          case "qkidStatus":
            return this.getStateText(v[j]);
          case "qkCreate":
          case "qkBirth":
            return parseTime(v[j],"{y}-{m}-{d}")
          default:
            return v[j];
        }
      }))
    }

  }

}
</script>

