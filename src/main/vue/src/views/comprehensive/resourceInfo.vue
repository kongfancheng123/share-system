<template>
  <div class="pad20">
    <!-- 筛选 -->
    <div class="filterbar">

      <el-form :model="formSearch"
               size="small"
               :inline="true"
               :rules="rules"
               ref="formSearch"
               label-width="80px">


        <el-form-item label="开始时间"
                                      prop="gameTime">
                          <el-date-picker v-model="formSearch.startTime"
                                          type="datetime"
                                          placeholder="选择日期时间">
                          </el-date-picker>
                        </el-form-item>

        <el-form-item label="结束时间"
                                              prop="gameTime">
                                  <el-date-picker v-model="formSearch.endTime"
                                                  type="datetime"
                                                  placeholder="选择日期时间">
                                  </el-date-picker>
                                </el-form-item>


        <el-form-item label="查询方式"
                              prop="state">
                  <el-select v-model="formSearch.state"
                             placeholder="请选择">
                    <el-option v-for="item in stateList"
                               :key="item.value"
                               :label="item.label"
                                :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>

        <!-- 查询 -->
        <el-form-item>
          <el-button type="primary"
                     @click="searchSumit">查询</el-button>
        </el-form-item>

        <el-form-item>
           <el-button type="primary"
                           v-if='user.isSuperUser===1'  @click="SendEvent">添加</el-button>
        </el-form-item>

      </el-form>
    </div>

    <!--表格 -->
    <el-table :data="tableData"
              border
              style="width: 100%">
      <el-table-column type="index">
      </el-table-column>
      <el-table-column prop="id"
                       label="编号"
                       width="180">
      </el-table-column>
      <el-table-column prop="resourceName"
                       label="资源名称">
      </el-table-column>

      <el-table-column prop="resourceDesc"
                             label="资源描述">
            </el-table-column>


      <el-table-column prop="appointmentTime"
                       label="预约时间">
      </el-table-column>


      <el-table-column prop="leaseTime"
                                   label="被租赁时间">
                  </el-table-column>

        <el-table-column prop="backTime"
                                           label="应归还日期">
                          </el-table-column>

       <el-table-column prop="state"
                                                  label="状态">
                                </el-table-column>

        <el-table-column prop="userName"
                                      label="操作人姓名">
          </el-table-column>

      <el-table-column   v-if='user.isSuperUser===1'  label="操作">
        <template slot-scope="scope">

          <el-button size="mini"
                     @click="warnEvent(scope.$index, scope.row,'更新',0)">更新</el-button>

          <el-button size="mini"
                     type="danger"
                     @click="deleteResource(scope.$index, scope.row,'更新',0)">删除</el-button>

           <el-button size="mini"
                                @click="copyResource(scope.$index, scope.row,'更新',0)">拷贝</el-button>



        </template>
      </el-table-column>
    </el-table>

    <!--page分页信息 -->
    <div class="page">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="pageNow"
                     :page-sizes="[30, 50, 100]"
                     :page-size="30"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="totalPage">
      </el-pagination>
    </div>

    <!--弹出层：添加资源 -->
    <el-dialog title="添加"
               width="880px"
               :visible.sync="dialog.sendFlag">
        <el-form :model="formWarn"
                       size="small"
                       :rules="rules"
                       ref="formWarn"
                       label-width="100px">


                <el-form-item label="资源名称"
                              prop="resourceName">
                  <el-input v-model.number="formWarn.resourceName"
                            placeholder="请输入资源名称"></el-input>
                </el-form-item>

                <el-form-item label="资源描述"
                              prop="resourceDesc">
                  <el-input v-model.number="formWarn.resourceDesc"
                            placeholder="请输入资源描述"></el-input>
                </el-form-item>

              </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialog.sendFlag = false"
                   size="small">取 消</el-button>
        <el-button type="primary"
                   size="small"
                   @click="addResource('formWarn')">确 定</el-button>
      </div>
    </el-dialog>

    <!--弹出层：更新资源 -->
    <el-dialog :title="title"
               width="600px"
               :visible.sync="dialog.warnFlag">
      <el-form :model="formWarn"
               size="small"
               :rules="rules"
               ref="formWarn"
               label-width="100px">

        <el-form-item label="资源编码"
                      prop="id">
          <el-input v-model.number="formWarn.id" disabled
                              placeholder="资源编码"></el-input>
        </el-form-item>

        <el-form-item label="资源名称"
                      prop="resourceName">
          <el-input v-model.number="formWarn.resourceName"
                    placeholder="资源名称"></el-input>
        </el-form-item>

        <el-form-item label="资源描述"
                      prop="resourceDesc">
          <el-input v-model.number="formWarn.resourceDesc"
                    placeholder="资源描述"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialog.warnFlag = false"
                   size="small">取 消</el-button>
        <el-button type="primary"
                   size="small"
                   @click="warnSumit('formWarn')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import * as AJAX from '@/api/comprehensive/resourceInfo.js'

