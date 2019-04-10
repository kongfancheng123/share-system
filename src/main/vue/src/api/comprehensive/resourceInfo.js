// 引入配置中导出的对象
import { instance } from '@/axiosConfig.js'
//  过滤：条件查询资源
let getSearchData = {
    r: params => {
    return instance.post('/ResourceInfo/selectResourcesByQuery', params)
}
}

// 更新资源
let sendWarnData = {
  r: params => {
    return instance.post('/ResourceInfo/updateResource', params)
  }
}


// 删除资源
let deleteResource = {
    r: params => {
    return instance.post('/ResourceInfo/deleteResource', params)
  }
}

// 添加资源
let addResource = {
    r: params => {
    return instance.post('/ResourceInfo/addResource', params)
  }

}

// 多条件查询资源
let getresourceInfosByCondition = {
    r: params => {
    return instance.post('/ResourceInfo/getresourceInfosByCondition', params)
}

}


// 预约资源
let appointResource = {
    r: params => {
    return instance.post('/ResourceInfo/appointResource', params)
}

}

// 租赁资源
let leaseResource = {
    r: params => {
    return instance.post('/ResourceInfo/leaseResource', params)
}

}

// 归还资源
let backResource = {
    r: params => {
    return instance.post('/ResourceInfo/backResource', params)
}

}

// 拷贝资源
let copyResource = {
    r: params => {
    return instance.post('/ResourceInfo/copyResource', params)
}

}

export {
  getSearchData,
  sendWarnData,
    deleteResource,
    addResource,
    getresourceInfosByCondition,
    appointResource,
    leaseResource,
    backResource,
    copyResource
}
