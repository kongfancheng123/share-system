// 引入配置中导出的对象
import { instance } from '@/axiosConfig.js'

// 登录
let login = {
    r: params => {
    return instance.post('/LeaguerInfo/login', params)
 }
}

// 更新会员
let sendWarnData = {
    r: params => {
    return instance.post('/LeaguerInfo/updateLeaguer', params)
}
}


// 删除会员
let deleteUser = {
    r: params => {
    return instance.post('/LeaguerInfo/deleteLeaguer', params)
}
}

// 添加/注册会员
let addLeaguerInfo = {
    r: params => {
    return instance.post('/LeaguerInfo/register', params)
}

}

//  过滤：条件查询赛事
let getSearchData = {
    r: params => {
    return instance.post('/LeaguerInfo/getLeaguersByLeaguerInfo', params)
}
}
export {
  login,
  sendWarnData,
  deleteUser,
    addLeaguerInfo,
    getSearchData
}
