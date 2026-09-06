import request from '@/utils/request'
/**
 *
 * 套餐管理
 *
 **/

// 查询列表数据
export const getComboPage = (params: any) => {
  return request({
    url: '/combo/page',
    method: 'get',
    params,
  },)
}

// 删除数据接口
export const deleteCombo = (ids: string) => {
  return request({
    url: '/combo',
    method: 'delete',
    params: { ids }
  })
}

// 修改数据接口
export const editCombo = (params: any) => {
  return request({
    url: '/combo',
    method: 'put',
    data: { ...params }
  })
}

// 新增数据接口
export const addCombo = (params: any) => {
  return request({
    url: '/combo',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情接口
export const queryComboById = (id: string | (string | null)[]) => {
  return request({
    url: `/combo/${id}`,
    method: 'get'
  })
}

// 批量起售禁售
export const comboStatusByStatus = (params: any) => {
  return request({
    url: `/combo/status/${params.status}`,
    method: 'post',
    params: { id: params.ids }
  })
}

//菜品分类数据查询
export const productCategoryList = (params: any) => {
  return request({
    url: `/category/list`,
    method: 'get',
    params: { ...params }
  })
}
