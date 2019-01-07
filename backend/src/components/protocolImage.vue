<template>
  <el-container>
    <el-header>
        <el-upload
          class="upload-demo"
          action="http://localhost:9008/util/upload?type=protocol"
          >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
    </el-header>
    <el-container>
    <el-aside width="180px">
      <div>
        <table>
          <tr>
            <td><span>1</span></td>
            <td><img src="http://img.php.cn/upload/article/000/061/021/65f9ce46f82329446be5cac730756df7-4.jpg" width="100px"/></td>
          </tr>
           <tr>
            <td><span>1</span></td>
            <td><img src="http://img.php.cn/upload/article/000/061/021/65f9ce46f82329446be5cac730756df7-4.jpg" width="100px"/></td>
          </tr>
           <tr>
            <td><span>1</span></td>
            <td><img src="http://img.php.cn/upload/article/000/061/021/65f9ce46f82329446be5cac730756df7-4.jpg" width="100px"/></td>
          </tr>
           <tr>
            <td><span>1</span></td>
            <td><img src="http://img.php.cn/upload/article/000/061/021/65f9ce46f82329446be5cac730756df7-4.jpg" width="100px"/></td>
          </tr>
           <tr>
            <td><span>1</span></td>
            <td><img src="http://img.php.cn/upload/article/000/061/021/65f9ce46f82329446be5cac730756df7-4.jpg" width="100px"/></td>
          </tr>
        </table>

      </div>

    </el-aside>

    <el-main>
        <img src="http://img.php.cn/upload/article/000/061/021/65f9ce46f82329446be5cac730756df7-4.jpg"/>

    </el-main>
    </el-container>
  </el-container>
</template>
<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  img{
    margin: 0;
    padding: 0;
  }
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
