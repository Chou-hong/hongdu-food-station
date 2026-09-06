import request from '@/utils/request'
/**
 *
 * 员工管理
 *
 **/
// 登录、
export const login = (data: any) =>
  request({
    'url': '/staff/login',
    'method': 'post',
    data
  })
  // 退出
 export const userLogout = (params: any) =>
 request({
   'url': `/staff/logout`,
   'method': 'post',
   params
 })

export const getStaffList = (params: any) => {
  return request({
    url: '/staff/page',
    method: 'get',
    params
  })
}

// 修改---启用禁用接口
export const enableOrDisableStaff = (params: any) => {
  return request({
    url: `/staff/status/${params.status}`,
    method: 'post',
    params: { id:params.id }
  })
}

// 新增---添加员工
export const addStaff = (params: any) => {
  return request({
    url: '/staff',
    method: 'post',
    data: { ...params }
  })
}

// 修改---添加员工
export const editStaff = (params: any) => {
  return request({
    url: '/staff',
    method: 'put',
    data: { ...params }
  })
}

// 修改页面反查详情接口
export const queryStaffById = (id: string | (string | null)[]) => {
  return request({
    url: `/staff/${id}`,
    method: 'get'
  })
}
