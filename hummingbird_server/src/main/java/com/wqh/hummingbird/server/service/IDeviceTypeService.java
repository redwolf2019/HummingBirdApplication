package com.wqh.hummingbird.server.service;

import com.wqh.hummingbird.api.common.IdModel;
import com.wqh.hummingbird.server.common.LayuiPage;
import com.wqh.hummingbird.server.common.Response;
import com.wqh.hummingbird.server.common.bo.BasePage;
import com.wqh.hummingbird.server.common.bo.DeviceTypeAddBO;
import com.wqh.hummingbird.server.common.vo.DeviceTypeVO;

import java.util.List;

public interface IDeviceTypeService {

    Response<String> addDeviceType(DeviceTypeAddBO data);

    Response<String> deleteDeviceTypeById(Integer id);

    LayuiPage<DeviceTypeVO> getDeviceTypePage(BasePage data);

    List<IdModel> getTypeList();

}