export default {
  name: 'Comprehensive',
  data() {
    return {
       user: null,
      // 当前页码
      pageNow: 1,
      pageSize: 30,
      totalPage: 0,
      // 弹窗flag
      dialog: {
        sendFlag: false,
        warnFlag: false,
      },
      title: null,
      // 表格数据
      tableData: [],
      // 查询方式
            stateList: [
              {
                value: 0,
                label: '可预约'
              },
              {
                value: 1,
                label: '已预约'
              },
              {
                value: 2,
                label: '已租赁'
              },
              {
                 value: 3,
                 label: '已超时'
               },
            ],

      // 轮询Num
      pollingNum: null,
      // 轮询时间: 根据上送周期时长
      pollingTime: 2000,
      // 表单：查询表单
      formSearch: {
        startTime: null,
        endTime:null,
        pageNow: 1,
        pageSize: 30
      },
      // 表单：获取数据page表单
      formPage: {
        resourceName: null,
        pageNow: 1,
        pageSize: 30
      },
      // 表单：报警发送
      formWarn: {
        id: null, // 类型：Number  必有字段  备注：事件码
        resourceName: null, // 类型：Number  必有字段  备注：事件类型
        resourceDesc: null
      },
      // 当前页码
      currentPage: 4,
      // 验证规则
      rules: {
        parentNodeCode: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ],
        alarmUpdate: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ],
        equipmentType: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ],
        equipmentPropertyTemplateCode: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ],
        baseValue: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ],
        upAndDown: [
          { required: true, message: '内容不能为空', trigger: 'blur' }
        ],
        eventCode: [
          {
            type: 'number',
            required: true,
            message: '内容不能为空',
            trigger: 'blur'
          }
        ],
        eventType: [
          {
            type: 'number',
            required: true,
            message: '内容不能为空',
            trigger: 'blur'
          }
        ],
        eventValue: [
          {
            type: 'number',
            required: true,
            message: '内容不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },

  methods: {
      getCookie(name){
          var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
          if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
          } else{
           return null;}

      },

    /* 数据校验是否重复 
      01: 400 说明数据库存在, 相同数据
      02：vm.$refs[formName].resetFields() 重置表单
      03:
        response: 后端返回数据
        formName: 表单名
        dialogFlag : 被关闭的弹层flag
        sucessMsg: 成功提示文字
    */
    check(response, formName, dialogFlag, sucessMsg) {
      let vm = this
      setTimeout(() => {
        // 删除表单不需要重置
        if (formName !== 'formDelete') {
          vm.$refs[formName].resetFields()
        }
      }, 200)
      if (formName === 'formSearch') {
        vm.tableData = response.data.data.items
        return false
      }
      vm.dialog[dialogFlag] = false
      response.data.code === 400
        ? vm.$message.error(response.data.message)
        : vm.$message.success(sucessMsg)
      vm.getPageData()
    },

    /* 后端请求
      01: 参数解释：
          urlName：调用的那个接口
          formName：表单名字
          dialogFlag：关闭的弹层falg
          sucessMsg：成功提示文字
          failMsg：失败提示文字
      02：
      vm.check()校验数据库重复，返回提示
    */
    httpRequst(urlName, formName, dialogFlag, sucessMsg, failMsg) {
      let vm = this
      var str =vm[formName].gameTime+"";
      console.log(vm[formName].gameTime);
      if(str.indexOf("中国标准时间")>-1){
        vm[formName].gameTime = vm.formatTime(+vm[formName].gameTime)
      }
      console.log(vm[formName].gameTime);
      AJAX[urlName].r(vm[formName]).then(response => {
        console.log(response)
          vm.check(response, formName, dialogFlag, sucessMsg)
        }).catch(error => {
          vm.$message.error(failMsg)
          console.log(error)
        })
    },

     //时间格式转换
    add0(m){
        return m<10?'0'+m:m
    },
    formatTime(shijianchuo){
         let time = new Date(shijianchuo);
         let y = time.getFullYear();
         let m = time.getMonth()+1;
         let d = time.getDate();
         let h = time.getHours();
         let mm = time.getMinutes();
         let s = time.getSeconds();
         return y+'-'+this.add0(m)+'-'+this.add0(d)+' '+this.add0(h)+':'+this.add0(mm)+':'+this.add0(s);
     },


    /*
    过滤条件 监测：设备类型，设备信号模板选择事件
    获取: 弹窗表格展示数据 
  */

    /* 获取分页数据 */
    getPageData() {
      let vm = this
      AJAX.getresourceInfosByCondition
        .r(vm.formPage)
        .then(response => {
          vm.tableData = response.data.data.items
          vm.totalPage = response.data.data.totalNum
          //vm.pollingEvent()
        })
        .catch(error => {
          console.log(error)
        })
    },

    /* 查询*/
    searchSumit() {
      let vm = this
      console.log(vm.formSearch.startTime);
      var startTime =vm.formSearch.startTime+"";
            if(startTime.indexOf("中国标准时间")>-1){
              vm.formSearch.startTime = vm.formatTime(+vm.formSearch.startTime)
            }
        console.log(vm.formSearch.startTime);
      var endTime =vm.formSearch.endTime+"";
                  if(endTime.indexOf("中国标准时间")>-1){
                    vm.formSearch.endTime = vm.formatTime(+vm.formSearch.endTime)
                  }
      vm.$refs.formSearch.validate(valid => {
        if (valid) {
          vm.formPage = { ...vm.formSearch }
          vm.getPageData()
        } else {
          return false
        }
      })
    },
    /* 开始发送事件*/
    SendEvent() {
      let vm = this
      vm.dialog.sendFlag = true
    },

    /* 轮训数据*/
    /*pollingEvent() {
      let vm = this
      clearTimeout(vm.pollingNum)
      vm.pollingNum = setTimeout(() => {
        vm.getPageData()
      }, 1500)
    },*/





    /* 更新资源
    02: warnEvent 弹窗
   */
    warnEvent(index, row, txt, num) {
      let vm = this
      vm.title = txt
      vm.formWarn.id = row.id
      vm.formWarn.resourceName = row.resourceName
      vm.formWarn.resourceDesc = row.resourceDesc

      // 0:遥测 1:遥信 不是这两个，则不能发送
      vm.dialog.warnFlag = true
    },
    /* 更新资源
       03：warnSumit 提交发送报警表单
      */
    warnSumit(formName) {
      let vm = this
      vm.$refs[formName].validate(valid => {
        if (valid) {
          vm.httpRequst('sendWarnData',formName,'warnFlag','更新成功','更新失败')
        } else {
          return false
        }
      })
    },

    /*添加资源 */
    addResource(formName) {
          let vm = this
          vm.$refs[formName].validate(valid => {
            if (valid) {
              vm.httpRequst('addResource',formName,'sendFlag','添加成功','添加失败')
            } else {
              return false
            }
          })
        },

    /*删除资源 */
    deleteResource(index, row, txt, num) {
          let vm = this
           AJAX.deleteResource.r({id:row.id}).then(res=>{
            console.log(res)
            vm.getPageData()
           })
        },

    /*拷贝资源 */
    copyResource(index, row, txt, num) {
          let vm = this
           AJAX.copyResource.r({resourceName:row.resourceName,resourceDesc:row.resourceDesc}).then(res=>{
            console.log(res)
            vm.getPageData()
           })
        },



    /* 分页功能*/
    handleSizeChange(val) {
      let vm = this
      vm.pageSize = val
      vm.formPage.pageSize = val
      vm.getPageData()
    },
    handleCurrentChange(val) {
      let vm = this
      vm.pageNow = val
      vm.formPage.pageNow = val
      vm.getPageData()
    }
  },
  /*
    00: 获取基本设置：数据使能，报警使能 0关闭 1开启
    01: 获取筛选数据
    02: 获取表格数据
  */
  created() {
    let vm = this
    vm.user = JSON.parse(vm.getCookie('user'))
    //vm.getReadyData()
  },
  destroyed() {
    let vm = this
    clearTimeout(vm.pollingNum)
  }
}</script>


<style scoped lang="scss">
.pad20 {
  padding: 20px;
}
.filterbar {
  padding: 5px 0 20px 0;
}
</style>
