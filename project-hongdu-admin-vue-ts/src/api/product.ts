import request from '@/utils/request'
/**
 *
 * 菜品管理
 *
 **/
// 查询列表接口
export const getProductPage = (params: any) => {
  return request({
    url: '/product/page',
    method: 'get',
    params
  })
}

// 删除接口
export const deleteProduct = (ids: string) => {
  return request({
    url: '/product',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
export const editProduct = (params: any) => {
  return request({
    url: '/product',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
export const addProduct = (params: any) => {
  return request({
    url: '/product',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
export const queryProductById = (id: string | (string | null)[]) => {
  return request({
    url: `/product/${id}`,
    method: 'get'
  })
}

// 获取菜品分类列表
export const getCategoryList = (params: any) => {
  return request({
    url: '/category/list',
    method: 'get',
    params
  })
}

// 查菜品列表的接口
export const queryProductList = (params: any) => {
  return request({
    url: '/product/list',
    method: 'get',
    params
  })
}

// 文件down预览
export const commonDownload = (params: any) => {
  return request({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
export const productStatusByStatus = (params: any) => {
  return request({
    url: `/product/status/${params.status}`,
    method: 'post',
    params: { id: params.id }
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
