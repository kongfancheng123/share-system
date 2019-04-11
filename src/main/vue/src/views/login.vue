<template>
  <el-container>
    <!-- el-header -->
    <el-header>

      <span>共享平台</span>
    </el-header>
    <router-view></router-view>
    <!-- el-main -->
    <el-main>
      <div class="loginFrom">
        <el-form :model="formLogin"
                 status-icon
                 :rules="rules2"
                 ref="formLogin">
          <h4>登录系统</h4>

          <el-form-item prop="name">
            <el-input type="text"
                      v-model="formLogin.name"
                      autocomplete="off"
                      placeholder="用户名"></el-input>
          </el-form-item>

          <el-form-item prop="pass">
            <el-input type="password"
                      v-model="formLogin.pass"
                      autocomplete="off"
                      placeholder="密码"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary"
                       class="login"
                       @click="submitForm('formLogin')">登 录</el-button>
          </el-form-item>

          <el-form-item>
                <el-button type="primary"
                                 class="login"
                                 @click="register('formLogin')">注册</el-button>
               </el-form-item>
        </el-form>
      </div>
    </el-main>

    <!-- el-footer -->
    <!--<el-footer>版权所有 苏州光格设备有限公司 苏ICP备14003533-1号</el-footer>-->
  </el-container>
</template>

<script>
import * as AJAX from '@/api/comprehensive/leaguerInfo.js'

export default {
  name: 'app',
  data() {
    let checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('名字不能为空'))
      }
      callback()
    }
    let checkPass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('密码不能为空'))
      }
      callback()
    }
    return {
      formLogin: {
        pass: ''
      }, // 登录表单
      rules2: {
        // 表单验证
        pass: [{ validator: checkPass, trigger: 'blur' }],
        name: [{ validator: checkName, trigger: 'blur' }]
      }
    }
  },
  methods: {
    submitForm(formName) {
      let vm = this
      vm.$refs[formName].validate(valid => {
        if (valid) {
            AJAX.login.r({leaguerName:vm.formLogin.name,password:vm.formLogin.pass}).then(res=>{
                   let data = res.data.data
                   if(res.data.code===200){
                        console.log(JSON.stringify(data))
                       vm.setCookie('user',JSON.stringify(data))
                       // 密码账号正确，跳转内页
                       console.log(res.data.data)
                       console.log(res.data.code)
                       console.log(res.data.isSuperUser)
                       if(res.data.data.isSuperUser===1){
                            vm.$router.push({ path: '/resourceInfo' })
                       }else{
                            vm.$router.push({ path: '/resourceInfo1' })
                       }

                   }else{
                        vm.$message.error("用户名或密码错误")
                   }
            })

        } else {

          console.log('error submit!!')
          return false
        }
      })
    },

     setCookie(name,value){
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
    },

    register(formName) {
          let vm = this
          vm.$refs[formName].validate(valid => {
            if (valid) {
                AJAX.addLeaguerInfo.r({leaguerName:vm.formLogin.name,password:vm.formLogin.pass}).then(res=>{
                       console.log(res.data.code)
                       if(res.data.code===200){
                            // 密码账号正确，跳转内页
                            vm.$message.success("注册成功,请重新登录")
                            //vm.$router.push({ path: '/resourceInfo' })
                       }else{
                            vm.$message.error("用户名已存在")

                       }
                })

            } else {

              console.log('error submit!!')
              return false
            }
          })
        },
  }
}
</script>

<style lang="scss" scoped>
.el-header,
.el-footer {
  background-color: #007ef3;
  line-height: 60px;
  text-align: center;
}
.el-header {
  font-size: 26px;
  color: #fff;
  position: relative;
  span {
    font-size: 28px;
    padding-right: 3px;
  }
  img {
    position: absolute;
    top: 18px;
    left: 10px;
  }
}
.el-main {
  display: flex;
  align-items: center;
  .loginFrom {
    width: 400px;
    margin: 0 auto;
    border: 1px solid #fff;
    box-shadow: 0px 0px 25px #bbb;
    padding: 50px;
    border-radius: 8px;
    text-align: center;
    h4 {
      font-size: 20px;
      margin-bottom: 30px;
    }
    .login {
      color: #fff;
      background-color: #409eff;
      border-color: #409eff;
      display: block;
      border-radius: 2px;
      width: 100%;
    }
  }
}
.el-footer {
  font-size: 14px;
  color: #fff;
}
</style>
