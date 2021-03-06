package cn.bysj.yty.qyyg.service;

import com.alibaba.fastjson.JSONObject;

public interface DepartmentService {
    /**
     * 新增部门
     *
     * @param departmentName
     * @param departmentDesc
     * @return
     * @throws Exception
     */
    JSONObject addDepartment(String departmentName, String departmentDesc, String departmentManager) throws Exception;

    /**
     * 分页条件查询部门列表信息
     *
     * @param departmentName
     * @param state
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    JSONObject queryDepartByCondition(String departmentName, String state, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * @Author
     * @Description 修改部门状态
     * @Date 22:47 2019/4/21
     * @Param [departId, state]
     * @return com.alibaba.fastjson.JSONObject
     **/
    JSONObject updateDepartState(Integer departId,Integer state) throws Exception;
}
