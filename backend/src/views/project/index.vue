<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="项目编号" v-model="listQuery.projectId" style="width: 100px;" class="filter-item" />
      <el-input :placeholder="$t('table.projectName')" v-model="listQuery.projectName" style="width: 200px;" class="filter-item" />
      <el-input :placeholder="$t('enterprise.name')" v-model="listQuery.enterpriseName" style="width: 200px;" class="filter-item" />
      <el-select v-model="listQuery.projectState" :placeholder="$t('project.status')" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in projectStatusList" :key="item.pstatusNumb" :label="item.pstatusName" :value="item.pstatusNumb"/>
      </el-select>
      <el-date-picker
      v-model="dateRange"
      type="daterange"
      value-format="yyyy-MM-dd"
      range-separator="至"
      start-placeholder="创建开始日期"
      end-placeholder="创建结束日期" style="    margin-bottom: 10px;
    vertical-align: middle;">
    </el-date-picker>


      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="getList">{{ $t('table.search') }}</el-button>

      <router-link :to="'/project/create/'">
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit">{{ $t('table.add') }}</el-button>
      </router-link>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="pageList"
      :cell-style="{overflow:'visible'}"
      border
      fit
      highlight-current-row
      style="width: 100%;">
      <el-table-column label="编号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.pNumb }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('project.name')" min-width="150px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.pName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('project.content')" min-width="150px" align="center">
        <template slot-scope="scope">
          <!-- <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.title }}</span> -->
          {{ scope.row.pContent }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('enterprise.name')" min-width="110px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.enterpriseName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          {{ getStatusTextById(scope.row.pStatus) }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('project.pepoles')" align="center">
        <template slot-scope="scope">
          {{ scope.row.qkQuantity + "/" + scope.row.uploadLicenseNum}}
          <!-- <span v-if="scope.row.pageviews" class="link-type" @click="handleFetchPv(scope.row.pageviews)">{{ scope.row.pageviews }}</span>
          <span v-else>0</span> -->
        </template>
      </el-table-column>
      <el-table-column label="更新人/时间" class-name="status-col" width="100">
        <template slot-scope="scope">
          {{ scope.row.pOperator }}
          <p style="margin:0 auto">{{ scope.row.updateTime |parseTime("{y}-{m}-{d}") }}</p>

          <!-- <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag> -->
        </template>
      </el-table-column>

      <el-table-column label="创建人/日期" class-name="status-col" width="100">
        <template slot-scope="scope">
          {{ scope.row.pOperator }}
           <p style="margin:0 auto">{{ scope.row.createTime |parseTime("{y}-{m}-{d}") }}</p>

          <!-- <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag> -->
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleDiscard(scope.row)" v-if="scope.row.pStatus!=5">{{ $t('作废') }}</el-button>
          <router-link :to="'/project/detail/'+scope.row.pNumb">
          <el-button type="text" size="mini">{{ $t('详情') }}</el-button>
          </router-link>

          <router-link :to="'/project/qingke/'+scope.row.pNumb">
          <el-badge :value="scope.row.waitAudit" class="item" :hidden="scope.row.waitAudit<=0">
          <el-button type="text" size="mini">{{ $t('人员') }}</el-button>
           </el-badge>
          </router-link>

        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getPageList" />
  </div>
</template>
<style>
  .el-table .cell
  {
    overflow: visible;
  }
</style>


<script>
import { parseTime } from '@/utils'
import { fetchList, fetchStatus, discard} from '@/api/project'
import waves from '@/directive/waves' // Waves directive

import Pagination from '@/components/Pagination' // Secondary package based on el-pagination


export default {
  name: 'ProjectList',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },

  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      page:0,
      limit:10,
      listLoading: true,
      dateRange:[],
      listQuery: {
        projectId:"",
        createDateEnd:"",
        createDateStart:"",
        enterpriseName:"",
        projectName:"",
        projectState:undefined,
      },
      importanceOptions: [1, 2, 3],
      projectStatusList:[],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false,
      pageList:[]
    }
  },
  created() {
    this.getStatus().then(()=>{
      this.getList()
      })


  },
  methods: {
    getList() {

      this.listLoading = true
      if(this.dateRange.length>0)
      {
        this.listQuery.createDateStart = this.dateRange[0]
        this.listQuery.createDateEnd = this.dateRange[1]
      }
      // console.log(this.listQuery)
      fetchList(this.listQuery).then(response => {

        this.list = response.data.data
        this.page = 0;
        this.getPageList();
        this.total = this.list.length;
        // this.total = response.data.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    getPageList()
    {
      if(this.page<=0)
        this.page = 1;
      let start = (this.page-1)*this.limit;
      let end = this.page*this.limit;
      this.pageList =  this.list.slice(start,end);

    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },

    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    getStatus(){
      return new Promise((resolve, reject)=>
      {
        fetchStatus().then(response => {
          this.projectStatusList = response.data.data;
          resolve();
        })
      })

    },
    getStatusTextById(id)
    {
      // console.log("id====",id)
      let stateText = "";
      this.projectStatusList.forEach(element => {
        if(element.pstatusNumb === id)
        {
           stateText = element.pstatusName;
        }
      });
      return stateText;
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true


    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          this.temp.author = 'vue-element-admin'

        }
      })
    },
    handleUpdate(row) {

    },

    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
        }
      })
    },
    handleDelete(row) {
      this.$notify({
        title: '成功',
        message: '删除成功',
        type: 'success',
        duration: 2000
      })
      const index = this.list.indexOf(row)
      this.list.splice(index, 1)
    },
    handleFetchPv(pv) {
      // fetchPv(pv).then(response => {
      //   this.pvData = response.data.pvData
      //   this.dialogPvVisible = true
      // })
    },
    handleDiscard(row) {
      this.$confirm('您确定将'+row.pName+'作废，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        discard(row.pNumb).then(res=>{
          if(res.data.data === true)
          {
            this.$message({
              type: 'success',
              message: row.pName+'已作废！'
            });
            row.pStatus = 5;
          }
          else
          {
            this.$message({
              type: 'error',
              message: row.pName+'作废失败！'
            });
          }

        })

      })

    },
    handleDownload() {
      this.listLoading = true
      if(this.dateRange.length>0)
      {
        this.listQuery.createDateStart = this.dateRange[0]
        this.listQuery.createDateEnd = this.dateRange[1]
      }
      exportExcel(this.listQuery).then(res=>{
        this.downloadLoading = true;
        let exportList = res.data.data;
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
          const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
          const data = this.formatJson(filterVal, exportList)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: 'table-list'
          })
          this.downloadLoading = false;
          this.listLoading = false;
        })
      })

    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

